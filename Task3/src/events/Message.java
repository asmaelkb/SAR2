package events;

public class Message {
	byte[] bytes;
	int offset;
	int length;
	
	public Message(String msg) {
		bytes = msg.getBytes();
		offset = 0;
		length = msg.length();
	}
	
	public Message(byte[] bytes, int offset, int length) {
		this.bytes = bytes;
		this.offset = offset;
		this.length = length;
	}
	

}
