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
import org.fnafworld.mvc.vista.AnimatronicoSprite;
import org.fnafworld.mvc.vista.FrameSimuladorLobby;
import org.fnafworld.mvc.vista.FrameSimuladorRed;
import org.fnafworld.mvc.vista.PanelCargaBatalla;
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
    private PanelCargaBatalla panelCargaBatalla;
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

        panelCargaBatalla = new PanelCargaBatalla();
        contenedorPrincipal.add(panelCargaBatalla, "PanelCargaBatalla");
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

    public void iniciarPartidaConCarga() {
        if (!modelo.puedeIniciarLobby()) {
            return;
        }
        mostrarCargaBatalla("Sincronizando equipos...");
        javax.swing.SwingUtilities.invokeLater(() -> control.iniciarPartidaDesdeLobby());
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
            mostrarCargaBatalla("Construyendo escenario...");
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

    private void mostrarCargaBatalla(String estado) {
        panelCargaBatalla.actualizarDatos(modelo.getJugadoresLobby(), estado);
        cambiarPantalla("PanelCargaBatalla");
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
                    sprites.add(crearSpriteParaTipo(anim.getIdAnimatronico()));
                }
            }
        }
        return sprites;
    }

    private AnimatronicoSprite crearSpriteParaTipo(String nombre) {
        switch (nombre) {
            case "Freddy":           return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Bonnie":           return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Chica":            return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Foxy":             return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "WitheredFreddy":   return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "WitheredBonnie":   return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "WitheredChica":    return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "WitheredFoxy":     return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "NightmareFreddy":  return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "NightmareBonnie":  return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "NightmareChica":   return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 12, 2, 40, 40);
            case "NightmareFoxy":    return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 12, 2, 40, 40);
            case "ToyFreddy":        return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "ToyBonnie":        return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "ToyChica":         return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Mangle":           return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "BalloonBoy":       return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "JJ":               return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 16, 2, 40, 40);
            case "PhantomFreddy":    return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "PhantomChica":     return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "PhantomPuppet":    return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "PhantomBB":        return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "PhantomFoxy":      return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "PhantomMangle":    return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 12, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "ShadowFreddy":     return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Puppet":           return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "GoldenFreddy":     return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Paperpals":        return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "ShadowBonnie":     return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Endo01":           return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Endo02":           return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Endoplush":        return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 12, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "Animdude":         return new AnimatronicoSprite(nombre, 2, 0, 250, 250, 10, 2, 2, 0, 250, 248, 10, 2, 40, 40);
            case "Coffee":           return new AnimatronicoSprite(nombre, 4, 4, 199, 147, 9, 4, 4, 157, 197, 147, 12, 6, 40, 40);
            case "CryingChild":      return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 12, 2, 40, 40);
            case "Fredbear":         return new AnimatronicoSprite(nombre, 2, 3, 250, 298, 10, 2, 2, 303, 250, 298, 11, 2, 40, 40);
            case "FuntimeFoxy":      return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 12, 2, 2, 253, 250, 248, 11, 2, 40, 45);
            case "JackOBonnie":      return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 12, 2, 40, 40);
            case "JackOChica":       return new AnimatronicoSprite(nombre, 4, 4, 249, 248, 11, 4, 4, 257, 254, 258, 12, 4, 40, 40);
            case "MrChipper":        return new AnimatronicoSprite(nombre, 5, 4, 248, 249, 9, 5, 5, 257, 248, 249, 10, 5, 40, 40);
            case "Nightmare":        return new AnimatronicoSprite(nombre, 2, 1, 250, 300, 12, 2, 2, 303, 250, 300, 13, 2, 40, 40);
            case "NightmareFredbear":return new AnimatronicoSprite(nombre, 2, 1, 250, 300, 12, 2, 2, 303, 250, 300, 11, 2, 40, 40);
            case "NightmareBB":      return new AnimatronicoSprite(nombre, 2, 3, 250, 247, 10, 2, 2, 253, 250, 247, 10, 2, 40, 40);
            case "NightmarePuppet":  return new AnimatronicoSprite(nombre, 2, 0, 250, 248, 9, 2, 2, 252, 250, 248, 11, 2, 40, 40);
            case "Plushtrap":        return new AnimatronicoSprite(nombre, 2, 1, 250, 248, 10, 2, 2, 505, 250, 248, 11, 2, 40, 40);
            case "Purpleguy":        return new AnimatronicoSprite(nombre, 2, 3, 250, 247, 2, 2, 2, 253, 250, 248, 11, 2, 500, 40);
            case "SpringBonnie":     return new AnimatronicoSprite(nombre, 2, 1, 250, 300, 12, 2, 2, 303, 250, 300, 13, 2, 40, 40);
            case "Springtrap":       return new AnimatronicoSprite(nombre, 2, 3, 250, 247, 12, 2, 2, 253, 250, 248, 11, 2, 40, 40);
            case "TheFan":           return new AnimatronicoSprite(nombre, 2, 1, 100, 100, 5, 2, 2, 1, 100, 100, 5, 2, 40, 40);
            default:                 return new AnimatronicoSprite(nombre, 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40);
        }
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
