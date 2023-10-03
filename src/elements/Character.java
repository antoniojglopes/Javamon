package elements;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyInput;

public class Character extends Element{
    
    GamePanel gamePanel;
    KeyInput keyInput;

    public Character (GamePanel gamePanel, KeyInput keyInput) {
        this.gamePanel = gamePanel;
        this.keyInput = keyInput;

        setDefaultPosition();
        getCharacterSprite();
    }

    public void setDefaultPosition() {
        x = 100;
        y = 100;
        speed = 5;
        direction = "front";
    }

    public void getCharacterSprite() {
        System.out.println("Image loading started");
        try {
            // Load character sprites
            up1 = ImageIO.read(getClass().getResourceAsStream("/character/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/character/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/character/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/character/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/character/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/character/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/character/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/character/boy_right_2.png"));
            front = ImageIO.read(getClass().getResourceAsStream("/character/front.png"));
    
        // Handle any potential IO exceptions
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Image loading ended");
    }

    public void update() {

        if(keyInput.upPressed == true || keyInput.downPressed == true || keyInput.leftPressed == true || keyInput.rightPressed == true) {
            
            if(keyInput.upPressed == true) {
            direction = "up";
            y -= speed;
            }
            if(keyInput.downPressed == true) {
                direction = "down";
                y += speed;
                }
            if(keyInput.leftPressed == true) {
                direction = "left";
                x -= speed;
                }
            if(keyInput.rightPressed == true) {
                direction = "right";
                x += speed;
                }

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNumber == 1)
                    spriteNumber = 2;
                else if(spriteNumber == 2)
                    spriteNumber = 1;
                spriteCounter = 0;
            }
        }
        else {
            direction = "front";
        }  
    }

    public void draw(Graphics2D g2) {

        BufferedImage sprite = null;

        switch(direction) {
            case "up":
                if(spriteNumber ==1)
                    sprite = up1;
                if(spriteNumber ==2)
                    sprite = up2;
                break;
            case "down":
                if(spriteNumber ==1)
                    sprite = down1;
                if(spriteNumber ==2)
                    sprite = down2;
                break;
            case "left":
                if(spriteNumber ==1)
                    sprite = left1;
                if(spriteNumber ==2)
                    sprite = left2;
                break;
            case "right":
                if(spriteNumber ==1)
                    sprite = right1;
                if(spriteNumber ==2)
                    sprite = right2;
                break; 
            default:
                sprite = front;
                break;   
        }
        g2.drawImage(sprite, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
