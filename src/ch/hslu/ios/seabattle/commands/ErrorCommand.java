package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

public class ErrorCommand extends ServerCommand {

	private final Severity fSeverity;
	private final String fMessage;

	public enum Severity {
		Message,
		Warning,
		Fatal
	}
	
	public ErrorCommand(Severity severity, String message) {
		super(ServerCommandType.Error);
		fSeverity = severity;
		fMessage = message;
	}
	
	@Override
	public ArrayList<Object> getParams() {
		ArrayList<Object> list = new ArrayList<>();
		list.add(fSeverity.ordinal());
		list.add(fMessage);
		
		return list;
	}

}
