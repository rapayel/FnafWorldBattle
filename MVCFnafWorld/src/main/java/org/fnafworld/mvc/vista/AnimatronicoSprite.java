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
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class AnimatronicoSprite {
    // NUEVO: Variable para almacenar el nombre del animatrónico
    private String nombre;
    
    private String urlAnimatronico;
    private List<BufferedImage> animacionQuieto;
    private List<BufferedImage> animacionAtaque;
    private BufferedImage derrotado;
    
    private int posX, posY;
    
    private int x1, y1, ancho1, alto1, frames1, gap1;
    private int x2, y2, ancho2, alto2, frames2, gap2;
    
    private List<BufferedImage> animacionActual;
    private int frameActual = 0;
    private boolean esDerrotado = false;
    private boolean invertido = false;

    private long ultimoTiempo;
    private int delayActual;
    private int delayQuieto;
    private int delayAtaque;

    public AnimatronicoSprite(String animatronico, int x1, int y1, int ancho1, int alto1, int frames1, int gap1,
            int x2, int y2, int ancho2, int alto2, int frames2, int gap2, int delayQuieto, int delayAtaque) {
        
        // NUEVO: Asignamos el parámetro recibido al atributo de la clase
        this.nombre = animatronico;
        
        this.urlAnimatronico = "/animatronicos/" + animatronico + ".png";
        this.x1 = x1; this.y1 = y1; this.ancho1 = ancho1; this.alto1 = alto1; this.frames1 = frames1; this.gap1 = gap1;
        this.x2 = x2; this.y2 = y2; this.ancho2 = ancho2; this.alto2 = alto2; this.frames2 = frames2; this.gap2 = gap2;
        this.delayQuieto = delayQuieto;
        this.delayAtaque = delayAtaque;
        this.delayActual = delayQuieto;

        this.animacionQuieto = new ArrayList<>();
        this.animacionAtaque = new ArrayList<>();
        
        extraerSprites();
        this.animacionActual = animacionQuieto;
        this.ultimoTiempo = System.currentTimeMillis();
    }

    // NUEVO: Getter para que PanelFondoBatalla pueda consultar el nombre
    public String getNombre() {
        return nombre;
    }

    private BufferedImage optimizarImagen(BufferedImage clonar) {
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage optimizada = config.createCompatibleImage(clonar.getWidth(), clonar.getHeight(), Transparency.TRANSLUCENT);
        Graphics g = optimizada.createGraphics();
        g.drawImage(clonar, 0, 0, null);
        g.dispose();
        return optimizada;
    }

    private void extraerSprites() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream(urlAnimatronico));
            
            for (int i = 0; i < frames1; i++) {
                int pX = x1 + (i * (ancho1 + gap1));
                animacionQuieto.add(optimizarImagen(spriteSheet.getSubimage(pX, y1, ancho1, alto1)));
            }
            
            for (int i = 0; i < frames2; i++) {
                int pX = x2 + (i * (ancho2 + gap2));
                animacionAtaque.add(optimizarImagen(spriteSheet.getSubimage(pX, y2, ancho2, alto2)));
            }
            
            BufferedImage lapidaTemp = ImageIO.read(getClass().getResourceAsStream("/extras/lapida.png"));
            this.derrotado = optimizarImagen(lapidaTemp);
            
        } catch (IOException | NullPointerException e) {
            System.err.println("Error al cargar recursos: " + e.getMessage());
        }
    }

    public void actualizar() {
        if (esDerrotado || animacionActual == null || animacionActual.isEmpty()) return;

        long tiempoAhora = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoAhora - ultimoTiempo;

        if (tiempoTranscurrido >= delayActual) {
            if (frameActual == animacionActual.size() - 1) {
                if (animacionActual == animacionAtaque) {
                    estadoQuieto();
                } else {
                    frameActual = 0;
                }
            } else {
                frameActual++;
            }
            ultimoTiempo = tiempoAhora - (tiempoTranscurrido % delayActual);
        }
    }

    public void dibujar(Graphics g) {
        if (esDerrotado) {
            if (derrotado != null) {
                if (invertido) {
                    g.drawImage(derrotado, posX + derrotado.getWidth(), posY, -derrotado.getWidth(), derrotado.getHeight(), null);
                } else {
                    g.drawImage(derrotado, posX, posY, null);
                }
            }
        } else if (animacionActual != null && !animacionActual.isEmpty()) {
            BufferedImage imgActual = animacionActual.get(frameActual);
            int ancho = imgActual.getWidth();
            int alto = imgActual.getHeight();
            if (invertido) {
                g.drawImage(imgActual, posX + ancho, posY, -ancho, alto, null);
            } else {
                g.drawImage(imgActual, posX, posY, null);
            }
        }
    }

    public void estadoQuieto() {
        if (!esDerrotado && animacionActual != animacionQuieto) {
            this.animacionActual = animacionQuieto;
            this.frameActual = 0;
            this.delayActual = delayQuieto;
        }
    }

    public void estadoAtacar() {
        if (!esDerrotado && animacionActual != animacionAtaque) {
            this.animacionActual = animacionAtaque;
            this.frameActual = 0;
            this.delayActual = delayAtaque;
        }
    }

    public void marcarComoDerrotado() {
        this.esDerrotado = true;
    }

    public void setInvertido(boolean invertido) { this.invertido = invertido; }
    public void setBounds(int x, int y) { this.posX = x; this.posY = y; }
    public int getAncho1() { return ancho1; }
    public int getAlto1() { return alto1; }
}