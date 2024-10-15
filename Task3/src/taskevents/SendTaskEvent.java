package taskevents;

import abs.AbstractEventMessageQueue.Listener;
import events.EventMessageQueue;
import events.Message;
import events.TaskEvent;
import implementation.DisconnectedException;

public class SendTaskEvent extends TaskEvent {
	
	EventMessageQueue mq;
	Message msg;
	Listener listener;
	
	public SendTaskEvent(EventMessageQueue mq, Message msg, Listener listener) {
		this.mq = mq;
		this.msg = msg;
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			mq._send(msg);
		} catch (DisconnectedException e) {
			e.printStackTrace();
		}
		
		listener.sent(msg);
        this.kill();
		
	}

}
