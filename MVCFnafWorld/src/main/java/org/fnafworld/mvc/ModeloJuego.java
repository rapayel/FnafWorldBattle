/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import java.util.List;
import java.util.ArrayList;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.dtos.EquiposDTO;
import org.fnafworld.dtos.AtaqueDTO;
import org.fnafworld.dtos.ResultadoAtaqueDTO;
import org.fnafworld.dominio.fachada.IFachadaJuego;
import org.fnafworld.TipoHabilidad;
/**
 * 
 * @author lagar
 */
public class ModeloJuego {

    private final IFachadaJuego fachada;
    private ResultadoAtaqueDTO estadoActual;
    private List<JugadorDTO> jugadores;
    private final List<Observador> observadores;

    public ModeloJuego(IFachadaJuego fachada) {
        this.fachada = fachada;
        this.observadores = new ArrayList<>();
        this.jugadores = new ArrayList<>();
    }

    public void iniciarPartidaEnDominio(List<JugadorDTO> listaMockJugadores, String urlCampo, String urlMusica) {
        this.jugadores = listaMockJugadores;
        EquiposDTO equipos = new EquiposDTO(listaMockJugadores, urlCampo, urlMusica);
        this.estadoActual = fachada.iniciarPartida(equipos);
        sincronizarJugadoresDesdeEstado();
        notificarObservadores();
    }

    public void ejecutarAtaqueEnDominio(String idJugador, String idAnimatronico, TipoHabilidad habilidad) {
        AtaqueDTO dtoAtaque = new AtaqueDTO(idJugador, idAnimatronico, habilidad);
        this.estadoActual = fachada.atacar(dtoAtaque);
        sincronizarJugadoresDesdeEstado();
        notificarObservadores();
    }

    public void registrarObservador(Observador o) {
        this.observadores.add(o);
    }

    private void notificarObservadores() {
        for (Observador o : observadores) {
            o.mapearActualizacion();
        }
    }

    private void sincronizarJugadoresDesdeEstado() {
        if (estadoActual != null && estadoActual.getJugadores() != null) {
            this.jugadores = estadoActual.getJugadores();
        }
    }

    public ResultadoAtaqueDTO getEstadoActual() { 
        return estadoActual; 
    }
    
    public List<JugadorDTO> getJugadores() { 
        return jugadores; 
    }
    
    public String getIdJugadorTurnoActual() {
        return estadoActual != null ? estadoActual.getIdJugadorTurnoActual() : "0";
    }

    public interface Observador {
        void mapearActualizacion();
    }
}
