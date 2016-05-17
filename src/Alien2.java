import processing.core.PApplet;

public class Alien2 extends Enemy{

	public Alien2(PApplet p){
		super(p);
		points = 20; // points for second level of enemies
		
	}
	@Override
	public void draw(int x, int y) {
		w.fill(100,20,20); //some color change later
		w.rect(x, y, 25, 25);
	}
}
