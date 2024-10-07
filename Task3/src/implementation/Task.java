package implementation;

public class Task extends AbstractTask {

	
	public Task(Broker b, Runnable r) {
		super(b, r);
	}
	
	public Task(QueueBroker b, Runnable r) {
		super(b, r);
	}
	
	public void run() {
		r.run();
	}

	public static Broker getBroker() {
		AbstractTask t = (AbstractTask) currentThread();
		return t.b;
	}
	
	public static QueueBroker getQueueBroker() {
		AbstractTask t = (AbstractTask) currentThread();
		return (QueueBroker)t.queueB;
	}
	
	public static Task getTask() {
		Task t = (Task) currentThread();
		return t;
	}
	
	
}