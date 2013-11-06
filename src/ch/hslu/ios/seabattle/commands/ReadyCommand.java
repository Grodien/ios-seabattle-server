package ch.hslu.ios.seabattle.commands;

import ch.hslu.ios.seabattle.game.Player;

public class ReadyCommand extends PlayerCommand {

	public ReadyCommand(Player source, String[] params) {
		super(PlayerCommandType.Ready, source, params);
	}

	public boolean isReady() {
		return (Boolean) fParams.get(0);
	}
	
	@Override
	protected void parseParams(String[] params) {
		fParams.add(Boolean.parseBoolean(params[1]));
	}
}
