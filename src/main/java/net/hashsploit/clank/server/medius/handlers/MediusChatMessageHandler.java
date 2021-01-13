package net.hashsploit.clank.server.medius.handlers;

import java.util.List;

import net.hashsploit.clank.server.MediusClient;
import net.hashsploit.clank.server.Player;
import net.hashsploit.clank.server.medius.MediusConstants;
import net.hashsploit.clank.server.medius.MediusLobbyServer;
import net.hashsploit.clank.server.medius.MediusMessageType;
import net.hashsploit.clank.server.medius.MediusPacketHandler;
import net.hashsploit.clank.server.medius.objects.MediusMessage;
import net.hashsploit.clank.server.medius.serializers.ChatFwdMessageResponse;
import net.hashsploit.clank.server.medius.serializers.ChatMessageRequest;
import net.hashsploit.clank.server.medius.serializers.GenericChatFwdMessageResponse;
import net.hashsploit.clank.utils.Utils;

public class MediusChatMessageHandler extends MediusPacketHandler {

	private ChatMessageRequest reqPacket;
	private GenericChatFwdMessageResponse respPacket;

	public MediusChatMessageHandler() {
		super(MediusMessageType.ChatMessage, MediusMessageType.ChatFwdMessage);
	}

	@Override
	public void read(MediusClient client, MediusMessage mm) {
		reqPacket = new ChatMessageRequest(mm.getPayload());		
		logger.finest(reqPacket.getDebugString());

	}

	@Override
	public List<MediusMessage> write(MediusClient client) {

		/*
		 * 
		 *  Send this chat message to each client in the same world
		*/
		String username = client.getPlayer().getUsername();
		String chatMsg = Utils.bytesToStringClean(reqPacket.getText());
		logger.info("[CHAT]: " + username + ": " + chatMsg);
		ChatFwdMessageResponse msg = new ChatFwdMessageResponse(
				reqPacket.getMessageId(), 
				Utils.buildByteArrayFromString(username, MediusConstants.USERNAME_MAXLEN.getValue()),
				Utils.buildByteArrayFromString(chatMsg, MediusConstants.CHATMESSAGE_MAXLEN.getValue()));
				
		/*
		 * Example:
		 * PACKET: 160.33.33.245:10078 => 192.168.0.50:53689
[SUCCESS] ID:ID_0a PLAINTEXT: 
01 3C 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 CF 84 13 00 5A 33 72 30 78 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 47 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 
.<1.......................I...Z3r0x...............................G...............................................................
		 */
		int playerWorldId = client.getPlayer().getChatWorldId();
		MediusLobbyServer server = (MediusLobbyServer) client.getServer();
		List<Player> playersInWorld = server.getLobbyWorldPlayers(playerWorldId);

		for (Player player: playersInWorld) {
			if (player != client.getPlayer()) // Dont send to self
				player.getClient().sendMediusMessage(msg);
		}
		
		return null;
	}

}
