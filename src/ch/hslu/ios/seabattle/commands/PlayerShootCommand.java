package ch.hslu.ios.seabattle.commands;

import ch.hslu.ios.seabattle.game.Player;

public class PlayerShootCommand extends PlayerCommand {

	public PlayerShootCommand(Player source, String[] params) {
		super(PlayerCommandType.PlayerShoot, source, params);
	}

	public int getPosX() {
		return (Integer) fParams.get(0);
	}
	
	public int getPosY() {
		return (Integer) fParams.get(1);
	}

	@Override
	protected void parseParams(String[] params) {
		fParams.add(Integer.parseInt(params[1]));
		fParams.add(Integer.parseInt(params[2]));
	}
}
