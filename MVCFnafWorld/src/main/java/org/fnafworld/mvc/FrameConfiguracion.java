/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import org.fnafworld.dominio.fachada.FachadaJuego;
import org.fnafworld.dominio.fachada.IFachadaJuego;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.mvc.vista.PanelSelector;
import org.fnafworld.mvc.vista.AnimatronicoSprite;
import org.fnafworld.mvc.vista.FrameSimuladorLobby;
import org.fnafworld.mvc.vista.FrameSimuladorRed;
import org.fnafworld.mvc.vista.PanelFondoBatalla;
import org.fnafworld.sonido.AudioManager;
import org.fnafworld.mvc.vista.PanelTitulo;
import org.fnafworld.mvc.vista.ScreenSelectorAnimatronicos;

public class FrameConfiguracion extends JFrame implements ModeloJuego.Observador {

    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;
    private ModeloJuego modelo;
    private ControlJuego control;
    private AudioManager audioManager;
    private ScreenSelectorAnimatronicos screenSelector;
    private FrameSimuladorLobby simuladorLobby;
    private boolean batallaAbierta;

    public FrameConfiguracion() {
        IFachadaJuego fachada = new FachadaJuego(null);
        this.modelo = new ModeloJuego(fachada);
        this.control = new ControlJuego(modelo);
        this.modelo.registrarObservador(this);

        setTitle("FNAF World - Modo Batalla");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        try {
            Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/extras/iconojuego.png"));
            setIconImage(icono);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono del juego: " + e.getMessage());
        }

        cardLayout = new CardLayout();
        contenedorPrincipal = new JPanel(cardLayout);

        PanelTitulo panelTitulo = new PanelTitulo(this);
        contenedorPrincipal.add(panelTitulo, "PanelTitulo");

        screenSelector = new ScreenSelectorAnimatronicos(this, modelo, control);
        contenedorPrincipal.add(screenSelector, "ScreenSelector");
        add(contenedorPrincipal);

        reproducirMusica();
    }

    private void reproducirMusica() {
        try {
            audioManager = new AudioManager();
            audioManager.loadMusic("/musica/TittleTheme.wav");
            audioManager.playMusicLoop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void cambiarPantalla(String nombreCard) {
        cardLayout.show(contenedorPrincipal, nombreCard);
    }

    public void crearPartida() {
        DatosJugador datos = pedirDatosJugador("Crear partida", "Jugador 0");
        if (datos == null) {
            return;
        }
        control.crearLobby(datos.nombre, datos.avatar);
        mostrarSimuladorLobby();
        cambiarPantalla("ScreenSelector");
    }

    public void unirsePartida() {
        DatosJugador datos = pedirDatosJugador("Unirse a partida", "Jugador 1");
        if (datos == null) {
            return;
        }
        control.unirseLobby(datos.nombre, datos.avatar);
        mostrarSimuladorLobby();
        cambiarPantalla("ScreenSelector");
    }

    @Override
    public void mapearActualizacion() {
        if (!batallaAbierta && modelo.getEstadoActual() != null) {
            abrirBatalla();
        }
    }

    private DatosJugador pedirDatosJugador(String titulo, String nombreDefault) {
        JTextField txtNombre = new JTextField(nombreDefault, 18);
        JComboBox<String> comboAvatar = new JComboBox<>(new String[] {
            "/avatars/mondongo.jpg",
            "/avatars/gatomojado.jpg",
            "/avatars/oruga.jpg",
            "/avatars/pabloncho.jpg"
        });

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel campos = new JPanel(new java.awt.GridLayout(2, 2, 8, 8));
        campos.add(new javax.swing.JLabel("Nombre:"));
        campos.add(txtNombre);
        campos.add(new javax.swing.JLabel("Avatar:"));
        campos.add(comboAvatar);
        panel.add(campos, BorderLayout.CENTER);

        int respuesta = JOptionPane.showConfirmDialog(this, panel, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (respuesta != JOptionPane.OK_OPTION) {
            return null;
        }
        return new DatosJugador(txtNombre.getText(), (String) comboAvatar.getSelectedItem());
    }

    private void mostrarSimuladorLobby() {
        if (simuladorLobby == null) {
            simuladorLobby = new FrameSimuladorLobby(modelo, control);
        }
        simuladorLobby.setVisible(true);
    }

    private void abrirBatalla() {
        batallaAbierta = true;
        if (simuladorLobby != null) {
            simuladorLobby.setVisible(false);
        }
        if (audioManager != null) {
            audioManager.stopMusic();
        }

        AudioManager audioBatalla = new AudioManager();
        audioBatalla.loadMusic("/musica/BossStoneCold.wav");
        audioBatalla.playMusicLoop();

        PanelFondoBatalla fondoBatalla = new PanelFondoBatalla("/escenarios/valle.png", crearSpritesDesdeJugadores(), modelo);
        FrameJuego frameJuego = new FrameJuego(audioBatalla, fondoBatalla, control, modelo);
        frameJuego.setVisible(true);

        FrameSimuladorRed simuladorRed = new FrameSimuladorRed(modelo, control);
        simuladorRed.setVisible(true);
        dispose();
    }

    private List<AnimatronicoSprite> crearSpritesDesdeJugadores() {
        List<AnimatronicoSprite> sprites = new ArrayList<>();
        for (JugadorDTO jugador : modelo.getJugadores()) {
            if (jugador == null || jugador.getGrupo() == null) {
                continue;
            }
            for (AnimatronicoDTO anim : jugador.getGrupo()) {
                if (anim != null) {
                    sprites.add(new AnimatronicoSprite(anim.getIdAnimatronico(), 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
                }
            }
        }
        return sprites;
    }

    private static class DatosJugador {
        private final String nombre;
        private final String avatar;

        private DatosJugador(String nombre, String avatar) {
            this.nombre = nombre;
            this.avatar = avatar;
        }
    }
}
