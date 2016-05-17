import processing.core.PApplet;

public class SpecialAlien extends Enemy {
	
	public SpecialAlien(int speed, PApplet p) {
		super(speed, p);
		points = 150; // points for the special alien
	}

	@Override
	public void draw(int x, int y) {
		w.fill(100,20,20); //some color change later
		w.rect(x, y, 25, 25);
	}
}
