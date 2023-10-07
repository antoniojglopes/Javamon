package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Objects {
    public BufferedImage sprite;
    public String name;
    public boolean collision = false;
    public int wX, wY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0, solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gamePanel) {

        int sX = wX - gamePanel.character.wX + gamePanel.character.sX;
        int sY = wY - gamePanel.character.wY + gamePanel.character.sY;

        if(wX + gamePanel.tileSize > gamePanel.character.wX - gamePanel.character.sX && wX - gamePanel.tileSize < gamePanel.character.wX + gamePanel.screenWidth &&
            wY + gamePanel.tileSize > gamePanel.character.wY - gamePanel.character.sY && wY - gamePanel.tileSize < gamePanel.character.wY + gamePanel.screenHeight)
            
        g2.drawImage(sprite, sX, sY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
