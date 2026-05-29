/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JFrame;
import org.fnafworld.Equipo;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.HabilidadDTO;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.mvc.vista.PanelAccionesBatalla;
import org.fnafworld.mvc.vista.PanelEstadisticasEquipo;
import org.fnafworld.mvc.vista.PanelFondoBatalla;
import org.fnafworld.sonido.AudioManager;

public class FrameJuego extends JFrame implements ModeloJuego.Observador {

    private PanelFondoBatalla fondoBatalla;
    private PanelEstadisticasEquipo panelIzquierdo;
    private PanelEstadisticasEquipo panelDerecho;
    private PanelAccionesBatalla panelAcciones; 
    private ControlJuego control;
    private ModeloJuego modelo;

    public FrameJuego(AudioManager audio, PanelFondoBatalla fondoBatalla, ControlJuego control, ModeloJuego modelo) {
        this.fondoBatalla = fondoBatalla;
        this.control = control;
        this.modelo = modelo;
        
        this.modelo.registrarObservador(this);
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconos/iconojuego.png"));
        setIconImage(icono);
        this.setTitle("FNAF World - Modo Batalla");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        panelIzquierdo = new PanelEstadisticasEquipo("EQUIPO ROJO");
        panelDerecho = new PanelEstadisticasEquipo("EQUIPO AZUL");
        panelAcciones = new PanelAccionesBatalla();

        this.add(panelIzquierdo, BorderLayout.WEST);
        this.add(fondoBatalla, BorderLayout.CENTER);
        this.add(panelDerecho, BorderLayout.EAST);
        this.add(panelAcciones, BorderLayout.SOUTH); 

        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false); 
        this.setLocationRelativeTo(null);
    }

    @Override
    public void mapearActualizacion() {
        List<JugadorDTO> listaJugadores = modelo.getJugadores();
        if (listaJugadores != null) {
            mapearEquiposHaciaPaneles(listaJugadores);
            buscarYEnviarHabilidadesTurno(listaJugadores); 
        }
    }

    private void mapearEquiposHaciaPaneles(List<JugadorDTO> todosLosJugadores) {
        List<JugadorDTO> equipoRojo = new java.util.ArrayList<>();
        List<JugadorDTO> equipoAzul = new java.util.ArrayList<>();

        for (int i = 0; i < todosLosJugadores.size(); i++) {
            JugadorDTO j = todosLosJugadores.get(i);
            if (j.getEquipo() == Equipo.Rojo) {
                equipoRojo.add(j);
            } else if (j.getEquipo() == Equipo.Azul) {
                equipoAzul.add(j);
            }
        }

        panelIzquierdo.actualizarDatos(equipoRojo);
        panelDerecho.actualizarDatos(equipoAzul);
    }

    private void buscarYEnviarHabilidadesTurno(List<JugadorDTO> todosLosJugadores) {
        for (JugadorDTO jugador : todosLosJugadores) {
            if (jugador.getGrupo() != null) {
                for (AnimatronicoDTO anim : jugador.getGrupo()) {
                    if ("0".equals(jugador.getId()) && anim.isTurnoAnimatronico()) {
                        panelAcciones.actualizarHabilidades(anim.getHabilidades());
                        conectarHabilidadesTurno(jugador, anim);
                        return; 
                    }
                }
            }
        }
        panelAcciones.actualizarHabilidades(null);
    }

    private void conectarHabilidadesTurno(JugadorDTO jugador, AnimatronicoDTO anim) {
        HabilidadDTO[] habilidades = anim.getHabilidades();
        if (habilidades == null) {
            return;
        }

        for (int i = 0; i < habilidades.length; i++) {
            final int indice = i;
            final HabilidadDTO habilidad = habilidades[i];
            if (habilidad != null) {
                panelAcciones.setAccionHabilidad(indice, () ->
                        control.procesarSeleccionHabilidad(
                                jugador.getId(),
                                anim.getIdAnimatronico(),
                                habilidad.getTipo()
                        )
                );
            }
        }
    }
}
