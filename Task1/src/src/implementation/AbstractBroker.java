package implementation;

public abstract class AbstractBroker {

	String name;
	
	AbstractBroker(String name){
		this.name = name;
	}
	
	public abstract AbstractChannel accept(int port) throws InterruptedException;
	public abstract AbstractChannel connect(String name, int port) throws InterruptedException;
}
