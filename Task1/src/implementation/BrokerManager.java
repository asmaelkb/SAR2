package implementation;

import java.util.ArrayList;

public class BrokerManager {

    ArrayList<Broker> allBrokers;

    public BrokerManager(){
        this.allBrokers = new ArrayList<Broker>();
    }

    public void addBroker(Broker b){
        allBrokers.add(b);
    }

    public void removeBroker(Broker b){
        allBrokers.remove(b);
    }

    public Broker getBroker(String name){

        for (Broker b : allBrokers){
            String bn = b.getName();

            if bn.equals(name){
                return b;
            }
        }
        
        return null;

    }
}