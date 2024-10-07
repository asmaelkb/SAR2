package events;

import abs.AbstractEventMessageQueue;
import implementation.Channel;
import implementation.DisconnectedException;

public class EventMessageQueue extends AbstractEventMessageQueue{
	
	Channel channel;

    public EventMessageQueue(String name) {
		super(name);
		// TODO Auto-generated constructor stub
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
        return send(bytes, 0, bytes.length);
    }

    public boolean send(byte[] bytes, int offset, int length) throws DisconnectedException {
        if (isClosed) {
            System.out.println("MessageQueue is closed. Cannot send message.");
            return false;
        }
        channel.write(bytes, offset, length);
        byte[] message = new byte[length];
        System.arraycopy(bytes, offset, message, 0, length);
        
        if (listener != null) {
            listener.received(message);
        }
        System.out.println("Message sent successfully.");
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