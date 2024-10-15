package implementation;

import abs.AbstractChannel;
import events.EventQueueBroker;

public class Channel extends AbstractChannel {
	
	public CircularBuffer in, out;
	public Channel rch; // Remote channel
	
	int port;
	
	boolean isConnected;
	boolean dangling; // Booléen qui indique que l'autre bout est déconnecté
	String rname;
	
	public Channel(Broker b, int port, CircularBuffer in, CircularBuffer out) {
		super(b);
		this.port = port;
		this.in = in;
		this.out = out;
		this.isConnected = true;
	}

    @Override
    public int read(byte[] bytes, int offset, int length) throws DisconnectedException {
        if (!isConnected) throw new DisconnectedException("Can't read if you're not connected");
        
        int nbRead = 0;
        
        try {
            while (nbRead == 0) {
                if (this.in.empty()) {
                    synchronized(this.in) {
                        while(this.in.empty()) {
                            if (!isConnected || dangling) {
                                throw new DisconnectedException("Can't read if you're not connected");
                            }
                            try {
                                this.in.wait();
                            } catch (InterruptedException e) {
                                
                            }
                        }
                    }
                }
                while (nbRead < length && !this.in.empty()) {
                    bytes[offset + nbRead++] = this.in.pull();
                }
                if (nbRead != 0) {
                    synchronized (this.in) {
                        this.in.notify();
                    }
                }
            }
        } catch (DisconnectedException e) {
            if (this.isConnected) {
                this.isConnected = false;
                synchronized (this.out) {
                    this.out.notifyAll();
                }
            }
            throw e;
        }
        
        return nbRead;
    }



    @Override
    public int write(byte[] bytes, int offset, int length) throws DisconnectedException {
        if (!isConnected) throw new DisconnectedException("Can't write if you're not connected");
        
        int nbWritten = 0;
        
        while (nbWritten == 0) {
            if (this.out.full()) {
                synchronized(this.out) {
                    while(this.out.full()) {
                        if (!this.isConnected) {
                            throw new DisconnectedException("Can't read if you're not connected");
                        }
                        if (this.dangling) {
                            return length;
                        }
                        try {
                            this.out.wait();
                        } catch (InterruptedException e) {
                            
                        }
                    }
                }
            }
            while (nbWritten < length && !this.out.full()) {
                this.out.push(bytes[offset + nbWritten++]);
            }
            if (nbWritten != 0) {
                synchronized (this.out) {
                    this.out.notify();
                }
            }
        }
        
        return nbWritten;
    }

    @Override
    public
    void disconnect() throws IllegalStateException {
    	if(!isConnected) {
    		throw new IllegalStateException("The channel is already disconnected");
    	}
    	
    	this.isConnected = false;
    	this.rch.dangling = true;
    }

    @Override
    public
    boolean disconnected() throws UnsupportedOperationException {
        return !isConnected;
    }
    
    public void connect(Channel remoteChannel, String name) {
    	this.rch = remoteChannel;
    	this.rch.rch = this;
    	
    	this.rch.out = this.in;
    	this.out = rch.in;
    	
    	rname = name;
    }

}