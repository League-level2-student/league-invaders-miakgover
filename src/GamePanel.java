import java.awt.Color;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel 
				implements ActionListener,KeyListener{
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	int currentState = MENU;
	Font titleFont;
	Font instructionFont;
	Timer frameDraw;
	Timer alienSpawn;
	Rocketship rocketship = new Rocketship(250,600,50,50);
	ObjectManager object_manager = new ObjectManager(rocketship);
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	GamePanel(){
		titleFont = new Font("Arial", Font.PLAIN,48);
		instructionFont = new Font("Arial", Font.PLAIN, 28);
		frameDraw = new Timer(1000/60, this);
		frameDraw.start();
		if (needImage) {
		    loadImage ("space.png");
		}
	}
	void startGame() {
		alienSpawn = new Timer(1000 , object_manager);
	    alienSpawn.start();
	}
	void loadImage(String imageFile) {
		if (needImage) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
		    gotImage = true;
	        } catch (Exception e) {
	            
	        }
	        needImage = false;
	    }
	}
	@Override
	public void paintComponent(Graphics g) {
		if(currentState == MENU) {
			drawMenuState(g);
		}
		else if(currentState == GAME) {
			drawGameState(g);
		}
		else if(currentState == END) {
			drawEndState(g);
		}
	}
	
	public void updateMenuState() {
		
	}
	
	public void updateGameState() {
		object_manager.update();
		if (!rocketship.isActive) {
			currentState = END;
		}
	}
	
	public void updateEndState() {
		
	}
	
	public void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("LEAGUE INVADERS", 20, 100);
		g.setFont(instructionFont);
		g.drawString("Press ENTER to start", 100, 300);
		g.drawString("Press SPACE for instructions", 50, 450);
	}
	
	public void drawGameState(Graphics g) {
		int score = object_manager.getScore();
		if (gotImage) {
			g.drawImage(image, 0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT, null);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		}
		object_manager.draw(g);
		g.setColor(Color.YELLOW);
		g.drawString("Score: ", 30, 50);
		g.drawString(String.valueOf(score), 80, 50);
	}
	
	public void drawEndState(Graphics g) {
		int score = object_manager.getScore();
		g.setColor(Color.RED);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("GAME OVER", 100, 100);
		g.setFont(instructionFont);
		g.drawString("You killed ", 90, 300);
		g.drawString(String.valueOf(score), 240, 300);
		g.drawString("enemies", 290, 300);
		g.drawString("Press ENTER to restart", 90, 500);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(currentState == MENU){
		    updateMenuState();
		}
		else if(currentState == GAME){
		    updateGameState();
		}
		else if(currentState == END){
		    updateEndState();
		}
		repaint();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
		    if (currentState == END) {
		        currentState = MENU;
		        rocketship = new Rocketship(250,600,50,50);
		    	object_manager = new ObjectManager(rocketship);
		    } 
		    else {
		        currentState++;
		        if (currentState == GAME) {
			    	startGame();
			    }
		        else if (currentState == END) {
		        	alienSpawn.stop();
		        }
		    }
		} 
		if (e.getKeyCode()==KeyEvent.VK_UP) {
			if(rocketship.y>=0) {
				rocketship.up();
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			if(rocketship.y<=700) {
				rocketship.down();
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(rocketship.x>=0) {
				rocketship.left();
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(rocketship.x<=500) {
				rocketship.right();
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_SPACE && currentState == GAME) {
			object_manager.addProjectiles(rocketship.getProjectile());
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
