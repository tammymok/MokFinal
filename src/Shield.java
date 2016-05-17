import processing.core.PApplet;

public class Shield {
	private int hp;
	private PApplet w;

	public Shield(PApplet p) {
		hp = 25; // default hp for now, change it later
		w = p;
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

	public void draw(int x, int y) {
		w.rect(x, y, 25, 10);
	}
}