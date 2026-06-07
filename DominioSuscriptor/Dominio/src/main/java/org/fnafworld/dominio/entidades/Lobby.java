package org.fnafworld.dominio.entidades;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.fnafworld.Equipo;
import org.fnafworld.dtos.*;
/**
 * 
 * @author lagar
 */
public class Lobby {
    public static final int MINIMO_JUGADORES = 2;
    public static final int MAXIMO_JUGADORES = 4;
    
    private final Map<String, JugadorLobbyDTO> jugadores;
    private final String urlCampo;
    private final String urlMusica;
    private boolean inicializada;
    private BatallaCampo batallaCampo;

    public Lobby(String urlCampo, String urlMusica) {
        this.jugadores = new LinkedHashMap<>();
        this.urlCampo = urlCampo;
        this.urlMusica = urlMusica;
        this.inicializada = false;
    }

    public JugadorLobbyDTO unirsePartida(JugadorDTO dto) {
        if (inicializada) {
            throw new IllegalStateException("No puedes unirte, la partida ya ha iniciado.");
        }
        if (jugadores.size() >= MAXIMO_JUGADORES) {
            throw new IllegalStateException("El lobby está lleno.");
        }
        if (jugadores.containsKey(dto.getId())) {
            throw new IllegalArgumentException("El jugador ya está en el lobby.");
        }

        JugadorLobbyDTO nuevoJugador = new JugadorLobbyDTO(
            dto.getId(), 
            dto.getNombre(), 
            dto.getAvatar(), 
            null, 
            new AnimatronicoDTO[0], 
            false
        );
        
        jugadores.put(dto.getId(), nuevoJugador);
        return nuevoJugador;
    }

    public JugadorLobbyDTO seleccionarEquipo(String idJugador, Equipo equipo) {
        JugadorLobbyDTO jugador = jugadores.get(idJugador);
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no existe en el lobby.");
        }
        if (inicializada) {
            throw new IllegalStateException("La partida ya inició.");
        }

        JugadorLobbyDTO actualizado = new JugadorLobbyDTO(
            jugador.getId(), 
            jugador.getNombre(), 
            jugador.getAvatar(), 
            equipo, 
            jugador.getGrupo(), 
            jugador.isListo()
        );
        
        jugadores.put(idJugador, actualizado);
        return actualizado;
    }

    public JugadorLobbyDTO establecerListo(String idJugador, AnimatronicoDTO[] grupo, boolean listo) {
        JugadorLobbyDTO jugador = jugadores.get(idJugador);
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no existe en el lobby.");
        }
        if (jugador.getEquipo() == null) {
            throw new IllegalStateException("Debes seleccionar un equipo antes de ponerte listo.");
        }

        JugadorLobbyDTO actualizado = new JugadorLobbyDTO(
            jugador.getId(), 
            jugador.getNombre(), 
            jugador.getAvatar(), 
            jugador.getEquipo(), 
            grupo, 
            listo
        );
        
        jugadores.put(idJugador, actualizado);
        return actualizado;
    }

    public EstadoLobbyDTO obtenerEstadoLobby() {
        List<JugadorDTO> listaJugadoresDTO = new ArrayList<>();
        for (JugadorLobbyDTO jl : jugadores.values()) {
            listaJugadoresDTO.add(new JugadorDTO(
                jl.getId(), 
                jl.getNombre(), 
                jl.getAvatar(), 
                jl.getGrupo(), 
                false, 
                jl.getEquipo()
            ));
        }
        return new EstadoLobbyDTO(
            listaJugadoresDTO, 
            inicializada, 
            puedeIniciar(), 
            MINIMO_JUGADORES, 
            MAXIMO_JUGADORES
        );
    }

    public ResultadoAtaqueDTO iniciarPartida() {
        validarPuedeIniciar();

        List<Jugador> jugadoresEntidad = new ArrayList<>();
        for (JugadorLobbyDTO jl : jugadores.values()) {
            Animatronico[] grupoEntidad = new Animatronico[jl.getGrupo().length];
            for (int i = 0; i < jl.getGrupo().length; i++) {
                AnimatronicoDTO dtoAnim = jl.getGrupo()[i];
                grupoEntidad[i] = mappearAEntidad(dtoAnim);
            }

            Jugador jugadorReal = new Jugador(
                jl.getId(),
                jl.getNombre(),
                jl.getAvatar(),
                grupoEntidad,
                jl.getEquipo()
            );
            jugadoresEntidad.add(jugadorReal);
        }

        this.batallaCampo = new BatallaCampo(jugadoresEntidad, urlCampo, urlMusica);
        this.inicializada = true;

        return construirResultado();
    }

    public ResultadoAtaqueDTO atacar(AtaqueDTO ataque) {
        if (!inicializada || batallaCampo == null) {
            throw new IllegalStateException("La batalla no se ha inicializado.");
        }
        batallaCampo.atacar(ataque.getIdJugador(), ataque.getIdAnimatronico(), ataque.getTipoHabilidad());
        return construirResultado();
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
            if (jugador == null || !jugador.isListo()) return false;
        }
        return true;
    }

    private boolean todosConGrupoCompleto() {
        for (JugadorLobbyDTO jugador : jugadores.values()) {
            if (jugador == null || jugador.getEquipo() == null || !grupoCompleto(jugador.getGrupo())) return false;
        }
        return true;
    }

    private boolean grupoCompleto(AnimatronicoDTO[] grupo) {
        if (grupo == null || grupo.length == 0) return false;
        for (AnimatronicoDTO animatronico : grupo) {
            if (animatronico == null || animatronico.getTipo() == null
                    || animatronico.getHabilidades() == null
                    || animatronico.getHabilidades().length == 0) return false;
        }
        return true;
    }

    private ResultadoAtaqueDTO construirResultado() {
        Jugador atacante = batallaCampo.getUltimoJugadorAtacante();
        Animatronico animAtacante = batallaCampo.getUltimoAnimatronicoAtacante();

        ParticipanteDTO atacanteDTO = null;
        if (atacante != null && animAtacante != null) {
            JugadorDTO jugadorAtacanteDTO = mappearJugadorDTO(atacante);
            AnimatronicoDTO animAtacanteDTO = mappearAnimatronicoDTO(animAtacante);
            atacanteDTO = new ParticipanteDTO(jugadorAtacanteDTO, animAtacanteDTO);
        }

        List<ParticipanteDTO> afectadosDTO = new ArrayList<>();
        for (Animatronico anim : batallaCampo.getUltimosAfectados()) {
            if (anim == animAtacante) continue;
            Jugador duenio = batallaCampo.encontrarJugadorDeAnimatronico(anim);
            if (duenio != null) {
                afectadosDTO.add(new ParticipanteDTO(mappearJugadorDTO(duenio), mappearAnimatronicoDTO(anim)));
            }
        }

        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        for (Jugador j : batallaCampo.getJugadores()) {
            jugadoresDTO.add(mappearJugadorDTO(j));
        }

        List<EfectoAnimatronicoDTO> efectos = new ArrayList<>();
        for (Jugador j : batallaCampo.getJugadores()) {
            for (Animatronico a : j.getGrupo()) {
                if (a != null) efectos.addAll(a.crearEfectosDTO(j.getId()));
            }
        }

        return new ResultadoAtaqueDTO(
            atacanteDTO,
            afectadosDTO,
            jugadoresDTO,
            batallaCampo.getIdJugadorTurno(),
            batallaCampo.getEquipoGanador(),
            efectos
        );
    }

    private JugadorDTO mappearJugadorDTO(Jugador jugador) {
        AnimatronicoDTO[] grupoDTO = new AnimatronicoDTO[jugador.getGrupo().length];
        for (int i = 0; i < jugador.getGrupo().length; i++) {
            grupoDTO[i] = mappearAnimatronicoDTO(jugador.getGrupo()[i]);
        }
        return new JugadorDTO(
            jugador.getId(),
            jugador.getNombre(),
            jugador.getAvatar(),
            grupoDTO,
            jugador.miTurno(),
            jugador.getEquipo()
        );
    }

    private AnimatronicoDTO mappearAnimatronicoDTO(Animatronico a) {
        if (a == null) return null;
        HabilidadDTO[] habilidadesDTO = new HabilidadDTO[a.getHabilidades().length];
        for (int i = 0; i < a.getHabilidades().length; i++) {
            Habilidad h = a.getHabilidades()[i];
            habilidadesDTO[i] = h != null
                ? new HabilidadDTO(h.getPoder(), h.getTipo(), h.getDescripcion())
                : null;
        }
        return new AnimatronicoDTO(
            a.getIdAnimatronico(),
            a.getTipo(),
            a.isTurnoAnimatronico(),
            a.getFuerza(),
            a.getArmadura(),
            a.getVidaActual(),
            a.getVidaTotal(),
            habilidadesDTO,
            a.sigueVivo()
        );
    }

    private Animatronico mappearAEntidad(AnimatronicoDTO dto) {
        Habilidad[] habilidadesEntidad = new Habilidad[dto.getHabilidades().length];
        for (int i = 0; i < dto.getHabilidades().length; i++) {
            HabilidadDTO hDto = dto.getHabilidades()[i];
            habilidadesEntidad[i] = new Habilidad(hDto.getPoder(), hDto.getTipo(), hDto.getDescripcion());
        }

        return new Animatronico(
            dto.getIdAnimatronico(),
            dto.isTurnoAnimatronico(),
            dto.getTipo(),
            dto.getFuerza(),
            dto.getArmadura(),
            dto.getVidaActual(),
            dto.getVidaTotal(),
            habilidadesEntidad
        );
    }
}