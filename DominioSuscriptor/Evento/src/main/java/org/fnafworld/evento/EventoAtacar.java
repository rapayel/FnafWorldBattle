/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.dtos.AtaqueDTO;

/**
 *
 * @author lagar
 */
public class EventoAtacar extends EventoAccion {
    private AtaqueDTO ataque;

    @JsonCreator
    public EventoAtacar(
        @JsonProperty("ataque") AtaqueDTO ataque, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.ataque = ataque;
    }

    public AtaqueDTO getAtaque() {
        return ataque;
    }
}