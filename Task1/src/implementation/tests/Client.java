package tests;

import implementation.*;

public class Client {
	
	public void testOneClient() throws InterruptedException {
		BrokerManager brokerManager = new BrokerManager();
		Broker serverBroker = new Broker("Server", brokerManager);
		
		Server server = new Server(serverBroker);
		server.startServer(8080);
		
		Broker clientBroker = new Broker("Client", brokerManager);
		AbstractChannel channel = clientBroker.connect("Server", 8080);
		
		byte[] msg = "Test".getBytes();
		byte[] resp = new byte[256];
		
		channel.write(msg, 0, msg.length);
		
		int bytesRead = channel.read(resp, 0, resp.length);
		
		String msgReceived = new String(resp, 0, bytesRead);
		
		if (!"Test".equals(msgReceived)) {
	        throw new RuntimeException("Test échoué : attendu 'Test', mais reçu '" + msgReceived + "'");
	    }
	    
	    System.out.println("Test réussi : Message reçu = " + msgReceived);
	    
	    channel.disconnect();
	}
	
	public void testMultipleClient() throws InterruptedException {
		BrokerManager brokerManager = new BrokerManager();
		Broker serverBroker = new Broker("Server", brokerManager);
		
		Server server = new Server(serverBroker);
		server.startServer(8080);
		
		Broker client1 = new Broker("Client1", brokerManager);
		Broker client2 = new Broker("Client2", brokerManager);
		
		AbstractChannel channel1 = client1.connect("Server", 8080);
		AbstractChannel channel2 = client2.connect("Server", 8080);
		
		byte[] msg1 = "Test client1".getBytes();
		byte[] msg2 = "Test client2".getBytes();
		byte[] resp1 = new byte[256];
		byte[] resp2 = new byte[256];
		
		channel1.write(msg1, 0, msg1.length);
		channel2.write(msg2, 0, msg2.length);
		
		int bytesRead1 = channel1.read(resp1, 0, resp1.length);
		int bytesRead2 = channel2.read(resp2, 0, resp2.length);
		
		String msgReceived1 = new String(resp1, 0, bytesRead1);
		String msgReceived2 = new String(resp2, 0, bytesRead2);
		
		if (!"Test client1".equals(msgReceived1)) {
	        throw new RuntimeException("Test échoué : attendu 'Test client1', mais reçu '" + msgReceived1 + "'");
	    }
	    
	    if (!"Test client2".equals(msgReceived2)) {
	        throw new RuntimeException("Test échoué : attendu 'Test client2', mais reçu '" + msgReceived2 + "'");
	    }
	    
	    System.out.println("Test réussi pour Client1 : Message reçu = " + msgReceived1);
	    System.out.println("Test réussi pour Client2 : Message reçu = " + msgReceived2);
	    
	    channel1.disconnect();
	    channel2.disconnect();
	}

}