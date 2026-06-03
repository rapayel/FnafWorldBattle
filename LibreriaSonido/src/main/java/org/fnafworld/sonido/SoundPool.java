/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.sonido;

import javax.sound.sampled.Clip;

public class SoundPool {

    private final Clip[] clips;
    private int index;

    public SoundPool(Clip[] clips) {
        this.clips = clips;
        this.index = 0;
    }

    public synchronized void play() {

        Clip clip = clips[index];

        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setFramePosition(0);
        clip.start();

        index = (index + 1) % clips.length;
    }
}