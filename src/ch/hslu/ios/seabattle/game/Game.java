package ch.hslu.ios.seabattle.game;

import ch.hslu.ios.seabattle.commands.PlayerCommand;


public class Game {
	
	private final Player fP1;
	private final Player fP2;
	private boolean fGameShouldCloseByDisconnect;
	private boolean fHasEnded;
	private Player fActivePlayer;

	public Game(Player p1, Player p2) {
		fP1 = p1;
		fP2 = p2;
		fActivePlayer = p1;
		
		fGameShouldCloseByDisconnect = false;
		fHasEnded = false;
		
		fP1.setCurrentGame(this);
		fP2.setCurrentGame(this);
		
		GameField field1 = new GameField();
		GameField field2 = new GameField();
		if (field1.createRandomField() && field2.createRandomField()) {
			fP1.setPlayerField(field1);
			fP2.setPlayerField(field2);
		} else {
			//TODO: notify players that games can't be created because of gamefield issues.
		}
	}
	
	public void closeGame() {
		fGameShouldCloseByDisconnect = true;
	}
	
	public Player getWinner() {
		// TODO: Calculate Winner
		return null;
	}
	
	public boolean isHasEnded() {
		return fHasEnded;
	}
	
	public boolean isGameShouldCloseByDisconnect() {
		return fGameShouldCloseByDisconnect;
	}

	public Player getP1() {
		return fP1;
	}

	public Player getP2() {
		return fP2;
	}
	
	public void handleCommand(PlayerCommand command) {
		
	}
}
