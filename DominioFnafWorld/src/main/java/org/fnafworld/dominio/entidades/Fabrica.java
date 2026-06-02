package org.fnafworld.dominio.entidades;

import java.util.ArrayList;
import java.util.List;
import org.fnafworld.TipoAnimatronico;
import org.fnafworld.TipoHabilidad;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.GrupoFabricaDTO;
import org.fnafworld.dtos.HabilidadDTO;
import org.fnafworld.dtos.SolicitudAnimatronicoFabricaDTO;

public class Fabrica {
    private static final int FUERZA_DEFAULT = 50;
    private static final int VIDA_DEFAULT = 1000;
    private static final int ESCALA_VIDA_BALANCE = 10;

    public AnimatronicoDTO crearAnimatronico(SolicitudAnimatronicoFabricaDTO solicitud) {
        if (solicitud == null || solicitud.getTipo() == null) {
            throw new IllegalArgumentException("La solicitud de animatronico no puede estar vacia.");
        }

        String id = solicitud.getIdAnimatronico() != null
                ? solicitud.getIdAnimatronico()
                : solicitud.getTipo().name();

        return crearAnimatronico(id, solicitud.getTipo(), solicitud.isTurnoAnimatronico());
    }

    public AnimatronicoDTO[] crearGrupo(GrupoFabricaDTO solicitudGrupo) {
        if (solicitudGrupo == null || solicitudGrupo.getAnimatronicos() == null) {
            return new AnimatronicoDTO[0];
        }

        List<AnimatronicoDTO> grupo = new ArrayList<>();
        for (SolicitudAnimatronicoFabricaDTO solicitud : solicitudGrupo.getAnimatronicos()) {
            grupo.add(crearAnimatronico(solicitud));
        }
        return grupo.toArray(new AnimatronicoDTO[0]);
    }

    public AnimatronicoDTO crearAnimatronico(String id, TipoAnimatronico tipo, boolean turnoAnimatronico) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de animatronico es obligatorio.");
        }

        switch (tipo) {
            case Freddy:
                return anim(id, tipo, turnoAnimatronico, 50, 100, habilidadesFreddy());
            case Bonnie:
                return anim(id, tipo, turnoAnimatronico, 45, 100, habilidadesBonnie());
            case Chica:
                return anim(id, tipo, turnoAnimatronico, 30, 100, habilidadesChica());
            case Foxy:
                return anim(id, tipo, turnoAnimatronico, 60, 100, habilidadesFoxy());
            case WitheredFreddy:
                return anim(id, tipo, turnoAnimatronico, 70, 120, habilidadesWitheredFreddy());
            case WitheredBonnie:
                return anim(id, tipo, turnoAnimatronico, 65, 110, habilidadesWitheredBonnie());
            case WitheredChica:
                return anim(id, tipo, turnoAnimatronico, 40, 120, habilidadesWitheredChica());
            case WitheredFoxy:
                return anim(id, tipo, turnoAnimatronico, 80, 90, habilidadesWitheredFoxy());
            case NightmareFreddy:
                return anim(id, tipo, turnoAnimatronico, 80, 140, habilidadesNightmareFreddy());
            case NightmareBonnie:
                return anim(id, tipo, turnoAnimatronico, 75, 130, habilidadesNightmareBonnie());
            case NightmareChica:
                return anim(id, tipo, turnoAnimatronico, 70, 135, habilidadesNightmareChica());
            case NightmareFoxy:
                return anim(id, tipo, turnoAnimatronico, 85, 110, habilidadesNightmareFoxy());
            case ToyFreddy:
                return anim(id, tipo, turnoAnimatronico, 45, 90, habilidadesToyFreddy());
            case ToyBonnie:
                return anim(id, tipo, turnoAnimatronico, 50, 85, habilidadesToyBonnie());
            case ToyChica:
                return anim(id, tipo, turnoAnimatronico, 40, 95, habilidadesToyChica());
            case Mangle:
                return anim(id, tipo, turnoAnimatronico, 55, 80, habilidadesMangle());
            case BalloonBoy:
                return anim(id, tipo, turnoAnimatronico, habilidadesBalloonBoy());
            case JJ:
                return anim(id, tipo, turnoAnimatronico, habilidadesJJ());
            case PhantomFreddy:
                return anim(id, tipo, turnoAnimatronico, habilidadesPhantomFreddy());
            case PhantomChica:
                return anim(id, tipo, turnoAnimatronico, habilidadesPhantomChica());
            case PhantomBB:
                return anim(id, tipo, turnoAnimatronico, habilidadesPhantomBB());
            case PhantomFoxy:
                return anim(id, tipo, turnoAnimatronico, habilidadesPhantomFoxy());
            case PhantomMangle:
                return anim(id, tipo, turnoAnimatronico, habilidadesPhantomMangle());
            case ShadowFreddy:
                return anim(id, tipo, turnoAnimatronico, habilidadesShadowFreddy());
            case Puppet:
                return anim(id, tipo, turnoAnimatronico, habilidadesPuppet());
            case PhantomPuppet:
                return anim(id, tipo, turnoAnimatronico, habilidadesPhantomPuppet());
            case GoldenFreddy:
                return anim(id, tipo, turnoAnimatronico, habilidadesGoldenFreddy());
            case Paperpals:
                return anim(id, tipo, turnoAnimatronico, habilidadesPaperpals());
            case Endo01:
                return anim(id, tipo, turnoAnimatronico, habilidadesEndo01());
            case Endo02:
                return anim(id, tipo, turnoAnimatronico, habilidadesEndo02());
            case Endoplush:
                return anim(id, tipo, turnoAnimatronico, habilidadesEndoplush());
            case ShadowBonnie:
                return anim(id, tipo, turnoAnimatronico, habilidadesShadowBonnie());
            case Plushtrap:
                return anim(id, tipo, turnoAnimatronico, habilidadesPlushtrap());
            case Sprintrap:
                return anim(id, tipo, turnoAnimatronico, habilidadesSpringtrap());
            case CryingChild:
                return anim(id, tipo, turnoAnimatronico, habilidadesCryingChild());
            case FuntimeFoxy:
                return anim(id, tipo, turnoAnimatronico, habilidadesFuntimeFoxy());
            case NightmareFredbear:
                return anim(id, tipo, turnoAnimatronico, habilidadesNightmareFredbear());
            case Nightmare:
                return anim(id, tipo, turnoAnimatronico, habilidadesNightmare());
            case Fredbear:
                return anim(id, tipo, turnoAnimatronico, habilidadesFredbear());
            case SpringBonnie:
                return anim(id, tipo, turnoAnimatronico, habilidadesSpringBonnie());
            case JackOBonnie:
                return anim(id, tipo, turnoAnimatronico, habilidadesJackOBonnie());
            case JackOChica:
                return anim(id, tipo, turnoAnimatronico, habilidadesJackOChica());
            case Animdude:
                return anim(id, tipo, turnoAnimatronico, habilidadesAnimdude());
            case MrChipper:
                return anim(id, tipo, turnoAnimatronico, habilidadesMrChipper());
            case NightmareBB:
                return anim(id, tipo, turnoAnimatronico, habilidadesNightmareBB());
            case NightmarePuppet:
                return anim(id, tipo, turnoAnimatronico, habilidadesNightmarePuppet());
            case Coffee:
                return anim(id, tipo, turnoAnimatronico, habilidadesCoffee());
            case Purpleguy:
                return anim(id, tipo, turnoAnimatronico, habilidadesPurpleGuy());
            default:
                throw new IllegalArgumentException("No hay configuracion logica para " + tipo + ".");
        }
    }

    private AnimatronicoDTO anim(String id, TipoAnimatronico tipo, boolean turno, HabilidadDTO[] habilidades) {
        return anim(id, tipo, turno, FUERZA_DEFAULT, VIDA_DEFAULT, habilidades);
    }

    private AnimatronicoDTO anim(String id, TipoAnimatronico tipo, boolean turno, int fuerza, int vida, HabilidadDTO[] habilidades) {
        int vidaBalanceada = balancearVida(vida);
        return new AnimatronicoDTO(id, tipo, turno, fuerza, vidaBalanceada, vidaBalanceada, habilidades, true);
    }

    private int balancearVida(int vida) {
        if (vida <= 0 || vida >= 500) {
            return vida;
        }
        return vida * ESCALA_VIDA_BALANCE;
    }

    private HabilidadDTO hab(int poder, TipoHabilidad tipo, String descripcion) {
        return new HabilidadDTO(poder, tipo, descripcion);
    }

    private HabilidadDTO[] habilidadesFreddy() {
        return new HabilidadDTO[] {
            hab(35, TipoHabilidad.MicToss, "Lanza el microfono causando dano moderado a un enemigo individual."),
            hab(50, TipoHabilidad.PizzaWheel, "Invoca una lluvia masiva de pizzas rodantes que dana en area al equipo rival."),
            hab(0, TipoHabilidad.Birthday, "Otorga un aumento temporal en los atributos de ataque, defensa y velocidad de todos los aliados.")
        };
    }

    private HabilidadDTO[] habilidadesBonnie() {
        return new HabilidadDTO[] {
            hab(30, TipoHabilidad.Bite, "Muerde ferozmente a un oponente infligiendo dano fisico directo."),
            hab(40, TipoHabilidad.BashJam, "Toca un solo de guitarra aturdidor que inflige dano constante en area."),
            hab(0, TipoHabilidad.HappyJam, "Sana de forma progresiva e instantanea los puntos de salud de todo el equipo aliado.")
        };
    }

    private HabilidadDTO[] habilidadesChica() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.Cupcake, "Lanza pastelillos magicos que recuperan la salud total del equipo completo."),
            hab(0, TipoHabilidad.PartyFavors, "Crea una explosion festiva que causa dano leve al enemigo y cura a los aliados al mismo tiempo."),
            hab(0, TipoHabilidad.RegenSong, "Entona una melodia que activa la regeneracion de salud pasiva en cada turno.")
        };
    }

    private HabilidadDTO[] habilidadesFoxy() {
        return new HabilidadDTO[] {
            hab(45, TipoHabilidad.Hook, "Corta con su garfio metalico ejecutando un dano critico alto a un unico rival."),
            hab(0, TipoHabilidad.Jumpscare, "Lanza un grito aterrador que paraliza temporalmente las acciones enemigas."),
            hab(40, TipoHabilidad.HotCheese, "Cubre el piso con queso hirviendo que causa dano por quemaduras en el tiempo.")
        };
    }

    private HabilidadDTO[] habilidadesWitheredFreddy() {
        return new HabilidadDTO[] {
            hab(50, TipoHabilidad.MicToss, "Lanza el viejo microfono oxidado causando dano de impacto severo."),
            hab(0, TipoHabilidad.GloomSong, "Entona un cantico deprimente que reduce el poder de ataque del equipo rival."),
            hab(60, TipoHabilidad.EscKey, "Una version mejorada que invoca multiples pizzas gigantes que destruyen defensas enemigas.")
        };
    }

    private HabilidadDTO[] habilidadesWitheredBonnie() {
        return new HabilidadDTO[] {
            hab(55, TipoHabilidad.EyeBeam, "Una brahmica potente y destructiva que rompe armaduras e inflige alto dano."),
            hab(65, TipoHabilidad.PizzaWheel, "Dispara lasers de energia desde sus ojos directo a las lineas enemigas."),
            hab(0, TipoHabilidad.Unscrew, "Tiene una probabilidad del 30% de desmantelar y eliminar instantaneamente a un enemigo.")
        };
    }

    private HabilidadDTO[] habilidadesWitheredChica() {
        return new HabilidadDTO[] {
            hab(60, TipoHabilidad.Bite, "Ataca con sus mandibulas rotas causando graves danos de trituracion."),
            hab(0, TipoHabilidad.Cupcake, "Dispara sorpresas festivas que danan y curan simultaneamente."),
            hab(0, TipoHabilidad.PrizeBall2, "Posee un 50% de probabilidad de desarmar y destruir a un oponente mecanico al instante.")
        };
    }

    private HabilidadDTO[] habilidadesWitheredFoxy() {
        return new HabilidadDTO[] {
            hab(65, TipoHabilidad.Hook, "Ataque veloz con el garfio que atraviesa defensas fisicas."),
            hab(0, TipoHabilidad.RainyDay, "Susto repentino que interrumpe los turnos y congela las barras de accion enemigas."),
            hab(50, TipoHabilidad.HotCheese2, "Lanza una ola masiva de queso derretido hirviendo causando alto dano continuo.")
        };
    }

    private HabilidadDTO[] habilidadesNightmareFreddy() {
        return new HabilidadDTO[] {
            hab(70, TipoHabilidad.Bite2, "Una mordida de pesadilla con multiples hileras de dientes afilados."),
            hab(80, TipoHabilidad.Sludge, "Suelta pequenos sirvientes que atacan freneticamente acumulando rafagas de dano."),
            hab(90, TipoHabilidad.Freddles, "Invoca pizzas en descomposicion que envenenan e infligen gran dano de area.")
        };
    }

    private HabilidadDTO[] habilidadesNightmareBonnie() {
        return new HabilidadDTO[] {
            hab(75, TipoHabilidad.Bite2, "Ataque mandibular salvaje e implacable que destroza los puntos de salud."),
            hab(0, TipoHabilidad.RainyDay2, "Un grito ensordecedor que inhabilita por completo las acciones del rival."),
            hab(85, TipoHabilidad.PizzaWheel2, "Lanza una sierra circular giratoria que rebana brutalmente al objetivo.")
        };
    }

    private HabilidadDTO[] habilidadesNightmareChica() {
        return new HabilidadDTO[] {
            hab(70, TipoHabilidad.Bite2, "Tritura al enemigo causando un traumatismo critico severo."),
            hab(90, TipoHabilidad.Waterhose, "Lanza una pizza envenenada que arruina el estado y vitalidad de la escuadra enemiga."),
            hab(100, TipoHabilidad.BadPizza, "Lanza una calabaza explosiva que estalla causando dano de fuego masivo.")
        };
    }

    private HabilidadDTO[] habilidadesNightmareFoxy() {
        return new HabilidadDTO[] {
            hab(80, TipoHabilidad.Bite2, "Abalanzamiento feroz con colmillos expuestos infligiendo dano masivo."),
            hab(0, TipoHabilidad.Unscrew2, "Aterroriza a las filas enemigas impidiendo su movilidad durante un ciclo."),
            hab(95, TipoHabilidad.HotCheese2, "Inunda el campo de batalla con magma quesero infligiendo un dano por segundo devastador.")
        };
    }

    private HabilidadDTO[] habilidadesToyFreddy() {
        return new HabilidadDTO[] {
            hab(30, TipoHabilidad.MicToss, "Lanza el microfono de plastico infligiendo dano basico constante."),
            hab(0, TipoHabilidad.PartyFavors, "Dispara confeti curativo que otorga soporte leve al equipo."),
            hab(40, TipoHabilidad.SpeedSong, "Aumenta fuerza y armadura en +45 a 1 aliado aleatorio durante 8 turnos.")
        };
    }

    private HabilidadDTO[] habilidadesToyBonnie() {
        return new HabilidadDTO[] {
            hab(35, TipoHabilidad.BashJam, "Interpreta una nota estruendosa desestabilizando al oponente."),
            hab(0, TipoHabilidad.Munchies, "Dano pasivo de 14/turno a TODOS los enemigos y 4 aliados aleatorios."),
            hab(0, TipoHabilidad.PrizeBall, "Invoca una esfera sorpresa que ejecuta una habilidad aleatoria de nivel bajo.")
        };
    }

    private HabilidadDTO[] habilidadesToyChica() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.Cupcake, "Sana la salud de la formacion con deliciosos panecillos rosados."),
            hab(0, TipoHabilidad.Birthday, "Festeja aumentando momentaneamente las estadisticas globales del escuadron."),
            hab(45, TipoHabilidad.Waterhose, "Rocia agua a alta presion limpiando estados negativos y danando enemigos debiles.")
        };
    }

    private HabilidadDTO[] habilidadesMangle() {
        return new HabilidadDTO[] {
            hab(40, TipoHabilidad.Bite, "Ataque directo utilizando sus piezas desarmadas para morder."),
            hab(0, TipoHabilidad.Poppers, "Coloca pequenas minas sorpresa que explotan cuando un enemigo intenta atacar."),
            hab(0, TipoHabilidad.PrizeBall, "Despliega una capsula de premio menor que desata ataques aleatorios de menor rango.")
        };
    }

    private HabilidadDTO[] habilidadesBalloonBoy() {
        return new HabilidadDTO[] {
            hab(25, TipoHabilidad.Balloons, "Invoca globos que danan objetivos multiples."),
            hab(35, TipoHabilidad.HotCheese, "Derrama queso caliente para causar dano de quemadura."),
            hab(20, TipoHabilidad.Munchies, "Invoca pequenas criaturas mordedoras pasivas.")
        };
    }

    private HabilidadDTO[] habilidadesJJ() {
        return new HabilidadDTO[] {
            hab(25, TipoHabilidad.Balloons, "Dispara globos para distraer y atacar."),
            hab(30, TipoHabilidad.Poppers, "Coloca explosivos festivos en el campo."),
            hab(0, TipoHabilidad.Unscrew, "Tiene probabilidad de desmantelar instantaneamente al enemigo.")
        };
    }

    private HabilidadDTO[] habilidadesPhantomFreddy() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.GloomSong, "Canta una melodia deprimente que baja el ataque rival."),
            hab(40, TipoHabilidad.Sludge, "Lanza lodo que ralentiza drasticamente a los enemigos."),
            hab(0, TipoHabilidad.RainyDay, "Lluvia tormentosa que debilita las defensas contrarias.")
        };
    }

    private HabilidadDTO[] habilidadesPhantomChica() {
        return new HabilidadDTO[] {
            hab(45, TipoHabilidad.ToxicBite, "Muerde envenenando al objetivo en el tiempo."),
            hab(40, TipoHabilidad.Sludge, "Lodo pesado que reduce la velocidad del enemigo."),
            hab(0, TipoHabilidad.Unscrew, "Probabilidad de desarmar por completo a un oponente.")
        };
    }

    private HabilidadDTO[] habilidadesPhantomBB() {
        return new HabilidadDTO[] {
            hab(35, TipoHabilidad.ToxicBalloon, "Lanza globos toxicos que infligen veneno."),
            hab(0, TipoHabilidad.GloomBalloon, "Globo fantasmal que reduce el ataque del enemigo."),
            hab(0, TipoHabilidad.RainyDay, "Tormenta que disminuye estadisticas de la escuadra rival.")
        };
    }

    private HabilidadDTO[] habilidadesPhantomFoxy() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.Jumpscare, "Susto repentino que paraliza al contrincante."),
            hab(50, TipoHabilidad.ToxicBite, "Feroz mordisco cargado de toxinas."),
            hab(0, TipoHabilidad.Unscrew, "Intento mecanico de desmantelamiento instantaneo.")
        };
    }

    private HabilidadDTO[] habilidadesPhantomMangle() {
        return new HabilidadDTO[] {
            hab(40, TipoHabilidad.ToxicBite, "Mordida acida que drena vida por turno."),
            hab(0, TipoHabilidad.MisteryBox, "Cambia los enemigos por otros aleatorios de menor nivel."),
            hab(60, TipoHabilidad.PizzaWheel2, "Sierras masivas de pizza que cortan al equipo rival.")
        };
    }

    private HabilidadDTO[] habilidadesShadowFreddy() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.Unscrew, "Desmantela piezas clave enemigas con un porcentaje de exito."),
            hab(70, TipoHabilidad.EscKey, "Lanza comandos que pueden borrar instantaneamente a multiples rivales."),
            hab(50, TipoHabilidad.Waterhose, "Chorro de agua potente que barre enemigos con baja salud.")
        };
    }

    private HabilidadDTO[] habilidadesPuppet() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.PrizeBall2, "Crea un efecto aleatorio muy potente basado en cajas de regalos."),
            hab(0, TipoHabilidad.MisteryBox, "Reemplaza al equipo enemigo al azar."),
            hab(80, TipoHabilidad.EscKey, "Ataque fulminante de desinstalacion virtual.")
        };
    }

    private HabilidadDTO[] habilidadesPhantomPuppet() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.Jumpscare, "Paralizacion total de la barra de accion del rival."),
            hab(55, TipoHabilidad.HotCheese2, "Gran oleada de queso hirviendo continuo."),
            hab(0, TipoHabilidad.MisteryBox2, "Invoca una caja de misterio avanzada desordenando al enemigo.")
        };
    }

    private HabilidadDTO[] habilidadesGoldenFreddy() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.Jumpscare, "Susto espectral que aturde a todos los enemigos."),
            hab(0, TipoHabilidad.RainyDay2, "Tormenta de rayos pesada que drena armadura y ataque."),
            hab(0, TipoHabilidad.Haunting, "Fantasma que paraliza permanentemente a un enemigo.")
        };
    }

    private HabilidadDTO[] habilidadesPaperpals() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.PrizeBall2, "Efectos combinados aleatorios de alto nivel."),
            hab(0, TipoHabilidad.MisteryBox2, "Baraja las posiciones y tipos de enemigos drasticamente."),
            hab(0, TipoHabilidad.MimicBall, "Genera una esfera que duplica todos los ataques aliados.")
        };
    }

    private HabilidadDTO[] habilidadesEndo01() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.PowerSong, "Aumenta considerablemente el dano de ataque de todo el equipo."),
            hab(0, TipoHabilidad.ArmorSong, "Incrementa la defensa global del escuadron mitigando dano."),
            hab(65, TipoHabilidad.EndoArmy, "Invoca un ejercito de endoesqueletos laser automaticos.")
        };
    }

    private HabilidadDTO[] habilidadesEndo02() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.SpeedSong, "Maximiza la velocidad de carga de turnos del equipo."),
            hab(75, TipoHabilidad.EndoArmy, "Ejercito metalico continuo que asiste disparando."),
            hab(0, TipoHabilidad.NeonWall, "Crea un escudo de luz que bloquea un porcentaje del dano entrante.")
        };
    }

    private HabilidadDTO[] habilidadesEndoplush() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.NeonWall, "Barrera defensiva de neon inmune a ataques criticos."),
            hab(60, TipoHabilidad.EyeBeam, "Laser ocular concentrado que inflige gran dano perforante."),
            hab(70, TipoHabilidad.Waterhose2, "Rafaga masiva de agua purificadora que liquida enemigos debiles.")
        };
    }

    private HabilidadDTO[] habilidadesShadowBonnie() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.Haunting, "Aterroriza y congela las acciones de un objetivo."),
            hab(0, TipoHabilidad.MimicBall, "Duplica instantaneamente cada habilidad ofensiva ejecutada."),
            hab(0, TipoHabilidad.GiftBoxes, "Otorga cajas de resurreccion automatica a todo el equipo.")
        };
    }

    private HabilidadDTO[] habilidadesPlushtrap() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.Haunting, "Aterroriza y congela las acciones de un objetivo."),
            hab(0, TipoHabilidad.MimicBall, "Duplica instantaneamente cada habilidad ofensiva ejecutada."),
            hab(0, TipoHabilidad.GiftBoxes, "Otorga cajas de resurreccion automatica a todo el equipo.")
        };
    }

    private HabilidadDTO[] habilidadesSpringtrap() {
        return new HabilidadDTO[] {
            hab(60, TipoHabilidad.Bite2, "Ataque mandibular salvaje que destroza los puntos de salud."),
            hab(80, TipoHabilidad.SpringLocks, "Desata trampas de resortes infligiendo grave dano mecanico."),
            hab(0, TipoHabilidad.RainyDay, "Lluvia tormentosa que debilita las defensas contrarias.")
        };
    }

    private HabilidadDTO[] habilidadesCryingChild() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.PowerSong, "Aumenta considerablemente el dano de ataque de todo el equipo."),
            hab(0, TipoHabilidad.ArmorSong, "Incrementa la defensa global del escuadron mitigando dano."),
            hab(0, TipoHabilidad.GiftBoxes, "Otorga cajas de resurreccion automatica a todo el equipo.")
        };
    }

    private HabilidadDTO[] habilidadesFuntimeFoxy() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.HappyJam2, "Sana de forma masiva e instantanea los puntos de salud aliados."),
            hab(75, TipoHabilidad.CosmicSong, "Invoca meteoros que infligen alto dano de energia continuo."),
            hab(0, TipoHabilidad.GiftBoxes, "Otorga cajas de resurreccion automatica a todo el equipo.")
        };
    }

    private HabilidadDTO[] habilidadesNightmareFredbear() {
        return new HabilidadDTO[] {
            hab(85, TipoHabilidad.ToxicBite2, "Mordida de pesadilla cargada con toxinas letales."),
            hab(90, TipoHabilidad.BadPizza, "Lanza una pizza podrida que estalla causando dano masivo."),
            hab(100, TipoHabilidad.MegaBite, "Ejecuta una mordida devastadora de dano critico extremo.")
        };
    }

    private HabilidadDTO[] habilidadesNightmare() {
        return new HabilidadDTO[] {
            hab(85, TipoHabilidad.ToxicBite2, "Mordida de pesadilla cargada con toxinas letales."),
            hab(0, TipoHabilidad.RainyDay2, "Un grito ensordecedor que inhabilita las acciones del rival."),
            hab(100, TipoHabilidad.MegaBite, "Ejecuta una mordida devastadora de dano critico extremo.")
        };
    }

    private HabilidadDTO[] habilidadesFredbear() {
        return new HabilidadDTO[] {
            hab(100, TipoHabilidad.MegaBite, "Ejecuta una mordida devastadora de dano critico extremo."),
            hab(0, TipoHabilidad.RegenSong, "Entona una melodia que activa la regeneracion de salud pasiva."),
            hab(0, TipoHabilidad.MimicBall, "Genera una esfera que duplica todos los ataques aliados.")
        };
    }

    private HabilidadDTO[] habilidadesSpringBonnie() {
        return new HabilidadDTO[] {
            hab(80, TipoHabilidad.SpringLocks, "Desata trampas de resortes infligiendo grave dano mecanico."),
            hab(0, TipoHabilidad.HappyJam2, "Sana de forma masiva e instantanea los puntos de salud aliados."),
            hab(75, TipoHabilidad.CosmicSong, "Invoca meteoros que infligen alto dano de energia continuo.")
        };
    }

    private HabilidadDTO[] habilidadesJackOBonnie() {
        return new HabilidadDTO[] {
            hab(95, TipoHabilidad.JackOBomb, "Lanza una calabaza explosiva que causa dano de fuego masivo."),
            hab(0, TipoHabilidad.Haunting, "Aterroriza y congela las acciones de un objetivo."),
            hab(120, TipoHabilidad.Slasher, "Tiene una probabilidad de infligir un dano masivo de un solo golpe.")
        };
    }

    private HabilidadDTO[] habilidadesJackOChica() {
        return new HabilidadDTO[] {
            hab(95, TipoHabilidad.JackOBomb, "Lanza una calabaza explosiva que causa dano de fuego masivo."),
            hab(50, TipoHabilidad.Munchies, "Invoca animadoras que causan dano pasivo continuo."),
            hab(85, TipoHabilidad.Buzzsaw, "Ataca con una sierra circular infligiendo dano constante.")
        };
    }

    private HabilidadDTO[] habilidadesAnimdude() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.NeonWall2, "Genera un escudo de luz avanzado que bloquea dano masivo."),
            hab(110, TipoHabilidad.MegaVirus, "Infecta con un virus informatico que drena salud velozmente."),
            hab(130, TipoHabilidad.Th4Wall, "Rompe la cuarta pared causando dano absoluto e inevitable.")
        };
    }

    private HabilidadDTO[] habilidadesMrChipper() {
        return new HabilidadDTO[] {
            hab(50, TipoHabilidad.Hook, "Corta con su garfio ejecutando un dano critico alto."),
            hab(0, TipoHabilidad.HocusPocus, "Hechizo que transforma a los enemigos en versiones mas debiles."),
            hab(85, TipoHabilidad.Buzzsaw, "Ataca con una sierra circular infligiendo dano constante.")
        };
    }

    private HabilidadDTO[] habilidadesNightmareBB() {
        return new HabilidadDTO[] {
            hab(100, TipoHabilidad.MegaBite, "Ejecuta una mordida devastadora de dano critico extremo."),
            hab(0, TipoHabilidad.BubbleBreath, "Otorga inmunidad temporal contra ataques de estado gaseoso."),
            hab(65, TipoHabilidad.Balloons2, "Invoca globos mejorados que danan objetivos multiples.")
        };
    }

    private HabilidadDTO[] habilidadesNightmarePuppet() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.PrizeBall2, "Crea un efecto aleatorio muy potente basado en cajas de regalos."),
            hab(0, TipoHabilidad.BubbleBreath, "Otorga inmunidad temporal contra ataques de estado gaseoso."),
            hab(130, TipoHabilidad.Th4Wall, "Rompe la cuarta pared causando dano absoluto e inevitable.")
        };
    }

    private HabilidadDTO[] habilidadesCoffee() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.GiftBoxes, "Otorga cajas de resurreccion automatica a todo el equipo."),
            hab(110, TipoHabilidad.MegaVirus, "Infecta con un virus informatico que drena salud velozmente."),
            hab(0, TipoHabilidad.Unscrew2, "Inhabilita a las filas enemigas impidiendo su movilidad.")
        };
    }

    private HabilidadDTO[] habilidadesPurpleGuy() {
        return new HabilidadDTO[] {
            hab(0, TipoHabilidad.SpeedSong, "Maximiza la velocidad de carga de turnos del equipo."),
            hab(0, TipoHabilidad.HocusPocus, "Hechizo que transforma a los enemigos en versiones mas debiles."),
            hab(120, TipoHabilidad.Slasher, "Tiene una probabilidad de infligir un dano masivo de un solo golpe.")
        };
    }
}
