/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import java.util.Collections;
import org.fnafworld.Equipo;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class ResultadoAtaqueDTO {
    private final ParticipanteDTO atacante;
    private final List<ParticipanteDTO> afectados;
    private final List<JugadorDTO> jugadores;
    private final List<EfectoAnimatronicoDTO> efectosAnimatronicos;
    private final String idJugadorTurnoActual;
    private final Equipo equipoGanador;
 
    public ResultadoAtaqueDTO(ParticipanteDTO atacante,
                               List<ParticipanteDTO> afectados,
                               String idJugadorTurnoActual,
                               Equipo equipoGanador) {
        this(atacante, afectados, null, idJugadorTurnoActual, equipoGanador, null);
    }

    public ResultadoAtaqueDTO(ParticipanteDTO atacante,
                               List<ParticipanteDTO> afectados,
                               List<JugadorDTO> jugadores,
                               String idJugadorTurnoActual,
                               Equipo equipoGanador) {
        this(atacante, afectados, jugadores, idJugadorTurnoActual, equipoGanador, null);
    }

    public ResultadoAtaqueDTO(ParticipanteDTO atacante,
                               List<ParticipanteDTO> afectados,
                               List<JugadorDTO> jugadores,
                               String idJugadorTurnoActual,
                               Equipo equipoGanador,
                               List<EfectoAnimatronicoDTO> efectosAnimatronicos) {
        this.atacante = atacante;
        this.afectados = afectados;
        this.jugadores = jugadores;
        this.efectosAnimatronicos = efectosAnimatronicos != null ? efectosAnimatronicos : Collections.emptyList();
        this.idJugadorTurnoActual = idJugadorTurnoActual;
        this.equipoGanador = equipoGanador;
    }
 
    public ParticipanteDTO getAtacante() {
        return atacante;
    }
 
    public List<ParticipanteDTO> getAfectados() {
        return afectados;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public List<EfectoAnimatronicoDTO> getEfectosAnimatronicos() {
        return efectosAnimatronicos;
    }
 
    public String getIdJugadorTurnoActual() {
        return idJugadorTurnoActual;
    }
 
    public Equipo getEquipoGanador() {
        return equipoGanador;
    }
}