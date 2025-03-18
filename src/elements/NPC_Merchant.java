package elements;

import java.io.File;

import javax.swing.ImageIcon;
import java.awt.Image;

import main.GamePanel;

public class NPC_Merchant extends Element{

     public NPC_Merchant(GamePanel gamePanel) {
        super (gamePanel);

        direction = "down";
        speed = 0;

        dialoguesSet = -1;

        getCharacterSprite();
        setDialogue();
        setupInventory();
    }

    public void getCharacterSprite() {
        // Load character sprites
        up1     = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        up2     = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        down1   = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        down2   = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        left1   = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        left2   = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        right1  = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        right2  = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        front   = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
        back    = setup("/res/tiles/040", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() { //Dialogues only support 2 lines of text

        dialogues[0][0] = "Welcome to the shop! \n Here you can buy and sell items!";
        dialogues[0][1] = "Take a look at what I have to offer!";
        dialogues[0][2] = "That's not enough money buddy...";
        dialogues[0][3] = "What would you like to sell?";
        dialogues[0][4] = "Goodbey! Come back soon!";
        dialogues[0][5] = "You don't have anymore of those!";

        dialogues[1][0] = "Good one!";
        dialogues[1][1] = "That will be useful!";
        dialogues[1][2] = "I'd also get that.";
        dialogues[1][3] = "That's a good choice!";

        dialogues[2][0] = "I'll take that.";
        dialogues[2][1] = "This will be useful.";
        dialogues[2][2] = "Let me take that off your bag.";
        dialogues[2][3] = "Feeling lighter?";

    }

    @Override
    public void update(int currentMap) {
        if(gamePanel.isHosting == 1 || (gamePanel.isHosting == 0 && gamePanel.joined == 0)){
            super.update(currentMap);
        }
    }

    @Override
    public void speak () {
        gamePanel.gameState = gamePanel.tradeState;
        gamePanel.ui.currentNPC = this;
    }

    public void setupInventory(){

        // Load an image
        String imagePath = "src/res/items/HealthPotion.png";
        // Get the Image from the ImageIcon
        File imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        Image image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        Itemvalue value = new Itemvalue(image, 1 ,50);
        // Put the Item into the map with a key
        inventory.put("HP Potion", value);

        // Load an image
        imagePath = "src/res/items/FullHeal.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 1, 300);
        // Put the Item into the map with a key
        inventory.put("Full Heal", value);

        // Load an image
        imagePath = "src/res/items/AttackPotion.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 1, 60);
        // Put the Item into the map with a key
        inventory.put("Attack Potion", value);

        // Load an image
        imagePath = "src/res/items/DefensePotion.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 1, 64);
        // Put the Item into the map with a key
        inventory.put("Defense Potion", value);

        // Load an image
        imagePath = "src/res/items/SpeedPotion.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 1, 29);
        // Put the Item into the map with a key
        inventory.put("Speed Potion", value);

        // Load an image
        imagePath = "src/res/items/Javaball.png";
        // Get the Image from the ImageIcon
        imageFile = new File(imagePath);
        // Create an Item with the image and an integer
        image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        value = new Itemvalue(image, 1, 37);
        // Put the Item into the map with a key
        inventory.put("Javaball", value);
    }
}
