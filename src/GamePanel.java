import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable {
	static final int GAME_WIDTH =  500;
	static final int GAME_HEIGHT =  500;
	static final int ENTITY_SIZE =  50;
	static final int PLAYERx =  (GAME_WIDTH/2)-(ENTITY_SIZE/2);
	static final int PLAYERy =  GAME_HEIGHT-ENTITY_SIZE;
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	boolean running = false;
	Random random;
	Ship player;
	Timer timer;
	Image image;
	Thread thread;
	Graphics graphics;
	
	GamePanel() {
		random = new Random();
		newGame();
		this.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
		this.setFocusable(true);
		this.addKeyListener(new KL());
		
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
		
	}
	public void draw(Graphics g) {
		player.draw(g);
		for(Alien a:aliens) {
			a.draw(g);
		}
		Toolkit.getDefaultToolkit().sync();
	}
	public void checkCollisions() {
		//check if player hits walls
		if(player.x <= 0) {
			player.x = 0;
		}
		if(player.x >= (GAME_WIDTH-ENTITY_SIZE)){
			player.x = (GAME_WIDTH-ENTITY_SIZE);
		}
		for(int i = aliens.size()-1; i >= 0; i--) {
			Alien a = aliens.get(i);
			if(player.bullets.size() > 0) {
				for(int j = player.bullets.size()-1; j>=0;j--) {
					Bullet b = player.bullets.get(j);
					if(b.intersects(a)) {
						player.bullets.remove(b);
						aliens.remove(a);
						aliens.add(new Alien(random.nextInt(GAME_WIDTH-ENTITY_SIZE),-(random.nextInt(GAME_HEIGHT)),
								ENTITY_SIZE, ENTITY_SIZE));
					}
					
				}
			}
			if(player.intersects(a)) {
				running = false;
			}
		}
		
		
		
	}
	public void newGame()  {
		running = true;
		player =  new Ship(PLAYERx,PLAYERy,ENTITY_SIZE,ENTITY_SIZE);
		spawnAliens();
		thread = new Thread(this);
		thread.start();
		
	}
	public void spawnAliens()  {
		for(int i = 0; i < 6; i++) {
			aliens.add(new Alien(random.nextInt(GAME_WIDTH-ENTITY_SIZE),-(random.nextInt(GAME_HEIGHT)),
					ENTITY_SIZE, ENTITY_SIZE));
		}
		
	}
	public void move() {
		player.move();
		for(int i = player.bullets.size()-1; i >= 0; i--) {
			Bullet b = player.bullets.get(i);
			b.move();
			if(b.y < 0) {
				player.bullets.remove(b);
			}
		}
		
		for(int i = aliens.size()-1; i >= 0; i--) {
			Alien a = aliens.get(i);
			a.move();	
			if(a.y > GAME_WIDTH) {
				aliens.remove(a);
				aliens.add(new Alien(random.nextInt(GAME_WIDTH-ENTITY_SIZE),-(random.nextInt(GAME_HEIGHT)),
						ENTITY_SIZE, ENTITY_SIZE));
			}
		}
		
	}
	public class KL extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
		@Override
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}
		
	}
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double tickCount = 60;
		double ns = 1000000000.0/tickCount;
		double delta = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				checkCollisions();
				move();
//				System.out.println(player.bullets.size());
				repaint();
				delta--;
			}
			
		}
		
	}
	
	
}
