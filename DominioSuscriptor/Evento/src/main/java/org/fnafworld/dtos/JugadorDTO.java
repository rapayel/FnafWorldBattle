/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import java.awt.image.BufferedImage;
import org.fnafworld.Equipo;

/**
 *
 * @author lagar
 */
public class JugadorDTO {
    private final String id; 
    private final String nombre;
    private final BufferedImage avatar;
    private final AnimatronicoDTO[] grupo;
    private final boolean miTurno;
    private final Equipo equipo;

    public JugadorDTO(String id, String nombre, BufferedImage avatar, AnimatronicoDTO[] grupo, boolean miTurno, Equipo equipo) {
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
        this.grupo = grupo;
        this.miTurno = miTurno;
        this.equipo = equipo;
    }
    
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public BufferedImage getAvatar() {
        return avatar;
    }

    public AnimatronicoDTO[] getGrupo() {
        return grupo;
    }

    public boolean isMiTurno() {
        return miTurno;
    }   

    public Equipo getEquipo() {
        return equipo;
    }
}
