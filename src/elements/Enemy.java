package elements;

import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Textures;


public class Enemy {

	private double x;
	private double y;
	private double speedY = 3;

	private Textures texture;

	public Enemy(double x, double y, double speedY, Textures texture) {
		this.x = x;
		this.y = y;
		this.speedY = speedY;
		this.texture = texture;
	}

	public void update() {
		y += speedY;
	}

	public void paint(Graphics g) {
		// g.drawRect((int) x, (int) y, 50, 45); //RETANGULO DE COLISÃO

		g.drawImage(texture.getAsteroid(), (int) x, (int) y, null);

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 50, 45);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
