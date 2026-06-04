package org.fnafworld.dtos;

import org.fnafworld.TipoAnimatronico;

public class EfectoAnimatronicoDTO {
    private final String idJugador;
    private final String idAnimatronico;
    private final TipoAnimatronico tipoAnimatronico;
    private final String nombre;
    private final int valor;
    private final int turnosRestantes;
    private final boolean positivo;

    public EfectoAnimatronicoDTO(String idJugador,
                                 String idAnimatronico,
                                 TipoAnimatronico tipoAnimatronico,
                                 String nombre,
                                 int valor,
                                 int turnosRestantes,
                                 boolean positivo) {
        this.idJugador = idJugador;
        this.idAnimatronico = idAnimatronico;
        this.tipoAnimatronico = tipoAnimatronico;
        this.nombre = nombre;
        this.valor = valor;
        this.turnosRestantes = turnosRestantes;
        this.positivo = positivo;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public String getIdAnimatronico() {
        return idAnimatronico;
    }

    public TipoAnimatronico getTipoAnimatronico() {
        return tipoAnimatronico;
    }

    public String getNombre() {
        return nombre;
    }

    public int getValor() {
        return valor;
    }

    public int getTurnosRestantes() {
        return turnosRestantes;
    }

    public boolean isPositivo() {
        return positivo;
    }
}
