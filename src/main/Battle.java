package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Random;

import elements.CharacterMP;
import elements.Javamon;
import multiplayer.packets.Packet10UpdateBattle;

public class Battle {

    CharacterMP character;
    GamePanel gamePanel;
    public int online = 0;

    public String action = "WAITING";
    public int catchluck;
    public int caught=0;

    public Battle(CharacterMP character, GamePanel gamePanel){
        this.character=character;
        this.gamePanel=gamePanel;
    }

    public Battle(CharacterMP character, GamePanel gamePanel, int online){
        this.gamePanel=gamePanel;
        this.character=character;
        this.online = online;
    }

    public void setUpBattle(Graphics2D g2, BufferedImage sprite, int sX, int sY, String direction) {

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.setColor(Color.BLACK);

        double oneScale = (double) gamePanel.tileSize/ (double) character.party.get(0).maxHp;
        double hpBar = oneScale * (double) character.party.get(0).hp;
        if (hpBar < 0)
            hpBar = 0;
            
        switch(direction) {
            case "up":
                sprite = character.back;
                g2.drawImage(sprite, sX, sY+gamePanel.tileSize+46, null);
                g2.drawString(character.username, sX-10, sY+gamePanel.tileSize+36);
                gamePanel.monsters[gamePanel.currentMap][character.index].wX=character.wX;
                gamePanel.monsters[gamePanel.currentMap][character.index].wY=character.wY-24;

                if(gamePanel.isHosting == 1 || (gamePanel.joined == 0 && gamePanel.isHosting == 0))
                    g2.drawImage( character.party.get(0).down1, sX, sY+46, null);
                if(gamePanel.joined == 1)
                    g2.drawImage( character.party.get(0).down1, sX, sY+46, null);

                g2.setColor(Color.BLACK);
                g2.fillRect(sX-1, sY +30, gamePanel.tileSize+2, 12);
                g2.setColor(Color.RED);
                g2.fillRect(sX, sY + 31, (int)hpBar, 10);
                break;
                case "down":
                sprite = character.front;
                g2.drawImage(sprite, sX, sY-gamePanel.tileSize-20, null); 
                g2.drawString(character.username, sX-10, sY-gamePanel.tileSize-30);
                gamePanel.monsters[gamePanel.currentMap][character.index].wX=character.wX;
                gamePanel.monsters[gamePanel.currentMap][character.index].wY=character.wY+gamePanel.tileSize+6;

                if(gamePanel.isHosting == 1 || (gamePanel.joined == 0 && gamePanel.isHosting == 0))
                    g2.drawImage( character.party.get(0).down1, sX, sY-14, null);
                if(gamePanel.joined == 1)
                    g2.drawImage( character.party.get(0).down1, sX, sY-14, null);

                g2.setColor(Color.BLACK);
                g2.fillRect(sX-1, sY - 30, gamePanel.tileSize+2, 12);
                g2.setColor(Color.RED);
                g2.fillRect(sX, sY - 29,  (int)hpBar, 10);
                break;
            case "left":
                sprite = character.left1;
                g2.drawImage(sprite, sX+gamePanel.tileSize, sY, null); 
                g2.drawString(character.username, sX+gamePanel.tileSize-10, sY-10);
                gamePanel.monsters[gamePanel.currentMap][character.index].wX=character.wX-gamePanel.tileSize;
                gamePanel.monsters[gamePanel.currentMap][character.index].wY=character.wY;

                if(gamePanel.isHosting == 1 || (gamePanel.joined == 0 && gamePanel.isHosting == 0))
                    g2.drawImage( character.party.get(0).down1, sX+8, sY, null);
                if(gamePanel.joined == 1)
                    g2.drawImage( character.party.get(0).down1, sX+8, sY, null);

                g2.setColor(Color.BLACK);
                g2.fillRect(sX+7, sY - 16, gamePanel.tileSize+2, 12);
                g2.setColor(Color.RED);
                g2.fillRect(sX+8, sY - 15, (int) hpBar, 10);
                break;
            case "right":
                sprite = character.right1;
                g2.drawImage(sprite, sX-gamePanel.tileSize, sY, null); 
                g2.drawString(character.username, sX-gamePanel.tileSize-10, sY-10);
                gamePanel.monsters[gamePanel.currentMap][character.index].wX=character.wX+gamePanel.tileSize;
                gamePanel.monsters[gamePanel.currentMap][character.index].wY=character.wY;

                if(gamePanel.isHosting == 1 || (gamePanel.joined == 0 && gamePanel.isHosting == 0))
                    g2.drawImage( character.party.get(0).down1, sX-8, sY, null);
                if(gamePanel.joined == 1)
                    g2.drawImage( character.party.get(0).down1, sX-8, sY, null);

                g2.setColor(Color.BLACK);
                g2.fillRect(sX-9, sY - 16, gamePanel.tileSize+2, 12);
                g2.setColor(Color.RED);
                g2.fillRect(sX-8, sY - 15, (int) hpBar, 10);
                break; 
            case "back":
                sprite = character.back;
                g2.drawImage(sprite, sX, sY+gamePanel.tileSize, null); 
                g2.drawString(character.username, sX-10, sY+gamePanel.tileSize-10);
                gamePanel.monsters[gamePanel.currentMap][character.index].wX=character.wX;
                gamePanel.monsters[gamePanel.currentMap][character.index].wY=character.wY-24;

                if(gamePanel.isHosting == 1 || (gamePanel.joined == 0 && gamePanel.isHosting == 0))
                    g2.drawImage( character.party.get(0).down1, sX, sY, null);
                if(gamePanel.joined == 1)
                    g2.drawImage( character.party.get(0).down1, sX, sY, null);

                g2.setColor(Color.BLACK);
                g2.fillRect(sX-1, sY +20, gamePanel.tileSize+2, 12);
                g2.setColor(Color.RED);
                g2.fillRect(sX, sY + 21, (int) hpBar, 10);
                break;
            case "front":
                sprite = character.front;
                g2.drawImage(sprite, sX, sY-gamePanel.tileSize, null); 
                g2.drawString(character.username, sX-10, sY-gamePanel.tileSize-10);
                gamePanel.monsters[gamePanel.currentMap][character.index].wX=character.wX;
                gamePanel.monsters[gamePanel.currentMap][character.index].wY=character.wY+gamePanel.tileSize+6;

                if(gamePanel.isHosting == 1 || (gamePanel.joined == 0 && gamePanel.isHosting == 0))
                    g2.drawImage( character.party.get(0).down1, sX, sY, null);
                if(gamePanel.joined == 1)
                    g2.drawImage( character.party.get(0).down1, sX, sY, null);

                g2.setColor(Color.BLACK);
                g2.fillRect(sX-1, sY - 16, gamePanel.tileSize+2, 12);
                g2.setColor(Color.RED);
                g2.fillRect(sX, sY - 15, (int) hpBar, 10);
                break;
            case "leftSide":
                sprite = character.left1;
                g2.drawImage(sprite, sX+gamePanel.tileSize, sY, null); 
                g2.drawString(character.username, sX+gamePanel.tileSize-10, sY-10);
                gamePanel.monsters[gamePanel.currentMap][character.index].wX=character.wX-gamePanel.tileSize;
                gamePanel.monsters[gamePanel.currentMap][character.index].wY=character.wY;

                if(gamePanel.isHosting == 1 || (gamePanel.joined == 0 && gamePanel.isHosting == 0))
                    g2.drawImage( character.party.get(0).down1, sX+8, sY, null);
                if(gamePanel.joined == 1)
                    g2.drawImage( character.party.get(0).down1, sX+8, sY, null);

                g2.setColor(Color.BLACK);
                g2.fillRect(sX+7, sY - 16, gamePanel.tileSize+2, 12);
                g2.setColor(Color.RED);
                g2.fillRect(sX+8, sY - 15, (int) hpBar, 10);
                break;
            case "rightSide":
                sprite = character.right1;
                g2.drawImage(sprite, sX-gamePanel.tileSize, sY, null); 
                g2.drawString(character.username, sX-gamePanel.tileSize-10, sY-10);
                gamePanel.monsters[gamePanel.currentMap][character.index].wX=character.wX+gamePanel.tileSize;
                gamePanel.monsters[gamePanel.currentMap][character.index].wY=character.wY;

                if(gamePanel.isHosting == 1 || (gamePanel.joined == 0 && gamePanel.isHosting == 0))
                    g2.drawImage( character.party.get(0).down1, sX-8, sY, null);
                if(gamePanel.joined == 1)
                    g2.drawImage( character.party.get(0).down1, sX-8, sY, null);

                g2.setColor(Color.BLACK);
                g2.fillRect(sX-9, sY - 16, gamePanel.tileSize+2, 12);
                g2.setColor(Color.RED);
                g2.fillRect(sX-8, sY - 15, (int) hpBar, 10);
                break;
        }
    }

    public void update() {

        switch (action) {
            case "WAITING":
                break;
            
            case "ATTACK1":
                battleAttack1();
                break;
            
            case "ATTACK2":
                battleAttack2();
                break;

            case "CATCH":
                battleCatch();
                break;
            
            case "SWITCH":
                battleSwitch();
                break;
            
            case "ITEM":
                battleSwitch();
                break;
        }
        action = "WAITING";
    }

    public void battleAttack1() {
        gamePanel.monsters[gamePanel.currentMap][character.index].hp-=((2*character.party.get(0).level/5+2)*character.party.get(0).currentAttack*10/gamePanel.monsters[gamePanel.currentMap][character.index].currentDefense/50+2);
    }

    public void battleAttack2() {
        character.party.get(0).hp-=((2*1/5+2)*gamePanel.monsters[gamePanel.currentMap][character.index].currentAttack*10/character.party.get(0).currentDefense/50+2);
        if(character.party.get(0).hp<0)
            character.party.get(0).hp=0;
    }

    public void battleCatch() {
        Random random = new Random ();
        catchluck = random.nextInt(100) + 1;
        if ((1/gamePanel.monsters[gamePanel.currentMap][character.index].hp/gamePanel.monsters[gamePanel.currentMap][character.index].maxHp)*50+catchluck*0.5 > 30) {
            if(character.partySize < 6){
                character.party.add((Javamon) gamePanel.monsters[gamePanel.currentMap][character.index]);
                character.partySize++;
            }
            else{
                character.box.add((Javamon) gamePanel.monsters[gamePanel.currentMap][character.index]);
                character.boxSize++;
            }
            character.monstersCaunght++;
            caught=1;
        }
        else {
            character.party.get(0).hp-=((2*1/5+2)*gamePanel.monsters[gamePanel.currentMap][character.index].currentAttack*10/character.party.get(0).currentDefense/50+2);
            if (character.party.get(0).hp <= 0) {
                if(character.party.get(0).hp<0)
                    character.party.get(0).hp=0;
                int lost = 1;
                for(int i = 0; i < gamePanel.character.party.size(); i++){
                    if(gamePanel.character.party.get(i).hp > 0){
                        Collections.swap(gamePanel.character.party, i, 0);
                        if(gamePanel.isHosting==1){
                            Packet10UpdateBattle packet = new Packet10UpdateBattle(gamePanel.character.index, gamePanel.character.party.get(0).hp, gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp, 0, gamePanel.currentMap);
                            packet.writeData(gamePanel.socketClient);
                        }
                        if(gamePanel.joined==1){
                            Packet10UpdateBattle packet = new Packet10UpdateBattle(gamePanel.character.index, gamePanel.character.party.get(0).hp, gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp, 1, gamePanel.currentMap);
                            packet.writeData(gamePanel.socketClient);
                        }
                        lost = 0;
                        break;
                    }
                }
                if(lost == 1)
                gamePanel.ui.substateBattle = 12;
            }
        }
    }

    public void battleSwitch() {
        character.party.get(0).hp-=((2*1/5+2)*gamePanel.monsters[gamePanel.currentMap][character.index].currentAttack*10/character.party.get(0).currentDefense/50+2);

        if (character.party.get(0).hp <= 0) {
                    if(character.party.get(0).hp<0)
                        character.party.get(0).hp=0;
                    int lost = 1;
                    for(int i = 0; i < gamePanel.character.party.size(); i++){
                        if(gamePanel.character.party.get(i).hp > 0){
                            Collections.swap(gamePanel.character.party, i, 0);
                            if(gamePanel.isHosting==1){
                                Packet10UpdateBattle packet = new Packet10UpdateBattle(gamePanel.character.index, gamePanel.character.party.get(0).hp, gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp, 0, gamePanel.currentMap);
                                packet.writeData(gamePanel.socketClient);
                            }
                            if(gamePanel.joined==1){
                                Packet10UpdateBattle packet = new Packet10UpdateBattle(gamePanel.character.index, gamePanel.character.party.get(0).hp, gamePanel.monsters[gamePanel.currentMap][gamePanel.character.index].hp, 1, gamePanel.currentMap);
                                packet.writeData(gamePanel.socketClient);
                            }
                            lost = 0;
                            break;
                        }
                    }
                    if(lost == 1)
                    gamePanel.ui.substateBattle = 12;
                }
                else{
                    gamePanel.ui.substateBattle = 7;
                }
    }
}
