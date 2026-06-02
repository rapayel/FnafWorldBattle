/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.mvc;

import java.util.List;
import java.util.ArrayList;
import org.fnafworld.Equipo;
import org.fnafworld.TipoAnimatronico;
import org.fnafworld.dominio.entidades.Fabrica;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.dtos.EquiposDTO;
import org.fnafworld.dtos.AtaqueDTO;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.ResultadoAtaqueDTO;
import org.fnafworld.dtos.SolicitudAnimatronicoFabricaDTO;
import org.fnafworld.dominio.fachada.IFachadaJuego;
import org.fnafworld.TipoHabilidad;
/**
 * 
 * @author lagar
 */
public class ModeloJuego {

    private final IFachadaJuego fachada;
    private final Fabrica fabrica;
    private ResultadoAtaqueDTO estadoActual;
    private List<JugadorDTO> jugadores;
    private List<JugadorDTO> jugadoresLobby;
    private String idJugadorConfigurando;
    private final List<Observador> observadores;

    public ModeloJuego(IFachadaJuego fachada) {
        this.fachada = fachada;
        this.fabrica = new Fabrica();
        this.observadores = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.jugadoresLobby = new ArrayList<>();
    }

    public void crearLobbyLocal(String nombre, String urlAvatar) {
        jugadoresLobby = new ArrayList<>();
        idJugadorConfigurando = "0";
        jugadoresLobby.add(new JugadorDTO("0", normalizarNombre(nombre, "Jugador 0"), urlAvatar, new AnimatronicoDTO[0], false, Equipo.Rojo));
        notificarObservadores();
    }

    public void unirseLobbyLocal(String nombre, String urlAvatar) {
        if (jugadoresLobby.isEmpty()) {
            crearLobbyLocal("Anfitrion", "/avatars/mondongo.jpg");
        }
        if (buscarJugadorLobby("0") == null) {
            jugadoresLobby.add(new JugadorDTO("0", "Jugador 0", "/avatars/mondongo.jpg", new AnimatronicoDTO[0], false, Equipo.Rojo));
        }
        idJugadorConfigurando = "1";
        agregarOActualizarJugadorLobby("1", normalizarNombre(nombre, "Jugador 1"), urlAvatar, Equipo.Azul, new AnimatronicoDTO[0], false);
        notificarObservadores();
    }

    public void agregarJugadorRemoto(String id, String nombre, String urlAvatar, Equipo equipo) {
        agregarOActualizarJugadorLobby(id, normalizarNombre(nombre, "Jugador " + id), urlAvatar, equipo, obtenerGrupoLobby(id), false);
        notificarObservadores();
    }

    public void seleccionarJugadorConfigurando(String idJugador) {
        if (buscarJugadorLobby(idJugador) != null) {
            idJugadorConfigurando = idJugador;
            notificarObservadores();
        }
    }

    public void agregarAnimatronicoAGrupo(String idJugador, TipoAnimatronico tipo) {
        JugadorDTO jugador = buscarJugadorLobby(idJugador);
        if (jugador == null || tipo == null) {
            return;
        }

        AnimatronicoDTO[] grupoActual = jugador.getGrupo() != null ? jugador.getGrupo() : new AnimatronicoDTO[0];
        if (grupoActual.length >= 4 || contieneAnimatronico(grupoActual, tipo)) {
            return;
        }

        AnimatronicoDTO[] nuevoGrupo = new AnimatronicoDTO[grupoActual.length + 1];
        System.arraycopy(grupoActual, 0, nuevoGrupo, 0, grupoActual.length);
        nuevoGrupo[grupoActual.length] = fabrica.crearAnimatronico(
                new SolicitudAnimatronicoFabricaDTO(tipo.name(), tipo, false)
        );
        actualizarJugadorLobby(jugador, nuevoGrupo, false);
        notificarObservadores();
    }

    public void quitarAnimatronicoDeGrupo(String idJugador, int indice) {
        JugadorDTO jugador = buscarJugadorLobby(idJugador);
        if (jugador == null || jugador.getGrupo() == null || indice < 0 || indice >= jugador.getGrupo().length) {
            return;
        }
        AnimatronicoDTO[] nuevoGrupo = new AnimatronicoDTO[jugador.getGrupo().length - 1];
        for (int i = 0, j = 0; i < jugador.getGrupo().length; i++) {
            if (i != indice) {
                nuevoGrupo[j++] = jugador.getGrupo()[i];
            }
        }
        actualizarJugadorLobby(jugador, nuevoGrupo, false);
        notificarObservadores();
    }

    public void marcarJugadorListo(String idJugador) {
        JugadorDTO jugador = buscarJugadorLobby(idJugador);
        if (jugador == null || jugador.getGrupo() == null || jugador.getGrupo().length == 0) {
            return;
        }

        AnimatronicoDTO[] grupo = jugador.getGrupo();
        AnimatronicoDTO[] grupoConTurno = new AnimatronicoDTO[grupo.length];
        for (int i = 0; i < grupo.length; i++) {
            AnimatronicoDTO anim = grupo[i];
            grupoConTurno[i] = new AnimatronicoDTO(
                    anim.getIdAnimatronico(),
                    anim.getTipo(),
                    i == 0,
                    anim.getFuerza(),
                    anim.getArmadura(),
                    anim.getVidaActual(),
                    anim.getVidaTotal(),
                    anim.getHabilidades(),
                    anim.isIsVivo()
            );
        }
        actualizarJugadorLobby(jugador, grupoConTurno, true);
        notificarObservadores();
    }

    public boolean puedeIniciarLobby() {
        if (jugadoresLobby.size() < 2 || jugadoresLobby.size() > 4) {
            return false;
        }
        for (JugadorDTO jugador : jugadoresLobby) {
            if (!jugador.isMiTurno() || jugador.getGrupo() == null || jugador.getGrupo().length == 0) {
                return false;
            }
        }
        return true;
    }

    public void iniciarPartidaDesdeLobby(String urlCampo, String urlMusica) {
        if (!puedeIniciarLobby()) {
            return;
        }
        iniciarPartidaEnDominio(jugadoresLobby, urlCampo, urlMusica);
    }

    public void iniciarPartidaEnDominio(List<JugadorDTO> listaMockJugadores, String urlCampo, String urlMusica) {
        this.jugadores = listaMockJugadores;
        EquiposDTO equipos = new EquiposDTO(listaMockJugadores, urlCampo, urlMusica);
        this.estadoActual = fachada.iniciarPartida(equipos);
        sincronizarJugadoresDesdeEstado();
        notificarObservadores();
    }

    public void ejecutarAtaqueEnDominio(String idJugador, String idAnimatronico, TipoHabilidad habilidad) {
        AtaqueDTO dtoAtaque = new AtaqueDTO(idJugador, idAnimatronico, habilidad);
        this.estadoActual = fachada.atacar(dtoAtaque);
        sincronizarJugadoresDesdeEstado();
        notificarObservadores();
    }

    public void registrarObservador(Observador o) {
        this.observadores.add(o);
    }

    private void notificarObservadores() {
        for (Observador o : observadores) {
            o.mapearActualizacion();
        }
    }

    private void sincronizarJugadoresDesdeEstado() {
        if (estadoActual != null && estadoActual.getJugadores() != null) {
            this.jugadores = estadoActual.getJugadores();
        }
    }

    public ResultadoAtaqueDTO getEstadoActual() { 
        return estadoActual; 
    }
    
    public List<JugadorDTO> getJugadores() { 
        return jugadores; 
    }

    public List<JugadorDTO> getJugadoresLobby() {
        return jugadoresLobby;
    }

    public String getIdJugadorConfigurando() {
        return idJugadorConfigurando;
    }

    public AnimatronicoDTO crearAnimatronicoVistaPrevia(TipoAnimatronico tipo) {
        return fabrica.crearAnimatronico(new SolicitudAnimatronicoFabricaDTO(tipo.name(), tipo, false));
    }
    
    public String getIdJugadorTurnoActual() {
        return estadoActual != null ? estadoActual.getIdJugadorTurnoActual() : "0";
    }

    public interface Observador {
        void mapearActualizacion();
    }

    private String normalizarNombre(String nombre, String respaldo) {
        return nombre != null && !nombre.isBlank() ? nombre.trim() : respaldo;
    }

    private void agregarOActualizarJugadorLobby(String id, String nombre, String urlAvatar, Equipo equipo, AnimatronicoDTO[] grupo, boolean listo) {
        JugadorDTO existente = buscarJugadorLobby(id);
        JugadorDTO actualizado = new JugadorDTO(id, nombre, urlAvatar, grupo, listo, equipo);
        if (existente == null) {
            if (jugadoresLobby.size() < 4) {
                jugadoresLobby.add(actualizado);
            }
            return;
        }

        for (int i = 0; i < jugadoresLobby.size(); i++) {
            if (jugadoresLobby.get(i).getId().equals(id)) {
                jugadoresLobby.set(i, actualizado);
                return;
            }
        }
    }

    private void actualizarJugadorLobby(JugadorDTO jugador, AnimatronicoDTO[] grupo, boolean listo) {
        agregarOActualizarJugadorLobby(
                jugador.getId(),
                jugador.getNombre(),
                jugador.getUrlAvatar(),
                jugador.getEquipo(),
                grupo,
                listo
        );
    }

    private JugadorDTO buscarJugadorLobby(String id) {
        if (id == null) {
            return null;
        }
        for (JugadorDTO jugador : jugadoresLobby) {
            if (jugador != null && id.equals(jugador.getId())) {
                return jugador;
            }
        }
        return null;
    }

    private AnimatronicoDTO[] obtenerGrupoLobby(String id) {
        JugadorDTO jugador = buscarJugadorLobby(id);
        return jugador != null && jugador.getGrupo() != null ? jugador.getGrupo() : new AnimatronicoDTO[0];
    }

    private boolean contieneAnimatronico(AnimatronicoDTO[] grupo, TipoAnimatronico tipo) {
        for (AnimatronicoDTO anim : grupo) {
            if (anim != null && anim.getTipo() == tipo) {
                return true;
            }
        }
        return false;
    }
}
