package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Door extends Objects{
    
    public Door() {

        name = "door";
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/objects/church10.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
