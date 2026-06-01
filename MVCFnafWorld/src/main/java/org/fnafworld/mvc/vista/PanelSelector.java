/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc.vista;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.fnafworld.mvc.FrameConfiguracion;
/**
 * 
 * @author lagar
 */
public class PanelSelector extends JPanel implements ActionListener {

    private final FrameConfiguracion parent;
    private final Timer loopAnimacion;

    private Image imgFondo;
    private final List<Image> iconosAnimatronicos = new ArrayList<>();
    
    private int pantallaAncho;
    private int pantallaAlto;
    
    private int columnas;
    private int filas;
    private final int tamanoIcono = 90; 
    private final int espacioSeparacion = 20;

    private int indiceSeleccionado = -1;
    private int indiceHover = -1;

    public PanelSelector(FrameConfiguracion parent) {
        this.parent = parent;
        this.setLayout(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.pantallaAncho = screenSize.width;
        this.pantallaAlto = screenSize.height;

        cargarRecursos();
        calcularDistribucionCuadrada();
        configurarEventosMouse();

        this.loopAnimacion = new Timer(16, this);
        this.loopAnimacion.start();
    }

    private void cargarRecursos() {
        try {
            imgFondo = new ImageIcon(getClass().getResource("/extras/fondotitulo.png")).getImage();
            
            URL urlCarpeta = getClass().getResource("/iconos");
            if (urlCarpeta != null) {
                File carpeta = new File(urlCarpeta.toURI());
                File[] archivos = carpeta.listFiles();
                if (archivos != null) {
                    for (File archivo : archivos) {
                        if (archivo.getName().toLowerCase().endsWith(".png")) {
                            iconosAnimatronicos.add(new ImageIcon(archivo.getAbsolutePath()).getImage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando iconos: " + e.getMessage());
        }

        if (iconosAnimatronicos.isEmpty()) {
            for (int i = 0; i < 20; i++) {
                iconosAnimatronicos.add(new ImageIcon(getClass().getResource("/iconos/iconojuego.png")).getImage());
            }
        }
    }

    private void calcularDistribucionCuadrada() {
        int totalIconos = iconosAnimatronicos.size();
        if (totalIconos == 0) return;

        columnas = (int) Math.ceil(Math.sqrt(totalIconos));
        filas = (int) Math.ceil((double) totalIconos / columnas);
    }

    private void configurarEventosMouse() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                indiceHover = obtenerIndicePorCoordenadas(e.getX(), e.getY());
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                indiceHover = -1;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int clickIndice = obtenerIndicePorCoordenadas(e.getX(), e.getY());
                if (clickIndice != -1) {
                    indiceSeleccionado = clickIndice;
                    System.out.println("Seleccionado animatrónico índice: " + indiceSeleccionado);
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private int obtenerIndicePorCoordenadas(int mx, int my) {
        int totalIconos = iconosAnimatronicos.size();
        int anchoGridTotal = (columnas * tamanoIcono) + ((columnas - 1) * espacioSeparacion);
        int xInicioGrid = (pantallaAncho - anchoGridTotal) / 2;
        int yInicioGrid = (pantallaAlto - ((filas * tamanoIcono) + ((filas - 1) * espacioSeparacion))) / 2;

        int contador = 0;
        for (int f = 0; f < filas; f++) {
            int iconosEnEstaFila = (f == filas - 1) ? (totalIconos - contador) : columnas;
            int anchoFilaActual = (iconosEnEstaFila * tamanoIcono) + ((iconosEnEstaFila - 1) * espacioSeparacion);
            int xInicioFila = (pantallaAncho - anchoFilaActual) / 2;

            int y = yInicioGrid + f * (tamanoIcono + espacioSeparacion);

            for (int c = 0; c < iconosEnEstaFila; c++) {
                int x = xInicioFila + c * (tamanoIcono + espacioSeparacion);

                if (mx >= x && mx <= x + tamanoIcono && my >= y && my <= y + tamanoIcono) {
                    return contador;
                }
                contador++;
            }
        }
        return -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (imgFondo != null) {
            g2d.drawImage(imgFondo, 0, 0, pantallaAncho, pantallaAlto, this);
        }

        int totalIconos = iconosAnimatronicos.size();
        if (totalIconos == 0) return;

        int anchoGridTotal = (columnas * tamanoIcono) + ((columnas - 1) * espacioSeparacion);
        int yInicioGrid = (pantallaAlto - ((filas * tamanoIcono) + ((filas - 1) * espacioSeparacion))) / 2;

        int contador = 0;
        for (int f = 0; f < filas; f++) {
            int iconosEnEstaFila = (f == filas - 1) ? (totalIconos - contador) : columnas;
            int anchoFilaActual = (iconosEnEstaFila * tamanoIcono) + ((iconosEnEstaFila - 1) * espacioSeparacion);
            int xInicioFila = (pantallaAncho - anchoFilaActual) / 2;

            int y = yInicioGrid + f * (tamanoIcono + espacioSeparacion);

            for (int c = 0; c < iconosEnEstaFila; c++) {
                int x = xInicioFila + c * (tamanoIcono + espacioSeparacion);

                g2d.setColor(new Color(0, 40, 80, 180));
                g2d.fillRoundRect(x, y, tamanoIcono, tamanoIcono, 10, 10);

                g2d.drawImage(iconosAnimatronicos.get(contador), x + 5, y + 5, tamanoIcono - 10, tamanoIcono - 10, this);

                if (contador == indiceSeleccionado) {
                    g2d.setColor(Color.GREEN);
                    g2d.setStroke(new java.awt.BasicStroke(3));
                    g2d.drawRoundRect(x, y, tamanoIcono - 1, tamanoIcono - 1, 10, 10);
                } else if (contador == indiceHover) {
                    g2d.setColor(Color.CYAN);
                    g2d.setStroke(new java.awt.BasicStroke(2));
                    g2d.drawRoundRect(x, y, tamanoIcono - 1, tamanoIcono - 1, 10, 10);
                } else {
                    g2d.setColor(new Color(0, 150, 255, 100));
                    g2d.setStroke(new java.awt.BasicStroke(1));
                    g2d.drawRoundRect(x, y, tamanoIcono - 1, tamanoIcono - 1, 10, 10);
                }

                contador++;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
