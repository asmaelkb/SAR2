package implementation;

public class Rdv {
	
	Broker acceptBroker, connectBroker;
	int port;
	boolean isConnected;
	
	Channel acceptChannel;
	Channel connectChannel;
	
	
	public Rdv(int port) {
		this.port = port;
		this.isConnected = false;
	}
	
	// Cette méthode est appelée par le broker qui veut se connecter
	public synchronized Channel connect(Broker b) throws InterruptedException {
		System.out.println(b.getName() + " is trying to connect on port: " + port);
		this.connectBroker = b;
		
		notifyAll();
		
		while(acceptBroker == null) {
			wait(); // On attend qu'un broker accept existe sur ce port
		}
		
		while(!isConnected) {
			wait();
		}
		
		System.out.println("Connection established between " + acceptBroker.getName() + " and " + connectBroker.getName());
		return connectChannel;
	}
	
	// Cette méthode est appelée par le broker qui veut accepter la connexion
	public synchronized Channel accept(Broker b) throws InterruptedException, IllegalStateException {
		if(acceptBroker != null) {
			throw new IllegalStateException("Only one broker can accept on this port");
		}
		
		System.out.println(b.getName() + " is accepting on port: " + port);
		this.acceptBroker = b;
		
		notifyAll();
		
		while(connectBroker == null) {
			wait(); // on attend une demande de connexion
		}
		
		System.out.println("Connection request received from: " + connectBroker.getName());
		
		CircularBuffer in = new CircularBuffer(256); 	
		CircularBuffer out = new CircularBuffer(256); 	
		
		this.connectChannel = new Channel(in, out);
		this.acceptChannel = new Channel(out, in);
		
		this.isConnected = true;
		notifyAll(); // on réveille le broker qui attend les connexions
		
		return acceptChannel;
	}

}