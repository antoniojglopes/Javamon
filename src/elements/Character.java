package elements;

import java.awt.Graphics2D;
import java.awt.Color;

import main.GamePanel;
import main.KeyInput;

public class Character extends Element{
    
    GamePanel gamePanel;
    KeyInput keyInput;

    public Character (GamePanel gamePanel, KeyInput keyInput) {
        this.gamePanel = gamePanel;
        this.keyInput = keyInput;
    }

    public void setDefaultPosition() {
        x = 250;
        y = 250;
        speed = 5;
    }

    public void update() {
        if(keyInput.upPressed) {
            y -= speed;
        }
        if(keyInput.downPressed) {
            y += speed;
        }
        if(keyInput.leftPressed) {
            x -= speed;
        }
        if(keyInput.rightPressed) {
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
    }

}
