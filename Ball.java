package ballGame;

public class Ball {
	double speed;
	double angle;
	int locX; 
	int size;
	int locY;
	
	public Ball() {
		size = 20;
		locX = 390;
		locY = 665;
		speed = 5;
		angle = 3*Math.PI/4;
	}
	
	public Ball(double angle, double speed) {
		size = 20;
		locX = 390;
		locY = 665;
		this.speed = speed;
		this.angle = angle;
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
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setAng(double ang) {
		angle = ang;
	}
	
	public void reset() {
		
	}

}
