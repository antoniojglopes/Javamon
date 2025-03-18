package multiplayer.packets;

import multiplayer.GameClient;
import multiplayer.GameServer;

public class Packet10UpdateBattle extends Packet{
    
    int index, hpPlayer, hpEnemy, player, currentMap;

    public Packet10UpdateBattle(byte[] data) {
        super(10);
        String[] dataArray = readData(data).split(",");
        this.index = Integer.parseInt(dataArray[0]);
        this.hpPlayer = Integer.parseInt(dataArray[1]);
        this.hpEnemy = Integer.parseInt(dataArray[2]);
        this.player = Integer.parseInt(dataArray[3]);
        this.currentMap = Integer.parseInt(dataArray[4]);
    }

    public Packet10UpdateBattle(int index, int hpPlayer, int hpEnemy, int player, int currentMap) {
        super(10);
        this.index = index;
        this.hpPlayer = hpPlayer;
        this.hpEnemy = hpEnemy;
        this.player = player;
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
        return ("10" + this.index + "," + this.hpPlayer + "," + this.hpEnemy + "," + this.player + "," + this.currentMap).getBytes();
    }

    public int getIndex() {
        return this.index;
    }    

    public int getHpPlayer() {
        return this.hpPlayer;
    }

    public int getHpEnemy() {
        return this.hpEnemy;
    }

    public int getPlayer() {
        return this.player;
    }

    public int getCurrentMap() {
        return this.currentMap;
    }
}
