package ch.hslu.ios.seabattle;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import ch.hslu.ios.seabattle.game.Player;

/**
 * TCP Server. Handles all the Socket stuff
 * @author Thomas Bomatter
 */
public final class TCPServer extends Thread{
	
	private static final int TCP_PORT = 8222;
	
	private static TCPServer instance = null;
	/**
	 * Retrieves the Server Instance or creates it if necessary. 
	 * @return the Server instance.
	 */
	public static synchronized TCPServer getInstance() {
		if (instance == null) {
			instance = new TCPServer();
		}
		return instance;
	}
	
	private ServerSocket mSocket = null;
	private List<Player> mConnectedPlayers;
	private iAction<Player> fPlayerConnectedAction;
	private iAction<Player> fPlayerDisconnectedAction;
	
	private TCPServer() {
		super();
		try {
			System.out.println("Starting TCP Listener...");
			mConnectedPlayers = new LinkedList<>();
			mSocket = new ServerSocket(TCP_PORT);
		} catch (IOException e) {		
			System.out.println("Failed to start TCP Listener!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the callback when an Player has connected to the Server.
	 * @param playerConnectedAction The callback
	 */
	public void setPlayerConnectedAction(iAction<Player> playerConnectedAction) {
		fPlayerConnectedAction = playerConnectedAction;
	}
	
	public void setPlayerDisconnectedAction(iAction<Player> playerDisconnectedAction) {
		fPlayerDisconnectedAction = playerDisconnectedAction;
	}
	
	/**
	 * Listen the tcp sockets
	 */
	@Override
	public void run() {
		try {
			System.out.println("TCP Listener started on IP: " + InetAddress.getLocalHost().getHostAddress() + " Port: " + TCP_PORT);
		} catch (UnknownHostException e1) {
			System.out.println("TCP Listener started on IP: UNKOWN Port: " + TCP_PORT);
		}
		
		while (true) {
			try {
				final Socket sock = mSocket.accept();
				System.out.println("New Player connected: " + sock.getInetAddress().toString());
				final Player player = new Player(sock, new iAction<Player>() {
					
					@Override
					public void perform(Player data) {
						mConnectedPlayers.remove(data);
						if (fPlayerDisconnectedAction != null)
							fPlayerDisconnectedAction.perform(data);
					}
				});
				mConnectedPlayers.add(player);
				player.start();
				
				if (fPlayerConnectedAction != null) {
					fPlayerConnectedAction.perform(player);
				}
			} catch (IOException e) {						
				e.printStackTrace();
			}
		}	
	}
}
