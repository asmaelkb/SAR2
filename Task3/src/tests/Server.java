package tests;

import events.EventQueueBroker;
import implementation.*;

public class Server {

    EventQueueBroker queueBroker;

    public Server(EventQueueBroker qb) {
        this.queueBroker = qb;
    }

    public void startServer(int port) throws InterruptedException {
        Runnable serverRunnable = () -> {
            try {
                System.out.println("Server started, waiting for connections...");

                while (true) {
                    MessageQueue clientQueue = queueBroker.accept(port);
                    System.out.println("New client connected");

                    // Pour chaque connexion de client, lancer une nouvelle task pour le gérer
                    new Task(queueBroker, () -> handleClient(clientQueue)).start();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Task(queueBroker, serverRunnable).start();
    }


    void handleClient(MessageQueue mq) {
    	try {
            while (!mq.closed()) {
                byte[] receivedMessage = mq.receive();
                mq.send(receivedMessage, 0, receivedMessage.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mq.close();
                System.out.println("Client disconnected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}