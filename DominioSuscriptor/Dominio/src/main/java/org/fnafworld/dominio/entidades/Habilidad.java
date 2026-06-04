/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dominio.entidades;

import org.fnafworld.TipoHabilidad;

/**
 *
 * @author lagar
 */
public class Habilidad {
    private int poder;
    private TipoHabilidad tipo;
    private String descripcion;

    public Habilidad(int poder, TipoHabilidad tipo) {
        this(poder, tipo, null);
    }

    public Habilidad(int poder, TipoHabilidad tipo, String descripcion) {
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
