import processing.core.PApplet;

public class Game extends PApplet {
	private int mode;
	public static final int INTRO = 0, PLAY = 1, PAUSED = 2, GAMEOVER = 3;
	private int size = 800;
	private Enemy e;
	
	public void setup() {
		size(size, size);
		mode = 0;
		e = new Enemy(this);
	}

	public void draw() {

		if (mode == INTRO) {
			displayIntro();
		} else if (mode == PLAY) {
			
		} else if (mode == PAUSED) {
			displayPaused();
		} else if (mode == GAMEOVER) {
			displayGameOver();
		}
	}

	private void displayGameOver() {
	
	}

	private void displayPaused() {
	
	}

	private void displayIntro() {
		background(100);
		fill(0, 50, 170, 180);
		textSize(50);
		text("Space Invaders", size / 4, size / 2);

	}

	public void mousePressed() {

	}
}
