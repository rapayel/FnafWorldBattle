/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.dtos.JugadorLobbyDTO;

/**
 *
 * @author lagar
 */
public class EventoJugadorListo extends EventoResultado {
    private JugadorLobbyDTO jugadorListo;

    @JsonCreator
    public EventoJugadorListo(
        @JsonProperty("jugadorListo") JugadorLobbyDTO jugadorListo, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.jugadorListo = jugadorListo;
    }

    public JugadorLobbyDTO getJugadorListo() {
        return jugadorListo;
    }
}