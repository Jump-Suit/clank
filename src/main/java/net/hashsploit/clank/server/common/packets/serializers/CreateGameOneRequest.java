package net.hashsploit.clank.server.common.packets.serializers;

import java.nio.ByteBuffer;

import net.hashsploit.clank.server.common.MediusConstants;
import net.hashsploit.clank.server.common.MediusMessageType;
import net.hashsploit.clank.server.common.objects.MediusMessage;
import net.hashsploit.clank.utils.Utils;

public class CreateGameOneRequest extends MediusMessage {

	private byte[] messageID = new byte[MediusConstants.MESSAGEID_MAXLEN.value];
	private byte[] sessionKey = new byte[MediusConstants.SESSIONKEY_MAXLEN.value];
	private byte[] appID = new byte[4];
	private byte[] minPlayers = new byte[4];
	private byte[] maxPlayers = new byte[4];
	private byte[] gameLevel = new byte[4];
	private byte[] gameName = new byte[MediusConstants.GAMENAME_MAXLEN.value];
	private byte[] gamePassword = new byte[MediusConstants.GAMEPASSWORD_MAXLEN.value];
	private byte[] spectatorPassword = new byte[MediusConstants.GAMEPASSWORD_MAXLEN.value];
	private byte[] playerSkillLevel = new byte[4];
	private byte[] rulesSet = new byte[4];
	private byte[] genField1 = new byte[4];
	private byte[] genField2 = new byte[4];
	private byte[] genField3 = new byte[4];
	private byte[] gameHostType = new byte[4];
	private byte[] attributes = new byte[4];
	private boolean noAttributes = false;
	
	public CreateGameOneRequest(byte[] data) {
		super(MediusMessageType.CreateGame1, data);
    	ByteBuffer buf = ByteBuffer.wrap(data);
    	
    	if (data.length == 212) {
    		buf.get(messageID);
        	buf.get(sessionKey);
        	buf.getShort(); //buffer
        	buf.get(appID);
        	buf.get(minPlayers);
        	buf.get(maxPlayers);
        	buf.get(gameLevel);//aquatos channel id?
        	buf.get(gameName);
        	buf.get(gamePassword);
        	buf.get(spectatorPassword);
        	buf.get(playerSkillLevel);
        	buf.get(rulesSet);
        	buf.get(genField1);
        	buf.get(genField2);
        	buf.get(genField3);
        	buf.get(gameHostType);
        	buf.get(attributes);
    	} else {
    		buf.get(messageID);
        	buf.get(sessionKey);
        	buf.getShort(); //buffer
        	buf.get(appID);
        	buf.get(minPlayers);
        	buf.get(maxPlayers);
        	buf.get(gameLevel);//aquatos channel id?
        	buf.get(gameName);
        	buf.get(gamePassword);
        	buf.get(spectatorPassword);
        	buf.get(playerSkillLevel);
        	buf.get(rulesSet);
        	buf.get(genField1);
        	buf.get(genField2);
        	buf.get(genField3);
        	buf.get(gameHostType);
        	noAttributes = true;
    	}
    	
    	
	}
	
	public String toString() {
		return "CreateGame1Request: \n" + 
				"messageID: " + Utils.bytesToHex(messageID) + '\n' + 
				"sessionKey: " + Utils.bytesToHex(sessionKey) + '\n' + 
				"appID: " + Utils.bytesToHex(appID) + '\n' + 
				"minPlayers: " + Utils.bytesToHex(minPlayers) + '\n' + 
				"maxPlayers: " + Utils.bytesToHex(maxPlayers) + '\n' + 
				"gameLevel: " + Utils.bytesToHex(gameLevel) + '\n' + 
				"gameName: " + Utils.bytesToHex(gameName) + '\n' + 
				"gamePassword: " + Utils.bytesToHex(gamePassword) + '\n' + 
				"spectatorPassword: " + Utils.bytesToHex(spectatorPassword) + '\n' + 
				"playerSkillLevel: " + Utils.bytesToHex(playerSkillLevel) + '\n' + 
				"rulesSet: " + Utils.bytesToHex(rulesSet) + '\n' + 
				"genField1: " + Utils.bytesToHex(genField1) + '\n' + 
				"genField2: " + Utils.bytesToHex(genField2) + '\n' + 
				"genField3: " + Utils.bytesToHex(genField3) + '\n' + 
				"gameHostType: " + Utils.bytesToHex(gameHostType) + '\n' + 
				"attributes: " + Utils.bytesToHex(attributes);		
	}
	

	public synchronized byte[] getMessageID() {
		return messageID;
	}

	public synchronized byte[] getSessionKey() {
		return sessionKey;
	}

	public synchronized byte[] getAppID() {
		return appID;
	}

	public synchronized byte[] getMinPlayers() {
		return minPlayers;
	}

	public synchronized byte[] getMaxPlayers() {
		return maxPlayers;
	}

	public synchronized byte[] getGameLevel() {
		return gameLevel;
	}

	public synchronized byte[] getGameName() {
		return gameName;
	}

	public synchronized byte[] getGamePassword() {
		return gamePassword;
	}

	public synchronized byte[] getSpectatorPassword() {
		return spectatorPassword;
	}

	public synchronized byte[] getPlayerSkillLevel() {
		return playerSkillLevel;
	}

	public synchronized byte[] getRulesSet() {
		return rulesSet;
	}

	public synchronized byte[] getGenField1() {
		return genField1;
	}

	public synchronized byte[] getGenField2() {
		return genField2;
	}

	public synchronized byte[] getGenField3() {
		return genField3;
	}

	public synchronized byte[] getGameHostType() {
		return gameHostType;
	}

	public synchronized byte[] getAttributes() {
		return attributes;
	}
	
}
