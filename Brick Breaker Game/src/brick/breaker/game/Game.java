package brick.breaker.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener 
{
	private boolean play = false;
	private int score = 0;
	private int hiddencount=0;
	private int totalBricks = 42;
	private Timer timer;
	private int delay=8;
	private int playerX = 310;
	private int ballposX = (int) (Math.random()*(550-50+1)+50);
	private int ballposY = (int) (Math.random()*(500-250+1)+250);
	private BricksMap map;
	private int ballXdir = -2;
	private int ballYdir = -3;
	
	public Game()
	{		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
		timer.start();
		
		map = new BricksMap(6, 7);
	}
	
	public void paint(Graphics g)
	{    		
		// background
		g.setColor(Color.getHSBColor(50, 100, 80));
		g.fillRect(1, 1, 692, 592);
		
		// borders
		g.setColor(Color.pink);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(683, 0, 3, 592);
		
		// paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 10);
		
		// ball
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		// BricksMap
		map.draw((Graphics2D)g);
		
		//score
		g.setColor(Color.BLUE);
		g.setFont(new Font("default", Font.BOLD, 16));
		g.drawString("Score : " + score, 550, 30);
		
		// Game over 
		if(ballposY>=570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			//printing game over 
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("serif", Font.BOLD, 32));
			g.drawString("Game Over! 😔", 220, 300);
			g.setFont(new Font("Cursive", Font.BOLD, 26));
			g.drawString("Score : " + score, 250, 340);
			
			g.setColor(Color.magenta);
			g.setFont(new Font("serif", Font.BOLD, 23));
			g.drawString("Press Enter to Restart", 210, 380);
		}
		
		// You Win
		if(totalBricks<=0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			//printing game over 
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("serif", Font.BOLD, 32));
			g.drawString("Congratulations You Won !", 180, 300);
			g.setFont(new Font("Cursive", Font.BOLD, 26));
			g.drawString("Score : " + score, 250, 340);
			
			g.setColor(Color.magenta);
			g.setFont(new Font("serif", Font.BOLD, 23));
			g.drawString("Press SpaceBar for Next Level", 180, 380);
		}
		
		g.dispose();
	}	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){        
			if(playerX >= 600){
				playerX = 600;
			}
			else{
				moveRight();
			}
        }
		if (e.getKeyCode() == KeyEvent.VK_LEFT){          
			if(playerX <= 0){
				playerX = 0;
			}
			else{
				moveLeft();
			}
        }
		if (play==false && e.getKeyCode() == KeyEvent.VK_ENTER){          
			score=0;
			hiddencount=0;
			totalBricks = 42;
			playerX = 310;
			ballposX = (int) (Math.random()*(550-50+1)+50);
			ballposY = (int) (Math.random()*(500-250+1)+250);
			ballXdir = -2;
			ballYdir = -3;
			
			
			map = new BricksMap(6, 7);
			
        }
		if (play==false && e.getKeyCode() == KeyEvent.VK_SPACE){    
			hiddencount++;
			score=0;
			totalBricks+=hiddencount*7;
			playerX = 310;
			ballposX = (int) (Math.random()*(550-50+1)+50);
			ballposY = (int) (Math.random()*(500-250+1)+250);
			ballXdir-=1;;
			ballYdir-=1;
			
			
			map = new BricksMap(totalBricks/8,8);
			
        }
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void moveRight()
	{
		play = true;
		playerX+=25;	
	}
	
	public void moveLeft()
	{
		play = true;
		playerX-=25;	 	
	}
	@Override
	public void actionPerformed(ActionEvent e){
		timer.start();
		
		if(play) {
			
			// if ball hit left boundry;
			if(ballposX<=0) {
				ballXdir = -ballXdir;
			}
			//if ball hits upper boundry
			if(ballposY<=0) {
				ballYdir = -ballYdir;
			}
			//if ball hits right boundry
			if(ballposX>=670) {
				ballXdir = -ballXdir;
			}
			// creating ball & paddle rectangles to detect intersection between paddle and ball 
			Rectangle bRect = new Rectangle(ballposX, ballposY, 20, 20);
			Rectangle pRect = new Rectangle(playerX, 550, 100, 10);
			
			if(bRect.intersects(pRect)) {
				ballYdir = -ballYdir;
			}
			
			label:for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						
						int brickposX = 80+j*map.brickWidth;
						int brickposY = 50+i*map.brickHeight;
						
						Rectangle brickRect = new Rectangle(brickposX, brickposY, map.brickWidth, map.brickHeight);
						
						if(bRect.intersects(brickRect)) {
							map.map[i][j]=0;
							totalBricks--;
							score+=10;
							if(ballposX+19<=brickposX || ballposX+1>=brickposX + map.brickWidth) {
								ballXdir = -ballXdir;
							}
							else{
								ballYdir = -ballYdir;
							}

							break label;
						}
					}
				}
			}
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
		}
		
		repaint();
	}
}