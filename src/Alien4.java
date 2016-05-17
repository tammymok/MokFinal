import processing.core.PApplet;

public class Alien4  extends Enemy{
	public Alien4(PApplet p){
		super(p);
		points = 40; // points for third level of aliens
	}
	
	@Override
	public void draw(int x, int y) {
		w.fill(100,20,20); //some color change later
		w.rect(x, y, 25, 25);
	}
}