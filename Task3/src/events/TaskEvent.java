package events;

public abstract class TaskEvent implements Runnable {
	
	boolean isKilled;
	
	public TaskEvent() {
		isKilled = false;
	}

	public void post(Runnable r) {
		if (!isKilled) {
			Executor.getSelf().post(r);
		}
	}
	public static TaskEvent task() {
		return Executor.getSelf().getRunnable();
	}
	
	public void kill() {
		isKilled = true;
	}
	
	boolean killed() {
		return this.isKilled;
	}

}
