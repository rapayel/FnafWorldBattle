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
public class EventoEquipoSeleccionado extends EventoResultado {
    private JugadorLobbyDTO jugadorEnNuevoEquipo;

    @JsonCreator
    public EventoEquipoSeleccionado(
        @JsonProperty("jugadorEnNuevoEquipo") JugadorLobbyDTO jugadorEnNuevoEquipo, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.jugadorEnNuevoEquipo = jugadorEnNuevoEquipo;
    }

    public JugadorLobbyDTO getJugadorEnNuevoEquipo() {
        return jugadorEnNuevoEquipo;
    }
}