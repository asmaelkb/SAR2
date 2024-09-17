package implementation;

public abstract class Task extends Thread {

	Broker b;
	Runnable r;
	
	Task(Broker b, Runnable r){
		this.b = b;
		this.r = r;
		
	}
	
	public static Broker getBroker(Task task) {
		return task.b;
	}
}
