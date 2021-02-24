package net.hashsploit.clank.server.medius.serializers;

import java.nio.ByteBuffer;

import net.hashsploit.clank.server.medius.MediusConstants;
import net.hashsploit.clank.server.medius.MediusMessageType;
import net.hashsploit.clank.server.medius.objects.MediusMessage;
import net.hashsploit.clank.utils.Utils;

public class AccountLoginRequest extends MediusMessage {

	private byte[] messageID = new byte[MediusConstants.MESSAGEID_MAXLEN.value];
	private byte[] sessionKey = new byte[MediusConstants.SESSIONKEY_MAXLEN.value];
	private byte[] usernameBytes = new byte[14];
	private byte[] passwordBytes = new byte[14];
	
	public AccountLoginRequest(byte[] data) {
		super(MediusMessageType.AccountLogin, data);
		
		ByteBuffer buf = ByteBuffer.wrap(data);
		buf.get(messageID);
		buf.get(sessionKey);
		// TODO: Username most likely is 32-bytes (14+18).
		buf.get(usernameBytes);
		buf.get(new byte[18]);
		// TODO: Password is also most likely 32-bytes, probably has 18-bytes trailing.
		buf.get(passwordBytes);
	}
	
	public String toString() {
		return "AccountLoginRequest: \n" + 
				"messageID: " + Utils.bytesToHex(messageID) + '\n' + 
				"sessionKey: " + Utils.bytesToHex(sessionKey) + '\n' + 
				"usernameBytes: " + Utils.bytesToHex(usernameBytes) + '\n' + 
				"passwordBytes: " + Utils.bytesToHex(passwordBytes) + '\n' + 
				"sessionKey: " + Utils.bytesToHex(sessionKey);		
	}
	
	public synchronized byte[] getMessageID() {
		return messageID;
	}

	public synchronized void setMessageID(byte[] messageID) {
		this.messageID = messageID;
	}

	public synchronized byte[] getSessionKey() {
		return sessionKey;
	}

	public synchronized void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}

	public synchronized byte[] getUsernameBytes() {
		return usernameBytes;
	}

	public synchronized void setUsernameBytes(byte[] usernameBytes) {
		this.usernameBytes = usernameBytes;
	}

	public synchronized byte[] getPasswordBytes() {
		return passwordBytes;
	}

	public synchronized void setPasswordBytes(byte[] passwordBytes) {
		this.passwordBytes = passwordBytes;
	}

	
}
