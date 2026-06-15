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
public class EventoPartidaCreada extends EventoResultado {
    private JugadorLobbyDTO creador;

    @JsonCreator
    public EventoPartidaCreada(
        @JsonProperty("creador") JugadorLobbyDTO creador, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.creador = creador;
    }

    public JugadorLobbyDTO getCreador() {
        return creador;
    } 
}