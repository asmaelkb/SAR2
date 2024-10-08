package implementation;

import events.EventQueueBroker;

public abstract class AbstractChannel {
	
	EventQueueBroker b;
	
	public AbstractChannel(EventQueueBroker b) {
		this.b = b;
	}
	
	public abstract int read(byte[] bytes, int offset, int length) throws DisconnectedException;
	public abstract int write(byte[] bytes, int offset, int length) throws DisconnectedException;
	public abstract void disconnect();
	public abstract boolean disconnected();
}