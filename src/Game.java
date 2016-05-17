import java.util.ArrayList;

import processing.core.PApplet;

public class Game extends PApplet {
	private static final int ROWS_OF_ENEMIES = 4;
	private static final int COLS_OF_ENEMIES = 10;

	private int mode;
	public static final int INTRO = 0, PLAY = 1, PAUSED = 2, GAMEOVER = 3;
	private int size = 800;
	private Enemy[][] enemies[][];
	private Player player;

	public void setup() {
		size(size, size);
		mode = 0;
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
		player = new Player(this, size/2, size/2);

	}

	public void inCollision() {

	}

	public void draw() {
		boolean bulletShot = false;

		if (mode == INTRO) { //intro
			displayIntro();
		} else if (mode == PLAY) { //play
			Bullet b = null;
			if (keyPressed) {
				if (key == CODED) {
					int x = player.getX();
					int y = player.getY();
					if (keyCode == UP) {
						bulletShot = true;
						b = new Bullet(x, y, this);
					} else if (keyCode == RIGHT) {
						player.draw(x+=2, y); //fix
						player.setX(x+=2);
					} else if (keyCode == LEFT) {
						player.draw(x--, y);
						player.setX(x-=2);
					}
				}
				
				if(key == 'p'||key == 'P'){
					mode = PAUSED;
					displayPaused();
				}
			}
			
			
			if(bulletShot == true){
				int bulletX = b.getX();
				int bulletY = b.getY();
				b.draw(bulletX, bulletY+=5);
			}
		} else if (mode == PAUSED) {
			displayPaused();
		} else if (mode == GAMEOVER) {
			displayGameOver();
		}

	
	}

	private void displayGameOver() {
		background(0);
		fill(255, 255, 255);
		textSize(50);
		text("GAME OVER", size / 4, size / 2);
	}

	private void displayPaused() {

	}

	private void displayIntro() {
		background(0);
		fill(0, 20, 200);
		textSize(50);
		textAlign(CENTER);
		text("Space Invaders", size / 2, size / 2);

	}

	public void mousePressed() {

	}
}
