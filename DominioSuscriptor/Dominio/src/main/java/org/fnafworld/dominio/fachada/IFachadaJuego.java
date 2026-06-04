/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.fnafworld.dominio.fachada;

import org.fnafworld.dtos.AtaqueDTO;
import org.fnafworld.dtos.EquiposDTO;
import org.fnafworld.dtos.ResultadoAtaqueDTO;

/**
 *
 * @author lagar
 */
public interface IFachadaJuego {
    public ResultadoAtaqueDTO iniciarPartida(EquiposDTO equipos);
    public ResultadoAtaqueDTO atacar(AtaqueDTO ataque);
}
