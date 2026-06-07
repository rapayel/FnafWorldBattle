/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import org.fnafworld.dtos.ResultadoAtaqueDTO;

/**
 *
 * @author lagar
 */
public class EventoPartidaIniciada extends EventoResultado{
    private ResultadoAtaqueDTO estadoInicial;

    public EventoPartidaIniciada(ResultadoAtaqueDTO estadoInicial, String id) {
        super(id);
        this.estadoInicial = estadoInicial;
    }

    public ResultadoAtaqueDTO getEstadoInicial() {
        return estadoInicial;
    }
}
