import processing.core.PApplet;

public class Enemy {
	protected int points;
	protected int speed;
	private PApplet w;
	
	public Enemy(PApplet p){
		speed = 50; //some constant change later
		w = p;
	}
	public Enemy(int speed) {
		this.speed = speed;
	}

	public boolean isHit() {
		return false;
	}
	
	public void disapper(){
		
	}
	
	public void draw() {
		w.rect(10, 10, 10, 10);
	}
}
