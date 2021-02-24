package net.hashsploit.clank.server.medius.serializers;

import java.nio.ByteBuffer;

import net.hashsploit.clank.server.medius.MediusConstants;
import net.hashsploit.clank.server.medius.MediusMessageType;
import net.hashsploit.clank.server.medius.objects.MediusMessage;
import net.hashsploit.clank.utils.Utils;

public class CheckMyClanInvitationsRequest extends MediusMessage {

	private byte[] messageId = new byte[MediusConstants.MESSAGEID_MAXLEN.value];
	private byte[] sessionKey = new byte[MediusConstants.SESSIONKEY_MAXLEN.value];
	private byte[] start = new byte[4];
	private byte[] pageSize = new byte[4];
	
	public CheckMyClanInvitationsRequest(byte[] data) {
		super(MediusMessageType.CheckMyClanInvitations, data);
		
		ByteBuffer buf = ByteBuffer.wrap(data);
		buf.get(messageId);
		buf.get(sessionKey);
		buf.get(new byte[2]); // padding
		buf.get(start);
		buf.get(pageSize);
	}
	
	@Override
	public String toString() {
		return "Message Id: " + Utils.bytesToHex(messageId) + "\n"
				+ "Session Key: " + Utils.bytesToHex(sessionKey) + "\n"
				+ "Start: " + Utils.bytesToHex(start) + "\n"
				+ "Page Size: " + Utils.bytesToHex(start);
	}
	
	public byte[] getMessageId() {
		return messageId;
	}

	public void setMessageId(byte[] messageId) {
		this.messageId = messageId;
	}

	public byte[] getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	public void setStart(byte[] start) {
		this.start = start;
	}
	
	public byte[] getStart() {
		return start;
	}
	
	public void setPageSize(byte[] pageSize) {
		this.pageSize = pageSize;
	}
	
	public byte[] getPageSize() {
		return pageSize;
	}
	
}
