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
public class EventoCrearPartida extends EventoAccion {
    private String urlFondo;
    private String urlMusica;
    private JugadorDTO jugadorCreado;

    @JsonCreator
    public EventoCrearPartida(
        @JsonProperty("urlFondo") String urlFondo, 
        @JsonProperty("urlMusica") String urlMusica, 
        @JsonProperty("jugadorCreado") JugadorDTO jugadorCreado, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.urlFondo = urlFondo;
        this.urlMusica = urlMusica;
        this.jugadorCreado = jugadorCreado;
    }

    public String getUrlFondo() {
        return urlFondo;
    }

    public String getUrlMusica() {
        return urlMusica;
    }

    public JugadorDTO getJugadorCreado() {
        return jugadorCreado;
    }
}