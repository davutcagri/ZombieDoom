package davutcagri;

import davutcagri.panel.MenuPanel;

import javax.swing.*;

public class Main {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Zombie Doom");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
//        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        /*GamePanel gamePanel = new GamePanel(WIDTH, HEIGHT);
        frame.add(gamePanel);*/

        MenuPanel menuPanel = new MenuPanel(WIDTH, HEIGHT);
        frame.add(menuPanel);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}