package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.TipoAnimatronico;
/**
 * 
 * @author lagar
 */
public class SolicitudAnimatronicoFabricaDTO {
    private final String idAnimatronico;
    private final TipoAnimatronico tipo;
    private final boolean turnoAnimatronico;
    
    public SolicitudAnimatronicoFabricaDTO(TipoAnimatronico tipo) {
        this(tipo != null ? tipo.name() : null, tipo, false);
    }
    
    @JsonCreator
    public SolicitudAnimatronicoFabricaDTO(
        @JsonProperty("idAnimatronico") String idAnimatronico, 
        @JsonProperty("tipo") TipoAnimatronico tipo, 
        @JsonProperty("turnoAnimatronico") boolean turnoAnimatronico
    ) {
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