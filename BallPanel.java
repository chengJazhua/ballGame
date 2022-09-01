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
	JFrame window;
	
	Block[][] squares;
	
	Bar bar;
	
	ArrayList<Ball> balls;
	ArrayList<Powerups> power;
	
	int gameState;
	boolean restart = false;
	
	int lives = 3;
	int points = 0;

	int blink = 0;
	
	JButton yes;
	JButton newGame;
	
	
	int level = 1;
	
	public BallPanel() {
		window = new JFrame();
		window.setSize(800,800);  
		window.setLocation(1, 1);				         
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		panel = new JPanel();
		
		
		int height = window.getHeight()/2;
		int width = window.getWidth();
		
		squares = new Block[5][5];
		makeBoxes(squares.length, squares[0].length, width/squares[0].length, height/squares.length);
		
		balls = new ArrayList<Ball>();
		balls.add(new Ball());
		bar = new Bar();
		
		power = new ArrayList<Powerups>();
		
		newGame = new JButton("New Game");
		newGame.addActionListener(new Listener_NewGame());
		newGame.setVisible(true);
		
		yes = new JButton("Yes!");
		yes.addActionListener(new Listener_Yes());
		yes.setVisible(false);
		
		
		panel.add(yes);
		panel.add(newGame);
		
		
		
		
		window.setContentPane(this);	
		window.add(panel);
		window.setVisible(true);
		
		window.addKeyListener(this);
		window.addMouseListener(this);
		window.setFocusable(true);
		
		gameState = 4;
		
		new Timer(5, this).start();
		
		
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
	      if (gameState == 3) 
	    	  nextLevel(g);
	      if (gameState == 4)
	    	  showTitle(g);
	      
	      
	   }
	
	public void showTitle(Graphics g) {
		int height = window.getHeight();
		int width = window.getWidth();
		g.drawString("Welcome to Ball!", width/2, height/2);
	}
	
	public void showBoard(Graphics g){
		
		
		int squaresLeft = 0;
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				Block box = squares[i][j];
				box.setColor(new Color(box.getStatus()*30, box.getStatus()*30, box.getStatus()*30));
				g.setColor(box.getColor());
				if (squares[i][j].getStatus() > 0) {
					squaresLeft++;
					g.fillRect(box.x(), box.y(), box.getWidth(), box.getHeight());
					g.setColor(Color.black);
					g.drawRect(box.x(), box.y(), box.getWidth(), box.getHeight());
				}
			}
		}
		
		for (int i = 0; i < power.size(); i++) {
			Powerups pow = power.get(i);
			g.setColor(Color.black);
			g.drawRect(pow.x(), pow.y(), pow.getSize(), pow.getSize());
			g.setColor(pow.getColor());
			g.fillRect(pow.x(), pow.y(), pow.getSize(), pow.getSize());
			if (pow.y() > window.getHeight()) {
				power.remove(i);
				i--;
			}
			
			if (powCollision(pow)) {
				if (pow.getType() == "moreBalls") {
					balls.add(new Ball(Math.random()*(Math.PI/2), balls.get(0).getSpeed()));
				}
				else if (pow.getType() == "longBar") {
					bar.setLength(bar.getLength() + 25);
				}
				else if (pow.getType() == "slowBall") {
					for (int j = 0; j < balls.size(); j++)
						balls.get(i).setSpeed(balls.get(i).getSpeed() - 2.5);
				}
				power.remove(i);
				i--;
				
			}
			pow.move();
			
			
		}
		
		if (squaresLeft == 0) {
			gameState = 3;
			level++;
		}
		squaresLeft = 0;
		
		g.setColor(Color.black);
		g.drawOval(bar.x(), bar.y(), bar.length(), bar.height());
		g.fillOval(bar.x(), bar.y(), bar.length(), bar.height());
		//g.drawImage(bar.getImage(), bar.x(), bar.y(), bar.length(), bar.height(), null);
		
		for (int i = 0; i < balls.size(); i++) {
			Ball ball = balls.get(i);
			g.drawOval(ball.x(), ball.y(), ball.size(), ball.size());
			g.setColor(Color.RED);
			g.fillOval(ball.x(), ball.y(), ball.size(), ball.size());
			ball.setSpeed(7 + 1.25*(points/100));
			collision(ball);
			ball.move();
		}
		
		g.setColor(Color.red);
		g.drawString("Lives: " + lives, 30, 30);
		g.drawString("Score: " + points, 30, 60);
		g.drawString("Level: " + level, 30, 90);
		generatePower();
		
		
		
		
		
	
	}
	
	public void showStart(Graphics g) {
		int height = window.getHeight();
		int width = window.getWidth();
		
		
		if (blink/20%2 == 0) {
			g.drawString("Click or press a button to start", width/2, height/2);
		}
		
		blink++;
	}
	
	public void showEnd(Graphics g) {
		int height = window.getHeight();
		int width = window.getWidth();
		
		g.drawString("Final Score: " + points, width/2, height/2);
		g.drawString("Play Again?", width/2, height/2 + 30);
		yes.setVisible(true);
		level = 1;
		panel.setVisible(true);
	}
	
	public void startNew() {
		int height = window.getHeight()/2;
		int width = window.getWidth();
		if (restart)
			points = 0;
		balls = new ArrayList<Ball>();
		power = new ArrayList<Powerups>();
		balls.add(new Ball());
		bar.reset();
		makeBoxes(squares.length, squares[0].length, width/squares[0].length, height/squares.length);
		gameState = 0;
		lives = 3;
		yes.setVisible(false);
		restart = false;
		window.requestFocus();
	}
	
	public void nextLevel(Graphics g) {
		int height = window.getHeight();
		int width = window.getWidth();
		g.drawString("Level Completed! Click to continue", height/2, width/2);
		
	}
	
	public void generatePower() {
		int random = (int)(Math.random()*3000);
		
		if (random < 10) {
			String type = "";
			Color color = null;
			switch ((int)(Math.random()*3)+1) {
				case (1): type = "moreBalls";
					color = Color.red;
					break;
				case (2): type = "longBar";
					color = Color.blue;
					break;
				case (3): type = "slowBall";
					color = Color.green;
					break;
			}
			power.add(new Powerups(window.getWidth(), type, color));
		}
	}
	public boolean powCollision(Powerups pow) {
		for (int i = 0; i < bar.length(); i++){
            for (int j = 0; j < pow.getSize(); j++) {
            	if (distance(bar.x() + i, bar.y(), pow.x()+j, pow.y()+pow.getSize()) < 5) {
            		return true;
            	}
            }
         }
		return false;
	}
	public void collision(Ball ball) {
		int sides = 0;
		int corner = 0;
		for (int i = squares.length-1; i >= 0 ; i--) {
			for (int j = squares[0].length-1; j >= 0; j--) {
				Block box = squares[i][j];
				if (box.status > 0) {
					for (int k = 0; k < box.getHeight(); k++) { //left
						if (distance(box.x(), box.y()+k, ball.x()+ball.size()/2, ball.y()+ball.size()/2) < ball.size()){
							calcAngRight(ball);
							sides++;
							corner += 1;
							break;
						}
					}
				
					for (int k = 0; k < box.getHeight(); k++) { //right
						if (distance(box.x()+box.getWidth(), box.y()+k, ball.x()+ball.size()/2, ball.y()+ball.size()/2) < ball.size()){
							calcAngLeft(ball);
							sides++;
							corner += 10;
							break;
						}
					}
				
					for (int k = 0; k < box.getWidth(); k++) { // bottom
						if (distance(box.x()+k, box.y() + box.getHeight(), ball.x()+ball.size()/2, ball.y()+ball.size()/2) < ball.size()) {
							calcAngTop(ball);
							sides++;
							corner += 100;
							break;
						}
						//top
						if (distance(box.x()+k, box.y(), ball.x()+ball.size()/2, ball.y()+ball.size()/2) < ball.size()) {
							calcAngBottom(ball);
							sides++;
							corner += 1000;
							break;
						}
					}
					
				}
				if (sides > 0) {
					box.setStatus(box.getStatus()-1);
					points += 10;
					if (sides > 1) {
						double set = 0;
						switch(corner) {
							case (101): set = 5*Math.PI/4;
								break;
							case (1001): set = 3*Math.PI/4;
								break;
							case (110): set = 7*Math.PI/4;
								break;
							case (1010): set = Math.PI/4;
								break;
						}
						ball.setAng(set);
						
					}
					return;
				}
			}
		}
		if (ball.x()-ball.size()/2 > bar.x()) {
		for (int i = ball.size(); i < bar.length()-ball.size(); i++){
            if (distance((bar.x()+i), bar.y(), ball.x(), ball.y()) < ball.size()){
               calcAngBottom(ball);
               
            }
         }
		}
		
		if (ball.y() < 0 ){
	        calcAngTop(ball);
	          
	    }
		if (ball.x() < 0 ) {
			calcAngLeft(ball);
		}
	    if (ball.x()+ball.size() > window.getWidth()){
	        calcAngRight(ball);
	           
	    }
	    if (ball.y()+ball.size() > window.getHeight()) {
	    	
	    	if (balls.size() > 1)
	    		balls.remove(ball);
	    	else {
	    		balls.remove(0);
	    		balls.add(new Ball());
	    		lives--;
	    		gameState = 0;
		    	if (lives == 0 )
		    		gameState = 2;
	    	}
	    	
	    }
		
		
	}
	
	public void makeBoxes(int rows, int cols, int width, int height) {
		int numSq = 0;
		squares = new Block[rows][cols];
		while (numSq < rows*cols/2) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					int status = (int)(Math.random()*(level+1));
					if (status > 0 && squares[i][j] == null) {
						numSq++;
						Color color = new Color((int)(Math.random()*31)+(status-1)*30, (int)(Math.random()*31)+(status-1)*30, (int)(Math.random()*31)+(status-1)*30);
						squares[i][j] = new Block(color, status, j*(width), i*(height), (width), (height));
					}
					else
						squares[i][j] = new Block(Color.white, status, j*(width), i*(height), (width), (height));
				}
			}
		}
	}
	
	public void calcAngTop(Ball ball) {
		ball.setAng((randomAng(0, .08) - ball.getAng()));
	}
	
	public void calcAngLeft(Ball ball) {
		ball.setAng((randomAng(Math.PI*.95, Math.PI)- ball.getAng()));
	}
	
	public void calcAngRight(Ball ball) {
		ball.setAng(randomAng(Math.PI*.95, Math.PI) - ball.getAng());
	}
	
	public void calcAngBottom(Ball ball) {
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
		if (gameState == 3)
			startNew();
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
		if (gameState == 3)
			startNew();

	}


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_RIGHT) {
			if (bar.x() + bar.length() < window.getWidth())
				bar.moveRight(30);
		}
		if (key == e.VK_LEFT) {
			if (bar.x() > -30)
				bar.moveLeft(30);
		}
		
		
	}


	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_RIGHT) {
			if (bar.x() + bar.length() < window.getWidth())
				bar.moveRight(30);
		}
		if (key == e.VK_LEFT) {
			if (bar.x() > -30)
				bar.moveLeft(30);
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
	private class Listener_NewGame implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			gameState = 1;
			newGame.setVisible(false);
			panel.setVisible(false);
		}

	}
}
