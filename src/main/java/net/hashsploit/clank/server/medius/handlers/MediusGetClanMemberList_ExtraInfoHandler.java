package net.hashsploit.clank.server.medius.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import net.hashsploit.clank.server.MediusClient;
import net.hashsploit.clank.server.medius.MediusCallbackStatus;
import net.hashsploit.clank.server.medius.MediusConstants;
import net.hashsploit.clank.server.medius.MediusMessageType;
import net.hashsploit.clank.server.medius.MediusPacketHandler;
import net.hashsploit.clank.server.medius.objects.MediusMessage;
import net.hashsploit.clank.utils.Utils;

public class MediusGetClanMemberList_ExtraInfoHandler extends MediusPacketHandler {
	
	private byte[] messageID = new byte[MediusConstants.MESSAGEID_MAXLEN.value];
	private byte[] clanID = new byte[4];
	private byte[] ladderStatIndex = new byte[4];
	private byte[] sortOrder = new byte[4];
	
    public MediusGetClanMemberList_ExtraInfoHandler() {
        super(MediusMessageType.GetClanMemberList_ExtraInfo, MediusMessageType.GetClanMemberList_ExtraInfoResponse);
    }
    
    @Override
    public void read(MediusClient client, MediusMessage mm) {
    	// Process the packet
    	ByteBuffer buf = ByteBuffer.wrap(mm.getPayload());
    	
    	buf.get(messageID);
    	buf.get(new byte[3]); // buffer
    	buf.get(clanID);
    	buf.get(ladderStatIndex);
    	buf.get(sortOrder);
    	
    	logger.fine("Message ID : " + Utils.bytesToHex(messageID));
    }
    
    @Override
    public List<MediusMessage> write(MediusClient client) { 

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		try {
			outputStream.write(messageID);
			outputStream.write(Utils.hexStringToByteArray("000000"));
			outputStream.write(Utils.intToBytes(MediusCallbackStatus.NO_RESULT.getValue()));	 // give no result for now		
			outputStream.write(Utils.intToBytesLittle(0)); // accountID
			outputStream.write(Utils.buildByteArrayFromString("", MediusConstants.ACCOUNTNAME_MAXLEN.value)); // 
			outputStream.write(Utils.buildByteArrayFromString("", MediusConstants.ACCOUNTSTATS_MAXLEN.value)); // 
			outputStream.write(Utils.intToBytesLittle(0)); // mediusPlayerOnlineStats
			outputStream.write(Utils.intToBytesLittle(0)); // ladderStat
			outputStream.write(Utils.intToBytesLittle(0)); // ladderPosition
			outputStream.write(Utils.intToBytesLittle(0)); // totalRankings
			outputStream.write(Utils.hexStringToByteArray("01000000")); // end of list
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<MediusMessage> response = new ArrayList<MediusMessage>();
		response.add(new MediusMessage(responseType, outputStream.toByteArray()));
		return response;
    }

}
