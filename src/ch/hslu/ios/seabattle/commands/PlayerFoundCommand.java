package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

import ch.hslu.ios.seabattle.game.Player;

public class PlayerFoundCommand extends ServerCommand {
	private final Player fPlayer;

	public PlayerFoundCommand(Player player) {
		super(ServerCommandType.PlayerFound);
		fPlayer = player;
	}
	
	@Override
	public ArrayList<Object> getParams() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(fPlayer.getPlayerName());
		
		return list;
	}

}
