package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import elements.Character;

public class GamePanel extends JPanel implements Runnable{
    
    //Screen parameters
    private final int originalTileSize = 16;
    private final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol; //768
    private final int screenHeight = tileSize * maxScreenRow; //576

    //Character
    int cX = 100;
    int cY = 100;
    int cSpeed = 5;

    //FPS
    private final int FPS = 60;

    KeyInput keyInput = new KeyInput();
    Thread gameThread;
    Character character = new Character(this, keyInput);

    

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double intervalTime = 1000000000 / FPS;
        double  delta = 0;
        long lastUpdateTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while(gameThread != null) {
            //Game loop

            currentTime = System.nanoTime();

            delta += (currentTime - lastUpdateTime) / intervalTime;
            timer += currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;

            if(delta >= 1) {
                //Update
                update();

                //Draw
                repaint();

                delta--;
            }

            if(timer >= 1000000000)
                timer = 0;
        }
    }

    public void update() {

        character.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        character.draw(g2);

        g2.dispose();
    }
}
