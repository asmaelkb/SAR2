package implementation;

import java.util.ArrayList;

public class Broker extends AbstractBroker {

	ArrayList<Rdv> rdvs;

	public Broker(String name, BrokerManager bm) {
		super(name, bm);
		this.bm.addBroker(this);

		this.rdvs = new ArrayList<Rdv>();
	}

	public String getName(){
		return super.name;
	}


	@Override
	public synchronized
	Channel accept(int port) {
		Rdv rdv = new Rdv(this, port);
		
		if(rdv.b_accept != null) {
			throw new IllegalStateException("Another broker is already accepting on the same port.");
		}
		
		return rdv.accept(this);
		
	}

	@Override
	public synchronized
	Channel connect(String name, int port) {
		Rdv rdv = findOrCreateRdv(port);
		
		return rdv.connect(this);
	}
	
	public synchronized Rdv findOrCreateRdv(int port) {
		for(Rdv rdv : rdvs) {
			if (rdv.port == port) {
				return rdv;
			}
		}
		
		Rdv newRdv = new Rdv(this, port);
		rdvs.add(newRdv);
		return newRdv;
	}

}
