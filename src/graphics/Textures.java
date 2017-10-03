package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import main.Game;

public class Textures {

	private BufferedImage gameBackground = null;
	private BufferedImage menuBackground = null;
	private BufferedImage menu = null;

	public BufferedImage button;
	public BufferedImage selector;

	private BufferedImage playerShip = null;
	private BufferedImage projectile = null;
	private BufferedImage asteroid = null;

	private SpriteSheet menuSheet = null;

	public Textures(Game game) {

		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			menu = loader.loadImage("/menusheet.png");
			menuBackground = loader.loadImage("/menubg.png");
			gameBackground = loader.loadImage("/gamebg.png");
			playerShip = loader.loadImage("/playership.png");
			projectile = loader.loadImage("/projectile.png");
			asteroid = loader.loadImage("/asteroid.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		menuSheet = new SpriteSheet(menu);

		getTextures();
	}

	public void getTextures() {
		button = menuSheet.grabImage(0, 0, 100, 30);
		selector = menuSheet.grabImage(100, 0, 37, 26);
	}

	public BufferedImage getGameBackground() {
		return gameBackground;
	}

	public BufferedImage getMenuBackground() {
		return menuBackground;
	}

	public BufferedImage getMenu() {
		return menu;
	}

	public BufferedImage getPlayerShip() {
		return playerShip;
	}

	public BufferedImage getProjectile() {
		return projectile;
	}

	public BufferedImage getAsteroid() {
		return asteroid;
	}

	public SpriteSheet getMenuSheet() {
		return menuSheet;
	}

}
