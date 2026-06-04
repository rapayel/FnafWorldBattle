package org.fnafworld.dtos;

import java.util.List;

public class EstadoLobbyDTO {
    private final List<JugadorDTO> jugadores;
    private final boolean inicializada;
    private final boolean puedeIniciar;
    private final int minimoJugadores;
    private final int maximoJugadores;

    public EstadoLobbyDTO(List<JugadorDTO> jugadores, boolean inicializada, boolean puedeIniciar, int minimoJugadores, int maximoJugadores) {
        this.jugadores = jugadores;
        this.inicializada = inicializada;
        this.puedeIniciar = puedeIniciar;
        this.minimoJugadores = minimoJugadores;
        this.maximoJugadores = maximoJugadores;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public boolean isInicializada() {
        return inicializada;
    }

    public boolean isPuedeIniciar() {
        return puedeIniciar;
    }

    public int getMinimoJugadores() {
        return minimoJugadores;
    }

    public int getMaximoJugadores() {
        return maximoJugadores;
    }
}
