package net.hashsploit.clank.server;

import java.util.logging.Logger;

import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.hashsploit.clank.server.common.MediusServer;
import net.hashsploit.clank.server.pipeline.TestHandlerMAS;
import net.hashsploit.clank.server.pipeline.TestHandlerMLS;
import net.hashsploit.clank.utils.Utils;

public class MediusClient implements IClient {

	private static final Logger logger = Logger.getLogger(MediusClient.class.getName());

	private final MediusServer server;
	private final SocketChannel socketChannel;
	private Player player;
	private ClientState state;
	private boolean encrypted;

	private long unixConnectTime;
	private long txPacketCount;
	private long rxPacketCount;

	public MediusClient(MediusServer server, SocketChannel channel) {
		this.server = server;
		this.socketChannel = channel;
		this.state = ClientState.UNAUTHENTICATED;
		this.encrypted = true;
		this.unixConnectTime = System.currentTimeMillis();
		this.txPacketCount = 0L;
		this.rxPacketCount = 0L;
		this.player = null;

		logger.info("Client connected: " + getIPAddress());

		// If so, initialize the correct pipeline for it.
		switch (server.getEmulationMode()) {
			case MEDIUS_AUTHENTICATION_SERVER:
				channel.pipeline().addLast("MediusTestHandlerMAS", new TestHandlerMAS(this));
				break;
			case MEDIUS_LOBBY_SERVER:
				channel.pipeline().addLast("MediusTestHandlerMLS", new TestHandlerMLS(this));
				break;
			default:
				break;
		}

		ChannelFuture closeFuture = channel.closeFuture();

		closeFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				onDisconnect();
			}
		});

	}
	
	/**
	 * Get the Medius Server associated with this client.
	 * @return
	 */
	public MediusServer getServer() {
		return server;
	}
	
	/**
	 * Get the Socket Channel associated with this client.
	 * @return
	 */
	protected SocketChannel getSocketChannel() {
		return socketChannel;
	}

	/**
	 * Get the client's socket IP Address.
	 */
	public String getIPAddress() {
		return socketChannel.remoteAddress().getAddress().getHostAddress();
	}
	
	/**
	 * Get the client's socket port. 
	 */
	public int getPort() {
		return socketChannel.remoteAddress().getPort();
	}

	/**
	 * Get the client's state bitmask.
	 */
	public ClientState getClientState() {
		return state;
	}

	/**
	 * Set the client's state bitmask.
	 * @param state
	 */
	protected void setClientState(final ClientState state) {
		this.state = state;
	}

	/**
	 * Returns true if the connection to this client is encrypted.
	 * 
	 * @return
	 */
	public boolean isEncrypted() {
		return encrypted;
	}
	
	/**
	 * Set if this client is using encryption or not.
	 * @param encrypted
	 */
	protected void setEncrypted(final boolean encrypted) {
		this.encrypted = encrypted;
	}
	
	/**
	 * Get the player object associated with this client.
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set the player object to be associated with this client.
	 * @param player
	 */
	protected void setPlayer(final Player player) {
		this.player = player;
	}

	/**
	 * Returns the UNIX timestamp of when the connection started.
	 * 
	 * @return
	 */
	public long getUnixConnectTime() {
		return unixConnectTime;
	}

	/**
	 * Get the total number of received packets.
	 * 
	 * @return
	 */
	public long getRxPacketCount() {
		return rxPacketCount;
	}

	/**
	 * Get the total number of transmitted packets.
	 * 
	 * @return
	 */
	public long getTxPacketCount() {
		return txPacketCount;
	}

	/**
	 * Send a raw payload to the client.
	 * 
	 * @param data
	 */
	public void sendRaw(byte[] data) {
		socketChannel.writeAndFlush(data).awaitUninterruptibly();
		txPacketCount++;
	}

	/**
	 * Disconnect this client.
	 */
	public void disconnect() {
		socketChannel.flush();
		socketChannel.disconnect();
		socketChannel.close();
	}

	/**
	 * This method is called when the client socket closes.
	 */
	private void onDisconnect() {
		logger.info("Client disconnect: " + socketChannel.remoteAddress());
		server.removeClient(this);
	}

	/**
	 * Send a data packet to the client.
	 * 
	 * @param msg
	 */
	public void sendMessage(DataPacket msg) {
		if (msg instanceof EncryptedDataPacket) {

			// TODO: handle encryption
			// final EncryptedDataPacket edp = (EncryptedDataPacket) msg;

		} else {
			final DataPacket pdp = (DataPacket) msg;
			final byte[] data = pdp.toBytes();
			logger.fine("Sending: " + Utils.bytesToString(data));
			sendRaw(data);
		}
	}

}
