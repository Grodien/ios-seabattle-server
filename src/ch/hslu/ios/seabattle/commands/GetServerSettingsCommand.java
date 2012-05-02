package ch.hslu.ios.seabattle.commands;

import ch.hslu.ios.seabattle.game.Player;

public class GetServerSettingsCommand extends PlayerCommand {

	public GetServerSettingsCommand(Player source, String[] params) {
		super(PlayerCommandType.GetServerSettings, source, params);
	}

	@Override
	protected void parseParams(String[] params) {
		// Has no Params
	}
}
