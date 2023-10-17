package elements;

public class Item {
    String name;
    String description;
    int hpup;
    int attackup;
    int defenseup;
    int speedup;

    public Item(String name, String description, int hpup, int attackup, int defenseup, int speedup) {
        this.name = name;
        this.description = description;
        this.hpup = hpup;
        this.attackup = attackup;
        this.defenseup = defenseup;
        this.speedup = speedup;
    }
}
