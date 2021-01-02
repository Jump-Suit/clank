package net.hashsploit.clank.server.common.packets.handlers;

import java.util.HashMap;

import net.hashsploit.clank.Clank;
import net.hashsploit.clank.config.configs.MasConfig;
import net.hashsploit.clank.server.MediusClient;
import net.hashsploit.clank.server.common.MediusAuthenticationServer;
import net.hashsploit.clank.server.common.MediusCallbackStatus;
import net.hashsploit.clank.server.common.MediusMessageType;
import net.hashsploit.clank.server.common.MediusPacketHandler;
import net.hashsploit.clank.server.common.objects.MediusMessage;
import net.hashsploit.clank.server.common.packets.serializers.AccountLoginRequest;
import net.hashsploit.clank.server.common.packets.serializers.AccountLoginResponse;
import net.hashsploit.clank.server.rpc.PlayerLoginResponse;
import net.hashsploit.clank.utils.Utils;

public class MediusAccountLoginHandler extends MediusPacketHandler {

	private AccountLoginRequest reqPacket;
	private AccountLoginResponse respPacket;

	public MediusAccountLoginHandler() {
		super(MediusMessageType.AccountLogin, MediusMessageType.AccountLoginResponse);
	}

	@Override
	public void read(MediusMessage mm) {
		reqPacket = new AccountLoginRequest(mm.getPayload());
		logger.finest(reqPacket.toString());
	}

	@Override
	public void write(MediusClient client) {
		byte[] callbackStatus = Utils.intToBytesLittle((MediusCallbackStatus.LOGIN_FAILED.getValue()));
		byte[] mlsToken = null;
		//byte[] mlsToken = Utils.hexStringToByteArray("12345678901234567890123456789012345678");
		                                              
		// TODO: Clean the username!!! add a utility to check if the username is valid, length, and characters in it.
		final String username = Utils.parseMediusString(reqPacket.getUsernameBytes());
		final String password = Utils.parseMediusString(reqPacket.getPasswordBytes());

		PlayerLoginResponse response = ((MediusAuthenticationServer) client.getServer()).playerLogin(username, password);
		int playerAccountId = response.getAccountId();
		boolean rpcSuccess = response.getSuccess();
		String mlsTokenString = response.getMlsToken();
		
		logger.info("PlayerLoginRPC AccountId: " + Integer.toString(playerAccountId));
		logger.info("PlayerLoginRPC success: " + rpcSuccess);
		logger.info("PlayerLoginRPC mlsTokenString: " + mlsTokenString);
		
		// TODO: Generate random auth token each connection, save in db for MLS to use.
		// db should have a expiration UNIX time-stamp as well that needs to be updated
		// by MLS.
		//new Random().nextBytes(mlsToken);
		
		// MLS Token is all 0s when failure happens
		
		// Last 3 bytes are padding
		mlsToken = Utils.hexStringToByteArray(mlsTokenString + "000000");
		
		mlsToken[mlsToken.length - 1] = (byte) 0x00;
		mlsToken[mlsToken.length - 2] = (byte) 0x00;
		mlsToken[mlsToken.length - 3] = (byte) 0x00;
	
		// If player login is successful, check if the username/pass is in whitelist also
		boolean whitelistSuccess = false;
		
		MasConfig masConfig;
		if (Clank.getInstance().getConfig() instanceof MasConfig) {
			masConfig = (MasConfig) Clank.getInstance().getConfig();	
			if (!masConfig.isWhitelistEnabled()) { // Whitelist is disabled
				whitelistSuccess = true;
			}
			else { // Whitelist is enabled. Check username/pass with whitelist
				HashMap<String, String> whitelist = masConfig.getWhitelist();
				if (whitelist.keySet().contains(username.toLowerCase())) {
					if (whitelist.get(username.toLowerCase()).equals(password)) {
						whitelistSuccess = true;
					}
				}
			}
		}
		
		logger.info("Whitelist success: " + whitelistSuccess);
		// gRPC Login success
		if (rpcSuccess && whitelistSuccess) {
			callbackStatus = Utils.intToBytesLittle((MediusCallbackStatus.SUCCESS.getValue()));
		} else {
			callbackStatus = Utils.intToBytesLittle((MediusCallbackStatus.INVALID_PASSWORD.getValue()));
		}
		
		byte[] accountID = Utils.intToBytesLittle(playerAccountId);
		byte[] accountType = Utils.intToBytesLittle(2);
		
		// FIXME: bad location
		//final LocationConfig location = client.getServer().getLogicHandler().getLocation();
		
		// Default world id = 0
		byte[] worldID = Utils.intToBytesLittle(0);
		String mlsIpAddress = (Clank.getInstance().getConfig() instanceof MasConfig) ? ((MasConfig) Clank.getInstance().getConfig()).getMlsAddress() : null;
		String natIpAddress = (Clank.getInstance().getConfig() instanceof MasConfig) ? ((MasConfig) Clank.getInstance().getConfig()).getNatAddress() : null;

		if (mlsIpAddress == null) {
			mlsIpAddress = Utils.getPublicIpAddress();
		}

		if (natIpAddress == null) {
			natIpAddress = Utils.getPublicIpAddress();
		}

		byte[] mlsAddress = mlsIpAddress.getBytes();
		int mlsNumZeros = 16 - mlsIpAddress.length();
		String mlsZeroString = new String(new char[mlsNumZeros]).replace("\0", "00");
		byte[] mlsZeroTrail = Utils.hexStringToByteArray(mlsZeroString);

		byte[] natAddress = natIpAddress.getBytes();
		int natNumZeros = 16 - natIpAddress.length();
		String natZeroString = new String(new char[natNumZeros]).replace("\0", "00");
		byte[] natZeroTrail = Utils.hexStringToByteArray(natZeroString);

		respPacket = new AccountLoginResponse(reqPacket.getMessageID(), callbackStatus, accountID, accountType, worldID, mlsAddress, mlsZeroTrail, natAddress, natZeroTrail, mlsToken);
		client.sendMediusMessage(respPacket);
	}

}
