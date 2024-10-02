package implementation;

public abstract class AbstractChannel {
	
	Broker b;
	
	public AbstractChannel(Broker b) {
		this.b = b;
	}
	
	public abstract int read(byte[] bytes, int offset, int length) throws DisconnectedException;
	public abstract int write(byte[] bytes, int offset, int length) throws DisconnectedException;
	public abstract void disconnect();
	public abstract boolean disconnected();
}