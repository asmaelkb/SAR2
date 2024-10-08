package taskevents;

import events.EventMessageQueue;
import events.Message;
import events.TaskEvent;
import implementation.DisconnectedException;

public class SendTaskEvent extends TaskEvent {
	
	EventMessageQueue mq;
	Message msg;
	
	public SendTaskEvent(EventMessageQueue mq, Message msg) {
		this.mq = mq;
		this.msg = msg;
	}

	@Override
	public void run() {
		try {
			if (mq.send(msg)) {
				this.kill();
			}
		} catch (DisconnectedException e) {
			e.printStackTrace();
		}
		
	}

}
