package data;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import elements.Javamon;

import java.io.FileInputStream;
import java.io.File;
import java.io.ObjectInputStream;

import main.GamePanel;

public class SaveLoad {
    GamePanel gamePanel; 

    public SaveLoad(GamePanel gamePanel)   {
        this.gamePanel = gamePanel;
    }

    public void save(){
        try{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("src/data/save.sav")));
        
        DataStorage ds = new DataStorage();

        //CHAR POSITION AND MAP
        ds.wX = gamePanel.character.wX;
        ds.wY = gamePanel.character.wY;
        ds.map = gamePanel.currentMap;
        ds.partySize = gamePanel.character.party.size();

        //BOSS NPC POSITION
        ds.bossNPCwx = gamePanel.npc[1][0].wX;
        ds.bossNPCwy = gamePanel.npc[1][0].wY;

        if(gamePanel.boss[5] == null){
            ds.boss = 0;
        }       

        for(int i = 0; i < gamePanel.character.partySize; i++) {
            ds.party[i][0] = gamePanel.character.party.get(i).type;
            ds.party[i][1] = gamePanel.character.party.get(i).hp;
            ds.party[i][2] = gamePanel.character.party.get(i).exp;
            ds.party[i][3] = gamePanel.character.party.get(i).level;
            ds.party[i][4] = gamePanel.character.party.get(i).maxHp;
            ds.party[i][5] = gamePanel.character.party.get(i).attack;
            ds.party[i][6] = gamePanel.character.party.get(i).defense;
            ds.party[i][7] = gamePanel.character.party.get(i).speedstat;
            ds.partyNames[i] = gamePanel.character.party.get(i).nickname;
        }

        for(int i = 0; i < gamePanel.character.boxSize; i++) {
            ds.box[i][0] = gamePanel.character.box.get(i).type;
            ds.box[i][1] = gamePanel.character.box.get(i).hp;
            ds.box[i][2] = gamePanel.character.box.get(i).exp;
            ds.box[i][3] = gamePanel.character.box.get(i).level;
            ds.box[i][4] = gamePanel.character.box.get(i).maxHp;
            ds.box[i][5] = gamePanel.character.box.get(i).attack;
            ds.box[i][6] = gamePanel.character.box.get(i).defense;
            ds.box[i][7] = gamePanel.character.box.get(i).speedstat;
            ds.boxNames[i] = gamePanel.character.box.get(i).nickname;
        }

        //write DataStorage object
        oos.writeObject(ds);
        oos.close();
        System.out.println("Save Complete!");

        }
        catch(Exception e)  {
            System.out.println("Save Exception!" + e.getClass() + " " + e.getMessage());
        }
    }
    public void load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("src/data/save.sav")));
            
            //Read the DataStorage object
            DataStorage ds = (DataStorage) ois.readObject();
            
            //CHAR POSITION AND MAP
            gamePanel.character.wX = ds.wX;
            gamePanel.character.wY = ds.wY;
            gamePanel.currentMap = ds.map;
            gamePanel.character.partySize = ds.partySize;
            gamePanel.character.boxSize = ds.boxSize;

            //LOAD BOSS NPC POSITION
            gamePanel.npc[1][0].wX = ds.bossNPCwx;
            gamePanel.npc[1][0].wY = ds.bossNPCwy;

            if(ds.boss == 0){
                gamePanel.boss[5] = null;
            }

            for(int i = 0; i < gamePanel.character.partySize; i++) {
                gamePanel.character.party.add(new Javamon(gamePanel, ds.party[i][0]));
                gamePanel.character.party.get(i).hp = ds.party[i][1];
                gamePanel.character.party.get(i).exp = ds.party[i][2];
                gamePanel.character.party.get(i).level = ds.party[i][3];
                gamePanel.character.party.get(i).maxHp = ds.party[i][4];
                gamePanel.character.party.get(i).attack = ds.party[i][5];
                gamePanel.character.party.get(i).defense = ds.party[i][6];
                gamePanel.character.party.get(i).speedstat = ds.party[i][7];
                gamePanel.character.party.get(i).nickname = ds.partyNames[i];
            }
            for(int i = 0; i < gamePanel.character.boxSize; i++) {
                gamePanel.character.box.add(new Javamon(gamePanel, ds.box[i][0]));
                gamePanel.character.box.get(i).hp = ds.box[i][1];
                gamePanel.character.box.get(i).exp = ds.box[i][2];
                gamePanel.character.box.get(i).level = ds.box[i][3];
                gamePanel.character.box.get(i).maxHp = ds.box[i][4];	
                gamePanel.character.box.get(i).attack = ds.box[i][5];
                gamePanel.character.box.get(i).defense = ds.box[i][6];
                gamePanel.character.box.get(i).speedstat = ds.box[i][7];
                gamePanel.character.box.get(i).nickname = ds.boxNames[i];
            }
            ois.close();
            System.out.println("Load Complete!");
        }
        catch(Exception e){
            System.out.println("Load Exception!");
        }
    }
}