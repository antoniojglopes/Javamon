package main;

import elements.Element;

public class CollisionCheck {

    GamePanel gamePanel;

    public CollisionCheck(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkElement(Element element) {
        
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

    public int checkObject(Element element, boolean character){
        int index = 999;

        for(int i =0; i< gamePanel.objects.length; i++){
            if(gamePanel.objects[i] != null){
                element.solidArea.x = element.wX + element.solidArea.x;
                element.solidArea.y = element.wY + element.solidArea.y;

                gamePanel.objects[i].solidArea.x = gamePanel.objects[i].wX + gamePanel.objects[i].solidArea.x;
                gamePanel.objects[i].solidArea.y = gamePanel.objects[i].wY + gamePanel.objects[i].solidArea.y;

                switch(element.direction) {
                    case "up":
                        element.solidArea.y -= element.speed;
                        if(element.solidArea.intersects(gamePanel.objects[i].solidArea)){
                            if(gamePanel.objects[i].collision == true){
                                element.collision = true;
                            }
                            if(character == true){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        element.solidArea.y += element.speed;
                        if(element.solidArea.intersects(gamePanel.objects[i].solidArea)){
                            if(gamePanel.objects[i].collision == true){
                                element.collision = true;
                            }
                            if(character == true){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        element.solidArea.x -= element.speed;
                        if(element.solidArea.intersects(gamePanel.objects[i].solidArea)){
                            if(gamePanel.objects[i].collision == true){
                                element.collision = true;
                            }
                            if(character == true){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        element.solidArea.x += element.speed;
                        if(element.solidArea.intersects(gamePanel.objects[i].solidArea)){
                            if(gamePanel.objects[i].collision == true){
                                element.collision = true;
                            }
                            if(character == true){
                                index = i;
                            }
                        }
                        break;
                }
                element.solidArea.x = element.solidAreaDefaultX;
                element.solidArea.y = element.solidAreaDefaultY;
                gamePanel.objects[i].solidArea.x = gamePanel.objects[i].solidAreaDefaultX;
                gamePanel.objects[i].solidArea.y = gamePanel.objects[i].solidAreaDefaultY;
            }
        }

        return index;
    }

}
