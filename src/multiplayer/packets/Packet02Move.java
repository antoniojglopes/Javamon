package multiplayer.packets;

import multiplayer.GameClient;
import multiplayer.GameServer;

public class Packet02Move extends Packet{
    
    private String username, direction;
    private int x, y, spriteNumber, currentMap;

    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.direction = dataArray[3];
        this.spriteNumber = Integer.parseInt(dataArray[4]);
        this.currentMap = Integer.parseInt(dataArray[5]);
    }

    public Packet02Move(String username, int x, int y, String direction, int spriteNumber, int currentMap) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.spriteNumber = spriteNumber;
        this.currentMap = currentMap;
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("02" + this.username + "," + this.x + "," + this.y + "," + this.direction + "," + this.spriteNumber + "," + this.currentMap).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getDirection() {
        return this.direction;
    }

    public int getSpriteNumber() {
        return this.spriteNumber;
    }

    public int getCurrentMap() {
        return this.currentMap;
    }
}
