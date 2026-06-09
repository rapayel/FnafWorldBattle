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
    @JsonSubTypes.Type(value = EventoPartidaCreada.class, name = "partidaCreada"),
    @JsonSubTypes.Type(value = EventoJugadorUnido.class, name = "jugadorUnido"),
    @JsonSubTypes.Type(value = EventoEquipoSeleccionado.class, name = "equipoSeleccionado"),
    @JsonSubTypes.Type(value = EventoJugadorListo.class, name = "jugadorListo"),
    @JsonSubTypes.Type(value = EventoEstadoLobby.class, name = "estadoLobby"),
    @JsonSubTypes.Type(value = EventoPartidaIniciada.class, name = "partidaIniciada"),
    @JsonSubTypes.Type(value = EventoResultadoAtaque.class, name = "resultadoAtaque") 
})
public class EventoResultado {
    private String id;

    @JsonCreator
    public EventoResultado(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}