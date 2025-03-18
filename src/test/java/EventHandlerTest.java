import org.junit.Before;
import org.junit.Test;

import elements.CharacterMP;

import static org.junit.Assert.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import elements.CharacterMP;

import main.EventHandler;
import main.GamePanel;

public class EventHandlerTest {

    private EventHandler eventHandler;
    private GamePanel gamePanel;
    private CharacterMP character;
    private JFrame jframe;

    @Before
    public void setUp() {
        // Initialize the GamePanel and EventHandler for each test
        jframe = new JFrame();
        gamePanel = new GamePanel(jframe);
        character = new CharacterMP(gamePanel,gamePanel.tileSize * 2, gamePanel.  tileSize * 8, null, "123", null, -1, 0);
        gamePanel.character = character;
        eventHandler = new EventHandler(gamePanel);
    }

    @Test
    public void testCheckEvent_hitRight_shouldChangeMap() {
        // Arrange
        gamePanel.character.wX = gamePanel.tileSize*48;
        gamePanel.character.wY = gamePanel.tileSize*22;
        gamePanel.currentMap = 0;
        gamePanel.character.direction = "right";

        // Act
        eventHandler.checkEvent();

        // Assert
        assertEquals(1, gamePanel.currentMap);
        assertEquals(2, gamePanel.character.wX);
        assertEquals(48, gamePanel.character.wY);
    }

    @Test
    public void testCheckEvent_hitDown_shouldChangeMap() {
        // Arrange
        gamePanel.character.wX = 2;
        gamePanel.character.wY = 48;
        gamePanel.currentMap = 1;
        gamePanel.character.direction = "down";

        // Act
        eventHandler.checkEvent();

        // Assert
        assertEquals(0, gamePanel.currentMap);
        assertEquals(48, gamePanel.character.wX);
        assertEquals(22, gamePanel.character.wY);
    }

    // Add more test cases for the hit() method

    @Test
    public void testHitBoss_hitBossEventRect_shouldReturnTrue() {
        // Arrange
        int map = 5;
        int col = 12;
        int row = 18;
        String reqDirection = "right";
       // gamePanel.boss[5] = new Boss(); // Assuming Boss class exists

        // Act
        boolean result = eventHandler.hitboss(map, col, row, reqDirection);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testHitBoss_noHitBossEventRect_shouldReturnFalse() {
        // Arrange
        int map = 5;
        int col = 12;
        int row = 18;
        String reqDirection = "left";
        gamePanel.boss[5] = null;

        // Act
        boolean result = eventHandler.hitboss(map, col, row, reqDirection);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testBossCutscene_shouldUpdateGameStateAndSetSceneNumber() {
        // Arrange
        gamePanel.bossBattleOn = false;

        // Act
        eventHandler.bosscutscene();

        // Assert
        assertEquals(gamePanel.cutsceneState, gamePanel.gameState);
        assertEquals(gamePanel.cutMan.Boss, gamePanel.cutMan.sceneNum);
    }

    // Add more test cases for the bosscutscene() method

}