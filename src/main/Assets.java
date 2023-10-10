package main;

import object.Monster;

public class Assets {

    GamePanel gamePanel;

    public Assets(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void placeObjects() {
        gamePanel.objects[0] = new Monster();
        gamePanel.objects[0].wX = 5 * gamePanel.tileSize;
        gamePanel.objects[0].wY = 5 * gamePanel.tileSize;
    }
}
