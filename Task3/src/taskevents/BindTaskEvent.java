package taskevents;

import abs.AbstractEventQueueBroker.AcceptListener;
import events.EventQueueBroker;
import events.TaskEvent;

public class BindTaskEvent extends TaskEvent {
	
	private EventQueueBroker broker;
	private int port;
	private AcceptListener listener;
	
	public BindTaskEvent(EventQueueBroker broker, int port, AcceptListener listener) {
		this.broker = broker;
		this.port = port;
		this.listener = listener;
	}

	@Override
	public void run() {
		if(broker.bind(port, listener)) {
			this.kill();
		}
		
	}

}
