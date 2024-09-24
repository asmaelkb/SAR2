package implementation;

public abstract class AbstractBroker {

	String name;
	BrokerManager bm;

	AbstractBroker(String name, BrokerManager bm){
		this.name = name;
		this.bm = bm;
	}
	
	public abstract Channel accept(int port);
	
	public abstract Channel connect(String name, int port);
}
