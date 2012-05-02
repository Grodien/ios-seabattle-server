package ch.hslu.ios.seabattle.game;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;


public class PlayerAndGameHandler extends Thread {

	private final LinkedBlockingDeque<Player> fQueue;
	private final ArrayList<Game> fActiveGames;

	public PlayerAndGameHandler(LinkedBlockingDeque<Player> queue) {
		fQueue = queue;
		fActiveGames = new ArrayList<>();
	}
	
	@Override
	public void run() {
		System.out.println("Player and Game Handler started");
		while (true) {
			if (fQueue.size() >= 2) {
				Player p1 = fQueue.poll();
				Player p2 = fQueue.poll();
				
				if (p1 == null || !p1.isConnected())
					fQueue.addFirst(p2);
				if (p2 == null || !p2.isConnected())
					fQueue.addFirst(p1);
				
				Game game = new Game(p1, p2);
				fActiveGames.add(game);
				
				System.out.println("New game created! (" + game.getP1().getPlayerName() + " vs. "
							+ game.getP2().getPlayerName() + ")");
			}
			
			for (int i = fActiveGames.size(); i > 0; i--) {
				Game game = fActiveGames.get(i-1);
				if (game.isGameShouldCloseByDisconnect()) {
					System.out.println("Game " + game.getP1().getPlayerName() + " vs. "
							+ game.getP2().getPlayerName() + " ended because "
							+ (game.getP1().isConnected() ? game.getP2().getPlayerName() : game.getP1().getPlayerName())
							+ " disconnected!");
					
					//TODO: Notify User
					
					game.getP1().setCurrentGame(null);
					game.getP2().setCurrentGame(null);
					fActiveGames.remove(game);
				} else if (game.isHasEnded()) {
					System.out.println("Game " + game.getP1().getPlayerName() + " vs. "
							+ game.getP2().getPlayerName() + " ended: Winner "
							+ game.getWinner().getPlayerName());
					game.getP1().setCurrentGame(null);
					game.getP2().setCurrentGame(null);
					fActiveGames.remove(game);
				}
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {	}
		}		
	}
}
