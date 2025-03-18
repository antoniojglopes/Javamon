package elements;

import main.GamePanel;

public class Flames extends Element{
    
    public int i;

    public Flames(GamePanel gamePanel) {
        super (gamePanel);
        
        speed = 0;

        dialoguesSet = -1;

        getSprite();
    }

    public void getSprite() {
        // Load character sprites
        down1 = setup("/res/others/Flame", gamePanel.tileSize, gamePanel.tileSize);
    }

}
