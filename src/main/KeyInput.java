package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;

import javax.swing.JOptionPane;

import multiplayer.GameClient;
import multiplayer.GameServer;
import multiplayer.packets.Packet00Login;
import multiplayer.packets.Packet05JavamonRespawn;
import multiplayer.packets.Packet06ElementState;
import multiplayer.packets.Packet10UpdateBattle;
import multiplayer.packets.Packet11GetParty;

public class KeyInput implements KeyListener{

    GamePanel gamePanel;

    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean enterPressed = false;
    public boolean escPressed = false;

    public int health = 0, healthPlayer = 0;

    public boolean lvlup = false;

    public KeyInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (gamePanel.gameState == gamePanel.titleState) {
            titleState(keyCode);
        }
        if (gamePanel.gameState == gamePanel.playState ) {
            gamestate(keyCode);
        }
        else if (gamePanel.gameState == gamePanel.menuState) {
            menuState(keyCode);
        }
        else if (gamePanel.gameState == gamePanel.battleState) {
            battleState(keyCode);
        }
        else if (gamePanel.gameState == gamePanel.battleBossState) {
            battleBossState(keyCode);
        }
        else if(gamePanel.gameState == gamePanel.dialoguesState || gamePanel.gameState == gamePanel.cutsceneState) { 
            dialoguesState(keyCode);
        }
        else if(gamePanel.gameState == gamePanel.tradeState){
            tradeState(keyCode);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    public void titleState(int keyCode) {   //TITLE SCREEN

        if(gamePanel.ui.TitleScreenState == 0){
            if(keyCode == KeyEvent.VK_ENTER) {
                gamePanel.ui.TitleScreenState = 1;
            }
        }
        else if(gamePanel.ui.TitleScreenState == 1){
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            gamePanel.ui.commandNum--;
                if (gamePanel.ui.commandNum < 0) {
                    gamePanel.ui.commandNum = 5;
                }
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;
                if (gamePanel.ui.commandNum > 5) {
                    gamePanel.ui.commandNum = 0;
                }
            }
            if(keyCode == KeyEvent.VK_ENTER){
                if (gamePanel.ui.commandNum == 0) { //New Game
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.stopwatch.start();
                    gamePanel.playMusic(0);
                    gamePanel.ui.commandNum = 0;
                }
                if (gamePanel.ui.commandNum == 1) { //Continue
                    gamePanel.saveLoad.load();
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(gamePanel.currentMap);
                    gamePanel.ui.commandNum = 0;
                }
                if (gamePanel.ui.commandNum == 2) { //Join Game
                    gamePanel.ui.TitleScreenState = 2;
                    gamePanel.ui.commandNum = 0;
                }
                if (gamePanel.ui.commandNum == 4) { //Info
                    gamePanel.ui.TitleScreenState = 3;
                    gamePanel.ui.commandNum = 0;
                }
                if (gamePanel.ui.commandNum == 3) { //Settings
                    gamePanel.ui.TitleScreenState = 4;
                    gamePanel.ui.commandNum = 0;                                 
                }
                if (gamePanel.ui.commandNum == 5) { //Quit
                    System.exit(0);
                }
            }
        }
        else if(gamePanel.ui.TitleScreenState == 2){
            if(keyCode == KeyEvent.VK_ENTER) {
                gamePanel.socketClient = new GameClient(gamePanel, JOptionPane.showInputDialog(gamePanel, "Please enter the IP Address \"localhost\" for same PC"));
                gamePanel.socketClient.start();
                Packet00Login loginPacket = new Packet00Login(gamePanel.character.getUsername(), gamePanel.character.wX, gamePanel.character.wY, gamePanel.character.currentMap);
                gamePanel.joined = 1;
                loginPacket.writeData(gamePanel.socketClient);
                gamePanel.gameState = gamePanel.playState;
                gamePanel.boss[5]=null;
                gamePanel.npc[0][1] = null;
                gamePanel.npc[0][2] = null;
                gamePanel.npc[0][3] = null;
                gamePanel.stopwatch.start();
                gamePanel.playMusic(0);
            }
            if(keyCode == KeyEvent.VK_ESCAPE) {
                gamePanel.ui.TitleScreenState = 1;
            }
        }
        else if(gamePanel.ui.TitleScreenState == 3){
            if(keyCode == KeyEvent.VK_ESCAPE){
                gamePanel.ui.TitleScreenState = 1;
            }
        }
        else if(gamePanel.ui.TitleScreenState == 4){
            if(keyCode == KeyEvent.VK_ESCAPE){
                gamePanel.ui.TitleScreenState = 1;
            }
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                gamePanel.ui.commandNum--;
                if (gamePanel.ui.commandNum == -1) {
                    gamePanel.ui.commandNum = 0;
                }
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;
                if (gamePanel.ui.commandNum == 2) {
                    gamePanel.ui.commandNum = 1;
                }
            }
             if(keyCode == KeyEvent.VK_A){
                if(gamePanel.ui.commandNum == 0 && gamePanel.sound.volumeScale > 0){
                    gamePanel.sound.volumeScale--;
                }
                if(gamePanel.ui.commandNum == 1 && gamePanel.se.sevolumeScale > 0){
                    gamePanel.se.sevolumeScale--;
                }       
            }

            if(keyCode == KeyEvent.VK_D){
                    if(gamePanel.ui.commandNum == 0 && gamePanel.sound.volumeScale < 5){
                        gamePanel.sound.volumeScale++;
                    }
                    if(gamePanel.ui.commandNum == 1 && gamePanel.se.sevolumeScale < 5){
                        gamePanel.se.sevolumeScale++;
                    }
            }       
        }
        
    }

    public void gamestate(int keyCode) {    //IN GAME MOVEMENT
        
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(keyCode == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.menuState;
        }
        if(keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    public void dialoguesState(int keyCode) {

        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    public void menuState(int keyCode) {    //MENU SCREEN
        if(gamePanel.ui.substateMenu == 0){ //MAIN MENU
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.slotRow != 0)
                    gamePanel.ui.slotRow--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.slotRow != 1)
                    gamePanel.ui.slotRow++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.slotCol != 0)
                    gamePanel.ui.slotCol--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.slotCol != 2)
                    gamePanel.ui.slotCol++;
            }
        }

        if(gamePanel.ui.substateMenu == 6){ //OPTIONS MENU
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.commandNum != 0)
                    gamePanel.ui.commandNum--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.commandNum != 5)
                    gamePanel.ui.commandNum++;
            }

            if(keyCode == KeyEvent.VK_A){
                if(gamePanel.ui.commandNum == 0 && gamePanel.sound.volumeScale > 0){
                    gamePanel.sound.volumeScale--;
                    gamePanel.sound.checkVolume();
                }
                if(gamePanel.ui.commandNum == 1 && gamePanel.se.sevolumeScale > 0){
                    gamePanel.se.sevolumeScale--;
                }       
        }

            if(keyCode == KeyEvent.VK_D){
                    if(gamePanel.ui.commandNum == 0 && gamePanel.sound.volumeScale < 5){
                        gamePanel.sound.volumeScale++;
                        gamePanel.sound.checkVolume();
                    }
                    if(gamePanel.ui.commandNum == 1 && gamePanel.se.sevolumeScale < 5){
                        gamePanel.se.sevolumeScale++;
                    }
        }       
        }

        if(gamePanel.ui.substateMenu == 1){ //JAVAMON MENU
             if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.partySlotY != 0)
                    gamePanel.ui.partySlotY--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.partySlotY != gamePanel.ui.partyNumRow - 1)
                    if(gamePanel.ui.partySlotX < gamePanel.ui.partyNumCol2)
                        gamePanel.ui.partySlotY++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.partySlotX != 0)
                    gamePanel.ui.partySlotX--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.partySlotY == 0){
                    if(gamePanel.ui.partySlotX != gamePanel.ui.partyNumCol1 - 1)
                    gamePanel.ui.partySlotX++;
                }
                if(gamePanel.ui.partySlotY == 1){
                    if(gamePanel.ui.partySlotX != gamePanel.ui.partyNumCol2 - 1)
                    gamePanel.ui.partySlotX++;
                }
                
            }
        }

        if(gamePanel.ui.substateMenu == 2){ //INVENTORY MENU
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.inventorySlot != 0)
                    gamePanel.ui.inventorySlot--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.inventorySlot != gamePanel.character.inventorySize - 1) //ONLY GOES TO INVENTORY SIZE
                    gamePanel.ui.inventorySlot++;
            }
        }
        
        if(gamePanel.ui.substateMenu == 10){//Exit Game MENU
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                gamePanel.ui.commandNum = 1;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                gamePanel.ui.commandNum = 0;
            }
        }
        
        if(gamePanel.ui.substateMenu == 11){
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.commandNum != 3)
                    gamePanel.ui.commandNum++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.commandNum != 0)
                    gamePanel.ui.commandNum--;
            }
        }

        if(gamePanel.ui.substateMenu == 12){
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.boxSlotY != 0)
                    gamePanel.ui.boxSlotY--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.boxSlotY < gamePanel.ui.boxNumRow - 1){
                    if(gamePanel.ui.boxSlotX < gamePanel.ui.boxNumCol2 && gamePanel.ui.boxNumRow == 2)
                        gamePanel.ui.boxSlotY++;
                    if(gamePanel.ui.boxSlotX < gamePanel.ui.boxNumCol3 && gamePanel.ui.boxNumRow == 3)
                        gamePanel.ui.boxSlotY++;
                    if(gamePanel.ui.boxSlotX < gamePanel.ui.boxNumCol4 && gamePanel.ui.boxNumRow == 4)
                        gamePanel.ui.boxSlotY++;
                }
                    
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.boxSlotX != 0)
                    gamePanel.ui.boxSlotX--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.boxSlotY == 0){
                    if(gamePanel.ui.boxSlotX < gamePanel.ui.boxNumCol1 - 1)
                        gamePanel.ui.boxSlotX++;
                }
                if(gamePanel.ui.boxSlotY == 1){
                    if(gamePanel.ui.boxSlotX < gamePanel.ui.boxNumCol2 - 1)
                        gamePanel.ui.boxSlotX++;
                } 
                if(gamePanel.ui.boxSlotY == 2){
                    if(gamePanel.ui.boxSlotX < gamePanel.ui.boxNumCol3 - 1)
                        gamePanel.ui.boxSlotX++;
                } 
                if(gamePanel.ui.boxSlotY == 3){
                    if(gamePanel.ui.boxSlotX < gamePanel.ui.boxNumCol4 - 1)
                        gamePanel.ui.boxSlotX++;
                } 
            }
        }

        if(gamePanel.ui.substateMenu == 13){
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.commandNum != 1)
                    gamePanel.ui.commandNum++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.commandNum != 0)
                    gamePanel.ui.commandNum--;
            }
        }
        
        if(keyCode == KeyEvent.VK_ESCAPE) {
            if(gamePanel.ui.substateMenu == 0)
                gamePanel.gameState = gamePanel.playState;
            else if (gamePanel.ui.substateMenu == 7 || gamePanel.ui.substateMenu == 9)
                gamePanel.ui.substateMenu = 6;
            else if (gamePanel.ui.substateMenu == 11){
                gamePanel.ui.substateMenu = 1;
                gamePanel.ui.commandNum = 0;
            }
            else if(gamePanel.ui.substateMenu == 12){
                gamePanel.ui.substateMenu = 11;
                gamePanel.ui.commandNum = 0;
                gamePanel.ui.boxSlotX = 0;
                gamePanel.ui.boxSlotY = 0;
            }
            else if(gamePanel.ui.substateMenu == 13){
                gamePanel.ui.substateMenu = 12;
                gamePanel.ui.commandNum = 0;
            }
            else {
                gamePanel.ui.substateMenu = 0;
                gamePanel.ui.commandNum = 0;
            }

        }

        if(keyCode == KeyEvent.VK_ENTER) {
            if(gamePanel.ui.substateMenu == 0){
                if(gamePanel.ui.slotRow == 0 && gamePanel.ui.slotCol == 0)
                    gamePanel.ui.substateMenu = 1;
                if(gamePanel.ui.slotRow == 0 && gamePanel.ui.slotCol == 1)
                    gamePanel.ui.substateMenu = 2;
                if(gamePanel.ui.slotRow == 0 && gamePanel.ui.slotCol == 2)
                {
                    if(gamePanel.isHosting == 1 || gamePanel.joined == 1)
                    {
                        gamePanel.ui.substateMenu = 3;
                    }   
                }
                if(gamePanel.ui.slotRow == 1 && gamePanel.ui.slotCol == 0)
                    gamePanel.ui.substateMenu = 4;
                if(gamePanel.ui.slotRow == 1 && gamePanel.ui.slotCol == 1)
                    gamePanel.ui.substateMenu = 5;
                if(gamePanel.ui.slotRow == 1 && gamePanel.ui.slotCol == 2)
                    gamePanel.ui.substateMenu = 6;
            }
            else if(gamePanel.ui.substateMenu == 2){
                String item = gamePanel.character.getItemName(gamePanel.ui.inventorySlot);
                if(gamePanel.character.checkItem(item)>0){
                    if(gamePanel.character.party.size() > 0){
                        switch(item){
                            case "HP Potion":
                                if(gamePanel.character.party.get(0).hp < gamePanel.character.party.get(0).maxHp){
                                    gamePanel.character.party.get(0).hp += 30;
                                    if(gamePanel.character.party.get(0).hp > gamePanel.character.party.get(0).maxHp)
                                        gamePanel.character.party.get(0).hp = gamePanel.character.party.get(0).maxHp;
                                    gamePanel.character.removeItem(item, 1);
                                    gamePanel.character.updateInventorySize();
                                    gamePanel.ui.substateMenu = 0;
                                    gamePanel.ui.commandNum = 0;
                                }
                                break;
                            case "Full Heal":
                                for(int i = 0; i < gamePanel.character.party.size(); i++){
                                    gamePanel.character.party.get(i).hp = gamePanel.character.party.get(i).maxHp;
                                }
                                gamePanel.character.removeItem(item, 1);
                                gamePanel.character.updateInventorySize();
                                gamePanel.ui.substateMenu = 0;
                                gamePanel.ui.commandNum = 0;
                                break;
                        }
                    }
                }
            }
            else if(gamePanel.ui.substateMenu == 5) { // SAVE MENU
                gamePanel.saveLoad.save();
                gamePanel.ui.substateMenu = 8;
            }
            else if(gamePanel.ui.substateMenu == 8)
                gamePanel.ui.substateMenu = 0;
            else if(gamePanel.ui.substateMenu == 9){
                if (JOptionPane.showConfirmDialog(gamePanel, "Do you want to run the server") == 0) {
                    gamePanel.isHosting = 1;
                    gamePanel.socketServer = new GameServer(gamePanel);
                    gamePanel.socketServer.start();
                    gamePanel.socketClient = new GameClient(gamePanel, "localhost");
                    gamePanel.socketClient.start();
                    Packet00Login loginPacket = new Packet00Login(gamePanel.character.getUsername(), gamePanel.character.wX, gamePanel.character.wY, gamePanel.character.currentMap);
                    if (gamePanel.socketServer != null) {
                        gamePanel.socketServer.addConnection(gamePanel.character, loginPacket);
                    }
                    loginPacket.writeData(gamePanel.socketClient);
                    gamePanel.boss[5]=null;
                    gamePanel.npc[0][1] = null;
                    gamePanel.npc[0][2] = null;
                    gamePanel.npc[0][3] = null;
                }
                gamePanel.ui.substateMenu = 6;
            }  
            else if(gamePanel.ui.substateMenu == 10){
                if(gamePanel.ui.commandNum == 1){
                    gamePanel.ui.substateMenu = 6;
                }
                if(gamePanel.ui.commandNum == 0){
                    System.exit(0);
                }
                gamePanel.ui.commandNum = 0;
            }
            else if(gamePanel.ui.substateMenu == 1){
                if(gamePanel.character.partySize > 0){
                    gamePanel.ui.substateMenu = 11;
                    gamePanel.ui.commandNum = 0;
                }
            }
            else if(gamePanel.ui.substateMenu == 11){
                if(gamePanel.ui.commandNum == 0){
                    gamePanel.character.party.get(gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3).nickname = JOptionPane.showInputDialog(gamePanel, "Please enter new nickname");
                    if(gamePanel.character.party.get(gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3).nickname == null)
                        gamePanel.character.party.get(gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3).nickname = gamePanel.character.party.get(gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3).name;
                }
                if(gamePanel.ui.commandNum == 1){
                    Collections.swap(gamePanel.character.party, gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3, 0);
                    gamePanel.ui.substateMenu = 1;
                    gamePanel.ui.commandNum = 0;
                    gamePanel.ui.partySlotX = 0;
                    gamePanel.ui.partySlotY = 0;
                }
                if(gamePanel.ui.commandNum == 2){
                    gamePanel.ui.substateMenu = 12;
                    gamePanel.ui.commandNum = 0;
                }
                if(gamePanel.ui.commandNum == 3){
                    if(gamePanel.character.party.size()>1){
                    gamePanel.character.party.remove(gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3);
                    if(gamePanel.character.boxSize > 0){
                        gamePanel.character.party.add(gamePanel.character.box.get(0));
                        gamePanel.character.box.remove(0);
                        gamePanel.character.boxSize--;
                    }
                    else 
                        gamePanel.character.partySize--;
                    gamePanel.ui.substateMenu = 1;
                    gamePanel.ui.commandNum = 0;
                    gamePanel.ui.partySlotX = 0;
                    gamePanel.ui.partySlotY = 0;
                }
                    
                }
            }
            else if (gamePanel.ui.substateMenu == 12){
                if(gamePanel.character.boxSize > 0){
                      gamePanel.ui.substateMenu = 13;
                    gamePanel.ui.commandNum = 0;
                }
            }
            else if (gamePanel.ui.substateMenu == 13){
                if(gamePanel.ui.commandNum == 0){
                    gamePanel.character.party.add(gamePanel.character.box.get(gamePanel.ui.boxSlotX + gamePanel.ui.boxSlotY*6));
                    Collections.swap(gamePanel.character.party, gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3, 6);

                    gamePanel.character.box.add(gamePanel.character.party.get(6));
                    gamePanel.character.box.remove(gamePanel.ui.boxSlotX + gamePanel.ui.boxSlotY*6);
                    gamePanel.character.party.remove(6);
                    gamePanel.ui.substateMenu = 1;
                    gamePanel.ui.commandNum = 0;
                    gamePanel.ui.boxSlotX = 0;
                    gamePanel.ui.boxSlotY = 0;
                }
                if(gamePanel.ui.commandNum == 1){
                    gamePanel.character.box.remove(gamePanel.ui.boxSlotX + gamePanel.ui.boxSlotY*4);
                    gamePanel.character.boxSize--;
                    gamePanel.ui.substateMenu = 12;
                    gamePanel.ui.commandNum = 0;
                    gamePanel.ui.boxSlotX = 0;
                    gamePanel.ui.boxSlotY = 0;
                }
            }

            if(gamePanel.ui.substateMenu == 6){     //OPTIONS MENU   
                if(gamePanel.ui.commandNum == 2 && gamePanel.isHosting == 0 && gamePanel.joined == 0) {  //HOST GAME BUTTON
                    gamePanel.ui.substateMenu = 9;
                }
                if(gamePanel.ui.commandNum == 3) {  //INFO BUTTON
                    gamePanel.ui.commandNum = 0;
                    gamePanel.ui.substateMenu = 7;
                }
                if(gamePanel.ui.commandNum == 5) {  //LEAVE OPTIONS BUTTON
                    gamePanel.ui.commandNum = 0;
                    gamePanel.ui.substateMenu = 0;
                }
                if(gamePanel.ui.commandNum == 4) {  //LEAVE GAME BUTTON
                    gamePanel.ui.substateMenu = 10;
                    gamePanel.ui.commandNum = 0;
                }
            }            
        }
    }

    public void battleState(int keyCode) {    //BATTLE SCREEN
        if(gamePanel.ui.substateBattle == 0){ //BATTLE MENU
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.slotRowBattle != 0)
                    gamePanel.ui.slotRowBattle--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.slotRowBattle != 1)
                    gamePanel.ui.slotRowBattle++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.slotColBattle != 0)
                    gamePanel.ui.slotColBattle--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.slotColBattle != 1)
                    gamePanel.ui.slotColBattle++;
            }
        }

        if(gamePanel.ui.substateBattle == 1){ //ATTACK MENU
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.slotRowBattle != 0)
                    gamePanel.ui.slotRowBattle--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.slotRowBattle != 1)
                    gamePanel.ui.slotRowBattle++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.slotColBattle != 0)
                    gamePanel.ui.slotColBattle--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.slotColBattle != 1)
                    gamePanel.ui.slotColBattle++;
            }
        }

        if(gamePanel.ui.substateBattle == 2){ //BAG BATTLE
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.inventorySlot != 0)
                    gamePanel.ui.inventorySlot--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.inventorySlot != gamePanel.character.inventorySize - 1) //ONLY GOES TO INVENTORY SIZE
                    gamePanel.ui.inventorySlot++;
            }
        }

        if(gamePanel.ui.substateBattle == 4){ //PARTY BATTLE
           if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.partySlotY != 0)
                    gamePanel.ui.partySlotY--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.partySlotY != gamePanel.ui.partyNumRow - 1)
                    if(gamePanel.ui.partySlotX < gamePanel.ui.partyNumCol2)
                        gamePanel.ui.partySlotY++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.partySlotX != 0)
                    gamePanel.ui.partySlotX--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.partySlotY == 0){
                    if(gamePanel.ui.partySlotX != gamePanel.ui.partyNumCol1 - 1)
                    gamePanel.ui.partySlotX++;
                }
                if(gamePanel.ui.partySlotY == 1){
                    if(gamePanel.ui.partySlotX != gamePanel.ui.partyNumCol2 - 1)
                    gamePanel.ui.partySlotX++;
                }    
            }
        }
        
        if(gamePanel.ui.substateBattle == 11){
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.commandNum != 3)
                    gamePanel.ui.commandNum++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.commandNum != 0)
                    gamePanel.ui.commandNum--;
            }
        }
        
        if(keyCode == KeyEvent.VK_ESCAPE) {
            if(gamePanel.ui.substateBattle == 0){
                gamePanel.ui.slotRowBattle = 0;
                gamePanel.ui.slotColBattle = 0;
                gamePanel.character.inBattle = 0;
                if(gamePanel.isHosting==1){
                    Packet06ElementState packet = new Packet06ElementState(gamePanel.currentMap, gamePanel.character.index, gamePanel.character.inBattle, 0);
                    packet.writeData(gamePanel.socketClient);
                }else if(gamePanel.joined==1){
                    Packet06ElementState packet = new Packet06ElementState(gamePanel.currentMap, gamePanel.character.index, gamePanel.character.inBattle, 1);
                    packet.writeData(gamePanel.socketClient);
                }
                else 
                gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].isBattling = 0;
                gamePanel.gameState = gamePanel.playState;
            }
            else if (gamePanel.ui.substateBattle == 5 || gamePanel.ui.substateBattle == 3){}
            else
            {   
                if(gamePanel.ui.substateBattle == 1){
                    gamePanel.ui.slotRowBattle = 0; 
                    gamePanel.ui.slotColBattle = 0;
                }
                gamePanel.ui.substateBattle = 0;
                gamePanel.ui.commandNum = 0;
            }

        }
        
        if(keyCode == KeyEvent.VK_ENTER) {
            if(gamePanel.ui.substateBattle==0){//BATTLE MENU
                
                if(gamePanel.isHosting==1){
                    Packet10UpdateBattle packet = new Packet10UpdateBattle(gamePanel.character.index, gamePanel.character.party.get(0).hp, gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp, 0, gamePanel.currentMap);
                    packet.writeData(gamePanel.socketClient);
                }
                if(gamePanel.joined==1){
                    Packet10UpdateBattle packet = new Packet10UpdateBattle(gamePanel.character.index, gamePanel.character.party.get(0).hp, gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp, 1, gamePanel.currentMap);
                    packet.writeData(gamePanel.socketClient);
                }

                if(gamePanel.ui.slotRowBattle == 0 && gamePanel.ui.slotColBattle == 0){
                    if(gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].currentSpeed < gamePanel.character.party.get(0).currentSpeed) {
                        health = gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp-((2*gamePanel.character.party.get(0).level/5+2)*gamePanel.character.party.get(0).currentAttack*10/gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].currentDefense/50+2);
                        gamePanel.battle.action = "ATTACK1";
                        gamePanel.ui.substateBattle = 1;
                    }
                    else if(gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].currentSpeed > gamePanel.character.party.get(0).currentSpeed) {
                        healthPlayer =  gamePanel.character.party.get(0).hp-((2*1/5+2)*gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].attack*10/gamePanel.character.party.get(0).defense/50+2);
                        gamePanel.battle.action = "ATTACK2";
                        gamePanel.ui.substateBattle = 8;
                    }
                    else {
                        if(Math.random() < 0.5) {
                            health = gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp-((2*gamePanel.character.party.get(0).level/5+2)*gamePanel.character.party.get(0).currentAttack*10/gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].currentDefense/50+2);
                            gamePanel.battle.action = "ATTACK1";
                            gamePanel.ui.substateBattle = 1;
                        }
                        else {
                            healthPlayer =  gamePanel.character.party.get(0).hp-((2*1/5+2)*gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].attack*10/gamePanel.character.party.get(0).defense/50+2);
                            gamePanel.ui.substateBattle = 8;
                            gamePanel.battle.action = "ATTACK2";
                        }
                    }
                }
                if(gamePanel.ui.slotRowBattle == 0 && gamePanel.ui.slotColBattle == 1)
                    gamePanel.ui.substateBattle = 2;
                if(gamePanel.ui.slotRowBattle == 1 && gamePanel.ui.slotColBattle == 0){
                    if(gamePanel.character.checkItem("Javaball") > 0){
                        gamePanel.character.removeItem("Javaball", 1);
                        gamePanel.character.updateInventorySize();
                        gamePanel.battle.action = "CATCH";
                        gamePanel.ui.substateBattle = 3;
                    }
                }
                    
                if(gamePanel.ui.slotRowBattle == 1 && gamePanel.ui.slotColBattle == 1)
                    gamePanel.ui.substateBattle = 4;
            }
            else if(gamePanel.ui.substateBattle == 1) {
                if(health < 0){
                    gamePanel.ui.substateBattle = 5;
                }else {
                    healthPlayer =  gamePanel.character.party.get(0).hp-((2*1/5+2)*gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].attack*10/gamePanel.character.party.get(0).defense/50+2);
                    gamePanel.battle.action = "ATTACK2";
                    gamePanel.ui.substateBattle = 7;
                }
                
            }
            else if(gamePanel.ui.substateBattle == 7) {
                if (healthPlayer <= 0){
                    int lost = 1;
                    for(int i = 0; i < gamePanel.character.party.size(); i++){
                        if(gamePanel.character.party.get(i).hp > 0){
                            Collections.swap(gamePanel.character.party, i, 0);
                            if(gamePanel.isHosting == 1){
                            Packet11GetParty packet = new Packet11GetParty(0, gamePanel.character.party, gamePanel.character.partySize);
                            packet.writeData(gamePanel.socketClient);
                            }
                            if(gamePanel.joined == 1){
                            Packet11GetParty packet = new Packet11GetParty(1, gamePanel.character.party, gamePanel.character.partySize);
                            packet.writeData(gamePanel.socketClient);
                            }
                            lost = 0;
                            break;
                        }
                    }
                    if(lost == 1)
                    gamePanel.ui.substateBattle = 12;
                    else{
                        gamePanel.ui.slotRowBattle = 0; 
                        gamePanel.ui.slotColBattle = 0;
                        gamePanel.ui.substateBattle = 0;
                    }
                }
                else{
                    gamePanel.ui.slotRowBattle = 0; 
                    gamePanel.ui.slotColBattle = 0;
                    gamePanel.ui.substateBattle = 0;
                }
            }
            else if(gamePanel.ui.substateBattle == 8) {
                if (healthPlayer <= 0) {
                    int lost = 1;
                    for(int i = 0; i < gamePanel.character.party.size(); i++){
                        if(gamePanel.character.party.get(i).hp > 0){
                            Collections.swap(gamePanel.character.party, i, 0);
                            if(gamePanel.isHosting == 1){
                            Packet11GetParty packet = new Packet11GetParty(0, gamePanel.character.party, gamePanel.character.partySize);
                            packet.writeData(gamePanel.socketClient);
                            }
                            if(gamePanel.joined == 1){
                            Packet11GetParty packet = new Packet11GetParty(1, gamePanel.character.party, gamePanel.character.partySize);
                            packet.writeData(gamePanel.socketClient);
                            }
                            lost = 0;
                            break;
                        }
                    }
                    if(lost == 1)
                    gamePanel.ui.substateBattle = 12;
                    else{
                        gamePanel.ui.slotRowBattle = 0; 
                        gamePanel.ui.slotColBattle = 0;
                        gamePanel.ui.substateBattle = 0;
                    } 
                }
                else{
                    health = gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp-((2*gamePanel.character.party.get(0).level/5+2)*gamePanel.character.party.get(0).currentAttack*10/gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].currentDefense/50+2);
                    gamePanel.battle.action = "ATTACK1";
                    gamePanel.ui.substateBattle = 9;
                } 
            }
            else if(gamePanel.ui.substateBattle == 9) {
                if (health <= 0){
                    gamePanel.ui.substateBattle = 5;
                }
                else{
                    gamePanel.ui.slotRowBattle = 0; 
                    gamePanel.ui.slotColBattle = 0;
                    gamePanel.ui.substateBattle = 0;
                }
            }
            else if(gamePanel.ui.substateBattle == 2) { //BAG
                String item = gamePanel.character.getItemName(gamePanel.ui.inventorySlot);
                if(gamePanel.character.checkItem(item)>0){
                    switch(item){
                        case "HP Potion":
                            if(gamePanel.character.party.get(0).hp < gamePanel.character.party.get(0).maxHp){
                                gamePanel.character.party.get(0).hp += 30;
                                if(gamePanel.character.party.get(0).hp > gamePanel.character.party.get(0).maxHp)
                                    gamePanel.character.party.get(0).hp = gamePanel.character.party.get(0).maxHp;
                                gamePanel.character.removeItem(item, 1);
                                gamePanel.character.updateInventorySize();
                                gamePanel.battle.action = "SWITCH";
                                gamePanel.ui.substateBattle = 0;
                                gamePanel.ui.commandNum = 0;
                                gamePanel.ui.partySlotX = 0;
                                gamePanel.ui.partySlotY = 0;
                            }
                            break;
                        case "Attack Potion":
                            gamePanel.character.party.get(0).currentAttack += 10;
                            gamePanel.character.removeItem(item, 1);
                            gamePanel.character.updateInventorySize();
                            gamePanel.battle.action = "SWITCH";
                            gamePanel.ui.substateBattle = 0;
                            gamePanel.ui.commandNum = 0;
                            gamePanel.ui.partySlotX = 0;
                            gamePanel.ui.partySlotY = 0;
                            break;
                        case "Defense Potion":
                            gamePanel.character.party.get(0).currentDefense += 10;
                            gamePanel.character.removeItem(item, 1);
                            gamePanel.character.updateInventorySize();
                            gamePanel.battle.action = "SWITCH";
                            gamePanel.ui.substateBattle = 0;
                            gamePanel.ui.commandNum = 0;
                            gamePanel.ui.partySlotX = 0;
                            gamePanel.ui.partySlotY = 0;
                            break;
                        case "Speed Potion":
                            gamePanel.character.party.get(0).currentSpeed += 10;
                            gamePanel.character.removeItem(item, 1);
                            gamePanel.character.updateInventorySize();
                            gamePanel.battle.action = "SWITCH";
                            gamePanel.ui.substateBattle = 0;
                            gamePanel.ui.commandNum = 0;
                            gamePanel.ui.partySlotX = 0;
                            gamePanel.ui.partySlotY = 0;
                            break;
                        case "Full Heal":
                            for(int i = 0; i < gamePanel.character.party.size(); i++){
                                gamePanel.character.party.get(i).hp = gamePanel.character.party.get(i).maxHp;
                            }
                            gamePanel.character.removeItem(item, 1);
                            gamePanel.character.updateInventorySize();
                            gamePanel.battle.action = "SWITCH";
                            gamePanel.ui.substateBattle = 0;
                            gamePanel.ui.commandNum = 0;
                            gamePanel.ui.partySlotX = 0;
                            gamePanel.ui.partySlotY = 0;
                            break;
                    }
                }
            }
            else if(gamePanel.ui.substateBattle == 4) { //PARTY
                gamePanel.ui.substateBattle = 11;
                gamePanel.ui.commandNum = 0;
            }
            else if(gamePanel.ui.substateBattle == 11){
                if(gamePanel.ui.commandNum == 0){
                   
                }
                if(gamePanel.ui.commandNum == 1){
                    Collections.swap(gamePanel.character.party, gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3, 0);
                    if(gamePanel.isHosting == 1){
                    Packet11GetParty packet = new Packet11GetParty(0, gamePanel.character.party, gamePanel.character.partySize);
                    packet.writeData(gamePanel.socketClient);
                    }
                    if(gamePanel.joined == 1){
                    Packet11GetParty packet = new Packet11GetParty(1, gamePanel.character.party, gamePanel.character.partySize);
                    packet.writeData(gamePanel.socketClient);
                    }
                    gamePanel.battle.action = "SWITCH";
                    gamePanel.ui.substateBattle = 0;
                    gamePanel.ui.commandNum = 0;
                    gamePanel.ui.partySlotX = 0;
                    gamePanel.ui.partySlotY = 0;
                }
                if(gamePanel.ui.commandNum == 2){

                }
                if(gamePanel.ui.commandNum == 3){

                }
            }
            else if(gamePanel.ui.substateBattle == 3) { //CATCH
                    if(gamePanel.battle.caught == 1){
                        gamePanel.ui.substateBattle = 6;
                    }else{
                        gamePanel.ui.substateBattle = 0;
                    }
                }
            else if(gamePanel.ui.substateBattle == 5) {
                gamePanel.character.party.get(0).exp += 8;
                gamePanel.character.money += 30;
                lvlup = gamePanel.character.party.get(0).checkLevel();
                gamePanel.ui.substateBattle = 13;
            }
            else if(gamePanel.ui.substateBattle == 6) {
                if(gamePanel.battle.caught == 1){
                    gamePanel.character.party.get(0).exp += 8;
                    gamePanel.character.money += 30;
                    lvlup = gamePanel.character.party.get(0).checkLevel();
                    gamePanel.ui.substateBattle = 13;
                }else{
                    gamePanel.ui.substateBattle = 0;
                }
            }
            else if(gamePanel.ui.substateBattle == 12){
                gamePanel.ui.slotRowBattle = 0;
                gamePanel.ui.slotColBattle = 0;
                gamePanel.ui.substateBattle = 0;
                gamePanel.character.inBattle = 0;
                gamePanel.lost=1;
                if(gamePanel.isHosting==1){
                    Packet06ElementState packet = new Packet06ElementState(gamePanel.currentMap, gamePanel.character.index, gamePanel.character.inBattle, 0);
                    packet.writeData(gamePanel.socketClient);
                }else if(gamePanel.joined==1){
                    Packet06ElementState packet = new Packet06ElementState(gamePanel.currentMap, gamePanel.character.index, gamePanel.character.inBattle, 1);
                    packet.writeData(gamePanel.socketClient);
                }
                gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index] = null;
                lvlup = false;
                if(gamePanel.joined == 1 || gamePanel.isHosting == 1){
                    Packet05JavamonRespawn packet = new Packet05JavamonRespawn(gamePanel.currentMap, gamePanel.character.index);
                    packet.writeData(gamePanel.socketClient);
                }
                gamePanel.battle=null;
                gamePanel.eventHandler.checkEvent();
            }
            else if(gamePanel.ui.substateBattle == 13){
                gamePanel.ui.slotRowBattle = 0;
                gamePanel.ui.slotColBattle = 0;
                gamePanel.ui.substateBattle = 0;
                gamePanel.character.inBattle = 0;
                if(gamePanel.isHosting==1){
                    Packet06ElementState packet = new Packet06ElementState(gamePanel.currentMap, gamePanel.character.index, gamePanel.character.inBattle, 0);
                    packet.writeData(gamePanel.socketClient);
                }else if(gamePanel.joined==1){
                    Packet06ElementState packet = new Packet06ElementState(gamePanel.currentMap, gamePanel.character.index, gamePanel.character.inBattle, 1);
                    packet.writeData(gamePanel.socketClient);
                }
                gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index] = null;
                lvlup = false;
                if(gamePanel.joined == 1 || gamePanel.isHosting == 1){
                    Packet05JavamonRespawn packet = new Packet05JavamonRespawn(gamePanel.currentMap, gamePanel.character.index);
                    packet.writeData(gamePanel.socketClient);
                }
                gamePanel.battle=null;
                gamePanel.gameState = gamePanel.playState;
            }
        }
    }

    public void battleBossState(int keyCode) {    //BATTLE SCREEN
        if(gamePanel.ui.substateBattle == 0){ //BATTLE MENU
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.slotRowBattle != 0)
                    gamePanel.ui.slotRowBattle--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.slotRowBattle != 1)
                    gamePanel.ui.slotRowBattle++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.slotColBattle != 0)
                    gamePanel.ui.slotColBattle--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.slotColBattle != 1)
                    gamePanel.ui.slotColBattle++;
            }
        }

        if(gamePanel.ui.substateBattle == 1){ //ATTACK MENU
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.slotRowBattle != 0)
                    gamePanel.ui.slotRowBattle--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.slotRowBattle != 1)
                    gamePanel.ui.slotRowBattle++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.slotColBattle != 0)
                    gamePanel.ui.slotColBattle--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.slotColBattle != 1)
                    gamePanel.ui.slotColBattle++;
            }
        }

        if(gamePanel.ui.substateBattle == 2){ //BAG BATTLE
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.inventorySlot != 0)
                    gamePanel.ui.inventorySlot--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.inventorySlot != gamePanel.character.inventorySize - 1) //ONLY GOES TO INVENTORY SIZE
                    gamePanel.ui.inventorySlot++;
            }
        }

        if(gamePanel.ui.substateBattle == 4){ //PARTY BATTLE
           if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.partySlotY != 0)
                    gamePanel.ui.partySlotY--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.partySlotY != gamePanel.ui.partyNumRow - 1)
                    if(gamePanel.ui.partySlotX < gamePanel.ui.partyNumCol2)
                        gamePanel.ui.partySlotY++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.partySlotX != 0)
                    gamePanel.ui.partySlotX--;
            }
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.partySlotY == 0){
                    if(gamePanel.ui.partySlotX != gamePanel.ui.partyNumCol1 - 1)
                    gamePanel.ui.partySlotX++;
                }
                if(gamePanel.ui.partySlotY == 1){
                    if(gamePanel.ui.partySlotX != gamePanel.ui.partyNumCol2 - 1)
                    gamePanel.ui.partySlotX++;
                }    
            }
        }
        
        if(gamePanel.ui.substateBattle == 11){
            if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                if(gamePanel.ui.commandNum != 3)
                    gamePanel.ui.commandNum++;
            }
            if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                if(gamePanel.ui.commandNum != 0)
                    gamePanel.ui.commandNum--;
            }
        }
        
        if(keyCode == KeyEvent.VK_ESCAPE) {
            if (gamePanel.ui.substateBattle == 5 || gamePanel.ui.substateBattle == 3){}
            else
            {   
                if(gamePanel.ui.substateBattle == 1){
                    gamePanel.ui.slotRowBattle = 0; 
                    gamePanel.ui.slotColBattle = 0;
                }
                gamePanel.ui.substateBattle = 0;
                gamePanel.ui.commandNum = 0;
            }

        }
        
        if(keyCode == KeyEvent.VK_ENTER) {
            if(gamePanel.ui.substateBattle==0){//BATTLE MENU

                if(gamePanel.ui.slotRowBattle == 0 && gamePanel.ui.slotColBattle == 0){
                    if(gamePanel.boss[5].currentSpeed < gamePanel.character.party.get(0).currentSpeed) {
                        health = gamePanel.boss[5].hp-((2*gamePanel.character.party.get(0).level/5+2)*gamePanel.character.party.get(0).currentAttack*10/gamePanel.boss[5].currentDefense/50+2);
                        gamePanel.battleBoss.action = "ATTACK1";
                        gamePanel.ui.substateBattle = 1;
                    }
                    else if(gamePanel.boss[5].currentSpeed > gamePanel.character.party.get(0).currentSpeed) {
                        healthPlayer =  gamePanel.character.party.get(0).hp-((2*1/5+2)*gamePanel.boss[5].attack*10/gamePanel.character.party.get(0).defense/50+2);
                        gamePanel.battleBoss.action = "ATTACK2";
                        gamePanel.ui.substateBattle = 8;
                    }
                    else {
                        if(Math.random() < 0.5) {
                            health = gamePanel.boss[5].hp-((2*gamePanel.character.party.get(0).level/5+2)*gamePanel.character.party.get(0).currentAttack*10/gamePanel.boss[5].currentDefense/50+2);
                            gamePanel.battleBoss.action = "ATTACK1";
                            gamePanel.ui.substateBattle = 1;
                        }
                        else {
                            healthPlayer =  gamePanel.character.party.get(0).hp-((2*1/5+2)*gamePanel.boss[5].attack*10/gamePanel.character.party.get(0).defense/50+2);
                            gamePanel.ui.substateBattle = 8;
                            gamePanel.battleBoss.action = "ATTACK2";
                        }
                    }
                }
                if(gamePanel.ui.slotRowBattle == 0 && gamePanel.ui.slotColBattle == 1)
                    gamePanel.ui.substateBattle = 2;
                    
                if(gamePanel.ui.slotRowBattle == 1 && gamePanel.ui.slotColBattle == 1)
                    gamePanel.ui.substateBattle = 4;
            }
            else if(gamePanel.ui.substateBattle == 1) {
                if(health < 0){
                    gamePanel.ui.substateBattle = 5;
                }else {
                    healthPlayer =  gamePanel.character.party.get(0).hp-((2*1/5+2)*gamePanel.boss[5].attack*10/gamePanel.character.party.get(0).defense/50+2);
                    gamePanel.battleBoss.action = "ATTACK2";
                    gamePanel.ui.substateBattle = 7;
                }
                
            }
            else if(gamePanel.ui.substateBattle == 7) {
                if (healthPlayer <= 0){
                    int lost = 1;
                    for(int i = 0; i < gamePanel.character.party.size(); i++){
                        if(gamePanel.character.party.get(i).hp > 0){
                            Collections.swap(gamePanel.character.party, i, 0);
                            if(gamePanel.isHosting == 1){
                            Packet11GetParty packet = new Packet11GetParty(0, gamePanel.character.party, gamePanel.character.partySize);
                            packet.writeData(gamePanel.socketClient);
                            }
                            if(gamePanel.joined == 1){
                            Packet11GetParty packet = new Packet11GetParty(1, gamePanel.character.party, gamePanel.character.partySize);
                            packet.writeData(gamePanel.socketClient);
                            }
                            lost = 0;
                            break;
                        }
                    }
                    if(lost == 1)
                    gamePanel.ui.substateBattle = 12;
                    else{
                        gamePanel.ui.slotRowBattle = 0; 
                        gamePanel.ui.slotColBattle = 0;
                        gamePanel.ui.substateBattle = 0;
                    }
                }
                else{
                    gamePanel.ui.slotRowBattle = 0; 
                    gamePanel.ui.slotColBattle = 0;
                    gamePanel.ui.substateBattle = 0;
                }
            }
            else if(gamePanel.ui.substateBattle == 8) {
                if (healthPlayer <= 0) {
                    int lost = 1;
                    for(int i = 0; i < gamePanel.character.party.size(); i++){
                        if(gamePanel.character.party.get(i).hp > 0){
                            Collections.swap(gamePanel.character.party, i, 0);
                            if(gamePanel.isHosting == 1){
                            Packet11GetParty packet = new Packet11GetParty(0, gamePanel.character.party, gamePanel.character.partySize);
                            packet.writeData(gamePanel.socketClient);
                            }
                            if(gamePanel.joined == 1){
                            Packet11GetParty packet = new Packet11GetParty(1, gamePanel.character.party, gamePanel.character.partySize);
                            packet.writeData(gamePanel.socketClient);
                            }
                            lost = 0;
                            break;
                        }
                    }
                    if(lost == 1)
                    gamePanel.ui.substateBattle = 12;
                    else{
                        gamePanel.ui.slotRowBattle = 0; 
                        gamePanel.ui.slotColBattle = 0;
                        gamePanel.ui.substateBattle = 0;
                    } 
                }
                else{
                    health = gamePanel.boss[5].hp-((2*gamePanel.character.party.get(0).level/5+2)*gamePanel.character.party.get(0).currentAttack*10/gamePanel.boss[5].currentDefense/50+2);
                    gamePanel.battleBoss.action = "ATTACK1";
                    gamePanel.ui.substateBattle = 9;
                } 
            }
            else if(gamePanel.ui.substateBattle == 9) {
                if (health <= 0){
                    gamePanel.ui.substateBattle = 5;
                }
                else{
                    gamePanel.ui.slotRowBattle = 0; 
                    gamePanel.ui.slotColBattle = 0;
                    gamePanel.ui.substateBattle = 0;
                }
            }
            else if(gamePanel.ui.substateBattle == 2) { //BAG
                String item = gamePanel.character.getItemName(gamePanel.ui.inventorySlot);
                if(gamePanel.character.checkItem(item)>0){
                    switch(item){
                        case "HP Potion":
                            if(gamePanel.character.party.get(0).hp < gamePanel.character.party.get(0).maxHp){
                                gamePanel.character.party.get(0).hp += 30;
                                if(gamePanel.character.party.get(0).hp > gamePanel.character.party.get(0).maxHp)
                                    gamePanel.character.party.get(0).hp = gamePanel.character.party.get(0).maxHp;
                                gamePanel.character.removeItem(item, 1);
                                gamePanel.character.updateInventorySize();
                                gamePanel.battleBoss.action = "SWITCH";
                                gamePanel.ui.substateBattle = 0;
                                gamePanel.ui.commandNum = 0;
                                gamePanel.ui.partySlotX = 0;
                                gamePanel.ui.partySlotY = 0;
                            }
                            break;
                        case "Attack Potion":
                            gamePanel.character.party.get(0).currentAttack += 10;
                            gamePanel.character.removeItem(item, 1);
                            gamePanel.character.updateInventorySize();
                            gamePanel.battleBoss.action = "SWITCH";
                            gamePanel.ui.substateBattle = 0;
                            gamePanel.ui.commandNum = 0;
                            gamePanel.ui.partySlotX = 0;
                            gamePanel.ui.partySlotY = 0;
                            break;
                        case "Defense Potion":
                            gamePanel.character.party.get(0).currentDefense += 10;
                            gamePanel.character.removeItem(item, 1);
                            gamePanel.character.updateInventorySize();
                            gamePanel.battleBoss.action = "SWITCH";
                            gamePanel.ui.substateBattle = 0;
                            gamePanel.ui.commandNum = 0;
                            gamePanel.ui.partySlotX = 0;
                            gamePanel.ui.partySlotY = 0;
                            break;
                        case "Speed Potion":
                            gamePanel.character.party.get(0).currentSpeed += 10;
                            gamePanel.character.removeItem(item, 1);
                            gamePanel.character.updateInventorySize();
                            gamePanel.battleBoss.action = "SWITCH";
                            gamePanel.ui.substateBattle = 0;
                            gamePanel.ui.commandNum = 0;
                            gamePanel.ui.partySlotX = 0;
                            gamePanel.ui.partySlotY = 0;
                            break;
                        case "Full Heal":
                            for(int i = 0; i < gamePanel.character.party.size(); i++){
                                gamePanel.character.party.get(i).hp = gamePanel.character.party.get(i).maxHp;
                            }
                            gamePanel.character.removeItem(item, 1);
                            gamePanel.character.updateInventorySize();
                            gamePanel.battleBoss.action = "SWITCH";
                            gamePanel.ui.substateBattle = 0;
                            gamePanel.ui.commandNum = 0;
                            gamePanel.ui.partySlotX = 0;
                            gamePanel.ui.partySlotY = 0;
                            break;
                    }
                }
            }
            else if(gamePanel.ui.substateBattle == 4) { //PARTY
                gamePanel.ui.substateBattle = 11;
                gamePanel.ui.commandNum = 0;
            }
            else if(gamePanel.ui.substateBattle == 11){
                if(gamePanel.ui.commandNum == 0){
                   
                }
                if(gamePanel.ui.commandNum == 1){
                    Collections.swap(gamePanel.character.party, gamePanel.ui.partySlotX + gamePanel.ui.partySlotY*3, 0);
                    if(gamePanel.isHosting == 1){
                    Packet11GetParty packet = new Packet11GetParty(0, gamePanel.character.party, gamePanel.character.partySize);
                    packet.writeData(gamePanel.socketClient);
                    }
                    if(gamePanel.joined == 1){
                    Packet11GetParty packet = new Packet11GetParty(1, gamePanel.character.party, gamePanel.character.partySize);
                    packet.writeData(gamePanel.socketClient);
                    }
                    gamePanel.battleBoss.action = "SWITCH";
                    gamePanel.ui.substateBattle = 0;
                    gamePanel.ui.commandNum = 0;
                    gamePanel.ui.partySlotX = 0;
                    gamePanel.ui.partySlotY = 0;
                }
                if(gamePanel.ui.commandNum == 2){

                }
                if(gamePanel.ui.commandNum == 3){

                }
            }
            else if(gamePanel.ui.substateBattle == 3) { //CATCH
                    if(gamePanel.battleBoss.caught == 1){
                        gamePanel.ui.substateBattle = 6;
                    }else{
                        gamePanel.ui.substateBattle = 0;
                    }
                }
            else if(gamePanel.ui.substateBattle == 5) {
                gamePanel.character.party.get(0).exp += 50;
                lvlup = gamePanel.character.party.get(0).checkLevel();
                gamePanel.ui.substateBattle = 13;
            }
            else if(gamePanel.ui.substateBattle == 6) {
                if(gamePanel.battleBoss.caught == 1){
                    gamePanel.character.party.get(0).exp += 50;
                    lvlup = gamePanel.character.party.get(0).checkLevel();
                    gamePanel.ui.substateBattle = 13;
                }else{
                    gamePanel.ui.substateBattle = 0;
                }
            }
            else if(gamePanel.ui.substateBattle == 12){
                gamePanel.ui.slotRowBattle = 0;
                gamePanel.ui.slotColBattle = 0;
                gamePanel.ui.substateBattle = 0;
                gamePanel.character.inBattleBoss = 0;
                gamePanel.lost=1;
                gamePanel.boss[5] = null;
                gamePanel.bossBattleOn = false;
                for(int i = 0; i < gamePanel.flames[5].length; i++){
                    gamePanel.flames[5][i] = null;
                }
                lvlup = false;
                gamePanel.battleBoss=null;
                gamePanel.eventHandler.checkEvent();
            }
            else if(gamePanel.ui.substateBattle == 13){
                gamePanel.ui.slotRowBattle = 0;
                gamePanel.ui.slotColBattle = 0;
                gamePanel.ui.substateBattle = 0;
                gamePanel.character.inBattleBoss = 0;
                gamePanel.boss[5] = null;
                gamePanel.bossBattleOn = false;
                for(int i = 0; i < gamePanel.flames[5].length; i++){
                    gamePanel.flames[5][i] = null;
                }
                lvlup = false;
                gamePanel.battleBoss=null;
                gamePanel.gameState = gamePanel.playState;
            }
        }
    }


    public void tradeState(int keyCode) {
        if(keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if(keyCode == KeyEvent.VK_ESCAPE) {
            escPressed = true;
        }

        if(gamePanel.ui.substateTrade == 0){
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.commandNum != 0)
                    gamePanel.ui.commandNum--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.commandNum != 2)
                    gamePanel.ui.commandNum++;
            }

            if(keyCode == KeyEvent.VK_ESCAPE) {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.ui.commandNum = 0;
            }
        }

        if(gamePanel.ui.substateTrade == 1){
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.buySlot != 0)
                    gamePanel.ui.buySlot--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.buySlot != gamePanel.ui.currentNPC.inventory.size() - 1)
                    gamePanel.ui.buySlot++;
            }
        }

        if(gamePanel.ui.substateTrade == 2){
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                if(gamePanel.ui.sellSlot != 0)
                    gamePanel.ui.sellSlot--;
            }
            if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                if(gamePanel.ui.sellSlot != gamePanel.ui.currentNPC.inventory.size() - 1)
                    gamePanel.ui.sellSlot++;
            }
        }

        
    }

}

