package tests;

import implementation.*;

public class Server {
    
    Broker broker;
    
    public Server(Broker b) {
        this.broker = b;
    }
    
    public void startServer(int port) throws InterruptedException {
        new Thread(() -> {
            while(true) {
			    AbstractChannel channel = broker.accept(port);
			    new Thread(() -> handleClient(channel)).start();
			}
        }).start();
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