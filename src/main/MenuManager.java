package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import graphics.Textures;

public class MenuManager {

	private Textures texture;
	private Game game;

	private int currentOption = 0;
	private String[] options = { "PLAY", "QUIT" };

	private Font fnt0 = new Font("arial", Font.BOLD, 50);
	private Font fnt2 = new Font("arial", Font.BOLD, 30);
	private Font fnt3 = new Font("arial", Font.PLAIN, 10);

	public MenuManager(Textures texture, Game game) {
		this.texture = texture;
		this.game = game;
	}

	public void render(Graphics g) {

		switch (game.State) {
		case MENU:
			g.setFont(fnt0);
			g.setColor(Color.WHITE);
			g.drawString("SPACE SHOOTER", 200, 100);

			g.setFont(fnt2);
			g.setColor(Color.BLACK);

			g.drawImage(texture.button, 350, 200, null);
			g.drawString(options[0], 350 + 10, 200 + 25);

			g.drawImage(texture.button, 350, 300, null);
			g.drawString(options[1], 350 + 15, 300 + 25);
			
			g.setFont(fnt3);
			g.setColor(Color.GRAY);
			g.drawString("Developed by Vitor Martins, 2017", 320, 590);

			// PLAY BUTTON
			if (currentOption == 0) {
				g.drawImage(texture.selector, 300, 205, null);
			}
			// QUIT
			else if (currentOption == 1) {
				g.drawImage(texture.selector, 300, 305, null);
			}
			break;

		case DIFFICULTYMENU:
			g.setFont(fnt0);
			g.setColor(Color.WHITE);
			g.drawString("SELECT DIFFICULTY", 180, 100);

			g.setFont(fnt2);
			g.setColor(Color.BLACK);

			g.drawImage(texture.button, 350, 200, null);
			g.drawString("EASY", 350 + 10, 200 + 25);

			g.drawImage(texture.button, 350, 300, null);
			g.drawString("HARD", 350 + 8, 300 + 25);
			
			g.setFont(fnt3);
			g.setColor(Color.GRAY);
			g.drawString("Developed by Vitor Martins, 2017", 320, 590);

			// PLAY BUTTON
			if (currentOption == 0) {
				g.drawImage(texture.selector, 300, 205, null);
			}
			// QUIT
			else if (currentOption == 1) {
				g.drawImage(texture.selector, 300, 305, null);
			}
			break;
			
		case GAME:
			g.setFont(fnt2);
			g.setColor(Color.WHITE);
			g.drawString("SCORE: " + game.getScore(), 20, 40);
			
			break;

		case GAMEOVER:
			g.setFont(fnt0);
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER", 250, 100);

			g.setFont(fnt2);
			g.drawString("FINAL SCORE: " + game.getScore(), 275, 150);

			HighScore highScore = new HighScore(game);
			if (game.getScore() > game.getHighScoreCount() && game.getScore() > highScore.getScore()) {
				game.setHighScoreCount(game.getScore());
				highScore.setScore(game.getHighScoreCount());
			} else if (game.getScore() == 0 && highScore.getScore() == 0 && game.getHighScoreCount() == 0) {
				highScore.setScore(game.getScore());
			}

			g.setColor(Color.RED);
			g.drawString("HIGH SCORE: " + highScore.getScore(), 280, 200);

			g.setFont(fnt2);
			g.setColor(Color.BLACK);

			g.drawImage(texture.button, 350, 300, null);
			g.drawString(options[0], 350 + 10, 300 + 25);

			g.drawImage(texture.button, 350, 400, null);
			g.drawString(options[1], 350 + 15, 400 + 25);
			
			g.setFont(fnt3);
			g.setColor(Color.GRAY);
			g.drawString("Developed by Vitor Martins, 2017", 320, 590);

			// PLAY BUTTON
			if (currentOption == 0) {
				g.drawImage(texture.selector, 300, 305, null);
			}
			// QUIT
			if (currentOption == 1) {
				g.drawImage(texture.selector, 300, 405, null);
			}
			
			break;
		}

		if (currentOption < 0) {
			currentOption = 1;
		} else if (currentOption > 1) {
			currentOption = 0;
		}
	}

	public int getCurrentOption() {
		return currentOption;
	}

	public void setCurrentOption(int currentOption) {
		this.currentOption = currentOption;
	}
}
