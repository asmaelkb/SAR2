package implementation;

public class SimpleChannel extends Channel {

	@Override
	public
	int read(byte[] bytes, int offset, int length) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public
	int write(byte[] bytes, int offset, int length) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public
	void disconnect() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public
	boolean disconnected() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}

