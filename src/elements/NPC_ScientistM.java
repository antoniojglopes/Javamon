package elements;

import main.GamePanel;

public class NPC_ScientistM extends Element{
    public NPC_ScientistM(GamePanel gamePanel) {
        super (gamePanel);

        direction = "down";
        speed = 0;

        dialoguesSet = 0;

        getCharacterSprite();
        setDialogue();
    }

    public void getCharacterSprite() {
        // Load character sprites
        up1     = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        up2     = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        down1   = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        down2   = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        left1   = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        left2   = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        right1  = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        right2  = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        front   = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
        back    = setup("/res/npc/scientistM", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() { //Dialogues only support 2 lines of text

        dialogues[0][0] = "...";
        
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

        dialoguesSet = 0;

    }
}
