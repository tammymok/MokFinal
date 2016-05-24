import processing.core.PApplet;

public class Alien4 extends Enemy {
	public Alien4(PApplet p) {
		super(p);
		points = 10; // points for third level of aliens
		width = 20;
	}

	@Override
	public void draw() {
		w.rectMode(Game.returnCENTER());

		if (dead == false) {
			w.fill(0, 30, 255); // some color change later
			w.rect(x, y, width, 15);
		}
	}
}