package tests;

import implementation.*;

public class Client {

	QueueBroker queueBroker;

	public Client(String name) {
		this.queueBroker = new QueueBroker(new Broker(name));
	}

	public void connectClient(String name, int port) {
		Runnable clientRunnable = () -> {
	        try {
	            MessageQueue queue = queueBroker.connect(name, port);

	            String m = "Test client " + String.valueOf(Thread.currentThread().getId());
	            byte[] msg = m.getBytes();
	            queue.send(msg, 0, msg.length);

	            byte[] resp = queue.receive();
	            String respMessage = new String(resp);

	            System.out.println("Client received: " + respMessage);

	            if (queue != null && !queue.closed()) {
				    try {
				        queue.close();
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
				}

	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (DisconnectedException e1) {
				e1.printStackTrace();
			}
	    };

	    new Task(queueBroker, clientRunnable).start();

	}

}