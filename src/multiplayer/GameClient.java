package multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import elements.CharacterMP;
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

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private GamePanel gamePanel;

    public GameClient(GamePanel gamePanel, String ipAddress) {

        this.gamePanel = gamePanel;
        
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
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
            /*String message = new String(packet.getData());
            System.out.println("SERVER [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]> " + message);*/
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
            handleLogin((Packet00Login) packet, address, port);
            break;
        case DISCONNECT:
            packet = new Packet01Disconnect(data);
            System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect)packet).getUsername() + " has left the game...");
            gamePanel.removeCharacterMP(((Packet01Disconnect)packet).getUsername());
            break;
        case MOVE:
            packet = new Packet02Move(data);
            handleMove((Packet02Move)packet);
            break;
        case NPC:
            packet = new Packet03UpdateNPC(data);
            handleNPC((Packet03UpdateNPC)packet);
            break;
        case JAVAMON:
            packet = new Packet04UpdateJavamon(data);
            handleJavamon((Packet04UpdateJavamon)packet);
            break;
        case JAVAMONRES:
            packet = new Packet05JavamonRespawn(data);
            handleJavamonRes((Packet05JavamonRespawn)packet);
            break;
        case ELEMJSTATE:
            packet = new Packet06ElementState(data);
            handleElements((Packet06ElementState)packet);
            break;
        case ELEMNPCSTATE:
            packet = new Packet07ElementNPCState(data);
            handleNPCElements((Packet07ElementNPCState)packet);
            break;
        case BATTLE:
            packet = new Packet10UpdateBattle(data);
            handleBattle((Packet10UpdateBattle)packet);
            break;
        case PARTY:
            packet = new Packet11GetParty(data);
            handleParty((Packet11GetParty)packet);
            break;
        }
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMove(Packet02Move packet) {
        this.gamePanel.moveCharacter(packet.getUsername(), packet.getX(), packet.getY(), packet.getDirection(), packet.getSpriteNumber(), packet.getCurrentMap());
    }

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has joined the game...");
        CharacterMP player = new CharacterMP(gamePanel, packet.getX(), packet.getY(), packet.getUsername(), address, port, packet.getCurrentMap());
        gamePanel.players.add(player);
    }

    private void handleNPC(Packet03UpdateNPC packet) {
        this.gamePanel.updateNPC(packet.getSize(), packet.getDirection(), packet.getX(), packet.getY(), packet.getSpriteNumber(), packet.getMapNumber());
    }

    private void handleJavamon(Packet04UpdateJavamon packet) {
        this.gamePanel.updateJavamon(packet.getX(), packet.getY(), packet.getMapNum(), packet.getMonsterIndex(), packet.getType());
    }

    private void handleJavamonRes(Packet05JavamonRespawn packet) {
        this.gamePanel.JavamonRes(packet.getMapNum(), packet.getMonsterIndex());
    }

    private void handleElements(Packet06ElementState packet) {
        this.gamePanel.updateElements(packet.getMapNum(), packet.getIndex(), packet.getInBattle(), packet.getPlayer());
    }

    private void handleNPCElements(Packet07ElementNPCState packet) {
        this.gamePanel.updateNPCElements(packet.getMapNum(), packet.getIndex(), packet.getInDialogue());
    }

    private void handleBattle(Packet10UpdateBattle packet) {
        this.gamePanel.updateBattle(packet.getIndex(), packet.getHpPlayer(), packet.getHpEnemy(), packet.getPlayer(), packet.getCurrentMap());
    }

    private void handleParty(Packet11GetParty packet) {
        this.gamePanel.updateParty(packet.getPlayer(), packet.getParty(), packet.getPartySize());
    }
}