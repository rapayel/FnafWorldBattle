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
public class EventoErrorJugadorLobby extends EventoError {
    private JugadorLobbyDTO jugadorError;

    @JsonCreator
    public EventoErrorJugadorLobby(
        @JsonProperty("jugadorError") JugadorLobbyDTO jugadorError, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.jugadorError = jugadorError;
    }

    public JugadorLobbyDTO getJugadorError() {
        return jugadorError;
    }
}