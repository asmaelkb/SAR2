package implementation;

public abstract class AbstractTask extends Thread {

	QueueBroker queueB;
	Broker b;
	Runnable r;
	
	public AbstractTask(Broker b, Runnable r) {
		this.b = b;
		this.r = r;
	}
	
	public AbstractTask(QueueBroker b, Runnable r) {
		this.queueB = b;
		this.r = r;
	}
	
}