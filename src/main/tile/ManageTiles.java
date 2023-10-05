package main.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.GamePanel;

public class ManageTiles {
    
    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNum[][];

    public ManageTiles(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[20];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        tileSprite();
        loadMap("/maps/map1.txt");
    }

    public void tileSprite() {

        try {
            // Load character sprites
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tiles[1].isSolid = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tiles[2].isSolid = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath) {
        
        try {
            InputStream in = getClass().getResourceAsStream(mapPath);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int row = 0;
            int col = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();
                
                while(col < gamePanel.maxWorldCol) {
                    
                    String tokens[] = line.split(" ");

                    int tileNum = Integer.parseInt(tokens[col]);

                    mapTileNum[col][row] = tileNum;
                    col++;
                }
                if(col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            
        }

    }

    public void drawTiles(Graphics2D g2) {
        
        int worldcol = 0;
        int worldrow = 0;

        while(worldcol < gamePanel.maxWorldCol && worldrow < gamePanel.maxWorldRow) {
            
            int tileNum = mapTileNum[worldcol][worldrow];

            int wX = worldcol * gamePanel.tileSize;
            int wY = worldrow * gamePanel.tileSize;
            int sX = wX - gamePanel.character.wX + gamePanel.character.sX;
            int sY = wY - gamePanel.character.wY + gamePanel.character.sY;

            if(wX + gamePanel.tileSize > gamePanel.character.wX - gamePanel.character.sX && wX - gamePanel.tileSize < gamePanel.character.wX + gamePanel.screenWidth &&
                wY + gamePanel.tileSize > gamePanel.character.wY - gamePanel.character.sY && wY - gamePanel.tileSize < gamePanel.character.wY + gamePanel.screenHeight)
            g2.drawImage(tiles[tileNum].image, sX, sY, gamePanel.tileSize, gamePanel.tileSize, null);

            worldcol++;
            
            if(worldcol == gamePanel.maxWorldCol) {
                worldcol = 0;
                worldrow++;
            }
        }
    }
}
