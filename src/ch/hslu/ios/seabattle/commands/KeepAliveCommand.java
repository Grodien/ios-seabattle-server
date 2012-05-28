package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

public class KeepAliveCommand extends ServerCommand {

	
	public KeepAliveCommand() {
		super(ServerCommandType.KeepAlive);
	}
	
	@Override
	public ArrayList<Object> getParams() {
		return new ArrayList<Object>();
	}

}
