/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author lagar
 */
public class EventoIniciarPartida extends EventoAccion {
    
    @JsonCreator
    public EventoIniciarPartida(@JsonProperty("id") String id) {
        super(id);
    }
}