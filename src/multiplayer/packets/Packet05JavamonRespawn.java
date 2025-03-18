package multiplayer.packets;


import multiplayer.GameClient;
import multiplayer.GameServer;

public class Packet05JavamonRespawn extends Packet{

    int mapNum, monsterIndex;

    public Packet05JavamonRespawn(byte[] data) {
        super(05);
        String[] dataArray = readData(data).split(",");
        this.mapNum = Integer.parseInt(dataArray[0]);
        this.monsterIndex = Integer.parseInt(dataArray[1]);
    }

    public Packet05JavamonRespawn(int mapNum, int monsterIndex) {
        super(05);
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
        return ("05" + this.mapNum + "," + this.monsterIndex).getBytes();
    }

    public int getMapNum() {
        return this.mapNum;
    }

    public int getMonsterIndex() {
        return this.monsterIndex;
    }
}
