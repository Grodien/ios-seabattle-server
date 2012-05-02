package ch.hslu.ios.seabattle;

import ch.hslu.ios.seabattle.game.GameField;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = Server.getInstance();
		server.start();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*GameField field = new GameField();
		field.createRandomField();
		field.printInvisibleFields();
		field.createRandomField();
		field.printInvisibleFields();
		field.createRandomField();
		field.printInvisibleFields();
		field.createRandomField();
		field.printInvisibleFields();
		field.createRandomField();
		field.printInvisibleFields();
		field.createRandomField();
		field.printInvisibleFields();*/
	}
}
