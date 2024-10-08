package implementation;

import events.EventQueueBroker;

public class Task extends AbstractTask {

	
	public Task(Broker b, Runnable r) {
		super(b, r);
	}
	
	public Task(EventQueueBroker b, Runnable r) {
		super(b, r);
	}
	
	public void run() {
		r.run();
	}

	public static Broker getBroker() {
		AbstractTask t = (AbstractTask) currentThread();
		return t.b;
	}
	
	public static EventQueueBroker getQueueBroker() {
		AbstractTask t = (AbstractTask) currentThread();
		return (EventQueueBroker)t.eventQueueB;
	}
	
	public static Task getTask() {
		Task t = (Task) currentThread();
		return t;
	}
	
	
}