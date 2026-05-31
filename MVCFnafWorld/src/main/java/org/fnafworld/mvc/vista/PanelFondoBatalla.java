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
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.dtos.ParticipanteDTO;
import org.fnafworld.dtos.ResultadoAtaqueDTO;
import org.fnafworld.mvc.ModeloJuego;


public class PanelFondoBatalla extends JPanel implements ModeloJuego.Observador {
    private BufferedImage fondo;
    private String urlFondo;
    private List<AnimatronicoSprite> animatronicos;
    private Timer timerMaestro;
    private ModeloJuego modelo;
    private String idUltimoAtacante = "";
    
    public PanelFondoBatalla(String urlFondo, List<AnimatronicoSprite> animatronicos, ModeloJuego modelo) {
        this.urlFondo = urlFondo;
        this.animatronicos = animatronicos;
        this.modelo = modelo;
        
        if (modelo != null) {
            modelo.registrarObservador(this);
        }
        
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
    public void mapearActualizacion() {
        sincronizarSpritesConModelo();
        repaint();
    }

    private void sincronizarSpritesConModelo() {
        if (modelo == null || modelo.getJugadores() == null) {
            return;
        }

        for (JugadorDTO jugador : modelo.getJugadores()) {
            if (jugador == null || jugador.getGrupo() == null) {
                continue;
            }
            for (AnimatronicoDTO anim : jugador.getGrupo()) {
                AnimatronicoSprite sprite = buscarSprite(anim);
                if (sprite == null) {
                    continue;
                }
                if (anim.isIsVivo()) {
                    sprite.marcarComoVivo();
                } else {
                    sprite.marcarComoDerrotado();
                }
            }
        }

        ResultadoAtaqueDTO estado = modelo.getEstadoActual();
        if (estado == null) {
            idUltimoAtacante = "";
            return;
        }

        ParticipanteDTO atacante = estado.getAtacante();
        if (atacante != null && atacante.getAnimatronico() != null) {
            String idActual = atacante.getAnimatronico().getIdAnimatronico();
            if (!idActual.equals(idUltimoAtacante)) {
                AnimatronicoSprite spriteAtacante = buscarSprite(atacante.getAnimatronico());
                
                if (spriteAtacante != null && atacante.getAnimatronico().isIsVivo()) {
                    spriteAtacante.estadoAtacar(); 
                    idUltimoAtacante = idActual;  
                }
            }
        } else {
            idUltimoAtacante = ""; 
        }
    }

    private AnimatronicoSprite buscarSprite(AnimatronicoDTO animatronico) {
        if (animatronico == null || animatronicos == null) {
            return null;
        }
        for (AnimatronicoSprite sprite : animatronicos) {
            if (sprite != null && animatronico.getIdAnimatronico().equals(sprite.getNombre())) {
                return sprite;
            }
        }
        return null;
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
