package org.fnafworld.dominio.entidades;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.fnafworld.Equipo;
import org.fnafworld.dtos.*;
import org.fnafworld.ErrorLobby;

/**
 * * @author lagar
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
        if (urlCampo == null || urlCampo.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del campo no puede ser nula o vacía.");
        }
        if (urlMusica == null || urlMusica.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la música no puede ser nula o vacía.");
        }
        this.jugadores = new LinkedHashMap<>();
        this.urlCampo = urlCampo;
        this.urlMusica = urlMusica;
        this.inicializada = false;
    }

    public JugadorLobbyDTO unirsePartida(JugadorDTO dto) {
        if (dto == null || dto.getId() == null) {
            return mappearErrorJugador(dto, ErrorLobby.JUGADOR_INVALIDO);
        }
        if (inicializada) {
            return mappearErrorJugador(dto, ErrorLobby.PARTIDA_YA_INICIADA);
        }
        if (jugadores.size() >= MAXIMO_JUGADORES) {
            return mappearErrorJugador(dto, ErrorLobby.LOBBY_LLENO);
        }
        if (jugadores.containsKey(dto.getId())) {
            return mappearErrorJugador(dto, ErrorLobby.JUGADOR_YA_EN_LOBBY);
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
        if (idJugador == null) {
            JugadorDTO errorDto = new JugadorDTO(null, null, null, null, false, equipo);
            return mappearErrorJugador(errorDto, ErrorLobby.JUGADOR_INVALIDO);
        }
        
        JugadorLobbyDTO jugador = jugadores.get(idJugador);
        if (jugador == null) {
            JugadorDTO errorDto = new JugadorDTO(idJugador, null, null, null, false, equipo);
            return mappearErrorJugador(errorDto, ErrorLobby.JUGADOR_NO_EXISTE);
        }
        if (inicializada) {
            JugadorDTO errorDto = new JugadorDTO(jugador.getId(), jugador.getNombre(), jugador.getAvatar(), jugador.getGrupo(), false, jugador.getEquipo());
            return mappearErrorJugador(errorDto, ErrorLobby.PARTIDA_YA_INICIADA);
        }
        if (equipo == null) {
            JugadorDTO errorDto = new JugadorDTO(jugador.getId(), jugador.getNombre(), jugador.getAvatar(), jugador.getGrupo(), false, null);
            return mappearErrorJugador(errorDto, ErrorLobby.EQUIPO_INVALIDO);
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
        if (idJugador == null) {
            JugadorDTO errorDto = new JugadorDTO(null, null, null, grupo, false, null);
            return mappearErrorJugador(errorDto, ErrorLobby.JUGADOR_INVALIDO);
        }

        JugadorLobbyDTO jugador = jugadores.get(idJugador);
        if (jugador == null) {
            JugadorDTO errorDto = new JugadorDTO(idJugador, null, null, grupo, false, null);
            return mappearErrorJugador(errorDto, ErrorLobby.JUGADOR_NO_EXISTE);
        }
        if (jugador.getEquipo() == null) {
            JugadorDTO errorDto = new JugadorDTO(jugador.getId(), jugador.getNombre(), jugador.getAvatar(), grupo, false, null);
            return mappearErrorJugador(errorDto, ErrorLobby.EQUIPO_NO_SELECCIONADO);
        }
        if (listo && !grupoCompleto(grupo)) {
            JugadorDTO errorDto = new JugadorDTO(jugador.getId(), jugador.getNombre(), jugador.getAvatar(), grupo, false, jugador.getEquipo());
            return mappearErrorJugador(errorDto, ErrorLobby.GRUPO_INVALIDO);
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
        if (inicializada) {
            return new ResultadoAtaqueDTO(null, null, null, null, null, null, ErrorLobby.PARTIDA_YA_INICIADA);
        }
        if (jugadores.size() < MINIMO_JUGADORES) {
            return new ResultadoAtaqueDTO(null, null, null, null, null, null, ErrorLobby.JUGADORES_INSUFICIENTES);
        }
        if (!todosListos()) {
            return new ResultadoAtaqueDTO(null, null, null, null, null, null, ErrorLobby.JUGADORES_NO_LISTOS);
        }
        if (!todosConGrupoCompleto()) {
            return new ResultadoAtaqueDTO(null, null, null, null, null, null, ErrorLobby.GRUPO_INVALIDO);
        }

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
            return new ResultadoAtaqueDTO(null, null, null, null, null, null, ErrorLobby.BATALLA_NO_INICIADA);
        }
        if (ataque == null || ataque.getIdJugador() == null || ataque.getIdAnimatronico() == null || ataque.getTipoHabilidad() == null) {
            return new ResultadoAtaqueDTO(null, null, null, null, null, null, ErrorLobby.ATAQUE_INVALIDO);
        }
        
        batallaCampo.atacar(ataque.getIdJugador(), ataque.getIdAnimatronico(), ataque.getTipoHabilidad());
        return construirResultado();
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
            efectos,
            batallaCampo.getIdJugadorTurno(),
            batallaCampo.getEquipoGanador(),
            null
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
    
    private JugadorLobbyDTO mappearErrorJugador(JugadorDTO dto, ErrorLobby error) {
        String id = (dto != null) ? dto.getId() : null;
        String nombre = (dto != null) ? dto.getNombre() : null;
        byte[] avatar = (dto != null) ? dto.getAvatar() : null;
        Equipo equipo = (dto != null) ? dto.getEquipo() : null;
        AnimatronicoDTO[] grupo = (dto != null) ? dto.getGrupo() : null;
        
        return new JugadorLobbyDTO(id, nombre, avatar, equipo, grupo, false);
    }
}