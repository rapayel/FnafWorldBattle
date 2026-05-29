/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import java.util.List;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.interfaces.IModeloAccion;
/**
 * 
 * @author lagar
 */
public class ControlJuego {
    private IModeloAccion iModeloAccion;

    public ControlJuego(IModeloAccion iModeloAccion) {
        this.iModeloAccion = iModeloAccion;
    } 

    public void arrancarBatalla(List<JugadorDTO> jugadores) {
        this.iModeloAccion.inicializarPartida(jugadores);
    }
}
