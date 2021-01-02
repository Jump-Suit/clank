package net.hashsploit.clank.server.common.packets.serializers;

import java.nio.ByteBuffer;

import net.hashsploit.clank.server.common.MediusConstants;
import net.hashsploit.clank.server.common.MediusMessageType;
import net.hashsploit.clank.server.common.objects.MediusMessage;
import net.hashsploit.clank.utils.Utils;

public class GameList_ExtraInfoZeroRequest extends MediusMessage {

	private byte[] messageId = new byte[MediusConstants.MESSAGEID_MAXLEN.getValue()];
	private byte[] pageId = new byte[2];
	private byte[] pageSize = new byte[2];

	public GameList_ExtraInfoZeroRequest(byte[] data) {
		super(MediusMessageType.GameList_ExtraInfo0, data);
		ByteBuffer buf = ByteBuffer.wrap(data);
		buf.get(messageId);
		buf.get(pageId);
		buf.get(pageSize);
	}

	public String toString() {
		return "GameList_ExtraInfoZeroRequest: \n" + "messageId: " + Utils.bytesToHex(messageId) + '\n' + "pageId: " + Utils.bytesToHex(pageId) + '\n' + "pageSize: " + Utils.bytesToHex(pageSize);
	}

	public byte[] getMessageId() {
		return messageId;
	}

	public byte[] getPageId() {
		return pageId;
	}

	public byte[] getPageSize() {
		return pageSize;
	}

}
