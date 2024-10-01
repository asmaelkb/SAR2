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
		byte[] lengthMsg = ByteBuffer.allocate(4).putInt(length).array();
		channel.write(lengthMsg, offset, 4);
		offset += 4;
		channel.write(bytes, offset, length);
	}
	
	public byte[] receive() throws DisconnectedException {
		int offset = channel.out.m_head;
		
		byte[] lengthMsg = new byte[4];
		channel.read(lengthMsg, offset, 4);
		offset += 4;
		
		int length = ByteBuffer.wrap(lengthMsg).getInt();
		byte[] msg = new byte[length];
		channel.read(lengthMsg, offset, length);
		
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
