package ch.hslu.ios.seabattle.commands;

import ch.hslu.ios.seabattle.game.Player;

public class RenewGameFieldCommand extends PlayerCommand {
	
	public RenewGameFieldCommand(Player source, String[] params) {
		super(PlayerCommandType.RenewGameField, source, params);
	}
	
	@Override
	protected void parseParams(String[] params) {
	}
}
