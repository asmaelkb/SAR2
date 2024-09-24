package implementation;

public class Channel extends AbstractChannel {

	CircularBuffer in;
	CircularBuffer out;
	boolean disconnected;

	public Channel(CircularBuffer in, CircularBuffer out){
		this.disconnected = false;

		this.in = in;
		this.out = out;
	}

	@Override
	public synchronized int read(byte[] bytes, int offset, int length) throws UnsupportedOperationException {
		
		int bytesRead = 0;
		
		if (disconnected()) {
			throw new IllegalStateException("The channel is disconnected");
		}
		
		while(in.empty()) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				throw new IllegalStateException("The buffer is empty");
			}
		}
		
		while (!in.empty() && bytesRead < length) {
			bytes[offset + bytesRead] = in.pull();
			bytesRead++;
		}
		
		notifyAll();
		return bytesRead;
	}

	@Override
	public synchronized int write(byte[] bytes, int offset, int length) throws UnsupportedOperationException {
		int bytesWritten = 0;
		
		if (disconnected()) {
			throw new IllegalStateException("The channel is disconnected");
		}
		
		while(out.full()) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				throw new IllegalStateException("The buffer is full");
			}
		}
		
		while(!out.full() && bytesWritten < length) {
			out.push(bytes[offset + bytesWritten]);
			bytesWritten++;
		}
		
		notifyAll();
		return bytesWritten;
	}

	@Override
	public void disconnect() throws UnsupportedOperationException {
		this.disconnected = true;
	}

	@Override
	public boolean disconnected() throws UnsupportedOperationException {
		return this.disconnected;
	}

}

