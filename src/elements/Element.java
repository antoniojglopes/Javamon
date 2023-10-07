package elements;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Element {
    
    public int wX;
    public int wY;
    public int speed;

    public BufferedImage back, front, up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
}
