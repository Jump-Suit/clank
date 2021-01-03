package net.hashsploit.clank.server.common.packets.serializers;

import java.nio.ByteBuffer;

import net.hashsploit.clank.server.common.MediusConstants;
import net.hashsploit.clank.server.common.MediusMessageType;
import net.hashsploit.clank.server.common.objects.MediusMessage;

public class SessionEndRequest extends MediusMessage {

	private byte[] messageId = new byte[MediusConstants.MESSAGEID_MAXLEN.value];
	private byte[] sessionKey = new byte[MediusConstants.SESSIONKEY_MAXLEN.value];
	
	public SessionEndRequest(byte[] data) {
		super(MediusMessageType.SessionEnd, data);
		
		ByteBuffer buf = ByteBuffer.wrap(data);
		buf.get(messageId);
		buf.get(sessionKey);
	}
	
	public byte[] getMessageId() {
		return messageId;
	}

	public byte[] getSessionKey() {
		return sessionKey;
	}
	
	
}
