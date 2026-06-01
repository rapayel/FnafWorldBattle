/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import org.fnafworld.sonido.AudioManager;
import org.fnafworld.mvc.vista.PanelTitulo;

public class FrameConfiguracion extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;

    public FrameConfiguracion() {
        setTitle("FNAF World - Modo Batalla");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        try {
            Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconos/iconojuego.png"));
            setIconImage(icono);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono del juego: " + e.getMessage());
        }

        cardLayout = new CardLayout();
        contenedorPrincipal = new JPanel(cardLayout);

        PanelTitulo panelTitulo = new PanelTitulo(this);
        contenedorPrincipal.add(panelTitulo, "PanelTitulo");

        add(contenedorPrincipal);

        reproducirMusica();
    }

    private void reproducirMusica() {
        try {
            AudioManager audioManager = new AudioManager();
            audioManager.loadMusic("/musica/TittleTheme.wav");
            audioManager.playMusicLoop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void cambiarPantalla(String nombreCard) {
        cardLayout.show(contenedorPrincipal, nombreCard);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            FrameConfiguracion frame = new FrameConfiguracion();
            frame.setVisible(true);
        });
    }
}