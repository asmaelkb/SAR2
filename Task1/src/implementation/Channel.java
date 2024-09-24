package implementation;

public class Channel extends AbstractChannel {

	CircularBuffer in;
	CircularBuffer out;
	boolean disconnected;

	public Channel(CircularBuffer in, CircularBuffer out){
		this.disconnected = false;

		this.in = in;
		this.out = out;

		// this.in = new CircularBuffer(256);
		// this.out = new CircularBuffer(256);
	}

	@Override
	public int read(byte[] bytes, int offset, int length) throws UnsupportedOperationException {
		
	}

	@Override
	public int write(byte[] bytes, int offset, int length) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void disconnect() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public boolean disconnected() throws UnsupportedOperationException {
		return this.disconnected;
	}

}

