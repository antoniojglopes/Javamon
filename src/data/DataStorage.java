package data;

import java.io.Serializable;

public class DataStorage implements Serializable{

    private static final long serialVersionUID = 1L;

    //player position
    public int wX;
    public int wY;
    public int map;
    public int boss;
    public String username;

    //Javamons
    public int party[][] = new int[6][10];
    public String partyNames[] = new String[6];
    public int partySize;

    public int box[][] = new int[28][10];
    public String boxNames[] = new String[30];
    public int boxSize;

    //Player Inventory
    public int inventory[][] = new int[30][1];

    //BOSS NPC
    public int bossNPCwx, bossNPCwy;
    
}