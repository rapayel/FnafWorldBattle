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
    @JsonSubTypes.Type(value = EventoCrearPartida.class, name = "crearPartida"),
    @JsonSubTypes.Type(value = EventoUnirsePartida.class, name = "unirsePartida"),
    @JsonSubTypes.Type(value = EventoSeleccionarEquipo.class, name = "seleccionarEquipo"),
    @JsonSubTypes.Type(value = EventoEstablecerListo.class, name = "establecerListo"),
    @JsonSubTypes.Type(value = EventoObtenerEstadoLobby.class, name = "obtenerEstadoLobby"),
    @JsonSubTypes.Type(value = EventoIniciarPartida.class, name = "iniciarPartida"),
    @JsonSubTypes.Type(value = EventoAtacar.class, name = "atacar"),
    @JsonSubTypes.Type(value = EventoResultadoAtaque.class, name = "resultadoAtaque")
})
public class EventoAccion {
    private String id;

    @JsonCreator
    public EventoAccion(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}