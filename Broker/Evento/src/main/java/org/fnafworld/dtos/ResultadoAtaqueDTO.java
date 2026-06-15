/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import org.fnafworld.Equipo;
import org.fnafworld.ErrorLobby;
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
    private final ErrorLobby error;
 
    public ResultadoAtaqueDTO(ParticipanteDTO atacante, List<ParticipanteDTO> afectados, String idJugadorTurnoActual, Equipo equipoGanador) {
        this(atacante, afectados, null, null, idJugadorTurnoActual, equipoGanador, null);
    }

    public ResultadoAtaqueDTO(ParticipanteDTO atacante, List<ParticipanteDTO> afectados, List<JugadorDTO> jugadores, String idJugadorTurnoActual, Equipo equipoGanador) {
        this(atacante, afectados, jugadores, Collections.emptyList(), idJugadorTurnoActual, equipoGanador, null);
    }

    @JsonCreator
    public ResultadoAtaqueDTO(
        @JsonProperty("atacante") ParticipanteDTO atacante, 
        @JsonProperty("afectados") List<ParticipanteDTO> afectados, 
        @JsonProperty("jugadores") List<JugadorDTO> jugadores, 
        @JsonProperty("efectosAnimatronicos") List<EfectoAnimatronicoDTO> efectosAnimatronicos, 
        @JsonProperty("idJugadorTurnoActual") String idJugadorTurnoActual, 
        @JsonProperty("equipoGanador") Equipo equipoGanador, 
        @JsonProperty("error") ErrorLobby error
    ) {
        this.atacante = atacante;
        this.afectados = afectados;
        this.jugadores = jugadores;
        this.efectosAnimatronicos = efectosAnimatronicos != null ? efectosAnimatronicos : Collections.emptyList();
        this.idJugadorTurnoActual = idJugadorTurnoActual;
        this.equipoGanador = equipoGanador;
        this.error = error;
    }
    
    public ParticipanteDTO getAtacante() { return atacante; }
    public List<ParticipanteDTO> getAfectados() { return afectados; }
    public List<JugadorDTO> getJugadores() { return jugadores; }
    public List<EfectoAnimatronicoDTO> getEfectosAnimatronicos() { return efectosAnimatronicos; }
    public String getIdJugadorTurnoActual() { return idJugadorTurnoActual; }
    public Equipo getEquipoGanador() { return equipoGanador; }
    public ErrorLobby getError() { return error; }
}