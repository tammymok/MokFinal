import processing.core.PApplet;

public class Alien2 extends Enemy{

	public Alien2(PApplet p){
		super(p);
		points = 30; // points for second level of enemies
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
