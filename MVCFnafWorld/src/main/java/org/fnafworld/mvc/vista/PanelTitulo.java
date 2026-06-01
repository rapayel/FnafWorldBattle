/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc.vista;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import org.fnafworld.mvc.FrameConfiguracion;

public class PanelTitulo extends JPanel implements ActionListener {

    private final FrameConfiguracion parent;
    private final Timer loopAnimacion;

    private Image imgFondo;
    private Image imgAnimatronicos;
    private Image imgTitulo;

    private int pantallaAncho;
    private int pantallaAlto;

    private int xAnimatronicos, yAnimatronicos;
    private int xTitulo, yTitulo;
    
    private int destinoXAnimatronicos;
    private int destinoXTitulo;

    private int anchoAnimatronicos;
    private int altoAnimatronicos;
    private int anchoTitulo;
    private int altoTitulo;

    private JButton btnCrearPartida;
    private JButton btnUnirsePartida;
    private boolean animacionTerminada = false;

    public PanelTitulo(FrameConfiguracion parent) {
        this.parent = parent;
        this.setLayout(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.pantallaAncho = screenSize.width;
        this.pantallaAlto = screenSize.height;

        cargarRecursos();
        inicializarCoordenadas();
        inicializarBotones();

        this.loopAnimacion = new Timer(16, this);
        this.loopAnimacion.start();
    }

    private void cargarRecursos() {
        try {
            imgFondo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/extras/fondotitulo.png"))).getImage();
            imgAnimatronicos = new ImageIcon(Objects.requireNonNull(getClass().getResource("/extras/animatronicostitulo.png"))).getImage();
            imgTitulo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/extras/titulo.png"))).getImage();
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    private void inicializarCoordenadas() {
        anchoAnimatronicos = 1100; 
        altoAnimatronicos = 650;
        xAnimatronicos = -anchoAnimatronicos;
        yAnimatronicos = pantallaAlto - altoAnimatronicos - 60;
        destinoXAnimatronicos = (pantallaAncho / 2) - (anchoAnimatronicos / 2);

        anchoTitulo = 650;
        altoTitulo = 260; 
        xTitulo = pantallaAncho;
        yTitulo = 40;
        destinoXTitulo = (pantallaAncho / 2) - (anchoTitulo / 2);
    }

    private void inicializarBotones() {
        int btnAncho = 250;
        int btnAlto = 50;
        int centroX = (pantallaAncho / 2) - (btnAncho / 2);
        int baseY = (pantallaAlto / 2) + 120;

        btnCrearPartida = crearBotonEstilizado("Crear Partida", centroX, baseY, btnAncho, btnAlto);
        btnUnirsePartida = crearBotonEstilizado("Unirse Partida", centroX, baseY + 70, btnAncho, btnAlto);

        btnCrearPartida.setVisible(false);
        btnUnirsePartida.setVisible(false);

        add(btnCrearPartida);
        add(btnUnirsePartida);

        btnCrearPartida.addActionListener(e -> System.out.println("Creando partida..."));
        btnUnirsePartida.addActionListener(e -> System.out.println("Uniéndose a partida..."));
    }

    private JButton crearBotonEstilizado(String texto, int x, int y, int ancho, int alto) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(0, 200, 255, 200));
                } else {
                    g2d.setColor(new Color(0, 100, 200, 150));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2d.setColor(Color.CYAN);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        boton.setBounds(x, y, ancho, alto);
        boton.setFont(new Font("Impact", Font.PLAIN, 22));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        return boton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (imgFondo != null) {
            g2d.drawImage(imgFondo, 0, 0, pantallaAncho, pantallaAlto, this);
        }

        if (imgAnimatronicos != null) {
            g2d.drawImage(imgAnimatronicos, xAnimatronicos, yAnimatronicos, anchoAnimatronicos, altoAnimatronicos, this);
        }

        if (imgTitulo != null) {
            g2d.drawImage(imgTitulo, xTitulo, yTitulo, anchoTitulo, altoTitulo, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean movioAnimatronicos = false;
        boolean movioTitulo = false;

        int velocidadA = Math.max(1, (destinoXAnimatronicos - xAnimatronicos) / 25);
        if (xAnimatronicos < destinoXAnimatronicos) {
            xAnimatronicos += velocidadA;
            movioAnimatronicos = true;
        }

        int velocidadT = Math.max(1, (xTitulo - destinoXTitulo) / 25);
        if (xTitulo > destinoXTitulo) {
            xTitulo -= velocidadT;
            movioTitulo = true;
        }

        if (!movioAnimatronicos && !movioTitulo) {
            animacionTerminada = true;
            btnCrearPartida.setVisible(true);
            btnUnirsePartida.setVisible(true);
        }

        repaint();

        if (animacionTerminada) {
            loopAnimacion.stop();
        }
    }
}