package ballGame;

import java.awt.Color;

public class Block {
	Color color;
	int status; 
	int x;
	int y;
	int width;
	int length;
	
	public Block() {
		color = Color.BLUE;
		status = 0;
	}
	
	public Block(Color color, int status, int x, int y, int width, int length) {
		this.color = color;
		this.status = status;
		this.x = x;
		this.y = y;
		this.width = width;
		this.length = length;
	}
	
	public void setStatus(int change) {
		status = change;
	}
	
	public void setColor(Color change) {
		color = change;
	}
	
	
	public int getStatus() {
		return status;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getLength() {
		return length;
	}
	

}
