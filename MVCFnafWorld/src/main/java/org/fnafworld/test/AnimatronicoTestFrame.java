/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;
import org.fnafworld.mvc.vista.AnimatronicoSprite;
/**
 * 
 * @author lagar
 */
public class AnimatronicoTestFrame extends JFrame {

    private JComboBox<AnimatronicoSprite> selectorAnimatronicos; 
    private JButton btnAtaque;
    private JToggleButton btnLapida;
    private AnimatronicoTestPanel panelVisual;
    private List<AnimatronicoSprite> listaAnimatronicos;

    public AnimatronicoTestFrame(List<AnimatronicoSprite> listaAnimatronicos) {
        this.listaAnimatronicos = listaAnimatronicos;
        
        this.setTitle("FNAF World - Laboratorio de Pruebas de Animatrónicos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // --- PANEL SUPERIOR: SELECTOR ---
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("Seleccionar Animatrónico:"));
        
        selectorAnimatronicos = new JComboBox<>();
        for (AnimatronicoSprite anim : listaAnimatronicos) {
            selectorAnimatronicos.addItem(anim); 
        }

        // Renderizador personalizado para extraer el nombre real en lugar del hash @idMemoria
        selectorAnimatronicos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof AnimatronicoSprite) {
                    AnimatronicoSprite sprite = (AnimatronicoSprite) value;
                    setText(sprite.getNombre()); // Usa el método getNombre() de tu clase
                }
                return this;
            }
        });

        panelSuperior.add(selectorAnimatronicos);
        this.add(panelSuperior, BorderLayout.NORTH);

        // --- PANEL CENTRAL: VISUALIZADOR ---
        panelVisual = new AnimatronicoTestPanel();
        if (!listaAnimatronicos.isEmpty()) {
            panelVisual.setAnimatronico(listaAnimatronicos.get(0));
        }
        this.add(panelVisual, BorderLayout.CENTER);

        // --- PANEL INFERIOR: BOTONES ---
        JPanel panelInferior = new JPanel(new FlowLayout());
        btnAtaque = new JButton("Ejecutar Ataque");
        btnLapida = new JToggleButton("Activar Lápida");

        panelInferior.add(btnAtaque);
        panelInferior.add(btnLapida);
        this.add(panelInferior, BorderLayout.SOUTH);

        // --- EVENTOS ---
        // Dentro del constructor de AnimatronicoTestFrame.java:
        selectorAnimatronicos.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AnimatronicoSprite seleccionado = (AnimatronicoSprite) selectorAnimatronicos.getSelectedItem();
                if (seleccionado != null) {
                    panelVisual.setAnimatronico(seleccionado); // Actualiza y reubica las coordenadas del layout
                    btnLapida.setSelected(false);
                    btnAtaque.setEnabled(true);
                }
            }
        });

        btnAtaque.addActionListener(e -> {
            btnAtaque.setEnabled(false); 
            selectorAnimatronicos.setEnabled(false); 
            btnLapida.setEnabled(false);

            panelVisual.ejecutarAnimacionAtaque(() -> {
                btnAtaque.setEnabled(true);
                selectorAnimatronicos.setEnabled(true);
                btnLapida.setEnabled(true);
            });
        });

        btnLapida.addActionListener(e -> {
            boolean activo = btnLapida.isSelected();
            panelVisual.setMostrandoLapida(activo);
            btnAtaque.setEnabled(!activo);
        });

        this.setSize(450, 480);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}