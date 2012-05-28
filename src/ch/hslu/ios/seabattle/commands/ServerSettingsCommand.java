package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

import ch.hslu.ios.seabattle.game.GameField;

public class ServerSettingsCommand extends ServerCommand {

	public ServerSettingsCommand() {
		super(ServerCommandType.ServerSettings);
	}

	@Override
	public ArrayList<Object> getParams() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(GameField.SIZE);
		list.add(GameField.SMALL_SHIP_COUNT);
		list.add(GameField.MEDIUM_SHIP_COUNT);
		list.add(GameField.BIG_SHIP_COUNT);
		list.add(GameField.HUGE_SHIP_COUNT);
		list.add(GameField.ULTIMATE_SHIP_COUNT);
		
		return list;
	}
}
