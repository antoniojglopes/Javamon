package main;

import elements.Element;

public class CollisionCheck {

    GamePanel gamePanel;

    public CollisionCheck(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checker(Element element) {
        
        int elementLeftWX = element.wX + element.solidArea.x;
        int elementRightWX = element.wX + element.solidArea.x + element.solidArea.width;
        int elementTopWY = element.wY + element.solidArea.y;
        int elementBottomWY = element.wY + element.solidArea.y + element.solidArea.height;

        int elementLeftCol = elementLeftWX / gamePanel.tileSize;
        int elementRightCol = elementRightWX / gamePanel.tileSize;
        int elementTopRow = elementTopWY / gamePanel.tileSize;
        int elementBottomRow = elementBottomWY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch(element.direction) {
            case "up":
                elementTopRow = (elementTopWY - element.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.manageTiles.mapTileNum[elementLeftCol][elementTopRow];
                tileNum2 = gamePanel.manageTiles.mapTileNum[elementRightCol][elementTopRow];
                if(gamePanel.manageTiles.tiles[tileNum1].isSolid || gamePanel.manageTiles.tiles[tileNum2].isSolid) {
                    element.collision = true;
                } else {
                    element.collision = false;
                }
                break;
            case "down":
                elementBottomRow = (elementBottomWY + element.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.manageTiles.mapTileNum[elementLeftCol][elementBottomRow];
                tileNum2 = gamePanel.manageTiles.mapTileNum[elementRightCol][elementBottomRow];
                if(gamePanel.manageTiles.tiles[tileNum1].isSolid || gamePanel.manageTiles.tiles[tileNum2].isSolid) {
                    element.collision = true;
                } else {
                    element.collision = false;
                }
                break;
            case "left":
                elementLeftCol = (elementLeftWX - element.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.manageTiles.mapTileNum[elementLeftCol][elementTopRow];
                tileNum2 = gamePanel.manageTiles.mapTileNum[elementLeftCol][elementBottomRow];
                if(gamePanel.manageTiles.tiles[tileNum1].isSolid || gamePanel.manageTiles.tiles[tileNum2].isSolid) {
                    element.collision = true;
                } else {
                    element.collision = false;
                }
                break;
            case "right":
                elementRightCol = (elementRightWX + element.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.manageTiles.mapTileNum[elementRightCol][elementTopRow];
                tileNum2 = gamePanel.manageTiles.mapTileNum[elementRightCol][elementBottomRow];
                if(gamePanel.manageTiles.tiles[tileNum1].isSolid || gamePanel.manageTiles.tiles[tileNum2].isSolid) {
                    element.collision = true;
                } else {
                    element.collision = false;
                }
                break;
        }
    }
    
}
