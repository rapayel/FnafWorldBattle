package org.fnafworld.dtos;

import java.util.List;

public class LobbyDTO {
    private final List<JugadorLobbyDTO> jugadores;
    private final String urlCampo;
    private final String urlMusica;

    public LobbyDTO(List<JugadorLobbyDTO> jugadores, String urlCampo, String urlMusica) {
        this.jugadores = jugadores;
        this.urlCampo = urlCampo;
        this.urlMusica = urlMusica;
    }

    public List<JugadorLobbyDTO> getJugadores() {
        return jugadores;
    }

    public String getUrlCampo() {
        return urlCampo;
    }

    public String getUrlMusica() {
        return urlMusica;
    }
}
