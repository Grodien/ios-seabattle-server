package ch.hslu.ios.seabattle.commands;

import java.util.ArrayList;

public class FullUpdateCommand extends ServerCommand {

	private boolean fMyField;
	private boolean fMyTurn;
	private String fFieldData;

		public FullUpdateCommand(String fieldData, boolean myField, boolean myTurn) {
			super(ServerCommandType.FullUpdate);
			fFieldData = fieldData;
			fMyField = myField;
			fMyTurn = myTurn;
		}

	@Override
	public ArrayList<Object> getParams() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(fFieldData);
		list.add(fMyField);
		list.add(fMyTurn);
		
		return list;
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

	public String getFieldData() {
		return fFieldData;
	}

	public void setFieldData(String fieldData) {
		fFieldData = fieldData;
	}

}
