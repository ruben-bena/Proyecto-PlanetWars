package GUI;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private FloatControl volumeControl;
    private boolean isMuted;
    public AudioPlayer(String clipPath, int volumeValue, boolean continuos) {
        new Thread(() -> {
        // Initializes JavaFX runtime
            File audioFile = new File(clipPath);
            AudioInputStream audioStream;
            isMuted = false;
            try {
                audioStream = AudioSystem.getAudioInputStream(audioFile);

                // Get a sound clip resource
                Clip clip = AudioSystem.getClip();
                
                // Open audio clip and load samples from the audio input stream
                clip.open(audioStream);
                
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float volume = -30 + volumeValue; // Lower value = quieter; 0.0f is default max
                volumeControl.setValue(volume);

                // Start playing the audio
                clip.start();
                if(continuos) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            } catch (UnsupportedAudioFileException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        }).start();

    }

    public static void buttonSound() {
        new AudioPlayer("M3-Programacion/GUI/sfx/button_sound.wav", 10, false);
    }
 
    public static void doAlarm() {
        new AudioPlayer("M3-Programacion/GUI/sfx/alarm.wav", 20, false);
    }

    public static AudioPlayer doBGM() {
        return new AudioPlayer("M3-Programacion/GUI/sfx/bgm.wav", 0, true);
    }

    public static void doError() {
        new AudioPlayer("M3-Programacion/GUI/sfx/error.wav", 20, false);
    }

    public static void doExplosion() {
        new AudioPlayer("M3-Programacion/GUI/sfx/explosion-8bit.wav", 20, false);
    }

    public void changeVolume(int newVolume) {
        volumeControl.setValue(-30 + newVolume);
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean isMuted) {
        this.isMuted = isMuted;
    }
            
    
}

