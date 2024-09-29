package tests;
import implementation.*;

public class Client {
	
	Broker clientBroker;
	
	public Client(String name) {
		this.clientBroker = new Broker(name);
	}
	
	public void connectClient(String name, int port) {
		Runnable clientRunnable = () -> {
	        try {
	            AbstractChannel channel = clientBroker.connect(name, port);
	            
	            String m = "Test client " + String.valueOf(Thread.currentThread().getId());
	            byte[] msg = m.getBytes();
	            byte[] resp = new byte[256];
	            
	            try {
					channel.write(msg, 0, msg.length);
				} catch (DisconnectedException e) {
					e.printStackTrace();
				}
	            
	            try {
					channel.read(resp, 0, msg.length);
				} catch (DisconnectedException e) {
					e.printStackTrace();
				}
	            
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    };

	    Task clientTask = new Task(clientBroker, clientRunnable);
	    clientTask.start();
		
	}

}