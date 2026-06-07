/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import org.fnafworld.Equipo;

/**
 *
 * @author lagar
 */
public class EventoSeleccionarEquipo extends EventoAccion{
    private String idJugador;
    private Equipo equipo;

    public EventoSeleccionarEquipo(String idJugador, Equipo equipo, String id) {
        super(id);
        this.idJugador = idJugador;
        this.equipo = equipo;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public Equipo getEquipo() {
        return equipo;
    }
}
