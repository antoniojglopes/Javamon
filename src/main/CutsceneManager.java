package main;

import java.awt.Graphics2D;

public class CutsceneManager {

    GamePanel gamePanel;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    public int increment=0;

    public final int NA = 0;
    public final int Boss = 1;

    public CutsceneManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch (sceneNum) {
            case NA:
                break;
            case Boss:
                sceneBoss();
                break;
        }
    }

    private void sceneBoss() {
        if(scenePhase==0) {
            gamePanel.bossBattleOn = true; 

            gamePanel.drawing = false;

            scenePhase++;
        }
        if(scenePhase==1) {
            gamePanel.character.wX += 2;
            increment+=2;
            gamePanel.character.spriteCounter++;
                if(gamePanel.character.spriteCounter > 12) {
                    if(gamePanel.character.spriteNumber == 1)
                        gamePanel.character.spriteNumber = 2;
                    else if(gamePanel.character.spriteNumber == 2)
                        gamePanel.character.spriteNumber = 1;
                    gamePanel.character.spriteCounter = 0;
                }
            if(gamePanel.character.wX >21*gamePanel.tileSize) {
                scenePhase++;
            }
        }
        if(scenePhase==2) {
            gamePanel.boss[5].speak();
            scenePhase++;
        }
    }

}
