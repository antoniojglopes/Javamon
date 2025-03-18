package main;

import elements.Element;

public class CollisionCheck {

 GamePanel gamePanel;

    public CollisionCheck(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Element element, int map) {
        
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
                tileNum1 = gamePanel.manageTiles.mapTileNum[map][elementLeftCol][elementTopRow];
                tileNum2 = gamePanel.manageTiles.mapTileNum[map][elementRightCol][elementTopRow];
                if(gamePanel.manageTiles.tiles[tileNum1].isSolid || gamePanel.manageTiles.tiles[tileNum2].isSolid) {
                    element.collisionOn = true;
                } else {
                    element.collisionOn = false;
                }
                break;
            case "down":
                elementBottomRow = (elementBottomWY + element.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.manageTiles.mapTileNum[map][elementLeftCol][elementBottomRow];
                tileNum2 = gamePanel.manageTiles.mapTileNum[map][elementRightCol][elementBottomRow];
                if(gamePanel.manageTiles.tiles[tileNum1].isSolid || gamePanel.manageTiles.tiles[tileNum2].isSolid) {
                    element.collisionOn = true;
                } else {
                    element.collisionOn = false;
                }
                break;
            case "left":
                elementLeftCol = (elementLeftWX - element.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.manageTiles.mapTileNum[map][elementLeftCol][elementTopRow];
                tileNum2 = gamePanel.manageTiles.mapTileNum[map][elementLeftCol][elementBottomRow];
                if(gamePanel.manageTiles.tiles[tileNum1].isSolid || gamePanel.manageTiles.tiles[tileNum2].isSolid) {
                    element.collisionOn = true;
                } else {
                    element.collisionOn = false;
                }
                break;
            case "right":
                elementRightCol = (elementRightWX + element.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.manageTiles.mapTileNum[map][elementRightCol][elementTopRow];
                tileNum2 = gamePanel.manageTiles.mapTileNum[map][elementRightCol][elementBottomRow];
                if(gamePanel.manageTiles.tiles[tileNum1].isSolid || gamePanel.manageTiles.tiles[tileNum2].isSolid) {
                    element.collisionOn = true;
                } else {
                    element.collisionOn = false;
                }
                break;
        }
    }

    // NPC COLLISION 
    public int checkElement (Element element, Element [][] target, int map) {
        int index = 999;
        
        for(int i =0; i< target[map].length; i++){
            if(target[map][i] != null) {
                element.solidArea.x = element.wX + element.solidArea.x;
                element.solidArea.y = element.wY + element.solidArea.y;

                target[map][i].solidArea.x = target[map][i].wX + target[map][i].solidArea.x;
                target[map][i].solidArea.y = target[map][i].wY + target[map][i].solidArea.y;

                switch(element.direction) {
                    case "up":
                        element.solidArea.y -= element.speed;
                        if(element.solidArea.intersects(target[map][i].solidArea)){
                            element.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        element.solidArea.y += element.speed;
                        if(element.solidArea.intersects(target[map][i].solidArea)){
                                element.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "left":
                        element.solidArea.x -= element.speed;
                        if(element.solidArea.intersects(target[map][i].solidArea)){
                            element.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "right":
                        element.solidArea.x += element.speed;
                        if(element.solidArea.intersects(target[map][i].solidArea)){
                           element.collisionOn = true;
                           index = i;
                        break;
                        }
                }       
                element.solidArea.x = element.solidAreaDefaultX;
                element.solidArea.y = element.solidAreaDefaultY;
                target[map][i].solidArea.x = target[map][i].solidAreaDefaultX;
                target[map][i].solidArea.y = target[map][i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // CHARACTER COLLISION
    public void checkCharacter(Element element) {
        element.solidArea.x = element.wX + element.solidArea.x;
        element.solidArea.y = element.wY + element.solidArea.y;

                gamePanel.character.solidArea.x = gamePanel.character.wX + gamePanel.character.solidArea.x;
                gamePanel.character.solidArea.y = gamePanel.character.wY + gamePanel.character.solidArea.y;

                switch(element.direction) {
                    case "up":
                        element.solidArea.y -= element.speed;
                        if(element.solidArea.intersects(gamePanel.character.solidArea)){
                            element.collisionOn = true;
                        }
                        break;
                    case "down":
                        element.solidArea.y += element.speed;
                        if(element.solidArea.intersects(gamePanel.character.solidArea)){
                                element.collisionOn = true;
                        }
                        break;
                    case "left":
                        element.solidArea.x -= element.speed;
                        if(element.solidArea.intersects(gamePanel.character.solidArea)){
                            element.collisionOn = true;
                        }
                        break;
                    case "right":
                        element.solidArea.x += element.speed;
                        if(element.solidArea.intersects(gamePanel.character.solidArea)){
                           element.collisionOn = true;
                        break;
                        }
                }       
                element.solidArea.x = element.solidAreaDefaultX;
                element.solidArea.y = element.solidAreaDefaultY;
                gamePanel.character.solidArea.x = gamePanel.character.solidAreaDefaultX;
                gamePanel.character.solidArea.y = gamePanel.character.solidAreaDefaultY;
    }
}


