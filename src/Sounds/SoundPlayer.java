package Sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;

/**
 * Created by Justin Kwan on 5/12/2016.
 */
public class SoundPlayer {

    public static Mixer mixer;
    public static Clip jump;
    public static Clip stomp;
    public static Clip stompjump;
    public static Clip gameOver;


    public SoundPlayer(){
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixerInfos[0]);

        DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, null);
        try {
            jump = (Clip) mixer.getLine(dataLineInfo);
            URL url = getClass().getResource("NSMBWiiSoundEffectsRip/nsmbwiiJump.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            jump.open(ais);

            stomp = (Clip) mixer.getLine(dataLineInfo);
            url = getClass().getResource("NSMBWiiSoundEffectsRip/nsmbwiiStomp1.wav");
            ais = AudioSystem.getAudioInputStream(url);
            stomp.open(ais);

            stompjump = (Clip) mixer.getLine(dataLineInfo);
            url = getClass().getResource("NSMBWiiSoundEffectsRip/nsmbwiiStomp2.wav");
            ais = AudioSystem.getAudioInputStream(url);
            stompjump.open(ais);

            gameOver = (Clip) mixer.getLine(dataLineInfo);
            url = getClass().getResource("NSMBWiiSoundEffectsRip/nsmbwiiDeath.wav");
            ais = AudioSystem.getAudioInputStream(url);
            gameOver.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void playSound(int i){
        switch(i) {
            case 0:
                jump.stop();
                jump.start();
                jump.setMicrosecondPosition(0);
                break;
            case 1:
                stomp.stop();
                stomp.start();
                stomp.setMicrosecondPosition(0);
                break;
            case 2:
                stompjump.stop();
                stompjump.start();
                stompjump.setMicrosecondPosition(0);
                break;
            case 3:
                gameOver.setMicrosecondPosition(0);
                gameOver.start();
                break;
            default:
                break;
        }


    }
}
