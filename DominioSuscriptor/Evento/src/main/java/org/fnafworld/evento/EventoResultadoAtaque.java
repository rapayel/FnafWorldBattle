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
public class EventoResultadoAtaque extends EventoAccion{
    private ResultadoAtaqueDTO resultadoAtaque;

    public EventoResultadoAtaque(ResultadoAtaqueDTO resultadoAtaque, String id) {
        super(id);
        this.resultadoAtaque = resultadoAtaque;
    }

    
    public ResultadoAtaqueDTO getResultadoAtaque() {
        return resultadoAtaque;
    }
}
