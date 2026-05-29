/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.fnafworld.interfaces;

import java.util.List;
import org.fnafworld.dtos.JugadorDTO;
/**
 * 
 * @author lagar
 */
public interface IModeloAccion {
    public void inicializarPartida(List<JugadorDTO> jugadoresIniciales);
}
