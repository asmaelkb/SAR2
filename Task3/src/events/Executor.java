package events;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Executor extends Thread {
	
	List<Runnable> pumpEvent;
	private static Runnable currentRunnable;
	
	
	private static Executor instance;
	
    static {
        try {
            instance = new Executor();
        } catch (Exception e) {
            throw new RuntimeException("Échec de l'initialisation de Executor.");
        }
    }
	
	public Executor() {
		pumpEvent = new LinkedList<Runnable>();
	}
	
	
	public synchronized void run() {
		while(true) {
			currentRunnable = pumpEvent.remove(0);
			while (currentRunnable!=null) {
				currentRunnable.run();
				currentRunnable = pumpEvent.remove(0);
			}
			sleep();
		}
	}
	
	public synchronized void post(Runnable r) {
		pumpEvent.add(r);
		notify();
	}
	
	public TaskEvent getRunnable() {
		return (TaskEvent)currentRunnable;
	}
	
	private void sleep() {
		try {
			wait();
		}catch(InterruptedException ex) {
			// Nothing
		}
	}
	
	public static Executor getSelf() {
		return instance;
	}
}
