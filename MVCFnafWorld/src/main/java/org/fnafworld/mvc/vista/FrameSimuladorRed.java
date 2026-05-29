/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.mvc.ModeloJuego;
import org.fnafworld.mvc.ControlJuego;
/**
 * 
 * @author lagar
 */
public class FrameSimuladorRed extends JFrame implements ModeloJuego.Observador {

    private final ModeloJuego modelo;
    private final ControlJuego controlador;
    
    private JLabel lblTurnoActual;
    private JComboBox<String> comboJugadoresRemotos;
    private JComboBox<String> comboAnimatronicos;
    private JComboBox<org.fnafworld.TipoHabilidad> comboHabilidades;
    private JButton btnInyectarAccion;

    public FrameSimuladorRed(ModeloJuego modelo, ControlJuego controlador) {
        this.modelo = modelo;
        this.controlador = controlador;
        this.modelo.registrarObservador(this);
        
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Simulador de Red (Jugadores 1, 2 y 3)");
        setSize(400, 280);
        setLocation(100, 100);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelContenedor = new JPanel(new GridLayout(5, 2, 10, 10));
        panelContenedor.setBorder(new EmptyBorder(10, 10, 10, 10));

        lblTurnoActual = new JLabel("Turno actual ID: 0");
        comboJugadoresRemotos = new JComboBox<>();
        comboAnimatronicos = new JComboBox<>();
        comboHabilidades = new JComboBox<>();
        btnInyectarAccion = new JButton("Inyectar Ataque Remoto");

        comboJugadoresRemotos.addActionListener(e -> actualizarAnimatronicosRemotos());
        comboAnimatronicos.addActionListener(e -> actualizarHabilidadesRemotas());
        
        btnInyectarAccion.addActionListener(e -> {
            String jugadorId = (String) comboJugadoresRemotos.getSelectedItem();
            String animatronicoId = (String) comboAnimatronicos.getSelectedItem();
            org.fnafworld.TipoHabilidad hab = (org.fnafworld.TipoHabilidad) comboHabilidades.getSelectedItem();
            controlador.procesarSeleccionHabilidad(jugadorId, animatronicoId, hab);
        });

        panelContenedor.add(new JLabel("Control de Turno:"));
        panelContenedor.add(lblTurnoActual);
        panelContenedor.add(new JLabel("Simular Jugador ID:"));
        panelContenedor.add(comboJugadoresRemotos);
        panelContenedor.add(new JLabel("Animatrónico Atacante:"));
        panelContenedor.add(comboAnimatronicos);
        panelContenedor.add(new JLabel("Habilidad Usada:"));
        panelContenedor.add(comboHabilidades);
        panelContenedor.add(new JLabel("Acción:"));
        panelContenedor.add(btnInyectarAccion);

        add(panelContenedor, BorderLayout.CENTER);
    }

    @Override
    public void mapearActualizacion() {
        String turnoId = modelo.getIdJugadorTurnoActual();
        lblTurnoActual.setText("Jugador " + turnoId);
        actualizarJugadoresRemotos(turnoId);
        actualizarAnimatronicosRemotos();
        
        if ("0".equals(turnoId)) {
            btnInyectarAccion.setText("Turno del Jugador Local (0)");
            btnInyectarAccion.setEnabled(false);
        } else {
            btnInyectarAccion.setText("Inyectar Ataque Remoto");
            btnInyectarAccion.setEnabled(true);
        }
    }

    private void actualizarJugadoresRemotos(String turnoId) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        if (modelo.getJugadores() != null) {
            for (JugadorDTO jugador : modelo.getJugadores()) {
                if (jugador != null && !"0".equals(jugador.getId())) {
                    modeloCombo.addElement(jugador.getId());
                }
            }
        }

        comboJugadoresRemotos.setModel(modeloCombo);
        if (turnoId != null && !"0".equals(turnoId)) {
            comboJugadoresRemotos.setSelectedItem(turnoId);
        }
    }

    private void actualizarAnimatronicosRemotos() {
        String jugadorId = (String) comboJugadoresRemotos.getSelectedItem();
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        String animatronicoEnTurno = null;

        if (modelo.getJugadores() != null) {
            for (JugadorDTO jugador : modelo.getJugadores()) {
                if (jugador == null || !jugador.getId().equals(jugadorId) || jugador.getGrupo() == null) {
                    continue;
                }
                for (AnimatronicoDTO anim : jugador.getGrupo()) {
                    if (anim != null) {
                        modeloCombo.addElement(anim.getIdAnimatronico());
                        if (anim.isTurnoAnimatronico()) {
                            animatronicoEnTurno = anim.getIdAnimatronico();
                        }
                    }
                }
                break;
            }
        }

        comboAnimatronicos.setModel(modeloCombo);
        if (animatronicoEnTurno != null) {
            comboAnimatronicos.setSelectedItem(animatronicoEnTurno);
        }
        actualizarHabilidadesRemotas();
    }

    private void actualizarHabilidadesRemotas() {
        String jugadorId = (String) comboJugadoresRemotos.getSelectedItem();
        String animatronicoId = (String) comboAnimatronicos.getSelectedItem();
        DefaultComboBoxModel<org.fnafworld.TipoHabilidad> modeloCombo = new DefaultComboBoxModel<>();

        if (modelo.getJugadores() != null) {
            for (JugadorDTO jugador : modelo.getJugadores()) {
                if (jugador == null || !jugador.getId().equals(jugadorId) || jugador.getGrupo() == null) {
                    continue;
                }
                for (AnimatronicoDTO anim : jugador.getGrupo()) {
                    if (anim == null || !anim.getIdAnimatronico().equals(animatronicoId) || anim.getHabilidades() == null) {
                        continue;
                    }
                    for (org.fnafworld.dtos.HabilidadDTO habilidad : anim.getHabilidades()) {
                        if (habilidad != null) {
                            modeloCombo.addElement(habilidad.getTipo());
                        }
                    }
                    break;
                }
                break;
            }
        }

        comboHabilidades.setModel(modeloCombo);
    }
}
