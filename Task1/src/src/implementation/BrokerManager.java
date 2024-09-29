package implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrokerManager {
	
	public ArrayList<Broker> allBrokers;
	private static BrokerManager instance;
	
    static {
        try {
            instance = new BrokerManager();
        } catch (Exception e) {
            throw new RuntimeException("Échec de l'initialisation de BrokerManager.");
        }
    }
	
	public BrokerManager() {	
		allBrokers = new ArrayList<Broker>();
	}
	
	public static BrokerManager getSelf() {
		return instance;
	}
	
	public synchronized void addBrokers(Broker b) {
		allBrokers.add(b);
	}
	
	public synchronized  void removeBroker(Broker b) {
		allBrokers.remove(b);
	}
	
	public Broker getBroker(String name) {
		for (Broker broker : allBrokers) {
			if(broker.getName().equals(name)) {
				return broker;
			}
		}
		return null;
	}
	


}