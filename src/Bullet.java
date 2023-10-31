import java.awt.*;

public class Bullet extends Rectangle{
	
	int yVelocity = 5;
	
	
	Bullet(int x, int y, int width, int height){
		super(x,y,width,height);											
	}
	public void move(){
		this.y -= yVelocity;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x,y, width, height);
	}
}
