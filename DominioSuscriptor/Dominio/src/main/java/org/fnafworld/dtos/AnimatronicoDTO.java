/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import org.fnafworld.TipoAnimatronico;
/**
 * 
 * @author lagar
 */
public class AnimatronicoDTO {
    private final String idAnimatronico;
    private final TipoAnimatronico tipo;
    private final boolean turnoAnimatronico;
    private final int fuerza;
    private final int armadura;
    private final int vidaActual;
    private final int vidaTotal;
    private final HabilidadDTO[] habilidades;
    private final boolean isVivo;

    public AnimatronicoDTO(String idAnimatronico, TipoAnimatronico tipo, boolean turnoAnimatronico, int fuerza, int vidaActual, int vidaTotal, HabilidadDTO[] habilidades, boolean isVivo) {
        this(idAnimatronico, tipo, turnoAnimatronico, fuerza, 0, vidaActual, vidaTotal, habilidades, isVivo);
    }

    public AnimatronicoDTO(String idAnimatronico, TipoAnimatronico tipo, boolean turnoAnimatronico, int fuerza, int armadura, int vidaActual, int vidaTotal, HabilidadDTO[] habilidades, boolean isVivo) {
        this.idAnimatronico = idAnimatronico;
        this.tipo = tipo;
        this.turnoAnimatronico = turnoAnimatronico;
        this.fuerza = fuerza;
        this.armadura = armadura;
        this.vidaActual = vidaActual;
        this.vidaTotal = vidaTotal;
        this.habilidades = habilidades;
        this.isVivo = isVivo;
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

    public int getFuerza() {
        return fuerza;
    }

    public int getArmadura() {
        return armadura;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getVidaTotal() {
        return vidaTotal;
    }

    public HabilidadDTO[] getHabilidades() {
        return habilidades;
    }

    public boolean isIsVivo() {
        return isVivo;
    }
}