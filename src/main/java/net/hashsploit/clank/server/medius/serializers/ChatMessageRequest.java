package net.hashsploit.clank.server.medius.serializers;

import java.nio.ByteBuffer;

import net.hashsploit.clank.server.medius.MediusConstants;
import net.hashsploit.clank.server.medius.MediusMessageType;
import net.hashsploit.clank.server.medius.objects.MediusChatMessageType;
import net.hashsploit.clank.server.medius.objects.MediusMessage;
import net.hashsploit.clank.utils.Utils;

public class ChatMessageRequest extends MediusMessage {

	private final byte[] messageId = new byte[MediusConstants.MESSAGEID_MAXLEN.value];
	private final byte[] sessionKey = new byte[MediusConstants.SESSIONKEY_MAXLEN.value];
	private final MediusChatMessageType mediusChatMessageType;
	private final int targetId;
	private final byte[] text = new byte[MediusConstants.CHATMESSAGE_MAXLEN.value];

	public ChatMessageRequest(byte[] data) {
		super(MediusMessageType.ChatMessage, data);

		// Process the packet
		ByteBuffer buf = ByteBuffer.wrap(data);

		buf.get(messageId);
		buf.get(sessionKey);
		buf.getShort(); // Padding buffer
		mediusChatMessageType = MediusChatMessageType.getByValue(buf.getInt());
		targetId = buf.getInt();
		buf.get(text);
	}

	public int getTargetId() {
		return targetId;
	}

	public MediusChatMessageType getMessageType() {
		return mediusChatMessageType;
	}

	@Override
	public String getDebugString() {
		return Utils.generateDebugPacketString(ChatMessageRequest.class.getName(),
			new String[] {
				"messageId",
				"sessionKey",
				"mediusChatMessageType",
				"targetId",
				"text"
			},
			new String[] {
				Utils.bytesToHex(messageId),
				Utils.bytesToHex(sessionKey),
				mediusChatMessageType.name() + " (" + Utils.intToHex(mediusChatMessageType.getValue()) + ")",
				"" + targetId,
				Utils.bytesToStringClean(text)
			}
		);
	}

	public synchronized byte[] getMessageId() {
		return messageId;
	}

	public synchronized byte[] getText() {
		return text;
	}

	
}
