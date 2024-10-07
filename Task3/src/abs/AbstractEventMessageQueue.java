package abs;

import implementation.DisconnectedException;

public abstract class AbstractEventMessageQueue {

	public AbstractEventMessageQueue(String name) {
		
	}
	
	public interface Listener {
		void received(byte[] msg);
		void closed();
	}
	
	protected abstract void setListener(Listener l);
	
	public abstract boolean send(byte[] bytes) throws DisconnectedException;
	public abstract boolean send(byte[] bytes, int offset, int length) throws DisconnectedException;
	
	public abstract void close();
	public abstract boolean closed();
}
