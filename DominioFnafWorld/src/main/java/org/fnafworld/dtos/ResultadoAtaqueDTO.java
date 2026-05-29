/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import java.util.List;
/**
 * 
 * @author lagar
 */
public class ResultadoAtaqueDTO {
    private final ParticipanteDTO atacante;
    private final List<ParticipanteDTO> afectados;
    private final List<JugadorDTO> jugadores;
    private final String idJugadorTurnoActual;
    private final JugadorDTO ganador;
 
    public ResultadoAtaqueDTO(ParticipanteDTO atacante,
                               List<ParticipanteDTO> afectados,
                               String idJugadorTurnoActual,
                               JugadorDTO ganador) {
        this(atacante, afectados, null, idJugadorTurnoActual, ganador);
    }

    public ResultadoAtaqueDTO(ParticipanteDTO atacante,
                               List<ParticipanteDTO> afectados,
                               List<JugadorDTO> jugadores,
                               String idJugadorTurnoActual,
                               JugadorDTO ganador) {
        this.atacante = atacante;
        this.afectados = afectados;
        this.jugadores = jugadores;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
        this.ganador = ganador;
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
 
    public String getIdJugadorTurnoActual() {
        return idJugadorTurnoActual;
    }
 
    public JugadorDTO getGanador() {
        return ganador;
    }
}
