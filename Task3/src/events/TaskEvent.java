package events;

import implementation.AbstractTask;
import implementation.QueueBroker;

public class TaskEvent extends AbstractTask {
	
	boolean isKilled;
	
	public TaskEvent(QueueBroker b, Runnable r) {
		super(b, r);
	}

	public void post(Runnable r) {
		if (!isKilled) {
			Executor.getSelf().post(r);
		}
	}
	public static TaskEvent task() {
		TaskEvent t = (TaskEvent) currentThread();
		return t;
	}
	
	public void kill() {
		
	}
	
	boolean killed() {
		return this.isKilled;
	}
}
