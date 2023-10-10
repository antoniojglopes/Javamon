package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.Monster;

public class UI {
    
    GamePanel gamePanel;
    Font arial_Font;
    BufferedImage monsterImage;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_Font = new Font("Arial", Font.PLAIN, 25);
        Monster monster = new Monster();
        monsterImage = monster.sprite;
    }

    public void draw(Graphics2D g2) {
        
        g2.setFont(arial_Font);
        g2.setColor(Color.BLACK);
        g2.drawImage(monsterImage, gamePanel.tileSize/4, gamePanel.tileSize/4, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
        g2.drawString("Monsters Captured : " + gamePanel.character.monsterscaught, 35, 30);
    }
}
