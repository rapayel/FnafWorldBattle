/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.fnafworld.dtos.HabilidadDTO;
/**
 * 
 * @author lagar
 */
public class PanelAccionesBatalla extends JPanel {

    private final JPanel contenedorBotones;
    private final JTextArea txtDescripcion;
    private final JLabel lblTituloHabilidad;
    private final JButton[] botones;
    private final Runnable[] accionesHabilidad;
    private final MouseListener[] listenersHover;

    public PanelAccionesBatalla() {
        this.setPreferredSize(new Dimension(1200, 150));
        this.setBackground(new Color(30, 30, 35));
        this.setLayout(new BorderLayout(20, 0));

        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(55, 55, 65)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        contenedorBotones = new JPanel(new GridLayout(1, 3, 15, 0));
        contenedorBotones.setOpaque(false);
        contenedorBotones.setPreferredSize(new Dimension(600, 120));

        botones = new JButton[3];
        accionesHabilidad = new Runnable[3];
        listenersHover = new MouseListener[3];
        for (int i = 0; i < 3; i++) {
            botones[i] = crearBotonBonito();
            contenedorBotones.add(botones[i]);
        }
        this.add(contenedorBotones, BorderLayout.WEST);

        JPanel panelInfo = new JPanel(new BorderLayout(0, 5));
        panelInfo.setOpaque(false);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(70, 70, 80)),
                BorderFactory.createEmptyBorder(0, 20, 0, 0)
        ));

        lblTituloHabilidad = new JLabel("SELECCIONA UNA HABILIDAD");
        lblTituloHabilidad.setFont(new Font("Arial", Font.BOLD, 14));
        lblTituloHabilidad.setForeground(new Color(255, 215, 0));
        panelInfo.add(lblTituloHabilidad, BorderLayout.NORTH);

        txtDescripcion = new JTextArea("Pasa el cursor sobre una habilidad para ver sus detalles de combate.");
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDescripcion.setForeground(Color.LIGHT_GRAY);
        txtDescripcion.setBackground(new Color(30, 30, 35));
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        panelInfo.add(txtDescripcion, BorderLayout.CENTER);

        this.add(panelInfo, BorderLayout.CENTER);
        
        deshabilitarPanel();
    }

    private JButton crearBotonBonito() {
        JButton btn = new JButton("-");
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(50, 50, 60));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 95), 2));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }

    public void setAccionHabilidad(int indice, Runnable accion) {
        if (indice >= 0 && indice < accionesHabilidad.length) {
            accionesHabilidad[indice] = accion;
        }
    }

    public void actualizarHabilidades(HabilidadDTO[] habilidades) {
        if (habilidades == null || habilidades.length < 3) {
            deshabilitarPanel();
            return;
        }

        for (int i = 0; i < 3; i++) {
            HabilidadDTO hab = habilidades[i];
            JButton btn = botones[i];
            final int indice = i;

            limpiarInteraccionesBoton(indice);
            accionesHabilidad[indice] = null;

            if (hab == null) {
                btn.setText("-");
                btn.setEnabled(false);
                continue;
            }
            
            btn.setText(hab.getTipo().name());
            btn.setEnabled(true);
            btn.setBackground(new Color(40, 70, 120));
            btn.setBorder(BorderFactory.createLineBorder(new Color(70, 110, 180), 2));

            listenersHover[indice] = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (btn.isEnabled()) {
                        btn.setBackground(new Color(55, 95, 160));
                        lblTituloHabilidad.setText(hab.getTipo().name() + " (Poder: " + hab.getPoder() + ")");
                        txtDescripcion.setText(hab.getDescripcion() != null ? hab.getDescripcion() : "Sin descripción disponible.");
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (btn.isEnabled()) {
                        btn.setBackground(new Color(40, 70, 120));
                    }
                }
            };
            btn.addMouseListener(listenersHover[indice]);
            btn.addActionListener(e -> ejecutarAccionHabilidad(indice));
        }
    }

    private void ejecutarAccionHabilidad(int indice) {
        if (indice >= 0 && indice < accionesHabilidad.length && accionesHabilidad[indice] != null) {
            accionesHabilidad[indice].run();
        }
    }

    private void limpiarInteraccionesBoton(int indice) {
        JButton btn = botones[indice];
        if (listenersHover[indice] != null) {
            btn.removeMouseListener(listenersHover[indice]);
            listenersHover[indice] = null;
        }
        for (ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }
    }

    private void deshabilitarPanel() {
        for (int i = 0; i < botones.length; i++) {
            JButton btn = botones[i];
            limpiarInteraccionesBoton(i);
            accionesHabilidad[i] = null;
            btn.setText("-");
            btn.setEnabled(false);
            btn.setBackground(new Color(40, 40, 45));
            btn.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 65), 2));
        }
        if (lblTituloHabilidad != null && txtDescripcion != null) {
            lblTituloHabilidad.setText("ESPERANDO TURNO");
            txtDescripcion.setText("No hay ningún animatrónico aliado listo para ejecutar acciones en este momento.");
        }
    }
}
