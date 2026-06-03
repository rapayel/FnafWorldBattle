package org.fnafworld.mvc.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.fnafworld.TipoAnimatronico;

public class PanelSelector extends JPanel implements ActionListener {

    private static final int COLUMNAS = 8;
    private static final int TAMANO_ICONO = 74;
    private static final int SEPARACION = 16;

    private final Timer loopAnimacion;
    private final List<TipoAnimatronico> tipos;
    private final List<Image> iconos;
    private Consumer<TipoAnimatronico> alSeleccionar;
    private int indiceSeleccionado = -1;
    private int indiceHover = -1;

    public PanelSelector() {
        this.tipos = new ArrayList<>();
        this.iconos = new ArrayList<>();
        this.setPreferredSize(new Dimension(860, 620));
        this.setOpaque(false);
        cargarIconos();
        configurarEventosMouse();
        this.loopAnimacion = new Timer(16, this);
        this.loopAnimacion.start();
    }

    public void setAlSeleccionar(Consumer<TipoAnimatronico> alSeleccionar) {
        this.alSeleccionar = alSeleccionar;
    }

    public TipoAnimatronico getSeleccionado() {
        if (indiceSeleccionado < 0 || indiceSeleccionado >= tipos.size()) {
            return null;
        }
        return tipos.get(indiceSeleccionado);
    }

    private void cargarIconos() {
        for (TipoAnimatronico tipo : TipoAnimatronico.values()) {
            String ruta = iconoParaTipo(tipo);
            java.net.URL url = getClass().getResource(ruta);
            if (url != null) {
                tipos.add(tipo);
                iconos.add(new ImageIcon(url).getImage());
            }
        }
    }

    public static String iconoParaTipo(TipoAnimatronico tipo) {
        if (tipo == null) {
            return "/iconos/iconojuego.png";
        }
        switch (tipo) {
            case BalloonBoy:
                return "/iconos/balloonboy_ico.png";
            case MrChipper:
                return "/iconos/MrChipper_ico.png";
            case Springtrap:
                return "/iconos/springtrap_ico.png";
            default:
                return "/iconos/" + tipo.name().toLowerCase() + "_ico.png";
        }
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
                int indice = obtenerIndicePorCoordenadas(e.getX(), e.getY());
                if (indice != -1) {
                    indiceSeleccionado = indice;
                    if (alSeleccionar != null) {
                        alSeleccionar.accept(tipos.get(indice));
                    }
                    repaint();
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private int obtenerIndicePorCoordenadas(int mx, int my) {
        int filas = obtenerFilas();
        int anchoGrid = COLUMNAS * TAMANO_ICONO + (COLUMNAS - 1) * SEPARACION;
        int altoGrid = filas * TAMANO_ICONO + (filas - 1) * SEPARACION;
        int xInicio = Math.max(20, (getWidth() - anchoGrid) / 2);
        int yInicio = Math.max(20, (getHeight() - altoGrid) / 2);

        for (int i = 0; i < tipos.size(); i++) {
            int fila = i / COLUMNAS;
            int columna = i % COLUMNAS;
            int x = xInicio + columna * (TAMANO_ICONO + SEPARACION);
            int y = yInicio + fila * (TAMANO_ICONO + SEPARACION);
            if (mx >= x && mx <= x + TAMANO_ICONO && my >= y && my <= y + TAMANO_ICONO) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerFilas() {
        return Math.max(1, (int) Math.ceil(tipos.size() / (double) COLUMNAS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.setColor(new Color(10, 18, 30, 190));
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        g2d.setColor(new Color(0, 180, 255, 90));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

        int filas = obtenerFilas();
        int anchoGrid = COLUMNAS * TAMANO_ICONO + (COLUMNAS - 1) * SEPARACION;
        int altoGrid = filas * TAMANO_ICONO + (filas - 1) * SEPARACION;
        int xInicio = Math.max(20, (getWidth() - anchoGrid) / 2);
        int yInicio = Math.max(20, (getHeight() - altoGrid) / 2);

        for (int i = 0; i < tipos.size(); i++) {
            int fila = i / COLUMNAS;
            int columna = i % COLUMNAS;
            int x = xInicio + columna * (TAMANO_ICONO + SEPARACION);
            int y = yInicio + fila * (TAMANO_ICONO + SEPARACION);

            g2d.setColor(new Color(0, 40, 80, 180));
            g2d.fillRoundRect(x, y, TAMANO_ICONO, TAMANO_ICONO, 10, 10);
            g2d.drawImage(iconos.get(i), x + 5, y + 5, TAMANO_ICONO - 10, TAMANO_ICONO - 10, this);

            if (i == indiceSeleccionado) {
                g2d.setColor(new Color(80, 255, 120));
                g2d.setStroke(new BasicStroke(3));
            } else if (i == indiceHover) {
                g2d.setColor(Color.CYAN);
                g2d.setStroke(new BasicStroke(2));
            } else {
                g2d.setColor(new Color(0, 150, 255, 100));
                g2d.setStroke(new BasicStroke(1));
            }
            g2d.drawRoundRect(x, y, TAMANO_ICONO - 1, TAMANO_ICONO - 1, 10, 10);

            if (i == indiceHover || i == indiceSeleccionado) {
                g2d.setFont(new Font("Arial", Font.BOLD, 10));
                String nombre = tipos.get(i).name();
                int ancho = g2d.getFontMetrics().stringWidth(nombre);
                g2d.drawString(nombre, x + (TAMANO_ICONO - ancho) / 2, y + TAMANO_ICONO + 12);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
