/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import org.fnafworld.dtos.EstadoLobbyDTO;

/**
 *
 * @author lagar
 */
public class EventoEstadoLobby extends EventoResultado{
    private EstadoLobbyDTO estadoActualLobby;

    public EventoEstadoLobby(EstadoLobbyDTO estadoActualLobby, String id) {
        super(id);
        this.estadoActualLobby = estadoActualLobby;
    }

    public EstadoLobbyDTO getEstadoActualLobby() {
        return estadoActualLobby;
    }
}
