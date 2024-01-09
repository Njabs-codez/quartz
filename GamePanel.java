package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class GamePanel extends JPanel implements Runnable {
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 700;
    private static final int PLAYER_WIDTH = 90;
    private static final int PLAYER_HEIGHT = 20;
    private Color bgcolour = Color.darkGray;
    private Ball ball;
    private ArrayList<Obstacle> obstacles;
    private Paddle player;
    boolean run = true;
    int score = 0;
    private Thread gameThread;

    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(bgcolour);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                player.keyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e){
                super.keyReleased(e);
                player.keyReleased(e);
            }
        });

        startGame();

    }

    public void startGame(){
        player = new Paddle((SCREEN_WIDTH/2)-(PLAYER_WIDTH/2), SCREEN_HEIGHT-(PLAYER_HEIGHT + 10), PLAYER_WIDTH, PLAYER_HEIGHT);
        ball = new Ball((player.x + (PLAYER_WIDTH/2))-10, player.y - 20, 20, 20);
        obstacles = new ArrayList<Obstacle>();
        gameThread = new Thread(this);
        for(int i = 2; i < 10; i++){
            for(int j = 0; j < 5; j++){
                obstacles.add(new Obstacle(j*120, i*30, 120, 30));
            }
        }
        gameThread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double fps = 60.0;
        double ns = 1000000000.0 / fps;
        double delta = 0;
        while(run){
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/ ns;
            lastTime = currentTime;
            if(delta >= 1){
                move();
                checkCollisions();
                repaint();
                delta--;
            }

        }


    }

    public void checkCollisions(){
        // collision with blocks
        for(int i = obstacles.size()-1; i >= 0; i--){
            Obstacle obstacle = obstacles.get(i);
            if(ball.intersects(obstacle)){
                score++;
                //top of obstacle
                if(ball.getX() >= obstacle.getX() && ball.getX() <= obstacle.getX() + obstacle.getWidth() && (ball.getY() + ball.getHeight()) >= obstacle.getY()
                        && (ball.getY() + ball.getHeight()) < obstacle.getY() + obstacle.getHeight()){
                    ball.setyVelocity(ball.getyVelocity()*-1);
                    obstacles.remove(obstacle);

                } //bottom of obstacle
                else if(ball.getX() >= obstacle.getX() && ball.getX() <= obstacle.getX() + obstacle.getWidth() && (ball.getY()) <= obstacle.getY() + obstacle.getHeight()
                        && (ball.getY()) >= obstacle.getY()){
                    ball.setyVelocity(ball.getyVelocity()*-1);
                    obstacles.remove(obstacle);
                } //left of obstacle
                else if ((ball.getX() + ball.getWidth()) >= obstacle.getX() && (ball.getX() + ball.getWidth()) < obstacle.getCenterX() &&
                        (ball.getY() + ball.getHeight()) >= obstacle.getY() && (ball.getY() + ball.getHeight()) < obstacle.getY() + obstacle.getHeight() ){
                    ball.setxVelocity(ball.getxVelocity()*-1);
                    obstacles.remove(obstacle);
                } //right of obstacle
                else if ((ball.getX()) <= obstacle.getX()+obstacle.getWidth() && (ball.getX()) > obstacle.getCenterX() &&
                        (ball.getY() + ball.getHeight()) >= obstacle.getY() && (ball.getY() + ball.getHeight()) < obstacle.getY() + obstacle.getHeight()){
                    ball.setxVelocity(ball.getxVelocity()*-1);
                    obstacles.remove(obstacle);
                }

            }
        }
        // collision with player paddle
        paddleBallCollision();


        // collision with bottom of screen
        if(ball.getY() >= SCREEN_HEIGHT){
            resetObjects();
        }

        // collision with "Boundary"
        if(ball.intersectsLine(0, 60, SCREEN_WIDTH, 60)){
            ball.setyVelocity(ball.getyVelocity()*-1);
        }
    }
    public void move(){
        player.move();
        ball.move();
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Points: "+score, (SCREEN_WIDTH/2) - (metrics.stringWidth("Points "+score)/2), g.getFont().getSize()+8);

        for(Obstacle ob: obstacles){
            ob.draw(g);
        }
        player.draw(g);
        ball.draw(g);
    }

    public void resetObjects(){
        player = new Paddle((SCREEN_WIDTH/2)-(PLAYER_WIDTH/2), SCREEN_HEIGHT-(PLAYER_HEIGHT + 10), PLAYER_WIDTH, PLAYER_HEIGHT);
        ball = new Ball((player.x + (PLAYER_WIDTH/2))-10, player.y - 20, 20, 20);
    }

    private void paddleBallCollision(){
        if(ball.intersects(player)){
            //top of player.
            if(ball.getX() >= player.getX() && ball.getX() < player.getX() + player.getWidth() && (ball.getY() + ball.getHeight()) >= player.getY()
            && (ball.getY() + ball.getHeight()) < player.getCenterY()){
                ball.setyVelocity(ball.getyVelocity()*-1);
            } //left of player
            else if ((ball.getX() + ball.getWidth()) >= player.getX() && (ball.getX() + ball.getWidth()) < player.getCenterX() &&
                    (ball.getY() + ball.getHeight()) >= player.getY() && (ball.getY() + ball.getHeight()) < player.getY() + getHeight() ){
                ball.setyVelocity(ball.getyVelocity()*-1);
                ball.setxVelocity(ball.getxVelocity()*-1);
            } //right of player
            else if ((ball.getX()) <= player.getX()+player.getWidth() && (ball.getX()) > player.getCenterX() &&
                    (ball.getY() + ball.getHeight()) >= player.getY() && (ball.getY() + ball.getHeight()) < player.getY() + getHeight()){
                ball.setyVelocity(ball.getyVelocity()*-1);
                ball.setxVelocity(ball.getxVelocity()*-1);
            }

        }
    }

}
