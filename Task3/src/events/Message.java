package events;

public class Message {
	public byte[] bytes;
	public int offset;
	public int length;
	
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
