package net.hashsploit.clank.server.medius.serializers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.hashsploit.clank.server.medius.MediusCallbackStatus;
import net.hashsploit.clank.server.medius.MediusConstants;
import net.hashsploit.clank.server.medius.MediusMessageType;
import net.hashsploit.clank.server.medius.objects.MediusApplicationType;
import net.hashsploit.clank.server.medius.objects.MediusMessage;
import net.hashsploit.clank.server.medius.objects.MediusWorldStatus;
import net.hashsploit.clank.utils.Utils;

public class FindWorldByNameResponse extends MediusMessage {

	private byte[] messageId;
	private MediusCallbackStatus callbackStatus;
	private int appId;
	private String appName;
	private MediusApplicationType appType;
	private int worldId;
	private String worldName;
	private MediusWorldStatus worldStatus;
	private boolean endOfList;

	public FindWorldByNameResponse(byte[] messageId, MediusCallbackStatus callbackStatus, int appId, String appName, MediusApplicationType appType, int worldId, String worldName, MediusWorldStatus worldStatus, boolean endOfList) {
		super(MediusMessageType.FindWorldByNameResponse);

		this.messageId = messageId;
		this.callbackStatus = callbackStatus;
		this.appId = appId;
		this.appName = appName;
		this.appType = appType;
		this.worldId = worldId;
		this.worldName = worldName;
		this.worldStatus = worldStatus;
		this.endOfList = endOfList;
	}

	@Override
	public byte[] getPayload() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(messageId);
			outputStream.write(Utils.hexStringToByteArray("000000")); // padding
			outputStream.write(Utils.intToBytesLittle(callbackStatus.getValue()));
			outputStream.write(Utils.intToBytesLittle(appId));
			outputStream.write(Utils.buildByteArrayFromString(appName, MediusConstants.APPNAME_MAXLEN.value));
			outputStream.write(Utils.intToBytesLittle(appType.value));
			outputStream.write(Utils.intToBytesLittle(worldId));
			outputStream.write(Utils.buildByteArrayFromString(worldName, MediusConstants.WORLDNAME_MAXLEN.value));
			outputStream.write(Utils.intToBytesLittle(worldStatus.value));
			
			outputStream.write(Utils.intToBytesLittle(endOfList ? 1 : 0));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}
}