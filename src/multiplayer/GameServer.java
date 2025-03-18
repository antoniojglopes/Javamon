package multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import elements.CharacterMP;
import elements.Element;
import elements.Javamon;
import main.GamePanel;
import multiplayer.packets.Packet;
import multiplayer.packets.Packet.PacketTypes;
import multiplayer.packets.Packet00Login;
import multiplayer.packets.Packet01Disconnect;
import multiplayer.packets.Packet02Move;
import multiplayer.packets.Packet03UpdateNPC;
import multiplayer.packets.Packet04UpdateJavamon;
import multiplayer.packets.Packet05JavamonRespawn;
import multiplayer.packets.Packet06ElementState;
import multiplayer.packets.Packet07ElementNPCState;
import multiplayer.packets.Packet10UpdateBattle;
import multiplayer.packets.Packet11GetParty;

public class GameServer extends Thread {

    private DatagramSocket socket;
    private GamePanel gamePanel;
    public List<CharacterMP> connectedPlayers = new ArrayList<CharacterMP>();

    public GameServer(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
        default:
        case INVALID:
            break;
        case LOGIN:
            packet = new Packet00Login(data);
            System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet00Login)packet).getUsername()
                    + " has connected...");
            CharacterMP player = new CharacterMP(gamePanel,300, 800, ((Packet00Login)packet).getUsername(), address, port, ((Packet00Login)packet).getCurrentMap());
            this.addConnection(player, ((Packet00Login)packet));
            break;
        case DISCONNECT:
            packet = new Packet01Disconnect(data);
            System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect)packet).getUsername()
                    + " has disconnected...");
            
            this.removeConnection(((Packet01Disconnect)packet));
            break;
        case MOVE:
            packet = new Packet02Move(data);
            this.handleMove(((Packet02Move)packet));
            break;
        case NPC:
            packet = new Packet03UpdateNPC(data);
            this.handleNPC(((Packet03UpdateNPC)packet));
            break;
        case JAVAMON:
            packet = new Packet04UpdateJavamon(data);
            this.handleJavamon(((Packet04UpdateJavamon)packet));
            break;
        case JAVAMONRES:
            packet = new Packet05JavamonRespawn(data);
            this.handleJavamonRes(((Packet05JavamonRespawn)packet));
            break;
        case ELEMJSTATE:
            packet = new Packet06ElementState(data);
            this.handleElemJava(((Packet06ElementState)packet));
            break;
        case ELEMNPCSTATE:
            packet = new Packet07ElementNPCState(data);
            this.handleElemNPC(((Packet07ElementNPCState)packet));
            break;
        case BATTLE:
            packet = new Packet10UpdateBattle(data);
            this.handleBattle(((Packet10UpdateBattle)packet));
            break;
        case PARTY:
            packet = new Packet11GetParty(data);
            this.handleParty(((Packet11GetParty)packet));
            break;
        }
    }


    public void addConnection(CharacterMP player, Packet00Login packet) {
        boolean alreadyConnected = false;
        for (CharacterMP p : this.connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            }else {
                sendData(packet.getData(), p.ipAddress, p.port);
                packet = new Packet00Login(p.getUsername(), p.wX, p.wY, p.currentMap);
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected) {
            this.connectedPlayers.add(player);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getCharacterMPindex(packet.getUsername()));
        packet.writeData(this);
    }  

    public int getNPCsize() {
        int size = 0;
        for (Element npc : this.gamePanel.npc[this.gamePanel.currentMap]) {
            if (npc != null) {
                size++;
            }
        }
        return size;
    }

    public CharacterMP getCharacterMP(String username) {
        for (CharacterMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public int getCharacterMPindex(String username) {
        int index = 0;
        for (CharacterMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (CharacterMP p : connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }

    private void handleMove(Packet02Move packet) {
        if (getCharacterMP(packet.getUsername()) != null) {
            int i = getCharacterMPindex(packet.getUsername());
            this.connectedPlayers.get(i).wX = packet.getX();
            this.connectedPlayers.get(i).wY = packet.getY();
            this.connectedPlayers.get(i).direction = packet.getDirection();
            this.connectedPlayers.get(i).spriteNumber = packet.getSpriteNumber();
            this.connectedPlayers.get(i).currentMap = packet.getCurrentMap();
            packet.writeData(this);
        }
    }

    private void handleNPC(Packet03UpdateNPC packet) {
        if (getNPCsize() > 0) {
                for (int i = 0; i < getNPCsize(); i++) {
                if (gamePanel.npc[gamePanel.currentMap][i] != null) {
                    gamePanel.npc[packet.getMapNumber()][i].wX = packet.getX();
                    gamePanel.npc[packet.getMapNumber()][i].wY = packet.getY();
                    gamePanel.npc[packet.getMapNumber()][i].direction = packet.getDirection();
                    gamePanel.npc[packet.getMapNumber()][i].spriteNumber = packet.getSpriteNumber();
                    packet.writeData(this);
                }
            }            
        }
    }

    private void handleJavamon(Packet04UpdateJavamon packet) {
        gamePanel.monsters[packet.getMapNum()][packet.getMonsterIndex()] = new Javamon(gamePanel, packet.getType());
        gamePanel.monsters[packet.getMapNum()][packet.getMonsterIndex()].wX = packet.getX();
        gamePanel.monsters[packet.getMapNum()][packet.getMonsterIndex()].wY = packet.getY();
        packet.writeData(this);
    }

    private void handleJavamonRes(Packet05JavamonRespawn packet) {
        gamePanel.monsters[packet.getMapNum()][packet.getMonsterIndex()] = null;
        packet.writeData(this);
    }

    private void handleElemJava(Packet06ElementState packet) {
        if(packet.getPlayer()==1){
            gamePanel.players.get(packet.getPlayer()).isBattling = packet.getInBattle();
            if(packet.getInBattle()==1)
                gamePanel.monsters[packet.getMapNum()][packet.getIndex()].isBattling = 1;
            else
                gamePanel.monsters[packet.getMapNum()][packet.getIndex()].isBattling = 0;
        }
        packet.writeData(this);
    }


    private void handleElemNPC(Packet07ElementNPCState packet) {
        if(packet.getInDialogue()==1)
            gamePanel.npc[packet.getMapNum()][packet.getIndex()].isTalking = 1;
        else
            gamePanel.npc[packet.getMapNum()][packet.getIndex()].isTalking = 0;
        packet.writeData(this);
    }

    private void handleBattle(Packet10UpdateBattle packet) {
        if(gamePanel.players.get(packet.getPlayer()).party.size()>0){
            gamePanel.monsters[packet.getCurrentMap()][packet.getIndex()].hp = packet.getHpEnemy();
            gamePanel.players.get(packet.getPlayer()).party.get(0).hp = packet.getHpPlayer();
        }
        packet.writeData(this);
    }

    private void handleParty(Packet11GetParty packet) {
        gamePanel.players.get(packet.getPlayer()).party.clear();
        
        for (int i = 0; i < packet.getPartySize(); i++) {
        gamePanel.players.get(packet.getPlayer()).party.add(new Javamon(gamePanel, packet.getParty()[i][0]));
        gamePanel.players.get(packet.getPlayer()).party.get(i).hp = packet.getParty()[i][1];
        gamePanel.players.get(packet.getPlayer()).party.get(i).exp = packet.getParty()[i][2];
        gamePanel.players.get(packet.getPlayer()).party.get(i).level = packet.getParty()[i][3];
        }
        gamePanel.players.get(packet.getPlayer()).partySize = packet.getPartySize();
        packet.writeData(this);
    }
}