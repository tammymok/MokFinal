import processing.core.PApplet;

public class Enemy {
	protected int points;
	protected int speed;
	protected PApplet w;
	protected int x, y, width;
	protected boolean dead; 
	protected int direction; // 1 is right, -1 is left
	protected String name = "";

	public Enemy(PApplet p) {
		speed = 3; // some constant change later
		w = p;
		dead = false;
		direction = 1;
	}

	public Enemy(int speed, PApplet p) {
		this.speed = speed;
		w = p;
		dead = false;
		direction =1 ;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void reverseDirection(){
		direction *= -1; 
	}

	public int getPoints() {
		return points;
	}


	public boolean getDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		if (dead == false) {
			return x;
		}
		return 0;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		if (dead == false) {
			return y;
		}
		return 0;	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isHit() {
		return false;
	}

	public int getWidth() {
		return width;
	}

	public void move(){
		x+= speed*direction;
	}
	public String getImageName(){
		return name;
	}
	public void draw() {
		w.rectMode(Game.returnCENTER());
		if (dead == false) {
			w.fill(25, 100, 50); // some color change later
			w.rect(x, y, 25, 25);
		}
	}
}
