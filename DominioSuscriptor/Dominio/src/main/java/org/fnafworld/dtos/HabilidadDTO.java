/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dtos;

import org.fnafworld.TipoHabilidad;

/**
 *
 * @author lagar
 */
public class HabilidadDTO {
    private final int poder;
    private final TipoHabilidad tipo;
    private final String descripcion;

    public HabilidadDTO(int poder, TipoHabilidad tipo) {
        this(poder, tipo, null);
    }

    public HabilidadDTO(int poder, TipoHabilidad tipo, String descripcion) {
        this.poder = poder;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int getPoder() {
        return poder;
    }

    public TipoHabilidad getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
