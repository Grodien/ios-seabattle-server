package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

public abstract class ServerCommand extends Command {
	private final ServerCommandType fCommandType;

	public enum ServerCommandType {
		PartialUpdate,
		FullUpdate,
		PlayerReady,
		ServerSettings,
		Error,
		Win,
		PlayerFound,
		KeepAlive
	}
	
	public ServerCommand(ServerCommandType commandType) {
		fCommandType = commandType;	
	}
	
	public abstract ArrayList<Object> getParams();
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(fCommandType.ordinal());
		
		for (Object param : getParams()) {
			builder.append(PARAM_SEPERATOR);
			builder.append(param);
		}
		
		builder.append(LINE_BREAK);
		
		return builder.toString();
	}
}
