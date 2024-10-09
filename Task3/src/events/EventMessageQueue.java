package events;

import java.util.List;

import abs.AbstractEventMessageQueue;
import implementation.Channel;
import implementation.DisconnectedException;

import java.util.LinkedList;

public class EventMessageQueue extends AbstractEventMessageQueue{
	
	Channel channel;
	public List<Message> pendingMessages = new LinkedList<>();

    public EventMessageQueue(String name) {
		super(name);
	}

	private Listener listener;
    private boolean isClosed = false;

    public interface Listener {
        void received(byte[] msg);
        void closed();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public boolean send(byte[] bytes) throws DisconnectedException {
    	Message message = new Message(bytes, 0, bytes.length);
        return send(message);
    }

    public synchronized boolean send(Message message) throws DisconnectedException {
        if (isClosed) {
            System.out.println("MessageQueue is closed. Cannot send message.");
            return false;
        }

        pendingMessages.add(message);
        synchronized (pendingMessages) {
            pendingMessages.notify();
        }
        return true;
    }


    public void close() {
        isClosed = true;
        if (listener != null) {
            listener.closed();
        }
        System.out.println("MessageQueue closed.");
    }

    public boolean closed() {
        return isClosed;
    }


	@Override
	protected void setListener(abs.AbstractEventMessageQueue.Listener l) {
		this.listener = (Listener) l;
	}
}