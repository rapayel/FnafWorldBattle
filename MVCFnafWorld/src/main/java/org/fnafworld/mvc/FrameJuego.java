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
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.interfaces.Observador;
import org.fnafworld.mvc.vista.PanelEstadisticasEquipo;
import org.fnafworld.mvc.vista.PanelFondoBatalla;
import org.fnafworld.sonido.AudioManager;
/**
 * 
 * @author lagar
 */
public class FrameJuego extends JFrame implements Observador {

    private PanelFondoBatalla fondoBatalla;
    private PanelEstadisticasEquipo panelIzquierdo;
    private PanelEstadisticasEquipo panelDerecho;
    private ControlJuego control;

    public FrameJuego(AudioManager audio, PanelFondoBatalla fondoBatalla, ControlJuego control, ModeloJuego modelo) {
        this.fondoBatalla = fondoBatalla;
        this.control = control;
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconos/iconojuego.png"));
        modelo.registrarObservador(this);
        setIconImage(icono);
        this.setTitle("FNAF World - Modo Batalla");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        panelIzquierdo = new PanelEstadisticasEquipo("EQUIPO ROJO");
        panelDerecho = new PanelEstadisticasEquipo("EQUIPO AZUL");

        this.add(panelIzquierdo, BorderLayout.WEST);
        this.add(fondoBatalla, BorderLayout.CENTER);
        this.add(panelDerecho, BorderLayout.EAST);

        this.pack();
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false); 
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void Actualizar(ModeloJuego contexto) {
        List<JugadorDTO> listaJugadores = contexto.getJugadores();
        if (listaJugadores != null) {
            mapearEquiposHaciaPaneles(listaJugadores);
        }
    }

    private void mapearEquiposHaciaPaneles(List<JugadorDTO> todosLosJugadores) {
        List<JugadorDTO> equipoRojo = new java.util.ArrayList<>();
        List<JugadorDTO> equipoAzul = new java.util.ArrayList<>();

        for (int i = 0; i < todosLosJugadores.size(); i++) {
            JugadorDTO j = todosLosJugadores.get(i);
            if (i == 0 || i == 2) {
                equipoRojo.add(j);
            } else if (i == 1 || i == 3) {
                equipoAzul.add(j);
            }
        }

        panelIzquierdo.actualizarDatos(equipoRojo);
        panelDerecho.actualizarDatos(equipoAzul);
    }
}