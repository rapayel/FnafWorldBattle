/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author lagar
 */
public class ParticipanteDTO {
    private final JugadorDTO jugador;
    private final AnimatronicoDTO animatronico;
 
    @JsonCreator
    public ParticipanteDTO(
        @JsonProperty("jugador") JugadorDTO jugador, 
        @JsonProperty("animatronico") AnimatronicoDTO animatronico
    ) {
        this.jugador = jugador;
        this.animatronico = animatronico;
    }
 
    public JugadorDTO getJugador() { return jugador; }
    public AnimatronicoDTO getAnimatronico() { return animatronico; }
}
