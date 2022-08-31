package ballGame;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.image.*;
import java.io.File;

import javax.swing.border.*;

import java.util.TimerTask;
import java.util.ArrayList; 
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;

public class BallPanel extends JPanel implements KeyListener, ActionListener{
	
	JPanel panel;
	JFrame window = new JFrame();
	
	Block[][] squares;
	
	Bar bar;
	Ball ball;
	
	public BallPanel() {
		window.setSize(800,800);  
		window.setLocation(1, 1);				         
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		
		panel = new JPanel();
		
		
		int height = window.getHeight()/2;
		int width = window.getWidth();
		
		squares = new Block[10][10];
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				squares[i][j] = new Block(Color.blue, 0, i*(width/10), j*(height/10), (width/10), (height/10));
			}
		}
		
		ball = new Ball();
		bar = new Bar();
		
		//window.add(panel);
		window.setContentPane(this);		
		window.setVisible(true);
		window.addKeyListener(this);
		
		
		new Timer(3, this).start();
		
		repaint();
	}
	
	
	public void paintComponent(Graphics g)
	   {
	      super.paintComponent(g); 
	      showBoard(g);					      
	   }
	
	public void showBoard(Graphics g){
		
		
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				g.setColor(squares[i][j].getColor());
				if (squares[i][j].getStatus() == 0) {
					g.fillRect(squares[i][j].x(), squares[i][j].y(), squares[i][j].getWidth(), squares[i][j].getLength());
					g.setColor(Color.black);
					g.drawRect(squares[i][j].x(), squares[i][j].y(), squares[i][j].getWidth(), squares[i][j].getLength());
				}
			}
		}
		
		g.drawOval(bar.x(), bar.y(), bar.length(), bar.height());
		g.drawOval(ball.x(), ball.y(), ball.size(), ball.size());
		g.setColor(Color.RED);
		g.fillOval(ball.x(), ball.y(), ball.size(), ball.size());
		
		collision();
		ball.move();
		
	
	}
	
	public boolean collision() {
		
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				Block box = squares[i][j];
				
				
			}
		}
		
		for (int i = ball.size(); i < bar.x()-ball.size(); i++){
            if (distance((bar.x()+i), bar.y(), ball.x(), ball.y()) < ball.size()){
               calcAngBar();
               
               return true;
            }
         }
		
		if (ball.x() < 0 || ball.x() > window.getWidth() ){
	        calcAngBar();
	          
	    }
	    if (ball.y() < 0 || ball.y() > window.getHeight()){
	        calcAngSide();
	           
	    }
		
		
		return false;
	}
	
	
	public void calcAngSide() {
		ball.setAng(Math.abs(randomAng(Math.PI*1.9, Math.PI*2)- ball.getAng()));
	}
	
	public void calcAngBar() {
		ball.setAng(Math.abs(randomAng(Math.PI*.95, Math.PI) - ball.getAng()));
	}
	
	public double randomAng(double min, double max) {
		double prod = min + Math.random()*(max-min);
		return prod;
	}
	
	public double distance(int x1, int y1, int x2, int y2){
	      return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}


	
	/*public void mouseClicked(MouseEvent e) {
		
	}


	
	public void mousePressed(MouseEvent e) {
		
	}


	public void mouseReleased(MouseEvent e) {
		
	}


	public void mouseEntered(MouseEvent e) {
		
	}


	public void mouseExited(MouseEvent e) {
		
	}*/


	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();

	}


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_RIGHT) {
			bar.moveRight(10);
		}
		if (key == e.VK_LEFT) {
			bar.moveLeft(10);
		}
		
		repaint();
	}


	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_RIGHT) {
			bar.moveRight(10);
		}
		if (key == e.VK_LEFT) {
			bar.moveLeft(10);
		}
		
		repaint();
	}


	
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	//scrapped collision 
	
	/*System.out.println(ball.y() + " " + box.y());
	if (ball.y() < box.y()) {
		if(box.status == 0 && distance(box.x()+box.getWidth()/2, box.y()+box.getLength()/2, 
				ball.x()+ball.size()/2, ball.y() + ball.size()/2) <= ball.size/2 + box.width/2) {
			calcAngSide();
			squares[i][j].status += 1;
			return true;
		}
		
		if (ball.x() < box.x()) {
			
			for (int k = ball.size(); k < box.getWidth()-ball.size(); k++) {
				if (distance(box.x(), box.y()+k, ball.x(), ball.y()) < ball.size()) {
					calcAngSide();
					squares[i][j].status += 1;
					return true;
				}
			}
			
		}
		
		else if (ball.x() > box.x()+box.getLength()) {
			for (int k = ball.size(); k < box.getWidth()-ball.size(); k++) {
				if (distance(box.x()+box.getLength(), box.y()+k, ball.x(), ball.y()) < ball.size()) {
					calcAngSide();
					squares[i][j].status += 1;
					return true;
				}
			}
		}
		else {
			for (int k = ball.size(); k < box.getLength()-ball.size(); k++) {
				if (distance(box.x()+k, box.y(), ball.x(), ball.y()) < ball.size()) {
					calcAngBar();
					squares[i][j].status += 1;
					return true;
				}
			}
			
		}
		
		
	}
	else {
		if(box.status == 0 && distance(box.x()+box.getWidth()/2, box.y()+box.getLength()/2, 
				ball.x()+ball.size()/2, ball.y() + ball.size()/2) <= ball.size/2 + box.length/2) {
			calcAngBar();
			squares[i][j].status += 1;
			System.out.println("idk bruh");
			return true;
		}
		for (int k = 0; k < box.getLength()-ball.size(); k++) {
			if (distance(box.x()+k, box.y()+box.getWidth(), ball.x(), ball.y()) < ball.size()) {
				calcAngBar();
				squares[i][j].status += 1;
				System.out.println("hi");
				return true;
			}
		}
		
		*/

}
