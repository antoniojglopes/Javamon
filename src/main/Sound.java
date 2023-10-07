package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    
    Clip clip;
    URL soudUrl[] = new URL[10];

    public Sound() {

        soudUrl[0] = getClass().getResource("/sound/mainsong.wav");
    }

    public void setFile(int i) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soudUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
        }
    }

    public void play() {
        
        clip.start();
    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {

        clip.stop();
    }
}
