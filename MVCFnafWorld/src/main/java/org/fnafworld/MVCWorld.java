/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.fnafworld;

import java.util.ArrayList; 
import java.util.List;
import javax.swing.SwingUtilities;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.HabilidadDTO;
import org.fnafworld.dtos.JugadorDTO;
import org.fnafworld.dominio.fachada.IFachadaJuego;
import org.fnafworld.dominio.fachada.FachadaJuego;
import org.fnafworld.mvc.ControlJuego;
import org.fnafworld.mvc.FrameJuego;
import org.fnafworld.mvc.ModeloJuego;
import org.fnafworld.mvc.vista.AnimatronicoSprite;
import org.fnafworld.mvc.vista.PanelFondoBatalla;
import org.fnafworld.mvc.vista.FrameSimuladorRed;
import org.fnafworld.sonido.AudioManager;

/**
 * * @author lagar
 */
public class MVCWorld {

    public static void main(String[] args) {
        AudioManager audioManager = new AudioManager();
        audioManager.loadMusic("/musica/BossBattle.wav");
        audioManager.playMusicLoop();
        
        List<AnimatronicoSprite> animatronicosVisuales = cargarSpritesMock();

        IFachadaJuego fachadaDominio = new FachadaJuego(null);
        
        ModeloJuego modelo = new ModeloJuego(fachadaDominio);
        ControlJuego control = new ControlJuego(modelo);
        
        PanelFondoBatalla fondoBatallaShared = new PanelFondoBatalla("/escenarios/valle.png", animatronicosVisuales, modelo);
        
        List<JugadorDTO> listaMockJugadores = generarMockJugadores();

        SwingUtilities.invokeLater(() -> {
            FrameJuego framePrincipal = new FrameJuego(audioManager, fondoBatallaShared, control, modelo);
            framePrincipal.setVisible(true);
            
            FrameSimuladorRed simuladorRemoto = new FrameSimuladorRed(modelo, control);
            simuladorRemoto.setVisible(true);
            
            control.arrancarBatalla(listaMockJugadores);
        });
    }

    private static List<AnimatronicoSprite> cargarSpritesMock() {
        List<AnimatronicoSprite> lista = new ArrayList<>();
        
        // --- ANIMATRONICOS ANTERIORES COMENTADOS ---
        /*
        lista.add(new AnimatronicoSprite("Freddy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Bonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Chica", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Foxy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("WitheredFreddy", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("WitheredBonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("WitheredChica", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("WitheredFoxy", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("NightmareFreddy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("NightmareBonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("NightmareChica", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 12, 2, 40, 40));
        lista.add(new AnimatronicoSprite("NightmareFoxy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 12, 2, 40, 40));
        lista.add(new AnimatronicoSprite("ToyFreddy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("ToyBonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("ToyChica", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Mangle", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        */

        // --- NUEVOS ANIMATRONICOS ACTIVADOS ---
        lista.add(new AnimatronicoSprite("BalloonBoy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("JJ", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 16, 2, 40, 40));
        lista.add(new AnimatronicoSprite("PhantomFreddy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("PhantomChica", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("PhantomPuppet", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("PhantomBB", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("PhantomFoxy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("PhantomMangle", 2, 3, 250, 248, 12, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("ShadowFreddy", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Puppet", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("GoldenFreddy", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Paperpals", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("ShadowBonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Endo01", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Endo02", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("EndoPlush", 2, 3, 250, 248, 12, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        
        return lista;
    }

    private static List<JugadorDTO> generarMockJugadores() {
        // --- HABILIDADES ANTERIORES COMENTADAS ---
        /*
        HabilidadDTO[] habFreddy = {
            new HabilidadDTO(35, TipoHabilidad.MicToss, "Lanza el microfono causando daño moderado a un enemigo individual."),
            new HabilidadDTO(50, TipoHabilidad.PizzaWheel, "Invoca una lluvia masiva de pizzas rodantes que daña en area al equipo rival."),
            new HabilidadDTO(0, TipoHabilidad.Birthday, "Otorga un aumento temporal en los atributos de ataque, defensa y velocidad de todos los aliados.")
        };
        HabilidadDTO[] habBonnie = {
            new HabilidadDTO(30, TipoHabilidad.Bite, "Muerde ferozmente a un oponente infligiendo daño fisico directo."),
            new HabilidadDTO(40, TipoHabilidad.BashJam, "Toca un solo de guitarra aturdidor que inflige daño constante en area."),
            new HabilidadDTO(0, TipoHabilidad.HappyJam, "Sana de forma progresiva e instantanea los puntos de salud de todo el equipo aliado.")
        };
        HabilidadDTO[] habChica = {
            new HabilidadDTO(0, TipoHabilidad.Cupcake, "Lanza pastelillos magicos que recuperan la salud total del equipo completo."),
            new HabilidadDTO(0, TipoHabilidad.PartyFavors, "Crea una explosion festiva que causa daño leve al enemigo y cura a los aliados al mismo tiempo."),
            new HabilidadDTO(0, TipoHabilidad.RegenSong, "Entona una melodia que activa la regeneracion de salud pasiva en cada turno.")
        };
        HabilidadDTO[] habFoxy = {
            new HabilidadDTO(45, TipoHabilidad.Hook, "Corta con su garfio metalico ejecutando un daño critico alto a un unico rival."),
            new HabilidadDTO(0, TipoHabilidad.Jumpscare, "Lanza un grito aterrador que paraliza temporalmente las acciones enemigas."),
            new HabilidadDTO(40, TipoHabilidad.HotCheese, "Cubre el piso con queso hirviendo que causa daño por quemaduras en el tiempo.")
        };
        HabilidadDTO[] habWFreddy = {
            new HabilidadDTO(50, TipoHabilidad.MicToss, "Lanza el viejo microfono oxidado causando daño de impacto severo."),
            new HabilidadDTO(0, TipoHabilidad.GloomSong, "Entona un cántico deprimente que reduce el poder de ataque del equipo rival."),
            new HabilidadDTO(60, TipoHabilidad.EscKey, "Una version mejorada que invoca multiples pizzas gigantes que destruyen defensas enemigas.")
        };
        HabilidadDTO[] habWBonnie = {
            new HabilidadDTO(55, TipoHabilidad.EyeBeam, "Una brahmica potente y destructiva que rompe armaduras e inflige alto daño."),
            new HabilidadDTO(65, TipoHabilidad.PizzaWheel, "Dispara lasers de energia desde sus ojos directo a las lineas enemigas."),
            new HabilidadDTO(0, TipoHabilidad.Unscrew, "Tiene una probabilidad del 30% de desmantelar y eliminar instantaneamente a un enemigo.")
        };
        HabilidadDTO[] habWChica = {
            new HabilidadDTO(60, TipoHabilidad.Bite, "Ataca con sus mandibulas rotas causando graves daños de trituracion."),
            new HabilidadDTO(0, TipoHabilidad.Cupcake, "Dispara sorpresas festivas que dañan y curan simultaneamente."),
            new HabilidadDTO(0, TipoHabilidad.PrizeBall2, "Posee un 50% de probabilidad de desarmar y destruir a un oponente mecanico al instante.")
        };
        HabilidadDTO[] habWFoxy = {
            new HabilidadDTO(65, TipoHabilidad.Hook, "Ataque veloz con el garfio que atraviesa defensas fisicas."),
            new HabilidadDTO(0, TipoHabilidad.RainyDay, "Susto repentino que interrumpe los turnos y congela las barras de accion enemigas."),
            new HabilidadDTO(50, TipoHabilidad.HotCheese2, "Lanza una ola masiva de queso derretido hirviendo causando alto daño continuo.")
        };
        HabilidadDTO[] habNFreddy = {
            new HabilidadDTO(70, TipoHabilidad.Bite2, "Una mordida de pesadilla con multiples hileras de dientes afilados."),
            new HabilidadDTO(80, TipoHabilidad.Sludge, "Suelta pequeños sirvientes que atacan freneticamente acumulando rafagas de daño."),
            new HabilidadDTO(90, TipoHabilidad.Freddles, "Invoca pizzas en descomposicion que envenenan e infligen gran daño de area.")
        };
        HabilidadDTO[] habNBonnie = {
            new HabilidadDTO(75, TipoHabilidad.Bite2, "Ataque mandibular salvaje e implacable que destroza los puntos de salud."),
            new HabilidadDTO(0, TipoHabilidad.RainyDay2, "Un grito ensordecedor que inhabilita por completo las acciones del rival."),
            new HabilidadDTO(85, TipoHabilidad.PizzaWheel2, "Lanza una sierra circular giratoria que rebana brutalmente al objetivo.")
        };
        HabilidadDTO[] habNChica = {
            new HabilidadDTO(70, TipoHabilidad.Bite2, "Tritura al enemigo causando un traumatismo critico severo."),
            new HabilidadDTO(90, TipoHabilidad.Waterhose, "Lanza una pizza envenenada que arruina el estado y vitalidad de la escuadra enemiga."),
            new HabilidadDTO(100, TipoHabilidad.BadPizza, "Lanza una calabaza explosiva que estalla causando daño de fuego masivo.")
        };
        HabilidadDTO[] habNFoxy = {
            new HabilidadDTO(80, TipoHabilidad.Bite2, "Abalanzamiento feroz con colmillos expuestos infligiendo daño masivo."),
            new HabilidadDTO(0, TipoHabilidad.Unscrew2, "Aterroriza a las filas enemigas impidiendo su movilidad durante un ciclo."),
            new HabilidadDTO(95, TipoHabilidad.HotCheese2, "Inunda el campo de batalla con magma quesero infligiendo un daño por segundo devastador.")
        };
        HabilidadDTO[] habTFreddy = {
            new HabilidadDTO(30, TipoHabilidad.MicToss, "Lanza el microfono de plastico infligiendo daño basico constante."),
            new HabilidadDTO(0, TipoHabilidad.PartyFavors, "Dispara confeti curativo que otorga soporte leve al equipo."),
            new HabilidadDTO(40, TipoHabilidad.SpeedSong, "Aumenta fuerza y armadura en +45 a 1 aliado aleatorio durante 8 turnos.")
        };
        HabilidadDTO[] habTBonnie = {
            new HabilidadDTO(35, TipoHabilidad.BashJam, "Interpreta una nota estruendosa desestabilizando al oponente."),
            new HabilidadDTO(0, TipoHabilidad.Munchies, "Daño pasivo de 14/turno a TODOS los enemigos y 4 aliados aleatorios."),
            new HabilidadDTO(0, TipoHabilidad.PrizeBall, "Invoca una esfera sorpresa que ejecuta una habilidad aleatoria de nivel bajo.")
        };
        HabilidadDTO[] habTChica = {
            new HabilidadDTO(0, TipoHabilidad.Cupcake, "Sana la salud de la formacion con deliciosos panecillos rosados."),
            new HabilidadDTO(0, TipoHabilidad.Birthday, "Festeja aumentando momentaneamente las estadisticas globales del escuadron."),
            new HabilidadDTO(45, TipoHabilidad.Waterhose, "Rocia agua a alta presion limpiando estados negativos y dañando enemigos debiles.")
        };
        HabilidadDTO[] habMangle = {
            new HabilidadDTO(40, TipoHabilidad.Bite, "Ataque directo utilizando sus piezas desarmadas para morder."),
            new HabilidadDTO(0, TipoHabilidad.Poppers, "Coloca pequeñas minas sorpresa que explotan cuando un enemigo intenta atacar."),
            new HabilidadDTO(0, TipoHabilidad.PrizeBall, "Despliega una capsula de premio menor que desata ataques aleatorios de menor rango.")
        };
        */

        // --- NUEVAS HABILIDADES PARA NUEVOS ANIMATRONICOS ---
        HabilidadDTO[] habBB = {
            new HabilidadDTO(25, TipoHabilidad.Balloons, "Invoca globos que dañan objetivos múltiples."),
            new HabilidadDTO(35, TipoHabilidad.HotCheese, "Derrama queso caliente para causar daño de quemadura."),
            new HabilidadDTO(20, TipoHabilidad.Munchies, "Invoca pequeñas criaturas mordedoras pasivas.")
        };
        HabilidadDTO[] habJJ = {
            new HabilidadDTO(25, TipoHabilidad.Balloons, "Dispara globos para distraer y atacar."),
            new HabilidadDTO(30, TipoHabilidad.Poppers, "Coloca explosivos festivos en el campo."),
            new HabilidadDTO(0, TipoHabilidad.Unscrew, "Tiene probabilidad de desmantelar instantáneamente al enemigo.")
        };
        HabilidadDTO[] habPhantomFreddy = {
            new HabilidadDTO(0, TipoHabilidad.GloomSong, "Canta una melodía deprimente que baja el ataque rival."),
            new HabilidadDTO(40, TipoHabilidad.Sludge, "Lanza lodo que ralentiza drásticamente a los enemigos."),
            new HabilidadDTO(0, TipoHabilidad.RainyDay, "Lluvia tormentosa que debilita las defensas contrarias.")
        };
        HabilidadDTO[] habPhantomChica = {
            new HabilidadDTO(45, TipoHabilidad.ToxicBite, "Muerde envenenando al objetivo en el tiempo."),
            new HabilidadDTO(40, TipoHabilidad.Sludge, "Lodo pesado que reduce la velocidad del enemigo."),
            new HabilidadDTO(0, TipoHabilidad.Unscrew, "Probabilidad de desarmar por completo a un oponente.")
        };
        HabilidadDTO[] habPhantomBB = {
            new HabilidadDTO(35, TipoHabilidad.ToxicBalloon, "Lanza globos tóxicos que infligen veneno."),
            new HabilidadDTO(0, TipoHabilidad.GloomBalloon, "Globo fantasmal que reduce el ataque del enemigo."),
            new HabilidadDTO(0, TipoHabilidad.RainyDay, "Tormenta que disminuye estadísticas de la escuadra rival.")
        };
        HabilidadDTO[] habPhantomFoxy = {
            new HabilidadDTO(0, TipoHabilidad.Jumpscare, "Susto repentino que paraliza al contrincante."),
            new HabilidadDTO(50, TipoHabilidad.ToxicBite, "Feroz mordisco cargado de toxinas."),
            new HabilidadDTO(0, TipoHabilidad.Unscrew, "Intento mecánico de desmantelamiento instantáneo.")
        };
        HabilidadDTO[] habPhantomMangle = {
            new HabilidadDTO(40, TipoHabilidad.ToxicBite, "Mordida ácida que drena vida por turno."),
            new HabilidadDTO(0, TipoHabilidad.MisteryBox, "Cambia los enemigos por otros aleatorios de menor nivel."),
            new HabilidadDTO(60, TipoHabilidad.PizzaWheel2, "Sierras masivas de pizza que cortan al equipo rival.")
        };
        HabilidadDTO[] habShadowFreddy = {
            new HabilidadDTO(0, TipoHabilidad.Unscrew, "Desmantela piezas clave enemigas con un porcentaje de éxito."),
            new HabilidadDTO(70, TipoHabilidad.EscKey, "Lanza comandos que pueden borrar instantáneamente a múltiples rivales."),
            new HabilidadDTO(50, TipoHabilidad.Waterhose, "Chorro de agua potente que barre enemigos con baja salud.")
        };
        HabilidadDTO[] habPuppet = {
            new HabilidadDTO(0, TipoHabilidad.PrizeBall2, "Crea un efecto aleatorio muy potente basado en cajas de regalos."),
            new HabilidadDTO(0, TipoHabilidad.MisteryBox, "Reemplaza al equipo enemigo al azar."),
            new HabilidadDTO(80, TipoHabilidad.EscKey, "Ataque fulminante de desinstalación virtual.")
        };
        HabilidadDTO[] habPhantomPuppet = {
            new HabilidadDTO(0, TipoHabilidad.Jumpscare, "Paralización total de la barra de acción del rival."),
            new HabilidadDTO(55, TipoHabilidad.HotCheese2, "Gran oleada de queso hirviendo continuo."),
            new HabilidadDTO(0, TipoHabilidad.MisteryBox2, "Invoca una caja de misterio avanzada desordenando al enemigo.")
        };
        HabilidadDTO[] habGoldenFreddy = {
            new HabilidadDTO(0, TipoHabilidad.Jumpscare, "Susto espectral que aturde a todos los enemigos."),
            new HabilidadDTO(0, TipoHabilidad.RainyDay2, "Tormenta de rayos pesada que drena armadura y ataque."),
            new HabilidadDTO(0, TipoHabilidad.Haunting, "Fantasma que paraliza permanentemente a un enemigo.")
        };
        HabilidadDTO[] habPaperPals = {
            new HabilidadDTO(0, TipoHabilidad.PrizeBall2, "Efectos combinados aleatorios de alto nivel."),
            new HabilidadDTO(0, TipoHabilidad.MisteryBox2, "Baraja las posiciones y tipos de enemigos drásticamente."),
            new HabilidadDTO(0, TipoHabilidad.MimicBall, "Genera una esfera que duplica todos los ataques aliados.")
        };
        HabilidadDTO[] habEndo01 = {
            new HabilidadDTO(0, TipoHabilidad.PowerSong, "Aumenta considerablemente el daño de ataque de todo el equipo."),
            new HabilidadDTO(0, TipoHabilidad.ArmorSong, "Incrementa la defensa global del escuadrón mitigando daño."),
            new HabilidadDTO(65, TipoHabilidad.EndoArmy, "Invoca un ejército de endoesqueletos láser automáticos.")
        };
        HabilidadDTO[] habEndo02 = {
            new HabilidadDTO(0, TipoHabilidad.SpeedSong, "Maximiza la velocidad de carga de turnos del equipo."),
            new HabilidadDTO(75, TipoHabilidad.EndoArmy, "Ejército metálico continuo que asiste disparando."),
            new HabilidadDTO(0, TipoHabilidad.NeonWall, "Crea un escudo de luz que bloquea un porcentaje del daño entrante.")
        };
        HabilidadDTO[] habEndoplush = {
            new HabilidadDTO(0, TipoHabilidad.NeonWall, "Barrera defensiva de neón inmune a ataques críticos."),
            new HabilidadDTO(60, TipoHabilidad.EyeBeam, "Láser ocular concentrado que inflige gran daño perforante."),
            new HabilidadDTO(70, TipoHabilidad.Waterhose2, "Ráfaga masiva de agua purificadora que liquida enemigos débiles.")
        };
        HabilidadDTO[] habShadowBonnie = {
            new HabilidadDTO(0, TipoHabilidad.Haunting, "Aterroriza y congela las acciones de un objetivo."),
            new HabilidadDTO(0, TipoHabilidad.MimicBall, "Duplica instantáneamente cada habilidad ofensiva ejecutada."),
            new HabilidadDTO(0, TipoHabilidad.GiftBoxes, "Otorga cajas de resurrección automática a todo el equipo.")
        };

        // --- GRUPOS DE JUGADORES ORIGINALES COMENTADOS (DOCUMENTACIÓN) ---
        /*
        AnimatronicoDTO[] grupoJ1 = {
            new AnimatronicoDTO("Freddy", TipoAnimatronico.Freddy, true, 50, 1100, 1100, habFreddy, true),
            new AnimatronicoDTO("Bonnie", TipoAnimatronico.Bonnie, false, 45, 1100, 1100, habBonnie, true),
            new AnimatronicoDTO("Chica", TipoAnimatronico.Chica, false, 30, 1100, 1100, habChica, true),
            new AnimatronicoDTO("Foxy", TipoAnimatronico.Foxy, false, 60, 1100, 1100, habFoxy, true)
        };
        AnimatronicoDTO[] grupoJ2 = {
            new AnimatronicoDTO("WitheredFreddy", TipoAnimatronico.WitheredFreddy, false, 70, 1120, 1120, habWFreddy, true),
            new AnimatronicoDTO("WitheredBonnie", TipoAnimatronico.WitheredBonnie, false, 65, 1110, 1110, habWBonnie, true),
            new AnimatronicoDTO("WitheredChica", TipoAnimatronico.WitheredChica, false, 40, 1120, 1120, habWChica, true),
            new AnimatronicoDTO("WitheredFoxy", TipoAnimatronico.WitheredFoxy, false, 80, 1090, 1090, habWFoxy, true)
        };
        AnimatronicoDTO[] grupoJ3 = {
            new AnimatronicoDTO("NightmareFreddy", TipoAnimatronico.NightmareFreddy, false, 80, 1140, 1140, habNFreddy, true),
            new AnimatronicoDTO("NightmareBonnie", TipoAnimatronico.NightmareBonnie, false, 75, 1130, 1130, habNBonnie, true),
            new AnimatronicoDTO("NightmareChica", TipoAnimatronico.NightmareChica, false, 70, 1135, 1135, habNChica, true),
            new AnimatronicoDTO("NightmareFoxy", TipoAnimatronico.NightmareFoxy, false, 85, 1110, 1110, habNFoxy, true)
        };
        AnimatronicoDTO[] grupoJ4 = {
            new AnimatronicoDTO("ToyFreddy", TipoAnimatronico.ToyFreddy, false, 45, 1090, 1090, habTFreddy, true),
            new AnimatronicoDTO("ToyBonnie", TipoAnimatronico.ToyBonnie, false, 50, 1085, 1085, habTBonnie, true),
            new AnimatronicoDTO("ToyChica", TipoAnimatronico.ToyChica, false, 40, 1095, 1095, habTChica, true),
            new AnimatronicoDTO("Mangle", TipoAnimatronico.Mangle, false, 55, 1080, 1080, habMangle, true)
        };
        */

        // --- NUEVOS GRUPOS CON LOS ANIMATRONICOS A PROBAR ---
        AnimatronicoDTO[] grupoJ1 = {
            new AnimatronicoDTO("Balloon Boy", TipoAnimatronico.BalloonBoy, true, 35, 1050, 1050, habBB, true),
            new AnimatronicoDTO("JJ", TipoAnimatronico.JJ, false, 38, 1050, 1050, habJJ, true),
            new AnimatronicoDTO("Phantom Freddy", TipoAnimatronico.PhantomFreddy, false, 45, 1100, 1100, habPhantomFreddy, true),
            new AnimatronicoDTO("Phantom Chica", TipoAnimatronico.PhantomChica, false, 42, 1100, 1100, habPhantomChica, true)
        };
        
        AnimatronicoDTO[] grupoJ2 = {
            new AnimatronicoDTO("Phantom BB", TipoAnimatronico.PhantomBB, false, 40, 1080, 1080, habPhantomBB, true),
            new AnimatronicoDTO("Phantom Foxy", TipoAnimatronico.PhantomFoxy, false, 52, 1110, 1110, habPhantomFoxy, true),
            new AnimatronicoDTO("Phantom Mangle", TipoAnimatronico.PhantomMangle, false, 48, 1090, 1090, habPhantomMangle, true),
            new AnimatronicoDTO("Shadow Freddy", TipoAnimatronico.ShadowFreddy, false, 65, 1200, 1200, habShadowFreddy, true)
        };

        AnimatronicoDTO[] grupoJ3 = {
            new AnimatronicoDTO("Marionette", TipoAnimatronico.Puppet, false, 70, 1250, 1250, habPuppet, true),
            new AnimatronicoDTO("Phantom Marionette", TipoAnimatronico.PhantomPuppet, false, 68, 1200, 1200, habPhantomPuppet, true),
            new AnimatronicoDTO("Golden Freddy", TipoAnimatronico.GoldenFreddy, false, 85, 1300, 1300, habGoldenFreddy, true),
            new AnimatronicoDTO("Paper Pals", TipoAnimatronico.Paperpals, false, 30, 950, 950, habPaperPals, true)
        };

        AnimatronicoDTO[] grupoJ4 = {
            new AnimatronicoDTO("Endo 01", TipoAnimatronico.Endo01, false, 50, 1150, 1150, habEndo01, true),
            new AnimatronicoDTO("Endo 02", TipoAnimatronico.Endo02, false, 55, 1180, 1180, habEndo02, true),
            new AnimatronicoDTO("Endoplush", TipoAnimatronico.Endoplush, false, 40, 1020, 1020, habEndoplush, true),
            new AnimatronicoDTO("Shadow Bonnie", TipoAnimatronico.ShadowBonnie, false, 75, 1200, 1200, habShadowBonnie, true)
        };
        
        List<JugadorDTO> listaMockJugadores = new ArrayList<>();
        listaMockJugadores.add(new JugadorDTO("0", "Mondongo", "/avatars/mondongo.jpg", grupoJ1, true, Equipo.Rojo));
        listaMockJugadores.add(new JugadorDTO("1", "El gato", "/avatars/gatomojado.jpg", grupoJ2, false, Equipo.Azul));
        listaMockJugadores.add(new JugadorDTO("2", "ohhhh", "/avatars/oruga.jpg", grupoJ3, false, Equipo.Rojo));
        listaMockJugadores.add(new JugadorDTO("3", "pablo", "/avatars/pabloncho.jpg", grupoJ4, false, Equipo.Azul));
        
        return listaMockJugadores;
    }
}