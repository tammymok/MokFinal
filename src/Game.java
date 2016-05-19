import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PShape;

public class Game extends PApplet {
	private static final int ROWS_OF_ENEMIES = 4;
	private static final int COLS_OF_ENEMIES = 10;
	private static final int TIMER_CONSTANT = 10;
	
	private int mode;
	public static final int INTRO = 0, PLAY = 1, PAUSED = 2, GAMEOVER = 3;
	private int size = 800;
	private Enemy[][] enemies[][];
	private Player player;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public void setup() {
		size(size, size);
		mode = INTRO;
		Enemy[][] enemies = new Enemy[ROWS_OF_ENEMIES][COLS_OF_ENEMIES];
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

	}

	public void inCollision() {

	}

	int timer = 0;

	public void draw() {

		if (mode == INTRO) { // intro
			displayIntro();
		}
		if (mode == PLAY) { // play

			displayPlayScreen(); // set up

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
						player.draw(x += 2, y); // fix
						player.setX(x += 2);
					} else if (keyCode == LEFT) {
						player.draw(x--, y);
						player.setX(x -= 2);
					}
				}
				if (key == ' ') {
					if (timer == 0) {
						b = new Bullet(player.getX(), player.getY(), this);
						bullets.add(b);
						timer = TIMER_CONSTANT ;
					} else {
						timer--;
					}
				}

			}
			shootBullets();

			checkForPaused();

		} else if (mode == PAUSED) {
			displayPaused();
		} else if (mode == GAMEOVER) {
			displayGameOver();
		}

		stroke(0);
	}

	private void shootBullets() {
		for (Bullet bullet : bullets) {
			int bulletX = bullet.getX();
			int bulletY = bullet.getY();
			bullet.draw(bulletX, bulletY -= 5);
			bullet.setX(bulletX);
			bullet.setY(bulletY);
		}
	}

	private void displayEnemies() {

	}

	private void displayGameOver() {
		background(0);
		fill(255, 255, 255);
		textSize(50);
		textAlign(CENTER);
		text("GAME OVER", size / 2, size / 2);
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
		if ((isOverRect(x, y, width) == true) && mousePressed) {
			mode = PLAY;
		}
	}

	private void displayPlayScreen() {
		background(0);
		rectMode(CENTER);
		player.draw(player.getX(), player.getY());
	}

	private void displayIntro() {
		background(0);
		fill(0, 20, 230);
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

	public void mousePressed() {

	}
}
