package elements;

import main.GamePanel;

public class NPC_BigBoy extends Element{
    public NPC_BigBoy(GamePanel gamePanel) {
        super (gamePanel);

        direction = "down";
        speed = 0;

        dialoguesSet = -1;

        getCharacterSprite();
        setDialogue();
    }

    public void getCharacterSprite() {
        // Load character sprites
        up1     = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        up2     = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        down1   = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        down2   = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        left1   = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        left2   = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        right1  = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        right2  = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        front   = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
        back    = setup("/res/npc/bigboy", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() { //Dialogues only support 2 lines of text

        dialogues[0][0] = "Hi!";
        dialogues[0][1] = "So you are the new guy?";
        dialogues[0][2] = "Welcome to our small town!";
        dialogues[0][3] = "There are lots of monsters around here.";
        dialogues[0][4] = "You can befriend them or battle them\nto get exp and evolve your team!";
        dialogues[0][5] = "Go explore the town and have fun!"; 
        dialogues[0][6] = "There definetly is something wrong\nin that cave...";
        dialogues[0][7] = "Been hearing strange noises coming from\nthere lately...";
        dialogues[0][8] = "Don't go there alone!\nEspecially without a team!";
        dialogues[1][0] = "You are back!";
        dialogues[1][1] = "Did you find out what is going on\nin that cave?";
        dialogues[1][2] = "Player: Yes, there is a huge monster\nin there!";
        dialogues[1][3] = "I knew it! I knew it!";
        dialogues[1][4] = "Player: I took care of it, it won't be\na problem anymore.";
        dialogues[1][5] = "Thank you so much for your help!!";
        
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
