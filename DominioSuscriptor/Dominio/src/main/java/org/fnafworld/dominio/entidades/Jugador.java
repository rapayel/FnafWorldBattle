/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dominio.entidades;

import java.awt.image.BufferedImage;
import org.fnafworld.Equipo;

/**
 *
 * @author lagar
 */
public class Jugador {
    private String id; 
    private String nombre;
    private BufferedImage avatar;
    private Animatronico[] grupo;
    private boolean miTurno;
    private Equipo equipo;

    public Jugador(String id, String nombre, BufferedImage avatar, Animatronico[] grupo, Equipo equipo) {
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
        this.grupo = grupo;
        this.equipo = equipo;
    }       
    
    public boolean equipoDerrotado() {
        if (grupo == null || grupo.length == 0) {
            return true; 
        }
        for (Animatronico animatronico : grupo) {
            if (animatronico != null && animatronico.sigueVivo()) {
                return false; 
            }
        }
        return true;
    }
    
    public boolean miTurno(){
        return miTurno;
    }

    public String getId() {
        return id;
    }

    public Animatronico[] getGrupo() {
        return grupo;
    }

    public void setMiTurno(boolean miTurno) {
        this.miTurno = miTurno;
    }

    public String getNombre() {
        return nombre;
    }

    public BufferedImage getAvatar() {
        return avatar;
    }

    public Equipo getEquipo() {
        return equipo;
    }
}
