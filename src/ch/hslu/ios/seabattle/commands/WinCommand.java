package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

public class WinCommand extends ServerCommand {
	private final boolean fWin;

	public WinCommand(boolean win) {
		super(ServerCommandType.Win);
		fWin = win;
	}
	
	@Override
	public ArrayList<Object> getParams() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(fWin);
		
		return list;
	}

}
