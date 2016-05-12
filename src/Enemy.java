
public class Enemy {
	protected int points;
	protected int speed;
	
	public Enemy(){
		speed = 50; //some constant change later
	}
	public Enemy(int speed) {
		this.speed = speed;
	}

	public boolean isHit() {
		return false;
	}
	
	public void disapper(){
		
	}
}
