/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dominio.fachada;

import org.fnafworld.dominio.entidades.Lobby;
import org.fnafworld.dtos.*;
import org.fnafworld.Equipo;
/**
 * 
 * @author lagar
 */
public class FachadaJuego implements IFachadaJuego {
 
    private Lobby lobby;
 
    @Override
    public JugadorLobbyDTO crearPartida(String urlCampo, String urlMusica, JugadorDTO jugadorCreador) {
        this.lobby = new Lobby(urlCampo, urlMusica);
        return lobby.unirsePartida(jugadorCreador);
    }

    @Override
    public JugadorLobbyDTO unirsePartida(JugadorDTO jugadorSolicitud) {
        validarLobbyExistente();
        return lobby.unirsePartida(jugadorSolicitud);
    }

    @Override
    public JugadorLobbyDTO seleccionarEquipo(String idJugador, Equipo equipo) {
        validarLobbyExistente();
        return lobby.seleccionarEquipo(idJugador, equipo);
    }

    @Override
    public JugadorLobbyDTO establecerListo(String idJugador, AnimatronicoDTO[] grupo, boolean listo) {
        validarLobbyExistente();
        return lobby.establecerListo(idJugador, grupo, listo);
    }

    @Override
    public EstadoLobbyDTO obtenerEstadoLobby() {
        validarLobbyExistente();
        return lobby.obtenerEstadoLobby();
    }
 
    @Override
    public ResultadoAtaqueDTO iniciarPartida() {
        validarLobbyExistente();
        return lobby.iniciarPartida();
    }
 
    @Override
    public ResultadoAtaqueDTO atacar(AtaqueDTO ataque) {
        validarLobbyExistente();
        return lobby.atacar(ataque);
    }

    private void validarLobbyExistente() {
        if (this.lobby == null) {
            throw new IllegalStateException("No hay ninguna partida o lobby creado actualmente.");
        }
    }
}