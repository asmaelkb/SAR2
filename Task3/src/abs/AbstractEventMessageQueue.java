package abs;

import events.Message;
import implementation.DisconnectedException;

public abstract class AbstractEventMessageQueue {

	
	public interface Listener {
		void received(Message msg);
		void sent(Message msg);
		void closed();
	}
	
	public AbstractEventMessageQueue(String name) {
		// TODO Auto-generated constructor stub
	}

	protected abstract void setListener(Listener l);
	
	public abstract boolean send(byte[] bytes) throws DisconnectedException;
	public abstract boolean send(Message msg) throws DisconnectedException;
	
	public abstract void close();
	public abstract boolean closed();
}
