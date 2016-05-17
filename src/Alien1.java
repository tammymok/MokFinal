import processing.core.PApplet;

public class Alien1 extends Enemy{

	public Alien1(PApplet p){
		super(p);
		points = 10; // points for first level of aliens
	}
	@Override
	public void draw(int x, int y) {
		w.fill(100,20,20); //some color change later
		w.rect(x, y, 25, 25);
	}
}
