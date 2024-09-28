package implementation;

public abstract class AbstractBroker {

	String name;
	BrokerManager bm;

	AbstractBroker(String name, BrokerManager bm){
		this.name = name;
		this.bm = bm;
	}
	
	public abstract AbstractChannel accept(int port) throws InterruptedException;
	public abstract AbstractChannel connect(String name, int port) throws InterruptedException;
}
