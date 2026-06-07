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
public class EventoJugadorUnido extends EventoResultado{
    private JugadorLobbyDTO jugadorUnido;

    public EventoJugadorUnido(JugadorLobbyDTO jugadorUnido, String id) {
        super(id);
        this.jugadorUnido = jugadorUnido;
    }

    public JugadorLobbyDTO getJugadorUnido() {
        return jugadorUnido;
    }
}
