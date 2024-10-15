package abs;

import events.EventQueueBroker;
import implementation.Broker;

public abstract class AbstractTask extends Thread {

	public EventQueueBroker eventQueueB;
	public Broker b;
	protected Runnable r;
	
	public AbstractTask(Broker b, Runnable r) {
		this.b = b;
		this.r = r;
	}
	
	public AbstractTask(EventQueueBroker b, Runnable r) {
		this.eventQueueB = b;
		this.r = r;
	}
	
}