package elements;


import main.GamePanel;

public class NPC_Dungeon extends Element{
    
    public int i;

    public NPC_Dungeon(GamePanel gamePanel) {
        super (gamePanel);

        direction = "leftSide";
        speed = 0;

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

        dialogues[0][0] = "Riddle me this.";
        dialogues[0][1] = "Stand me straight, I’m a part of eight.\nFlip me around, I’ll make a sound.";
        dialogues[0][2] = "How many times for our talk, to reveal\nthe hidden walks?";
        dialogues[0][3] = "You should get catch some Javamon\n to advance";

        dialogues[1][0] = "I'm the number of wishes you get no more\nno less.";
        dialogues[1][1] = "You either speak to me again\nor you will not pass!";

        dialogues[2][0] = "Congratulations, my friend, on your\npersitence. The key to revelation";
        dialogues[2][1] = "Lies in your insistence.\nFeel free to go!";

        dialogues[3][0] = "Go catch some Javamon\n and come back later.";
    }

    @Override
    public void setAction () { //Character behaviour
        if (dialoguesSet == 2) {
            if(wX != gamePanel.tileSize*36-3 && wY != gamePanel.tileSize*20+6){
                    if(direction == "leftSide")
                        direction = "down";
                    else if(direction == "front")
                        direction = "left";
                    speed = 1;
                }
            else{
                if(direction == "left")
                    direction = "front";
                else if(direction == "down")
                    direction = "leftSide";
                speed = 0;
            }
        }
        if (dialoguesSet == 3)
            dialoguesSet = 0;
    }

    @Override
    public void update(int currentMap) {
        super.update(currentMap);
    }

    @Override
    public void speak () {

        faceCharacter();

        if(gamePanel.character.party.size() > 0)
            startDialogue (dialoguesSet);
        else
            startDialogue (3);


        //Play with this in order to display dialogues when we want,
        //in this case it ends one dialog set and goes to the next and then resets in the end
        //use differnt conditions to call the dialog sets we want

        dialoguesSet++;

        if (dialogues[dialoguesSet][0] == null){
            dialoguesSet --;
        }
    }
}

