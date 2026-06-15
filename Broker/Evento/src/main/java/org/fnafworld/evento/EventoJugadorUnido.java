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
public class EventoJugadorUnido extends EventoResultado {
    private JugadorLobbyDTO jugadorUnido;

    @JsonCreator
    public EventoJugadorUnido(
        @JsonProperty("jugadorUnido") JugadorLobbyDTO jugadorUnido, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.jugadorUnido = jugadorUnido;
    }

    public JugadorLobbyDTO getJugadorUnido() {
        return jugadorUnido;
    }
}
