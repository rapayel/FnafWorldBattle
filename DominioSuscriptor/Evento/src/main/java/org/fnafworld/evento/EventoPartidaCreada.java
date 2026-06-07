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
public class EventoPartidaCreada extends EventoResultado{
    private JugadorLobbyDTO creador;

    public EventoPartidaCreada(JugadorLobbyDTO creador, String id) {
        super(id);
        this.creador = creador;
    }

    public JugadorLobbyDTO getCreador() {
        return creador;
    } 
}
