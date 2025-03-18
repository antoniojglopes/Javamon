package elements;

import java.util.Random;

import main.GamePanel;

public class NPC_OldTow extends Element{
    
    public int i;

    public NPC_OldTow(GamePanel gamePanel) {
        super (gamePanel);

        direction = "down";
        speed = 1;

        dialoguesSet = -1;

        getCharacterSprite();
        setDialogue();
    }

    public void getCharacterSprite() {
        // Load character sprites
        up1     = setup("/res/npc/character_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2     = setup("/res/npc/character_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1   = setup("/res/npc/character_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2   = setup("/res/npc/character_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1   = setup("/res/npc/character_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2   = setup("/res/npc/character_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1  = setup("/res/npc/character_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2  = setup("/res/npc/character_right_2", gamePanel.tileSize, gamePanel.tileSize);
        front   = setup("/res/npc/front", gamePanel.tileSize, gamePanel.tileSize);
        back    = setup("/res/npc/back", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() { //Dialogues only support 2 lines of text

        dialogues[0][0] = "Say My Name!!";
        dialogues[0][1] = "Player: You're... You're Heisenberg!";
        dialogues[0][2] = "You are goddamn right!";
        dialogues[0][3] = "You know where i live?";
        dialogues[0][4] = "Player: N-No...";
        dialogues[0][5] = "My name is Walter Hartwell White.I live at\n308 Negra Arroyo Lane,"; 
        dialogues[0][6] = "Albuquerque New Mexico 87104. This is\nmy confession.";
        dialogues[1][0] = "Jesse, Jesse...";
        dialogues[1][1] = "LET'S COOK JESSE!!";
        
    }

    @Override
    public void setAction () { //Character behaviour
        if(gamePanel.isHosting == 1 || (gamePanel.isHosting == 0 && gamePanel.joined == 0)){

            actionLockCounter ++;

            if(actionLockCounter == 35) {
                Random random = new Random ();
                i = random.nextInt(100) + 1;
                if (i <= 25 && wY > gamePanel.tileSize*23 ) {
                    direction = "up";
                }
                if (i > 25 && i <= 50 && wY < gamePanel.tileSize* 27 ) {
                    direction = "down";
                }
                if (i > 50 && i <= 75 && wX > gamePanel.tileSize* 31) {
                    direction = "left";
                }
                if (i > 75 && i <= 100 && wX < gamePanel.tileSize* 34) {
                    direction = "right";
                }
                actionLockCounter = 0;
            } 
        }
    }

    @Override
    public void update(int currentMap) {
        if(gamePanel.isHosting == 1 || (gamePanel.isHosting == 0 && gamePanel.joined == 0)){
            super.update(currentMap);
        }
    }

    @Override
    public void speak () {

        faceCharacter();
        startDialogue (dialoguesSet);


        //Play with this in order to display dialogues when we want,
        //in this case it ends one dialog set and goes to the next and then resets in the end
        //use differnt conditions to call the dialog sets we want

        dialoguesSet++;

        if (dialogues[dialoguesSet][0] == null){
            dialoguesSet = 0;
            //dialoguesSet--; Para repetir ultima fala sempre
        }
    }
}

