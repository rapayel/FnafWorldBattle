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
public class EventoAtaqueError extends EventoError {
    private AtaqueDTO errorAtaque;

    @JsonCreator
    public EventoAtaqueError(
        @JsonProperty("errorAtaque") AtaqueDTO errorAtaque, 
        @JsonProperty("id") String id
    ) {
        super(id);
        this.errorAtaque = errorAtaque;
    }

    public AtaqueDTO getErrorAtaque() {
        return errorAtaque;
    }
}