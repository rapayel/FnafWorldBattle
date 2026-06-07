/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import org.fnafworld.dtos.AnimatronicoDTO;

/**
 *
 * @author lagar
 */
public class EventoEstablecerListo extends EventoAccion{
    private String idJugador;
    private AnimatronicoDTO[] grupo;
    private boolean listo;

    public EventoEstablecerListo(String idJugador, AnimatronicoDTO[] grupo, boolean listo, String id) {
        super(id);
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
