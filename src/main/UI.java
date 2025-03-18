package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.util.Map;

import javax.swing.ImageIcon;

import elements.*;
import multiplayer.packets.Packet07ElementNPCState;

import java.awt.BasicStroke;

import java.awt.Image;

public class UI {
    
    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_Font;

    public int substateMenu = 0;

    public int substateBattle = 0;

    public int substateTrade = 0;

    public int commandNum = 0;
    public int volumeNun = 0;
    public int slotCol = 0;
    public int slotRow = 0;
    
    public int buySlot = 0;
    public int sellSlot = 0;

    public int slotColBattle = 0;
    public int slotRowBattle = 0;
    public Element monster;

    public int TitleScreenState = 0;    // 0 - Title Screen; 1 - Menu; 2 - Join Game; 3 - Info; 4 - Settings;

    public int inventorySlot = 0;

    public int partySlotX = 0;
    public int partySlotY = 0;
    public int partyNumCol1 = 0;
    public int partyNumCol2 = 0;
    public int partyNumRow = 0;

    public int boxSlotX = 0;
    public int boxSlotY = 0;
    public int boxNumCol1 = 0;
    public int boxNumCol2 = 0;
    public int boxNumCol3 = 0;
    public int boxNumCol4 = 0;
    public int boxNumRow = 0;

    public String currentDialogue = "";
    public Element currentNPC;
    int charIndex = 0;
    String combinedText = "";

    int counter = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_Font = new Font("Arial", Font.PLAIN, 25);
        monster = new Javamon(gamePanel, 1);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_Font);
        g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        if(gamePanel.gameState == gamePanel.menuState) {
            drawMenu();
        }
        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }
        if(gamePanel.gameState == gamePanel.battleState || gamePanel.gameState == gamePanel.battleBossState) {
            drawBattle();
        }
        if(gamePanel.gameState == gamePanel.dialoguesState) {
            drawDialogueScreen();
        }
        if(gamePanel.gameState == gamePanel.tradeState) {
            drawTradeScreen();
        }
        if(gamePanel.gameState == gamePanel.transitionState) {
            drawTransition();
        }
    }

    public void drawTitleScreen(){
        if (TitleScreenState == 0){
            g2.setColor(new Color(25, 25, 25));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight); 

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 97F));
            String text = "Javamon";
            int textX = getX(g2, text);
            int textY = gamePanel.tileSize * 3;
            
            //Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, textX+5, textY+5);

            //Main colour
            g2.setColor(Color.WHITE);
            g2.drawString(text, textX, textY);

            //TitleScreen Image
            int x = textX - gamePanel.tileSize;
            int y = gamePanel.screenHeight/2 - gamePanel.tileSize*2;

            g2.drawImage(monster.down1, x, y, gamePanel.tileSize*5, gamePanel.tileSize*5, null);

            //Press Enter
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            text = "Press ENTER";
            x += gamePanel.tileSize*7;
            y += gamePanel.tileSize*2;
            g2.drawString(text, x, y);

            text = "to start";
            x += gamePanel.tileSize;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
        }
        else if (TitleScreenState == 1){
            g2.setColor(new Color(25, 25, 25));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight); 

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 97F));
            String text = "Javamon";
            int textX = getX(g2, text);
            int textY = gamePanel.tileSize * 3;
            
            //Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, textX+5, textY+5);

            //Main colour
            g2.setColor(Color.WHITE);
            g2.drawString(text, textX, textY);

            //TitleScreen Image
            int x = textX - gamePanel.tileSize;
            int y = gamePanel.screenHeight/2 - gamePanel.tileSize*2;

            g2.drawImage(monster.down1, x, y, gamePanel.tileSize*5, gamePanel.tileSize*5, null);

            //Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

            text = "New Game";
            x += gamePanel.tileSize*7;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 0){
                g2.drawString(">", x + gamePanel.tileSize/3 - gamePanel.tileSize, y);
            }

            text = "Continue";
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1){
                g2.drawString(">", x + gamePanel.tileSize/3 - gamePanel.tileSize, y);
            }

            text = "Join Game";
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2){
                g2.drawString(">", x + gamePanel.tileSize/3 - gamePanel.tileSize, y);
            }

            text = "Settings";
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 3){
                g2.drawString(">", x + gamePanel.tileSize/3 - gamePanel.tileSize, y);
            }

            text = "Info";
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 4){
                g2.drawString(">", x + gamePanel.tileSize/3 - gamePanel.tileSize, y);
            }

            text = "Quit";
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 5){
                g2.drawString(">", x + gamePanel.tileSize/3 - gamePanel.tileSize, y);
            }
        }
        else if (TitleScreenState == 2){
            g2.setColor(new Color(25, 25, 25));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight); 

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 97F));
            String text = "Javamon";
            int textX = getX(g2, text);
            int textY = gamePanel.tileSize * 3;
            
            //Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, textX+5, textY+5);

            //Main colour
            g2.setColor(Color.WHITE);
            g2.drawString(text, textX, textY);

            //TitleScreen Image
            int x = textX - gamePanel.tileSize;
            int y = gamePanel.screenHeight/2 - gamePanel.tileSize*2;

            g2.drawImage(monster.down1, x, y, gamePanel.tileSize*5, gamePanel.tileSize*5, null);

            //Press Enter
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            text = "Enter IP";
            x += gamePanel.tileSize*7;
            y += gamePanel.tileSize*2;
            g2.drawString(text, x, y);

            text = "Press Enter";
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
        }
        else if(TitleScreenState == 3){
            int textX;
            int textY;

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
            g2.setColor(Color.WHITE);

            String text = "Info";
            textX = getX(g2, text);
            textY = gamePanel.tileSize;
            g2.drawString(text, textX, textY);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            text = "This game was developed by:";
            textX = gamePanel.tileSize;
            textY = gamePanel.tileSize*2;
            textY += gamePanel.tileSize/2;
            g2.drawString(text, textX, textY);

            text = "- António Jorge Gouveia Lopes, up202006725";
            textX = gamePanel.tileSize;
            textY += gamePanel.tileSize/2;
            g2.drawString(text, textX, textY);

            text = "- Francisco Bessa Lopes Câmara, up202006727";
            textX = gamePanel.tileSize;
            textY += gamePanel.tileSize/2;
            g2.drawString(text, textX, textY);

            text = "- Francisco Gonçalves Vilarinho, up202005500";
            textX = gamePanel.tileSize;
            textY += gamePanel.tileSize/2;
            g2.drawString(text, textX, textY);

            text = "- Manuel Fernando Correia Gonçalves, up202300112";
            textX = gamePanel.tileSize;
            textY += gamePanel.tileSize/2;
            g2.drawString(text, textX, textY);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
            text = "Use W,A,S,D keys or arrow keys to move";
            textX = gamePanel.tileSize;
            textY = gamePanel.tileSize*8;
            g2.drawString(text, textX, textY);

            text = "ESC to go back";
            textX = gamePanel.tileSize*11;
            textY += gamePanel.tileSize*3;
            g2.drawString(text, textX, textY);
        }
        else if(TitleScreenState == 4){  
            int textX;
            int textY;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
            g2.setColor(Color.WHITE);

            String text = "Settings";
            textX = getX(g2, text);
            textY = gamePanel.tileSize*2;
            g2.drawString(text, textX, textY);

            g2.setStroke(new BasicStroke(4));
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

            //Music
            textX = gamePanel.tileSize;
            textY += gamePanel.tileSize*2+24;
            g2.setColor(Color.WHITE);
            g2.drawString("Music", textX, textY);
            g2.drawRoundRect(gamePanel.tileSize*8, textY-gamePanel.tileSize+12, gamePanel.tileSize*6+24, 45, 10, 10);
            int volumeWidth = (gamePanel.tileSize*6+24)*gamePanel.sound.volumeScale/5;
            g2.fillRect(gamePanel.tileSize*8, textY-gamePanel.tileSize+12, volumeWidth, 45);
            if(commandNum == 0) {
                g2.setColor(Color.GRAY);
                g2.drawRoundRect(gamePanel.tileSize*8, textY-gamePanel.tileSize+12, gamePanel.tileSize*6+24, 45, 10, 10);
            }

            //SE
            textY += gamePanel.tileSize*3;
            g2.setColor(Color.WHITE);
            g2.drawString("Sound Effects", textX, textY);
            g2.drawRoundRect(gamePanel.tileSize*8, textY-gamePanel.tileSize+12, gamePanel.tileSize*6+24, 45, 10, 10 );
            int sevolumeWidth = (gamePanel.tileSize*6+24)*gamePanel.se.sevolumeScale/5;
            g2.fillRect(gamePanel.tileSize*8, textY-gamePanel.tileSize+12, sevolumeWidth, 45);
            if(commandNum == 1) {
                g2.setColor(Color.GRAY);
                g2.drawRoundRect(gamePanel.tileSize*8, textY-gamePanel.tileSize+12, gamePanel.tileSize*6+24, 45, 10, 10);
            }
        }
    }

    public void drawMenu() {

        g2.setColor(Color.WHITE);

        //sub window
        int subWindowWidth = gamePanel.tileSize * 12;
        int subWindowHeight = gamePanel.tileSize * 8;
        int subWindowX = gamePanel.tileSize * 2;
        int subWindowY = gamePanel.tileSize * 2;        

        switch (substateMenu){
            case 0:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                menuTop(g2, subWindowX, subWindowY);
                break;
            case 1:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                menuJavamon(g2, subWindowX, subWindowY);
                break;
            case 2:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                menuBag(g2, subWindowX, subWindowY);
                break;
            case 3:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                menuMultiplayer(g2, subWindowX, subWindowY);
                break;
            case 4:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                menuPlayer(g2, subWindowX, subWindowY);
                break;
            case 5:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight/2);
                menuSave(g2, subWindowX, subWindowY);
                break;
            case 6:
                drawSubWindow(g2, subWindowX + gamePanel.tileSize * 2, subWindowY-gamePanel.tileSize, subWindowWidth-gamePanel.tileSize *4, subWindowHeight+gamePanel.tileSize *2);
                menuOptions(g2, subWindowX + gamePanel.tileSize * 2, subWindowY-gamePanel.tileSize);
                break;
            case 7:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                infoOptions(g2, subWindowX, subWindowY);
                break;
            case 8:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight/2);
                menugamesaved(g2, subWindowX, subWindowY);
                break;
            case 9:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                startServer(g2, subWindowX, subWindowY);
                break;
            case 10:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                exitConfirmation(g2, subWindowX, subWindowY);
                break;
            case 11:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                javamonInfo(g2, subWindowX, subWindowY);
                break;
            case 12:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                menuBox(g2, subWindowX, subWindowY);
                break;
            case 13:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                boxInfo(g2, subWindowX, subWindowY);
                break;
        }
    }

    private void drawBattle() {

        g2.setColor(Color.WHITE);

        //sub window
        int subWindowWidth = gamePanel.tileSize * 14;
        int subWindowHeight = gamePanel.tileSize * 4;
        int subWindowX = gamePanel.tileSize * 1;
        int subWindowY = gamePanel.tileSize * 8;        

        switch (substateBattle){
            case 0:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleTop(g2, subWindowX, subWindowY);
                break;
            case 1:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleAttack(g2, subWindowX, subWindowY);
                break;
            case 2:
                subWindowWidth = gamePanel.tileSize * 12;
                subWindowHeight = gamePanel.tileSize * 8;
                subWindowX = gamePanel.tileSize * 2;
                subWindowY = gamePanel.tileSize * 2; 
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                menuBag(g2, subWindowX, subWindowY);
                break;
            case 3:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleCatch(g2, subWindowX, subWindowY);
                break;
            case 4:
                subWindowWidth = gamePanel.tileSize * 12;
                subWindowHeight = gamePanel.tileSize * 8;
                subWindowX = gamePanel.tileSize * 2;
                subWindowY = gamePanel.tileSize * 2; 
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                menuJavamon(g2, subWindowX, subWindowY);
                break;
            case 5:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleKill(g2, subWindowX, subWindowY);
                break;
            case 6:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleCaught(g2, subWindowX, subWindowY);
                break;
            case 7:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleAttacked(g2, subWindowX, subWindowY);
                break;
            case 8:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleAttacked(g2, subWindowX, subWindowY);
                break;
            case 9:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleAttack(g2, subWindowX, subWindowY);
                break;
            case 11:
                subWindowWidth = gamePanel.tileSize * 12;
                subWindowHeight = gamePanel.tileSize * 8;
                subWindowX = gamePanel.tileSize * 2;
                subWindowY = gamePanel.tileSize * 2; 
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                javamonInfo(g2, subWindowX, subWindowY);
                break;
            case 12:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                battleLost(g2, subWindowX, subWindowY);
                break;
            case 13:
                drawSubWindow(g2, subWindowX, subWindowY, subWindowWidth, subWindowHeight);
                levelUp(g2, subWindowX, subWindowY);
                break;

        }
    }

    public void menuTop(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        String imagePath;
        File imageFile;
        Image bag, saveicon, player, optionsicon, javamonicon, map;

        final int slotXstart = subWindowX + gamePanel.tileSize;
        final int slotYstart = subWindowY + gamePanel.tileSize * 2;

        //Cursor
        int cursorX = slotXstart + gamePanel.tileSize*4 * slotCol;
        int cursorY = slotYstart + gamePanel.tileSize*3 * slotRow;
        int cursorWidth = gamePanel.tileSize;
        int cursorHeight = gamePanel.tileSize;

        String text = "Menu";
         g2.setStroke(new BasicStroke(3));
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);

        //Javamon
        imagePath = "src/res/others/JavamonIcon.png";
        imageFile = new File(imagePath);
        javamonicon = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize * 4;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX, subWindowY + gamePanel.tileSize*2, 48*2, 48*2,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX, subWindowY + gamePanel.tileSize*2, 48*2, 48*2, 10, 10);
        g2.drawImage(javamonicon, textX + 10, subWindowY + gamePanel.tileSize*2 + 10, gamePanel.tileSize + 30, gamePanel.tileSize + 30,null);
        g2.drawString("Javamon", textX, textY+24);

        //Bag
        imagePath = "src/res/others/BagIcon.png";
        imageFile = new File(imagePath);
        bag = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize * 4;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX-18, subWindowY + gamePanel.tileSize*2, 48*2, 48*2,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX-18, subWindowY + gamePanel.tileSize*2, 48*2, 48*2, 10, 10);
        g2.drawImage(bag, textX - 7, subWindowY + gamePanel.tileSize*2 + 5, gamePanel.tileSize + 30, gamePanel.tileSize + 30,null);
        g2.setColor(Color.WHITE);
        g2.drawString("Bag", textX+10, textY+24);

        //Map
        imagePath = "src/res/others/MultIcon.png";
        imageFile = new File(imagePath);
        map = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        textX = subWindowX + gamePanel.tileSize * 9;
        textY = subWindowY + gamePanel.tileSize * 4;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX, subWindowY + gamePanel.tileSize*2, 48*2, 48*2,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX, subWindowY + gamePanel.tileSize*2, 48*2, 48*2, 10, 10);
        g2.drawImage(map, textX+9, subWindowY + gamePanel.tileSize*2 + 5, gamePanel.tileSize + 30, gamePanel.tileSize + 30,null);
        g2.setColor(Color.WHITE);
        g2.drawString("Multiplayer", textX-12, textY+24);

        //Player Info
        imagePath = "src/res/others/PlayerIcon.png";
        imageFile = new File(imagePath);
        player = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        textX = subWindowX + gamePanel.tileSize ;
        textY = subWindowY + gamePanel.tileSize * 6;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX, subWindowY + gamePanel.tileSize*5, 48*2, 48*2,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawImage(player, textX + 10, subWindowY + gamePanel.tileSize*5 + 15, gamePanel.tileSize + 30, gamePanel.tileSize + 30,null);
        g2.drawRoundRect(textX, textY-48, 48*2, 48*2, 10, 10);
        g2.drawString("Player", textX + 12, textY+72);

        //Save
        imagePath = "src/res/others/SaveIcon.png";
        imageFile = new File(imagePath);
        saveicon = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize * 6;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX-18, subWindowY + gamePanel.tileSize*5, 48*2, 48*2,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawImage(saveicon, textX - 7, subWindowY + gamePanel.tileSize*5 + 10, gamePanel.tileSize + 30, gamePanel.tileSize + 30,null);
        g2.drawRoundRect(textX-18, textY-48, 48*2, 48*2, 10, 10);
        g2.drawString("Save", textX+6, textY+72);

        //Options
        imagePath = "src/res/others/OptionsIcon.png";
        imageFile = new File(imagePath);
        optionsicon = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        textX = subWindowX + gamePanel.tileSize * 9;
        textY = subWindowY + gamePanel.tileSize * 6;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX + 3, subWindowY + gamePanel.tileSize*5, 48*2, 48*2,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawImage(optionsicon, textX + 7, subWindowY + gamePanel.tileSize*5 + 10, gamePanel.tileSize + 30, gamePanel.tileSize + 30,null);
        g2.drawRoundRect(textX, textY-48, 48*2, 48*2, 10, 10);
        g2.drawString("Options", textX+6, textY+72);
        
        g2.setColor(Color.orange);
        g2.drawRoundRect(cursorX, cursorY, cursorWidth * 2, cursorHeight * 2, 10, 10);
    }

    public void menuJavamon(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        //Party Title
        g2.setColor(Color.BLUE);
        String text = "Party";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        // SLOTS INITIAL POSITIONS
        final int slotXstart = subWindowX + gamePanel.tileSize + 30;
        final int slotYstart = subWindowY + gamePanel.tileSize + 24;
 
        int slotX = slotXstart;
        int slotY = slotYstart;
        int i = 0;
        int lenght;
        int x;

        if (gamePanel.character.partySize < 4){
            partyNumCol1 = gamePanel.character.partySize;
            partyNumCol2 = 0;
            partyNumRow = 1;
        }
        else {
            partyNumCol1 = 3;
            partyNumCol2 = gamePanel.character.partySize - 3;
            partyNumRow = 2;
        }

        if (gamePanel.character.partySize == 0){    // No Javamons in Party 
            g2.setColor(Color.WHITE);
            g2.drawString("No Javamons in the party", getX(g2, "No Javamons in the party"), textY + gamePanel.tileSize*2);
        }
        else{

            for (Element monster : gamePanel.character.party) {
                
                //SLOT
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));
                if(i < 3){
                    slotX = slotXstart + (gamePanel.tileSize*3 + 12) * i;
                    slotY = slotYstart;
                    g2.drawRoundRect(slotX, slotY, gamePanel.tileSize * 2 + 12, gamePanel.tileSize * 2 + 12, 10, 10); 
                    g2.drawImage(monster.down1, slotX + 6, slotY + 6, gamePanel.tileSize*2, gamePanel.tileSize*2, null);        // DRAW JAVAMON ICON
               }
                else{
                    slotX = slotXstart + (gamePanel.tileSize*3 + 12) * (i - 3);
                    slotY = slotYstart + (gamePanel.tileSize*3 + 12);
                    g2.drawRoundRect(slotX, slotY, gamePanel.tileSize * 2 + 12, gamePanel.tileSize * 2 + 12, 10, 10); 
                    g2.drawImage(monster.down1, slotX + 6, slotY + 6, gamePanel.tileSize*2, gamePanel.tileSize*2, null);        // DRAW JAVAMON ICON
                }

                lenght = (int) g2.getFontMetrics().getStringBounds(monster.name, g2).getWidth();
                x = slotX + ((gamePanel.tileSize * 2 + 12)/2 - lenght/2);
                g2.drawString(monster.nickname, x, slotY + gamePanel.tileSize*3 - 6);

                i++;
            }

            // CURSOR POSITION
            int cursorX = slotXstart + (gamePanel.tileSize*3 + 12) * partySlotX;
            int cursorY = slotYstart + (gamePanel.tileSize*3 + 12) * partySlotY;
            int cursorWidth = gamePanel.tileSize * 2 + 12;
            int cursorHeight = gamePanel.tileSize * 2 + 12;
    
            // DRAW CURSOR
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);  
        
        }

    }

    public void javamonInfo(Graphics2D g2, int subWindowX, int subWindowY){

        Javamon monster = gamePanel.character.party.get(partySlotX + partySlotY*3);

        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        String text = monster.nickname;
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        textX = subWindowX + gamePanel.tileSize*5 + 24;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX + gamePanel.tileSize*2 - 12, textY + gamePanel.tileSize - 24, gamePanel.tileSize*4, gamePanel.tileSize*5-24,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX + gamePanel.tileSize*2 - 12, textY + gamePanel.tileSize - 24, gamePanel.tileSize*4, gamePanel.tileSize*5-24, 10, 10);
        g2.drawImage(monster.down1, textX + gamePanel.tileSize*2 - 6, textY + gamePanel.tileSize - 18, gamePanel.tileSize*4 - 12, gamePanel.tileSize*4 - 12, null);
        
        textX = subWindowX + gamePanel.tileSize + 24;
        textY = subWindowY + gamePanel.tileSize*2 + 12;

        g2.drawString("Species: " + monster.name, textX, textY);
    
        textY += gamePanel.tileSize - 12;
        g2.drawString("Level: " + monster.level, textX, textY);

        textY += gamePanel.tileSize - 12;
        g2.drawString("HP: " + monster.hp + "/" + monster.maxHp, textX, textY);

        textY += gamePanel.tileSize - 12;
        g2.drawString("Attack: " + monster.attack, textX, textY);

        textY += gamePanel.tileSize - 12;
        g2.drawString("Defense: " + monster.defense, textX, textY);

        textY += gamePanel.tileSize - 12;
        g2.drawString("Speed: " + monster.speedstat, textX, textY);

        g2.setStroke(new BasicStroke(2));

        textY += gamePanel.tileSize - 24;
        textX = subWindowX + gamePanel.tileSize - 36;
        text = "Change name";
        int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        g2.drawString(text, textX + 12, textY + gamePanel.tileSize - 18);

        if(commandNum == 0){
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        }

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        textX += lenght + 30;
        text = "Swap to first";
        lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        g2.drawString(text, textX + 12, textY + gamePanel.tileSize - 18);

        if(commandNum == 1){
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        }

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        textX += lenght + 30;
        text = "Box";
        lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        g2.drawString(text, textX + 12, textY + gamePanel.tileSize - 18);

        if(commandNum == 2){
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        }

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        textX += lenght + 30;
        text = "Release";
        lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        g2.drawString(text, textX + 12, textY + gamePanel.tileSize - 18);

        if(commandNum == 3){
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        }
  
    }

    public void menuBox(Graphics2D g2, int subWindowX, int subWindowY){

        int textX;
        int textY;

        //Party Title
        g2.setColor(Color.BLUE);
        String text = "Box";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        // SLOTS INITIAL POSITIONS
        final int slotXstart = subWindowX + gamePanel.tileSize - 24;
        final int slotYstart = subWindowY + gamePanel.tileSize + 24;
 
        int slotX = slotXstart;
        int slotY = slotYstart;
        int i = 0;
        int j = 0;

        boxNumRow = gamePanel.character.boxSize / 7 + 1;
        switch (boxNumRow){
            case 1:
                boxNumCol1 = gamePanel.character.boxSize;
                boxNumCol2 = 0;
                boxNumCol3 = 0;
                boxNumCol4 = 0;
                break;
            case 2:
                boxNumCol1 = 7;
                boxNumCol2 = gamePanel.character.boxSize - 7;
                boxNumCol3 = 0;
                boxNumCol4 = 0;
                break;
            case 3:
                boxNumCol1 = 7;
                boxNumCol2 = 7;
                boxNumCol3 = gamePanel.character.boxSize - 14;
                boxNumCol4 = 0;
                break;
            case 4:
                boxNumCol1 = 7;
                boxNumCol2 = 7;
                boxNumCol3 = 7;
                boxNumCol4 = gamePanel.character.boxSize - 21;
                break;
        }

        if (gamePanel.character.boxSize == 0){    // No Javamons in Box 
            g2.setColor(Color.WHITE);
            g2.drawString("No Javamons in the box", getX(g2, "No Javamons in the box"), textY + gamePanel.tileSize*2);
        }
        else{

            for (Element monster : gamePanel.character.box) {
                
                //SLOT
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));

                slotX = slotXstart + (gamePanel.tileSize + 30) * i;
                slotY = slotYstart + (gamePanel.tileSize + 30) * j;
                g2.drawRoundRect(slotX, slotY, gamePanel.tileSize + 12, gamePanel.tileSize + 12, 10, 10); 
                g2.drawImage(monster.down1, slotX + 6, slotY + 6, gamePanel.tileSize, gamePanel.tileSize, null);        // DRAW JAVAMON ICON

                i++;

                if(i == 7){
                    i = 0;
                    j++;
                }
            }

            // CURSOR POSITION
            int cursorX = slotXstart + (gamePanel.tileSize + 30) * boxSlotX;
            int cursorY = slotYstart + (gamePanel.tileSize + 30) * boxSlotY;
            int cursorWidth = gamePanel.tileSize + 12;
            int cursorHeight = gamePanel.tileSize + 12;
    
            // DRAW CURSOR
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);  
        }
    }

    public void boxInfo(Graphics2D g2, int subWindowX, int subWindowY){
        Javamon monster = gamePanel.character.box.get(boxSlotX + boxSlotY*7);

        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        String text = monster.nickname;
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        textX = subWindowX + gamePanel.tileSize*5 + 24;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX + gamePanel.tileSize*2 - 12, textY + gamePanel.tileSize - 24, gamePanel.tileSize*4, gamePanel.tileSize*5-24,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX + gamePanel.tileSize*2 - 12, textY + gamePanel.tileSize - 24, gamePanel.tileSize*4, gamePanel.tileSize*5-24, 10, 10);
        g2.drawImage(monster.down1, textX + gamePanel.tileSize*2 - 6, textY + gamePanel.tileSize - 18, gamePanel.tileSize*4 - 12, gamePanel.tileSize*4 - 12, null);
        
        textX = subWindowX + gamePanel.tileSize + 24;
        textY = subWindowY + gamePanel.tileSize*2 + 12;

        g2.drawString("Species: " + monster.name, textX, textY);
    
        textY += gamePanel.tileSize - 12;
        g2.drawString("Level: " + monster.level, textX, textY);

        textY += gamePanel.tileSize - 12;
        g2.drawString("HP: " + monster.hp, textX, textY);

        textY += gamePanel.tileSize - 12;
        g2.drawString("Attack: " + monster.attack, textX, textY);

        textY += gamePanel.tileSize - 12;
        g2.drawString("Defense: " + monster.defense, textX, textY);

        textY += gamePanel.tileSize - 12;
        g2.drawString("Speed: " + monster.speedstat, textX, textY);

        g2.setStroke(new BasicStroke(2));

        textY += gamePanel.tileSize - 24;
        text = "Swap in Party";
        int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        g2.drawString(text, textX + 12, textY + gamePanel.tileSize - 18);

        if(commandNum == 0){
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        }

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        textX += lenght + 42;
        text = "Release";
        lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        g2.drawString(text, textX + 12, textY + gamePanel.tileSize - 18);

        if(commandNum == 1){
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(textX, textY, lenght + 24, gamePanel.tileSize, 10, 10);
        }
    }

    public void menuBag(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        // BAG TITLE
        g2.setColor(Color.white);
        String text = "Bag";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);

        // SLOTS INITIAL POSITIONS
        final int slotXstart = subWindowX + 20;
        final int slotYstart = subWindowY + gamePanel.tileSize*2;
 
        int slotX = slotXstart;
        int slotY = slotYstart;

        for (Map.Entry<String, elements.Element.Itemvalue> entry : gamePanel.character.inventory.entrySet()) {
            String key = entry.getKey();
            elements.Element.Itemvalue value = entry.getValue();

            g2.drawImage(value.image, slotX + 20, slotY-5, gamePanel.tileSize - 10, gamePanel.tileSize - 10,null);        // DRAW ITEM ICON

            g2.setColor(Color.WHITE);   
            g2.drawString(key, slotX + gamePanel.tileSize*2, slotY+ gamePanel.tileSize - 25);                                       // DRAW NAME OF ITEM
            g2.drawString("x" + Integer.toString(value.quantity), slotX + gamePanel.tileSize*10, slotY+ gamePanel.tileSize - 25);   // DRAW QUANTITY

            slotY += gamePanel.tileSize;    // MOVE TO NEXT SLOT
        }
        
        // CURSOR POSITION
        int cursorX = slotXstart;
        int cursorY = slotYstart + gamePanel.tileSize * inventorySlot-10;
        int cursorWidth = gamePanel.tileSize*11 + 7;
        int cursorHeight = gamePanel.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    public void menuMultiplayer(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.YELLOW);

        String text = "Multiplayer";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);

    }

    public void menuPlayer(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.ORANGE);

        String text = "Player Info";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize+12;
        g2.drawString(text, textX, textY);

        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX + gamePanel.tileSize*2, textY + gamePanel.tileSize - 12, gamePanel.tileSize*4, gamePanel.tileSize*5-24,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX + gamePanel.tileSize*2, textY + gamePanel.tileSize - 12, gamePanel.tileSize*4, gamePanel.tileSize*5-24, 10, 10);
        g2.drawImage(gamePanel.character.front, textX + gamePanel.tileSize*2, textY + gamePanel.tileSize, gamePanel.tileSize*4, gamePanel.tileSize*4, null);
        
        textX = subWindowX + gamePanel.tileSize - 24;
        textY = subWindowY + gamePanel.tileSize*2+30;
        g2.drawString("Name: " + gamePanel.character.username, textX, textY);

        textY += gamePanel.tileSize;
        double timePlayed = gamePanel.stopwatch.getElapsedTimeSeconds() / 60;   
        String timePlayedFormatted = String.format("%.0f", timePlayed);
        g2.drawString("Time Played: " + timePlayedFormatted + " minutes", textX, textY);

        textY += gamePanel.tileSize;
        g2.drawString("Javamons caught: " + gamePanel.character.monstersCaunght, textX, textY);

        textY += gamePanel.tileSize;
        g2.drawString("Money: " + gamePanel.character.money + " €", textX, textY);
    }

    public void menuSave(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.GREEN);

        String text = "Save";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);


        textX = subWindowX + gamePanel.tileSize;
        textY += gamePanel.tileSize+24;
        g2.setColor(Color.WHITE);
        g2.drawString("Press Enter to save your game!", textX, textY);
        
    }

    public void menugamesaved(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.GREEN);

        String text = "Save";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);


        textX = subWindowX + gamePanel.tileSize;
        textY += gamePanel.tileSize+24;
        g2.setColor(Color.WHITE);
        g2.drawString("Your game has been saved!", textX, textY);
    }

    public void menuOptions(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;
        g2.setColor(Color.GRAY);

        String text = "Options";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize - 12;
        g2.drawString(text, textX, textY);

        //Music
        textX = subWindowX + gamePanel.tileSize;
        textY += gamePanel.tileSize - 12;
        g2.setColor(Color.WHITE);
        g2.drawString("Music", textX, textY);
        g2.drawRoundRect(textX-12, textY+12, gamePanel.tileSize*6+24, 30, 10, 10);
        int volumeWidth = (gamePanel.tileSize*6+24)*gamePanel.sound.volumeScale/5;
        g2.fillRect(textX-12, textY+12, volumeWidth, 30);
        if(commandNum == 0) {
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(textX-12, textY+12, gamePanel.tileSize*6+24, 30, 10, 10);
        }

        //SE
        textY += gamePanel.tileSize*2 - 24;
        g2.setColor(Color.WHITE);
        g2.drawString("Sound Effects", textX, textY);
        g2.drawRoundRect(textX - 12, textY+12, gamePanel.tileSize*6+24, 30, 10, 10 );
        int sevolumeWidth = (gamePanel.tileSize*6+24)*gamePanel.se.sevolumeScale/5;
        g2.fillRect(textX-12, textY+12, sevolumeWidth, 30);
        if(commandNum == 1) {
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(textX-12, textY+12, gamePanel.tileSize*6+24, 30, 10, 10);
        }

        //Server
        textY += gamePanel.tileSize*2;
        g2.setColor(Color.WHITE);
        if(gamePanel.isHosting == 0 && gamePanel.joined == 0) {
            g2.drawString("Start Server", textX, textY);
        }
        else if(gamePanel.isHosting == 1){
            g2.drawString("Hosting", textX, textY);
        }
        else if(gamePanel.joined == 1){
            g2.drawString("Joined", textX, textY);
        }
        g2.drawRoundRect(textX-12, textY-33, gamePanel.tileSize*6+24, gamePanel.tileSize, 10, 10);
        if(commandNum == 2) {
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(textX-12, textY-33, gamePanel.tileSize*6+24,  gamePanel.tileSize, 10, 10);
        }
        
        //Info
        textY += gamePanel.tileSize+18;
        g2.setColor(Color.WHITE);
        g2.drawString("Info", textX, textY);
        g2.drawRoundRect(textX-12, textY-33, gamePanel.tileSize*6+24, gamePanel.tileSize, 10, 10);
        if(commandNum == 3) {
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(textX-12, textY-33, gamePanel.tileSize*6+24,  gamePanel.tileSize, 10, 10);
        }

        //Leave Game
        textY += gamePanel.tileSize+18;
        g2.setColor(Color.WHITE);
        g2.drawString("Leave Game", textX, textY);
        g2.drawRoundRect(textX-12, textY-33, gamePanel.tileSize*6+24, gamePanel.tileSize, 10, 10);
        if(commandNum == 4) {
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(textX-12, textY-33, gamePanel.tileSize*6+24,  gamePanel.tileSize, 10, 10);
        }

        //Back
        textY += gamePanel.tileSize+18;
        g2.setColor(Color.WHITE);
        g2.drawString("Back", textX, textY);
        g2.drawRoundRect(textX-12, textY-33, gamePanel.tileSize*6+24, gamePanel.tileSize, 10, 10);
        if(commandNum == 5) {
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(textX-12, textY-33, gamePanel.tileSize*6+24,  gamePanel.tileSize, 10, 10);
        }
    }

    public void infoOptions(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        String text = "Info";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);

        text = "Use W,A,S,D to move or arrow keys";
        textX = subWindowX + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY+gamePanel.tileSize*2);

        text = "Press ENTER to select";
        g2.drawString(text, textX, textY+gamePanel.tileSize*3);

        text = "ESC to go back";
        textX = subWindowX + gamePanel.tileSize*7;
        textY = gamePanel.tileSize*5;
        g2.drawString(text, textX, textY+gamePanel.tileSize*4);
    }

    public void startServer(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        String text = "Start Server";
        textX = getX(g2, text);
        textY = subWindowY + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);

        text = "Would you like to start a server?";
        textX = subWindowX + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY+gamePanel.tileSize*2);

        text = "Press ENTER to start server";
        g2.drawString(text, textX, textY+gamePanel.tileSize*4);
    }

    public void exitConfirmation(Graphics2D g2, int subWindowX, int subWindowY){

        int textX;
        int textY;
        String text;

        g2.setColor(Color.WHITE);

        textY = subWindowY + gamePanel.tileSize+10;
        text = "Are you sure you want to exit?";
        textX = getX(g2, text);
        g2.drawString(text, textX, textY+gamePanel.tileSize*2);

        text = "YES";
        textX = getX(g2, text);
        g2.drawString(text, textX - gamePanel.tileSize-24, textY+gamePanel.tileSize*4);

        text = "NO";
        textX = getX(g2, text);
        g2.drawString(text, textX + gamePanel.tileSize+24, textY+gamePanel.tileSize*4);

        if(commandNum == 0){
            g2.drawString(">", textX - gamePanel.tileSize*2, textY+gamePanel.tileSize*4);
        }
        else if(commandNum == 1){
            g2.drawString(">", textX + gamePanel.tileSize+6, textY+gamePanel.tileSize*4);
        }
    }

    private void battleTop(Graphics2D g22, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        final int slotXstart = subWindowX + gamePanel.tileSize;
        final int slotYstart = subWindowY + gamePanel.tileSize-12;

        //Cursor
        int cursorX = slotXstart + gamePanel.tileSize*8 * slotColBattle;
        int cursorY = slotYstart + (gamePanel.tileSize*1+24) * slotRowBattle;
        int cursorWidth = gamePanel.tileSize*2;
        int cursorHeight = gamePanel.tileSize/2;


        //Attack
        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX, subWindowY + 36, gamePanel.tileSize*4, gamePanel.tileSize,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX, subWindowY + 36, gamePanel.tileSize*4, gamePanel.tileSize,  10, 10);
        g2.drawString("Attack", textX+gamePanel.tileSize+8, textY+gamePanel.tileSize+20);

        //Bag
        textX = subWindowX + gamePanel.tileSize*9;
        textY = subWindowY;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX, subWindowY +36, gamePanel.tileSize*4, gamePanel.tileSize,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX, subWindowY +36, gamePanel.tileSize*4, gamePanel.tileSize,  10, 10);
        g2.drawString("Bag", textX+gamePanel.tileSize+24, textY+gamePanel.tileSize+20);

        //Party
        textX = subWindowX + gamePanel.tileSize*9;
        textY = subWindowY + gamePanel.tileSize*1;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX, subWindowY + gamePanel.tileSize*2+12, gamePanel.tileSize*4, gamePanel.tileSize,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX, subWindowY + gamePanel.tileSize*2+12, gamePanel.tileSize*4, gamePanel.tileSize,  10, 10);
        g2.drawString("Party", textX+gamePanel.tileSize+16, textY+gamePanel.tileSize*2-4);

        //Catch
        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize*1;
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(textX, subWindowY + gamePanel.tileSize*2+12, gamePanel.tileSize*4, gamePanel.tileSize,  10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(textX, subWindowY + gamePanel.tileSize*2+12, gamePanel.tileSize*4, gamePanel.tileSize,  10, 10);
        g2.drawString("Catch", textX+gamePanel.tileSize+12, textY+gamePanel.tileSize*2-4);
        
        g2.setColor(Color.orange);
        g2.drawRoundRect(cursorX, cursorY, cursorWidth * 2, cursorHeight * 2, 10, 10);
    }

    private void battleAttack(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize+34;
        g2.setColor(Color.WHITE);
        g2.drawString("Attack!", textX, textY+gamePanel.tileSize/2);
    }

    private void battleAttacked(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize+34;
        g2.setColor(Color.WHITE);
        g2.drawString("Foe Attacked", textX, textY+gamePanel.tileSize/2);
    }

    private void battleCatch(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize+34;
        g2.setColor(Color.WHITE);
        g2.drawString("...", textX, textY+gamePanel.tileSize);
    }

    private void battleKill(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize+34;
        g2.setColor(Color.WHITE);
        g2.drawString("Javamon Defeated!", textX, textY-24);
        g2.drawString("Earned 8XP!", textX, textY+gamePanel.tileSize-24);
        g2.drawString("Earned 30€!", textX, textY+gamePanel.tileSize*2-24);
    }

    private void battleCaught(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize+34;
        g2.setColor(Color.WHITE);
        g2.drawString("Javamon Caught!", textX, textY-24);
        g2.drawString("Earned 5XP!", textX, textY+gamePanel.tileSize-24);
        g2.drawString("Earned 30€!", textX, textY+gamePanel.tileSize*2-24);
    }

    private void battleLost(Graphics2D g2, int subWindowX, int subWindowY) {
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize+34;
        g2.setColor(Color.WHITE);
        g2.drawString("You lost!", textX, textY+gamePanel.tileSize/2);
    }
    
    private void levelUp(Graphics2D g2, int subWindowX, int subWindowY){
        int textX;
        int textY;

        g2.setColor(Color.WHITE);

        textX = subWindowX + gamePanel.tileSize;
        textY = subWindowY + gamePanel.tileSize+34;
        g2.setColor(Color.WHITE);
        if(gamePanel.keyInput.lvlup == true){
            g2.drawString("Level Up!", textX, textY);
            g2.drawString("Javamon EXP: " + gamePanel.character.party.get(0).exp, textX, textY+gamePanel.tileSize);
        }
        else {
            g2.drawString("Javamon EXP: " + gamePanel.character.party.get(0).exp, textX, textY+gamePanel.tileSize/2);
        }
    }

    public void drawTradeScreen (){

        switch (substateTrade) {
            case 0:
                tradeSelect();
                break;
            case 1:
                tradeBuy();
                break;
            case 2:
                tradeSell();
                break;
        }

        gamePanel.keyInput.enterPressed = false;
        gamePanel.keyInput.escPressed = false;
    }

    public void tradeSelect(){

        currentNPC.dialoguesSet = 0;
        currentNPC.dialoguesIndex = 0;
        drawDialogueScreen();

        gamePanel.ui.buySlot = 0;
        gamePanel.ui.sellSlot = 0;

        //DRAW WINDOW
        int x = gamePanel.tileSize*11;
        int y = gamePanel.tileSize*5;
        int width = gamePanel.tileSize*3;
        int height = gamePanel.tileSize*3 + 24;
        drawSubWindow(g2, x, y, width, height);

        //DRAW TEXT 
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-24, y);
            if(gamePanel.keyInput.enterPressed == true){
                substateTrade = 1;
                commandNum = 0;
                gamePanel.keyInput.enterPressed = false;
                currentNPC.dialoguesSet = 0;
                currentNPC.dialoguesIndex = 1;
            }
        }

        y += gamePanel.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString(">", x-24, y);
            if(gamePanel.keyInput.enterPressed == true){
                substateTrade = 2;
                commandNum = 0;
                gamePanel.keyInput.enterPressed = false;
                currentNPC.dialoguesSet = 0;
                currentNPC.dialoguesIndex = 3;
            }
        }

        y += gamePanel.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 2){
            g2.drawString(">", x-24, y);
            if(gamePanel.keyInput.enterPressed == true){
                gamePanel.keyInput.enterPressed = false;
                commandNum = 0;
                currentNPC.dialoguesSet = 0;
                currentNPC.dialoguesIndex = 4;
                gamePanel.gameState = gamePanel.playState;
            }
        }

    }

    public void tradeBuy(){
        
        drawDialogueScreen();

        //DRAW PLAYER INVENTORY
        int x = gamePanel.tileSize*9 - 12;
        int y = gamePanel.tileSize - 24 ;
        int width = gamePanel.tileSize*7;
        int height = gamePanel.tileSize*8;

        drawSubWindow(g2, x, y, width, height);   

        int textX;
        int textY;
        // BAG TITLE
        g2.setColor(Color.white);
        String text = "Bag";
        textX = x + gamePanel.tileSize*3;
        textY = y + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);

        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(textX + gamePanel.tileSize*2-24, y+gamePanel.tileSize/2, gamePanel.tileSize * 2 +12, gamePanel.tileSize, 10, 10);
        g2.drawString(Integer.toString(gamePanel.character.money) + " €", textX-12 + gamePanel.tileSize*2 + 6, textY);

        // SLOTS INITIAL POSITIONS
        int slotXstart = x - 6;
        int slotYstart = y + gamePanel.tileSize*2 - 18;
 
        int slotX = slotXstart;
        int slotY = slotYstart;

        for (Map.Entry<String, elements.Element.Itemvalue> entry : gamePanel.character.inventory.entrySet()) {
            String key = entry.getKey();
            elements.Element.Itemvalue value = entry.getValue();

            g2.drawImage(value.image, slotX + 20, slotY + 5, gamePanel.tileSize - 10, gamePanel.tileSize - 10,null);        // DRAW ITEM ICON

            g2.setColor(Color.WHITE);   
            g2.drawString(key, slotX + gamePanel.tileSize + 24, slotY+ gamePanel.tileSize - 15);                                       // DRAW NAME OF ITEM
            g2.drawString("x" + Integer.toString(value.quantity), slotX + gamePanel.tileSize*6 - 12, slotY+ gamePanel.tileSize - 15);   // DRAW QUANTITY

            slotY += gamePanel.tileSize;    // MOVE TO NEXT SLOT
        }
    
        //DRAW NPC INVENTORY

        x = gamePanel.tileSize - 36;
        y = gamePanel.tileSize - 24 ;
        width = gamePanel.tileSize*7;
        height = gamePanel.tileSize*8;
        drawSubWindow(g2, x, y, width, height);   

        // BAG TITLE
        g2.setColor(Color.white);
        text = "Shop";
        textX = x + gamePanel.tileSize*3 - 12;
        textY = y + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);

        // SLOTS INITIAL POSITIONS
        slotXstart = x - 6;
        slotYstart = y + gamePanel.tileSize*2 - 18;
 
        slotX = slotXstart;
        slotY = slotYstart;

        for (Map.Entry<String, elements.Element.Itemvalue> entry : gamePanel.ui.currentNPC.inventory.entrySet()) {
            String key = entry.getKey();
            elements.Element.Itemvalue value = entry.getValue();

            g2.drawImage(value.image, slotX + 20, slotY + 5, gamePanel.tileSize - 10, gamePanel.tileSize - 10,null);    // DRAW ITEM ICON

            g2.setColor(Color.WHITE);   
            g2.drawString(key, slotX + gamePanel.tileSize + 24, slotY+ gamePanel.tileSize - 15);    // DRAW NAME OF ITEM
            g2.drawString(Integer.toString(value.price) + "€", slotX + gamePanel.tileSize*5 + 24, slotY+ gamePanel.tileSize - 15); // DRAW PRICE

            slotY += gamePanel.tileSize;    // MOVE TO NEXT SLOT
        }

        // CURSOR POSITION
        int cursorX = slotXstart + 18;
        int cursorY = slotYstart + gamePanel.tileSize * buySlot;
        int cursorWidth = gamePanel.tileSize*6 + 24;
        int cursorHeight = gamePanel.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //ENTER PRESSED IN ITEM
        if(gamePanel.keyInput.enterPressed == true){
            if( gamePanel.character.money >= gamePanel.ui.currentNPC.inventory.get(gamePanel.ui.currentNPC.inventory.keySet().toArray()[buySlot]).price){
                gamePanel.character.money -= gamePanel.ui.currentNPC.inventory.get(gamePanel.ui.currentNPC.inventory.keySet().toArray()[buySlot]).price;
                gamePanel.character.inventory.get(gamePanel.ui.currentNPC.inventory.keySet().toArray()[buySlot]).quantity++;
                currentNPC.dialoguesSet = 1;
                currentNPC.dialoguesIndex++;

                if(currentNPC.dialoguesIndex == 4){
                    currentNPC.dialoguesSet = 1;
                    currentNPC.dialoguesIndex = 0;
                    charIndex = 0;
                    combinedText = "";
                }
                
                drawDialogueScreen();
            }
            else{
                currentNPC.dialoguesSet = 0;
                currentNPC.dialoguesIndex = 2;
                charIndex = 0;
                combinedText = "";
                drawDialogueScreen();
            }
            gamePanel.keyInput.enterPressed = false;
        }

        if(gamePanel.keyInput.escPressed == true){
            charIndex = 0;
            combinedText = "";
            substateTrade = 0;
            commandNum = 0;
            gamePanel.keyInput.escPressed = false;
        }
    }

    public void tradeSell(){
        drawDialogueScreen();

        //DRAW PLAYER INVENTORY
        int x = gamePanel.tileSize*9 - 12;
        int y = gamePanel.tileSize - 24 ;
        int width = gamePanel.tileSize*7;
        int height = gamePanel.tileSize*8;

        drawSubWindow(g2, x, y, width, height);   

        int textX;
        int textY;
        // BAG TITLE
        g2.setColor(Color.white);
        String text = "Bag";
        textX = x + gamePanel.tileSize*3;
        textY = y + gamePanel.tileSize+10;
        g2.drawString(text, textX, textY);

        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(textX + gamePanel.tileSize*2-24, y+gamePanel.tileSize/2, gamePanel.tileSize * 2 +12, gamePanel.tileSize, 10, 10);
        g2.drawString(Integer.toString(gamePanel.character.money) + " €", textX-12 + gamePanel.tileSize*2 + 6, textY);

        // SLOTS INITIAL POSITIONS
        int slotXstart = x - 6;
        int slotYstart = y + gamePanel.tileSize*2 - 18;
 
        int slotX = slotXstart;
        int slotY = slotYstart;

        for (Map.Entry<String, elements.Element.Itemvalue> entry : gamePanel.character.inventory.entrySet()) {
            String key = entry.getKey();
            elements.Element.Itemvalue value = entry.getValue();

            g2.drawImage(value.image, slotX + 20, slotY + 5, gamePanel.tileSize - 10, gamePanel.tileSize - 10,null);        // DRAW ITEM ICON

            g2.setColor(Color.WHITE);   
            g2.drawString(key, slotX + gamePanel.tileSize + 24, slotY+ gamePanel.tileSize - 15);                                       // DRAW NAME OF ITEM
            g2.drawString("x" + Integer.toString(value.quantity), slotX + gamePanel.tileSize*6 - 12, slotY+ gamePanel.tileSize - 15);   // DRAW QUANTITY

            slotY += gamePanel.tileSize;    // MOVE TO NEXT SLOT
        }

        // CURSOR POSITION
        int cursorX = slotXstart + 18;
        int cursorY = slotYstart + gamePanel.tileSize * sellSlot;
        int cursorWidth = gamePanel.tileSize*6 + 24;
        int cursorHeight = gamePanel.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //ENTER PRESSED IN ITEM
        if(gamePanel.keyInput.enterPressed == true){
            if( gamePanel.character.inventory.get(gamePanel.character.inventory.keySet().toArray()[sellSlot]).quantity > 0){
                gamePanel.character.money += gamePanel.character.inventory.get(gamePanel.ui.currentNPC.inventory.keySet().toArray()[buySlot]).price;
                gamePanel.character.inventory.get(gamePanel.character.inventory.keySet().toArray()[sellSlot]).quantity--;
                currentNPC.dialoguesSet = 2;
                currentNPC.dialoguesIndex++;

                if(currentNPC.dialoguesIndex == 4){
                    currentNPC.dialoguesSet = 2;
                    currentNPC.dialoguesIndex = 0;
                    charIndex = 0;
                    combinedText = "";
                }
                
                drawDialogueScreen();
            }
            else{
                currentNPC.dialoguesSet = 0;
                currentNPC.dialoguesIndex = 5;
                charIndex = 0;
                combinedText = "";
                drawDialogueScreen();
            }
            gamePanel.keyInput.enterPressed = false;
        }

        if(gamePanel.keyInput.escPressed == true){
            charIndex = 0;
            combinedText = "";
            substateTrade = 0;
            commandNum = 0;
            gamePanel.keyInput.escPressed = false;
        }
    }  

    public void drawTransition(){

        counter++;
        g2.setColor(new Color(0, 0, 0, counter*5));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if(counter == 50){
            counter = 0;
            gamePanel.lost=0;
            gamePanel.gameState = gamePanel.playState;
            gamePanel.currentMap = gamePanel.eventHandler.tempMap;
            gamePanel.character.wX = gamePanel.tileSize * gamePanel.eventHandler.tempCol - 8;
            gamePanel.character.wY = gamePanel.tileSize * gamePanel.eventHandler.tempRow - 8;
        }

    }

    public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public void drawDialogueScreen() {
        //WINDOW
        int x = gamePanel.tileSize*2;
        int y = gamePanel.tileSize/2 + gamePanel.tileSize*8;
        int width = gamePanel.screenWidth - (gamePanel.tileSize*4);
        int heigth = gamePanel.tileSize*2 + gamePanel.tileSize/2;
        drawSubWindow(g2, x, y, width, heigth);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gamePanel.tileSize - 20;
        y +=gamePanel.tileSize;


        if (currentNPC.dialogues[currentNPC.dialoguesSet][currentNPC.dialoguesIndex] != null) {

            //currentDialogue = currentNPC.dialogues[currentNPC.dialoguesSet][currentNPC.dialoguesIndex];

            char stringCharacter[] = currentNPC.dialogues[currentNPC.dialoguesSet][currentNPC.dialoguesIndex].toCharArray();

            if(charIndex < stringCharacter.length){

                String s = String.valueOf(stringCharacter[charIndex]);
                combinedText += s;
                currentDialogue = combinedText;

                charIndex++;
            }

            if (gamePanel.keyInput.enterPressed == true){

                charIndex = 0;
                combinedText = "";
            
                if(gamePanel.gameState == gamePanel.dialoguesState || gamePanel.gameState == gamePanel.cutsceneState){
                    currentNPC.dialoguesIndex++;
                    gamePanel.keyInput.enterPressed = false;
                }     
            }
        }
        else {
            currentNPC.dialoguesIndex = 0;
            if(gamePanel.gameState == gamePanel.dialoguesState){
                gamePanel.character.inDialogue=0;
                if(gamePanel.joined==1 || gamePanel.isHosting==1){
                    Packet07ElementNPCState packet = new Packet07ElementNPCState(gamePanel.character.currentMap, gamePanel.character.index, gamePanel.character.inDialogue);
                    packet.writeData(gamePanel.socketClient);
                }
                if(gamePanel.bossBattleOn == false)
                    gamePanel.gameState = gamePanel.playState;
                else{
                    gamePanel.character.inBattleBoss = 1;
                    gamePanel.gameState = gamePanel.battleBossState;
                }
            }
        }

        for(String line : currentDialogue.split("\n")) { //char to limit line
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public int getX(Graphics2D g2, String text){

        int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gamePanel.screenWidth/2 - lenght/2;
        return x; 
    }

}