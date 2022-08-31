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

public class BallPanel extends JPanel implements KeyListener, ActionListener, MouseListener{
	
	JPanel panel;
	JFrame window = new JFrame();
	
	Block[][] squares;
	
	Bar bar;
	Ball ball;
	
	int gameState;
	boolean restart = false;
	
	int lives = 3;
	int points = 0;

	int blink = 0;
	
	JButton yes;
	
	
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
		
		yes = new JButton("Yes!");
		yes.addActionListener(new Listener_Yes());
		yes.setVisible(false);
		yes.setBounds(width/2, height+60, 30, 30);
		
		
		
		//window.add(panel);
		window.setContentPane(this);		
		window.setVisible(true);
		window.add(yes);
		window.addKeyListener(this);
		window.addMouseListener(this);
		window.setFocusable(true);
		
		gameState = 0;
		
		new Timer(5, this).start();
		
		repaint();
	}
	
	
	public void paintComponent(Graphics g)
	   {
	      super.paintComponent(g); 
	      if (restart)
	    	  startNew();
	      if (gameState == 0)
	    	  showStart(g);
	      if (gameState == 1)
	    	  showBoard(g);				
	      if (gameState == 2) 
	    	  showEnd(g);
	      
	   }
	
	public void showBoard(Graphics g){
		
		
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				g.setColor(squares[i][j].getColor());
				if (squares[i][j].getStatus() == 0) {
					g.fillRect(squares[i][j].x(), squares[i][j].y(), squares[i][j].getWidth(), squares[i][j].getHeight());
					g.setColor(Color.black);
					g.drawRect(squares[i][j].x(), squares[i][j].y(), squares[i][j].getWidth(), squares[i][j].getHeight());
				}
			}
		}
		
		g.drawOval(bar.x(), bar.y(), bar.length(), bar.height());
		g.fillOval(bar.x(), bar.y(), bar.length(), bar.height());
		g.drawOval(ball.x(), ball.y(), ball.size(), ball.size());
		g.setColor(Color.RED);
		g.fillOval(ball.x(), ball.y(), ball.size(), ball.size());
		
		g.setColor(Color.red);
		g.drawString("Lives: " + lives, 30, 30);
		g.drawString("Score: " + points, 30, 60);
		collision();
		ball.move();
		
	
	}
	
	public void showStart(Graphics g) {
		int height = window.getHeight();
		int width = window.getWidth();
		
		
		if (blink/20%2 == 0) {
			g.drawString("Click or press a button to start", height/2, width/2);
		}
		
		blink++;
	}
	
	public void showEnd(Graphics g) {
		int height = window.getHeight();
		int width = window.getWidth();
		
		g.drawString("Final Score: " + points, height/2, width/2);
		g.drawString("Play Again?", height/2, width/2 + 30);
		yes.setVisible(true);
		
	}
	
	public void startNew() {
		int height = window.getHeight()/2;
		int width = window.getWidth();
		points = 0;
		ball = new Ball();
		bar.reset();
		squares = new Block[10][10];
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				squares[i][j] = new Block(Color.blue, 0, i*(width/10), j*(height/10), (width/10), (height/10));
			}
		}
		gameState = 0;
		lives = 3;
		yes.setVisible(false);
		restart = false;
		window.requestFocus();
	}
	public boolean collision() {
		
		
		
		for (int i = squares.length-1; i >= 0 ; i--) {
			for (int j = squares[0].length-1; j >= 0; j--) {
				Block box = squares[i][j];
				if (box.status == 0) {
					if (ball.x()+ball.size() < box.x() && ball.y() > box.y() && ball.y() < box.y() + box.getHeight()) {
						for (int k = ball.size(); k < box.getHeight(); k++) {
							if (distance(box.x(), box.y()+k, ball.x()+ball.size()/2, ball.y()+ball.size()/2) < ball.size()){
								calcAngRight();
								box.status++;
								points += 10;
								break;
							}
						}
					}
					else if (ball.x()+ball.size() > box.x() && ball.y() > box.y() && ball.y() < box.y() + box.getHeight()) {
						for (int k = ball.size(); k < box.getHeight(); k++) {
							if (distance(box.x()+box.getWidth(), box.y()+k, ball.x()+ball.size()/2, ball.y()+ball.size()/2) < ball.size()){
								calcAngLeft();
								box.status++;
								points += 10;
								break;
							}
						}
					}
					else {
						for (int k = ball.size(); k < box.getWidth()-ball.size(); k++) {
							if (distance(box.x()+k, box.y() + box.getHeight(), ball.x()+ball.size()/2, ball.y()+ball.size()/2) < ball.size()) {
								calcAngTop();
								box.status++;
								points += 10;
								break;
							}
							if (distance(box.x()+k, box.y(), ball.x()+ball.size()/2, ball.y()+ball.size()/2) < ball.size()) {
								calcAngBottom();
								box.status++;
								points += 10;
								break;
							}
						}
					}
				}
			}
		}
		
		for (int i = ball.size(); i < bar.length()-ball.size(); i++){
            if (distance((bar.x()+i), bar.y(), ball.x(), ball.y()) < ball.size()){
               calcAngBottom();
               
               return true;
            }
         }
		
		if (ball.y() < 0 ){
	        calcAngTop();
	        return true;
	          
	    }
		if (ball.x() < 0 ) {
			calcAngLeft();
			return true;
		}
	    if (ball.x()+ball.size() > window.getWidth()){
	        calcAngRight();
	        return true;
	           
	    }
	    if (ball.y()+ball.size() > window.getHeight()) {
	    	lives--;
	    	ball = new Ball();
	    	gameState = 0;
	    	if (lives == 0 )
	    		gameState = 2;
	    	return true;
	    }
		
		
		return false;
	}
	
	
	public void calcAngTop() {
		ball.setAng((randomAng(0, .08) - ball.getAng()));
	}
	
	public void calcAngLeft() {
		ball.setAng((randomAng(Math.PI*.95, Math.PI)- ball.getAng()));
	}
	
	public void calcAngRight() {
		ball.setAng(randomAng(Math.PI*.95, Math.PI) - ball.getAng());
	}
	
	public void calcAngBottom() {
		ball.setAng((randomAng(0, .08) - ball.getAng()));
	}
	
	public double randomAng(double min, double max) {
		double prod = min + Math.random()*(max-min);
		return prod;
	}
	
	public double distance(int x1, int y1, int x2, int y2){
	      return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}


	
	public void mouseClicked(MouseEvent e) {
	}


	
	public void mousePressed(MouseEvent e) {
		if (gameState == 0)
			gameState = 1;
		repaint();
	}


	public void mouseReleased(MouseEvent e) {
		
	}


	public void mouseEntered(MouseEvent e) {
		
	}


	public void mouseExited(MouseEvent e) {
		
	}


	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (gameState == 0)
			gameState = 1;

	}


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_RIGHT) {
			if (bar.x() + bar.length() < window.getWidth())
				bar.moveRight(20);
		}
		if (key == e.VK_LEFT) {
			if (bar.x() > 0)
				bar.moveLeft(20);
		}
		
		
	}


	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_RIGHT) {
			if (bar.x() + bar.length() < window.getWidth())
				bar.moveRight(20);
		}
		if (key == e.VK_LEFT) {
			if (bar.x() > 0)
				bar.moveLeft(20);
		}
		
		
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
	private class Listener_Yes implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			restart = true;
		}
		
	

	}

}
