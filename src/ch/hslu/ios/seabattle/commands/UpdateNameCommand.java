package ch.hslu.ios.seabattle.commands;

import ch.hslu.ios.seabattle.game.Player;

public class UpdateNameCommand extends PlayerCommand {

	private String fNewName;
	
	public UpdateNameCommand(Player source, String[] params) {
		super(PlayerCommandType.UpdateName, source, params);
	}
	
	@Override
	protected void parseParams(String[] params) {
		fNewName = params[1];
	}

	public String getNewName() {
		return fNewName;
	}

	public void setNewName(String newName) {
		fNewName = newName;
	}

}
