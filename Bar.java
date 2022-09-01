package ballGame;

import java.awt.Image;

import javax.swing.ImageIcon;

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
		height = 40;
		//ImageIcon x = new ImageIcon(this.getClass().getResource("s.jpg"));
	    //image = x.getImage();
		
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
	
	public void setLength(int length) {
		this.length = length;
	}
	public int getLength() {
		return length;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void moveLeft(int left) {
		locX -= left;
	}
	
	public void moveRight(int right) {
		locX += right;
	}
	
	public void reset() {
		locX = 300;
		locY = 700;
		length = 200;
	}

}
