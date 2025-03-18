package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip, se;

    URL soundURL[] = new URL [30];
    FloatControl fc, fse;
    int volumeScale = 2,sevolumeScale = 2;
    float volume, sevolume;

    public Sound(){

        soundURL[0]= getClass().getResource("/res/music/Town1.wav");
        soundURL[1]= getClass().getResource("/res/music/Dungeon.wav");
        soundURL[2]= getClass().getResource("/res/music/Town1.wav");
        soundURL[3]= getClass().getResource("/res/music/Dungeon.wav");
        soundURL[4]= getClass().getResource("/res/music/Town1.wav");
        soundURL[5]= getClass().getResource("/res/music/Dungeon.wav");
        soundURL[11]= getClass().getResource("/res/music/Door.wav");
        soundURL[12]= getClass().getResource("/res/music/Fire.wav");
        soundURL[13]= getClass().getResource("/res/music/Boss.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundURL[i]);
            if(i < 10){
            clip = AudioSystem.getClip();        
            clip.open(sound);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
            }
            else{
            se = AudioSystem.getClip();
            se.open(sound);
            fse = (FloatControl) se.getControl(FloatControl.Type.MASTER_GAIN);
            secheckVolume();
            }

        }catch(Exception e){
        }
    }
    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    public void playSE(){
        se.start();
    }

    public void stopSE(){
        se.stop();
    }
    public void checkVolume(){
        switch(volumeScale){
            case 0:
            volume = -80.0f;
            break;
            case 1:
            volume = -20.0f;
            break;
            case 2:
            volume = -12.0f;
            break;
            case 3:
            volume = -5.0f;
            break;
            case 4:
            volume = 0.0f;
            break;
            case 5:
            volume = 6.0f;
            break;
        }
        fc.setValue(volume);
    }
    public void secheckVolume(){
        switch(sevolumeScale){
            case 0:
            sevolume = -80.0f;
            break;
            case 1:
            sevolume = -20.0f;
            break;
            case 2:
            sevolume = -12.0f;
            break;
            case 3:
            sevolume = -5.0f;
            break;
            case 4:
            sevolume = 0.0f;
            break;
            case 5:
            sevolume = 6.0f;
            break;
        }
        fse.setValue(sevolume);
    }
}