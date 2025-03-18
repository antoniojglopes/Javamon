package elements;

import main.GamePanel;

public class Boss extends Element{

    public int level;

    String path;

    public Boss(GamePanel gamePanel) {
        super(gamePanel);

        nickname = "Rabidus";
        boss=1;
        level = 1;

        getBossStats();
        setDialogue();
        collision = true;
    }

    public void getBossStats(){

        down1 = setup("/res/monsters/bossSleep", gamePanel.tileSize*5, gamePanel.tileSize*5);
        down2 = setup("/res/monsters/boss", gamePanel.tileSize*5, gamePanel.tileSize*5);
        hp = 50;
        maxHp = 50;
        attack = 10;
        currentAttack = attack;
        defense = 10;
        currentDefense = defense;
        speedstat = 10;
        currentSpeed = speedstat;
    }

    public void setDialogue(){
        dialogues[0][0] = "RrRRhHhiIIiI!";
    }

    @Override
    public void speak () {
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

    public int getSpeed() {
        return speedstat;
    }
}    

