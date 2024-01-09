package game;

import java.awt.*;

public class Obstacle extends Rectangle {

    Obstacle(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }

}
