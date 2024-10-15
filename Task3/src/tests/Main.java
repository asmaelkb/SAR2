package tests;

import abs.AbstractEventQueueBroker;
import abs.AbstractEventQueueBroker.AcceptListener;
import abs.AbstractEventQueueBroker.ConnectListener;
import events.EventMessageQueue;
import events.EventQueueBroker;
import events.Executor;
import events.Message;
import events.QueueBrokerManager;
import implementation.Broker;
import implementation.BrokerManager;
import implementation.DisconnectedException;

public class Main {

	public final static int messageSize = 256;

	public static void main(String[] args) {

		Runnable serverRunnable = new Runnable() {
			public void run() {
				BrokerManager brokerManagement = BrokerManager.getSelf();
				Broker brokerServer = new Broker("server");
				brokerManagement.addBrokers(brokerServer);

				QueueBrokerManager management = QueueBrokerManager.getSelf();
				EventQueueBroker serverQueueBroker = new EventQueueBroker("server");
				serverQueueBroker.setBroker(brokerServer);
				management.addBroker(serverQueueBroker);
		        

				MyAcceptListener listener = new MyAcceptListener();
				boolean bound = serverQueueBroker.bind(8080, (AcceptListener) listener);
				if (!bound) {
					System.out.println("Server failed to bind");
					return;
				}
			}
		};

		Runnable clientRunnable = new Runnable() {
			public void run() {
				BrokerManager brokerManagement = BrokerManager.getSelf();
				Broker brokerClient = new Broker("client");
				brokerManagement.addBrokers(brokerClient);
				
				QueueBrokerManager management = QueueBrokerManager.getSelf();
				EventQueueBroker clientQueueBroker = new EventQueueBroker("client");
				clientQueueBroker.setBroker(brokerClient);
				management.addBroker(clientQueueBroker);
				
				MyConnectListener listener = new MyConnectListener();
				boolean connected = clientQueueBroker.connect("server", 8080, (ConnectListener)listener);
				if (!connected) {
					System.out.println("Client failed to connect");
					return;
				}
			}
		};

		Executor.getSelf().post(serverRunnable);
		
		try {
            Thread.sleep(1000); // Wait for the server to starts
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		Executor.getSelf().post(clientRunnable);
		Executor.getSelf().run();
	}
}

class MyEchoServerQueueListener implements EventMessageQueue.Listener {

	private EventMessageQueue queue;

	public MyEchoServerQueueListener(EventMessageQueue queue) {
		this.queue = queue;
	}

	@Override
	public void received(Message msg) {
		System.out.println("Server received message");
		try {
			boolean sent = queue.send(msg);
			if (!sent) {
				System.out.println("Server failed to send response");
			}
		} catch (Exception e) {
			System.out.println("Server failed to send message: " + e.getMessage());
		}
	}

	@Override
	public void closed() {
		System.out.println("Server finished");
	}

	@Override
	public void sent(Message message) {
		System.out.println("Server sent response");
	}

}

class MyEchoClientQueueListener implements EventMessageQueue.Listener {

	private EventMessageQueue queue;

	public MyEchoClientQueueListener(EventMessageQueue queue) {
		this.queue = queue;
	}

	@Override
	public void received(Message msg) {
		System.out.println("Client received response");

		// Check if the response is correct
		int messageSize = Main.messageSize;
		byte[] messageContent = new byte[messageSize];
		for (int i = 0; i < messageSize; i++) {
			messageContent[i] = (byte) (i + 1);
		}

		byte[] bytes = msg.bytes;
		
		for (int i = 0; i < messageSize; i++) {
			if (bytes[i] != messageContent[i]) {
				System.out.println("Client received incorrect response");
				return;
			}
		}

		queue.close();

		System.out.println("Test passed");
	}

	@Override
	public void closed() {
		System.out.println("Client finished");
	}

	@Override
	public void sent(Message message) {
		System.out.println("Client sent message");
	}

}

class MyAcceptListener implements AbstractEventQueueBroker.AcceptListener {

	@Override
	public void accepted(EventMessageQueue queue) {
		System.out.println("Server accepted connection");
		MyEchoServerQueueListener listener = new MyEchoServerQueueListener(queue);
		queue.setListener(listener);
	}

}

class MyConnectListener implements AbstractEventQueueBroker.ConnectListener {

	@Override
	public void connected(EventMessageQueue queue) {
		System.out.println("Connection established for client");
		int messageSize = Main.messageSize;
		byte[] messageContent = new byte[messageSize];
		for (int i = 0; i < messageSize; i++) {
			messageContent[i] = (byte) (i + 1);
		}

		MyEchoClientQueueListener listener = new MyEchoClientQueueListener(queue);
		queue.setListener(listener);


		boolean sent;
		try {
			sent = queue.send(messageContent);
			if (!sent) System.out.println("Client failed to send message");
			
		} catch (DisconnectedException e) {
			System.out.println("Client failed to send message");
		}
		
	}

	@Override
	public void refused() {
		System.out.println("Connection refused");
	}
}