package elements;

import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Textures;

public class Projectile {

	private final double SPEED = 7;

	private double x;
	private double y;

	private Textures texture;

	public Projectile(double x, double y, Textures texture) {
		this.x = x;
		this.y = y;
		this.texture = texture;
	}

	public void update() {
		y -= SPEED;
	}

	public void paint(Graphics g) {
		g.drawImage(texture.getProjectile(), (int) x, (int) y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 9, 37);
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
