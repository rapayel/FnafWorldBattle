package org.fnafworld.dominio.entidades;

import java.util.*;
import org.fnafworld.Equipo;
import org.fnafworld.TipoHabilidad;

/**
 *
 * @author lagar
 */
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


    private String urlCampo;
    private String urlMusica;
    private Equipo equipoGanador;
    private List<Jugador> jugadores;
    private Jugador jugadorActual;
    private Random random;
    private int indiceJugadorActual;
    private int indiceAnimatronicoActual;
    private Jugador ultimoJugadorAtacante;
    private Animatronico ultimoAnimatronicoAtacante;
    private List<Animatronico> ultimosAfectados;

    public BatallaCampo(List<Jugador> jugadores, String urlCampo, String urlMusica) {
        if (jugadores == null) {
            throw new IllegalArgumentException("Los jugadores de la batalla no pueden ser nulos.");
        }
        this.urlCampo = urlCampo;
        this.urlMusica = urlMusica;
        this.equipoGanador = null;
        this.random = new Random();
        this.indiceJugadorActual = 0;
        this.indiceAnimatronicoActual = 0;
        this.jugadores = new ArrayList<>(jugadores);
        this.ultimosAfectados = new ArrayList<>();
        iniciarTurnos();
    }

    public void atacar(String idJugador, String idAnimatronico, TipoHabilidad tipoHabilidad) {
        Jugador jugadorEntidad = buscarJugadorPorId(idJugador);

        if (jugadorEntidad != null && jugadorActual != null
                && jugadorEntidad.getId().equals(jugadorActual.getId())
                && jugadorEntidad.miTurno()) {

            Animatronico atacante = extraerAnimatronicoEnTurno(jugadorEntidad);
            Habilidad habilidad = buscarHabilidadSeleccionada(atacante, tipoHabilidad);
            List<Animatronico> objetivosContrarios = extraerAnimatronicosContrarios(jugadorEntidad);
            List<Animatronico> objetivosAliados = extraerAnimatronicosAliados(jugadorEntidad);

            if (atacante != null && Objects.equals(idAnimatronico, atacante.getIdAnimatronico())
                    && habilidad != null && habilidad.getTipo().esAtaque()
                    && (!objetivosContrarios.isEmpty() || !objetivosAliados.isEmpty())) {

                Map<Animatronico, Integer> versionesAntes = new HashMap<>();
                for (Animatronico a : extraerAnimatronicosCampoCompleto()) {
                    versionesAntes.put(a, a.getEstadoVersion());
                }

                boolean repetirAtaque = habilidad.getTipo() != TipoHabilidad.MimicBall
                        && atacante.consumirAtaqueDoble();
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

                if (equipoGanador == null) {
                    avanzarTurno();
                } else {
                    limpiarTurnos();
                    jugadorActual = null;
                }
            }
        }
    }

    public List<Jugador> getJugadores() {
        return Collections.unmodifiableList(jugadores);
    }

    public Equipo getEquipoGanador() {
        return equipoGanador;
    }

    public String getIdJugadorTurno() {
        return jugadorActual != null ? jugadorActual.getId() : null;
    }

    public Jugador getUltimoJugadorAtacante() {
        return ultimoJugadorAtacante;
    }

    public Animatronico getUltimoAnimatronicoAtacante() {
        return ultimoAnimatronicoAtacante;
    }

    public List<Animatronico> getUltimosAfectados() {
        return ultimosAfectados != null
                ? Collections.unmodifiableList(ultimosAfectados)
                : Collections.emptyList();
    }

    public Jugador encontrarJugadorDeAnimatronico(Animatronico animatronico) {
        for (Jugador jugador : jugadores) {
            if (jugador == null || jugador.getGrupo() == null) continue;
            for (Animatronico a : jugador.getGrupo()) {
                if (a == animatronico) return jugador;
            }
        }
        return null;
    }

    private Jugador buscarJugadorPorId(String idJugador) {
        if (idJugador == null) return null;
        for (Jugador j : jugadores) {
            if (j != null && j.getId().equals(idJugador)) return j;
        }
        return null;
    }

    private void ejecutarTipoAtaque(Animatronico atacante, Habilidad habilidad,
            List<Animatronico> objetivosContrarios, List<Animatronico> objetivosAliados) {
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
                atacarVariosAleatorios(objetivosContrarios, calcularDanioConEscala(55, atacante, habilidad, 0.60), 3);
                break;
            case JackOBomb:
                atacarTodos(objetivosContrarios, calcularDanioConEscala(10, atacante, habilidad, 0.25));
                break;
            case Buzzsaw:
                atacarVariosAleatorios(objetivosContrarios, calcularDanioConEscala(25, atacante, habilidad, 0.45), random.nextInt(4) + 1);
                break;
            case Balloons2:
                atacarAleatorio(objetivosContrarios, calcularDanio(130, atacante, habilidad));
                break;
            case PrizeBall:
                ejecutarHabilidadAleatoria(atacante, habilidad, objetivosContrarios, objetivosAliados, TipoHabilidad.enPrizeBall());
                break;
            case PrizeBall2:
                ejecutarHabilidadAleatoria(atacante, habilidad, objetivosContrarios, objetivosAliados, TipoHabilidad.enPrizeBall2());
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
                atacarConToxicidadAleatorio(objetivosContrarios,
                        calcularDanio(95, atacante, habilidad),
                        calcularDanioConEscala(10, atacante, habilidad, ESCALA_TOXICIDAD));
                break;
            case ToxicBite2:
                atacarConToxicidadVariosAleatorios(objetivosContrarios,
                        calcularDanioConEscala(45, atacante, habilidad, 0.45),
                        calcularDanioConEscala(10, atacante, habilidad, ESCALA_TOXICIDAD), 3);
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

    private void atacarAleatorio(List<Animatronico> objetivos, int danio) {
        Animatronico objetivo = extraerAnimatronicoAleatorio(objetivos);
        if (objetivo != null) aplicarDanio(objetivo, danio);
    }

    private void atacarVariosAleatorios(List<Animatronico> objetivos, int danio, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            aplicarDanio(copia.remove(random.nextInt(copia.size())), danio);
        }
    }

    private void atacarTodos(List<Animatronico> objetivos, int danio) {
        for (Animatronico o : objetivos) aplicarDanio(o, danio);
    }

    private void disminuirFuerzaAleatorio(List<Animatronico> objetivos, int cantidad) {
        Animatronico o = extraerAnimatronicoAleatorio(objetivos);
        if (o != null) o.disminuirFuerza(cantidad, TURNOS_EFECTO_ESTADO);
    }

    private void disminuirFuerzaTodos(List<Animatronico> objetivos, int cantidad) {
        for (Animatronico o : objetivos) o.disminuirFuerza(cantidad, TURNOS_EFECTO_ESTADO);
    }

    private void quitarPoderHabilidadesAleatorio(List<Animatronico> objetivos) {
        Animatronico o = extraerAnimatronicoAleatorio(objetivos);
        if (o != null) o.quitarPoderHabilidades(TURNOS_EFECTO_ESTADO);
    }

    private void disminuirArmaduraVariosAleatorios(List<Animatronico> objetivos, int cantidad, int cantidadObjetivos) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidadObjetivos && !copia.isEmpty(); i++) {
            copia.remove(random.nextInt(copia.size())).disminuirArmadura(cantidad, TURNOS_EFECTO_ESTADO);
        }
    }

    private void disminuirArmaduraTodos(List<Animatronico> objetivos, int cantidad) {
        for (Animatronico o : objetivos) o.disminuirArmadura(cantidad, TURNOS_EFECTO_ESTADO);
    }

    private void eliminarAleatorio(List<Animatronico> objetivos, double prob) {
        Animatronico o = extraerAnimatronicoAleatorio(objetivos);
        if (o != null && random.nextDouble() < prob) o.eliminar();
    }

    private void curarVariosAleatorios(List<Animatronico> objetivos, int curacion, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            copia.remove(random.nextInt(copia.size())).curar(curacion);
        }
    }

    private void curarPorcentajeTodos(List<Animatronico> objetivos, double porcentaje) {
        for (Animatronico o : objetivos) o.curar((int) Math.round(o.getVidaActual() * porcentaje));
    }

    private void curarCompletoMasDanado(List<Animatronico> objetivos) {
        Animatronico masDanado = null;
        for (Animatronico o : objetivos) {
            if (masDanado == null || o.getVidaFaltante() > masDanado.getVidaFaltante()) masDanado = o;
        }
        if (masDanado != null) masDanado.curarCompleto();
    }

    private void regenerarTodos(List<Animatronico> objetivos, int curacionPorTurno) {
        for (Animatronico o : objetivos) o.regenerarVida(curacionPorTurno, TURNOS_EFECTO_ESTADO);
    }

    private void protegerToxicidadTodos(List<Animatronico> objetivos) {
        for (Animatronico o : objetivos) o.protegerToxicidad(TURNOS_EFECTO_ESTADO);
    }

    private void aplicarToxicidadVariosAleatorios(List<Animatronico> objetivos, int danioPorTurno, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            extraerProtectorNeonWall(copia.remove(random.nextInt(copia.size()))).aplicarToxicidad(danioPorTurno, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aplicarToxicidadTodos(List<Animatronico> objetivos, int danioPorTurno) {
        for (Animatronico o : objetivos) extraerProtectorNeonWall(o).aplicarToxicidad(danioPorTurno, TURNOS_EFECTO_ESTADO);
    }

    private void atacarConToxicidadAleatorio(List<Animatronico> objetivos, int danio, int danioTox) {
        Animatronico o = extraerAnimatronicoAleatorio(objetivos);
        if (o != null) {
            aplicarDanio(o, danio);
            if (o.sigueVivo()) extraerProtectorNeonWall(o).aplicarToxicidad(danioTox, TURNOS_EFECTO_ESTADO);
        }
    }

    private void atacarConToxicidadVariosAleatorios(List<Animatronico> objetivos, int danio, int danioTox, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            Animatronico o = copia.remove(random.nextInt(copia.size()));
            aplicarDanio(o, danio);
            if (o.sigueVivo()) extraerProtectorNeonWall(o).aplicarToxicidad(danioTox, TURNOS_EFECTO_ESTADO);
        }
    }

    private void eliminarPorVidaMaximaAleatorios(List<Animatronico> objetivos, double porcentaje, int cantidad) {
        List<Animatronico> candidatos = new ArrayList<>();
        for (Animatronico o : objetivos) {
            if (o.getVidaActual() <= o.getVidaTotal() * porcentaje) candidatos.add(o);
        }
        eliminarVariosAleatorios(candidatos, cantidad);
    }

    private void eliminarVariosAleatoriosPorProbabilidad(List<Animatronico> objetivos, double prob, int cantidad) {
        if (random.nextDouble() < prob) eliminarVariosAleatorios(objetivos, cantidad);
    }

    private void eliminarVariosAleatorios(List<Animatronico> objetivos, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            copia.remove(random.nextInt(copia.size())).eliminar();
        }
    }

    private void aumentarFuerzaYArmaduraVariosAleatorios(List<Animatronico> objetivos, int fuerza, int armadura, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            copia.remove(random.nextInt(copia.size())).aumentarFuerzaYArmadura(fuerza, armadura, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aumentarFuerzaYArmaduraAleatorio(List<Animatronico> objetivos, int fuerza, int armadura) {
        Animatronico o = extraerAnimatronicoAleatorio(objetivos);
        if (o != null) o.aumentarFuerzaYArmadura(fuerza, armadura, TURNOS_EFECTO_ESTADO);
    }

    private void aplicarDanioCosmicoTodos(List<Animatronico> objetivos) {
        for (Animatronico o : objetivos) o.aplicarDanioCosmico(DANIO_COSMICO, TURNOS_EFECTO_ESTADO);
    }

    private void aumentarPoderTodos(List<Animatronico> objetivos, int cantidad) {
        for (Animatronico o : objetivos) o.aumentarPoderHabilidades(cantidad, TURNOS_EFECTO_ESTADO);
    }

    private void aumentarArmaduraTodos(List<Animatronico> objetivos, int cantidad) {
        for (Animatronico o : objetivos) o.aumentarArmadura(cantidad, TURNOS_EFECTO_ESTADO);
    }

    private void castigarAtacanteYAliados(Animatronico atacante, List<Animatronico> aliados, int danio, int cantidad) {
        atacante.recibirDanioDirecto(danio);
        List<Animatronico> copia = new ArrayList<>(aliados);
        copia.remove(atacante);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            copia.remove(random.nextInt(copia.size())).recibirDanioDirecto(danio);
        }
    }

    private void noquearVariosAleatorios(List<Animatronico> objetivos, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            copia.remove(random.nextInt(copia.size())).noquear(TURNOS_NOQUEO);
        }
    }

    private void aplicarDanioPasivoVariosAleatorios(List<Animatronico> objetivos, int danioPorTurno, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            extraerProtectorNeonWall(copia.remove(random.nextInt(copia.size()))).aplicarDanioPasivo(danioPorTurno, TURNOS_EFECTO_ESTADO);
        }
    }

    private void aplicarDanioPasivoTodos(List<Animatronico> objetivos, int danioPorTurno) {
        for (Animatronico o : objetivos) extraerProtectorNeonWall(o).aplicarDanioPasivo(danioPorTurno, TURNOS_EFECTO_ESTADO);
    }

    private void aplicarDanioRetardadoVariosAleatorios(List<Animatronico> objetivos, int danio, int cantidad) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            extraerProtectorNeonWall(copia.remove(random.nextInt(copia.size()))).aplicarDanioRetardado(danio, TURNOS_EFECTO_ESTADO);
        }
    }

    private void atacarAleatorioConRetroceso(Animatronico atacante, List<Animatronico> objetivos, int danio, double retroceso) {
        Animatronico o = extraerAnimatronicoAleatorio(objetivos);
        if (o != null) atacante.recibirDanioDirecto((int) Math.ceil(aplicarDanioYObtener(o, danio) * retroceso));
    }

    private void atacarVariosAleatoriosConRetroceso(Animatronico atacante, List<Animatronico> objetivos, int danio, int cantidad, double retroceso) {
        List<Animatronico> copia = new ArrayList<>(objetivos);
        int retrocesoTotal = 0;
        for (int i = 0; i < cantidad && !copia.isEmpty(); i++) {
            retrocesoTotal += (int) Math.ceil(aplicarDanioYObtener(copia.remove(random.nextInt(copia.size())), danio) * retroceso);
        }
        if (retrocesoTotal > 0) atacante.recibirDanioDirecto(retrocesoTotal);
    }

    private void quitarPorcentajeVidaAleatorio(List<Animatronico> objetivos, double porcentaje) {
        Animatronico o = extraerAnimatronicoAleatorio(objetivos);
        if (o != null) extraerProtectorNeonWall(o).quitarPorcentajeVida(porcentaje);
    }

    private void aplicarNeonWall2Aleatorio(List<Animatronico> objetivos) {
        Animatronico o = extraerAnimatronicoAleatorio(objetivos);
        if (o != null) {
            o.hacerInvencible(TURNOS_EFECTO_ESTADO);
            o.aplicarDanioPasivoSevero(DANIO_PASIVO_NEON_WALL2, TURNOS_EFECTO_ESTADO);
        }
    }

    private List<Animatronico> extraerAnimatronicosCampo(List<Animatronico> contrarios, List<Animatronico> aliados) {
        List<Animatronico> todos = new ArrayList<>(contrarios);
        todos.addAll(aliados);
        return todos;
    }

    private void ejecutarHabilidadAleatoria(Animatronico atacante, Habilidad habilidad,
            List<Animatronico> contrarios, List<Animatronico> aliados, TipoHabilidad[] opciones) {
        if (opciones.length == 0) return;
        TipoHabilidad tipo = opciones[random.nextInt(opciones.length)];
        ejecutarTipoAtaque(atacante, new Habilidad(habilidad.getPoder(), tipo, habilidad.getDescripcion()), contrarios, aliados);
    }

    private void transformarEnAnimatronicoAleatorio(Animatronico atacante, List<Animatronico> contrarios) {
        Animatronico o = extraerAnimatronicoAleatorio(contrarios);
        if (o != null) atacante.copiarFormaDe(o);
    }

    private void transformarAleatorioEnFormaAliada(List<Animatronico> contrarios, List<Animatronico> aliados) {
        Animatronico rival = extraerAnimatronicoAleatorio(contrarios);
        Animatronico aliado = extraerAnimatronicoAleatorio(aliados);
        if (rival != null && aliado != null) rival.copiarFormaDe(aliado);
    }

    private void aumentarDanioAtaqueTodos(List<Animatronico> objetivos, int cantidad) {
        for (Animatronico o : objetivos) o.aumentarDanioAtaque(cantidad, TURNOS_EFECTO_ESTADO);
    }

    private void revivirAliadosAleatorios(Animatronico atacante, int cantidad, double prob) {
        if (random.nextDouble() >= prob) return;
        Jugador jugador = encontrarJugadorDeAnimatronico(atacante);
        List<Animatronico> derrotados = extraerAnimatronicosAliadosDerrotados(jugador);
        for (int i = 0; i < cantidad && !derrotados.isEmpty(); i++) {
            Animatronico o = derrotados.remove(random.nextInt(derrotados.size()));
            o.revivir((int) Math.ceil(o.getVidaTotal() * VIDA_REVIVIR_GIFT_BOXES));
        }
    }

    private void activarMimicBallAliado(Animatronico atacante, List<Animatronico> aliados) {
        List<Animatronico> copia = new ArrayList<>(aliados);
        copia.remove(atacante);
        Animatronico o = extraerAnimatronicoAleatorio(copia.isEmpty() ? aliados : copia);
        if (o != null) o.activarAtaqueDoble();
    }

    private void potenciarAliadosYSacrificar(Animatronico atacante, List<Animatronico> aliados) {
        for (Animatronico o : aliados) {
            if (o != atacante) {
                o.aumentarFuerzaYArmadura(AUMENTO_HOCUS_POCUS, AUMENTO_HOCUS_POCUS, TURNOS_EFECTO_ESTADO);
                o.aumentarPoderHabilidades(AUMENTO_HOCUS_POCUS, TURNOS_EFECTO_ESTADO);
            }
        }
        atacante.sacrificar();
    }

    private void aplicarDanio(Animatronico objetivo, int danio) {
        aplicarDanioYObtener(objetivo, danio);
    }

    private int aplicarDanioYObtener(Animatronico objetivo, int danio) {
        if (objetivo == null) return 0;
        return extraerProtectorNeonWall(objetivo).recibirDanioYObtener(danio);
    }

    private Animatronico extraerProtectorNeonWall(Animatronico objetivo) {
        Jugador duenio = encontrarJugadorDeAnimatronico(objetivo);
        if (duenio == null || duenio.getGrupo() == null) return objetivo;
        for (Animatronico a : duenio.getGrupo()) {
            if (a != null && a != objetivo && a.protegeConNeonWall() && a.sigueVivo()) return a;
        }
        return objetivo;
    }

    private Animatronico extraerAnimatronicoAleatorio(List<Animatronico> animatronicos) {
        if (animatronicos == null || animatronicos.isEmpty()) return null;
        return animatronicos.get(random.nextInt(animatronicos.size()));
    }

    private List<Animatronico> extraerAnimatronicosVivos(Animatronico[] animatronicos) {
        List<Animatronico> vivos = new ArrayList<>();
        if (animatronicos == null) return vivos;
        for (Animatronico a : animatronicos) {
            if (a != null && a.sigueVivo()) vivos.add(a);
        }
        return vivos;
    }

    private List<Animatronico> extraerAnimatronicosCampoCompleto() {
        List<Animatronico> todos = new ArrayList<>();
        if (jugadores == null) return todos;
        for (Jugador j : jugadores) {
            if (j == null || j.getGrupo() == null) continue;
            for (Animatronico a : j.getGrupo()) {
                if (a != null) todos.add(a);
            }
        }
        return todos;
    }

    private int calcularDanio(int base, Animatronico atacante, Habilidad habilidad) {
        return base + atacante.getFuerzaActual() + atacante.getPoderHabilidad(habilidad) + atacante.getDanioExtraAtaque();
    }

    private int calcularDanioConEscala(int base, Animatronico atacante, Habilidad habilidad, double escala) {
        return base + (int) Math.round((atacante.getFuerzaActual() + atacante.getPoderHabilidad(habilidad)) * escala) + atacante.getDanioExtraAtaque();
    }

    private int calcularCuracion(int base, Animatronico atacante, Habilidad habilidad, double escala) {
        return Math.max(1, base + (int) Math.round((atacante.getFuerzaActual() + atacante.getPoderHabilidad(habilidad)) * escala));
    }

    private Animatronico extraerAnimatronicoEnTurno(Jugador jugador) {
        if (jugador.getGrupo() == null) return null;
        for (Animatronico a : jugador.getGrupo()) {
            if (a != null && a.isTurnoAnimatronico() && a.sigueVivo()) return a;
        }
        return null;
    }


    private List<Animatronico> extraerAnimatronicosContrarios(Jugador jugador) {
        List<Animatronico> contrarios = new ArrayList<>();
        if (jugador.getEquipo() == null) return contrarios;
        for (Jugador j : jugadores) {
            if (j != null && j.getEquipo() != null && !j.getEquipo().equals(jugador.getEquipo())) {
                contrarios.addAll(extraerAnimatronicosVivos(j.getGrupo()));
            }
        }
        return contrarios;
    }

    private List<Animatronico> extraerAnimatronicosAliados(Jugador jugador) {
        List<Animatronico> aliados = new ArrayList<>();
        if (jugador.getEquipo() == null) return aliados;
        for (Jugador j : jugadores) {
            if (j != null && j.getEquipo() != null && j.getEquipo().equals(jugador.getEquipo())) {
                aliados.addAll(extraerAnimatronicosVivos(j.getGrupo()));
            }
        }
        return aliados;
    }

    private List<Animatronico> extraerAnimatronicosAliadosDerrotados(Jugador jugador) {
        List<Animatronico> derrotados = new ArrayList<>();
        if (jugador == null || jugador.getEquipo() == null) return derrotados;
        for (Jugador j : jugadores) {
            if (j != null && j.getEquipo() != null && j.getEquipo().equals(jugador.getEquipo()) && j.getGrupo() != null) {
                for (Animatronico a : j.getGrupo()) {
                    if (a != null && !a.sigueVivo()) derrotados.add(a);
                }
            }
        }
        return derrotados;
    }

    private void iniciarTurnos() {
        if (!cantidadJugadoresValida()) { jugadorActual = null; return; }
        aplicarTurnoActual();
    }

    private void avanzarTurno() {
        if (!cantidadJugadoresValida()) { jugadorActual = null; return; }
        int mayor = obtenerMayorCantidadAnimatronicos();
        if (mayor == 0) { limpiarTurnos(); jugadorActual = null; return; }
        int intentos = jugadores.size() * mayor;
        Set<Animatronico> noqueadosSaltados = new HashSet<>();
        do {
            indiceJugadorActual++;
            if (indiceJugadorActual >= jugadores.size()) {
                indiceJugadorActual = 0;
                indiceAnimatronicoActual++;
                if (indiceAnimatronicoActual >= mayor) indiceAnimatronicoActual = 0;
            }
            intentos--;
        } while (intentos > 0 && !turnoDisponibleParaAvance(indiceJugadorActual, indiceAnimatronicoActual, noqueadosSaltados));
        aplicarTurnoActual();
    }

    private void aplicarTurnoActual() {
        limpiarTurnos();
        if (!turnoDisponible(indiceJugadorActual, indiceAnimatronicoActual)) { jugadorActual = null; return; }
        jugadorActual = jugadores.get(indiceJugadorActual);
        jugadorActual.setMiTurno(true);
        jugadorActual.getGrupo()[indiceAnimatronicoActual].setTurnoAnimatronico(true);
    }

    private void limpiarTurnos() {
        if (jugadores == null) return;
        for (Jugador j : jugadores) {
            if (j == null) continue;
            j.setMiTurno(false);
            if (j.getGrupo() != null) {
                for (Animatronico a : j.getGrupo()) {
                    if (a != null) a.setTurnoAnimatronico(false);
                }
            }
        }
    }

    private boolean turnoDisponible(int indiceJugador, int indiceAnimatronico) {
        if (indiceJugador < 0 || indiceJugador >= jugadores.size()) return false;
        Jugador j = jugadores.get(indiceJugador);
        if (j == null || j.equipoDerrotado() || j.getGrupo() == null
                || indiceAnimatronico >= cantidadAnimatronicosPermitidos(indiceJugador)) return false;
        Animatronico a = j.getGrupo()[indiceAnimatronico];
        return a != null && a.sigueVivo() && !a.estaNoqueado();
    }

    private boolean turnoDisponibleParaAvance(int indiceJugador, int indiceAnimatronico, Set<Animatronico> noqueadosSaltados) {
        if (indiceJugador < 0 || indiceJugador >= jugadores.size()) return false;
        Jugador j = jugadores.get(indiceJugador);
        if (j == null || j.equipoDerrotado() || j.getGrupo() == null
                || indiceAnimatronico >= cantidadAnimatronicosPermitidos(indiceJugador)) return false;
        Animatronico a = j.getGrupo()[indiceAnimatronico];
        if (a == null || !a.sigueVivo() || noqueadosSaltados.contains(a)) return false;
        if (a.estaNoqueado()) { a.consumirTurnoNoqueado(); noqueadosSaltados.add(a); return false; }
        return true;
    }

    private int obtenerMayorCantidadAnimatronicos() {
        int mayor = 0;
        for (int i = 0; i < jugadores.size(); i++) {
            int permitidos = cantidadAnimatronicosPermitidos(i);
            if (permitidos > mayor) mayor = permitidos;
        }
        return mayor;
    }

    private boolean cantidadJugadoresValida() {
        return jugadores != null
                && jugadores.size() >= CANTIDAD_MINIMA_JUGADORES
                && jugadores.size() <= CANTIDAD_MAXIMA_JUGADORES;
    }

    private int cantidadAnimatronicosPermitidos(int indiceJugador) {
        Jugador j = jugadores.get(indiceJugador);
        if (j == null || j.getGrupo() == null) return 0;
        int limite = (jugadores.size() == 2 || (jugadores.size() == 3 && indiceJugador == 1))
                ? CANTIDAD_BALANCE_ANIMATRONICOS : CANTIDAD_NORMAL_ANIMATRONICOS;
        return Math.min(j.getGrupo().length, limite);
    }

    private void verificarGanador() {
        if (equipoGanador != null) return;
        for (Jugador j : jugadores) {
            if (j != null && j.equipoDerrotado()) {
                Equipo posibleGanador = j.getEquipo() == Equipo.Rojo ? Equipo.Azul : Equipo.Rojo;
                if (equipoTieneVivos(posibleGanador)) {
                    equipoGanador = posibleGanador;
                }
                return;
            }
        }
    }

    private boolean equipoTieneVivos(Equipo equipo) {
        for (Jugador j : jugadores) {
            if (j != null && j.getEquipo() == equipo && !j.equipoDerrotado()) return true;
        }
        return false;
    }

    private Habilidad buscarHabilidadSeleccionada(Animatronico animatronico, TipoHabilidad tipo) {
        if (animatronico == null || animatronico.getHabilidades() == null || tipo == null) return null;
        for (Habilidad h : animatronico.getHabilidades()) {
            if (h != null && h.getTipo() == tipo) return h;
        }
        return null;
    }
}