package elements;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import graphics.Textures;
import main.Game;

public class Controller {

	private ArrayList<Projectile> p = new ArrayList<Projectile>();
	private ArrayList<Enemy> e = new ArrayList<Enemy>();

	private Random r = new Random();

	private int enemyX;
	private int enemyY;
	private int minEnemyY = 200;
	private int maxEnemyY = 550;
	private double enemySpeed = 2;

	private Projectile projectile;
	private Enemy enemy;
	private Textures texture;
	private Game game;

	private boolean collide = false;

	public Controller(Textures texture, Game game) {
		this.texture = texture;
		this.game = game;
	}

	public void update() {
		for (int i = 0; i < p.size(); i++) {
			projectile = p.get(i);
			projectile.update();

		}

		for (int i = 0; i < e.size(); i++) {
			enemy = e.get(i);
			enemy.update();

			for (int j = 0; j < p.size(); j++) {
				projectile = p.get(j);
				if (checkCollision()) {
					removeEnemy(enemy);
					removeProjectile(projectile);
					game.setEnemyKilled(game.getEnemyKilled() + 1);
					game.setEnemyCount(game.getEnemyCount() - 1);
					game.setScore(game.getScore() + 1);
				}

			}

		}

	}

	public void paint(Graphics g) {
		for (int i = 0; i < p.size(); i++) {
			projectile = p.get(i);

			if (projectile.getY() < -10) {
				removeProjectile(projectile);
			}

			projectile.paint(g);
		}

		for (int i = 0; i < e.size(); i++) {
			enemy = e.get(i);

			if (enemy.getY() > 600) {
				removeEnemy(enemy);
				game.setEnemyCount(game.getEnemyCount() - 1);
			}

			enemy.paint(g);
		}

	}

	public void createEnemy(int enemyCount) {
		for (int i = 0; i < enemyCount; i++) {
			enemyX = r.nextInt(760) + 1;
			enemyY = r.nextInt((maxEnemyY - minEnemyY + 1) + minEnemyY) * -1;
			double speed = enemySpeed;

			addEnemy(new Enemy(enemyX, enemyY, (int) speed, texture));
		}
	}

	public boolean checkCollision() {
		if (projectile.getBounds().intersects(enemy.getBounds())) {
			collide = true;
			return true;
		} else {
			collide = false;
			return false;
		}
	}

	public void addProjectile(Projectile block) {
		p.add(block);
	}

	public void removeProjectile(Projectile block) {
		p.remove(block);
	}

	public void addEnemy(Enemy block) {
		e.add(block);
	}

	public void removeEnemy(Enemy block) {
		e.remove(block);
	}

	public ArrayList<Projectile> getP() {
		return p;
	}

	public ArrayList<Enemy> getE() {
		return e;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public double getEnemySpeed() {
		return enemySpeed;
	}

	public void setEnemySpeed(double enemySpeed) {
		this.enemySpeed = enemySpeed;
	}

	public int getEnemyX() {
		return enemyX;
	}

	public void setEnemyX(int enemyX) {
		this.enemyX = enemyX;
	}

	public int getEnemyY() {
		return enemyY;
	}

	public void setEnemyY(int enemyY) {
		this.enemyY = enemyY;
	}

	public boolean isCollide() {
		return collide;
	}

	public void setCollide(boolean collide) {
		this.collide = collide;
	}

}
