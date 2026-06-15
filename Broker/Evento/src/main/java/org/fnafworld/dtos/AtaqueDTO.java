/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.TipoHabilidad;
/**
 * 
 * @author lagar
 */
public class AtaqueDTO {
    private final String idJugador;
    private final String idAnimatronico;
    private final TipoHabilidad tipoHabilidad;

    @JsonCreator
    public AtaqueDTO(
        @JsonProperty("idJugador") String idJugador, 
        @JsonProperty("idAnimatronico") String idAnimatronico, 
        @JsonProperty("tipoHabilidad") TipoHabilidad tipoHabilidad
    ) {
        this.idJugador = idJugador;
        this.idAnimatronico = idAnimatronico;
        this.tipoHabilidad = tipoHabilidad;
    }

    public String getIdJugador() { return idJugador; }
    public String getIdAnimatronico() { return idAnimatronico; }
    public TipoHabilidad getTipoHabilidad() { return tipoHabilidad; }
}