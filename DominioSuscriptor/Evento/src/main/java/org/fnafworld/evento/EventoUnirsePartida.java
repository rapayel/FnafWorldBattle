/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import org.fnafworld.dtos.JugadorDTO;

/**
 *
 * @author lagar
 */
public class EventoUnirsePartida extends EventoAccion{
    private JugadorDTO jugadorSolicitud;

    public EventoUnirsePartida(JugadorDTO jugadorSolicitud, String id) {
        super(id);
        this.jugadorSolicitud = jugadorSolicitud;
    }

    public JugadorDTO getJugadorSolicitud() {
        return jugadorSolicitud;
    }
}
