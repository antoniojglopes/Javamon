package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Monster extends Objects{
    
    public Monster() {

        name = "monster";
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/objects/monster.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
