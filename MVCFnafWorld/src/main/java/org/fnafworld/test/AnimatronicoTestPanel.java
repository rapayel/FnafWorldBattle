/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.fnafworld.mvc.vista.AnimatronicoSprite;

public class AnimatronicoTestPanel extends JPanel {
    private AnimatronicoSprite spriteActual;
    private Timer timerLocal;
    private boolean enAtaque = false;
    private boolean mostrandoLapida = false;
    private Runnable onAtaqueTerminadoCallback;
    
    // Contador para controlar el tiempo que dura el estado de ataque antes de restablecer los botones
    private int ticksAtaque = 0;
    private static final int DURACION_ATAQUE_TICKS = 40; // Ajusta según qué tan rápido termine de golpear

    public AnimatronicoTestPanel() {
        this.setPreferredSize(new Dimension(400, 400));
        this.setBackground(new Color(25, 25, 25)); // Fondo oscuro neutro para pruebas

        // Un ciclo idéntico al 'timerMaestro' de tu PanelFondoBatalla (ejecución constante a ~60 FPS)
        timerLocal = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (spriteActual != null) {
                    // 1. Forzar al sprite real a procesar su lógica de frames y parpadeos
                    spriteActual.actualizar();
                    
                    // 2. Controlar la ventana de tiempo del ataque para el callback de los botones
                    if (enAtaque) {
                        ticksAtaque++;
                        if (ticksAtaque >= DURACION_ATAQUE_TICKS) {
                            enAtaque = false;
                            ticksAtaque = 0;
                            if (onAtaqueTerminadoCallback != null) {
                                SwingUtilities.invokeLater(onAtaqueTerminadoCallback);
                            }
                        }
                    }
                    
                    // 3. Redibujar el contenedor Swing
                    repaint();
                }
            }
        });
        timerLocal.start();
    }

    public void setAnimatronico(AnimatronicoSprite nuevoSprite) {
        this.spriteActual = nuevoSprite;
        this.enAtaque = false;
        this.ticksAtaque = 0;
        
        if (this.spriteActual != null) {
            // Reestablecer estados iniciales en el sprite
            this.spriteActual.marcarComoVivo();
            
            // Centrar el sprite dentro de los límites de este panel de pruebas
            // Modifica los valores (150, 150) si el sprite de tu juego se corta o queda muy abajo
            this.spriteActual.setBounds(150, 120); 
        }
        repaint();
    }

    public void setMostrandoLapida(boolean mostrar) {
        this.mostrandoLapida = mostrar;
        if (spriteActual != null) {
            if (mostrar) {
                spriteActual.marcarComoDerrotado(); // Cambia al sprite a su estado muerto/lápida
            } else {
                spriteActual.marcarComoVivo(); // Regresa al estado quieto (Idle)
            }
        }
        repaint();
    }

    public void ejecutarAnimacionAtaque(Runnable callbackTerminado) {
        if (!enAtaque && !mostrandoLapida && spriteActual != null) {
            this.enAtaque = true;
            this.ticksAtaque = 0;
            this.onAtaqueTerminadoCallback = callbackTerminado;
            
            // Invocar el disparador original de tu lógica visual
            spriteActual.estadoAtacar(); 
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (spriteActual == null) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("Cargando lista de animatrónicos...", 100, 200);
            return;
        }

        // --- RENDERIZADO ORIGINAL ---
        // Delegamos el flujo por completo al método que ya lee tus hojas de imágenes (.png)
        spriteActual.dibujar(g2d);

        // --- TEXTOS DE MONITOREO SUPERIOR ---
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 13));
        g2d.drawString("ID / Nombre: " + spriteActual.getNombre(), 20, 30);
        
        if (mostrandoLapida) {
            g2d.setColor(Color.RED);
            g2d.drawString("Estado: MODO LÁPIDA (Derrotado)", 20, 50);
        } else if (enAtaque) {
            g2d.setColor(Color.ORANGE);
            g2d.drawString("Estado: EJECUTANDO ATAQUE (" + ticksAtaque + "t)", 20, 50);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.drawString("Estado: BUCLE QUIETO (Idle)", 20, 50);
        }
    }
}