import java.awt.Color;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	GameFrame() {
		this.add(new GamePanel());
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Space Invaders");
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
