package main;

import java.util.Random;

import elements.*;
import multiplayer.packets.Packet04UpdateJavamon;

public class Assets {

    GamePanel gamePanel;
    public int javamonMap[] = new int[10];
    public int javamonPlaced[] = new int[10];

    public Assets(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        javamonMap[0] = 5;
        javamonMap[1] = 6;

        javamonPlaced[0] = 0;
        javamonPlaced[1] = 0;
    }

    public void placeMonsters() {
        Random rand = new Random();
        int randomNumber1 = -1;
        int randomNumber2 = -1; 
        int randomNumber3 = -1;
        int tile = -1;
        boolean placed = false;
        boolean isOccupied = false;

        for (int i = 0; i < javamonMap.length; i++){    //Go through all the maps in the game
            if(javamonMap[i] != 0){
                for(int j = 0; j < javamonMap[i]; j++){ //Go through all the possible monsters in the map
                    if (gamePanel.monsters[i][j] == null){
                        randomNumber1 = rand.nextInt(8);
                        gamePanel.monsters[i][j] = new Javamon(gamePanel, randomNumber1);
                        if(gamePanel.monsters[i][j].spawnMaps[i] == false){
                            gamePanel.monsters[i][j] = null;
                            j--;
                            continue;
                        }
                        placed = false;
                        while (placed == false){
                            randomNumber2 = rand.nextInt(gamePanel.maxWorldCol - 1);    //random number between 0 and 27 (maxWorldCol - 1)
                            randomNumber3 = rand.nextInt(gamePanel.maxWorldRow - 1);    //random number between 0 and 22 (maxWorldRow - 1)
                            isOccupied = false;

                            if (randomNumber2 != 0 || randomNumber3 != 0){        //if the monster is not placed in the first tile of the map
                                tile = gamePanel.manageTiles.mapTileNum[i][randomNumber2][randomNumber3];

                                if (gamePanel.manageTiles.tiles[tile].isSolid == false){    //if the tile is not solid

                                    for(int h = 0; h < gamePanel.monsters[i].length; h++){
                                        if (gamePanel.monsters[i][h] != null){
                                            if (gamePanel.monsters[i][h].wX == randomNumber2 * gamePanel.tileSize && gamePanel.monsters[i][h].wY == randomNumber3 * gamePanel.tileSize){
                                                isOccupied = true;
                                            }
                                        }
                                    }
                                    if (isOccupied == false){
                                        gamePanel.monsters[i][j].wX = randomNumber2 * gamePanel.tileSize;
                                        gamePanel.monsters[i][j].wY = randomNumber3 * gamePanel.tileSize;
                                        placed = true;
                                        javamonPlaced[i]++;
                                        if(gamePanel.isHosting == 1) {
                                            Packet04UpdateJavamon packet = new Packet04UpdateJavamon(gamePanel.monsters[i][j].wX, gamePanel.monsters[i][j].wY, i, j, randomNumber1);
                                            packet.writeData(gamePanel.socketClient);
                                        }
                                    }
                                }
                            }    
                        }
                    }
                }
            }
        }
    }

    
    public void setNPC() {
        int mapNum = 0;
        
        gamePanel.npc[mapNum][0] = new NPC_OldTow (gamePanel);
        gamePanel.npc[mapNum][0].wX = gamePanel.tileSize*32;
        gamePanel.npc[mapNum][0].wY = gamePanel.tileSize*25;

        gamePanel.npc[mapNum][1] = new NPC_BigBoy (gamePanel);
        gamePanel.npc[mapNum][1].wX = 716;
        gamePanel.npc[mapNum][1].wY = 744;

        gamePanel.npc[mapNum][2] = new NPC_ScientistF (gamePanel);
        gamePanel.npc[mapNum][2].wX = 2300;
        gamePanel.npc[mapNum][2].wY = 934;

        gamePanel.npc[mapNum][3] = new NPC_ScientistM (gamePanel);
        gamePanel.npc[mapNum][3].wX = 2250;
        gamePanel.npc[mapNum][3].wY = 934;

        //se quiserem adicionar NPC noutros mapas fazer 
        mapNum = 1;
        
        gamePanel.npc[mapNum][0] = new NPC_Dungeon (gamePanel);
        gamePanel.npc[mapNum][0].wX = gamePanel.tileSize*39-3;
        gamePanel.npc[mapNum][0].wY = gamePanel.tileSize*18+6;


        mapNum = 3;
        
        gamePanel.npc[mapNum][0] = new NPC_Merchant (gamePanel);
        gamePanel.npc[mapNum][0].wX = gamePanel.tileSize*27;
        gamePanel.npc[mapNum][0].wY = gamePanel.tileSize*41;

    }

    public void setBoss(){
        gamePanel.boss[5] = new Boss(gamePanel);
        gamePanel.boss[5].wX = gamePanel.tileSize*23;
        gamePanel.boss[5].wY = gamePanel.tileSize*16;
    }

    public void setFlames(){
        gamePanel.flames[5][0] = new Flames(gamePanel);
        gamePanel.flames[5][0].wX = gamePanel.tileSize*12;
        gamePanel.flames[5][0].wY = gamePanel.tileSize*19;
        gamePanel.flames[5][0].temp = true;
        
        gamePanel.flames[5][1] = new Flames(gamePanel);
        gamePanel.flames[5][1].wX = gamePanel.tileSize*12;
        gamePanel.flames[5][1].wY = gamePanel.tileSize*17;
        gamePanel.flames[5][1].temp = true;
        
        gamePanel.flames[5][2] = new Flames(gamePanel);
        gamePanel.flames[5][2].wX = gamePanel.tileSize*12;
        gamePanel.flames[5][2].wY = gamePanel.tileSize*18;
        gamePanel.flames[5][2].temp = true;
        
        gamePanel.flames[5][3] = new Flames(gamePanel);
        gamePanel.flames[5][3].wX = gamePanel.tileSize*30;
        gamePanel.flames[5][3].wY = gamePanel.tileSize*17;
        gamePanel.flames[5][3].temp = true;
        
        gamePanel.flames[5][4] = new Flames(gamePanel);
        gamePanel.flames[5][4].wX = gamePanel.tileSize*30;
        gamePanel.flames[5][4].wY = gamePanel.tileSize*18;
        gamePanel.flames[5][4].temp = true;
        
        gamePanel.flames[5][5] = new Flames(gamePanel);
        gamePanel.flames[5][5].wX = gamePanel.tileSize*30;
        gamePanel.flames[5][5].wY = gamePanel.tileSize*19;
        gamePanel.flames[5][5].temp = true;
    }
}
