package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

public class ErrorCommand extends ServerCommand {
	public static final int ERROR_CODE_PLAYER_DISCONNECTED = 1;
	
	private final int fErrorCode;
	
	public ErrorCommand(int errorCode) {
		super(ServerCommandType.Error);
		fErrorCode = errorCode;
	}
	
	@Override
	public ArrayList<Object> getParams() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(fErrorCode);
		
		return list;
	}

}
