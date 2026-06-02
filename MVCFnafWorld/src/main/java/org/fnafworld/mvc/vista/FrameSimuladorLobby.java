package org.fnafworld.mvc.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.fnafworld.Equipo;
import org.fnafworld.TipoAnimatronico;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.mvc.ControlJuego;
import org.fnafworld.mvc.ModeloJuego;

public class FrameSimuladorLobby extends JFrame implements ModeloJuego.Observador {

    private final ModeloJuego modelo;
    private final ControlJuego control;
    private JComboBox<String> comboJugador;
    private JTextField txtNombre;
    private JComboBox<String> comboAvatar;
    private JComboBox<Equipo> comboEquipo;
    private JComboBox<TipoAnimatronico> comboAnimatronico;
    private JLabel lblEstado;

    public FrameSimuladorLobby(ModeloJuego modelo, ControlJuego control) {
        this.modelo = modelo;
        this.control = control;
        this.modelo.registrarObservador(this);
        configurarVentana();
        inicializarComponentes();
        mapearActualizacion();
    }

    private void configurarVentana() {
        setTitle("Simulador de Lobby / Red");
        setSize(460, 340);
        setLocation(120, 120);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 2, 8, 8));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        comboJugador = new JComboBox<>(new String[] {"1", "2", "3"});
        txtNombre = new JTextField("Remoto");
        comboAvatar = new JComboBox<>(new String[] {
            "/avatars/mondongo.jpg",
            "/avatars/gatomojado.jpg",
            "/avatars/oruga.jpg",
            "/avatars/pabloncho.jpg"
        });
        comboEquipo = new JComboBox<>(Equipo.values());
        comboAnimatronico = new JComboBox<>(TipoAnimatronico.values());
        lblEstado = new JLabel("Jugadores: 0");

        JButton btnUnir = new JButton("Crear/Unir jugador");
        JButton btnAgregar = new JButton("Agregar animatronico");
        JButton btnListo = new JButton("Listo remoto");
        JButton btnIniciar = new JButton("Iniciar si todos listos");

        btnUnir.addActionListener(e -> unirJugadorRemoto());
        btnAgregar.addActionListener(e -> agregarAnimatronicoRemoto());
        btnListo.addActionListener(e -> control.marcarJugadorListo((String) comboJugador.getSelectedItem()));
        btnIniciar.addActionListener(e -> control.iniciarPartidaDesdeLobby());

        panel.add(new JLabel("Jugador ID:"));
        panel.add(comboJugador);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Avatar:"));
        panel.add(comboAvatar);
        panel.add(new JLabel("Equipo:"));
        panel.add(comboEquipo);
        panel.add(new JLabel("Animatronico:"));
        panel.add(comboAnimatronico);
        panel.add(new JLabel("Estado:"));
        panel.add(lblEstado);
        panel.add(btnUnir);
        panel.add(btnAgregar);
        panel.add(btnListo);
        panel.add(btnIniciar);

        add(panel, BorderLayout.CENTER);
    }

    private void unirJugadorRemoto() {
        String id = (String) comboJugador.getSelectedItem();
        control.agregarJugadorRemoto(
                id,
                txtNombre.getText(),
                (String) comboAvatar.getSelectedItem(),
                (Equipo) comboEquipo.getSelectedItem()
        );
    }

    private void agregarAnimatronicoRemoto() {
        String id = (String) comboJugador.getSelectedItem();
        if (!existeJugador(id)) {
            unirJugadorRemoto();
        }
        control.agregarAnimatronicoAGrupo(id, (TipoAnimatronico) comboAnimatronico.getSelectedItem());
    }

    private boolean existeJugador(String id) {
        for (JugadorDTO jugador : modelo.getJugadoresLobby()) {
            if (jugador != null && jugador.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mapearActualizacion() {
        int listos = 0;
        for (JugadorDTO jugador : modelo.getJugadoresLobby()) {
            if (jugador != null && jugador.isMiTurno()) {
                listos++;
            }
        }
        lblEstado.setText(modelo.getJugadoresLobby().size() + " jugadores, " + listos + " listos");
    }
}
