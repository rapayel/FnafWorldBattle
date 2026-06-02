package org.fnafworld.mvc.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.JugadorDTO;

public class PanelCargaBatalla extends JPanel {

    private final JLabel lblEstado;
    private final JPanel panelJugadores;

    public PanelCargaBatalla() {
        setLayout(new BorderLayout(0, 22));
        setBackground(new Color(12, 14, 22));
        setBorder(BorderFactory.createEmptyBorder(36, 48, 36, 48));

        JLabel titulo = new JLabel("CARGANDO BATALLA", SwingConstants.CENTER);
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        add(titulo, BorderLayout.NORTH);

        panelJugadores = new JPanel();
        panelJugadores.setOpaque(false);
        panelJugadores.setLayout(new BoxLayout(panelJugadores, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(panelJugadores);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 160, 255, 90), 1));
        add(scroll, BorderLayout.CENTER);

        lblEstado = new JLabel("Preparando equipos...", SwingConstants.CENTER);
        lblEstado.setForeground(Color.LIGHT_GRAY);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblEstado, BorderLayout.SOUTH);
    }

    public void actualizarDatos(List<JugadorDTO> jugadores, String estado) {
        panelJugadores.removeAll();
        if (jugadores != null) {
            for (JugadorDTO jugador : jugadores) {
                if (jugador != null) {
                    panelJugadores.add(crearPanelJugador(jugador));
                    panelJugadores.add(Box.createVerticalStrut(10));
                }
            }
        }
        lblEstado.setText(estado);
        panelJugadores.revalidate();
        panelJugadores.repaint();
    }

    private JPanel crearPanelJugador(JugadorDTO jugador) {
        JPanel panel = new JPanel(new BorderLayout(14, 8));
        panel.setBackground(new Color(22, 26, 38));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 150, 255, 100), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 132));

        JLabel avatar = new JLabel();
        avatar.setPreferredSize(new Dimension(72, 72));
        ImageIcon avatarIcon = cargarIcono(jugador.getUrlAvatar(), 72, 72);
        if (avatarIcon != null) {
            avatar.setIcon(avatarIcon);
        }
        panel.add(avatar, BorderLayout.WEST);

        JPanel contenido = new JPanel(new BorderLayout(0, 8));
        contenido.setOpaque(false);

        JLabel titulo = new JLabel(jugador.getNombre() + " | Equipo " + jugador.getEquipo());
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        contenido.add(titulo, BorderLayout.NORTH);

        JPanel animatronicos = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        animatronicos.setOpaque(false);
        if (jugador.getGrupo() != null) {
            for (AnimatronicoDTO anim : jugador.getGrupo()) {
                if (anim != null) {
                    animatronicos.add(crearFichaAnimatronico(anim));
                }
            }
        }
        contenido.add(animatronicos, BorderLayout.CENTER);

        panel.add(contenido, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearFichaAnimatronico(AnimatronicoDTO anim) {
        JPanel ficha = new JPanel(new GridLayout(2, 1, 0, 3));
        ficha.setOpaque(false);
        ficha.setPreferredSize(new Dimension(78, 86));

        JLabel icono = new JLabel("", SwingConstants.CENTER);
        ImageIcon imagen = cargarIcono(PanelSelector.iconoParaTipo(anim.getTipo()), 44, 44);
        if (imagen != null) {
            icono.setIcon(imagen);
        }

        JLabel nombre = new JLabel(anim.getTipo().name(), SwingConstants.CENTER);
        nombre.setForeground(Color.LIGHT_GRAY);
        nombre.setFont(new Font("Arial", Font.BOLD, 10));

        ficha.add(icono);
        ficha.add(nombre);
        return ficha;
    }

    private ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        if (ruta == null) {
            return null;
        }
        java.net.URL url = getClass().getResource(ruta);
        if (url == null) {
            return null;
        }
        Image imagen = new ImageIcon(url).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
}
