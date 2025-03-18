package elements;

import java.awt.Graphics2D;
import java.net.InetAddress;

import main.GamePanel;
import main.KeyInput;

public class CharacterMP extends Character {

    public InetAddress ipAddress;
    public int port;
    public GamePanel gamePanel;

    public CharacterMP(GamePanel gamePanel, int x, int y, KeyInput input, String username, InetAddress ipAddress, int port, int currentMap) {
        super(gamePanel, input, username);
        this.ipAddress = ipAddress;
        this.port = port;
        this.wX = x;
        this.wY = y;
        this.currentMap = currentMap;
    }

    public CharacterMP(GamePanel gamePanel, int x, int y, String username, InetAddress ipAddress, int port, int currentMap) {
        super(gamePanel, null, username);
        this.ipAddress = ipAddress;
        this.port = port;
        this.wX = x;
        this.wY = y;
        this.currentMap = currentMap;
    }

    @Override
    public void update(int currentMap) {
        super.update(currentMap);
        //System.out.println(currentMap);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }

    @Override
    public void interactMonster(int i) {
        super.interactMonster(i);
    }
    
    @Override
    public void interactNPC(int i) {
        super.interactNPC(i);
    }
}