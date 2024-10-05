package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("John Snow adventure");

        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);
        window.pack();
        window.setLocationRelativeTo(null); //window on center
        window.setVisible(true);

        gamepanel.setupGame();
        gamepanel.startGameThread();
    }
}
