package ch.hslu.ios.seabattle;

import java.util.concurrent.LinkedBlockingDeque;

import ch.hslu.ios.seabattle.game.Game;
import ch.hslu.ios.seabattle.game.Player;
import ch.hslu.ios.seabattle.game.PlayerAndGameHandler;

public class Server {
	private static Server fInstance = null;
	
	public static synchronized Server getInstance() {
		if (fInstance == null) {
			fInstance = new Server();
		}
		return fInstance;
	}
	
	private TCPServer fTcpServer;
	private PlayerAndGameHandler fHandler;
	private LinkedBlockingDeque<Player> fQueue;

	/**
	 * Actual server class with business logic. 
	 * Starts TCP Server and handles the incoming clients
	 */
	private Server() {
		System.out.println("Welcome to the seabattle server");
		
		fQueue = new LinkedBlockingDeque<Player>();
		
		fTcpServer = TCPServer.getInstance();
		fTcpServer.setPlayerConnectedAction(new iAction<Player>() {
			
			@Override
			public void perform(Player data) {
				if (fQueue.offer(data)) {
					
				} else {
					
				}
			}
		});
		fTcpServer.setPlayerDisconnectedAction(new iAction<Player>() {
			
			@Override
			public void perform(Player data) {
				fQueue.remove(data);
				Game game = data.getCurrentGame();
				if (game != null) {
					game.closeGame();
				}
			}
		});
		
		fHandler = new PlayerAndGameHandler(fQueue);
	}
	
	/**
	 * This method starts the TCP server
	 */
	public void start() {
		fTcpServer.start();
		fHandler.start();
	}
}
