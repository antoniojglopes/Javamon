package multiplayer.packets;

import multiplayer.GameClient;
import multiplayer.GameServer;

public class Packet06ElementState extends Packet{
    
    int mapNum, index, inBattle, player;

    public Packet06ElementState(byte[] data) {
        super(06);
        String[] dataArray = readData(data).split(",");
        this.mapNum = Integer.parseInt(dataArray[0]);
        this.index = Integer.parseInt(dataArray[1]);
        this.inBattle = Integer.parseInt(dataArray[2]);
        this.player = Integer.parseInt(dataArray[3]);
    }

    public Packet06ElementState(int mapNum, int index, int inBattle, int player) {
        super(06);
        this.mapNum = mapNum;
        this.index = index;
        this.inBattle = inBattle;
        this.player = player;
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
        return ("06" + this.mapNum + "," + this.index + "," + this.inBattle + "," + this.player).getBytes();
    }

    public int getMapNum() {
        return this.mapNum;
    }

    public int getIndex() {
        return this.index;
    }    

    public int getInBattle() {
        return this.inBattle;
    }

    public int getPlayer() {
        return this.player;
    }
}
