/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import org.fnafworld.dtos.JugadorLobbyDTO;

/**
 *
 * @author lagar
 */
public class EventoEquipoSeleccionado extends EventoResultado{
    private JugadorLobbyDTO jugadorEnNuevoEquipo;

    public EventoEquipoSeleccionado(JugadorLobbyDTO jugadorEnNuevoEquipo, String id) {
        super(id);
        this.jugadorEnNuevoEquipo = jugadorEnNuevoEquipo;
    }

    public JugadorLobbyDTO getJugadorEnNuevoEquipo() {
        return jugadorEnNuevoEquipo;
    }
}
