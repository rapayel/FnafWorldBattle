package org.fnafworld.dtos;

public class GrupoJugadorLobbyDTO {
    private final String idJugador;
    private final AnimatronicoDTO[] grupo;
    private final boolean listo;

    public GrupoJugadorLobbyDTO(String idJugador, AnimatronicoDTO[] grupo, boolean listo) {
        this.idJugador = idJugador;
        this.grupo = grupo;
        this.listo = listo;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public AnimatronicoDTO[] getGrupo() {
        return grupo;
    }

    public boolean isListo() {
        return listo;
    }
}
