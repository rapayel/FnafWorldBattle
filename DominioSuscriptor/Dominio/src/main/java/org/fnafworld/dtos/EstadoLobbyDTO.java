package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.fnafworld.ErrorLobby;
/**
 * 
 * @author lagar
 */
public class EstadoLobbyDTO {
    private final List<JugadorDTO> jugadores;
    private final boolean inicializada;
    private final boolean puedeIniciar;
    private final int minimoJugadores;
    private final int maximoJugadores;
    private final ErrorLobby error;
    
    public EstadoLobbyDTO(List<JugadorDTO> jugadores, boolean inicializada, boolean puedeIniciar, int minimoJugadores, int maximoJugadores) {
        this(jugadores, inicializada, puedeIniciar, minimoJugadores, maximoJugadores, null);
    }

    @JsonCreator
    public EstadoLobbyDTO(
        @JsonProperty("jugadores") List<JugadorDTO> jugadores, 
        @JsonProperty("inicializada") boolean inicializada, 
        @JsonProperty("puedeIniciar") boolean puedeIniciar, 
        @JsonProperty("minimoJugadores") int minimoJugadores, 
        @JsonProperty("maximoJugadores") int maximoJugadores, 
        @JsonProperty("error") ErrorLobby error
    ) {
        this.jugadores = jugadores;
        this.inicializada = inicializada;
        this.puedeIniciar = puedeIniciar;
        this.minimoJugadores = minimoJugadores;
        this.maximoJugadores = maximoJugadores;
        this.error = error;
    }
    
    public List<JugadorDTO> getJugadores() { return jugadores; }
    public boolean isInicializada() { return inicializada; }
    public boolean isPuedeIniciar() { return puedeIniciar; }
    public int getMinimoJugadores() { return minimoJugadores; }
    public int getMaximoJugadores() { return maximoJugadores; }
    public ErrorLobby getError() { return error; }
}