package multiplayer.packets;


import multiplayer.GameClient;
import multiplayer.GameServer;

public class Packet04UpdateJavamon extends Packet{

    int type;
    int wX, wY;
    int mapNum, monsterIndex;

    public Packet04UpdateJavamon(byte[] data) {
        super(04);
        String[] dataArray = readData(data).split(",");
        this.wX = Integer.parseInt(dataArray[0]);
        this.wY = Integer.parseInt(dataArray[1]);
        this.mapNum = Integer.parseInt(dataArray[2]);
        this.monsterIndex = Integer.parseInt(dataArray[3]);
        this.type = Integer.parseInt(dataArray[4]);
    }

    public Packet04UpdateJavamon(int x, int y, int mapNum, int monsterIndex, int type) {
        super(04);
        this.type = type;
        this.wX = x;
        this.wY = y;
        this.mapNum = mapNum;
        this.monsterIndex = monsterIndex;        
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
        return ("04" + this.wX + "," + this.wY + "," + this.mapNum + "," + this.monsterIndex + "," + this.type).getBytes();
    }

    public int getType() {
        return this.type;
    }

    public int getX() {
        return this.wX;
    }

    public int getY() {
        return this.wY;
    }

    public int getMapNum() {
        return this.mapNum;
    }

    public int getMonsterIndex() {
        return this.monsterIndex;
    }
}
