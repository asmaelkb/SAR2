package implementation;

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
		
		
	}

	@Override
	public synchronized
	Channel connect(String name, int port) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
