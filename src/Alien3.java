import processing.core.PApplet;

public class Alien3 extends Enemy {

	public Alien3(PApplet p) {
		super(p);
		points = 20; // points for third level of aliens
		width = 30;
	}

	@Override
	public void draw() {
		w.rectMode(Game.returnCENTER());

		if (dead == false) {
			w.fill(0, 30, 255); // some color change later
			w.rect(x, y, width, 20);
		}
	}
}
