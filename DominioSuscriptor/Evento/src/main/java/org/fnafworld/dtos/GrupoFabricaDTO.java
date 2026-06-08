package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class GrupoFabricaDTO {
    private final List<SolicitudAnimatronicoFabricaDTO> animatronicos;

    @JsonCreator
    public GrupoFabricaDTO(@JsonProperty("animatronicos") List<SolicitudAnimatronicoFabricaDTO> animatronicos) {
        this.animatronicos = animatronicos;
    }

    public List<SolicitudAnimatronicoFabricaDTO> getAnimatronicos() { return animatronicos; }
}