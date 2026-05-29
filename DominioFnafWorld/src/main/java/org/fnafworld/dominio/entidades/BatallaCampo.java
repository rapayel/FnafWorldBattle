package org.fnafworld.dominio.entidades;

import java.util.*;
import org.fnafworld.Equipo;
import org.fnafworld.TipoHabilidad;
import org.fnafworld.dtos.*;

public class BatallaCampo {
    private static final int CANTIDAD_MINIMA_JUGADORES = 2;
    private static final int CANTIDAD_MAXIMA_JUGADORES = 4;
    private static final int CANTIDAD_NORMAL_ANIMATRONICOS = 4;
    private static final int CANTIDAD_BALANCE_ANIMATRONICOS = 8;
    private static final int TURNOS_EFECTO_ESTADO = 8;
    private static final double ESCALA_TOXICIDAD = 0.10;
    private static final double VIDA_MAXIMA_WATERHOSE = 0.10;
    private static final double VIDA_MAXIMA_WATERHOSE2 = 0.20;
    private static final int DANIO_COSMICO = 10;
    private static final int AUMENTO_BIRTHDAY = 10;
    private static final int AUMENTO_POWER_SONG = 25;
    private static final int AUMENTO_ARMOR_SONG = 25;
    private static final int AUMENTO_SPEED_SONG = 45;
    private static final int DANIO_BASE_PIZZA_WHEEL = 95;
    private static final int DANIO_BASE_PIZZA_WHEEL2 = 140;
    private static final int CASTIGO_PIZZA_WHEEL = 30;
    private static final int CASTIGO_PIZZA_WHEEL2 = 60;
    private static final int CASTIGO_BASH_JAM = 55;
    private static final int TURNOS_NOQUEO = 1;
    private static final int DANIO_PASIVO_HOT_CHEESE = 18;
    private static final int DANIO_PASIVO_HOT_CHEESE2 = 30;
    private static final int DANIO_PASIVO_MUNCHIES = 14;
    private static final int DANIO_RETARDADO_POPPERS = 300;
    private static final int DANIO_BASE_EYE_BEAM = 280;
    private static final int DANIO_BASE_FREDDLES = 95;
    private static final int DANIO_BASE_MEGA_BITE = 135;
    private static final int DANIO_BASE_BALLOONS = 95;
    private static final int DANIO_PASIVO_MEGA_VIRUS = 14;
    private static final int DANIO_PASIVO_NEON_WALL2 = 35;
    private static final int DANIO_EXTRA_ENDO_ARMY = 10;
    private static final int AUMENTO_NEON_WALL = 80;
    private static final int AUMENTO_HOCUS_POCUS = 45;
    private static final double PROBABILIDAD_ESC_KEY = 0.03;
    private static final double PROBABILIDAD_GIFT_BOXES = 0.15;
    private static final double VIDA_REVIVIR_GIFT_BOXES = 0.50;
    private static final TipoHabilidad[] HABILIDADES_PRIZE_BALL = {
        TipoHabilidad.MicToss, TipoHabilidad.Hook, TipoHabilidad.Bite, TipoHabilidad.Bite2,
        TipoHabilidad.JackOBomb, TipoHabilidad.Buzzsaw, TipoHabilidad.Balloons2,
        TipoHabilidad.GloomBalloon, TipoHabilidad.GloomSong, TipoHabilidad.Sludge,
        TipoHabilidad.RainyDay, TipoHabilidad.RainyDay2, TipoHabilidad.Th4Wall,
        TipoHabilidad.Slasher, TipoHabilidad.Cupcake, TipoHabilidad.HappyJam,
        TipoHabilidad.HappyJam2, TipoHabilidad.PartyFavors, TipoHabilidad.RegenSong,
        TipoHabilidad.BubbleBreath
    };
    private static final TipoHabilidad[] HABILIDADES_PRIZE_BALL2 = {
        TipoHabilidad.ToxicBalloon, TipoHabilidad.ToxicBite, TipoHabilidad.ToxicBite2,
        TipoHabilidad.BadPizza, TipoHabilidad.PizzaWheel, TipoHabilidad.PizzaWheel2,
        TipoHabilidad.BashJam, TipoHabilidad.Jumpscare, TipoHabilidad.Jumscare,
        TipoHabilidad.HotCheese, TipoHabilidad.HotCheese2, TipoHabilidad.Munchies,
        TipoHabilidad.Poppers, TipoHabilidad.EyeBeam, TipoHabilidad.SpringLocks,
        TipoHabilidad.Freddles, TipoHabilidad.MegaBite, TipoHabilidad.Balloons,
        TipoHabilidad.MegaVirus, TipoHabilidad.NeonWall2, TipoHabilidad.Waterhose,
        TipoHabilidad.Waterhose2, TipoHabilidad.Unscrew, TipoHabilidad.Unscrew2,
        TipoHabilidad.Birthday, TipoHabilidad.CosmicSong, TipoHabilidad.PowerSong,
        TipoHabilidad.ArmorSong, TipoHabilidad.SpeedSong
    };
    private String urlCampo;
    private String urlMusica;
    private Jugador ganador;
    private List<Jugador> jugadores;
    private Jugador jugadorActual;
    private Random random;
    private int indiceJugadorActual;
    private int indiceAnimatronicoActual;
    private Jugador ultimoJugadorAtacante;
    private Animatronico ultimoAnimatronicoAtacante;
    private List<Animatronico> ultimosAfectados;

    public BatallaCampo(EquiposDTO equiposDTO) {
        if (equiposDTO == null || equiposDTO.getJugadores() == null) {
            throw new IllegalArgumentException("La configuración de equipos no puede ser nula.");
        }

        this.urlCampo = equiposDTO.getUrlCampo();
        this.urlMusica = equiposDTO.getUrlMusica();
        this.ganador = null;
        this.random = new Random();
        this.indiceJugadorActual = 0;
        this.indiceAnimatronicoActual = 0;
        this.jugadores = new ArrayList<>();
        this.ultimosAfectados = new ArrayList<>();

        for (JugadorDTO jDto : equiposDTO.getJugadores()) {
            if (jDto != null) {
                Animatronico[] grupoEntidad = mapearGrupoDTOaEntidades(jDto.getGrupo());
                Jugador jugador = new Jugador(
                        jDto.getId(),
                        jDto.getNombre(),
                        jDto.getUrlAvatar(),
                        grupoEntidad,
                        jDto.getEquipo()
                );
                this.jugadores.add(jugador);
            }
        }
        iniciarTurnos();
    }
    
    public ResultadoAtaqueDTO atacar(AtaqueDTO ataque) {
        Jugador jugadorEntidad = extraerJugador(ataque);

        if (jugadorEntidad != null && jugadorActual != null
                && jugadorEntidad.getId().equals(jugadorActual.getId())
                && jugadorEntidad.miTurno()) {

            Animatronico atacante = extraerAnimatronicoEnTurno(jugadorEntidad);
            Habilidad habilidad = buscarHabilidadSeleccionada(atacante, ataque.getTipoHabilidad());
            List<Animatronico> objetivosContrarios = extraerAnimatronicosContrarios(jugadorEntidad);
            List<Animatronico> objetivosAliados = extraerAnimatronicosAliados(jugadorEntidad);

            if (atacante != null && Objects.equals(ataque.getIdAnimatronico(), atacante.getIdAnimatronico())
                    && habilidad != null && esHabilidadAtaque(habilidad.getTipo())
                    && (!objetivosContrarios.isEmpty() || !objetivosAliados.isEmpty())) {
                Map<Animatronico, Integer> versionesAntes = new HashMap<>();
                for (Animatronico a : extraerAnimatronicosCampoCompleto()) {
                    versionesAntes.put(a, a.getEstadoVersion());
                }

                boolean repetirAtaque = habilidad.getTipo() != TipoHabilidad.MimicBall && atacante.consumirAtaqueDoble();
                ejecutarTipoAtaque(atacante, habilidad, objetivosContrarios, objetivosAliados);
                if (repetirAtaque && atacante.sigueVivo()) {
                    ejecutarTipoAtaque(atacante, habilidad, objetivosContrarios, objetivosAliados);
                }
                atacante.recibirDanioCosmicoPorAtaque();
                atacante.avanzarTurnoEfectos();
                verificarGanador();
                ultimoJugadorAtacante = jugadorEntidad;
                ultimoAnimatronicoAtacante = atacante;

                ultimosAfectados = new ArrayList<>();
                for (Map.Entry<Animatronico, Integer> entry : versionesAntes.entrySet()) {
                    if (entry.getKey().getEstadoVersion() != entry.getValue()) {
                        ultimosAfectados.add(entry.getKey());
                    }
                }

                avanzarTurno();
            }
        }

        return construirResultado();
    }

    public ResultadoAtaqueDTO construirResultado() {
        ParticipanteDTO atacanteDTO = null;
        if (ultimoJugadorAtacante != null && ultimoAnimatronicoAtacante != null) {
            atacanteDTO = new ParticipanteDTO(
                    convertirJugadorADTO(ultimoJugadorAtacante),
                    convertirAnimatronicoADTO(ultimoAnimatronicoAtacante)
            );
        }
        List<ParticipanteDTO> afectadosDTO = new ArrayList<>();
        if (ultimosAfectados != null) {
            for (Animatronico a : ultimosAfectados) {
                Jugador duenio = encontrarJugadorDeAnimatronico(a);
                if (duenio != null) {
                    afectadosDTO.add(new ParticipanteDTO(
                            convertirJugadorADTO(duenio),
                            convertirAnimatronicoADTO(a)
                    ));
                }
            }
        }

        JugadorDTO ganadorDTO = ganador != null ? convertirJugadorADTO(ganador) : null;
        String idJugadorTurno = jugadorActual != null ? jugadorActual.getId() : null;

        return new ResultadoAtaqueDTO(atacanteDTO, afectadosDTO, idJugadorTurno, ganadorDTO);
    }
    
    private JugadorDTO convertirJugadorADTO(Jugador jugador) {
        Animatronico[] grupo = jugador.getGrupo();
        AnimatronicoDTO[] grupoDTO = new AnimatronicoDTO[grupo != null ? grupo.length : 0];

        if (grupo != null) {
            for (int i = 0; i < grupo.length; i++) {
                Animatronico a = grupo[i];
                if (a != null) {
                    grupoDTO[i] = convertirAnimatronicoADTO(a);
                }
            }
        }

        return new JugadorDTO(
                jugador.getId(),
                jugador.getNombre(),
                jugador.getUrlAvatar(),
                grupoDTO,
                jugador.miTurno(),
                jugador.getEquipo()
        );
    }

    private AnimatronicoDTO convertirAnimatronicoADTO(Animatronico a) {
        HabilidadDTO[] habilidadesDTO = convertirHabilidadesADTO(a.getHabilidades());
        return new AnimatronicoDTO(
                a.getIdAnimatronico(),
                a.getTipo() != null ? a.getTipo() : null,
                a.isTurnoAnimatronico(),
                a.getFuerza(),
                a.getArmadura(),
                a.getVidaActual(),
                a.getVidaTotal(),
                habilidadesDTO,
                a.sigueVivo()
        );
    }

    private HabilidadDTO[] convertirHabilidadesADTO(Habilidad[] habilidades) {
        if (habilidades == null) {
            return new HabilidadDTO[0];
        }
        HabilidadDTO[] dtos = new HabilidadDTO[habilidades.length];
        for (int i = 0; i < habilidades.length; i++) {
            Habilidad h = habilidades[i];
            if (h != null) {
                dtos[i] = new HabilidadDTO(h.getPoder(), h.getTipo(), h.getDescripcion());
            }
        }
        return dtos;
    }

    private Jugador encontrarJugadorDeAnimatronico(Animatronico animatronico) {
        for (Jugador jugador : jugadores) {
            if (jugador == null || jugador.getGrupo() == null) {
                continue;
            }
            for (Animatronico a : jugador.getGrupo()) {
                if (a == animatronico) {
                    return jugador;
                }
            }
        }
        return null;
    }

    private Animatronico[] mapearGrupoDTOaEntidades(AnimatronicoDTO[] grupoDto) {
        if (grupoDto == null) {
            return new Animatronico[0];
        }
        Animatronico[] grupo = new Animatronico[grupoDto.length];
        for (int i = 0; i < grupoDto.length; i++) {
            AnimatronicoDTO aDto = grupoDto[i];
            if (aDto != null) {
                Habilidad[] habilidades = mapearHabilidadesDTOaEntidades(aDto.getHabilidades());
                grupo[i] = new Animatronico(
                        aDto.getIdAnimatronico(),
                        aDto.isTurnoAnimatronico(),
                        aDto.getTipo(),
                        aDto.getFuerza(),
                        aDto.getArmadura(),
                        aDto.getVidaActual(),
                        aDto.getVidaTotal(),
                        habilidades
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

    private void ejecutarTipoAtaque(Animatronico atacante, Habilidad habilidad, List<Animatronico> objetivosContrarios, List<Animatronico> objetivosAliados) {
        TipoHabilidad tipo = habilidad.getTipo();
        switch (tipo) {
            case MicToss:
                atacarAleatorio(objetivosContrarios, calcularDanio(random.nextInt(41) + 45, atacante, habilidad));
                break;
            case Hook:
                atacarAleatorio(objetivosContrarios, calcularDanio(random.nextInt(96) + 20, atacante, habilidad));
                break;
            case Bite:
                atacarAleatorio(objetivosContrarios, calcularDanio(95, atacante, habilidad));
                break;
            case Bite2:
                int danioBite2 = calcularDanioConEscala(55, atacante, habilidad, 0.60);
                atacarVariosAleatorios(objetivosContrarios, danioBite2, 3);
                break;
            case JackOBomb:
                atacarTodos(objetivosContrarios, calcularDanioConEscala(10, atacante, habilidad, 0.25));
                break;
            case Buzzsaw:
                int cantidadObjetivosBuzzsaw = random.nextInt(4) + 1;
                atacarVariosAleatorios(objetivosContrarios, calcularDanioConEscala(25, atacante, habilidad, 0.45), cantidadObjetivosBuzzsaw);
                break;
            case Balloons2:
                atacarAleatorio(objetivosContrarios, calcularDanio(130, atacante, habilidad));
                break;
            case PrizeBall:
                ejecutarHabilidadAleatoria(atacante, habilidad, objetivosContrarios, objetivosAliados, HABILIDADES_PRIZE_BALL);
                break;
            case PrizeBall2:
                ejecutarHabilidadAleatoria(atacante, habilidad, objetivosContrarios, objetivosAliados, HABILIDADES_PRIZE_BALL2);
                break;
            case MisteryBox:
                transformarEnAnimatronicoAleatorio(atacante, objetivosContrarios);
                break;
            case MisteryBox2:
                transformarAleatorioEnFormaAliada(objetivosContrarios, objetivosAliados);
                break;
            case EscKey:
                eliminarVariosAleatoriosPorProbabilidad(objetivosContrarios, PROBABILIDAD_ESC_KEY, 4);
                break;
            case Haunting:
                noquearVariosAleatorios(objetivosContrarios, 1);
                break;
            case EndoArmy:
                aumentarDanioAtaqueTodos(objetivosAliados, DANIO_EXTRA_ENDO_ARMY);
                break;
            case GiftBoxes:
                revivirAliadosAleatorios(atacante, 2, PROBABILIDAD_GIFT_BOXES);
                break;
            case NeonWall:
                atacante.activarNeonWall(AUMENTO_NEON_WALL, TURNOS_EFECTO_ESTADO);
                break;
            case MimicBall:
                activarMimicBallAliado(atacante, objetivosAliados);
                break;
            case HocusPocus:
                potenciarAliadosYSacrificar(atacante, objetivosAliados);
                break;
            case GloomBalloon:
                disminuirFuerzaAleatorio(objetivosContrarios, 45);
                break;
            case GloomSong:
                disminuirFuerzaTodos(objetivosContrarios, 20);
                break;
            case Sludge:
                quitarPoderHabilidadesAleatorio(objetivosContrarios);
                break;
            case RainyDay:
                disminuirArmaduraVariosAleatorios(objetivosContrarios, 35, 4);
                break;
            case RainyDay2:
                disminuirArmaduraTodos(objetivosContrarios, 15);
                atacarTodos(objetivosContrarios, calcularDanioConEscala(3, atacante, habilidad, 0.15));
                break;
            case Slasher:
                eliminarAleatorio(objetivosContrarios, 0.10);
                break;
            case Th4Wall:
                atacarVariosAleatorios(objetivosContrarios, calcularDanioConEscala(65, atacante, habilidad, 0.60), 6);
                break;
            case Cupcake:
                curarVariosAleatorios(objetivosAliados, calcularCuracion(70, atacante, habilidad, 0.40), 4);
                break;
            case HappyJam:
                curarVariosAleatorios(objetivosAliados, calcularCuracion(110, atacante, habilidad, 0.55), 2);
                break;
            case PartyFavors:
                curarPorcentajeTodos(objetivosAliados, 0.15);
                break;
            case HappyJam2:
                curarCompletoMasDanado(objetivosAliados);
                break;
            case RegenSong:
                regenerarTodos(objetivosAliados, calcularCuracion(8, atacante, habilidad, 0.08));
                break;
            case BubbleBreath:
                protegerToxicidadTodos(objetivosAliados);
                break;
            case ToxicBalloon:
                aplicarToxicidadVariosAleatorios(objetivosContrarios, calcularDanioConEscala(8, atacante, habilidad, ESCALA_TOXICIDAD), 5);
                break;
            case ToxicBite:
                atacarConToxicidadAleatorio(
                        objetivosContrarios,
                        calcularDanio(95, atacante, habilidad),
                        calcularDanioConEscala(10, atacante, habilidad, ESCALA_TOXICIDAD)
                );
                break;
            case ToxicBite2:
                atacarConToxicidadVariosAleatorios(
                        objetivosContrarios,
                        calcularDanioConEscala(45, atacante, habilidad, 0.45),
                        calcularDanioConEscala(10, atacante, habilidad, ESCALA_TOXICIDAD),
                        3
                );
                break;
            case BadPizza:
                aplicarToxicidadTodos(objetivosContrarios, calcularDanioConEscala(10, atacante, habilidad, ESCALA_TOXICIDAD));
                break;
            case Waterhose:
                eliminarPorVidaMaximaAleatorios(objetivosContrarios, VIDA_MAXIMA_WATERHOSE, 3);
                break;
            case Waterhose2:
                eliminarPorVidaMaximaAleatorios(objetivosContrarios, VIDA_MAXIMA_WATERHOSE2, 2);
                break;
            case Unscrew:
                eliminarVariosAleatoriosPorProbabilidad(objetivosContrarios, 0.30, 1);
                break;
            case Unscrew2:
                eliminarVariosAleatoriosPorProbabilidad(objetivosContrarios, 0.10, 2);
                break;
            case Birthday:
                aumentarFuerzaYArmaduraVariosAleatorios(objetivosAliados, AUMENTO_BIRTHDAY, AUMENTO_BIRTHDAY, 4);
                break;
            case CosmicSong:
                aplicarDanioCosmicoTodos(objetivosContrarios);
                break;
            case PowerSong:
                aumentarPoderTodos(objetivosAliados, AUMENTO_POWER_SONG);
                break;
            case ArmorSong:
                aumentarArmaduraTodos(objetivosAliados, AUMENTO_ARMOR_SONG);
                break;
            case SpeedSong:
                aumentarFuerzaYArmaduraAleatorio(objetivosAliados, AUMENTO_SPEED_SONG, AUMENTO_SPEED_SONG);
                break;
            case PizzaWheel:
                atacarTodos(objetivosContrarios, calcularDanio(DANIO_BASE_PIZZA_WHEEL, atacante, habilidad));
                castigarAtacanteYAliados(atacante, objetivosAliados, CASTIGO_PIZZA_WHEEL, 3);
                break;
            case PizzaWheel2:
                atacarTodos(objetivosContrarios, calcularDanio(DANIO_BASE_PIZZA_WHEEL2, atacante, habilidad));
                castigarAtacanteYAliados(atacante, objetivosAliados, CASTIGO_PIZZA_WHEEL2, 3);
                break;
            case BashJam:
                atacarVariosAleatorios(objetivosContrarios, calcularDanioConEscala(120, atacante, habilidad, 0.85), 4);
                atacante.recibirDanioDirecto(CASTIGO_BASH_JAM);
                break;
            case Jumpscare:
            case Jumscare:
                noquearVariosAleatorios(objetivosContrarios, 3);
                break;
            case HotCheese:
                aplicarDanioPasivoVariosAleatorios(objetivosContrarios, DANIO_PASIVO_HOT_CHEESE, 3);
                atacante.aplicarDanioPasivo(DANIO_PASIVO_HOT_CHEESE, TURNOS_EFECTO_ESTADO);
                break;
            case HotCheese2:
                aplicarDanioPasivoVariosAleatorios(objetivosContrarios, DANIO_PASIVO_HOT_CHEESE2, 3);
                atacante.aplicarDanioPasivo(DANIO_PASIVO_HOT_CHEESE2, TURNOS_EFECTO_ESTADO);
                break;
            case Munchies:
                aplicarDanioPasivoTodos(objetivosContrarios, DANIO_PASIVO_MUNCHIES);
                aplicarDanioPasivoVariosAleatorios(objetivosAliados, DANIO_PASIVO_MUNCHIES, 4);
                break;
            case Poppers:
                aplicarDanioRetardadoVariosAleatorios(objetivosContrarios, DANIO_RETARDADO_POPPERS, 2);
                break;
            case EyeBeam:
                atacarAleatorioConRetroceso(atacante, objetivosContrarios, calcularDanio(DANIO_BASE_EYE_BEAM, atacante, habilidad), 0.50);
                break;
            case SpringLocks:
                quitarPorcentajeVidaAleatorio(extraerAnimatronicosCampo(objetivosContrarios, objetivosAliados), 0.90);
                break;
            case Freddles:
                atacarTodos(extraerAnimatronicosCampo(objetivosContrarios, objetivosAliados), calcularDanio(DANIO_BASE_FREDDLES, atacante, habilidad));
                break;
            case MegaBite:
                atacarTodos(objetivosContrarios, calcularDanioConEscala(DANIO_BASE_MEGA_BITE, atacante, habilidad, 0.85));
                atacante.sacrificar();
                break;
            case Balloons:
                atacarVariosAleatoriosConRetroceso(atacante, objetivosContrarios, calcularDanio(DANIO_BASE_BALLOONS, atacante, habilidad), 3, 0.50);
                break;
            case MegaVirus:
                aplicarToxicidadTodos(extraerAnimatronicosCampo(objetivosContrarios, objetivosAliados), DANIO_PASIVO_MEGA_VIRUS);
                break;
            case NeonWall2:
                aplicarNeonWall2Aleatorio(objetivosAliados);
                break;
            default:
                break;
        }
    }
    
    private void atacarAleatorio(List<Animatronico> objetivosContrarios, int danio) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosContrarios);
        if (objetivo != null) {
            aplicarDanio(objetivo, danio);
        }
    }

    private void atacarVariosAleatorios(List<Animatronico> objetivosContrarios, int danio, int cantidadAtaques) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        for (int i = 0; i < cantidadAtaques && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            aplicarDanio(objetivo, danio);
        }
    }

    private void atacarTodos(List<Animatronico> objetivosContrarios, int danio) {
        for (Animatronico objetivo : objetivosContrarios) {
            aplicarDanio(objetivo, danio);
        }
    }

    private void disminuirFuerzaAleatorio(List<Animatronico> objetivosContrarios, int cantidad) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosContrarios);
        if (objetivo != null) {
            objetivo.disminuirFuerza(cantidad, TURNOS_EFECTO_ESTADO);
        }
    }

    private void disminuirFuerzaTodos(List<Animatronico> objetivosContrarios, int cantidad) {
        for (Animatronico objetivo : objetivosContrarios) {
            objetivo.disminuirFuerza(cantidad, TURNOS_EFECTO_ESTADO);
        }
    }

    private void quitarPoderHabilidadesAleatorio(List<Animatronico> objetivosContrarios) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosContrarios);
        if (objetivo != null) {
            objetivo.quitarPoderHabilidades(TURNOS_EFECTO_ESTADO);
        }
    }

    private void disminuirArmaduraVariosAleatorios(List<Animatronico> objetivosContrarios, int cantidad, int cantidadObjetivos) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        for (int i = 0; i < cantidadObjetivos && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            objetivo.disminuirArmadura(cantidad, TURNOS_EFECTO_ESTADO);
        }
    }

    private void disminuirArmaduraTodos(List<Animatronico> objetivosContrarios, int cantidad) {
        for (Animatronico objetivo : objetivosContrarios) {
            objetivo.disminuirArmadura(cantidad, TURNOS_EFECTO_ESTADO);
        }
    }

    private void eliminarAleatorio(List<Animatronico> objetivosContrarios, double probabilidad) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosContrarios);
        if (objetivo != null && random.nextDouble() < probabilidad) {
            objetivo.eliminar();
        }
    }

    private void curarVariosAleatorios(List<Animatronico> objetivosAliados, int curacion, int cantidadObjetivos) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosAliados);
        for (int i = 0; i < cantidadObjetivos && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            objetivo.curar(curacion);
        }
    }

    private void curarPorcentajeTodos(List<Animatronico> objetivosAliados, double porcentaje) {
        for (Animatronico objetivo : objetivosAliados) {
            int curacion = (int) Math.round(objetivo.getVidaActual() * porcentaje);
            objetivo.curar(curacion);
        }
    }

    private void curarCompletoMasDanado(List<Animatronico> objetivosAliados) {
        Animatronico masDanado = null;
        for (Animatronico objetivo : objetivosAliados) {
            if (masDanado == null || objetivo.getVidaFaltante() > masDanado.getVidaFaltante()) {
                masDanado = objetivo;
            }
        }
        if (masDanado != null) {
            masDanado.curarCompleto();
        }
    }

    private void regenerarTodos(List<Animatronico> objetivosAliados, int curacionPorTurno) {
        for (Animatronico objetivo : objetivosAliados) {
            objetivo.regenerarVida(curacionPorTurno, TURNOS_EFECTO_ESTADO);
        }
    }

    private void protegerToxicidadTodos(List<Animatronico> objetivosAliados) {
        for (Animatronico objetivo : objetivosAliados) {
            objetivo.protegerToxicidad(TURNOS_EFECTO_ESTADO);
        }
    }

    private void aplicarToxicidadVariosAleatorios(List<Animatronico> objetivosContrarios, int danioPorTurno, int cantidadObjetivos) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        for (int i = 0; i < cantidadObjetivos && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            extraerProtectorNeonWall(objetivo).aplicarToxicidad(danioPorTurno, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aplicarToxicidadTodos(List<Animatronico> objetivosContrarios, int danioPorTurno) {
        for (Animatronico objetivo : objetivosContrarios) {
            extraerProtectorNeonWall(objetivo).aplicarToxicidad(danioPorTurno, TURNOS_EFECTO_ESTADO);
        }
    }

    private void atacarConToxicidadAleatorio(List<Animatronico> objetivosContrarios, int danio, int danioToxicidad) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosContrarios);
        if (objetivo != null) {
            aplicarDanio(objetivo, danio);
            if (objetivo.sigueVivo()) {
                extraerProtectorNeonWall(objetivo).aplicarToxicidad(danioToxicidad, TURNOS_EFECTO_ESTADO);
            }
        }
    }

    private void atacarConToxicidadVariosAleatorios(List<Animatronico> objetivosContrarios, int danio, int danioToxicidad, int cantidadObjetivos) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        for (int i = 0; i < cantidadObjetivos && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            aplicarDanio(objetivo, danio);
            if (objetivo.sigueVivo()) {
                extraerProtectorNeonWall(objetivo).aplicarToxicidad(danioToxicidad, TURNOS_EFECTO_ESTADO);
            }
        }
    }

    private void eliminarPorVidaMaximaAleatorios(List<Animatronico> objetivosContrarios, double porcentajeVida, int cantidadMaxima) {
        List<Animatronico> objetivos = new ArrayList<>();
        for (Animatronico objetivo : objetivosContrarios) {
            if (objetivo.getVidaActual() <= objetivo.getVidaTotal() * porcentajeVida) {
                objetivos.add(objetivo);
            }
        }
        eliminarVariosAleatorios(objetivos, cantidadMaxima);
    }

    private void eliminarVariosAleatoriosPorProbabilidad(List<Animatronico> objetivosContrarios, double probabilidad, int cantidadMaxima) {
        if (random.nextDouble() < probabilidad) {
            eliminarVariosAleatorios(objetivosContrarios, cantidadMaxima);
        }
    }

    private void eliminarVariosAleatorios(List<Animatronico> objetivosContrarios, int cantidadMaxima) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        for (int i = 0; i < cantidadMaxima && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            objetivo.eliminar();
        }
    }

    private void aumentarFuerzaYArmaduraVariosAleatorios(List<Animatronico> objetivosAliados, int fuerza, int armadura, int cantidadObjetivos) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosAliados);
        for (int i = 0; i < cantidadObjetivos && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            objetivo.aumentarFuerzaYArmadura(fuerza, armadura, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aumentarFuerzaYArmaduraAleatorio(List<Animatronico> objetivosAliados, int fuerza, int armadura) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosAliados);
        if (objetivo != null) {
            objetivo.aumentarFuerzaYArmadura(fuerza, armadura, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aplicarDanioCosmicoTodos(List<Animatronico> objetivosContrarios) {
        for (Animatronico objetivo : objetivosContrarios) {
            objetivo.aplicarDanioCosmico(DANIO_COSMICO, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aumentarPoderTodos(List<Animatronico> objetivosAliados, int cantidad) {
        for (Animatronico objetivo : objetivosAliados) {
            objetivo.aumentarPoderHabilidades(cantidad, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aumentarArmaduraTodos(List<Animatronico> objetivosAliados, int cantidad) {
        for (Animatronico objetivo : objetivosAliados) {
            objetivo.aumentarArmadura(cantidad, TURNOS_EFECTO_ESTADO);
        }
    }

    private void castigarAtacanteYAliados(Animatronico atacante, List<Animatronico> objetivosAliados, int danio, int cantidadAliados) {
        atacante.recibirDanioDirecto(danio);

        List<Animatronico> aliados = new ArrayList<>(objetivosAliados);
        aliados.remove(atacante);
        for (int i = 0; i < cantidadAliados && !aliados.isEmpty(); i++) {
            int indice = random.nextInt(aliados.size());
            Animatronico objetivo = aliados.remove(indice);
            objetivo.recibirDanioDirecto(danio);
        }
    }

    private void noquearVariosAleatorios(List<Animatronico> objetivosContrarios, int cantidadObjetivos) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        for (int i = 0; i < cantidadObjetivos && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            objetivo.noquear(TURNOS_NOQUEO);
        }
    }

    private void aplicarDanioPasivoVariosAleatorios(List<Animatronico> objetivos, int danioPorTurno, int cantidadObjetivos) {
        List<Animatronico> candidatos = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidadObjetivos && !candidatos.isEmpty(); i++) {
            int indice = random.nextInt(candidatos.size());
            Animatronico objetivo = candidatos.remove(indice);
            extraerProtectorNeonWall(objetivo).aplicarDanioPasivo(danioPorTurno, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aplicarDanioPasivoTodos(List<Animatronico> objetivos, int danioPorTurno) {
        for (Animatronico objetivo : objetivos) {
            extraerProtectorNeonWall(objetivo).aplicarDanioPasivo(danioPorTurno, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aplicarDanioRetardadoVariosAleatorios(List<Animatronico> objetivosContrarios, int danio, int cantidadObjetivos) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        for (int i = 0; i < cantidadObjetivos && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            extraerProtectorNeonWall(objetivo).aplicarDanioRetardado(danio, TURNOS_EFECTO_ESTADO);
        }
    }

    private void atacarAleatorioConRetroceso(Animatronico atacante, List<Animatronico> objetivosContrarios, int danio, double porcentajeRetroceso) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosContrarios);
        if (objetivo != null) {
            int danioRecibido = aplicarDanioYObtener(objetivo, danio);
            atacante.recibirDanioDirecto((int) Math.ceil(danioRecibido * porcentajeRetroceso));
        }
    }

    private void atacarVariosAleatoriosConRetroceso(Animatronico atacante, List<Animatronico> objetivosContrarios, int danio, int cantidadAtaques, double porcentajeRetroceso) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        int retrocesoTotal = 0;
        for (int i = 0; i < cantidadAtaques && !objetivos.isEmpty(); i++) {
            int indice = random.nextInt(objetivos.size());
            Animatronico objetivo = objetivos.remove(indice);
            int danioRecibido = aplicarDanioYObtener(objetivo, danio);
            retrocesoTotal += (int) Math.ceil(danioRecibido * porcentajeRetroceso);
        }
        if (retrocesoTotal > 0) {
            atacante.recibirDanioDirecto(retrocesoTotal);
        }
    }

    private void quitarPorcentajeVidaAleatorio(List<Animatronico> objetivos, double porcentaje) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivos);
        if (objetivo != null) {
            extraerProtectorNeonWall(objetivo).quitarPorcentajeVida(porcentaje);
        }
    }

    private void aplicarNeonWall2Aleatorio(List<Animatronico> objetivosAliados) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosAliados);
        if (objetivo != null) {
            objetivo.hacerInvencible(TURNOS_EFECTO_ESTADO);
            objetivo.aplicarDanioPasivoSevero(DANIO_PASIVO_NEON_WALL2, TURNOS_EFECTO_ESTADO);
        }
    }

    private List<Animatronico> extraerAnimatronicosCampo(List<Animatronico> objetivosContrarios, List<Animatronico> objetivosAliados) {
        List<Animatronico> objetivos = new ArrayList<>(objetivosContrarios);
        objetivos.addAll(objetivosAliados);
        return objetivos;
    }

    private void ejecutarHabilidadAleatoria(Animatronico atacante, Habilidad habilidad, List<Animatronico> objetivosContrarios, List<Animatronico> objetivosAliados, TipoHabilidad[] opciones) {
        if (opciones.length == 0) {
            return;
        }
        TipoHabilidad tipoElegido = opciones[random.nextInt(opciones.length)];
        ejecutarTipoAtaque(atacante, new Habilidad(habilidad.getPoder(), tipoElegido, habilidad.getDescripcion()), objetivosContrarios, objetivosAliados);
    }

    private void transformarEnAnimatronicoAleatorio(Animatronico atacante, List<Animatronico> objetivosContrarios) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivosContrarios);
        if (objetivo != null) {
            atacante.copiarFormaDe(objetivo);
        }
    }

    private void transformarAleatorioEnFormaAliada(List<Animatronico> objetivosContrarios, List<Animatronico> objetivosAliados) {
        Animatronico rival = extraerAnimatronicoAleatorio(objetivosContrarios);
        Animatronico aliado = extraerAnimatronicoAleatorio(objetivosAliados);
        if (rival != null && aliado != null) {
            rival.copiarFormaDe(aliado);
        }
    }

    private void aumentarDanioAtaqueTodos(List<Animatronico> objetivosAliados, int cantidad) {
        for (Animatronico objetivo : objetivosAliados) {
            objetivo.aumentarDanioAtaque(cantidad, TURNOS_EFECTO_ESTADO);
        }
    }

    private void revivirAliadosAleatorios(Animatronico atacante, int cantidadObjetivos, double probabilidad) {
        if (random.nextDouble() >= probabilidad) {
            return;
        }
        Jugador jugador = encontrarJugadorDeAnimatronico(atacante);
        List<Animatronico> derrotados = extraerAnimatronicosAliadosDerrotados(jugador);
        for (int i = 0; i < cantidadObjetivos && !derrotados.isEmpty(); i++) {
            int indice = random.nextInt(derrotados.size());
            Animatronico objetivo = derrotados.remove(indice);
            objetivo.revivir((int) Math.ceil(objetivo.getVidaTotal() * VIDA_REVIVIR_GIFT_BOXES));
        }
    }

    private void activarMimicBallAliado(Animatronico atacante, List<Animatronico> objetivosAliados) {
        List<Animatronico> aliados = new ArrayList<>(objetivosAliados);
        aliados.remove(atacante);
        Animatronico objetivo = extraerAnimatronicoAleatorio(aliados.isEmpty() ? objetivosAliados : aliados);
        if (objetivo != null) {
            objetivo.activarAtaqueDoble();
        }
    }

    private void potenciarAliadosYSacrificar(Animatronico atacante, List<Animatronico> objetivosAliados) {
        for (Animatronico objetivo : objetivosAliados) {
            if (objetivo != atacante) {
                objetivo.aumentarFuerzaYArmadura(AUMENTO_HOCUS_POCUS, AUMENTO_HOCUS_POCUS, TURNOS_EFECTO_ESTADO);
                objetivo.aumentarPoderHabilidades(AUMENTO_HOCUS_POCUS, TURNOS_EFECTO_ESTADO);
            }
        }
        atacante.sacrificar();
    }

    private void aplicarDanio(Animatronico objetivo, int danio) {
        aplicarDanioYObtener(objetivo, danio);
    }

    private int aplicarDanioYObtener(Animatronico objetivo, int danio) {
        if (objetivo == null) {
            return 0;
        }
        Animatronico receptor = extraerProtectorNeonWall(objetivo);
        return receptor.recibirDanioYObtener(danio);
    }

    private Animatronico extraerProtectorNeonWall(Animatronico objetivo) {
        Jugador duenio = encontrarJugadorDeAnimatronico(objetivo);
        if (duenio == null || duenio.getGrupo() == null) {
            return objetivo;
        }
        for (Animatronico aliado : duenio.getGrupo()) {
            if (aliado != null && aliado != objetivo && aliado.protegeConNeonWall()) {
                return aliado;
            }
        }
        return objetivo;
    }
    
    private Animatronico extraerAnimatronicoAleatorio(List<Animatronico> animatronicos) {
        if (animatronicos.isEmpty()) {
            return null;
        }
        return animatronicos.get(random.nextInt(animatronicos.size()));
    }

    private List<Animatronico> extraerAnimatronicosVivos(Animatronico[] animatronicos) {
        List<Animatronico> vivos = new ArrayList<>();
        if (animatronicos == null) {
            return vivos;
        }
        for (Animatronico animatronico : animatronicos) {
            if (animatronico != null && animatronico.sigueVivo()) {
                vivos.add(animatronico);
            }
        }
        return vivos;
    }

    private List<Animatronico> extraerAnimatronicosCampoCompleto() {
        List<Animatronico> animatronicos = new ArrayList<>();
        if (jugadores == null) {
            return animatronicos;
        }
        for (Jugador jugador : jugadores) {
            if (jugador == null || jugador.getGrupo() == null) {
                continue;
            }
            for (Animatronico animatronico : jugador.getGrupo()) {
                if (animatronico != null) {
                    animatronicos.add(animatronico);
                }
            }
        }
        return animatronicos;
    }

    private int calcularDanio(int danioBase, Animatronico atacante, Habilidad habilidad) {
        return danioBase + atacante.getFuerzaActual() + atacante.getPoderHabilidad(habilidad) + atacante.getDanioExtraAtaque();
    }

    private int calcularDanioConEscala(int danioBase, Animatronico atacante, Habilidad habilidad, double escalaAtributos) {
        int atributos = atacante.getFuerzaActual() + atacante.getPoderHabilidad(habilidad);
        return danioBase + (int) Math.round(atributos * escalaAtributos) + atacante.getDanioExtraAtaque();
    }

    private int calcularCuracion(int curacionBase, Animatronico atacante, Habilidad habilidad, double escalaAtributos) {
        int atributos = atacante.getFuerzaActual() + atacante.getPoderHabilidad(habilidad);
        return Math.max(1, curacionBase + (int) Math.round(atributos * escalaAtributos));
    }

    private Jugador extraerJugador(AtaqueDTO ataque) {
        if (ataque == null) {
            return null;
        }
        for (Jugador jugadorEntidad : jugadores) {
            if (jugadorEntidad != null && jugadorEntidad.getId().equals(ataque.getIdJugador())) {
                return jugadorEntidad;
            }
        }
        return null;
    }

    private Animatronico extraerAnimatronicoEnTurno(Jugador jugador) {
        if (jugador.getGrupo() == null) {
            return null;
        }
        for (Animatronico animatronico : jugador.getGrupo()) {
            if (animatronico != null && animatronico.isTurnoAnimatronico() && animatronico.sigueVivo()) {
                return animatronico;
            }
        }
        return null;
    }

    private boolean esHabilidadAtaque(TipoHabilidad tipo) {
        return tipo == TipoHabilidad.MicToss
                || tipo == TipoHabilidad.Hook
                || tipo == TipoHabilidad.Bite
                || tipo == TipoHabilidad.Bite2
                || tipo == TipoHabilidad.JackOBomb
                || tipo == TipoHabilidad.Buzzsaw
                || tipo == TipoHabilidad.Balloons2
                || tipo == TipoHabilidad.PrizeBall
                || tipo == TipoHabilidad.PrizeBall2
                || tipo == TipoHabilidad.MisteryBox
                || tipo == TipoHabilidad.MisteryBox2
                || tipo == TipoHabilidad.EscKey
                || tipo == TipoHabilidad.Haunting
                || tipo == TipoHabilidad.EndoArmy
                || tipo == TipoHabilidad.GiftBoxes
                || tipo == TipoHabilidad.NeonWall
                || tipo == TipoHabilidad.MimicBall
                || tipo == TipoHabilidad.HocusPocus
                || tipo == TipoHabilidad.GloomBalloon
                || tipo == TipoHabilidad.GloomSong
                || tipo == TipoHabilidad.Sludge
                || tipo == TipoHabilidad.RainyDay
                || tipo == TipoHabilidad.RainyDay2
                || tipo == TipoHabilidad.Slasher
                || tipo == TipoHabilidad.Th4Wall
                || tipo == TipoHabilidad.Cupcake
                || tipo == TipoHabilidad.HappyJam
                || tipo == TipoHabilidad.PartyFavors
                || tipo == TipoHabilidad.HappyJam2
                || tipo == TipoHabilidad.RegenSong
                || tipo == TipoHabilidad.BubbleBreath
                || tipo == TipoHabilidad.ToxicBalloon
                || tipo == TipoHabilidad.ToxicBite
                || tipo == TipoHabilidad.ToxicBite2
                || tipo == TipoHabilidad.BadPizza
                || tipo == TipoHabilidad.Waterhose
                || tipo == TipoHabilidad.Waterhose2
                || tipo == TipoHabilidad.Unscrew
                || tipo == TipoHabilidad.Unscrew2
                || tipo == TipoHabilidad.Birthday
                || tipo == TipoHabilidad.CosmicSong
                || tipo == TipoHabilidad.PowerSong
                || tipo == TipoHabilidad.ArmorSong
                || tipo == TipoHabilidad.SpeedSong
                || tipo == TipoHabilidad.PizzaWheel
                || tipo == TipoHabilidad.PizzaWheel2
                || tipo == TipoHabilidad.BashJam
                || tipo == TipoHabilidad.Jumpscare
                || tipo == TipoHabilidad.Jumscare
                || tipo == TipoHabilidad.HotCheese
                || tipo == TipoHabilidad.HotCheese2
                || tipo == TipoHabilidad.Munchies
                || tipo == TipoHabilidad.Poppers
                || tipo == TipoHabilidad.EyeBeam
                || tipo == TipoHabilidad.SpringLocks
                || tipo == TipoHabilidad.Freddles
                || tipo == TipoHabilidad.MegaBite
                || tipo == TipoHabilidad.Balloons
                || tipo == TipoHabilidad.MegaVirus
                || tipo == TipoHabilidad.NeonWall2;
    }

    private List<Animatronico> extraerAnimatronicosContrarios(Jugador jugador) {
        List<Animatronico> animatronicosContrarios = new ArrayList<>();
        if (jugador.getEquipo() == null) {
            return animatronicosContrarios;
        }
        for (Jugador jugadorEntidad : jugadores) {
            if (jugadorEntidad != null && jugadorEntidad.getEquipo() != null
                    && !jugadorEntidad.getEquipo().equals(jugador.getEquipo())) {
                animatronicosContrarios.addAll(extraerAnimatronicosVivos(jugadorEntidad.getGrupo()));
            }
        }
        return animatronicosContrarios;
    }

    private List<Animatronico> extraerAnimatronicosAliados(Jugador jugador) {
        List<Animatronico> animatronicosAliados = new ArrayList<>();
        if (jugador.getEquipo() == null) {
            return animatronicosAliados;
        }
        for (Jugador jugadorEntidad : jugadores) {
            if (jugadorEntidad != null && jugadorEntidad.getEquipo() != null
                    && jugadorEntidad.getEquipo().equals(jugador.getEquipo())) {
                animatronicosAliados.addAll(extraerAnimatronicosVivos(jugadorEntidad.getGrupo()));
            }
        }
        return animatronicosAliados;
    }

    private List<Animatronico> extraerAnimatronicosAliadosDerrotados(Jugador jugador) {
        List<Animatronico> animatronicosAliados = new ArrayList<>();
        if (jugador == null || jugador.getEquipo() == null) {
            return animatronicosAliados;
        }
        for (Jugador jugadorEntidad : jugadores) {
            if (jugadorEntidad != null && jugadorEntidad.getEquipo() != null
                    && jugadorEntidad.getEquipo().equals(jugador.getEquipo())
                    && jugadorEntidad.getGrupo() != null) {
                for (Animatronico animatronico : jugadorEntidad.getGrupo()) {
                    if (animatronico != null && !animatronico.sigueVivo()) {
                        animatronicosAliados.add(animatronico);
                    }
                }
            }
        }
        return animatronicosAliados;
    }

    private void iniciarTurnos() {
        if (!cantidadJugadoresValida()) {
            jugadorActual = null;
            return;
        }
        aplicarTurnoActual();
    }

    private void avanzarTurno() {
        if (!cantidadJugadoresValida()) {
            jugadorActual = null;
            return;
        }

        int mayorCantidadAnimatronicos = obtenerMayorCantidadAnimatronicos();
        if (mayorCantidadAnimatronicos == 0) {
            limpiarTurnos();
            jugadorActual = null;
            return;
        }

        int intentos = jugadores.size() * mayorCantidadAnimatronicos;
        Set<Animatronico> noqueadosSaltados = new HashSet<>();
        do {
            indiceJugadorActual++;
            if (indiceJugadorActual >= jugadores.size()) {
                indiceJugadorActual = 0;
                indiceAnimatronicoActual++;
                if (indiceAnimatronicoActual >= mayorCantidadAnimatronicos) {
                    indiceAnimatronicoActual = 0;
                }
            }
            intentos--;
        } while (intentos > 0 && !turnoDisponibleParaAvance(indiceJugadorActual, indiceAnimatronicoActual, noqueadosSaltados));

        aplicarTurnoActual();
    }

    private void aplicarTurnoActual() {
        limpiarTurnos();

        if (!turnoDisponible(indiceJugadorActual, indiceAnimatronicoActual)) {
            jugadorActual = null;
            return;
        }

        jugadorActual = jugadores.get(indiceJugadorActual);
        jugadorActual.setMiTurno(true);
        jugadorActual.getGrupo()[indiceAnimatronicoActual].setTurnoAnimatronico(true);
    }

    private void limpiarTurnos() {
        if (jugadores == null) {
            return;
        }
        for (Jugador jugador : jugadores) {
            if (jugador != null) {
                jugador.setMiTurno(false);
                limpiarTurnosAnimatronicos(jugador);
            }
        }
    }

    private void limpiarTurnosAnimatronicos(Jugador jugador) {
        if (jugador.getGrupo() == null) {
            return;
        }
        for (Animatronico animatronico : jugador.getGrupo()) {
            if (animatronico != null) {
                animatronico.setTurnoAnimatronico(false);
            }
        }
    }

    private boolean turnoDisponible(int indiceJugador, int indiceAnimatronico) {
        if (indiceJugador < 0 || indiceJugador >= jugadores.size()) {
            return false;
        }
        Jugador jugador = jugadores.get(indiceJugador);
        if (jugador == null || jugador.equipoDerrotado() || jugador.getGrupo() == null
                || indiceAnimatronico >= cantidadAnimatronicosPermitidos(indiceJugador)) {
            return false;
        }
        Animatronico animatronico = jugador.getGrupo()[indiceAnimatronico];
        if (animatronico == null || !animatronico.sigueVivo()) {
            return false;
        }
        return !animatronico.estaNoqueado();
    }

    private boolean turnoDisponibleParaAvance(int indiceJugador, int indiceAnimatronico, Set<Animatronico> noqueadosSaltados) {
        if (indiceJugador < 0 || indiceJugador >= jugadores.size()) {
            return false;
        }
        Jugador jugador = jugadores.get(indiceJugador);
        if (jugador == null || jugador.equipoDerrotado() || jugador.getGrupo() == null
                || indiceAnimatronico >= cantidadAnimatronicosPermitidos(indiceJugador)) {
            return false;
        }
        Animatronico animatronico = jugador.getGrupo()[indiceAnimatronico];
        if (animatronico == null || !animatronico.sigueVivo() || noqueadosSaltados.contains(animatronico)) {
            return false;
        }
        if (animatronico.estaNoqueado()) {
            animatronico.consumirTurnoNoqueado();
            noqueadosSaltados.add(animatronico);
            return false;
        }
        return true;
    }

    private int obtenerMayorCantidadAnimatronicos() {
        int mayorCantidad = 0;
        for (int i = 0; i < jugadores.size(); i++) {
            int cantidadPermitida = cantidadAnimatronicosPermitidos(i);
            if (cantidadPermitida > mayorCantidad) {
                mayorCantidad = cantidadPermitida;
            }
        }
        return mayorCantidad;
    }

    private boolean cantidadJugadoresValida() {
        return jugadores != null
                && jugadores.size() >= CANTIDAD_MINIMA_JUGADORES
                && jugadores.size() <= CANTIDAD_MAXIMA_JUGADORES;
    }

    private int cantidadAnimatronicosPermitidos(int indiceJugador) {
        Jugador jugador = jugadores.get(indiceJugador);
        if (jugador == null || jugador.getGrupo() == null) {
            return 0;
        }
        int limite;
        if (jugadores.size() == 2) {
            limite = CANTIDAD_BALANCE_ANIMATRONICOS;
        } else if (jugadores.size() == 3 && indiceJugador == 1) {
            limite = CANTIDAD_BALANCE_ANIMATRONICOS;
        } else {
            limite = CANTIDAD_NORMAL_ANIMATRONICOS;
        }
        return Math.min(jugador.getGrupo().length, limite);
    }

    private void verificarGanador() {
        if (ganador != null) {
            return;
        }
        for (Jugador j : jugadores) {
            if (j != null && j.equipoDerrotado()) {
                Equipo equipoGanador = j.getEquipo() == Equipo.Rojo ? Equipo.Azul : Equipo.Rojo;
                for (Jugador posibleGanador : jugadores) {
                    if (posibleGanador != null && posibleGanador.getEquipo() == equipoGanador) {
                        ganador = posibleGanador;
                        return;
                    }
                }
            }
        }
    }

    private Habilidad buscarHabilidadSeleccionada(Animatronico animatronico, TipoHabilidad tipoElegido) {
        if (animatronico == null || animatronico.getHabilidades() == null || tipoElegido == null) {
            return null;
        }
        for (Habilidad habilidad : animatronico.getHabilidades()) {
            if (habilidad != null && habilidad.getTipo() == tipoElegido) {
                return habilidad;
            }
        }
        return null;
    }
}
