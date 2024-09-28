package implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrokerManager {
	
	ArrayList<Broker> allBrokers;
	Map<Integer, Rdv> Rdvs;
	
	
	public BrokerManager() {	
		allBrokers = new ArrayList<Broker>();
		Rdvs = new HashMap<>();
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
	
	 public synchronized Rdv findOrCreateRdv(int port) {
		 
		 Rdv Rdv = Rdvs.get(port);
		 
		 if (Rdv == null) {
			 Rdv = new Rdv(port); 
			 Rdvs.put(port, Rdv);
		 }
		 
		 return Rdv;
    }

}