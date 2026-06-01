package org.fnafworld.dtos;

import java.util.List;

public class GrupoFabricaDTO {
    private final List<SolicitudAnimatronicoFabricaDTO> animatronicos;

    public GrupoFabricaDTO(List<SolicitudAnimatronicoFabricaDTO> animatronicos) {
        this.animatronicos = animatronicos;
    }

    public List<SolicitudAnimatronicoFabricaDTO> getAnimatronicos() {
        return animatronicos;
    }
}
