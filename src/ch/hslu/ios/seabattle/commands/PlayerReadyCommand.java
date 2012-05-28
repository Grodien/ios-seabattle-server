package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

public class PlayerReadyCommand extends ServerCommand {

	private final boolean fRdyState;
	private final boolean fStartGame;
	private final boolean fMyTurn;

	public PlayerReadyCommand(boolean rdyState, boolean startGame, boolean myTurn) {
		super(ServerCommandType.PlayerReady);
		fRdyState = rdyState;
		fStartGame = startGame;
		fMyTurn = myTurn;
	}
	
	@Override
	public ArrayList<Object> getParams() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(fRdyState);
		list.add(fStartGame);
		list.add(fMyTurn);
		
		return list;
	}

}
