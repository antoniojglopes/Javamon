package multiplayer.packets;

import multiplayer.GameClient;
import multiplayer.GameServer;

public class Packet07ElementNPCState extends Packet{
    
    int mapNum, index, inDialogue;

    public Packet07ElementNPCState(byte[] data) {
        super(07);
        String[] dataArray = readData(data).split(",");
        this.mapNum = Integer.parseInt(dataArray[0]);
        this.index = Integer.parseInt(dataArray[1]);
        this.inDialogue = Integer.parseInt(dataArray[2]);
    }

    public Packet07ElementNPCState(int mapNum, int index, int inDialogue) {
        super(07);
        this.mapNum = mapNum;
        this.index = index;
        this.inDialogue = inDialogue; 
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
        return ("07" + this.mapNum + "," + this.index + "," + this.inDialogue).getBytes();
    }

    public int getMapNum() {
        return this.mapNum;
    }

    public int getIndex() {
        return this.index;
    }    

    public int getInDialogue() {
        return this.inDialogue;
    }
}
