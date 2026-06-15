package org.fnafworld.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fnafworld.TipoAnimatronico;
/**
 * 
 * @author lagar
 */
public class EfectoAnimatronicoDTO {
    private final String idJugador;
    private final String idAnimatronico;
    private final TipoAnimatronico tipoAnimatronico;
    private final String nombre;
    private final int valor;
    private final int turnosRestantes;
    private final boolean positivo;

    @JsonCreator
    public EfectoAnimatronicoDTO(
        @JsonProperty("idJugador") String idJugador,
        @JsonProperty("idAnimatronico") String idAnimatronico,
        @JsonProperty("tipoAnimatronico") TipoAnimatronico tipoAnimatronico,
        @JsonProperty("nombre") String nombre,
        @JsonProperty("valor") int valor,
        @JsonProperty("turnosRestantes") int turnosRestantes,
        @JsonProperty("positivo") boolean positivo
    ) {
        this.idJugador = idJugador;
        this.idAnimatronico = idAnimatronico;
        this.tipoAnimatronico = tipoAnimatronico;
        this.nombre = nombre;
        this.valor = valor;
        this.turnosRestantes = turnosRestantes;
        this.positivo = positivo;
    }

    public String getIdJugador() { return idJugador; }
    public String getIdAnimatronico() { return idAnimatronico; }
    public TipoAnimatronico getTipoAnimatronico() { return tipoAnimatronico; }
    public String getNombre() { return nombre; }
    public int getValor() { return valor; }
    public int getTurnosRestantes() { return turnosRestantes; }
    public boolean isPositivo() { return positivo; }
}