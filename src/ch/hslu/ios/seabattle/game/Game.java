package ch.hslu.ios.seabattle.game;

import ch.hslu.ios.seabattle.commands.FullUpdateCommand;
import ch.hslu.ios.seabattle.commands.PartialUpdateCommand;
import ch.hslu.ios.seabattle.commands.PlayerCommand;
import ch.hslu.ios.seabattle.commands.PlayerFoundCommand;
import ch.hslu.ios.seabattle.commands.PlayerReadyCommand;
import ch.hslu.ios.seabattle.commands.PlayerShootCommand;
import ch.hslu.ios.seabattle.commands.ReadyCommand;
import ch.hslu.ios.seabattle.commands.WinCommand;


public class Game {
	
	private final Player fP1;
	private final Player fP2;
	private boolean fGameShouldCloseByDisconnect;
	private boolean fHasStarted;
	private Player fActivePlayer;
	private Player fWinner;

	public Game(Player p1, Player p2) {
		fP1 = p1;
		fP2 = p2;
		fActivePlayer = p1;
		fWinner = null;
		
		fGameShouldCloseByDisconnect = false;
		
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
		return fWinner;
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
		switch (command.getCommandType()) {
			case PlayerShoot:
				if (fActivePlayer == command.getSource() && fHasStarted && fWinner == null) {
					PlayerShootCommand cmd = (PlayerShootCommand)command;
					Player enemy = fActivePlayer == fP1 ? fP2 : fP1;
					int cell = enemy.getPlayerField().getCell(cmd.getPosX(), cmd.getPosY());
					if (cell == GameField.VALUE_FREE) {
						cell = GameField.VALUE_FREE_HIT;
						enemy.getPlayerField().setCell(cmd.getPosX(), cmd.getPosY(), cell);
						PartialUpdateCommand cmd1 = new PartialUpdateCommand(cmd.getPosX(), cmd.getPosY(), cell, false, false);
						command.getSource().sendCommand(cmd1);
						
						PartialUpdateCommand cmd2 = new PartialUpdateCommand(cmd.getPosX(), cmd.getPosY(), cell, true, true);
						enemy.sendCommand(cmd2);
						fActivePlayer = enemy;
					} else if (cell == GameField.VALUE_SHIP) {
						cell = GameField.VALUE_SHIP_HIT;
						enemy.getPlayerField().setCell(cmd.getPosX(), cmd.getPosY(), cell);
						if (enemy.getPlayerField().shipIsDestroyed(cmd.getPosX(), cmd.getPosY())) {
							enemy.getPlayerField().destroyShipSurroundings(cmd.getPosX(), cmd.getPosY());
							
							FullUpdateCommand cmd1 = new FullUpdateCommand(enemy.getPlayerField().getAsHiddenString(), false, true);
							command.getSource().sendCommand(cmd1);
							
							FullUpdateCommand cmd2 = new FullUpdateCommand(enemy.getPlayerField().getAsString(), true, false);
							enemy.sendCommand(cmd2);
							
							if (!enemy.getPlayerField().hasMoreShips()) {
								fWinner = command.getSource();
								WinCommand cmd3 = new WinCommand(true);
								fWinner.sendCommand(cmd3);
								
								enemy.sendCommand(new FullUpdateCommand(fWinner.getPlayerField().getAsString(), false, false));
								enemy.sendCommand(new WinCommand(false));
							}
						} else {
							PartialUpdateCommand cmd1 = new PartialUpdateCommand(cmd.getPosX(), cmd.getPosY(), cell, false, true);
							command.getSource().sendCommand(cmd1);
							
							PartialUpdateCommand cmd2 = new PartialUpdateCommand(cmd.getPosX(), cmd.getPosY(), cell, true, false);
							enemy.sendCommand(cmd2);
						}
					} else if (cell == GameField.VALUE_FREE_HIT || cell == GameField.VALUE_SHIP_HIT) {
						// Already shot
						PartialUpdateCommand cmd1 = new PartialUpdateCommand(cmd.getPosX(), cmd.getPosY(), cell, false, true);
						command.getSource().sendCommand(cmd1);
					}
				}
				break;
			case RenewGameField:
				if (!fHasStarted) {
					GameField field = new GameField();
					if (field.createRandomField()) {
						command.getSource().setPlayerField(field);
						FullUpdateCommand cmd = new FullUpdateCommand(field.getAsString(), true, false);
						command.getSource().sendCommand(cmd);
					}
				}
				break;
			case Ready:
				if (!fHasStarted) {
					ReadyCommand cmd = (ReadyCommand)command;
					cmd.getSource().setIsReady(cmd.isReady());
					
					if (fP1.isIsReady() && fP2.isIsReady()) {
						fHasStarted = true;
						fActivePlayer = fP1;
						PlayerReadyCommand cmd1 = new PlayerReadyCommand(true, true, true);
						fP1.sendCommand(cmd1);
						PlayerReadyCommand cmd2 = new PlayerReadyCommand(true, true, false);
						fP2.sendCommand(cmd2);
					} else {
						PlayerReadyCommand cmd1 = new PlayerReadyCommand(cmd.isReady(), false, false);
						if (fP1 == cmd.getSource()) {
							fP2.sendCommand(cmd1);
						} else {
							fP1.sendCommand(cmd1);
						}
					}
				}
				break;
			case UpdateName:
				if (fP1 == command.getSource()) {
					fP2.sendCommand(new PlayerFoundCommand(command.getSource()));
				} else {
					fP1.sendCommand(new PlayerFoundCommand(command.getSource()));
				}
				break;
		}
	}
}
