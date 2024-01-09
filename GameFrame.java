package game;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.setResizable(false);
        this.setTitle("BreakOut");
        this.add(new GamePanel());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
