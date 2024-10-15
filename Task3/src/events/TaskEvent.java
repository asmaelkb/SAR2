package events;

public abstract class TaskEvent implements Runnable {
	
	boolean isKilled;
	
	public TaskEvent() {
		isKilled = false;
	}

	public void react() {
		this.run();
	}
	
	public void kill() {
		Executor.getSelf().pumpEvent.remove(this);
		isKilled = true;
	}
	
	boolean killed() {
		return this.isKilled;
	}

}
