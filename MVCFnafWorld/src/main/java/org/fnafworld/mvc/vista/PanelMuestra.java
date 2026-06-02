/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc.vista;

/**
 *
 * @author lagar
 */
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.HabilidadDTO;

public class PanelMuestra extends JPanel implements ActionListener {

    private AnimatronicoSprite spriteActual;
    private AnimatronicoDTO animatronicoActual;
    private HabilidadDTO[] habilidadesActuales;
    private Image iconoActual;
    private final Timer loopLocal;

    public PanelMuestra() {
        this.setPreferredSize(new Dimension(250, 300));
        this.setBackground(new Color(20, 24, 35));
        
        this.loopLocal = new Timer(16, this);
        this.loopLocal.start();
    }

    public void actualizarInformacion(AnimatronicoSprite nuevoSprite, HabilidadDTO[] habilidades) {
        this.spriteActual = nuevoSprite;
        this.animatronicoActual = null;
        this.iconoActual = null;
        this.habilidadesActuales = habilidades;

        if (this.spriteActual != null) {
            this.spriteActual.marcarComoVivo();
            this.spriteActual.setBounds(75, 50); 
        }
        repaint();
    }

    public void actualizarInformacion(AnimatronicoDTO animatronico, Image icono) {
        this.spriteActual = null;
        this.animatronicoActual = animatronico;
        this.iconoActual = icono;
        this.habilidadesActuales = animatronico != null ? animatronico.getHabilidades() : null;
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (spriteActual != null) {
            spriteActual.actualizar();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(new Color(0, 160, 255, 40));
        g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 15, 15);
        g2d.setColor(new Color(0, 160, 255, 120));
        g2d.drawRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 15, 15);

        if (spriteActual == null && animatronicoActual == null) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("Arial", Font.ITALIC, 13));
            g2d.drawString("Ningún Animatrónico", 55, 140);
            g2d.drawString("Seleccionado", 80, 160);
            return;
        }

        if (spriteActual != null) {
            spriteActual.dibujar(g2d);
        } else if (iconoActual != null) {
            g2d.drawImage(iconoActual, (getWidth() - 110) / 2, 48, 110, 110, this);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Impact", Font.PLAIN, 18));
        String nombre = spriteActual != null
                ? spriteActual.getNombre().toUpperCase()
                : animatronicoActual.getTipo().name().toUpperCase();
        int anchoTexto = g2d.getFontMetrics().stringWidth(nombre);
        g2d.drawString(nombre, (getWidth() - anchoTexto) / 2, 40);

        int casillaY = 155;
        int casillaAlto = 35;
        g2d.setFont(new Font("Arial", Font.BOLD, 11));

        for (int i = 0; i < 3; i++) {
            int yPos = casillaY + (i * (casillaAlto + 8));

            if (habilidadesActuales != null && i < habilidadesActuales.length && habilidadesActuales[i] != null) {
                HabilidadDTO hab = habilidadesActuales[i];

                g2d.setColor(new Color(35, 45, 70));
                g2d.fillRoundRect(20, yPos, getWidth() - 40, casillaAlto, 8, 8);
                g2d.setColor(new Color(0, 190, 255, 150));
                g2d.drawRoundRect(20, yPos, getWidth() - 40, casillaAlto, 8, 8);

                g2d.setColor(new Color(255, 215, 0));
                g2d.drawString(hab.getTipo().name(), 30, yPos + 22);
            } else {
                g2d.setColor(new Color(30, 32, 40));
                g2d.fillRoundRect(20, yPos, getWidth() - 40, casillaAlto, 8, 8);
                g2d.setColor(new Color(55, 55, 65));
                g2d.drawRoundRect(20, yPos, getWidth() - 40, casillaAlto, 8, 8);

                g2d.setColor(Color.DARK_GRAY);
                g2d.drawString("-", 120, yPos + 22);
            }
        }
    }
}
