package org.example;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.InputStream;
import java.net.URL;

public class PlayMusic {

    // Method to play background music from a specified file location
    public void playMusic(String musicLocation) {
        try {
            // Get the music file from the specified location
            URL musicPath = getClass().getResource(musicLocation);

            if (musicPath != null) {
                // Set up the audio input stream and clip to play the music
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);

                // Control the volume of the music
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // Reduce volume by 10 decibels

                // Start playing the music and loop it continuously
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                // If the file is not found, print an error message
                System.out.println("Can't Find File");
            }
        } catch (Exception e) {
            // Print any exceptions that occur during the process
            e.printStackTrace();
        }
    }
}
