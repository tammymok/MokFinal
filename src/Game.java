import java.text.DecimalFormat;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

//check enemy boolean dead

public class Game extends PApplet {
	private static final int ROWS_OF_ENEMIES = 4;
	private static final int COLS_OF_ENEMIES = 10;
	private static final int TIMER_CONSTANT = 10;/*
													 * private static final int
													 * UP = 0; private static
													 * final int RIGHT = 1, LEFT
													 * = 2, SPACE = 3, P = 4;
													 */
	private static final int TIME_ALLOWED_TO_PLAY = 60; // one minute before
														// game ends
	public static final int SPEED_ENEMIES = 5;
	private int mode;
	public static final int INTRO = 0, PLAY = 1, PAUSED = 2, GAMEOVER = 3, LOSTLIFE = 4;
	private int size = 800;
	private Enemy enemies[][];
	private Player player;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	private Boolean[] keysPressed = { false, false, false, false, false }; // didn't have time to finish this
	private Boolean firstSetUp = true;
	private PImage playerImage;
	private double startTime;
	private double seconds;
	private int specialAlienThreshold = 5;
	private ArrayList<Enemy> specialAliens = new ArrayList<Enemy>();
	private int enemyBulletThreshold = 1;
	private int countdown;
	private PImage life;

	public void setup() {
		size(size, size);
		imageMode(CENTER);
		mode = INTRO;
		enemies = new Enemy[ROWS_OF_ENEMIES][COLS_OF_ENEMIES];
		for (int row = 0; row < ROWS_OF_ENEMIES; row++) {
			for (int col = 0; col < COLS_OF_ENEMIES; col++) {
				if (row == 0) {
					enemies[row][col] = new Alien4(this);
				} else if (row == 1) {
					enemies[row][col] = new Alien3(this);
				} else if (row == 2) {
					enemies[row][col] = new Alien2(this);
				} else if (row == 3) {
					enemies[row][col] = new Alien1(this);
				}
			}
		}
		player = new Player(this, size / 2, 7 * size / 8);
		playerImage = loadImage("../assets/player.png");
		life = loadImage("../assets/player.png");
	}

	int timer = 0;

	public void draw() {

		if (mode == INTRO) { // intro
			displayIntro();
			countdown = 3 + millis();
		}
		if (mode == PLAY) { // play
			if (firstSetUp == true) {
				displayEnemies();
				firstSetUp = false;
				startTime = millis() / 1000;
			}

			seconds = TIME_ALLOWED_TO_PLAY + startTime - millis() / (double) 1000;
			displayPlayScreen(); // set updisplayScore();
			displayTime();
			displayScore();
			displayLives();

			areEnemiesShot(); // checks if enemies were shot and if yes, sets
								// them to null
			createSpecialAlienIfPossible();
			makeEnemiesMove();
			enemiesAddBullets();
			enemiesShootBullets();

			isPlayerShot();
			
			removeBullets();
			Bullet b = null;
			if (keyPressed) {
				if (key == CODED) {
					int x = player.getX();
					int y = player.getY();
					if (keyCode == UP) {
						if (timer == 0) {
							b = new Bullet(x, y, this);
							bullets.add(b);
							timer = TIMER_CONSTANT;
						} else {
							timer--;
						}

					} else if (keyCode == RIGHT) {
						player.setX(x += 4);
						displayPlayer();
					} else if (keyCode == LEFT) {
						player.setX(x -= 4);
						displayPlayer();
					}
				}
				if (key == ' ') {
					if (timer == 0) {
						b = new Bullet(player.getX(), player.getY(), this);
						bullets.add(b);
						timer = TIMER_CONSTANT;
					} else {
						timer--;
					}
				}

			}
			shootBullets();

			checkForPaused();
			if (TIME_ALLOWED_TO_PLAY - millis() / (double) 1000 <= 0) {
				mode = GAMEOVER;
			}

		} else if (mode == PAUSED) {
			displayPaused();
		} else if (mode == GAMEOVER) {
			displayGameOver();
		} else if (mode == LOSTLIFE) {
			background(0);
			fill(255, 255, 255);
			textAlign(CENTER);
			textSize(50);
			text("YOU LOST A LIFE!", size / 2, size / 3);
			fill(0, 230, 20);
			text("Lives Remaining: " + player.getLives(), size / 2, 2 * size / 5);

			fill(255, 255, 255);
			int num = 3 - ((millis() - countdown) / 1000);
			text("Starting in " + num + " seconds", size / 2, 3 * size / 4);

			if (num <= 0)
				mode = PLAY;
		}

		stroke(0);
	}

	public static int returnCENTER() {
		return CENTER;
	}

	private void removeBullets() {
		for(int i = 0; i< enemyBullets.size();i++){
			if (enemyBullets.get(i).getY() >= 7 * size / 8) {
				enemyBullets.remove(i);
			}
		}
	}

	private void createSpecialAlienIfPossible() {
		int random = (int) (Math.random() * 10000);
		if (random < specialAlienThreshold) {
			SpecialAlien a = new SpecialAlien(4, this); // 4 is the speed
			a.setY(150);
			specialAliens.add(a);
		}
	}

	private void moveSpecialAliens() {
		for (Enemy e : specialAliens) {
			e.move();
			e.draw();
			if (e.getX() > size) {
				e.setDead(true);
			}
		} 
	}

	private void displayTime() {
		textSize(20);
		textAlign(CENTER);
		fill(0, 30, 255);
		text(new DecimalFormat("##.#").format(seconds) + "s left", 5 * size / 8, 100); // formats the double, displays up to tenths
	}

	private int getRightMostColumn() {
		int col = 0;
		for (int r = 0; r < enemies.length; r++) {
			for (int c = 0; c < enemies[0].length; c++) {
				if (enemies[r][c].getX() != 0 && c > col) {
					col = c;
				}
			}
		}
		return col;
	}

	private int getLeftMostColumn() {
		int col = enemies.length - 1;
		for (int r = 0; r < enemies.length; r++) {
			for (int c = 0; c < enemies[0].length; c++) {
				if (enemies[r][c].getX() != 0 && c < col) {
					col = c;
				}
			}
		}
		return col;
	}

	private int getRightRow() {
		for (int r = 0; r < enemies.length; r++) {
			if (enemies[r][getRightMostColumn()].getX() != 0) {
				return r;
			}
		}
		return enemies.length - 1;
	}

	private int getLeftRow() {
		for (int r = 0; r < enemies.length; r++) {
			if (enemies[r][getLeftMostColumn()].getX() != 0) {
				return r;
			}
		}
		return enemies.length - 1;
	}

	private void displayScore() {
		textAlign(CENTER);
		textSize(50);
		fill(0, 30, 255);
		text(player.getTotalPoints(), 7 * size / 8, 100);
	}

	private void makeEnemiesMove() { // fix removing edge causes enemies to run
										// away
		int xVal = enemies[getRightRow()][getRightMostColumn()].getX(); // checking
																		// right
																		// side
		if (Math.abs(xVal - size) < 25) {
			for (int r = 0; r < enemies.length; r++) {
				for (int c = 0; c < enemies[0].length; c++) {
					enemies[r][c].reverseDirection();
				}
			}
		} else if (Math.abs(enemies[getLeftRow()][getLeftMostColumn()].getX() - 0) < 25) { // checking
																							// left
																							// side
			for (int r = 0; r < enemies.length; r++) {
				for (int c = 0; c < enemies[0].length; c++) {
					enemies[r][c].reverseDirection();
				}
			}
		}

		for (int r = 0; r < enemies.length; r++) {
			for (int c = 0; c < enemies[0].length; c++) {
				enemies[r][c].move();
			}
		}
		moveSpecialAliens();

	}

	private void areEnemiesShot() {
		for (int r = 0; r < enemies.length; r++) {
			for (int c = 0; c < enemies[0].length; c++) {
				int x = enemies[r][c].getX();
				int y = enemies[r][c].getY();
				if (isBulletOver(x, y, enemies[r][c].getWidth(), bullets)) {
					enemies[r][c].setDead(true);
					player.addToTotalPoints(enemies[r][c].getPoints());

				}
			}
		}
		for (Enemy e : specialAliens) {
			if (isBulletOver(e.getX(), e.getY(), e.getWidth(), bullets)) {
				e.setDead(true);
				player.addToTotalPoints(e.getPoints());
			}
		}
	}

	private void displayLives() {
		imageMode(CENTER);
		int x = 50;
		for (int i = 0; i < player.getLives(); i++) {
			image(life, x, 50);
			x += 50;
		}
	}

	private void isPlayerShot() {
		if (isBulletOver(player.getX(), player.getY(), player.getWidth(), enemyBullets)) {
			player.decreaseLives();
			if (player.getLives() <= 0)
				mode = GAMEOVER;
			else {
				countdown = 3 + millis();
				mode = LOSTLIFE;
			}
		}
	}

	private void shootBullets() {
		for (Bullet bullet : bullets) {
			bullet.setY(bullet.getY() - 9);
			bullet.draw();
		}
	}

	private void enemiesAddBullets() {
		for (int r = 0; r < enemies.length; r++) {
			for (int c = 0; c < enemies[0].length; c++) {
				int random = (int) (Math.random() * 1250);
				if (random < enemyBulletThreshold && enemies[r][c].getX() != 0) {
					Bullet b = new Bullet(enemies[r][c].getX(), enemies[r][c].getY(), this);
					enemyBullets.add(b);
				}
			}
		}
	}

	private void enemiesShootBullets() {
		for (Bullet b : enemyBullets) {
			b.setY(b.getY() + 7);
			b.draw();
		}
	}

	private void displayGameOver() {
		background(0);
		fill(255, 255, 255);
		textSize(50);
		textAlign(CENTER);
		text("GAME OVER", size / 2, size / 2);

		fill(0, 230, 20);
		text("Your score:" + player.getTotalPoints(), size / 2, 3 * size / 4);

		if (player.getLives() <= 0) {
			fill(255, 30, 0);
			text("YOU DIED TOO MANY TIMES!", size / 2, size / 3);
		} else {
			text("TIME'S UP", size / 2, size / 3);
		}
	}

	private void checkForPaused() {
		if (keyPressed) {
			if (key == 'p' || key == 'P') {
				mode = PAUSED;
				displayPaused();
			}
		}
	}

	private void displayPaused() {
		background(0);
		fill(255, 255, 255);
		textSize(60);
		textAlign(CENTER);
		text("PAUSED", size / 2, size / 2);
		rectMode(CENTER);
		int x = size / 2;
		int y = 3 * size / 4;
		int width = size / 2;
		noFill();
		stroke(0, 230, 20);
		rect(x, y, width, 80);
		fill(0, 230, 20);
		textSize(40);
		text("Click To Play", x, y + 30);
		fill(0, 20, 255);
		textSize(30);
		text("Current Score: " + player.getTotalPoints(), x, y - 110);
		text("Time Left: " + new DecimalFormat("##.#").format(seconds) + "s.", x, y - 80);
		if ((isOverRect(x, y, width) == true) && mousePressed) {
			mode = PLAY;
		}
	}

	private void displayPlayer() { // fix
		image(playerImage, player.getX(), player.getY());
	}

	private void displayPlayScreen() {
		background(0);
		rectMode(CENTER);
		displayPlayer();
		for (int r = 0; r < enemies.length; r++) {
			for (int c = 0; c < enemies[0].length; c++) {
				enemies[r][c].draw();
			}
		}
		textAlign(CENTER);
		fill(0, 30, 255);
		textSize(20);
		text("Press P for pause", size / 6, 100);
	}

	private void displayIntro() {
		background(0);
		fill(0, 30, 255);
		textSize(50);
		textAlign(CENTER);
		int x = size / 2;
		text("Space Invaders", x, x); // both x b/c center
		int diameter = 200;
		int y = 3 * size / 4;
		ellipseMode(CENTER);
		ellipse(x, y, diameter, diameter);
		textAlign(CENTER);
		fill(255, 255, 255);
		text("START", x, y + 20);
		if ((isOverCircle(x, y, diameter) == true) && mousePressed) {
			mode = PLAY;
		}

	}

	private void displayEnemies() {
		int x = (size / 10) - 35, y = size - 350;
		for (int r = 0; r < enemies.length; r++) {
			for (int c = 0; c < enemies[0].length; c++) {
				enemies[r][c].setX(x);
				enemies[r][c].setY(y);
				enemies[r][c].draw();
				x += size / 15;
			}
			x = (size / 10) - 35;
			y -= 75;
		}
	}

	boolean isBulletOver(int x, int y, int width, ArrayList<Bullet> bullets) {
		for (Bullet b : bullets) {
			double xDist = b.getX() - x;
			double yDist = b.getY() - y;
			if (Math.sqrt((xDist * xDist) + (yDist * yDist)) < width / 2) {
				bullets.remove(b);
				return true;
			}
		}
		return false;
	}

	boolean isOverRect(int x, int y, int width) {
		double xDist = x - mouseX;
		double yDist = y - mouseY;
		if (Math.sqrt((xDist * xDist) + (yDist * yDist)) < width / 2) {
			return true;
		} else {
			return false;
		}
	}

	boolean isOverCircle(int x, int y, int diameter) {
		double xDist = x - mouseX;
		double yDist = y - mouseY;
		if (Math.sqrt((xDist * xDist) + (yDist * yDist)) < diameter / 2) {
			return true;
		} else {
			return false;
		}
	}

}
