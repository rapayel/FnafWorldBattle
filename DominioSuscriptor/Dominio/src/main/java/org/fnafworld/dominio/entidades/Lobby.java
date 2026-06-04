package org.fnafworld.dominio.entidades;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.EquiposDTO;
import org.fnafworld.dtos.EstadoLobbyDTO;
import org.fnafworld.dtos.GrupoJugadorLobbyDTO;
import org.fnafworld.dtos.HabilidadDTO;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.dtos.JugadorLobbyDTO;
import org.fnafworld.dtos.LobbyDTO;

public class Lobby {
    public static final int MINIMO_JUGADORES = 2;
    public static final int MAXIMO_JUGADORES = 4;

    private final Map<String, JugadorLobbyDTO> jugadores;
    private final String urlCampo;
    private final String urlMusica;
    private boolean inicializada;

    public Lobby(LobbyDTO lobbyDTO) {
        if (lobbyDTO == null) {
            throw new IllegalArgumentException("La configuracion del lobby no puede ser nula.");
        }
        this.jugadores = new LinkedHashMap<>();
        this.urlCampo = lobbyDTO.getUrlCampo();
        this.urlMusica = lobbyDTO.getUrlMusica();
        this.inicializada = false;

        if (lobbyDTO.getJugadores() != null) {
            for (JugadorLobbyDTO jugador : lobbyDTO.getJugadores()) {
                unirJugador(jugador);
            }
        }
    }

    public Lobby(EquiposDTO equiposDTO) {
        this(convertirEquiposALobby(equiposDTO));
    }

    public void unirJugador(JugadorLobbyDTO jugador) {
        validarLobbyAbierto();
        if (jugador == null || jugador.getId() == null || jugador.getId().isBlank()) {
            throw new IllegalArgumentException("El jugador del lobby debe tener id.");
        }
        if (jugadores.containsKey(jugador.getId())) {
            throw new IllegalArgumentException("El jugador ya esta unido al lobby.");
        }
        if (jugadores.size() >= MAXIMO_JUGADORES) {
            throw new IllegalStateException("El lobby no permite mas de " + MAXIMO_JUGADORES + " jugadores.");
        }
        jugadores.put(jugador.getId(), jugador);
    }

    public void registrarGrupo(GrupoJugadorLobbyDTO grupo) {
        validarLobbyAbierto();
        if (grupo == null || grupo.getIdJugador() == null || !jugadores.containsKey(grupo.getIdJugador())) {
            throw new IllegalArgumentException("No existe el jugador para registrar el grupo.");
        }

        JugadorLobbyDTO jugador = jugadores.get(grupo.getIdJugador());
        jugadores.put(jugador.getId(), new JugadorLobbyDTO(
                jugador.getId(),
                jugador.getNombre(),
                jugador.getUrlAvatar(),
                jugador.getEquipo(),
                grupo.getGrupo(),
                grupo.isListo()
        ));
    }

    public BatallaCampo iniciarPartida() {
        validarPuedeIniciar();
        inicializada = true;
        return new BatallaCampo(crearJugadoresParaBatalla(), urlCampo, urlMusica);
    }

    public EstadoLobbyDTO obtenerEstado() {
        return new EstadoLobbyDTO(
                convertirJugadoresADTO(),
                inicializada,
                puedeIniciar(),
                MINIMO_JUGADORES,
                MAXIMO_JUGADORES
        );
    }

    private List<Jugador> crearJugadoresParaBatalla() {
        List<Jugador> jugadoresBatalla = new ArrayList<>();
        for (JugadorLobbyDTO jugador : jugadores.values()) {
            jugadoresBatalla.add(new Jugador(
                    jugador.getId(),
                    jugador.getNombre(),
                    jugador.getUrlAvatar(),
                    mapearGrupoDTOaEntidades(jugador.getGrupo()),
                    jugador.getEquipo()
            ));
        }
        return jugadoresBatalla;
    }

    private static LobbyDTO convertirEquiposALobby(EquiposDTO equiposDTO) {
        if (equiposDTO == null || equiposDTO.getJugadores() == null) {
            throw new IllegalArgumentException("La configuracion de equipos no puede ser nula.");
        }

        List<JugadorLobbyDTO> jugadoresLobby = new ArrayList<>();
        for (JugadorDTO jugador : equiposDTO.getJugadores()) {
            if (jugador != null) {
                jugadoresLobby.add(new JugadorLobbyDTO(
                        jugador.getId(),
                        jugador.getNombre(),
                        jugador.getUrlAvatar(),
                        jugador.getEquipo(),
                        jugador.getGrupo(),
                        true
                ));
            }
        }
        return new LobbyDTO(jugadoresLobby, equiposDTO.getUrlCampo(), equiposDTO.getUrlMusica());
    }

    private void validarLobbyAbierto() {
        if (inicializada) {
            throw new IllegalStateException("No se pueden unir jugadores cuando la partida ya inicio.");
        }
    }

    private void validarPuedeIniciar() {
        if (!puedeIniciar()) {
            throw new IllegalStateException("La partida requiere de 2 a 4 jugadores, todos listos y con grupos completos.");
        }
    }

    private boolean puedeIniciar() {
        return !inicializada
                && jugadores.size() >= MINIMO_JUGADORES
                && jugadores.size() <= MAXIMO_JUGADORES
                && todosListos()
                && todosConGrupoCompleto();
    }

    private boolean todosListos() {
        for (JugadorLobbyDTO jugador : jugadores.values()) {
            if (jugador == null || !jugador.isListo()) {
                return false;
            }
        }
        return true;
    }

    private boolean todosConGrupoCompleto() {
        for (JugadorLobbyDTO jugador : jugadores.values()) {
            if (jugador == null || jugador.getEquipo() == null || !grupoCompleto(jugador.getGrupo())) {
                return false;
            }
        }
        return true;
    }

    private boolean grupoCompleto(AnimatronicoDTO[] grupo) {
        if (grupo == null || grupo.length == 0) {
            return false;
        }
        for (AnimatronicoDTO animatronico : grupo) {
            if (animatronico == null || animatronico.getTipo() == null || animatronico.getHabilidades() == null
                    || animatronico.getHabilidades().length == 0) {
                return false;
            }
        }
        return true;
    }

    private List<JugadorDTO> convertirJugadoresADTO() {
        List<JugadorDTO> dtos = new ArrayList<>();
        for (JugadorLobbyDTO jugador : jugadores.values()) {
            dtos.add(new JugadorDTO(
                    jugador.getId(),
                    jugador.getNombre(),
                    jugador.getUrlAvatar(),
                    jugador.getGrupo(),
                    false,
                    jugador.getEquipo()
            ));
        }
        return dtos;
    }

    private Animatronico[] mapearGrupoDTOaEntidades(AnimatronicoDTO[] grupoDto) {
        if (grupoDto == null) {
            return new Animatronico[0];
        }
        Animatronico[] grupo = new Animatronico[grupoDto.length];
        for (int i = 0; i < grupoDto.length; i++) {
            AnimatronicoDTO aDto = grupoDto[i];
            if (aDto != null) {
                grupo[i] = new Animatronico(
                        aDto.getIdAnimatronico(),
                        aDto.isTurnoAnimatronico(),
                        aDto.getTipo(),
                        aDto.getFuerza(),
                        aDto.getArmadura(),
                        aDto.getVidaActual(),
                        aDto.getVidaTotal(),
                        mapearHabilidadesDTOaEntidades(aDto.getHabilidades())
                );
            }
        }
        return grupo;
    }

    private Habilidad[] mapearHabilidadesDTOaEntidades(HabilidadDTO[] habilidadesDto) {
        if (habilidadesDto == null) {
            return new Habilidad[0];
        }
        Habilidad[] habilidades = new Habilidad[habilidadesDto.length];
        for (int i = 0; i < habilidadesDto.length; i++) {
            HabilidadDTO hDto = habilidadesDto[i];
            if (hDto != null) {
                habilidades[i] = new Habilidad(hDto.getPoder(), hDto.getTipo(), hDto.getDescripcion());
            }
        }
        return habilidades;
    }
}
