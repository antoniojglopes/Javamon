package multiplayer.packets;

import java.util.ArrayList;

import elements.Javamon;
import multiplayer.GameClient;
import multiplayer.GameServer;

public class Packet11GetParty extends Packet{
    
    private int party[][] = new int[6][4];
    private int partySize;
    private int player;

    public Packet11GetParty(byte[] data) {
        super(11);
        String[] dataArray = readData(data).split(",");
        this.player = Integer.parseInt(dataArray[0]);
        this.partySize = Integer.parseInt(dataArray[1]);
        for (int i = 0; i < partySize; i++) {
            for (int j = 0; j < 4; j++) {
                this.party[i][j] = Integer.parseInt(dataArray[i * 4 + j + 2]);
            }
        }
    }

    public Packet11GetParty(int player, ArrayList<Javamon> party, int partySize) {
        super(11);
        this.player = player;
        this.partySize = partySize;
        for (int i = 0; i < partySize; i++) {
            this.party[i][0] = party.get(i).type;
            this.party[i][1] = party.get(i).hp;
            this.party[i][2] = party.get(i).exp;
            this.party[i][3] = party.get(i).level;
        }
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
        String data = "11"+ player + "," + partySize;
        for (int i = 0; i < partySize; i++) {
            for (int j = 0; j < 4; j++) {
                data += "," + party[i][j];
            }
        }
        return (data).getBytes();
    }

    public int[][] getParty() {
        return party;
    }

    public int getPartySize() {
        return partySize;
    }

    public int getPlayer() {
        return player;
    }

}
