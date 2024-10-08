package events;

import java.util.HashMap;
import java.util.Map;

import abs.AbstractEventQueueBroker;
import implementation.BrokerManager;
import implementation.Channel;
import implementation.CircularBuffer;
import taskevents.*;

public class EventQueueBroker extends AbstractEventQueueBroker {

    private Map<Integer, AcceptListener> listeners = new HashMap<>();
    private BrokerManager bm;
    
    public EventQueueBroker(String name) {
        super(name);
        bm = BrokerManager.getSelf();
    }

    @Override
	public boolean unbind(int port) {
		TaskEvent task = new UnbindTaskEvent(this, port);
		return true;
	}
	
	public boolean _unbind(int port) {
			listeners.remove(port);
			return true;
	}

	@Override
	public boolean bind(int port, AcceptListener listener) {
		TaskEvent task = new  BindTaskEvent(this, port, listener);
		return true;
	}
	
	public boolean _bind(int port, AcceptListener listener) {
			if(listeners.containsKey(port)) {
				return false;
			}
			listeners.put(port, listener);
			return true;
	}

	@Override
	public boolean connect(String name, int port, ConnectListener listener) {
		ConnectTaskEvent task = new ConnectTaskEvent(this, name, port, listener);
		return true;
	}
	
	public boolean _connect(int port, ConnectListener listener) {
			if(listeners.containsKey(port)) {
				
				CircularBuffer in = new CircularBuffer(256);
				CircularBuffer out = new CircularBuffer(256);
				
				Channel channel1 = new Channel(this, port, in, out);
				Channel channel2 = new Channel(this, port, out, in);

				channel1.rch = channel2;
				channel2.rch = channel1;
				
				EventMessageQueue queue1 = new EventMessageQueue(channel1);
				EventMessageQueue queue2 = new EventMessageQueue(channel2);
				
				queue1.remoteMq = queue2;
				queue2.remoteMq = queue1;
				
				listener.connected(queue1);
				listeners.get(port).accepted(queue2);
								
				return true;
			}
			return false;
		}
}