/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import java.util.List;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.interfaces.IModeloAccion;
import org.fnafworld.interfaces.Observador;
/**
 * 
 * @author lagar
 */
public class ModeloJuego implements IModeloAccion {
    private Observador observador;
    private List<JugadorDTO> jugadores;

    public ModeloJuego() {
    } 

    public void registrarObservador(Observador observador) {
        this.observador = observador;
    }

    @Override
    public void inicializarPartida(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
        notificar();
    }

    private void notificar() {
        if (this.observador != null) {
            this.observador.Actualizar(this);
        }
    }
    
    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }
}