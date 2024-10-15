package events;

import java.util.HashMap;
import java.util.Map;

import abs.AbstractEventQueueBroker;
import implementation.Broker;
import implementation.DisconnectedException;
import implementation.MessageQueue;
import implementation.Task;
import taskevents.BindTaskEvent;
import taskevents.ConnectTaskEvent;
import taskevents.UnbindTaskEvent;
import implementation.CircularBuffer;
import implementation.Channel;

public class EventQueueBroker extends AbstractEventQueueBroker {

    public static Map<Integer, AcceptListener> listeners = new HashMap<>();
    public Broker broker;
    

    public EventQueueBroker(String name) {
        super(name);
    }
    
    public String getName() {
    	return broker.getName();
    }
    
    public void setBroker(Broker broker) {
    	this.broker = broker;
    }

    public Broker getBroker() {
        return broker;
    }
    
    @Override
    public boolean bind(int port, AcceptListener listener) {
    	BindTaskEvent bindTask = new BindTaskEvent(this, port, listener);
    	Executor.getSelf().post(bindTask);
    	return true;
    }
    
    public void _bind(int port, AcceptListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				do {
					
					listeners.put(port, listener);
					Channel channelAccept;
					try {
						channelAccept = (Channel) broker.accept(port);
						MessageQueue mq = new MessageQueue(channelAccept);
						EventMessageQueue emq = new EventMessageQueue(mq);
						listener.accepted(emq);
					} catch (IllegalStateException | InterruptedException e) {
						e.printStackTrace();
					}
					

					
				} while(listeners.get(port) != null);
				
			}
		}).start();
	}
    
    @Override
    public boolean unbind(int port) {
    	UnbindTaskEvent unbindTask = new UnbindTaskEvent(this, port);
    	Executor.getSelf().post(unbindTask);
    	return true;
    }
    
    public void _unbind(int port) {
    	listeners.remove(port);
    }
    
    @Override
    public boolean connect(String name, int port, ConnectListener listener) {
    	ConnectTaskEvent connectTask = new ConnectTaskEvent(this, name, port, listener);
    	Executor.getSelf().post(connectTask);
    	return true;
    }
    
    public void _connect(String name, int port, ConnectListener listener) throws InterruptedException {
    	Channel channelConnect = (Channel) broker.connect(name, port);
		
		if (channelConnect == null) {
			listener.refused();
		}
		else {
			MessageQueue mq = new MessageQueue(channelConnect);
			EventMessageQueue emq = new EventMessageQueue(mq);
			listener.connected(emq);
    }
		
		
    }	
		
}