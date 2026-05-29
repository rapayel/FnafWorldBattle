/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import java.util.List;

public class ControlJuego {

    private final ModeloJuego modelo;

    public ControlJuego(ModeloJuego modelo) {
        this.modelo = modelo;
    }

    @SuppressWarnings("rawtypes")
    public void arrancarBatalla(List listaMockJugadores) {
        String escenarioPorDefecto = "/escenarios/valle.png";
        String musicaPorDefecto = "/musica/BossStoneCold.wav";
        modelo.iniciarPartidaEnDominio(listaMockJugadores, escenarioPorDefecto, musicaPorDefecto);
    }

    public void procesarSeleccionHabilidad(String idJugador, String idAnimatronico, Object tipoHabilidadEnum) {
        if (tipoHabilidadEnum instanceof org.fnafworld.TipoHabilidad) {
            modelo.ejecutarAtaqueEnDominio(idJugador, idAnimatronico, (org.fnafworld.TipoHabilidad) tipoHabilidadEnum);
        }
    }
}