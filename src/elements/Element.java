package elements;

import java.awt.image.BufferedImage;

public class Element {
    
    public int x;
    public int y;
    public int speed;

    public BufferedImage front, up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;
}
