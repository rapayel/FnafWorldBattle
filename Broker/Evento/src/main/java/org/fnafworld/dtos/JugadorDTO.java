/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.Equipo;
/**
 * 
 * @author lagar
 */
public class JugadorDTO {
    private final String id; 
    private final String nombre;
    private final byte[] avatar; 
    private final AnimatronicoDTO[] grupo;
    private final boolean miTurno;
    private final Equipo equipo;

    @JsonCreator
    public JugadorDTO(
        @JsonProperty("id") String id, 
        @JsonProperty("nombre") String nombre, 
        @JsonProperty("avatar") byte[] avatar, 
        @JsonProperty("grupo") AnimatronicoDTO[] grupo, 
        @JsonProperty("miTurno") boolean miTurno, 
        @JsonProperty("equipo") Equipo equipo
    ) {
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
        this.grupo = grupo;
        this.miTurno = miTurno;
        this.equipo = equipo;
    }
    
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public byte[] getAvatar() { return avatar; }
    public AnimatronicoDTO[] getGrupo() { return grupo; }
    public boolean isMiTurno() { return miTurno; }   
    public Equipo getEquipo() { return equipo; }
}