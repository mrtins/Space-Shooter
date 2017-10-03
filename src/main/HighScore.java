package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HighScore {

	private int highScore;

	private Game game;

	public HighScore(Game game) {
		this.game = game;
	}

	public void setScore(int highScore) {
		this.highScore = highScore;
		try {
			FileOutputStream saveFile = new FileOutputStream("SpaceShooterData.dat");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			save.writeObject(game.getHighScoreCount());
			save.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getScore() {
		try {
			FileInputStream saveFile = new FileInputStream("SpaceShooterData.dat");
			ObjectInputStream save = new ObjectInputStream(saveFile);

			highScore = (int) save.readObject();
			save.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return highScore;

	}

}
