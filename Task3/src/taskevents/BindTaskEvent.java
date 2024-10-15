package taskevents;

import abs.AbstractChannel;
import abs.AbstractEventQueueBroker.AcceptListener;
import events.EventQueueBroker;
import events.TaskEvent;
import implementation.Channel;

public class BindTaskEvent extends TaskEvent {
	
	public EventQueueBroker queueBroker;
	public int port;
	public AcceptListener listener;
	
	public BindTaskEvent(EventQueueBroker queueBroker, int port, AcceptListener listener) {
		this.queueBroker = queueBroker;
		this.port = port;
		this.listener = listener;
	}

	@Override
	public void run() {
		queueBroker._bind(port, listener);
		this.kill();
		
	}

}
