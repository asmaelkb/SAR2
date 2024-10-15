package taskevents;

import events.EventQueueBroker;
import events.TaskEvent;

public class UnbindTaskEvent extends TaskEvent {

	EventQueueBroker broker;
	int port;
	
	public UnbindTaskEvent(EventQueueBroker broker, int port) {
		this.broker = broker;
		this.port = port;
	}
	
	@Override
	public void run() {
		broker._unbind(port);
		this.kill();
		
	}

}
