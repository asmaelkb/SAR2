package tests;

import implementation.*;

public class Server {
	
	Broker broker;
	public AbstractChannel channel;
	
	public Server(Broker b) {
		this.broker = b;
	}
	
	public void startServer(int port) throws InterruptedException {
	    Runnable serverRunnable = () -> {
	        try {
	            System.out.println("Server started, waiting for connections...");
	            
	            while (true) {
	                this.channel = broker.accept(port);
	                
	                byte[] resp = new byte[256];
	                
	                int bytesRead = channel.read(resp, 0, resp.length);
	                int bytesWritten = channel.write(resp, 0, bytesRead);
	                
	            }
	            
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (DisconnectedException e) {
				e.printStackTrace();
			}
	    };

	    Task serverTask = new Task(broker, serverRunnable);
	    serverTask.start();
	}

	
	void handleClient(AbstractChannel channel) {
		byte[] buffer = new byte[256];
		int bytesRead;
		
		try {
			while((bytesRead = channel.read(buffer, 0, buffer.length)) > 0) {
				channel.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}