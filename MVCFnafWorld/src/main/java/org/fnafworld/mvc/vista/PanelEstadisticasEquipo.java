/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.JugadorDTO;

public class PanelEstadisticasEquipo extends JPanel {

    private String nombreEquipo;
    private List<JugadorDTO> jugadores;
    private final int ANCHO_PANEL = 280;

    public PanelEstadisticasEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
        this.jugadores = new ArrayList<>();
        this.setPreferredSize(new Dimension(ANCHO_PANEL, 750));
        this.setBackground(new Color(20, 20, 25));
    }

    public void actualizarDatos(List<JugadorDTO> nuevosJugadores) {
        this.jugadores = nuevosJugadores;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(40, 40, 50));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(nombreEquipo, 20, 35);
        g.drawLine(20, 45, ANCHO_PANEL - 20, 45);

        if (jugadores == null || jugadores.isEmpty()) {
            return;
        }

        int yOffset = 70;

        for (JugadorDTO jugador : jugadores) {
            if (jugador.isMiTurno()) {
                g.setColor(new Color(255, 215, 0, 40));
                g.fillRect(10, yOffset - 15, ANCHO_PANEL - 20, 260);
                g.setColor(new Color(255, 215, 0));
                g.drawRect(10, yOffset - 15, ANCHO_PANEL - 20, 260);
                g.setFont(new Font("Arial", Font.ITALIC, 11));
                g.drawString("► EN TURNO", ANCHO_PANEL - 100, yOffset + 5);
            }

            try {
                BufferedImage avatar = ImageIO.read(getClass().getResourceAsStream(jugador.getUrlAvatar()));
                g.drawImage(avatar, 20, yOffset, 40, 40, null);
            } catch (Exception e) {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(20, yOffset, 40, 40);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(jugador.getNombre(), 70, yOffset + 25);

            yOffset += 55;

            AnimatronicoDTO[] grupo = jugador.getGrupo();
            if (grupo != null) {
                for (AnimatronicoDTO anim : grupo) {
                    
                    String nombreAnimatronico = anim.getTipo().name();

                    if (anim.isTurnoAnimatronico()) {
                        g.setColor(new Color(0, 255, 0, 30));
                        g.fillRect(25, yOffset - 5, ANCHO_PANEL - 45, 45);
                        g.setColor(Color.GREEN);
                        g.drawRect(25, yOffset - 5, ANCHO_PANEL - 45, 45);
                    }

                    try {
                        String urlIcono = "/iconos/" + nombreAnimatronico.toLowerCase() + "_ico.png";
                        BufferedImage icono = ImageIO.read(getClass().getResourceAsStream(urlIcono));
                        g.drawImage(icono, 30, yOffset, 32, 32, null);
                    } catch (Exception e) {
                        g.setColor(Color.GRAY);
                        g.fillRect(30, yOffset, 32, 32);
                    }

                    g.setColor(anim.isIsVivo() ? Color.LIGHT_GRAY : Color.RED);
                    g.setFont(new Font("Arial", Font.PLAIN, 12));
                    g.drawString(nombreAnimatronico, 75, yOffset + 15);

                    String textoVida = anim.getVidaActual() + " / " + anim.getVidaTotal();
                    g.setFont(new Font("Arial", Font.PLAIN, 10));
                    g.setColor(Color.WHITE);
                    g.drawString(textoVida, ANCHO_PANEL - 85, yOffset + 15);

                    g.setColor(Color.BLACK);
                    g.fillRect(75, yOffset + 22, 180, 6);

                    if (anim.isIsVivo()) {
                        float porcentaje = (float) anim.getVidaActual() / anim.getVidaTotal();
                        g.setColor(porcentaje > 0.5f ? Color.GREEN : (porcentaje > 0.2f ? Color.ORANGE : Color.RED));
                        g.fillRect(75, yOffset + 22, (int) (180 * porcentaje), 6);
                    }

                    yOffset += 45;
                }
            }
            yOffset += 20;
        }
    }
}