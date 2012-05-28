package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

public class PartialUpdateCommand extends ServerCommand {

	private int fPosX;
	private int fPosY;
	private int fNewValue;
	private boolean fMyField;
	private boolean fMyTurn;

	public PartialUpdateCommand(int posX, int posY, int newValue, boolean myField, boolean myTurn) {
		super(ServerCommandType.PartialUpdate);
		fPosX = posX;
		fPosY = posY;
		fNewValue = newValue;
		fMyField = myField;
		fMyTurn = myTurn;
	}

	@Override
	public ArrayList<Object> getParams() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(fPosX);
		list.add(fPosY);
		list.add(fNewValue);
		list.add(fMyField);
		list.add(fMyTurn);
		
		return list;
	}

	public int getPosX() {
		return fPosX;
	}

	public void setPosX(int posX) {
		fPosX = posX;
	}

	public int getPosY() {
		return fPosY;
	}

	public void setPosY(int posY) {
		fPosY = posY;
	}

	public int getNewValue() {
		return fNewValue;
	}

	public void setNewValue(int newValue) {
		fNewValue = newValue;
	}

	public boolean isMyField() {
		return fMyField;
	}

	public void setMyField(boolean myField) {
		fMyField = myField;
	}

	public boolean isMyTurn() {
		return fMyTurn;
	}

	public void setMyTurn(boolean myTurn) {
		fMyTurn = myTurn;
	}

}
