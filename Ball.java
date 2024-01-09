package game;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {

    private int yVelocity = -2;
    private int xVelocity = 2;
    private double top;
    private double bottom;
    private double left;
    private double right;

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

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    Ball(int x, int y, int width, int height){
        super(x, y, width, height);
        Random random = new Random();
        int dir = random.nextInt(2);
        if(dir == 0){
            xVelocity = getxVelocity();
        }else{
            xVelocity *= -1;
        }
        this.left = getCenterX() - ((double) width /2);
        this.right = getCenterX() + ((double) width /2);
        this.top = getCenterY() - ((double) width /2);
        this.bottom = getCenterY() + ((double) width /2);

    }

    public void move(){
        this.x += xVelocity;
        this.y += yVelocity;
        if(x < 0){
            xVelocity *= -1;
        } else if(x > GamePanel.SCREEN_WIDTH - width){
            xVelocity *= -1;
        }
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
        g.setColor(Color.black);
        g.drawOval(x,y,width,height);
    }


}
