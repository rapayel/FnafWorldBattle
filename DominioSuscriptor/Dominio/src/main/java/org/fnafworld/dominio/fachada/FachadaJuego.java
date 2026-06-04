/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.dominio.fachada;

import org.fnafworld.dtos.ResultadoAtaqueDTO;
import org.fnafworld.dominio.entidades.BatallaCampo;
import org.fnafworld.dominio.entidades.Lobby;
import org.fnafworld.dtos.AtaqueDTO;
import org.fnafworld.dtos.EquiposDTO;

/**
 *
 * @author lagar
 */
public class FachadaJuego implements IFachadaJuego{
    private BatallaCampo batallaCampo;

    public FachadaJuego(BatallaCampo batallaCampo) {
        this.batallaCampo = batallaCampo;
    }
    
    @Override
    public ResultadoAtaqueDTO iniciarPartida(EquiposDTO equipos) {
        Lobby lobby = new Lobby(equipos);
        this.batallaCampo = lobby.iniciarPartida();
        return batallaCampo.construirResultado();
    }
    
    @Override
    public ResultadoAtaqueDTO atacar(AtaqueDTO ataque) {
        return batallaCampo.atacar(ataque);
    }
}
