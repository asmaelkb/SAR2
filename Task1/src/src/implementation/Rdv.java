package implementation;

public class Rdv {
	
	Broker acceptBroker, connectBroker;
	Channel acceptChannel, connectChannel;
	boolean isConnected;
	
	
	public Rdv() {
		this.isConnected = false;
	}
	
	private void rwait() {
		while (acceptChannel == null || connectChannel == null) {
			try {
				wait();
			}
			catch(InterruptedException ex) {
				
			}
		}
	}
	
	// Cette méthode est appelée par le broker qui veut se connecter
	public synchronized Channel connect(Broker b, int port) throws InterruptedException {
		this.connectBroker = b;
		
		connectChannel = new Channel(connectBroker, port, new CircularBuffer(512), new CircularBuffer(512));
		if (acceptChannel != null) {
			acceptChannel.connect(connectChannel, connectBroker.getName());
			notify(); // On réveille le thread serveur (accept)
		}
		else {
			rwait();
		}
		
		System.out.println("Connection established between " + acceptBroker.getName() + " and " + connectBroker.getName());
		return connectChannel;
	}
	
	// Cette méthode est appelée par le broker qui veut accepter la connexion
	public synchronized Channel accept(Broker b, int port) throws InterruptedException, IllegalStateException {

		this.acceptBroker = b;
		
		this.acceptChannel = new Channel(acceptBroker, port, new CircularBuffer(512), new CircularBuffer(512));
		if (connectChannel != null) {
			acceptChannel.connect(connectChannel, acceptBroker.getName());
			notify(); // On réveille le thread client (connect)
		}
		else {
			rwait();
		}
		
		return acceptChannel;
	}

}