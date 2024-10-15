package events;

import java.util.ArrayList;

public class QueueBrokerManager {
	
	public ArrayList<EventQueueBroker> allBrokers;
	private static QueueBrokerManager instance;
	
    static {
        try {
            instance = new QueueBrokerManager();
        } catch (Exception e) {
            throw new RuntimeException("Échec de l'initialisation de BrokerManager.");
        }
    }
	
	public QueueBrokerManager() {	
		allBrokers = new ArrayList<EventQueueBroker>();
	}
	
	public static QueueBrokerManager getSelf() {
		return instance;
	}
	
	public synchronized void addBroker(EventQueueBroker b) {
		allBrokers.add(b);
	}
	
	public synchronized  void removeBroker(EventQueueBroker b) {
		allBrokers.remove(b);
	}
	
	public EventQueueBroker getBroker(String name) {
		for (EventQueueBroker broker : allBrokers) {
			if(broker.getName().equals(name)) {
				return broker;
			}
		}
		return null;
	}
	


}