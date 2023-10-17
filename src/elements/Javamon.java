package elements;

public class Javamon extends Element{
    
    String name;
    String nickName;
    String type;

    int level;
    int exp;
    int expTotalLevel;
    int hp;
    int totalhp;
    int hpperLevel;
    int attack;
    int attackperLevel;
    int defense;
    int defenseperLevel;
    int speed; 
    int speedperLevel;

    public Javamon(String name, String nickName, String type, int level, int exp, int expTotalLevel, int hp, int totalhp, int hpperLevel, int attack, int attackperLevel, int defense, int defenseperLevel, int speed, int speedperLevel) {
        this.name = name;
        this.nickName = nickName;
        this.type = type;
        this.level = level;
        this.exp = exp;
        this.expTotalLevel = expTotalLevel;
        this.hp = hp;
        this.totalhp = totalhp;
        this.hpperLevel = hpperLevel;
        this.attack = attack;
        this.attackperLevel = attackperLevel;
        this.defense = defense;
        this.defenseperLevel = defenseperLevel;
        this.speed = speed;
        this.speedperLevel = speedperLevel;
    }

    public void lvlUp ()
    {
        level++;
        exp = 0;
        expTotalLevel = expTotalLevel + 100;
        totalhp = totalhp + hpperLevel; //increases total HP
        hp = hp + hpperLevel;           //increases current HP
        attack = attack + attackperLevel;
        defense = defense + defenseperLevel;
        speed = speed + speedperLevel;
    }

    public void Heal()
    {
        hp = totalhp;
    }

    public void ItemUse(Item item)
    {
        hp = hp + item.hpup;
        attack = attack + item.attackup;
        defense = defense + item.defenseup;
        speed = speed + item.speedup;
    }
}
