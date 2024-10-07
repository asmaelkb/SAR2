package events;

import java.util.LinkedList;
import java.util.List;

public class Executor extends Thread {
	
	List<Runnable> pumpEvent;
	
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
		Runnable r;
		while(true) {
			r = pumpEvent.remove(0);
			while (r!=null) {
				r.run();
				r = pumpEvent.remove(0);
			}
			sleep();
		}
	}
	
	public synchronized void post(Runnable r) {
		pumpEvent.add(r);
		notify();
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
