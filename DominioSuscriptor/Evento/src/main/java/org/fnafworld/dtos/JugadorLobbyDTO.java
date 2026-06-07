package org.fnafworld.dtos;

import java.awt.image.BufferedImage;
import org.fnafworld.Equipo;

public class JugadorLobbyDTO {
    private final String id;
    private final String nombre;
    private final BufferedImage avatar;
    private final Equipo equipo;
    private final AnimatronicoDTO[] grupo;
    private final boolean listo;

    public JugadorLobbyDTO(String id, String nombre, BufferedImage avatar, Equipo equipo, AnimatronicoDTO[] grupo, boolean listo) {
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
        this.equipo = equipo;
        this.grupo = grupo;
        this.listo = listo;
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

    public Equipo getEquipo() {
        return equipo;
    }

    public AnimatronicoDTO[] getGrupo() {
        return grupo;
    }

    public boolean isListo() {
        return listo;
    }
}
