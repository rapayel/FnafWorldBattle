/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.dtos.EstadoLobbyDTO;

/**
 *
 * @author lagar
 */
public class EventoEstadoLobby extends EventoResultado {
    private EstadoLobbyDTO estadoActualLobby;

    @JsonCreator
    public EventoEstadoLobby(
        @JsonProperty("estadoActualLobby") EstadoLobbyDTO estadoActualLobby, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.estadoActualLobby = estadoActualLobby;
    }

    public EstadoLobbyDTO getEstadoActualLobby() {
        return estadoActualLobby;
    }
}