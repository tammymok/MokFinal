import processing.core.PApplet;

public class SpecialAlien extends Enemy {

	public SpecialAlien(int speed, PApplet p) {
		super(speed, p);
		points = 150; // points for the special alien
		width = 25;
	}

	@Override
	public void draw() {
		w.rectMode(Game.returnCENTER());

		if (dead == false) {
			w.fill(20, 20, 200); // some color change later
			w.rect(x, y, width, 20);
		}
	}
}
