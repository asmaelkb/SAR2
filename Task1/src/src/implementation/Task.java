package implementation;

public class Task extends AbstractTask {

	
	public Task(Broker b, Runnable r) {
		super(b, r);
		
	}
	
	public void run() {
		r.run();
	}

	public static Broker getBroker() {
		AbstractTask t = (AbstractTask) currentThread();
		return t.b;
	}
}
