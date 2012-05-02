package ch.hslu.ios.seabattle.game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import ch.hslu.ios.seabattle.iAction;
import ch.hslu.ios.seabattle.commands.Command;
import ch.hslu.ios.seabattle.commands.PlayerCommand;
import ch.hslu.ios.seabattle.commands.PlayerShootCommand;
import ch.hslu.ios.seabattle.commands.ReadyCommand;
import ch.hslu.ios.seabattle.commands.ServerCommand;
import ch.hslu.ios.seabattle.commands.ServerSettingsCommand;
import ch.hslu.ios.seabattle.commands.PlayerCommand.PlayerCommandType;

/**
 * Handles the Player 
 * @author Thomas Bomatter
 */
public class Player extends Thread {

	private final Socket fsocket;
	private final iAction<Player> fclosedAction;
	private BufferedReader fInputStream;
	private BufferedWriter fOutputStream;
	private LinkedBlockingQueue<String> fOutputQueue;
	private Game fCurrentGame;
	private String fPlayerName;
	private boolean fIsConnected;
	private GameField fPlayerField;
	private boolean fIsReady;
	
	/**
	 * Creates a new Thread for a new Player.
	 * @param client The socket to the client
	 * @param closedAction The callback when the socket was closed
	 */
	public Player(Socket client, iAction<Player> closedAction) {
		fsocket = client;
		fclosedAction = closedAction;
		fCurrentGame = null;
		fPlayerName = "UNKOWN";
		fIsConnected = true;
		fPlayerField = null;
		fOutputQueue = new LinkedBlockingQueue<>();
	}
	
	public GameField getPlayerField() {
		return fPlayerField;
	}

	public void setPlayerField(GameField playerField) {
		fPlayerField = playerField;
	}

	public boolean isConnected() {
		return fIsConnected;
	}
	
	public String getPlayerName() {
		return fPlayerName;
	}
	
	public void setPlayerName(String playerName) {
		fPlayerName = playerName;
	}
	
	public Game getCurrentGame() {
		return fCurrentGame;
	}
	
	public void setCurrentGame(Game currentGame) {
		fCurrentGame = currentGame;
	}
	
	public void sendCommand(ServerCommand command) {
		fOutputQueue.offer(command.toString());
	}
	
	public boolean isIsReady() {
		return fIsReady;
	}

	public void setIsReady(boolean isReady) {
		fIsReady = isReady;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Handler for client (" + fsocket.getInetAddress().toString() + ") started!");
			fInputStream = new BufferedReader(new InputStreamReader(fsocket.getInputStream()));
			fOutputStream = new BufferedWriter(new OutputStreamWriter(fsocket.getOutputStream()));
			
			while (!fsocket.isClosed()) {
				
				if (fInputStream.ready()) {
					String[] cmd = fInputStream.readLine().split(Command.PARAM_SEPERATOR);
					
					PlayerCommandType type = PlayerCommandType.valueOf(cmd[0]);
					
					switch (type) {
						case GetServerSettings:
							sendCommand(new ServerSettingsCommand());
							break;
						case PlayerShoot:
							if (fCurrentGame != null) {
								fCurrentGame.handleCommand(new PlayerShootCommand(this, cmd));
							}
							break;
						case Ready:
							if (fCurrentGame != null) {
								fCurrentGame.handleCommand(new ReadyCommand(this, cmd));
							}
							break;
					}
					
				}
				/*
				try {
					Object obj = finputStream.readObject();
					System.out.println("Received data from client " + fsocket.getInetAddress().toString());
					fCommandReceivedAction.perform(obj);
				} catch (ClassNotFoundException e) {		
					System.out.println("Failed deserializing data from client " + fsocket.getInetAddress().toString());
					e.printStackTrace();
				}*/
				
				if (!fOutputQueue.isEmpty()) {
					String message = fOutputQueue.poll();
					if (message != null)
						fOutputStream.write(message);
				}
			}
			System.out.println("Connection to client (" + fsocket.getInetAddress().toString() + ") closed!");
		} catch (IOException e) { 
			System.out.println("Error occured! Client (" + fsocket.getInetAddress().toString() + ") will be disconnected");
		} finally {
			if (fInputStream != null) {
				try {
					fInputStream.close();
				} catch (IOException e) {}
			}
			fIsConnected = false;
			fclosedAction.perform(this);
		}
	}
}
