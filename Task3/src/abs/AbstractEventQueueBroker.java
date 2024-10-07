package abs;

import events.EventMessageQueue;

public abstract class AbstractEventQueueBroker {

	public AbstractEventQueueBroker(String name) {
		
	}
	
	public interface AcceptListener {
		void accepted(EventMessageQueue queue);
	}
	
	public abstract boolean bind(int port, AcceptListener listener);
	public abstract boolean unbind(int port);
	
	public interface ConnectListener{
		void connected(EventMessageQueue queue);
		void refused();
	}
	
	public abstract boolean connect(String name, int port, ConnectListener listener);
}