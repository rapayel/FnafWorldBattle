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
import org.fnafworld.mvc.ControlJuego;
import org.fnafworld.mvc.FrameJuego;
import org.fnafworld.mvc.ModeloJuego;
import org.fnafworld.mvc.vista.AnimatronicoSprite;
import org.fnafworld.mvc.vista.PanelFondoBatalla;
import org.fnafworld.sonido.AudioManager;

public class MVCWorld {

    public static void main(String[] args) {
        AudioManager a = new AudioManager();
        a.loadMusic("/musica/BossStoneCold.wav");
        a.playMusicLoop();
        
        List<AnimatronicoSprite> animatronicosVisuales = new ArrayList<>();
        animatronicosVisuales.add(new AnimatronicoSprite("Freddy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("Bonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("Chica", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("Foxy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("WitheredFreddy", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("WitheredBonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("WitheredChica", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("WitheredFoxy", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("NightmareFreddy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("NightmareBonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("NightmareChica", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 12, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("NightmareFoxy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 12, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("ToyFreddy", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("ToyBonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("ToyChica", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        animatronicosVisuales.add(new AnimatronicoSprite("Mangle", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 11, 2, 40, 40));

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
            new HabilidadDTO(60, TipoHabilidad.PizzaWheel2, "Una version mejorada que invoca multiples pizzas gigantes que destruyen defensas enemigas.")
        };
        HabilidadDTO[] habWBonnie = {
            new HabilidadDTO(55, TipoHabilidad.Bite2, "Una mordida potente y destructiva que rompe armaduras e inflige alto daño."),
            new HabilidadDTO(65, TipoHabilidad.EyeBeam, "Dispara lasers de energia desde sus ojos directo a las lineas enemigas."),
            new HabilidadDTO(0, TipoHabilidad.Unscrew, "Tiene una probabilidad del 30% de desmantelar y eliminar instantaneamente a un enemigo.")
        };
        HabilidadDTO[] habWChica = {
            new HabilidadDTO(60, TipoHabilidad.Bite2, "Ataca con sus mandibulas rotas causando graves daños de trituracion."),
            new HabilidadDTO(0, TipoHabilidad.PartyFavors, "Dispara sorpresas festivas que dañan y curan simultaneamente."),
            new HabilidadDTO(0, TipoHabilidad.Unscrew2, "Posee un 50% de probabilidad de desarmar y destruir a un oponente mecanico al instante.")
        };
        HabilidadDTO[] habWFoxy = {
            new HabilidadDTO(65, TipoHabilidad.Hook, "Ataque veloz con el garfio que atraviesa defensas fisicas."),
            new HabilidadDTO(0, TipoHabilidad.Jumpscare, "Susto repentino que interrumpe los turnos y congela las barras de accion enemigas."),
            new HabilidadDTO(50, TipoHabilidad.HotCheese2, "Lanza una ola masiva de queso derretido hirviendo causando alto daño continuo.")
        };

        HabilidadDTO[] habNFreddy = {
            new HabilidadDTO(70, TipoHabilidad.Bite2, "Una mordida de pesadilla con multiples hileras de dientes afilados."),
            new HabilidadDTO(80, TipoHabilidad.Freddles, "Suelta pequeños sirvientes que atacan freneticamente acumulando rafagas de daño."),
            new HabilidadDTO(90, TipoHabilidad.BadPizza, "Invoca pizzas en descomposicion que envenenan e infligen gran daño de area.")
        };
        HabilidadDTO[] habNBonnie = {
            new HabilidadDTO(75, TipoHabilidad.Bite2, "Ataque mandibular salvaje e implacable que destroza los puntos de salud."),
            new HabilidadDTO(0, TipoHabilidad.Jumpscare, "Un grito ensordecedor que inhabilita por completo las acciones del rival."),
            new HabilidadDTO(85, TipoHabilidad.Buzzsaw, "Lanza una sierra circular giratoria que rebana brutalmente al objetivo.")
        };
        HabilidadDTO[] habNChica = {
            new HabilidadDTO(70, TipoHabilidad.Bite2, "Tritura al enemigo causando un traumatismo critico severo."),
            new HabilidadDTO(90, TipoHabilidad.BadPizza, "Lanza una pizza envenenada que arruina el estado y vitalidad de la escuadra enemiga."),
            new HabilidadDTO(100, TipoHabilidad.JackOBomb, "Lanza una calabaza explosiva que estalla causando daño de fuego masivo.")
        };
        HabilidadDTO[] habNFoxy = {
            new HabilidadDTO(80, TipoHabilidad.Bite2, "Abalanzamiento feroz con colmillos expuestos infligiendo daño masivo."),
            new HabilidadDTO(0, TipoHabilidad.Jumpscare, "Aterroriza a las filas enemigas impidiendo su movilidad durante un ciclo."),
            new HabilidadDTO(95, TipoHabilidad.HotCheese2, "Inunda el campo de batalla con magma quesero infligiendo un daño por segundo devastador.")
        };

        HabilidadDTO[] habTFreddy = {
            new HabilidadDTO(30, TipoHabilidad.MicToss, "Lanza el microfono de plastico infligiendo daño basico constante."),
            new HabilidadDTO(0, TipoHabilidad.PartyFavors, "Dispara confeti curativo que otorga soporte leve al equipo."),
            new HabilidadDTO(40, TipoHabilidad.PizzaWheel, "Despliega una rueda de pizzas medianas para golpear de forma grupal.")
        };
        HabilidadDTO[] habTBonnie = {
            new HabilidadDTO(35, TipoHabilidad.BashJam, "Interpreta una nota estruendosa desestabilizando al oponente."),
            new HabilidadDTO(0, TipoHabilidad.HappyJam2, "Toca una cancion sumamente alegre que sana de golpe grandes cantidades de vida."),
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
            new HabilidadDTO(0, TipoHabilidad.PrizeBall2, "Despliega una capsula de premio mayor que desata ataques aleatorios de alto rango.")
        };

        AnimatronicoDTO[] grupoJ1 = {
            new AnimatronicoDTO("Freddy", TipoAnimatronico.Freddy, true, 50, 100, 100, habFreddy, true),
            new AnimatronicoDTO("Bonnie", TipoAnimatronico.Bonnie, false, 45, 100, 100, habBonnie, true),
            new AnimatronicoDTO("Chica", TipoAnimatronico.Chica, false, 30, 100, 100, habChica, true),
            new AnimatronicoDTO("Foxy", TipoAnimatronico.Foxy, false, 60, 100, 100, habFoxy, true)
        };
        
        AnimatronicoDTO[] grupoJ2 = {
            new AnimatronicoDTO("WitheredFreddy", TipoAnimatronico.WitheredFreddy, false, 70, 120, 120, habWFreddy, true),
            new AnimatronicoDTO("WitheredBonnie", TipoAnimatronico.WitheredBonnie, false, 65, 110, 110, habWBonnie, true),
            new AnimatronicoDTO("WitheredChica", TipoAnimatronico.WitheredChica, false, 40, 120, 120, habWChica, true),
            new AnimatronicoDTO("WitheredFoxy", TipoAnimatronico.WitheredFoxy, false, 80, 90, 90, habWFoxy, true)
        };

        AnimatronicoDTO[] grupoJ3 = {
            new AnimatronicoDTO("NightmareFreddy", TipoAnimatronico.NightmareFreddy, false, 80, 140, 140, habNFreddy, true),
            new AnimatronicoDTO("NightmareBonnie", TipoAnimatronico.NightmareBonnie, false, 75, 130, 130, habNBonnie, true),
            new AnimatronicoDTO("NightmareChica", TipoAnimatronico.NightmareChica, false, 70, 135, 135, habNChica, true),
            new AnimatronicoDTO("NightmareFoxy", TipoAnimatronico.NightmareFoxy, false, 85, 110, 110, habNFoxy, true)
        };

        AnimatronicoDTO[] grupoJ4 = {
            new AnimatronicoDTO("ToyFreddy", TipoAnimatronico.ToyFreddy, false, 45, 90, 90, habTFreddy, true),
            new AnimatronicoDTO("ToyBonnie", TipoAnimatronico.ToyBonnie, false, 50, 85, 85, habTBonnie, true),
            new AnimatronicoDTO("ToyChica", TipoAnimatronico.ToyChica, false, 40, 95, 95, habTChica, true),
            new AnimatronicoDTO("Mangle", TipoAnimatronico.Mangle, false, 55, 80, 80, habMangle, true)
        };

        List<JugadorDTO> listaMockJugadores = new ArrayList<>();
        listaMockJugadores.add(new JugadorDTO("0", "Lagarto_Rojo1", "/avatars/mondongo.jpg", grupoJ1, true, Equipo.Rojo));
        listaMockJugadores.add(new JugadorDTO("1", "Slayer_Blue1", "/avatars/mondongo.jpg", grupoJ2, false, Equipo.Azul));
        listaMockJugadores.add(new JugadorDTO("2", "Lagarto_Rojo2", "/avatars/mondongo.jpg", grupoJ3, false, Equipo.Rojo));
        listaMockJugadores.add(new JugadorDTO("3", "Slayer_Blue2", "/avatars/mondongo.jpg", grupoJ4, false, Equipo.Azul));
        
        ModeloJuego modelo = new ModeloJuego();
        ControlJuego control = new ControlJuego(modelo);
        PanelFondoBatalla fondoBatalla = new PanelFondoBatalla("/escenarios/valle.png", animatronicosVisuales);
        
        SwingUtilities.invokeLater(() -> {
            FrameJuego frame = new FrameJuego(a, fondoBatalla, control, modelo);
            control.arrancarBatalla(listaMockJugadores);
        });
    }
}