package tests;

import events.EventQueueBroker;
import implementation.*;

public class InitialTests {

    public void testOneClient() throws InterruptedException, DisconnectedException {
    	
    	System.out.println("\n Test One Client \n");

        EventQueueBroker serverEventQueueBroker = new EventQueueBroker("brokerServer");
        Server server = new Server(serverEventQueueBroker);
        server.startServer(8080);

        EventQueueBroker clientEventQueueBroker = new EventQueueBroker("Client");

        EventMessageQueue clientQueue = clientEventQueueBroker.connect("Server", 8080);

        byte[] msg = "Test".getBytes();
        clientQueue.send(msg, 0, msg.length);

        byte[] resp = clientQueue.receive();

        String msgReceived = new String(resp);

        if (!"Test".equals(msgReceived)) {
            throw new RuntimeException("Test échoué : attendu 'Test', mais reçu '" + msgReceived + "'");
        }

        System.out.println("Test réussi : Message reçu = " + msgReceived);

        clientQueue.close();
    }
    
//    public void testMultipleClients() throws InterruptedException, DisconnectedException {
//    	
//    	System.out.println("\n Test Multiple Clients \n");
//    	
//        Broker serverBroker = new Broker("Server");
//        EventQueueBroker serverEventQueueBroker = new EventQueueBroker(serverBroker);
//        Server server = new Server(serverEventQueueBroker);
//        server.startServer(8080);
//        
//        Broker client1Broker = new Broker("Client1");
//        EventQueueBroker client1EventQueueBroker = new EventQueueBroker(client1Broker);
//        MessageQueue client1Queue = client1EventQueueBroker.connect("Server", 8080);
//       
//        
//        Broker client2Broker = new Broker("Client2");
//        EventQueueBroker client2EventQueueBroker = new EventQueueBroker(client2Broker);
//        MessageQueue client2Queue = client2EventQueueBroker.connect("Server", 8080);
//        
//        byte[] msg1 = "Test client1".getBytes();
//        byte[] msg2 = "Test client2".getBytes();
//        
//        client1Queue.send(msg1, 0, msg1.length);
//        client2Queue.send(msg2, 0, msg2.length);
//        
//        byte[] resp1 = client1Queue.receive();
//        byte[] resp2 = client2Queue.receive();
//        
//        String msgReceived1 = new String(resp1);
//        String msgReceived2 = new String(resp2);
//        
//        if (!"Test client1".equals(msgReceived1)) {
//            throw new RuntimeException("Test échoué : attendu 'Test client1', mais reçu '" + msgReceived1 + "'");
//        }
//        
//        if (!"Test client2".equals(msgReceived2)) {
//            throw new RuntimeException("Test échoué : attendu 'Test client2', mais reçu '" + msgReceived2 + "'");
//        }
//        
//        System.out.println("Test réussi pour Client1 : Message reçu = " + msgReceived1);
//        System.out.println("Test réussi pour Client2 : Message reçu = " + msgReceived2);
//        
//        client1Queue.close();
//        client2Queue.close();
//    }
}