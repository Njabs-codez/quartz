import java.awt.*;
import java.util.Random;

public class Alien extends Rectangle{
	
	int yVelocity = 1;

	Alien(int x, int y, int width,int height){
		super(x,y,width,height);
		
	}
	
	public void move() {
		y += yVelocity;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}

}
