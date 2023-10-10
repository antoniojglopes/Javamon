package elements;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyInput;

public class Character extends Element{
    
    GamePanel gamePanel;
    KeyInput keyInput;

    public final int sX;
    public final int sY;
    public int monsterscaught = 0;

    public Character (GamePanel gamePanel, KeyInput keyInput) {
        this.gamePanel = gamePanel;
        this.keyInput = keyInput;

        sX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        sY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 26;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 24;

        setDefaultPosition();
        getCharacterSprite();
    }

    public void setDefaultPosition() {
        wX = gamePanel.tileSize * 6;
        wY = gamePanel.tileSize * 18;
        speed = 5;
        direction = "front";
    }

    public void getCharacterSprite() {
        try {
            // Load character sprites
            up1 = ImageIO.read(getClass().getResourceAsStream("/character/character_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/character/character_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/character/character_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/character/character_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/character/character_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/character/character_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/character/character_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/character/character_right_2.png"));
            front = ImageIO.read(getClass().getResourceAsStream("/character/front.png"));
            back = ImageIO.read(getClass().getResourceAsStream("/character/back.png"));
    
        // Handle any potential IO exceptions
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(keyInput.upPressed == true || keyInput.downPressed == true || keyInput.leftPressed == true || keyInput.rightPressed == true) {
            
            if(keyInput.upPressed == true) {
                direction = "up";
            }
            if(keyInput.downPressed == true) {
                direction = "down";
                }
            if(keyInput.leftPressed == true) {
                direction = "left";
                }
            if(keyInput.rightPressed == true) {
                direction = "right";
                }
            //check collision
            collision = false;
            gamePanel.collisionCheck.checkElement(this);

            int objIndex = gamePanel.collisionCheck.checkObject(this, true);
            pickUpObject(objIndex);
            
            //if collision is false, move
            if(collision == false) {
                switch(direction) {
                    case "up":
                        wY -= speed;
                        break;
                    case "down":
                        wY += speed;
                        break;
                    case "left":
                        wX -= speed;
                        break;
                    case "right":
                        wX += speed;
                        break;
                }
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
            if(direction == "up")
                direction = "back";
            if(direction == "down")
            direction = "front";
            if(direction == "left")
            direction = "leftSide";
            if(direction == "right")
            direction = "rightSide";
        }  
    }

    public void pickUpObject(int i) {
        if(i != 999) {
            
            String objName = gamePanel.objects[i].name;

            switch(objName) {
                case "monster":
                    //gamePanel.playSound(1);
                    gamePanel.objects[i] = null;
                    monsterscaught++;
                    break;
            }
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
            case "back":
                sprite = back;
                break;
            case "front":
                sprite = front;
                break;
            case "leftSide":
                sprite = left1;
                break;
            case "rightSide":
                sprite = right1;
                break;
        }
        g2.drawImage(sprite, sX, sY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
