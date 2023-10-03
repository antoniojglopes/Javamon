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
    int mapTileNum[][];

    public ManageTiles(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
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

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

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

            while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                String line = br.readLine();
                
                while(col < gamePanel.maxScreenCol) {
                    
                    String tokens[] = line.split(" ");

                    int tileNum = Integer.parseInt(tokens[col]);

                    mapTileNum[col][row] = tileNum;
                    col++;
                }
                if(col == gamePanel.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            
        }

    }

    public void drawTiles(Graphics2D g2) {
        
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
            
            int tileNum = mapTileNum[col][row];
            
            g2.drawImage(tiles[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;
            
            if(col == gamePanel.maxScreenCol) {
                col = 0;
                row++;
                x = 0;
                y += gamePanel.tileSize;
            }
        }
           
    }
}
