package net.hashsploit.clank.server.medius.serializers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.hashsploit.clank.server.medius.MediusCallbackStatus;
import net.hashsploit.clank.server.medius.MediusConstants;
import net.hashsploit.clank.server.medius.MediusMessageType;
import net.hashsploit.clank.server.medius.objects.MediusBuddyAddType;
import net.hashsploit.clank.server.medius.objects.MediusMessage;
import net.hashsploit.clank.utils.Utils;

public class GetBuddyInvitationsResponse extends MediusMessage {

	private byte[] messageId;
	private MediusCallbackStatus callbackStatus;
	private int accountId;
	private String accountName;
	private MediusBuddyAddType addType;
	private boolean endOfList;

	public GetBuddyInvitationsResponse(byte[] messageId, MediusCallbackStatus callbackStatus, int accountId, String accountName, MediusBuddyAddType addType, boolean endOfList) {
		super(MediusMessageType.GetBuddyInvitationsResponse);

		this.messageId = messageId;
		this.callbackStatus = callbackStatus;
		this.accountId = accountId;
		this.accountName = accountName;
		this.addType = addType;
		this.endOfList = endOfList;
	}

	@Override
	public byte[] getPayload() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(messageId);
			outputStream.write(Utils.hexStringToByteArray("000000")); // padding
			outputStream.write(Utils.intToBytesLittle(callbackStatus.getValue()));
			outputStream.write(Utils.intToBytesLittle(accountId));
			outputStream.write(Utils.buildByteArrayFromString(accountName, MediusConstants.ACCOUNTNAME_MAXLEN.value));
			outputStream.write(Utils.intToBytesLittle(addType.getValue()));
			outputStream.write(Utils.hexStringToByteArray(endOfList ? "01" : "00")); // EndOfList (char) 00 or 01
			outputStream.write(Utils.hexStringToByteArray("000000")); // padding
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}
}