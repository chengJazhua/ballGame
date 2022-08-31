package ballGame;

import java.awt.Color;

public class Block {
	Color color;
	int status; 
	int x;
	int y;
	int width;
	int height;
	
	public Block() {
		color = Color.BLUE;
		status = 0;
	}
	
	public Block(Color color, int status, int x, int y, int width, int height) {
		this.color = color;
		this.status = status;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
	
	public int getHeight() {
		return height;
	}
	

}
