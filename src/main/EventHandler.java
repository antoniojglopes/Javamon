package main;

public class EventHandler {

    GamePanel gamePanel;
    EventRect eventRect[][][];
    int tempMap, tempCol, tempRow;


    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;        
        
        eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        
        int col = 0;
        int row = 0;
        int map = 0;
        
        while(map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row< gamePanel.maxWorldRow) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 3;
            eventRect[map][col][row].height = 3;
            eventRect[map][col][row].eventRectDefaultx = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaulty = eventRect[map][col][row].y;

            col ++;
            if(col == gamePanel.maxWorldCol) {
                col = 0;
                row ++;
                if(row == gamePanel.maxWorldRow) {
                    row = 0;
                    map ++;
                }
            }
        };
    }

    public void checkEvent() {
        if(hit(0, 48, 22, "right")==true)  {
            changemap(1,2, 48);
            gamePanel.stopMusic();
            gamePanel.playMusic(1);
            }//First map to dungeon
    
            else if(hit(1, 2, 48, "down")==true){
            changemap(0,48, 22);
            gamePanel.stopMusic();
            gamePanel.playMusic(0);
            }//dungeon to first map
    
            else if(hit(1, 39, 18, "right")==true)  {
            changemap(5,1, 18);
            }//dungeon to boss
    
            else if(hit(5, 1, 18, "left")==true)  {
            changemap(1,39, 18);
            }//boss to dungeon
    
            else if(hit(5, 41, 18, "right")==true)  {
            changemap(2,1, 5);
            gamePanel.stopMusic();
            gamePanel.playMusic(0);
            }//boss to last city
            else if(hit(2, 1, 5, "left")==true)  {
            changemap(5,41, 18);
            gamePanel.stopMusic();
            gamePanel.playMusic(1);
            }//last city to boss
            else if(hit(1,17,48,"right")==true) {
            changemap(3,2,48);
            }//dungeon prison to bar
    
            else if(hit(3,1,48,"left")==true) {
            changemap(1,16,48);
            }//bar to prison

            else if(hit(2,2,13,"up")==true || hit(2,3,13,"up")==true) {changemap(4,14,21);}//city to house

            else if(hit(4,1,3,"left") == true) {changemap(2,2,13);}//house to city

            else if(hitboss(5, 12, 18, "right") == true){   
                gamePanel.assets.setFlames();
                gamePanel.playSoundEffect(12);
                gamePanel.playSoundEffect(13);
                bosscutscene();
            }

            else if(gamePanel.lost==1) { //lost battle
                changemap(0,3, 8);
                gamePanel.stopMusic();
                gamePanel.playMusic(0);
                gamePanel.character.direction = "down";
                gamePanel.character.spriteNumber = 3;
                for(int i=0;i<gamePanel.character.party.size();i++) {
                    gamePanel.character.party.get(i).hp = gamePanel.character.party.get(i).maxHp;
                }
                for(int i=0;i<gamePanel.character.box.size();i++) {
                    gamePanel.character.box.get(i).hp = gamePanel.character.box.get(i).maxHp;
                }
            }

    }
    
    public boolean hit(int map, int col, int row,  String reqDirection) {
    
        boolean hit = false;

        if(map == gamePanel.currentMap) {
            gamePanel.character.solidArea.x = gamePanel.character.wX + gamePanel.character.solidArea.x;
            gamePanel.character.solidArea.y = gamePanel.character.wY + gamePanel.character.solidArea.y;
            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;

            if(gamePanel.character.solidArea.intersects(eventRect[map][col][row])) {
                if(gamePanel.character.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any") ){
                    hit = true;
                }
            }

            gamePanel.character.solidArea.x = gamePanel.character.solidAreaDefaultX;
            gamePanel.character.solidArea.y = gamePanel.character.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultx;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaulty;
        }


        return hit;
    }
    public boolean hitboss(int map, int col, int row,  String reqDirection) {
    
        boolean hit = false;

        if(map == gamePanel.currentMap && gamePanel.boss[5]!=null) {          
            if(gamePanel.character.wY>15*gamePanel.tileSize && gamePanel.character.wY<20*gamePanel.tileSize && gamePanel.character.wX < 14*gamePanel.tileSize && gamePanel.character.wX > 13*gamePanel.tileSize) {
                if(gamePanel.character.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any") ){
                    hit = true;
                }
            }
        }
        return hit;
    }

    public void changemap(int map, int col, int row) {

        gamePanel.gameState = gamePanel.transitionState;
        gamePanel.playSoundEffect(11);
        tempMap = map;
        tempCol = col;
        tempRow = row;

    }

    public void bosscutscene(){
        if(gamePanel.bossBattleOn==false){
            gamePanel.gameState=gamePanel.cutsceneState;
            gamePanel.cutMan.sceneNum=gamePanel.cutMan.Boss;
        }
    }
}
