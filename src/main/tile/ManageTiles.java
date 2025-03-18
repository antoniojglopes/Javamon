package main.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Scale;

public class ManageTiles {
    
    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNum[][][];

    public ManageTiles(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[200];
        mapTileNum = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow]; 
        tileSprite();
        loadMap("/res/maps/(0)map1.txt",0);
        loadMap("/res/maps/(1)dungeonprison.txt",1);
        loadMap("/res/maps/(2)city1.txt",2);
        loadMap("/res/maps/(3)dungeonbar.txt", 3);
        loadMap("/res/maps/(4)house(city1).txt",4);
        loadMap("/res/maps/(5)dungeonboss.txt",5);
    }

    public void tileSprite() {

        setup(0, "000", false);
        setup(1, "001", true);
        setup(2, "002", true);
        setup(3, "003", true);
        setup(4, "004", false);
        setup(5, "005", false);
        setup(6, "006", true);
        setup(7, "007", true);
        setup(8, "008", true);
        setup(9, "009", true);
        setup(10, "010", true);
        setup(11, "011", false);
        setup(12, "012", true);
        setup(13, "013", false);
        setup(14, "014", true);
        setup(15, "015", false);
        setup(16, "016", true);
        setup(17, "017", true);
        setup(18, "018", false);
        setup(19, "019", false);
        setup(20, "020", true);
        setup(21, "021", true);
        setup(22, "022", true);
        setup(23, "023", true);
        setup(24, "024", true);
        setup(25, "025", true);
        setup(26, "026", true);
        setup(27, "027", true);
        setup(28, "028", true);
        setup(29, "029", true);
        setup(30, "030", true);
        setup(31, "031", true);
        setup(32, "032", true);
        setup(33, "033", true);
        setup(34, "034", true);
        setup(35, "035", true);
        setup(36, "036", true);
        setup(37, "037", true);
        setup(38, "038", false);
        setup(39, "039", true);
        setup(40, "040", true);
        setup(41, "041", true);
        setup(42, "042", true);
        setup(43, "043", true);
        setup(44, "044", true);
        setup(45, "045", true);
        setup(46, "046", true);
        setup(47, "047", true);
        setup(48, "048", true);
        setup(49, "049", true);
        setup(50, "050", true);
        setup(51, "051", true);
        setup(52, "052", true);
        setup(53, "053", true);
        setup(54, "054", true);
        setup(55, "055", true);
        setup(56, "056", true);
        setup(57, "057", true);
        setup(58, "058", true);
        setup(59, "059", true);
        setup(60, "060", true);
        setup(61, "061", true);
        setup(62, "062", true);
        setup(63, "063", true);
        setup(64, "064", true);
        setup(65, "065", true);
        setup(66, "066", true);
        setup(67, "067", true);
        setup(68, "068", true);
        setup(69, "069", false);
        setup(70, "070", false);
        setup(71, "071", true);
        setup(72, "072", true);
        setup(73, "073", true);
        setup(74, "074", true);
        setup(75, "075", true);
        setup(76, "076", true);
        setup(77, "077", false);
        setup(78, "078", false);
        setup(79, "079", false);
        setup(80, "080", false);
        setup(81, "081", false);
        setup(82, "082", false);
        setup(83, "083", false);
        setup(84, "084", false);
        setup(85, "085", false);
        setup(86, "086", false);
        setup(87, "087", false);
        setup(88, "088", false);
        setup(89, "089", false);
        setup(90, "090", false);
        setup(91, "091", false);
        setup(92, "092", false);
        setup(93, "093", false);
        setup(94, "094", false);
        setup(95, "095", false);
        setup(96, "096", false);
        setup(97, "097", false);
        setup(98, "098", false);
        setup(99, "099", false);
        setup(100, "100", false);
        setup(101, "101", false);
        setup(102, "102", false);
        setup(103, "103", false);
        setup(104, "104", true);
        setup(105, "105", true);
        setup(106, "106", true);
        setup(107, "107", true);
        setup(108, "108", true);
        setup(109, "109", true);
        setup(110, "110", true);
        setup(111, "111", true);
        setup(112, "112", true);
        setup(113, "113", true);
        setup(114, "114", true);
        setup(115, "115", true);
        setup(116, "116", false);
        setup(117, "117", true);
        setup(118, "118", true);
        setup(119, "119", true);
        setup(120, "120", false);
        setup(121, "121", true);
        setup(122, "122", true);
        setup(123, "123", true);
        setup(124, "124", true);
        setup(125, "125", true);
        setup(126, "126", false);
        setup(127, "127", false);
        setup(128, "128", true);
        setup(129, "129", true);
        setup(130, "130", true);
        setup(131, "131", true);
        setup(132, "132", true);
        setup(133, "133", true);
        setup(134, "134", true);
        setup(135, "135", true);
        setup(136, "136", true);
        setup(137, "137", true);
        setup(138, "138", true);
        setup(139, "139", true);
        setup(140, "140", true);
        setup(141, "141", true);
        setup(142, "142", true);
        setup(143, "143", true);
        setup(144, "144", true);
        setup(145, "145", true);
        setup(146, "146", true);
        setup(147, "147", true);   
        setup(148, "148", true);
        setup(149, "149", true);
        setup(150, "150", true);
        setup(151, "151", true);
        setup(152, "152", true);
        setup(153, "153", true);
        setup(154, "154", true);
        setup(155, "155", true);
        setup(156, "156", true);
        setup(157, "157", true);     
        setup(158, "158", true);
        setup(159, "159", true);
        setup(160, "160", true);
        setup(161, "161", true);
        setup(162, "162", true);
        setup(163, "163", true);
        setup(164, "164", true);
        setup(165, "165", true);
        setup(166, "166", true);
        setup(167, "167", true);
        setup(168, "168", true);
        setup(169, "169", true);
        setup(170, "170", true);
        setup(171, "171", true);
        setup(172, "172", true);
        setup(173, "173", true);
        setup(174, "174", true);
        setup(175, "175", true);
        setup(176, "176", true);
        setup(177, "177", true);
        setup(178, "178", true);
        setup(179, "179", true);
        setup(180, "180", true);
        setup(181, "181", true);
        setup(182, "182", true);
        setup(183, "183", true);
        setup(184, "184", true);
        setup(185, "185", true);
        setup(186, "186", true);
        setup(187, "187", true);
        setup(188, "188", false);
        setup(189, "189", false);
    }

    public void setup(int index, String imageName, boolean collision){
        Scale scaling= new Scale();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"));
            tiles[index].image = scaling.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tiles[index].isSolid = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream in = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();

                while(col < gamePanel.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum [map][col][row] = num;
                    col++;
                }
                if(col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2) {
        
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            
            int tileNum = mapTileNum[gamePanel.currentMap][worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.character.wX + gamePanel.character.sX;
            int screenY = worldY - gamePanel.character.wY + gamePanel.character.sY;

            if(worldX + gamePanel.tileSize > gamePanel.character.wX - gamePanel.character.sX && worldX - gamePanel.tileSize < gamePanel.character.wX + gamePanel.screenWidth &&
               worldY + gamePanel.tileSize > gamePanel.character.wY - gamePanel.character.sY && worldY - gamePanel.tileSize < gamePanel.character.wY + gamePanel.screenHeight)
               g2.drawImage(tiles[tileNum].image, screenX, screenY, null);
            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        
    }
}

