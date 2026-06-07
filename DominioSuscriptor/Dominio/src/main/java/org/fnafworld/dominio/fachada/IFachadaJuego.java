/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.fnafworld.dominio.fachada;

import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.AtaqueDTO;
import org.fnafworld.dtos.EstadoLobbyDTO;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.dtos.JugadorLobbyDTO;
import org.fnafworld.dtos.ResultadoAtaqueDTO;

/**
 *
 * @author lagar
 */
public interface IFachadaJuego {
    public JugadorLobbyDTO crearPartida(String urlCampo, String urlMusica, JugadorDTO jugadorCreador);
    public JugadorLobbyDTO unirsePartida(JugadorDTO jugadorSolicitud);
    public JugadorLobbyDTO seleccionarEquipo(String idJugador, org.fnafworld.Equipo equipo);
    public JugadorLobbyDTO establecerListo(String idJugador, AnimatronicoDTO[] grupo, boolean listo);
    public EstadoLobbyDTO obtenerEstadoLobby();
    public ResultadoAtaqueDTO iniciarPartida();
    public ResultadoAtaqueDTO atacar(AtaqueDTO ataque);
}