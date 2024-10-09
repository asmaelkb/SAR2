package tests;

import events.EventQueueBroker;
import events.Executor;

public class InitialTests {

    public static void main(String[] args) throws InterruptedException {
        InitialTests test = new InitialTests();
        test.testOneClient();
        test.testMultipleClients();
    }

    public void testOneClient() throws InterruptedException {
        System.out.println("\nTest One Client\n");

        Executor executor = Executor.getSelf();
        executor.start();

        EventQueueBroker serverQueueBroker = new EventQueueBroker("Server");
        Server server = new Server(serverQueueBroker);
        server.startServer(8080);

        EventQueueBroker clientQueueBroker = new EventQueueBroker("Client");
        Client client = new Client("Client", clientQueueBroker);
        client.connectClient(8080);
        
        executor.interrupt();
    }

    public void testMultipleClients() throws InterruptedException {
        System.out.println("\nTest Multiple Clients\n");

        Executor executor = Executor.getSelf();
        executor.start(); 

        EventQueueBroker serverQueueBroker = new EventQueueBroker("Server");
        Server server = new Server(serverQueueBroker);
        server.startServer(8080);

        EventQueueBroker client1QueueBroker = new EventQueueBroker("Client1");
        Client client1 = new Client("Client1", client1QueueBroker);
        client1.connectClient(8080);

        EventQueueBroker client2QueueBroker = new EventQueueBroker("Client2");
        Client client2 = new Client("Client2", client2QueueBroker);
        client2.connectClient(8080);

        executor.interrupt();
    }
}