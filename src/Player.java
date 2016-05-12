
public class Player {
	private int lives;
	private int totalPoints;

	public Player() {
		lives = 3;
	}

	public void shoot() {

	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void addToTotalPoints(int num) {
		totalPoints += num;
	}

	public int getLives(){
		return lives;
	}
	public void decreaseLives(){
		lives--;
	}
	public boolean isHit() {
		return false;
	}
}
