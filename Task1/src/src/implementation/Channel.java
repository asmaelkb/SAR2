package implementation;

public class Channel extends AbstractChannel {
	
	CircularBuffer in;
	CircularBuffer out;
	
	boolean isConnected;
	
	public Channel(CircularBuffer in, CircularBuffer out) {
		this.in = in;
		this.out = out;
		this.isConnected = true;
	}

    @Override
    public
    synchronized int read(byte[] bytes, int offset, int length) throws UnsupportedOperationException, IllegalStateException {
    	
    	if(!isConnected) {
    		throw new IllegalStateException("The channel is not connected");
    	}
    	
    	int bytesRead = 0;
    	
    	// si le buffer de lecture est vide mais que le channel est connecté
    	while(in.empty() && isConnected) {
    		try {
    			wait();
    		} catch (InterruptedException e) {
    			throw new IllegalStateException("The reading buffer is empty");
    		}
    	}
    	
    	// si le channel se déconnecte après l'attente
    	if(!isConnected) {
    		throw new IllegalStateException("The channel is not connected");
    	}
    	
    	while(!in.empty() && bytesRead < length) {
    		bytes[offset + bytesRead] = in.pull();
    		bytesRead ++;
    	}
    	
    	notifyAll();
        return bytesRead;
    }

    @Override
    public
    synchronized int write(byte[] bytes, int offset, int length) throws IllegalStateException {
    	
    	if(!isConnected) {
    		throw new IllegalStateException("The channel is not connected");
    	}
    	
    	int bytesWritten = 0;
    	
    	// si le buffer d'écriture est plein mais que le channel est connecté
    	while(out.full() && isConnected) {
    		try {
    			wait();
    		} catch (InterruptedException e) {
    			throw new IllegalStateException("The writing buffer is full");
    		}
    	}
    	
    	// si le channel se déconnecte après l'attente
    	if(!isConnected) {
    		throw new IllegalStateException("The channel is not connected");
    	}
    	
    	while(!out.full() && bytesWritten < length) {
    		out.push(bytes[offset + bytesWritten]);
    		bytesWritten ++;
    	}
    	
    	notifyAll();
        return bytesWritten;
    }

    @Override
    public
    void disconnect() throws IllegalStateException {
    	if(!isConnected) {
    		throw new IllegalStateException("The channel is already disconnected");
    	}
    	
    	this.isConnected = false;
    }

    @Override
    public
    boolean disconnected() throws UnsupportedOperationException {
        return !isConnected;
    }

}