package implementation;

public abstract class AbstractTask extends Thread {

	Broker b;
	Runnable r;
	
	public AbstractTask(Broker b, Runnable r) {
		this.b = b;
		this.r = r;
	}
	
}
