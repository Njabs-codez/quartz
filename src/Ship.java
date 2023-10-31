import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Ship extends Rectangle{
	int xVelocity;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	Ship(int x,int y,int width,int height){
		super(x,y,width,height);
	}
	public void xMovement(int velocity) {
		xVelocity = velocity;
	}
	public void move() {
		x += xVelocity;
		
	}
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);
		for(Bullet b : bullets) {
			b.draw(g);
		}
		
	}
	public void shoot() {
		bullets.add(new Bullet(x+(width/2)-5,y,10,10));
			
	}
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			xMovement(10);
			move();
			break;
		case KeyEvent.VK_LEFT:
			xMovement(-10);
			move();
			break;
		case KeyEvent.VK_SPACE:
			shoot();
			
		}
		
		
	}
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			xMovement(0);
			move();
			break;
		case KeyEvent.VK_LEFT:
			xMovement(0);
			move();
			break;
		}
		
	}
}
