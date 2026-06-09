/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.evento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.EXISTING_PROPERTY, 
    property = "id",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = EventoErrorJugadorLobby.class, name = "errorCrearPartida"),
    @JsonSubTypes.Type(value = EventoErrorJugadorLobby.class, name = "errorUnirsePartida"),
    @JsonSubTypes.Type(value = EventoErrorJugadorLobby.class, name = "errorSeleccionarEquipo"),
    @JsonSubTypes.Type(value = EventoErrorJugadorLobby.class, name = "errorEstablecerListo"),
    @JsonSubTypes.Type(value = EventoErrorJugadorLobby.class, name = "errorIniciarPartida"),
    @JsonSubTypes.Type(value = EventoAtaqueError.class, name = "ataqueError")
})
public class EventoError {
    private String id;
    
    @JsonCreator
    public EventoError(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}