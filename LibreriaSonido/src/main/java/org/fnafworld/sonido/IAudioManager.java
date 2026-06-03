/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.sonido;
/**
 * 
 * @author lagar
 */
public interface IAudioManager {
    void loadEffect(String name, String path, int poolSize);
    void playEffect(String name);
    void loadMusic(String path);
    void playMusicLoop();
    void stopMusic();
}