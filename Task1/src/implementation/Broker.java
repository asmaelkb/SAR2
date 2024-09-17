package implementation;

public abstract class Broker {

	Broker(String name){
		
	}
	
	public abstract Channel accept(int port);
	
	public abstract Channel connect(String name, int port);
}
