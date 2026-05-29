/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import java.util.List;

/**
 * @author lagar
 */
public class EquiposDTO {
    private final List<JugadorDTO> jugadores;
    private final String urlCampo;
    private final String urlMusica;

    public EquiposDTO(List<JugadorDTO> jugadores, String urlCampo, String urlMusica) {
        this.jugadores = jugadores;
        this.urlCampo = urlCampo;
        this.urlMusica = urlMusica;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public String getUrlCampo() {
        return urlCampo;
    }

    public String getUrlMusica() {
        return urlMusica;
    }
}
