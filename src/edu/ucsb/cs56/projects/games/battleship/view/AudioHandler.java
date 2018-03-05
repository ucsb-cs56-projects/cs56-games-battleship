package edu.ucsb.cs56.projects.games.battleship;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import javax.sound.sampled.*;

import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;

/**
 * Class for handling audio. This class is a singleton because
 * there should only be one instance of the game's audio status
 *
 * @version 2.5 (Winter 2018)
 */

public class AudioHandler {
    private static AudioHandler instance = null;
    private Clip clip;
    private Clip loopClip;
    private int volume = -80;

    protected AudioHandler() {
        // Exists only to defeat instantiation
    }

    public static AudioHandler getInstance() {
        if (instance == null) {
            instance = new AudioHandler();
        }
        return instance;
    }

    /**
     * Method that plays audio clip referenced by audioURL
     *
     * @param audioURL audio clip to be played
     **/
    public void playAudioFile(URL audioURL) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
            this.clip.start();
            this.clip.setMicrosecondPosition(0);
            FloatControl ctrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            ctrl.setValue(volume);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Method to loop audio clip
     *
     * @param loopAudioURL audio clip to be looped
     **/
    public void loopAudioFile(URL loopAudioURL) {
        try {
            AudioInputStream loopStream = AudioSystem.getAudioInputStream(loopAudioURL);
            this.loopClip = AudioSystem.getClip();
            this.loopClip.open(loopStream);
            this.loopClip.start();
            this.loopClip.loop(LOOP_CONTINUOUSLY);
            FloatControl ctrl = (FloatControl) loopClip.getControl(FloatControl.Type.MASTER_GAIN);
            ctrl.setValue(volume);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Setter for audio volume
     *
     * @param volume level between 0 and 4
     **/
    public void setVolume(int volume) {
        this.volume = volume;
        if (clip != null) {
            FloatControl ctrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            ctrl.setValue(volume);
        }
        if (loopClip != null) {
            FloatControl ctrl = (FloatControl) loopClip.getControl(FloatControl.Type.MASTER_GAIN);
            ctrl.setValue(volume);
        }
    }

    /**
     * Stops all audio
     **/
    public void stopMusic() {
        this.loopClip.stop();
    }
}