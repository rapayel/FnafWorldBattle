package org.fnafworld.dtos;

import org.fnafworld.TipoAnimatronico;

public class SolicitudAnimatronicoFabricaDTO {
    private final String idAnimatronico;
    private final TipoAnimatronico tipo;
    private final boolean turnoAnimatronico;

    public SolicitudAnimatronicoFabricaDTO(TipoAnimatronico tipo) {
        this(tipo != null ? tipo.name() : null, tipo, false);
    }

    public SolicitudAnimatronicoFabricaDTO(String idAnimatronico, TipoAnimatronico tipo, boolean turnoAnimatronico) {
        this.idAnimatronico = idAnimatronico;
        this.tipo = tipo;
        this.turnoAnimatronico = turnoAnimatronico;
    }

    public String getIdAnimatronico() {
        return idAnimatronico;
    }

    public TipoAnimatronico getTipo() {
        return tipo;
    }

    public boolean isTurnoAnimatronico() {
        return turnoAnimatronico;
    }
}
