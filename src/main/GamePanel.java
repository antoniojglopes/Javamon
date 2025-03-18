package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.SaveLoad;
import elements.Boss;
import elements.CharacterMP;
import elements.Element;
import elements.Flames;
import elements.Javamon;
import main.tile.ManageTiles;
import multiplayer.GameClient;
import multiplayer.GameServer;
import multiplayer.packets.Packet03UpdateNPC;
import multiplayer.packets.Packet04UpdateJavamon;
import multiplayer.packets.Packet11GetParty;


public class GamePanel extends JPanel implements Runnable{
    
    public JFrame frame;

    //Screen parameters
    private final int originalTileSize = 16;
    private final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576

    //World parameters
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public final int maxMap = 10; //Number of maps
    public int currentMap = 0;
    public int isHosting = 0;
    public int joined = 0;
    public int swaped = 0;
    public int lost = 0;
    public boolean bossBattleOn = false;
    public boolean drawing=true;


    //FPS
    private final int FPS = 60;

    public Assets assets = new Assets(this);
    public ManageTiles manageTiles = new ManageTiles(this);
    public KeyInput keyInput = new KeyInput(this);
    public Sound sound = new Sound();
    public Sound se = new Sound();
    Thread gameThread;
    public CollisionCheck collisionCheck = new CollisionCheck(this);
    public CharacterMP character;
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public Element[][] monsters = new Element[maxMap][10];
    public Element npc[][] = new Element[maxMap][10];
    public Boss boss[] = new Boss[maxMap];
    public Flames flames[][] = new Flames[maxMap][6];
    public ArrayList<Element> elementList = new ArrayList<>();
    public ArrayList<CharacterMP> players = new ArrayList<>();
    public GameServer socketServer;
    public GameClient socketClient;
    public WindowHandler windowHandler;
    public Graphics2D g2;
    public Stopwatch stopwatch = new Stopwatch();
    public Battle battle, battleplayer2;
    public BattleBoss battleBoss;
    public CutsceneManager cutMan = new CutsceneManager(this);

    //GameStates
    public int gameState;
    public final int titleState = 0; 
    public final int infoScreen = 1;
    public final int playState = 2;
    public final int menuState = 3;
    public final int battleState = 4;
    public final int dialoguesState = 5;
    public final int tradeState = 6;
    public final int transitionState = 7;
    public final int cutsceneState = 8;
    public final int battleBossState = 9;

    //SaveLoad
    SaveLoad saveLoad = new SaveLoad(this);

    public static GamePanel gamePanel;

    public GamePanel(JFrame frame) {
        this.frame = frame;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setup(){
        gamePanel = this;
        windowHandler = new WindowHandler(this);
        assets.setNPC();
        assets.setBoss();
        character = new CharacterMP(this, gamePanel.tileSize * 2, gamePanel.  tileSize * 8, keyInput, JOptionPane.showInputDialog(this, "Please enter a username"), null, -1, currentMap); //TO CHANGE CHARACTER STARTING POSITION SWITCH HERE AND NOT IN CHAR CLASS
        if(character.username == null)
            character.username = "Jesse";
        getPlayers().add(character);
        gameState = titleState;
    }

    public class Stopwatch {
        private long startTime;
    
        public void start() {
            startTime = System.currentTimeMillis();
        }
    
        public float getElapsedTimeSeconds() {
            return (System.currentTimeMillis() - startTime) / 1000f;
        }
    }

    @Override
    public void run() {

        double intervalTime = 1000000000 / FPS;
        double  delta = 0;
        long lastUpdateTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while(gameThread != null) {
            //Game loop
            currentTime = System.nanoTime();

            delta += (currentTime - lastUpdateTime) / intervalTime;
            timer += currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;

            if(delta >= 1) {
                //Update
                update();
                
                //Draw
                repaint();

                delta--;
            }

            if(timer >= 1000000000)
                timer = 0;
        }
    }

    public void update() {

        if(gameState==playState || gameState==battleState || gameState==dialoguesState || gameState==menuState || gameState==cutsceneState || gameState==battleBossState) {
            if(players.size()>1){
                if(isHosting==1){
                    if(players.get(1).inBattle == 1)
                        battleplayer2 = new Battle(players.get(1), this, 1);
                    else
                        battleplayer2 = null;
                }
                    
                if(joined==1){
                    if(players.get(0).inBattle == 1)
                        battleplayer2 = new Battle(players.get(0), this, 1);
                    else
                        battleplayer2 = null;
                }
            }
        }

        if(gameState == playState) {
            // PLAYER
            if (players.size() > 1 && swaped==0) {
                if(isHosting == 1) {
                    for (int i=0; i<maxMap; i++) {
                        for (int j=0; j<10; j++) {
                            if (monsters[i][j] != null) {
                                Packet04UpdateJavamon packet = new Packet04UpdateJavamon(gamePanel.monsters[i][j].wX, gamePanel.monsters[i][j].wY, i, j, monsters[i][j].type);
                                packet.writeData(gamePanel.socketClient);
                            }
                        }
                    }
                }
                if(joined == 1) {
                    if(getPlayers().size() > 1) {
                        Collections.swap(getPlayers(), 0, 1);
                    }
                }
                swaped = 1;
            }
            
            if(isHosting == 1 || (isHosting == 0 && joined == 0)) {
                for (int i=0; i<maxMap; i++) {
                    assets.javamonPlaced[i]=0;
                    for (int j=0; j<10; j++) {
                        if (monsters[i][j] != null) {
                            assets.javamonPlaced[i]++;
                        }
                    }
                }
                if(assets.javamonPlaced[currentMap] < assets.javamonMap[currentMap])
                    assets.placeMonsters();
            }

            for (int i = 0; i < getPlayers().size(); i++) {
                getPlayers().get(i).update(currentMap);
            }

            // NPC
            for (int j=0; j<maxMap; j++){
                for (int i = 0; i < npc[j].length; i++) {
                    if(npc[j][i] != null) {
                        if (npc[j][i].isTalking == 0) {
                            npc[j][i].update(j);
                        }
                        if(isHosting == 1 || joined == 1) {
                            Packet03UpdateNPC packet = new Packet03UpdateNPC(npc.length, npc[j][i].direction, npc[j][i].wX, npc[j][i].wY, npc[j][i].spriteNumber, j);
                            packet.writeData(socketClient);
                        }
                    }
                }
            }
            
        }
        if (gameState == battleState) {

            if (battle == null) {
                if(isHosting == 1){
                Packet11GetParty packet = new Packet11GetParty(0, gamePanel.character.party, gamePanel.character.partySize);
                packet.writeData(gamePanel.socketClient);
                }
                if(joined == 1){
                Packet11GetParty packet = new Packet11GetParty(1, gamePanel.character.party, gamePanel.character.partySize);
                packet.writeData(gamePanel.socketClient);
                }
                battle = new Battle(character, this);
            } else {
                battle.update();
            }

            if (players.size() > 1 && swaped==0) {
                if(isHosting == 1) {
                    for (int i=0; i<maxMap; i++) {
                        for (int j=0; j<10; j++) {
                            if (monsters[i][j] != null) {
                                Packet04UpdateJavamon packet = new Packet04UpdateJavamon(gamePanel.monsters[i][j].wX, gamePanel.monsters[i][j].wY, i, j, monsters[i][j].type);
                                packet.writeData(gamePanel.socketClient);
                            }
                        }
                    }
                }
                if(joined == 1) {
                    if(getPlayers().size() > 1) {
                        Collections.swap(getPlayers(), 0, 1);
                    }
                }
                swaped = 1;
            }

            if(isHosting == 1 || (isHosting == 0 && joined == 0)) {
                for (int i=0; i<maxMap; i++) {
                    assets.javamonPlaced[i]=0;
                    for (int j=0; j<10; j++) {
                        if (monsters[i][j] != null) {
                            assets.javamonPlaced[i]++;
                        }
                    }
                }
                if(assets.javamonPlaced[currentMap] < assets.javamonMap[currentMap])
                    assets.placeMonsters();
            }

            for (int j=0; j<maxMap; j++){
                for (int i = 0; i < npc[j].length; i++) {
                    if(npc[j][i] != null) {
                        if (npc[j][i].isTalking == 0) {
                            npc[j][i].update(j);
                        }
                        if(isHosting == 1 || joined == 1) {
                            Packet03UpdateNPC packet = new Packet03UpdateNPC(npc.length, npc[j][i].direction, npc[j][i].wX, npc[j][i].wY, npc[j][i].spriteNumber, j);
                            packet.writeData(socketClient);
                        }
                    }
                }
            }
        }
        if (gameState == battleBossState) {

            if (battleBoss == null) {
                battleBoss = new BattleBoss(character, this);
            } else {
                battleBoss.update();
            }

            if (players.size() > 1 && swaped==0) {
                if(isHosting == 1) {
                    for (int i=0; i<maxMap; i++) {
                        for (int j=0; j<10; j++) {
                            if (monsters[i][j] != null) {
                                Packet04UpdateJavamon packet = new Packet04UpdateJavamon(gamePanel.monsters[i][j].wX, gamePanel.monsters[i][j].wY, i, j, monsters[i][j].type);
                                packet.writeData(gamePanel.socketClient);
                            }
                        }
                    }
                }
                if(joined == 1) {
                    if(getPlayers().size() > 1) {
                        Collections.swap(getPlayers(), 0, 1);
                    }
                }
                swaped = 1;
            }

            if(isHosting == 1 || (isHosting == 0 && joined == 0)) {
                for (int i=0; i<maxMap; i++) {
                    assets.javamonPlaced[i]=0;
                    for (int j=0; j<10; j++) {
                        if (monsters[i][j] != null) {
                            assets.javamonPlaced[i]++;
                        }
                    }
                }
                if(assets.javamonPlaced[currentMap] < assets.javamonMap[currentMap])
                    assets.placeMonsters();
            }

            for (int j=0; j<maxMap; j++){
                for (int i = 0; i < npc[j].length; i++) {
                    if(npc[j][i] != null) {
                        if (npc[j][i].isTalking == 0) {
                            npc[j][i].update(j);
                        }
                        if(isHosting == 1 || joined == 1) {
                            Packet03UpdateNPC packet = new Packet03UpdateNPC(npc.length, npc[j][i].direction, npc[j][i].wX, npc[j][i].wY, npc[j][i].spriteNumber, j);
                            packet.writeData(socketClient);
                        }
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2 = (Graphics2D) g;

        //Tiltle Screen
        if(gameState == titleState) {
           ui.draw(g2);
        }
        //Info Screen
        else if(gameState == infoScreen) {
            ui.draw(g2);
        }
        //Others
        else{
            //Tiles
            manageTiles.draw(g2);

            //add NPCs to element list
            for (int i = 0; i < npc[currentMap].length; i++) {
                if(npc[currentMap][i] != null) {
                    elementList.add(npc[currentMap][i]);
                }
            }

            //add monsters to element list
            for (int i = 0; i < monsters[currentMap].length; i++) {
                if(monsters[currentMap][i] != null) {
                    elementList.add(monsters[currentMap][i]);
                }
            }

            //add boss to element list
            if(boss[currentMap] != null) {
                elementList.add(boss[currentMap]);
            }

            //add flames to element list
            for (int i = 0; i < flames[currentMap].length; i++) {
                if(flames[currentMap][i] != null) { 
                    if(flames[currentMap][i].temp == true){
                        elementList.add(flames[currentMap][i]);
                    } 
                }
            }

            if(gamePanel.lost==0)
            for (int i = 0; i < getPlayers().size(); i++) {
                if(getPlayers().get(i).currentMap == currentMap)
                    elementList.add(getPlayers().get(i));
            }

            //add player to element list
            //elementList.add(character);

            //Sort element list by y position
            /*Collections.sort(elementList, new Comparator<Element>() {
                @Override
                public int compare(Element e1, Element e2) {
                    int result = Integer.compare(e1.wY, e2.wY);
                    return result;
                }
            });*/

            //Draw elements
            for (int i = 0; i < elementList.size(); i++) {
                elementList.get(i).draw(g2);
            }

            //Remove elements
            for (int i = 0; i < elementList.size(); i++) {
                elementList.remove(i);
            }

            if(gameState == cutsceneState)
                cutMan.draw(g2);

            //UI
            ui.draw(g2);
        }
       
        g2.dispose();
    }

    public void playMusic (int i){

        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic (){
        sound.stop();
    }

    public void playSoundEffect (int i){
        se.setFile(i);
        se.playSE();
    }

    public void stopSoundEffect (){
        se.stopSE();
    }

    public void removeCharacterMP(String username) {
        for (CharacterMP player : getPlayers()) {
            if (player.getUsername().equals(username)) {
                getPlayers().remove(player);
                break;
            }
        }
    }

    public int getCharacterMPIndex(String username) {
        int index = 0;
        for (CharacterMP player : getPlayers()) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void moveCharacter(String username, int x, int y, String direction, int spriteNumber, int currentMap) {
        int i = getCharacterMPIndex(username);
        getPlayers().get(i).wX = x;
        getPlayers().get(i).wY = y;
        getPlayers().get(i).direction = direction;
        getPlayers().get(i).spriteNumber = spriteNumber;
        getPlayers().get(i).currentMap = currentMap;
    }

    public void updateNPC(int i, String direction, int x, int y, int spriteNumber, int mapNumber) {
        for (int j = 0; j < i; j++) {
            if (npc[mapNumber][j] != null) {
                npc[mapNumber][j].direction = direction;
                npc[mapNumber][j].wX = x;
                npc[mapNumber][j].wY = y;
                npc[mapNumber][j].spriteNumber = spriteNumber;
            }
        }
    }

    public void updateJavamon(int x, int y, int mapNum, int monsterIndex, int type) {
        gamePanel.monsters[mapNum][monsterIndex] = new Javamon(this, type);
        monsters[mapNum][monsterIndex].wX = x;
        monsters[mapNum][monsterIndex].wY = y;
    }

    public void JavamonRes(int mapNum, int monsterIndex) {
        gamePanel.monsters[mapNum][monsterIndex] = null;
    }

    public void updateElements(int mapNum, int index, int inBattle, int player) {

        if(player==0 && joined==1 || player==1 && isHosting==1){
        players.get(player).inBattle = inBattle;
        players.get(player).index = index;
        }

        if (inBattle == 1){
            gamePanel.monsters[mapNum][index].isBattling = 1;
        }else if (inBattle == 0 && gamePanel.monsters[mapNum][index]!=null && gamePanel.monsters[mapNum][index].isBattling == 1){
            gamePanel.monsters[mapNum][index].isBattling = 0;
        }
    }

    public void updateNPCElements(int mapNum, int index, int inDialogue) {
        if (inDialogue == 1){
            gamePanel.npc[mapNum][index].isTalking = 1;
        }else if (inDialogue == 0 && gamePanel.npc[mapNum][index].isTalking == 1){
            gamePanel.npc[mapNum][index].isTalking = 0;
        }
    }

    public void updateBattle(int index, int hpPlayer, int hpEnemy, int player, int currentMap) {
        if(player==0 && joined==1 || player==1 && isHosting==1){
        getPlayers().get(player).party.get(0).hp = hpPlayer;
    }
        monsters[currentMap][index].hp = hpEnemy;
    }

    public void updateParty(int player, int party[][], int partySize) {

        if(player==0 && joined==1 || player==1 && isHosting==1){
            players.get(player).party.clear();
            for (int i = 0; i < partySize; i++) {
                players.get(player).party.add(new Javamon(this, party[i][0]));
                players.get(player).party.get(i).hp = party[i][1];
                players.get(player).party.get(i).exp = party[i][2];
                players.get(player).party.get(i).level = party[i][3];
            }
            players.get(player).partySize = partySize;
        }
    }

    public synchronized ArrayList<CharacterMP> getPlayers() {
        return this.players;
    }
}
