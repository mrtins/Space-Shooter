package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import elements.Controller;
import elements.Player;
import elements.Projectile;
import graphics.Textures;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private boolean running = false;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	private boolean shooting = false;
	private Random r = new Random();

	private int minEnemys = 2; // NUMERO MINIMO DE INIMIGOS NO COMEÇO DO JOGO
	private int maxEnemys = 5; // NUMERO MAXIMO DE INIMIGOS NO COMEÇO DO JOG
	private int enemyCount = 0; // CONTAGEM DE INIMIGOS NA TELA

	private int enemyKilled = 0;
	private int enemyLevel = 10;

	private int score = 0;
	private int highScoreCount = 0;

	private boolean keypressed = false;

	private Textures texture;
	private Controller controller;
	private Player player;
	private MenuManager menu;

	public static enum STATE {
		MENU, GAME, GAMEOVER, DIFFICULTYMENU
	};

	public static enum DIFFICULTY {
		EASY, HARD
	};

	public STATE State = STATE.MENU;
	public static DIFFICULTY Difficulty;

	public void init() {
		texture = new Textures(this);
		controller = new Controller(texture, this);
		player = new Player(350, 535, texture, controller, this);
		menu = new MenuManager(texture, this);

		addKeyListener(this);
	}

	public synchronized void start() {
		init();
		requestFocus();

		if (!running) {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	private synchronized void stop() {
		if (!running) {
			return;
		}

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		// int updates = 0;
		// int frames = 0;
		// long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				// updates++;
				delta--;
			}
			paint();
		}

		stop();
	}

	private void update() {
		if (State == STATE.GAME) {
			switch (Difficulty) {
			case EASY:
				// EASY MODE
				player.update();
				controller.update();

				if (enemyCount <= 0) {
					enemyCount = r.nextInt(maxEnemys - minEnemys + 1) + minEnemys;
					controller.createEnemy(enemyCount);
				}

				if (enemyKilled >= enemyLevel + 5) {
					enemyKilled = 0;
					enemyLevel += 5;

					minEnemys += 1;
					maxEnemys += 1;

					controller.setEnemySpeed(controller.getEnemySpeed() + 0.2);
					controller.createEnemy(enemyCount);
				}

				break;

			case HARD:
				// HARD MODE
				player.update();
				controller.update();

				if (enemyCount <= 0) {
					enemyCount = r.nextInt(maxEnemys - minEnemys + 1) + minEnemys;
					controller.createEnemy(enemyCount);
				}

				if (enemyKilled >= enemyLevel - 5) {
					enemyKilled = 0;
					enemyLevel += 5;

					minEnemys += 2;
					maxEnemys += 5;

					controller.setEnemySpeed(controller.getEnemySpeed() + 0.9);
					controller.createEnemy(enemyCount);
				}

				break;
			}

		}
	}

	private void paint() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		switch (State) {
		case MENU:
			g.drawImage(texture.getMenuBackground(), 0, 0, null);
			break;

		case DIFFICULTYMENU:
			g.drawImage(texture.getMenuBackground(), 0, 0, null);
			break;

		case GAME:
			g.drawImage(texture.getGameBackground(), 0, 0, null);
			player.paint(g);
			controller.paint(g);
			break;

		case GAMEOVER:
			g.drawImage(texture.getGameBackground(), 0, 0, null);
			break;
		}

		menu.render(g);

		g.dispose();
		bs.show();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (State == STATE.GAME) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.moveLeft();
				player.setMovingLeft(true);
				break;

			case KeyEvent.VK_D:
				player.moveRight();
				player.setMovingRight(true);
				break;

			case KeyEvent.VK_LEFT:
				player.moveLeft();
				player.setMovingLeft(true);
				break;

			case KeyEvent.VK_RIGHT:
				player.moveRight();
				player.setMovingRight(true);
				break;

			case KeyEvent.VK_SPACE:
				if (!shooting) {
					controller.addProjectile(new Projectile(player.getX() + 25, player.getY() - 30, texture));
					shooting = true;
				}
				break;
			}

		}

		if (State == STATE.MENU) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (!keypressed) {
					keypressed = true;
					menu.setCurrentOption(menu.getCurrentOption() - 1);
				}
				break;

			case KeyEvent.VK_DOWN:
				if (!keypressed) {
					keypressed = true;
					menu.setCurrentOption(menu.getCurrentOption() + 1);
				}
				break;

			case KeyEvent.VK_ENTER:
				if (!keypressed) {
					keypressed = true;
					if (menu.getCurrentOption() == 0) {
						// INICIA O JOGO
						State = STATE.DIFFICULTYMENU;
					} else if (menu.getCurrentOption() == 1) {
						System.exit(1);
					}
					break;
				}
			}
		}

		if (State == STATE.DIFFICULTYMENU) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (!keypressed) {
					keypressed = true;
					menu.setCurrentOption(menu.getCurrentOption() - 1);
				}
				break;

			case KeyEvent.VK_DOWN:
				if (!keypressed) {
					keypressed = true;
					menu.setCurrentOption(menu.getCurrentOption() + 1);
				}
				break;

			case KeyEvent.VK_ENTER:
				if (!keypressed) {
					keypressed = true;
					if (menu.getCurrentOption() == 0) {
						// INICIA O JOGO
						start();
						State = STATE.GAME;
						Difficulty = DIFFICULTY.EASY;
					} else if (menu.getCurrentOption() == 1) {
						start();
						State = STATE.GAME;
						Difficulty = DIFFICULTY.HARD;
					}
				}
				break;
			}
		}

		if (State == STATE.GAMEOVER) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (!keypressed) {
					keypressed = true;
					menu.setCurrentOption(menu.getCurrentOption() - 1);
				}
				break;

			case KeyEvent.VK_DOWN:
				if (!keypressed)
					if (!keypressed) {
						keypressed = true;
						menu.setCurrentOption(menu.getCurrentOption() + 1);
					}
				break;

			case KeyEvent.VK_ENTER:
				if (!keypressed) {
					keypressed = true;
					if (menu.getCurrentOption() == 0) {
						// INICIA O JOGO
						resetStats();
						start();
						State = STATE.GAME;
					}

					if (menu.getCurrentOption() == 1) {
						System.exit(1);
					}
				}
				break;
			}
		}

	}

	private void resetStats() {
		minEnemys = 2;
		maxEnemys = 5;
		enemyCount = 0;
		enemyKilled = 0;
		enemyLevel = 10;
		score = 0;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keypressed = false;

		if (State == STATE.GAME) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.stopLeft();
				break;

			case KeyEvent.VK_D:
				player.stopRight();
				break;

			case KeyEvent.VK_LEFT:
				player.stopLeft();
				break;

			case KeyEvent.VK_RIGHT:
				player.stopRight();
				break;

			case KeyEvent.VK_SPACE:
				shooting = false;
				break;
			}
		}

		if (State == STATE.MENU || State == STATE.DIFFICULTYMENU || State == STATE.GAMEOVER) {
			if (keypressed)
				keypressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public int getEnemyCount() {
		return enemyCount;
	}

	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}

	public int getEnemyKilled() {
		return enemyKilled;
	}

	public void setEnemyKilled(int enemyKilled) {
		this.enemyKilled = enemyKilled;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHighScoreCount() {
		return highScoreCount;
	}

	public void setHighScoreCount(int highScoreCount) {
		this.highScoreCount = highScoreCount;
	}

}
