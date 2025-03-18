package main;


import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
    public static JFrame frame;
    public static void main(String[] args) {

        frame = new JFrame();
        GamePanel gamePanel = new GamePanel(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Javamon");
        new Main().setIcon();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(gamePanel);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.start();
        gamePanel.setup();
    }

    public void setIcon() {
        ImageIcon icon = new ImageIcon("src/res/monsters/monster1.png");
        frame.setIconImage(icon.getImage());
    }
}