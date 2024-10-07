package events;

import java.util.HashMap;
import java.util.Map;

import abs.AbstractEventQueueBroker;

public class EventQueueBroker extends AbstractEventQueueBroker {

    private String name;
    private Map<Integer, AcceptListener> listeners = new HashMap<>();

    public EventQueueBroker(String name) {
        super(name);
    }

    public interface AcceptListener {
        void accepted(EventMessageQueue queue);
    }

    public boolean unbind(int port) {
        if (listeners.remove(port) != null) {
            System.out.println("Port " + port + " unbound successfully.");
            return true;
        }
        System.out.println("Port " + port + " is not bound.");
        return false;
    }

    public interface ConnectListener {
        void connected(EventMessageQueue queue);
        void refused();
    }


	@Override
	public boolean bind(int port, abs.AbstractEventQueueBroker.AcceptListener listener) {
		if (listeners.containsKey(port)) {
            System.out.println("Port " + port + " is already bound.");
            return false;
        }
        listeners.put(port, (AcceptListener) listener);
        System.out.println("Port " + port + " bound successfully.");
        return true;
	}

	@Override
	public boolean connect(String name, int port, abs.AbstractEventQueueBroker.ConnectListener listener) {
		AcceptListener acceptListener = listeners.get(port);
        if (acceptListener != null) {
            EventMessageQueue messageQueue = new EventMessageQueue(name);
            acceptListener.accepted(messageQueue);
            listener.connected(messageQueue);
            System.out.println("Connection to " + name + " on port " + port + " was successful.");
            return true;
        } else {
            listener.refused();
            System.out.println("Connection to " + name + " on port " + port + " was refused.");
            return false;
        }
	}
}