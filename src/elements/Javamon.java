package elements;

import main.GamePanel;

public class Javamon extends Element{

    public int level;
    public int exp;
    public int ivlevel;

    String path;

    public Javamon(GamePanel gamePanel, int type) {
        super(gamePanel);

        getJavamon(type);

        nickname = name;

        level = 1;
        ivlevel = 5;
        exp = 0;
        this.type = type;

        down1 = setup(path, gamePanel.tileSize, gamePanel.tileSize);

        collision = true;
    }

    public void getJavamon(int type){
        switch  (type){
            case 0:
                path = "/res/monsters/monster1";
                this.name = "Pharos";
                hp = 15;
                maxHp = 15;
                attack = 5;
                currentAttack = attack;
                defense = 6;
                currentDefense = defense;
                speedstat = 4;
                currentSpeed = speedstat;

                ivmaxHp = 3;
                ivattack = 3;
                ivdefense = 2;
                ivspeedstat = 2;

                spawnMaps[0] = true;
                break;
            case 1: 
                path = "/res/monsters/monster2";
                this.name = "Pharofel";
                hp = 30;
                maxHp = 30;
                attack = 10;
                currentAttack = attack;
                defense = 12;
                currentDefense = defense;
                speedstat = 8;
                currentSpeed = speedstat;

                ivmaxHp = 3;
                ivattack = 3;
                ivdefense = 2;
                ivspeedstat = 2;

                spawnMaps[1] = true;
                break;
            case 2:
                path = "/res/monsters/bat1";
                this.name = "Murcieus";
                hp = 14;
                maxHp = 14;
                attack = 4;
                currentAttack = attack;
                defense = 6;
                currentDefense = defense;
                speedstat = 5;
                currentSpeed = speedstat;

                ivmaxHp = 2;
                ivattack = 3;
                ivdefense = 3;
                ivspeedstat = 2;

                spawnMaps[0] = true;
                break;
            case 3:
                path = "/res/monsters/bat2";
                this.name = "Morcela";
                hp = 28;
                maxHp = 28;
                attack = 8;
                currentAttack = attack;
                defense = 12;
                currentDefense = defense;
                speedstat = 10;
                currentSpeed = speedstat;

                ivmaxHp = 2;
                ivattack = 3;
                ivdefense = 3;
                ivspeedstat = 2;

                spawnMaps[1] = true;
                break;
            case 4:
                path = "/res/monsters/bird1";
                this.name = "Vults";
                hp = 17;
                maxHp = 17;
                attack = 3;
                currentAttack = attack;
                defense = 7;
                currentDefense = defense;
                speedstat = 3;
                currentSpeed = speedstat;

                ivmaxHp = 3;
                ivattack = 2;
                ivdefense = 3;
                ivspeedstat = 2;

                spawnMaps[0] = true;
                break;
            case 5:
                path = "/res/monsters/bird2";
                this.name = "Vulfirs";
                hp = 34;
                maxHp = 34;
                attack = 6;
                currentAttack = attack;
                defense = 14;
                currentDefense = defense;
                speedstat = 6;
                currentSpeed = speedstat;

                ivmaxHp = 3;
                ivattack = 2;
                ivdefense = 3;
                ivspeedstat = 2;


                spawnMaps[1] = true;
                break;
            case 6:
                path = "/res/monsters/dragon1";
                this.name = "Karnshard";
                hp = 13;
                maxHp = 13;
                attack = 7;
                currentAttack = attack;
                defense = 4;
                currentDefense = defense;
                speedstat = 6;
                currentSpeed = speedstat;

                ivmaxHp = 2;
                ivattack = 4;
                ivdefense = 2;
                ivspeedstat = 2;

                spawnMaps[0] = true;
                break;
            case 7:
                path = "/res/monsters/dragon2";
                this.name = "Karnfrost";
                hp = 26;
                maxHp = 26;
                attack = 14;
                currentAttack = attack;
                defense = 8;
                currentDefense = defense;
                speedstat = 12;
                currentSpeed = speedstat;

                ivmaxHp = 2;
                ivattack = 4;
                ivdefense = 2;
                ivspeedstat = 2;

                spawnMaps[1] = true;
                break;
        }
    }

    public int getSpeed() {
        return speedstat;
    }

    public void updateStats(){
        hp += ivmaxHp;
        maxHp += ivmaxHp;
        attack += ivattack;
        defense += ivdefense;
        speedstat += ivspeedstat;
    }

    public boolean checkLevel(){
        if(exp >= (ivlevel * level)){
            exp = exp - (ivlevel * level);
            level++;
            updateStats();
            return true;
        }
        return false;
    }
    
}
