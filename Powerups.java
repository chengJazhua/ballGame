package ballGame;

import java.awt.Color;

public class Powerups {
	
	int x;
	int y;
	int speed;
	int size;
	Color color;
	String type;
	
	public Powerups(int width, String type, Color color){
		x = (int)(Math.random()*width);
		y = 0;
		speed = 10;
		this.type = type;
		size = 30;
		this.color = color;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getType() {
		return type;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void move() {
		y += speed;
	}
	
	
}
