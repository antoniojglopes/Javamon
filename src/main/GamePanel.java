package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    //Screen parameters
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale; //48
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol; //768
    private final int screenHeight = tileSize * maxScreenRow; //576

    //Character
    int cX = 250;
    int cY = 250;
    int cSpeed = 5;

    //FPS
    private final int FPS = 60;

    KeyInput keyInput = new KeyInput();
    Thread gameThread;

    

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

        if(keyInput.upPressed) {
            cY -= cSpeed;
        }
        if(keyInput.downPressed) {
            cY += cSpeed;
        }
        if(keyInput.leftPressed) {
            cX -= cSpeed;
        }
        if(keyInput.rightPressed) {
            cX += cSpeed;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(cX, cY, tileSize, tileSize);
        g2.dispose();
    }
}
