package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

import ch.hslu.ios.seabattle.game.Player;

public abstract class PlayerCommand extends Command {
	private final PlayerCommandType fCommandType;
	
	private Player fSource;
	protected ArrayList<Object> fParams;
	
	public enum PlayerCommandType {
		PlayerShoot,
		GetServerSettings,
		Ready,
		UpdateName,
		RenewGameField,
		Disconnect,
		KeepAlive
	}
	
	public PlayerCommand(PlayerCommandType commandType, Player source, String[] params) {
		fCommandType = commandType;	
		fSource = source;
		fParams = new ArrayList<Object>();
		parseParams(params);
	}
	
	public PlayerCommandType getCommandType() {
		return fCommandType;
	}
	
	public Player getSource() {
		return fSource;
	}
	
	protected abstract void parseParams(String[] params);
}
