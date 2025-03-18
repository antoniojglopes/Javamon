package elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Image;
import java.io.File;

import main.*;
import multiplayer.packets.Packet02Move;
import multiplayer.packets.Packet05JavamonRespawn;
import multiplayer.packets.Packet06ElementState;
import multiplayer.packets.Packet07ElementNPCState;

public class Character extends Element{
    
    KeyInput keyInput;

    public int isMoving = 0;

    public final int sX;
    public final int sY;



    public ArrayList<Javamon> party = new ArrayList<Javamon>();
    public int partySize;

    public ArrayList<Javamon> box = new ArrayList<Javamon>();
    public int boxSize;

    public int monstersCaunght = 0;

    public int index;

    public String username;

    public int inBattle = 0, inBattleBoss=0 , inDialogue = 0 , currentMap;

    public int money = 400;


    public Character (GamePanel gamePanel, KeyInput keyInput, String username) {
       
        super(gamePanel);
        this.keyInput = keyInput;
        this.username = username;
        this.inventorySize = 0;

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

        setupInventory();
        updateInventorySize();
    }

    public void setDefaultPosition()
    {
        speed=10;
        direction = "down";
        spriteNumber = 3;
    }

    public void getCharacterSprite() {
        up1 = setup("character_up_1");
        up2 = setup("character_up_2");
        down1 = setup("character_down_1");
        down2 = setup("character_down_2");
        left1 = setup("character_left_1");
        left2 = setup("character_left_2");
        right1 = setup("character_right_1");
        right2 = setup("character_right_2");
        front = setup("front");
        back = setup("back");
    }

    public BufferedImage setup(String imageName){

        Scale scaling= new Scale();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/character/" + imageName + ".png"));
            image = scaling.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    public void update(int currentMap) {

        if(keyInput != null) {
            this.currentMap = currentMap;
            if(keyInput.upPressed == true || keyInput.downPressed == true || keyInput.leftPressed == true || keyInput.rightPressed == true) {
                if (isMoving == 0){
                    spriteNumber = 1;
                    spriteCounter = 0;
                }
                
                isMoving = 1;

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
                collisionOn = false;
                gamePanel.collisionCheck.checkTile(this, currentMap);
                
                /*int objIndex = gamePanel.collisionCheck.checkObject(this, true);
                pickUpObject(objIndex);*/

                gamePanel.collisionCheck.checkElement(this, gamePanel.flames, currentMap);

                // CHECK NPC COLLISION
                int npcIndex = gamePanel.collisionCheck.checkElement(this, gamePanel.npc, currentMap);
                interactNPC(npcIndex);

                // CHECK MONSTER COLLISION
                int monsterIndex = gamePanel.collisionCheck.checkElement(this, gamePanel.monsters, currentMap);
                interactMonster(monsterIndex);

                //CHECK EVENT            
                gamePanel.eventHandler.checkEvent();

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
                if(gamePanel.isHosting == 1 || gamePanel.joined == 1){
                    Packet02Move packet = new Packet02Move(this.getUsername(), this.wX, this.wY, this.direction, this.spriteNumber, this.currentMap);
                    packet.writeData(GamePanel.gamePanel.socketClient);
                }
            }
            else{
                spriteNumber = 3;
                if(gamePanel.isHosting == 1 || gamePanel.joined == 1){
                    Packet02Move packet = new Packet02Move(this.getUsername(), this.wX, this.wY, this.direction, this.spriteNumber, this.currentMap);
                    packet.writeData(GamePanel.gamePanel.socketClient);
                }
                isMoving = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if(i != 999) {
            
        }
    }

    public void interactNPC(int i) {
        if(i != 999 && gamePanel.npc[currentMap][i].isTalking == 0) {
                if (gamePanel.keyInput.enterPressed == true) {
                    inDialogue = 1;
                    index=i;
                    if(gamePanel.joined==1 || gamePanel.isHosting==1){
                        Packet07ElementNPCState packet = new Packet07ElementNPCState(currentMap, i, inDialogue);
                        packet.writeData(gamePanel.socketClient);
                    }
                    gamePanel.npc[gamePanel.currentMap][i].speak();
                }
        }
        gamePanel.keyInput.enterPressed = false; 
    }

    public void interactMonster (int i) {
        if(i != 999 && gamePanel.monsters[currentMap][i].isBattling == 0) {
            if(partySize==0){
                party.add((Javamon) gamePanel.monsters[gamePanel.currentMap][i]);
                partySize++;
                monstersCaunght++;
                gamePanel.monsters[gamePanel.currentMap][i] = null;
                if(gamePanel.joined==1 || gamePanel.isHosting==1){
                    Packet05JavamonRespawn packet = new Packet05JavamonRespawn(currentMap, i);
                    packet.writeData(gamePanel.socketClient);
                }
            }
            else{
                if (party.get(0).hp <= 0) {

                    for(int j = 0; j < party.size(); j++){
                        if(party.get(j).hp > 0){
                            Collections.swap(party, j, 0);
                            break;
                        }
                    }
                }
                inBattle = 1;
                index=i;
                if(gamePanel.isHosting==1){
                    Packet06ElementState packet = new Packet06ElementState(currentMap, i, inBattle, 0);
                    packet.writeData(gamePanel.socketClient);
                }else if(gamePanel.joined==1){
                    Packet06ElementState packet = new Packet06ElementState(currentMap, i, inBattle, 1);
                    packet.writeData(gamePanel.socketClient);
                }
                else
                    gamePanel.monsters[gamePanel.currentMap][i].isBattling = 1;
                gamePanel.gameState = gamePanel.battleState;
            }
        }
    }


    public void draw(Graphics2D g2) {

        BufferedImage sprite = null;

        int sX = wX - gamePanel.character.wX + gamePanel.character.sX;
        int sY = wY - gamePanel.character.wY + gamePanel.character.sY;

        switch(direction) {
            case "up":
                if(spriteNumber ==1)
                    sprite = up1;
                if(spriteNumber ==2)
                    sprite = up2;
                if(spriteNumber ==3)
                    sprite = back;
                break;
            case "down":
                if(spriteNumber ==1)
                    sprite = down1;
                if(spriteNumber ==2)
                    sprite = down2;
                if(spriteNumber ==3)
                    sprite = front;
                break;
            case "left":
                if(spriteNumber ==1 || spriteNumber ==3)
                    sprite = left1;
                if(spriteNumber ==2)
                    sprite = left2;
                break;
            case "right":
                if(spriteNumber ==1 || spriteNumber ==3)
                    sprite = right1;
                if(spriteNumber ==2)
                    sprite = right2;
                break; 
        }
        if(inBattle == 0 && inBattleBoss == 0) {
            g2.setColor(Color.BLACK);
            g2.drawImage(sprite, sX, sY, null);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2.drawString(username, sX-10, sY-10);
        }
        else if(inBattle == 1) {
            if(gamePanel.battle!=null && gamePanel.character.inBattle == 1 && gamePanel.character.party.size() > 0)
                gamePanel.battle.setUpBattle(g2, sprite, sX, sY, direction);
            else if(gamePanel.battleplayer2!=null){
                if(gamePanel.isHosting==1 && gamePanel.players.get(1).party.size() > 0)
                    gamePanel.battleplayer2.setUpBattle(g2, sprite, sX, sY, direction);
                else if(gamePanel.joined==1 && gamePanel.players.get(0).party.size() > 0)
                    gamePanel.battleplayer2.setUpBattle(g2, sprite, sX, sY, direction);
            } 
            
        }
        else if(gamePanel.character.inBattleBoss == 1 && gamePanel.battleBoss!=null && gamePanel.character.party.size() > 0){
                gamePanel.battleBoss.setUpBattle(g2, sprite, sX, sY, direction);
            }
    }


    public void setupInventory(){   //SETS UP INVENTORY
        // Load an image
        String imagePath = "src/res/items/HealthPotion.png";
        // Get the Image from the ImageIcon
        File imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        Image image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        Itemvalue value = new Itemvalue(image, 6, 50);
        // Put the Item into the map with a key
        inventory.put("HP Potion", value);

        // Load an image
        imagePath = "src/res/items/FullHeal.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 0, 300);
        // Put the Item into the map with a key
        inventory.put("Full Heal", value);

        // Load an image
        imagePath = "src/res/items/AttackPotion.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 0, 60);
        // Put the Item into the map with a key
        inventory.put("Attack Potion", value);


        // Load an image
        imagePath = "src/res/items/DefensePotion.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 0, 64);
        // Put the Item into the map with a key
        inventory.put("Defense Potion", value);



        // Load an image
        imagePath = "src/res/items/SpeedPotion.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 0, 29);
        // Put the Item into the map with a key
        inventory.put("Speed Potion", value);


        // Load an image
        imagePath = "src/res/items/Javaball.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 5, 37);
        // Put the Item into the map with a key
        inventory.put("Javaball", value);

    }

    public String getUsername() {
        return username;
    }
}