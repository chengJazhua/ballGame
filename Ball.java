package ballGame;

public class Ball {
	int speed;
	double angle;
	int locX; 
	int size;
	int locY;
	
	public Ball() {
		size = 20;
		locX = 390;
		locY = 665;
		speed = 10;
		angle = 3*Math.PI/4;
	}
	
	public int x() {
		return locX;
	}
	
	public int y() {
		return locY;
	}
	
	public int size() {
		return size;
	}
	
	public void move() {
		locY -= speed * Math.sin(angle);
		locX += speed * Math.cos(angle);
	}
	
	public double getAng() {
		return angle;
	}
	
	public void setAng(double ang) {
		angle = ang;
	}

}
