package taskevents;

import abs.AbstractEventMessageQueue.Listener;
import events.EventMessageQueue;
import events.TaskEvent;

public class CloseTaskEvent extends TaskEvent {

	EventMessageQueue mq;
	Listener listener;
	
	public CloseTaskEvent(EventMessageQueue mq, Listener listener) {
		this.mq = mq;
	}
	
	@Override
	public void run() {
		mq.channel.disconnect();
		listener.closed();
		this.kill();
	}

}
