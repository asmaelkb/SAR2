package implementation;

public abstract class AbstractTask extends Thread {

	static Broker b;
	Runnable r;
	
	public AbstractTask(Broker b, Runnable r) {
		AbstractTask.b = b;
		this.r = r;
	}

	public static Broker getBroker() {
		return b;
	}
}
