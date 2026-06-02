/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import java.util.List;
import org.fnafworld.Equipo;
import org.fnafworld.TipoAnimatronico;

public class ControlJuego {

    private final ModeloJuego modelo;

    public ControlJuego(ModeloJuego modelo) {
        this.modelo = modelo;
    }

    @SuppressWarnings("rawtypes")
    public void arrancarBatalla(List listaMockJugadores) {
        String escenarioPorDefecto = "/escenarios/valle.png";
        String musicaPorDefecto = "/musica/BossStoneCold.wav";
        modelo.iniciarPartidaEnDominio(listaMockJugadores, escenarioPorDefecto, musicaPorDefecto);
    }

    public void crearLobby(String nombre, String urlAvatar) {
        modelo.crearLobbyLocal(nombre, urlAvatar);
    }

    public void unirseLobby(String nombre, String urlAvatar) {
        modelo.unirseLobbyLocal(nombre, urlAvatar);
    }

    public void agregarJugadorRemoto(String id, String nombre, String urlAvatar, Equipo equipo) {
        modelo.agregarJugadorRemoto(id, nombre, urlAvatar, equipo);
    }

    public void seleccionarJugadorConfigurando(String idJugador) {
        modelo.seleccionarJugadorConfigurando(idJugador);
    }

    public void agregarAnimatronicoAGrupo(String idJugador, TipoAnimatronico tipo) {
        modelo.agregarAnimatronicoAGrupo(idJugador, tipo);
    }

    public void quitarAnimatronicoDeGrupo(String idJugador, int indice) {
        modelo.quitarAnimatronicoDeGrupo(idJugador, indice);
    }

    public void marcarJugadorListo(String idJugador) {
        modelo.marcarJugadorListo(idJugador);
    }

    public void iniciarPartidaDesdeLobby() {
        modelo.iniciarPartidaDesdeLobby("/escenarios/valle.png", "/musica/BossStoneCold.wav");
    }

    public void procesarSeleccionHabilidad(String idJugador, String idAnimatronico, Object tipoHabilidadEnum) {
        if (tipoHabilidadEnum instanceof org.fnafworld.TipoHabilidad) {
            modelo.ejecutarAtaqueEnDominio(idJugador, idAnimatronico, (org.fnafworld.TipoHabilidad) tipoHabilidadEnum);
        }
    }
}
