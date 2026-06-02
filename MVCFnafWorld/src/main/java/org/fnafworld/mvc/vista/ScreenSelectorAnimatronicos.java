package org.fnafworld.mvc.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.fnafworld.TipoAnimatronico;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.mvc.ControlJuego;
import org.fnafworld.mvc.FrameConfiguracion;
import org.fnafworld.mvc.ModeloJuego;

public class ScreenSelectorAnimatronicos extends JPanel implements ModeloJuego.Observador {

    private final FrameConfiguracion parent;
    private final ModeloJuego modelo;
    private final ControlJuego control;
    private PanelSelector panelGridIconos;
    private PanelMuestra panelVistaPrevia;
    private JPanel panelJugadores;
    private JLabel lblEstado;
    private JButton btnAgregar;
    private JButton btnListo;
    private JButton btnIniciar;
    private TipoAnimatronico tipoSeleccionado;

    public ScreenSelectorAnimatronicos(FrameConfiguracion parent, ModeloJuego modelo, ControlJuego control) {
        this.parent = parent;
        this.modelo = modelo;
        this.control = control;
        this.modelo.registrarObservador(this);

        this.setLayout(new BorderLayout(22, 0));
        this.setBackground(new Color(15, 15, 20));
        this.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        panelGridIconos = new PanelSelector();
        panelGridIconos.setAlSeleccionar(this::seleccionarAnimatronico);
        this.add(panelGridIconos, BorderLayout.CENTER);

        JPanel lateral = new JPanel(new BorderLayout(0, 16));
        lateral.setOpaque(false);
        lateral.setPreferredSize(new Dimension(380, 620));

        panelVistaPrevia = new PanelMuestra();
        lateral.add(panelVistaPrevia, BorderLayout.NORTH);

        panelJugadores = new JPanel();
        panelJugadores.setOpaque(false);
        panelJugadores.setLayout(new BoxLayout(panelJugadores, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(panelJugadores);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 160, 255, 90)));
        lateral.add(scroll, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new GridLayout(4, 1, 0, 8));
        acciones.setOpaque(false);
        lblEstado = new JLabel("Selecciona un animatronico");
        lblEstado.setForeground(Color.WHITE);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar = crearBoton("Agregar al grupo");
        btnListo = crearBoton("Listo");
        btnIniciar = crearBoton("Iniciar partida");

        btnAgregar.addActionListener(e -> agregarSeleccionAlGrupo());
        btnListo.addActionListener(e -> control.marcarJugadorListo(modelo.getIdJugadorConfigurando()));
        btnIniciar.addActionListener(e -> parent.iniciarPartidaConCarga());

        acciones.add(lblEstado);
        acciones.add(btnAgregar);
        acciones.add(btnListo);
        acciones.add(btnIniciar);
        lateral.add(acciones, BorderLayout.SOUTH);

        this.add(lateral, BorderLayout.EAST);
        mapearActualizacion();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(25, 85, 145));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 190, 255), 1));
        return boton;
    }

    private void seleccionarAnimatronico(TipoAnimatronico tipo) {
        tipoSeleccionado = tipo;
        AnimatronicoDTO dto = modelo.crearAnimatronicoVistaPrevia(tipo);
        panelVistaPrevia.actualizarInformacion(dto, new ImageIcon(getClass().getResource(PanelSelector.iconoParaTipo(tipo))).getImage());
        lblEstado.setText("Seleccionado: " + tipo.name());
    }

    private void agregarSeleccionAlGrupo() {
        if (tipoSeleccionado != null && modelo.getIdJugadorConfigurando() != null) {
            control.agregarAnimatronicoAGrupo(modelo.getIdJugadorConfigurando(), tipoSeleccionado);
        }
    }

    @Override
    public void mapearActualizacion() {
        panelJugadores.removeAll();
        for (JugadorDTO jugador : modelo.getJugadoresLobby()) {
            panelJugadores.add(crearPanelJugador(jugador));
        }
        boolean hayJugadorConfigurando = modelo.getIdJugadorConfigurando() != null;
        btnAgregar.setEnabled(hayJugadorConfigurando && puedeAgregarAlJugadorConfigurando());
        btnListo.setEnabled(hayJugadorConfigurando);
        btnIniciar.setEnabled(modelo.puedeIniciarLobby());
        actualizarEstadoConteoLobby();
        panelJugadores.revalidate();
        panelJugadores.repaint();
    }

    private JPanel crearPanelJugador(JugadorDTO jugador) {
        boolean editable = esJugadorConfigurando(jugador);
        int cantidadAnimatronicos = jugador.getGrupo() != null ? jugador.getGrupo().length : 0;
        int maximoAnimatronicos = modelo.getMaximoAnimatronicosPermitidos(jugador.getId());
        JPanel panel = new JPanel(new BorderLayout(8, 6));
        panel.setOpaque(true);
        panel.setBackground(editable
                ? new Color(25, 55, 85)
                : new Color(22, 26, 38));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 150, 255, 100)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel titulo = new JLabel(jugador.getNombre() + " - " + jugador.getEquipo()
                + " (" + cantidadAnimatronicos + "/" + maximoAnimatronicos + ")"
                + (jugador.isMiTurno() ? " (Listo)" : ""));
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel iconos = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        iconos.setOpaque(false);
        if (jugador.getGrupo() != null) {
            for (int i = 0; i < jugador.getGrupo().length; i++) {
                AnimatronicoDTO anim = jugador.getGrupo()[i];
                if (anim != null) {
                    JButton btnIcono = new JButton(new ImageIcon(new ImageIcon(getClass().getResource(PanelSelector.iconoParaTipo(anim.getTipo())))
                            .getImage().getScaledInstance(42, 42, java.awt.Image.SCALE_SMOOTH)));
                    btnIcono.setPreferredSize(new Dimension(48, 48));
                    btnIcono.setToolTipText(anim.getTipo().name());
                    btnIcono.setFocusPainted(false);
                    final int indice = i;
                    btnIcono.setEnabled(editable);
                    if (editable) {
                        btnIcono.addActionListener(e -> control.quitarAnimatronicoDeGrupo(jugador.getId(), indice));
                    }
                    iconos.add(btnIcono);
                }
            }
        }
        panel.add(iconos, BorderLayout.CENTER);

        if (editable) {
            JButton configurar = crearBoton("Tu grupo");
            configurar.setEnabled(false);
            panel.add(configurar, BorderLayout.EAST);
        }
        return panel;
    }

    private boolean esJugadorConfigurando(JugadorDTO jugador) {
        return jugador != null && jugador.getId().equals(modelo.getIdJugadorConfigurando());
    }

    private boolean puedeAgregarAlJugadorConfigurando() {
        for (JugadorDTO jugador : modelo.getJugadoresLobby()) {
            if (esJugadorConfigurando(jugador)) {
                int cantidadAnimatronicos = jugador.getGrupo() != null ? jugador.getGrupo().length : 0;
                return cantidadAnimatronicos < modelo.getMaximoAnimatronicosPermitidos(jugador.getId());
            }
        }
        return false;
    }

    private void actualizarEstadoConteoLobby() {
        String idJugador = modelo.getIdJugadorConfigurando();
        int cantidadJugadores = modelo.getCantidadJugadoresLobby();
        if (idJugador == null) {
            lblEstado.setText("Jugadores: " + cantidadJugadores);
            return;
        }

        for (JugadorDTO jugador : modelo.getJugadoresLobby()) {
            if (esJugadorConfigurando(jugador)) {
                int cantidadAnimatronicos = jugador.getGrupo() != null ? jugador.getGrupo().length : 0;
                int maximoAnimatronicos = modelo.getMaximoAnimatronicosPermitidos(jugador.getId());
                lblEstado.setText("Jugadores: " + cantidadJugadores + " | Tu grupo: "
                        + cantidadAnimatronicos + "/" + maximoAnimatronicos);
                return;
            }
        }
        lblEstado.setText("Jugadores: " + cantidadJugadores);
    }
}
