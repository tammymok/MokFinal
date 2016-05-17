import processing.core.PApplet;

public class Alien3 extends Enemy {

	public Alien3(PApplet p) {
		super(p);
		points = 30; // points for third level of aliens
	}

	@Override
	public void draw(int x, int y) {
		w.fill(100,20,20); //some color change later
		w.rect(x, y, 25, 25);
	}
}
