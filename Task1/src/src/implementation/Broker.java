package implementation;

public class Broker extends AbstractBroker {

	
    public Broker(String name, BrokerManager bm) {
        super(name, bm);
        
        super.bm.addBrokers(this);
    }

    @Override
    public synchronized AbstractChannel accept(int port) throws InterruptedException, IllegalStateException {
       Rdv Rdv = bm.findOrCreateRdv(port);
       
       if(Rdv.acceptBroker != null) {

    	   throw new IllegalStateException("Another broker is already accepting on this port");
       }
       
       return Rdv.accept(this);
    }

    @Override
    public synchronized AbstractChannel connect(String name, int port) throws InterruptedException {
    	Rdv Rdv = bm.findOrCreateRdv(port);
    	
    	return Rdv.connect(this);
    }
    
    public String getName() {
    	return this.name;
    }

}