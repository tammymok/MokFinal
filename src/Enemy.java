import processing.core.PApplet;

public class Enemy {
	protected int points;
	protected int speed;
	protected PApplet w;

	public Enemy(PApplet p) {
		speed = 50; // some constant change later
		w = p;
	}

	public Enemy(int speed, PApplet p) {
		this.speed = speed;
		w = p;
	}

	public boolean isHit() {
		return false;
	}

	public void disappear() {

	}

	public void draw(int x, int y) {
		w.fill(25, 100, 50); // some color change later
		w.rect(x, y, 25, 25);
	}
}
