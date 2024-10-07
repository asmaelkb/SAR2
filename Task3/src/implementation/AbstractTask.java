package implementation;

import events.EventQueueBroker;

public abstract class AbstractTask extends Thread {

	EventQueueBroker eventQueueB;
	QueueBroker queueB;
	Broker b;
	Runnable r;
	
	public AbstractTask(Broker b, Runnable r) {
		this.b = b;
		this.r = r;
	}
	
	public AbstractTask(QueueBroker b, Runnable r) {
		this.queueB = b;
		this.r = r;
	}
	
	public AbstractTask(EventQueueBroker b, Runnable r) {
		this.eventQueueB = b;
		this.r = r;
	}
	
}