package taskevents;

import abs.AbstractEventQueueBroker.ConnectListener;
import events.EventQueueBroker;
import events.TaskEvent;

public class ConnectTaskEvent extends TaskEvent {

	EventQueueBroker broker;
	String name;
	int port;
	ConnectListener listener;
	
	public ConnectTaskEvent(EventQueueBroker broker, String name, int port, ConnectListener listener) {
		this.broker = broker;
		this.name = name;
		this.port = port;
		this.listener = listener;
	}
	
	@Override
	public void run() {
		try {
			broker._connect(name, port, listener);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.kill();
		
	}

}
