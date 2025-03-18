package elements;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import java.awt.Image;
import main.GamePanel;
import main.Scale;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Element implements java.io.Serializable{
    
    GamePanel gamePanel;
    public int wX;
    public int wY;
    public int speed;

    public BufferedImage back, front, up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public String dialogues[][] = new String[20][20];
    public int dialoguesIndex =0;
    public int dialoguesSet = 0;

    public String name;
    public boolean collision = false;
    public int isTalking = 0;
    public int isBattling = 0;
    public int boss = 0;
    public boolean temp = false;

    public boolean spawnMaps[] = new boolean[10];
    public int hp;
    public int maxHp;
    public int ivmaxHp;
    public int attack;
    public int ivattack;
    public int currentAttack;
    public int defense;
    public int ivdefense;
    public int currentDefense;
    public int speedstat;
    public int ivspeedstat;
    public int currentSpeed;
    public int type;
    public String nickname;

    public Map<String, Itemvalue> inventory = new LinkedHashMap<>();
    public int inventorySize;
    
    public Element (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public class Itemvalue{ //CLASS FOR ITEMS
        public Image image;
        public int quantity;
        public int price;
    
        public Itemvalue(Image image, int quantity, int price) {
            this.image = image;
            this.quantity = quantity;
            this.price = price;
        }
    }

    public void setAction () {
        //Character behaviour
    }

    public void speak() {}

    public void faceCharacter () { //NPC face character when talking

        switch (gamePanel.character.direction) {
            case "up":
                direction = "front";
                break;
            case "down":
                direction = "back";
                break;
            case "left":
                direction = "rightSide";
                break;    
            case "right":
                direction = "leftSide";
                break;
        }

    }

    public void startDialogue (int setNum) {
        gamePanel.gameState = gamePanel.dialoguesState;
        gamePanel.ui.currentNPC = this;
        dialoguesSet = setNum;
    } 

    public void update (int map) { //shared for all npcs

        if(isTalking == 0) {
            setAction();        //Character behaviour

            collisionOn = false;
            gamePanel.collisionCheck.checkTile(this, map);
            gamePanel.collisionCheck.checkCharacter(this);
            gamePanel.collisionCheck.checkElement(this, gamePanel.monsters, map);

            //if collision is false, move
            if(collisionOn == false) {
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
    }

    public void draw (Graphics2D g2) {
        
        BufferedImage sprite = null;

        int sX = wX - gamePanel.character.wX + gamePanel.character.sX;
        int sY = wY - gamePanel.character.wY + gamePanel.character.sY;

        double oneScale;
        double hpBar;

        if( wX + gamePanel.tileSize*5 > gamePanel.character.wX - gamePanel.character.sX   &&
            wX - gamePanel.tileSize < gamePanel.character.wX + gamePanel.character.sX   &&
            wY + gamePanel.tileSize*5 > gamePanel.character.wY - gamePanel.character.sY   && 
            wY - gamePanel.tileSize < gamePanel.character.wY + gamePanel.character.sY) {

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
        }
        if(boss==1){
            if(hp >= maxHp/2)
                g2.drawImage(down1, sX, sY, gamePanel.tileSize*5, gamePanel.tileSize*5, null);
            else{
                currentAttack = attack*2;
                currentDefense = defense*2;
                currentSpeed = speedstat*2;
                g2.drawImage(down2, sX, sY, gamePanel.tileSize*5, gamePanel.tileSize*5, null);
            }
            oneScale = (double) gamePanel.tileSize*5 / (double) maxHp;
            hpBar = oneScale * (double) hp;
            if (hpBar < 0)
                hpBar = 0;
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2.drawString(nickname, sX+gamePanel.tileSize*2-32, sY-30);
            g2.setColor(Color.BLACK);
            g2.fillRect(sX-1, sY - 26, gamePanel.tileSize*5+2, 24);
            g2.setColor(Color.RED);
            g2.fillRect(sX, sY - 25, (int) hpBar, 22);   
        }
        else{
            g2.drawImage(sprite, sX, sY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
        

        if (isBattling == 1) {
            oneScale = (double) gamePanel.tileSize / (double) maxHp;
            hpBar = oneScale * (double) hp;
            if (hpBar < 0)
                hpBar = 0;
            g2.setColor(Color.BLACK);
            g2.fillRect(sX-1, sY - 16, gamePanel.tileSize+2, 12);
            g2.setColor(Color.RED);
            g2.fillRect(sX, sY - 15, (int) hpBar, 10);
        }
    }

    public BufferedImage setup (String imagePath, int tileSize, int tileSize2) { //SETS UP IMAGE
        BufferedImage image = null;
        Scale scaling = new Scale();

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = scaling.scaleImage(image, tileSize, tileSize2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public void addItem(String itemName, int quantity){ //ADDS QUANTITY OF ITEM TO INVENTORY
        Itemvalue value = inventory.get(itemName);
        value.quantity += quantity;
        inventory.put(itemName, value);
    }

    public void removeItem(String itemName, int quantity){  //REMOVES QUANTITY OF ITEM FROM INVENTORY
        Itemvalue value = inventory.get(itemName);
        value.quantity -= quantity;
        inventory.put(itemName, value);
    }

    public void updateInventorySize(){  //UPDATES INVENTORY SIZE
        inventorySize = 0;
        for (Map.Entry<String, Itemvalue> entry : inventory.entrySet()) {
            inventorySize++;
            }
    }
    
    public int checkItem(String itemName){  //RETURNS QUANTITY OF ITEM
        Itemvalue value = inventory.get(itemName);
        return value.quantity;
    }

    public String getItemName(int index){  //RETURNS NAME OF ITEM
        int i = 0;
        for (Map.Entry<String, Itemvalue> entry : inventory.entrySet()) {
            if(i == index){
                return entry.getKey();
            }
            i++;
        }
        return null;
    }

}
