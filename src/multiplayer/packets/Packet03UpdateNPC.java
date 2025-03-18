package multiplayer.packets;


import multiplayer.GameClient;
import multiplayer.GameServer;

public class Packet03UpdateNPC extends Packet{

    int size;
    String direction;
    int x, y;
    int spriteNumber;
    int mapNumber;

    public Packet03UpdateNPC(byte[] data) {
        super(03);
        String[] dataArray = readData(data).split(",");
        this.size = Integer.parseInt(dataArray[0]); 
        this.direction = dataArray[1];
        this.x = Integer.parseInt(dataArray[2]);
        this.y = Integer.parseInt(dataArray[3]);
        this.spriteNumber = Integer.parseInt(dataArray[4]);
        this.mapNumber = Integer.parseInt(dataArray[5]);
    }

    public Packet03UpdateNPC(int size, String direction, int x, int y, int spriteNumber, int mapNumber) {
        super(03);
        this.size = size;
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.spriteNumber = spriteNumber;
        this.mapNumber = mapNumber;
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
        return ("03" + this.size + "," + this.direction + "," + this.x + "," + this.y + "," + this.spriteNumber + "," + this.mapNumber).getBytes();
    }

    public int getSize() {
        return this.size;
    }

    public String getDirection() {
        return this.direction;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }    

    public int getSpriteNumber() {
        return this.spriteNumber;
    }

    public int getMapNumber() {
        return this.mapNumber;
    }
}
