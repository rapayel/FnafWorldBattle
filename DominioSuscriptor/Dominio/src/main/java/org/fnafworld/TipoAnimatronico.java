/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package org.fnafworld;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.fnafworld.dominio.entidades.Habilidad; 
/**
 * 
 * @author lagar
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TipoAnimatronico {
    Freddy(50, 1000, new Habilidad[]{ 
        new Habilidad(35, TipoHabilidad.MicToss, "Golpea a un enemigo aleatorio con daño variable (entre 45 y 85)."),
        new Habilidad(50, TipoHabilidad.PizzaWheel, "Ataca a todos los enemigos (95 daño base), pero también daña al atacante y hasta 3 aliados."),
        new Habilidad(0, TipoHabilidad.Birthday, "Aumenta fuerza y armadura de hasta 4 aliados aleatorios en 10.")
    }),  
    Bonnie(45, 1000, new Habilidad[]{ 
        new Habilidad(30, TipoHabilidad.Bite,  "Muerde a un enemigo aleatorio con 95 de daño fijo."),
        new Habilidad(40, TipoHabilidad.BashJam, "Golpea hasta 4 enemigos aleatorios con daño alto, pero el atacante recibe 55 de daño a sí mismo."),
        new Habilidad(0, TipoHabilidad.HappyJam, "Cura a hasta 2 aliados aleatorios con más potencia.")
    }),
    Chica(40,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.Cupcake,  "Cura a hasta 4 aliados aleatorios."),
        new Habilidad(0, TipoHabilidad.PartyFavors, "Cura a todos los aliados el 15% de su vida actual."),
        new Habilidad(0, TipoHabilidad.RegenSong, "Aplica regeneración de vida por turno a todos los aliados.")
    }),
    Foxy(55, 1000, new Habilidad[]{
        new Habilidad(45, TipoHabilidad.Hook,  "Ataca a un enemigo aleatorio con daño muy variable (entre 20 y 115)."),
        new Habilidad(0, TipoHabilidad.Jumpscare, "Noquea a hasta 3 enemigos aleatorios."),
        new Habilidad(40, TipoHabilidad.HotCheese, "Aplica daño pasivo a 3 enemigos aleatorios; el atacante también recibe daño pasivo a sí mismo.")
    }),
    ToyFreddy(45, 1000, new Habilidad[]{ 
        new Habilidad(30, TipoHabilidad.MicToss, "Golpea a un enemigo aleatorio con daño variable (entre 45 y 85)."),
        new Habilidad(0, TipoHabilidad.PartyFavors, "Cura a todos los aliados el 15% de su vida actual."),
        new Habilidad(40, TipoHabilidad.SpeedSong, "Aumenta fuerza y armadura de un aliado aleatorio en 45.")
    }),  
    ToyBonnie(42, 1000, new Habilidad[]{ 
        new Habilidad(35, TipoHabilidad.BashJam,  "Golpea hasta 4 enemigos aleatorios con daño alto, pero el atacante recibe 55 de daño a sí mismo."),
        new Habilidad(0, TipoHabilidad.Munchies, "Aplica daño pasivo a todos los enemigos y también a 4 aliados aleatorios."),
        new Habilidad(0, TipoHabilidad.PrizeBall, "Ejecuta una habilidad aleatoria de la lista básica (habilidades de nivel normal).")
    }),
    ToyChica(38,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.Cupcake,  "Cura a hasta 4 aliados aleatorios."),
        new Habilidad(0, TipoHabilidad.Birthday, "Aumenta fuerza y armadura de hasta 4 aliados aleatorios en 10."),
        new Habilidad(45, TipoHabilidad.Waterhose, "Elimina hasta 3 enemigos que tengan 10% o menos de vida.")
    }),
    Mangle(48, 1000, new Habilidad[]{
        new Habilidad(40, TipoHabilidad.Hook,  "Ataca a un enemigo aleatorio con daño muy variable (entre 20 y 115)."),
        new Habilidad(0, TipoHabilidad.Poppers, "Aplica daño retardado de 300 a hasta 2 enemigos; el daño se activa más tarde."),
        new Habilidad(0, TipoHabilidad.PrizeBall, "Ejecuta una habilidad aleatoria de la lista básica (habilidades de nivel normal).")
    }),
    BalloonBoy(30,1000, new Habilidad[]{
        new Habilidad(25, TipoHabilidad.Cupcake,  "Cura a hasta 4 aliados aleatorios."),
        new Habilidad(35, TipoHabilidad.Birthday, "Aumenta fuerza y armadura de hasta 4 aliados aleatorios en 10."),
        new Habilidad(20, TipoHabilidad.Waterhose, "Elimina hasta 3 enemigos que tengan 10% o menos de vida.")
    }),
    JJ(30, 1000, new Habilidad[]{
        new Habilidad(25, TipoHabilidad.Balloons,  "Ataca hasta 3 enemigos aleatorios con posibilidad de retroceso del atacante."),
        new Habilidad(30, TipoHabilidad.Poppers, "Aplica daño retardado de 300 a hasta 2 enemigos; el daño se activa más tarde."),
        new Habilidad(0, TipoHabilidad.Unscrew, "30% de probabilidad de eliminar instantáneamente a 1 enemigo aleatorio.")
    }),
    PhantomFreddy(55, 1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.GloomSong,  "Reduce 20 de fuerza a todos los enemigos por varios turnos."),
        new Habilidad(40, TipoHabilidad.Sludge, "Elimina el poder de las habilidades de un enemigo aleatorio temporalmente."),
        new Habilidad(0, TipoHabilidad.RainyDay, "Reduce la armadura de hasta 4 enemigos aleatorios.")
    }),
    PhantomChica(50, 1000, new Habilidad[]{
        new Habilidad(45, TipoHabilidad.ToxicBite,  "Muerde a un enemigo (95 daño) y lo envenena."),
        new Habilidad(40, TipoHabilidad.Sludge, "Elimina el poder de las habilidades de un enemigo aleatorio temporalmente."),
        new Habilidad(0, TipoHabilidad.Unscrew, "30% de probabilidad de eliminar instantáneamente a 1 enemigo aleatorio.")
    }),
    PhantomFoxy(60, 1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.Jumpscare, "Noquea a hasta 3 enemigos aleatorios."),
        new Habilidad(50, TipoHabilidad.ToxicBite,  "Muerde a un enemigo (95 daño) y lo envenena."),
        new Habilidad(0, TipoHabilidad.Unscrew, "30% de probabilidad de eliminar instantáneamente a 1 enemigo aleatorio.")
    }),
    PhantomBB(45, 1000, new Habilidad[]{
        new Habilidad(35, TipoHabilidad.ToxicBalloon,  "Envenena a hasta 5 enemigos aleatorios con daño por turno."),
        new Habilidad(0, TipoHabilidad.GloomBalloon, "Reduce 45 de fuerza a un enemigo aleatorio por varios turnos."),
        new Habilidad(0, TipoHabilidad.RainyDay, "Reduce la armadura de hasta 4 enemigos aleatorios.")
    }),
    PhantomMangle(60, 1000, new Habilidad[]{
        new Habilidad(40, TipoHabilidad.ToxicBite,  "Muerde a un enemigo (95 daño) y lo envenena."),
        new Habilidad(0, TipoHabilidad.MisteryBox, "El atacante se transforma en un animatrónico aleatorio del campo enemigo."),
        new Habilidad(60, TipoHabilidad.PizzaWheel2, "Versión más potente de PizzaWheel (140 daño base) con castigo mayor.")
    }),
    PhantomPuppet(70, 1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.Jumpscare, "Noquea a hasta 3 enemigos aleatorios."),
        new Habilidad(55, TipoHabilidad.HotCheese2, "Igual que HotCheese pero con más daño pasivo."),
        new Habilidad(0, TipoHabilidad.MisteryBox2, "Transforma a un enemigo aleatorio en la forma de un aliado.")
    }),
    WitheredFreddy(52, 1000, new Habilidad[]{
        new Habilidad(50, TipoHabilidad.MicToss,  "Golpea a un enemigo aleatorio con daño variable (entre 45 y 85)."),
        new Habilidad(0, TipoHabilidad.GloomSong, "Reduce 20 de fuerza a todos los enemigos por varios turnos."),
        new Habilidad(60, TipoHabilidad.EscKey, "3% de probabilidad de eliminar hasta 4 enemigos a la vez.")
    }),
    WitheredBonnie(68, 1000, new Habilidad[]{
        new Habilidad(55, TipoHabilidad.EyeBeam,  "Dispara un rayo a un enemigo aleatorio con 280 de daño, con 50% de probabilidad de empujar al atacante hacia atrás."),
        new Habilidad(65, TipoHabilidad.PizzaWheel, "Ataca a todos los enemigos (95 daño base), pero también daña al atacante y hasta 3 aliados."),
        new Habilidad(0, TipoHabilidad.Unscrew, "30% de probabilidad de eliminar instantáneamente a 1 enemigo aleatorio.")
    }),
    WitheredChica(62, 1000, new Habilidad[]{
        new Habilidad(60, TipoHabilidad.Bite,  "Muerde a un enemigo aleatorio con 95 de daño fijo."),
        new Habilidad(0, TipoHabilidad.Cupcake, "Cura a hasta 4 aliados aleatorios."),
        new Habilidad(0, TipoHabilidad.PrizeBall, "Ejecuta una habilidad aleatoria de la lista básica (habilidades de nivel normal).")
    }),
    WitheredFoxy(75, 1000, new Habilidad[]{
        new Habilidad(65, TipoHabilidad.Hook,  "Muerde a un enemigo aleatorio con 95 de daño fijo."),
        new Habilidad(0, TipoHabilidad.RainyDay, "Reduce la armadura de hasta 4 enemigos aleatorios."),
        new Habilidad(50, TipoHabilidad.HotCheese2, "Ejecuta una habilidad aleatoria de la lista básica (habilidades de nivel normal).")
    }),
    ShadowFreddy(70, 1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.Unscrew, "30% de probabilidad de eliminar instantáneamente a 1 enemigo aleatorio."),
        new Habilidad(70, TipoHabilidad.EscKey, "3% de probabilidad de eliminar hasta 4 enemigos a la vez."),
        new Habilidad(50, TipoHabilidad.Waterhose, "Elimina hasta 3 enemigos que tengan 10% o menos de vida.")
    }),
    Puppet(65,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.PrizeBall2,"Ejecuta una habilidad aleatoria de la lista avanzada (habilidades más poderosas)."),
        new Habilidad(0, TipoHabilidad.MisteryBox, "El atacante se transforma en un animatrónico aleatorio del campo enemigo."),
        new Habilidad(80, TipoHabilidad.EscKey, "3% de probabilidad de eliminar hasta 4 enemigos a la vez."),
    }),
    GoldenFreddy(80, 1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.Jumpscare, "Noquea a hasta 3 enemigos aleatorios."),
        new Habilidad(0, TipoHabilidad.RainyDay2, "Reduce la armadura de todos los enemigos y les inflige daño pequeño."),
        new Habilidad(0, TipoHabilidad.Haunting, "Noquea a 1 enemigo aleatorio (pierde su turno).")
    }),
    PapperPals(35, 1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.PrizeBall2,"Ejecuta una habilidad aleatoria de la lista avanzada (habilidades más poderosas)."),
        new Habilidad(0, TipoHabilidad.MisteryBox2, "Transforma a un enemigo aleatorio en la forma de un aliado."),
        new Habilidad(0, TipoHabilidad.MimicBall, "Copia la habilidad de un aliado aleatorio y la ejecuta.")
    }),
    NightmareFreddy(85, 1000, new Habilidad[]{
        new Habilidad(70, TipoHabilidad.Bite2,"Muerde a hasta 3 enemigos aleatorios con daño moderado."),
        new Habilidad(80, TipoHabilidad.Sludge, "Elimina el poder de las habilidades de un enemigo aleatorio temporalmente."),
        new Habilidad(90, TipoHabilidad.Freddles, "Daña a todos los animatrónicos en el campo (aliados y enemigos).")
    }),
    NightmareBonnie(88, 1000, new Habilidad[]{
        new Habilidad(75, TipoHabilidad.Bite2,"Muerde a hasta 3 enemigos aleatorios con daño moderado."),
        new Habilidad(0, TipoHabilidad.RainyDay2, "Reduce la armadura de todos los enemigos y les inflige daño pequeño."),
        new Habilidad(85, TipoHabilidad.PizzaWheel2, "Versión más potente de PizzaWheel (140 daño base) con castigo mayor.")
    }),
    NightmareChica(82, 1000, new Habilidad[]{
        new Habilidad(70, TipoHabilidad.Bite2,"Muerde a hasta 3 enemigos aleatorios con daño moderado."),
        new Habilidad(90, TipoHabilidad.Waterhose, "Elimina hasta 3 enemigos que tengan 10% o menos de vida."),
        new Habilidad(100, TipoHabilidad.BadPizza, "Envenena a todos los enemigos con daño pasivo por turno.")
    }),
    NightmareFoxy(82, 1000, new Habilidad[]{
        new Habilidad(80, TipoHabilidad.Bite2,"Muerde a hasta 3 enemigos aleatorios con daño moderado."),
        new Habilidad(0, TipoHabilidad.Unscrew2, "10% de probabilidad de eliminar instantáneamente a hasta 2 enemigos aleatorios."),
        new Habilidad(95, TipoHabilidad.HotCheese2, "Igual que HotCheese pero con más daño pasivo.")
    }),
    Endo01(50,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.PowerSong, "Aumenta el poder de las habilidades de todos los aliados en 25."),
        new Habilidad(0, TipoHabilidad.ArmorSong, "Aumenta la armadura de todos los aliados en 25."),
        new Habilidad(65, TipoHabilidad.EndoArmy, "Aumenta el daño de ataque de todos los aliados en 10.")
    }),
    Endo02(70,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.SpeedSong, "Aumenta fuerza y armadura de un aliado aleatorio en 45."),
        new Habilidad(75, TipoHabilidad.EndoArmy, "Aumenta el daño de ataque de todos los aliados en 10."),
        new Habilidad(0, TipoHabilidad.NeonWall, "El atacante activa un escudo que absorbe daño posible durante 8 turnos.")
    }),
    Endoplush(40,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.NeonWall, "El atacante activa un escudo que absorbe daño posible durante 8 turnos."),
        new Habilidad(60, TipoHabilidad.EyeBeam,  "Dispara un rayo a un enemigo aleatorio con 280 de daño, con 50% de probabilidad de empujar al atacante hacia atrás."),
        new Habilidad(70, TipoHabilidad.Waterhose2,  "Elimina hasta 2 enemigos que tengan 20% o menos de vida.")
    }),
    ShadowBonnie(50,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.Haunting, "Noquea a 1 enemigo aleatorio (pierde su turno)."),
        new Habilidad(0, TipoHabilidad.MimicBall, "Copia la habilidad de un aliado aleatorio y la ejecuta."),
        new Habilidad(0, TipoHabilidad.GiftBoxes, "Con 15% de probabilidad, revive hasta 2 aliados caídos al 50% de vida.")
    }),
    Plushtrap(40,1000, new Habilidad[]{
        new Habilidad(50, TipoHabilidad.ToxicBite,  "Muerde a un enemigo (95 daño) y lo envenena."),
        new Habilidad(80, TipoHabilidad.BadPizza, "Envenena a todos los enemigos con daño pasivo por turno."),
        new Habilidad(60, TipoHabilidad.Sludge, "Elimina el poder de las habilidades de un enemigo aleatorio temporalmente.")
    }),
    Springtrap(95,1000, new Habilidad[]{
        new Habilidad(60, TipoHabilidad.Bite2, "Muerde a hasta 3 enemigos aleatorios con daño moderado."),
        new Habilidad(80, TipoHabilidad.SpringLocks, " Quita el 90% de la vida a un animatrónico aleatorio del campo (aliados o enemigos)."),
        new Habilidad(0, TipoHabilidad.Sludge, "Elimina el poder de las habilidades de un enemigo aleatorio temporalmente.")
    }),
    CryingChild(30,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.PowerSong, "Aumenta el poder de las habilidades de todos los aliados en 25."),
        new Habilidad(0, TipoHabilidad.ArmorSong, "Aumenta la armadura de todos los aliados en 25."),
        new Habilidad(0, TipoHabilidad.Sludge, "Elimina el poder de las habilidades de un enemigo aleatorio temporalmente.")
    }),
    ToyFoxy(75,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.HappyJam2, "Cura completamente al aliado más dañado."),
        new Habilidad(75, TipoHabilidad.CosmicSong, "Aplica daño cósmico acumulativo a todos los enemigos."),
        new Habilidad(0, TipoHabilidad.GiftBoxes, "Con 15% de probabilidad, revive hasta 2 aliados caídos al 50% de vida.")
    }),
    NightmareFredbear(100,1000, new Habilidad[]{
        new Habilidad(85, TipoHabilidad.ToxicBite2, "Muerde a hasta 3 enemigos y los envenena."),
        new Habilidad(90, TipoHabilidad.BadPizza, "Envenena a todos los enemigos con daño pasivo por turno."),
        new Habilidad(100, TipoHabilidad.MegaBite, "Ataca a todos los enemigos con daño masivo, pero el atacante se sacrifica tras usarla.")
    }),
    Nightmare(110,1000, new Habilidad[]{
        new Habilidad(85, TipoHabilidad.ToxicBite2, "Muerde a hasta 3 enemigos y los envenena."),
        new Habilidad(0, TipoHabilidad.RainyDay2, "Reduce la armadura de todos los enemigos y les inflige daño pequeño."),
        new Habilidad(100, TipoHabilidad.MegaBite, "Ataca a todos los enemigos con daño masivo, pero el atacante se sacrifica tras usarla.")
    }),
    FredBear(105,1000, new Habilidad[]{
        new Habilidad(100, TipoHabilidad.MegaBite, "Ataca a todos los enemigos con daño masivo, pero el atacante se sacrifica tras usarla."),
        new Habilidad(0, TipoHabilidad.RegenSong, "Aplica regeneración de vida por turno a todos los aliados."),
        new Habilidad(0, TipoHabilidad.MimicBall, "Copia la habilidad de un aliado aleatorio y la ejecuta.")  
    }),
    SpringBonnie(85,1000, new Habilidad[]{
        new Habilidad(80, TipoHabilidad.SpringLocks, " Quita el 90% de la vida a un animatrónico aleatorio del campo (aliados o enemigos)."),
        new Habilidad(0, TipoHabilidad.HappyJam2, "Cura completamente al aliado más dañado."),
        new Habilidad(75, TipoHabilidad.CosmicSong, "Aplica daño cósmico acumulativo a todos los enemigos.")
    }),
    JackOBonnie(88,1000, new Habilidad[]{
        new Habilidad(95, TipoHabilidad.JackOBomb, "Daña a todos los enemigos con daño bajo."),
        new Habilidad(0, TipoHabilidad.Haunting, "Noquea a 1 enemigo aleatorio (pierde su turno)."),
         new Habilidad(120, TipoHabilidad.Slasher, "Con 10% de probabilidad, elimina instantáneamente a 1 enemigo aleatorio.")
    }),
    JackOChica(82,1000, new Habilidad[]{
        new Habilidad(95, TipoHabilidad.JackOBomb, "Daña a todos los enemigos con daño bajo."),
        new Habilidad(50, TipoHabilidad.Munchies, "Aplica daño pasivo a todos los enemigos y también a 4 aliados aleatorios."),
        new Habilidad(85, TipoHabilidad.Buzzsaw, "Ataca entre 1 y 4 enemigos aleatorios con daño moderado.")
    }),
    Animdude(55,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.NeonWall2, "Aplica el escudo NeonWall a un aliado aleatorio."),
        new Habilidad(110, TipoHabilidad.MegaVirus, "Envenena a todos los animatrónicos en el campo (aliados y enemigos)."),
        new Habilidad(130, TipoHabilidad.Th4Wall, "Ataca hasta 6 enemigos aleatorios con daño considerable.")
    }),
    MrChipper(60,1000, new Habilidad[]{
        new Habilidad(50, TipoHabilidad.Hook, "Ataca a un enemigo aleatorio con daño muy variable (entre 20 y 115)."),
        new Habilidad(0, TipoHabilidad.HocusPocus, "Potencia a todos los aliados sacrificando al atacante."),
        new Habilidad(85, TipoHabilidad.Buzzsaw, "Ataca entre 1 y 4 enemigos aleatorios con daño moderado.")
    }),
    NightmareBB(60,1000, new Habilidad[]{
        new Habilidad(100, TipoHabilidad.MegaBite, "Ataca a todos los enemigos con daño masivo, pero el atacante se sacrifica tras usarla."),
        new Habilidad(0, TipoHabilidad.BubbleBreath, "Protege a todos los aliados contra el veneno durante varios turnos."),
        new Habilidad(65, TipoHabilidad.Balloons2, " Golpea a un enemigo aleatorio con 130 de daño.")
        
    }),
    NightmarePuppet(40,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.PrizeBall2,"Ejecuta una habilidad aleatoria de la lista avanzada (habilidades más poderosas)."),
        new Habilidad(0, TipoHabilidad.BubbleBreath, "Protege a todos los aliados contra el veneno durante varios turnos."),
        new Habilidad(130, TipoHabilidad.Th4Wall, "Ataca hasta 6 enemigos aleatorios con daño considerable.")
    }),
    Coffee(45,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.GiftBoxes, "Con 15% de probabilidad, revive hasta 2 aliados caídos al 50% de vida."),
        new Habilidad(110, TipoHabilidad.MegaVirus, "Envenena a todos los animatrónicos en el campo (aliados y enemigos)."),
        new Habilidad(0, TipoHabilidad.Unscrew2, "10% de probabilidad de eliminar instantáneamente a hasta 2 enemigos aleatorios."),
    }),
    PurpleGuy(50,1000, new Habilidad[]{
        new Habilidad(0, TipoHabilidad.SpeedSong,  " Aumenta fuerza y armadura de un aliado aleatorio en 45."),
        new Habilidad(0, TipoHabilidad.HocusPocus, "Potencia a todos los aliados sacrificando al atacante."),
        new Habilidad(120, TipoHabilidad.Slasher, "Con 10% de probabilidad, elimina instantáneamente a 1 enemigo aleatorio.")
    });
    

    private final int fuerzaBase;
    private final int vidaBase;
    private final Habilidad[] habilidades;

    TipoAnimatronico(int fuerzaBase, int vidaBase, Habilidad[] habilidades) {
        this.fuerzaBase = fuerzaBase;
        this.vidaBase = vidaBase;
        this.habilidades = habilidades;
    }

    public int getFuerzaBase() { return fuerzaBase; }
    public int getVidaBase() { return vidaBase; }
    public Habilidad[] getHabilidades() { return habilidades; }
}