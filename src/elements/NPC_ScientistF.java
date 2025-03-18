package elements;

import main.GamePanel;

public class NPC_ScientistF extends Element{
    public NPC_ScientistF(GamePanel gamePanel) {
        super (gamePanel);

        direction = "down";
        speed = 0;

        dialoguesSet = -1;

        getCharacterSprite();
        setDialogue();
    }

    public void getCharacterSprite() {
        // Load character sprites
        up1     = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        up2     = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        down1   = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        down2   = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        left1   = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        left2   = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        right1  = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        right2  = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        front   = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
        back    = setup("/res/npc/scientistF", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() { //Dialogues only support 2 lines of text

        dialogues[0][0] = "Hello yong man, me and my husband\nare scientists.";
        dialogues[0][1] = "We have been studing these monsters\nfor years.";
        dialogues[0][2] = "Last year we found a HUGE one that since\nthen has been terrorizing the city.";
        dialogues[0][3] = "Can you help us?";
        dialogues[0][4] = "There is a store to your right,\nonce you enter the cave.";
        dialogues[0][5] = "You can buy useful items there."; 
        dialogues[1][0] = "Thank you so much for your help!!";
        dialogues[1][1] = "We will be forever grateful!!";
        
    }

    @Override
    public void update(int currentMap) {
        if(gamePanel.isHosting == 1 || (gamePanel.isHosting == 0 && gamePanel.joined == 0)){
            super.update(currentMap);
        }
    }

    @Override
    public void speak () {

        startDialogue (dialoguesSet);

        if(gamePanel.boss[5] != null){
            dialoguesSet = 0;
        }
        else{
            dialoguesSet = 1;
        }

    }
}
