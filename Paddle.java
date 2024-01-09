package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle{

    private int xVelocity = 0;
    private final double top;
    private final double bottom;

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    private final double left;
    private final double right;
    Paddle(int x, int y, int width, int height){
        super(x, y, width, height);
        this.left = getCenterX() - ((double) width /2);
        this.right = getCenterX() + ((double) width /2);
        this.top = getCenterY() - ((double) width /2);
        this.bottom = getCenterY() + ((double) width /2);

    }
    public void move(){
        this.x += xVelocity;
        if(x < 0){
            x = 0;
        } else if(x >= GamePanel.SCREEN_WIDTH - width){
            x = GamePanel.SCREEN_WIDTH - width;
        }
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            setxVelocity(5);
            move();
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            setxVelocity(-5);
            move();
        }
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            setxVelocity(0);
            move();
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            setxVelocity(0);
            move();
        }
    }
}
