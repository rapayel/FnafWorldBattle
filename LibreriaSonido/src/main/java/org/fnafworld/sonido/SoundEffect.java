/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.sonido;
/**
 * 
 * @author lagar
 */
public class SoundEffect {

    private final String name;
    private final String path;
    private final int poolSize;

    public SoundEffect(String name, String path, int poolSize) {
        this.name = name;
        this.path = path;
        this.poolSize = poolSize;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getPoolSize() {
        return poolSize;
    }
}