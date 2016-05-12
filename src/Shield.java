
public class Shield {
	private int hp;

	public Shield() {
		hp = 25; // default hp for now, change it later
	}

	public boolean isHit() {
		return false;
	}

	public int getHp() {
		return hp;
	}

	public void decreaseHp() {
		hp--;
	}
}
