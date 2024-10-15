package implementation;

import java.util.HashMap;
import java.util.Map;

import abs.AbstractBroker;
import abs.AbstractChannel;

public class Broker extends AbstractBroker {

	BrokerManager bm = BrokerManager.getSelf();;
	Map<Integer, Rdv> accepts;
	
    public Broker(String name) {
        super(name);
        accepts = new HashMap<Integer, Rdv>();
        this.bm.addBrokers(this);
    }

    @Override
    public synchronized AbstractChannel accept(int port) throws InterruptedException, IllegalStateException {
       Rdv rdv = null;
       
       synchronized(accepts) {
    	   rdv = accepts.get(port);
    	   if(rdv != null) {
    		   throw new IllegalStateException("Port " + port + "is already accepting");
    	   }
    	   rdv = new Rdv();
    	   accepts.put(port, rdv);
    	   accepts.notifyAll();
       }
       
       return rdv.accept(this, port);
    }

    @Override
    public AbstractChannel connect(String name, int port) throws InterruptedException {
    	Broker b = (Broker)bm.getBroker(name);
    	
    	if (b == null) 
    		return null;
    	
    	return b._connect(this, port);
    	
    }
    
    private AbstractChannel _connect(Broker b, int port) throws InterruptedException {
    	Rdv rdv = null;
    	synchronized(accepts) {
    		rdv = accepts.get(port);
    		while (rdv == null) {
    			try {
    				accepts.wait();
    			} catch(InterruptedException e) {
    				
    			}
    			rdv = accepts.get(port); 
    		}
    		accepts.remove(port);
    	}
    	return rdv.connect(b, port);
    }
    
    public String getName() {
    	return this.name;
    }
    
	 public synchronized Rdv findOrCreateRdv(int port) {
		 
		 Rdv Rdv = accepts.get(port);
		 
		 if (Rdv == null) {
			 Rdv = new Rdv(); 
			 accepts.put(port, Rdv);
		 }
		 
		 return Rdv;
    }

}