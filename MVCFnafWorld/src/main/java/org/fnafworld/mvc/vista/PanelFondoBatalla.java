/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc.vista;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * 
 * @author lagar
 */
public class PanelFondoBatalla extends JPanel {
    private BufferedImage fondo;
    private String urlFondo;
    private List<AnimatronicoSprite> animatronicos;
    private Timer timerMaestro;
    
    public PanelFondoBatalla(String urlFondo, List<AnimatronicoSprite> animatronicos) {
        this.urlFondo = urlFondo;
        this.animatronicos = animatronicos;
        
        setDoubleBuffered(true);
        cargarFondo(this.urlFondo);
        configurarPosiciones();
        
        this.timerMaestro = new Timer(16, e -> cicloJuego());
        this.timerMaestro.start();
    }
    
    private void cicloJuego() {
        if (animatronicos != null) {
            for (AnimatronicoSprite sprite : animatronicos) {
                sprite.actualizar();
            }
        }
        repaint();
    }
    
    private void configurarPosiciones() {
        if (animatronicos == null || animatronicos.isEmpty()) return;
        int[][] posRojo = {
            {0, 20}, {60, 200}, {0, 300}, {30, 415},       
            {175, 80}, {250, 185}, {215, 345}, {200, 475}   
        };
        int[][] posAzul = {
            {750, 20}, {690, 200}, {750, 300}, {720, 415},  
            {575, 80}, {500, 185}, {535, 345}, {550, 475}  
        };

        int indiceRojo = 0;
        int indiceAzul = 0;

        for (int i = 0; i < animatronicos.size(); i++) {
            AnimatronicoSprite sprite = animatronicos.get(i);
            int numJugador = i / 4;
            if (numJugador == 0 || numJugador == 2) {
                if (indiceRojo < posRojo.length) {
                    sprite.setInvertido(true);
                    sprite.setBounds(posRojo[indiceRojo][0], posRojo[indiceRojo][1]);
                    indiceRojo++;
                }
            } 
            else {
                if (indiceAzul < posAzul.length) {
                    sprite.setInvertido(false); 
                    sprite.setBounds(posAzul[indiceAzul][0], posAzul[indiceAzul][1]);
                    indiceAzul++;
                }
            }
        }
    }
    
    private void cargarFondo(String urlFondo) {
        try {
            BufferedImage imgTemp = ImageIO.read(getClass().getResource(urlFondo));
            GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();
            this.fondo = config.createCompatibleImage(imgTemp.getWidth(), imgTemp.getHeight(), Transparency.OPAQUE);
            Graphics g = this.fondo.createGraphics();
            g.drawImage(imgTemp, 0, 0, null);
            g.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), null);
        }
        
        if (animatronicos != null) {
            for (AnimatronicoSprite sprite : animatronicos) {
                sprite.dibujar(g);
            }
        }
    }
}