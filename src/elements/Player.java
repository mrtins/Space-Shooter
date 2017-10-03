package elements;

import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Textures;
import main.Game;
import main.Game.STATE;

public class Player {

	private final double SPEED = 10;

	private double x;
	private double y;
	private double speedX = 0;

	private boolean movingLeft = false;
	private boolean movingRight = false;

	private Textures texture;
	private Controller controller;
	private Game game;

	public Player(double x, double y, Textures texture, Controller controller, Game game) {
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.controller = controller;
		this.game = game;
	}

	public void update() {
		// Move a nave para esquerda, diminui 20 de x
		if (speedX < x) {
			x += speedX;
		}
		// Move a nave para direita quando ela estiver entre 0 e 19 pixels
		// horizontais (x) na tela
		else if (speedX > x) {
			x += speedX;
		}

		// Impede a nave de sair da tela
		if (x + speedX >= 750) {
			x = 750;
		} else if (x + speedX <= -2) {
			x = -2;
		}

		for (int i = 0; i < controller.getE().size(); i++) {
			Enemy tempEnemy = controller.getE().get(i);

			if (tempEnemy.getBounds().intersects(this.getBounds())) {
				game.State = STATE.GAMEOVER;
			}
		}

	}

	public void paint(Graphics g) {
		g.drawImage(texture.getPlayerShip(), (int) x, (int) y, null);
	}

	public void moveLeft() {
		speedX = -SPEED;
		movingLeft = true;
	}

	public void moveRight() {
		speedX = SPEED;
		movingRight = true;
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}

	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	public void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
			speedX = 0;
		}

		if (isMovingRight() == false && isMovingLeft() == true) {
			moveLeft();
		}

		if (isMovingRight() == true && isMovingLeft() == false) {
			moveRight();
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 60, 40);
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

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

}
