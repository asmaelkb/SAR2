package implementation;

public class QueueBroker {
	
	Broker b;
	
	public QueueBroker(Broker broker) {
		this.b = broker;
	}
	
	public String name() {
		return b.getName();
	}
	
	public MessageQueue accept(int port) throws IllegalStateException, InterruptedException {
		Channel channel = (Channel)(b.accept(port));
		return new MessageQueue(channel);
		
	}
	public MessageQueue connect(String name, int port) throws InterruptedException {
		Channel channel = (Channel)(b.connect(name, port));
		return new MessageQueue(channel);
	
	}

}
