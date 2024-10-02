package implementation;

import java.nio.ByteBuffer;

public class MessageQueue {
	
	Channel channel;
	boolean isClosed;

	public MessageQueue(Channel current) {
		this.channel = current;
		this.isClosed = false;
	}
	
	public void send(byte[] bytes, int offset, int length) throws DisconnectedException {
		byte[] msgLength = ByteBuffer.allocate(4).putInt(length).array();
	    channel.write(msgLength, 0, msgLength.length); 
	    channel.write(bytes, offset, length);
	}

	public byte[] receive() throws DisconnectedException {
		byte[] msgLength = new byte[4];
	    channel.read(msgLength, 0, 4);
	    int length = ByteBuffer.wrap(msgLength).getInt();

	    byte[] msg = new byte[length];
	    channel.read(msg, 0, length);

	    return msg;
	}

	
	void close() {
		channel.disconnect();
		isClosed = true;
	}
	
	boolean closed() {
		return isClosed;
	}

}
