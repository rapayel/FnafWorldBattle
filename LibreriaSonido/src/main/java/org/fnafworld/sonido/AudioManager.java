/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.sonido;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis Rafael
 */
public class AudioManager implements IAudioManager {

    private final Map<String, SoundPool> soundEffects;
    private Clip backgroundMusic;

    public AudioManager() {
        this.soundEffects = new HashMap<>();
    }

    public AudioManager(List<SoundEffect> effects) {
        this();

        for (SoundEffect effect : effects) {
            loadEffect(
                    effect.getName(),
                    effect.getPath(),
                    effect.getPoolSize()
            );
        }
    }

    /**
     * Valida que exista el archivo y que sea un WAV válido.
     */
    private URL loadWavResource(String path) {

        if (path == null || !path.toLowerCase().endsWith(".wav")) {
            throw new IllegalArgumentException(
                    "No es un archivo WAV válido: " + path
            );
        }

        URL url = getClass().getResource(path);

        if (url == null) {
            throw new IllegalArgumentException(
                    "No se encontró el archivo: " + path
            );
        }

        try (AudioInputStream ais =
                     AudioSystem.getAudioInputStream(url)) {

            System.out.println("WAV validado correctamente: " + path);
            return url;

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "WAV inválido o corrupto: " + path,
                    e
            );
        }
    }

    @Override
    public void loadEffect(String name, String path, int poolSize) {

        try {

            URL url = loadWavResource(path);

            try (AudioInputStream ais =
                         AudioSystem.getAudioInputStream(url)) {

                AudioFormat format = ais.getFormat();
                byte[] audioData = ais.readAllBytes();

                Clip[] clips = new Clip[poolSize];

                for (int i = 0; i < poolSize; i++) {

                    Clip clip = AudioSystem.getClip();
                    clip.open(
                            format,
                            audioData,
                            0,
                            audioData.length
                    );

                    clips[i] = clip;
                }

                soundEffects.put(name, new SoundPool(clips));
            }

        } catch (Exception e) {
            System.err.println(
                    "Error cargando efecto '" + name + "'"
            );
            e.printStackTrace();
        }
    }

    @Override
    public void playEffect(String name) {

        SoundPool pool = soundEffects.get(name);

        if (pool != null) {
            pool.play();
        }
    }

    @Override
    public void loadMusic(String path) {

        try {

            URL url = loadWavResource(path);

            try (AudioInputStream ais =
                         AudioSystem.getAudioInputStream(url)) {

                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(ais);
            }

        } catch (Exception e) {
            System.err.println(
                    "Error cargando música: " + path
            );
            e.printStackTrace();
        }
    }

    @Override
    public void playMusicLoop() {

        if (backgroundMusic == null) {
            return;
        }

        backgroundMusic.stop();
        backgroundMusic.setFramePosition(0);
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void stopMusic() {

        if (backgroundMusic != null &&
                backgroundMusic.isRunning()) {

            backgroundMusic.stop();
        }
    }
}