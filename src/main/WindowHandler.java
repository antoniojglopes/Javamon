package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import multiplayer.packets.Packet01Disconnect;

public class WindowHandler implements WindowListener{

    GamePanel gamePanel;

    public WindowHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.frame.addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(gamePanel.isHosting == 1 || gamePanel.joined == 1){
            Packet01Disconnect packet = new Packet01Disconnect(gamePanel.character.getUsername());
            packet.writeData(this.gamePanel.socketClient);
            gamePanel.swaped = 0;
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
