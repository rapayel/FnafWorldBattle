package org.fnafworld;

public enum TipoHabilidad {
    MicToss(true, true, false),
    Hook(true, true, false),
    Bite(true, true, false),
    Bite2(true, true, false),
    JackOBomb(true, true, false),
    Buzzsaw(true, true, false),
    Balloons2(true, true, false),
    PrizeBall(true, false, false),
    PrizeBall2(true, false, false),
    MisteryBox(true, false, false),
    MisteryBox2(true, false, false),
    EscKey(true, false, false),
    Haunting(true, false, false),
    EndoArmy(true, false, false),
    GiftBoxes(true, false, false),
    NeonWall(true, false, false),
    MimicBall(true, false, false),
    HocusPocus(true, false, false),
    GloomBalloon(true, true, false),
    GloomSong(true, true, false),
    Sludge(true, true, false),
    RainyDay(true, true, false),
    RainyDay2(true, true, false),
    Th4Wall(true, true, false),
    Slasher(true, true, false),
    Cupcake(true, true, false),
    HappyJam(true, true, false),
    HappyJam2(true, true, false),
    PartyFavors(true, true, false),
    RegenSong(true, true, false),
    BubbleBreath(true, true, false),
    ToxicBalloon(true, false, true),
    ToxicBite(true, false, true),
    ToxicBite2(true, false, true),
    BadPizza(true, false, true),
    PizzaWheel(true, false, true),
    PizzaWheel2(true, false, true),
    BashJam(true, false, true),
    Jumpscare(true, false, true),
    HotCheese(true, false, true),
    HotCheese2(true, false, true),
    Munchies(true, false, true),
    Poppers(true, false, true),
    EyeBeam(true, false, true),
    SpringLocks(true, false, true),
    Freddles(true, false, true),
    MegaBite(true, false, true),
    Balloons(true, false, true),
    MegaVirus(true, false, true),
    NeonWall2(true, false, true),
    Waterhose(true, false, true),
    Waterhose2(true, false, true),
    Unscrew(true, false, true),
    Unscrew2(true, false, true),
    Birthday(true, false, true),
    CosmicSong(true, false, true),
    PowerSong(true, false, true),
    ArmorSong(true, false, true),
    SpeedSong(true, false, true);

    private final boolean esAtaque;
    private final boolean enPrizeBall;
    private final boolean enPrizeBall2;

    TipoHabilidad(boolean esAtaque, boolean enPrizeBall, boolean enPrizeBall2) {
        this.esAtaque = esAtaque;
        this.enPrizeBall = enPrizeBall;
        this.enPrizeBall2 = enPrizeBall2;
    }

    public boolean esAtaque() { return esAtaque; }
    public boolean isEnPrizeBall() { return enPrizeBall; }
    public boolean isEnPrizeBall2() { return enPrizeBall2; }
}
