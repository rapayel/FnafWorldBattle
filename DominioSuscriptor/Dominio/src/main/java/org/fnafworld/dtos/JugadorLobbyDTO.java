package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.Equipo;
import org.fnafworld.ErrorLobby;
/**
 * 
 * @author lagar
 */
public class JugadorLobbyDTO {
    private final String id;
    private final String nombre;
    private final byte[] avatar; 
    private final Equipo equipo;
    private final AnimatronicoDTO[] grupo;
    private final boolean listo;
    private final ErrorLobby error;

    public JugadorLobbyDTO(String id, String nombre, byte[] avatar, Equipo equipo, AnimatronicoDTO[] grupo, boolean listo) {
        this(id, nombre, avatar, equipo, grupo, listo, null);
    }

    @JsonCreator
    public JugadorLobbyDTO(
        @JsonProperty("id") String id, 
        @JsonProperty("nombre") String nombre, 
        @JsonProperty("avatar") byte[] avatar, 
        @JsonProperty("equipo") Equipo equipo, 
        @JsonProperty("grupo") AnimatronicoDTO[] grupo, 
        @JsonProperty("listo") boolean listo, 
        @JsonProperty("error") ErrorLobby error
    ) {
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
        this.equipo = equipo;
        this.grupo = grupo;
        this.listo = listo;
        this.error = error;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public byte[] getAvatar() { return avatar; }
    public Equipo getEquipo() { return equipo; }
    public AnimatronicoDTO[] getGrupo() { return grupo; }
    public boolean isListo() { return listo; }
    public ErrorLobby getError() { return error; }
}