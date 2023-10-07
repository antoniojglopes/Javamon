package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import elements.Character;
import main.tile.ManageTiles;
import object.Objects;

public class GamePanel extends JPanel implements Runnable{
    
    //Screen parameters
    private final int originalTileSize = 16;
    private final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576

    //World parameters
    public final int maxWorldCol = 28;
    public final int maxWorldRow = 23;


    //FPS
    private final int FPS = 60;

    ManageTiles manageTiles = new ManageTiles(this);
    KeyInput keyInput = new KeyInput();
    Sound sound = new Sound();
    Sound music = new Sound();
    public CollisionCheck collisionCheck = new CollisionCheck(this);
    public Assets assets = new Assets(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //Elements and objects
    public Character character = new Character(this, keyInput);
    public Objects objects[] = new Objects[10];

    

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
    }

    public void setup() {
        assets.placeObjects();
        playMusic(0);
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

        //Tiles
        manageTiles.drawTiles(g2);

        //Objects
        for(int i = 0; i < objects.length; i++) {
            if(objects[i] != null)
                objects[i].draw(g2, this);
        }

        //Character
        character.draw(g2);

        //UI
        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSound(int i) {
        sound.setFile(i);
        sound.play();
    }
}
