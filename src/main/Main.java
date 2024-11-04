package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Slime  Chaser");
        new Main().setIcon();

        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);
        window.pack();
        window.setLocationRelativeTo(null); //window center
        window.setVisible(true);

        gamepanel.setupGame();
        gamepanel.startGameThread();
        //test
    }

    public void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("player/boy_down_1.png"));
        window.setIconImage(icon.getImage());
    }
}
