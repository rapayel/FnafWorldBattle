/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.dtos.JugadorDTO;

/**
 *
 * @author lagar
 */
public class EventoUnirsePartida extends EventoAccion{
    private JugadorDTO jugadorSolicitud;

    @JsonCreator
    public EventoUnirsePartida(
        @JsonProperty("jugadorSolicitud") JugadorDTO jugadorSolicitud, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.jugadorSolicitud = jugadorSolicitud;
    }

    public JugadorDTO getJugadorSolicitud() {
        return jugadorSolicitud;
    }
}
