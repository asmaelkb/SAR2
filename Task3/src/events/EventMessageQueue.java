package events;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import abs.AbstractEventMessageQueue;
import implementation.Channel;
import implementation.DisconnectedException;
import implementation.MessageQueue;
import implementation.Task;
import taskevents.CloseTaskEvent;
import taskevents.SendTaskEvent;

public class EventMessageQueue extends AbstractEventMessageQueue{
	
	public Channel channel;
	public MessageQueue msgQueue;
	
	private final Thread reader;
	private final Thread writer;

    public boolean isClosed = false;
    
    public BlockingQueue<Message> pendingMsg = new LinkedBlockingQueue<>();
    private Listener listener;

    public EventMessageQueue(MessageQueue msgQueue) {
		this.msgQueue = msgQueue;
		this.channel = this.msgQueue.channel;
		this.writer = new Thread(() -> sender());
		this.reader = new Thread(() -> receiver());
		this.writer.start();
	}

    public boolean send(byte[] bytes) throws DisconnectedException {
    	Message message = new Message(bytes, 0, bytes.length);
        return send(message);
    }
    
    public boolean send(Message message) {
    	SendTaskEvent sendMsg = new SendTaskEvent(this, message, listener);
    	Executor.getSelf().post(sendMsg);
    	return true;
    }
    
    public boolean _send(Message message) throws DisconnectedException {
        if (isClosed) {
            System.out.println("MessageQueue is closed. Cannot send message.");
            return false;
        }
        
        synchronized (pendingMsg) {
        	pendingMsg.add(message);
        	pendingMsg.notify();
        }
        
        return true;
    }
   
    
    private void receiver() {
        while (true) {

            try {
                byte[] msg = msgQueue.receive(); // bloquant
                Message message = new Message(msg, 0, msg.length);
                if (listener != null) {
                	listener.received(message);
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    private void sender() {
        while (true) {
            try {
                Message msg;
                synchronized (pendingMsg) {
                    if (!pendingMsg.isEmpty()) {
                        msg = pendingMsg.poll();
                    } else {
                    	pendingMsg.wait();
                        continue;
                    }
                }
                msgQueue.send(msg.bytes, 0, msg.length); // bloquant
            } catch (Exception e) {
                break;
            }
        }

    }

    public void close() {
        CloseTaskEvent closeTask = new CloseTaskEvent(this, listener);
        Executor.getSelf().post(closeTask);
        
    }

    public boolean closed() {
        return isClosed;
    }
   

	@Override
	public void setListener(abs.AbstractEventMessageQueue.Listener l) {
		this.listener = (Listener) l;
		this.reader.start();
	}
}