package ballGame;

import java.awt.Image;

public class Bar {
	
	int locX;
	int locY;
	int height;
	int length;
	Image image;
	
	public Bar() {
		locX = 300;
		locY = 700;
		length = 200;
		height = 25;
		
	}
	
	public int x() {
		return locX;
	}
	
	public int y() {
		return locY;
	}
	
	public int height() {
		return height;
	}
	
	public int length() {
		return length;
	}
	
	public void moveLeft(int left) {
		locX -= left;
	}
	
	public void moveRight(int right) {
		locX += right;
	}

}
