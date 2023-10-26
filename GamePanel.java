import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{
	
	static final int GAME_WIDTH = 500;
	static final int GAME_HEIGHT = 500;
	static final int OBJECT_SIZE = 25;
	static final int GAME_UNITS = (GAME_WIDTH*GAME_HEIGHT)/(OBJECT_SIZE*OBJECT_SIZE);
	static final int LATENCY = 85;
	int bodyParts = 5;
	final int[] x = new int[GAME_UNITS];
	final int[] y = new int[GAME_UNITS];
	int points = 0;
	Random random;
	Timer timer;
	Thread thread;
	boolean playing = false;
	int appleX;
	int appleY;
	char direction = 'd';
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		startGame();
	}
	
	public void startGame() {
		newApple();
		playing = true;
		timer = new Timer(LATENCY,this);
		timer.start();

	}
	
	public void newApple() {
		appleX = random.nextInt(GAME_WIDTH/OBJECT_SIZE)*OBJECT_SIZE;
		appleY = random.nextInt(GAME_HEIGHT/OBJECT_SIZE)*OBJECT_SIZE;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		for(int i = 0; i<GAME_WIDTH/OBJECT_SIZE;i++) {
			g.drawLine(i*OBJECT_SIZE, 0, i*OBJECT_SIZE, GAME_HEIGHT);
			g.drawLine(0, i*OBJECT_SIZE, GAME_WIDTH, i*OBJECT_SIZE);
		}
		if(playing) {
			g.setColor(Color.yellow);
			g.fillOval(appleX, appleY, OBJECT_SIZE, OBJECT_SIZE);
		
			for(int i = 0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.blue);
					g.fillRect(x[i], y[i], OBJECT_SIZE, OBJECT_SIZE);
				}
				else {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], OBJECT_SIZE, OBJECT_SIZE);
				}
			}
		}
		else {
			gameOver(g);
		}
	}
	public void checkCollisions() {
		//check if head bumps vertical walls
		if(x[0]<0 || x[0]>=GAME_WIDTH) {
			playing = false;
		}
		//check if head bumps horizontal walls
		if(y[0]<0 || y[0]>=GAME_HEIGHT) {
			playing = false;
		}
		//check if apple is ate
		if(x[0]==appleX && y[0]==appleY) {
			points++;
			bodyParts++;
			newApple();
		}
		//CHECK IF HEAD HITS BODYPARTS
		for(int i = bodyParts; i>0; i--) {
			if(x[0]==x[i] && y[0]==y[i]) {
				playing=false;
			}
		}
		
	}
	public void move() {
		for(int i = bodyParts; i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'u': y[0] -= OBJECT_SIZE;
		break;
		case 'd': y[0] += OBJECT_SIZE;
		break;
		case 'l': x[0] -= OBJECT_SIZE;
		break;
		case 'r': x[0] += OBJECT_SIZE;
		break;
		}
		
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("San serif",Font.BOLD,50));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (GAME_WIDTH-metrics.stringWidth("GAME OVER"))/2, GAME_HEIGHT/2);
	}
	
	public class AL extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if(direction != 'd') {
					direction = 'u';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'u') {
					direction = 'd';
				}
				break;
			case KeyEvent.VK_LEFT:
				if(direction != 'r') {
					direction = 'l';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'l') {
					direction = 'r';
				}
				break;
			}
			if(playing == false) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					startGame();
					repaint();
				}
			}
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(playing) {
			move();
			checkCollisions();
		}
		repaint();
		
	}
	
	
}
