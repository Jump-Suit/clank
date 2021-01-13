package net.hashsploit.clank.server;

import java.util.logging.Logger;

import net.hashsploit.clank.Clank;
import net.hashsploit.clank.server.medius.objects.MediusPlayerStatus;
import net.hashsploit.clank.server.rpc.ClankMlsRpcServer;

public class Player {
	private static final Logger logger = Logger.getLogger(Player.class.getName());

	private final MediusClient client;
	private MediusPlayerStatus playerStatus;
	private String username;
	private int accountId;
	
	private String sessionKey;
	
	private int gameWorldId;
	
	private int chatWorldId;
	
	public Player(MediusClient client, MediusPlayerStatus status) {
		this.client = client;
		this.playerStatus = status;
		this.gameWorldId = 0;
		this.chatWorldId = 0;
	}
	
	public String toString() {
		return "Player: \n" + 
				"Username: " + username + "\n" + 
				"State: " + playerStatus.name() + "\n" + 
				"AccountID: " + Integer.toString(accountId) + "\n" + 
				"ChatWorld: " + Integer.toString(chatWorldId) + "\n" + 
				"GameWorld: " + Integer.toString(gameWorldId);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MediusClient getClient() {
		return client;
	}

	public void setAccountId(int playerAccountId) {
		this.accountId = playerAccountId;
		this.username = Clank.getInstance().getDatabase().getUsername(accountId);
	}
	
	public void setChatWorld(int chatWorldId) {
		this.chatWorldId = chatWorldId;
	}

	public void updateStatus(MediusPlayerStatus status) {
		logger.info("PlayerUpdateStatus: [Username: '" + username + "', SessionKey: " + sessionKey + ", new Status: " + status.toString() + ", accountId: " + accountId + "]");
		if (status != MediusPlayerStatus.MEDIUS_PLAYER_IN_GAME_WORLD) {
			this.gameWorldId = 0;
		}
		this.playerStatus = status;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public MediusPlayerStatus getStatus() {
		return playerStatus;
	}

	public int getGameWorldId() {
		return gameWorldId;
	}

	public int getChatWorldId() {
		return chatWorldId;
	}

	public void setGameWorldId(int gameWorldId) {
		this.gameWorldId = gameWorldId;
	}

	public String getSessionKey() {
		return sessionKey;
	}
	
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
		
}
