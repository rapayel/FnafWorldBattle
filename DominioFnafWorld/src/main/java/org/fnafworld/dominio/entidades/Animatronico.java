package org.fnafworld.dominio.entidades;

import org.fnafworld.TipoAnimatronico;
/**
 * 
 * @author lagar
 */
public class Animatronico {
    private String idAnimatronico;
    private boolean turnoAnimatronico;
    private TipoAnimatronico tipo;
    private int fuerza;
    private int armadura;
    private int vidaActual;
    private int vidaTotal;
    private Habilidad[] habilidades;
    private int fuerzaAumentada;
    private int turnosFuerzaAumentada;
    private int fuerzaReducida;
    private int turnosFuerzaReducida;
    private int armaduraAumentada;
    private int turnosArmaduraAumentada;
    private int armaduraReducida;
    private int turnosArmaduraReducida;
    private int poderHabilidadesAumentado;
    private int turnosPoderHabilidadesAumentado;
    private int turnosHabilidadesSinPoder;
    private int regeneracionPorTurno;
    private int turnosRegeneracion;
    private int toxicidadPorTurno;
    private int turnosToxicidad;
    private int turnosProteccionToxicidad;
    private int danioPasivoPorTurno;
    private int turnosDanioPasivo;
    private int danioPasivoSeveroPorTurno;
    private int turnosDanioPasivoSevero;
    private int danioRetardado;
    private int turnosDanioRetardado;
    private int turnosInvencible;
    private int danioCosmicoPorAtaque;
    private int turnosDanioCosmico;
    private int turnosNoqueado;
    private int danioExtraAtaque;
    private int turnosDanioExtraAtaque;
    private int ataquesDoblesPendientes;
    private int turnosNeonWall;
    private int estadoVersion;

    public Animatronico(String idAnimatronico, boolean turnoAnimatronico, TipoAnimatronico tipo, int fuerza, int vidaActual, int vidaTotal, Habilidad[] habilidades) {
        this(idAnimatronico, turnoAnimatronico, tipo, fuerza, 0, vidaActual, vidaTotal, habilidades);
    }

    public Animatronico(String idAnimatronico, boolean turnoAnimatronico, TipoAnimatronico tipo, int fuerza, int armadura, int vidaActual, int vidaTotal, Habilidad[] habilidades) {
        this.idAnimatronico = idAnimatronico;
        this.turnoAnimatronico = turnoAnimatronico;
        this.tipo = tipo;
        this.fuerza = fuerza;
        this.armadura = armadura;
        this.vidaActual = vidaActual;
        this.vidaTotal = vidaTotal;
        this.habilidades = habilidades;
        this.estadoVersion = 0;
    }
    
    private void marcarCambio() {
        estadoVersion++;
    }

    public int getEstadoVersion() {
        return estadoVersion;
    }

    public boolean sigueVivo() {
        return vidaActual > 0;
    }

    public boolean isTurnoAnimatronico() {
        return turnoAnimatronico;
    }

    public void setTurnoAnimatronico(boolean turnoAnimatronico) {
        this.turnoAnimatronico = turnoAnimatronico;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getFuerzaActual() {
        return Math.max(0, fuerza + fuerzaAumentada - fuerzaReducida);
    }

    public int getArmadura() {
        return armadura;
    }

    public int getArmaduraActual() {
        return Math.max(0, armadura + armaduraAumentada - armaduraReducida);
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getVidaTotal() {
        return vidaTotal;
    }

    public int getVidaFaltante() {
        return Math.max(0, vidaTotal - vidaActual);
    }

    public Habilidad[] getHabilidades() {
        return habilidades;
    }

    public int getDanioExtraAtaque() {
        return danioExtraAtaque;
    }

    public int getPoderHabilidad(Habilidad habilidad) {
        if (habilidad == null || turnosHabilidadesSinPoder > 0) {
            return 0;
        }
        return habilidad.getPoder() + poderHabilidadesAumentado;
    }

    public void recibirDanio(int danio) {
        recibirDanioYObtener(danio);
    }

    public int recibirDanioYObtener(int danio) {
        if (turnosInvencible > 0) {
            return 0;
        }
        int danioFinal = Math.max(1, danio - getArmaduraActual());
        vidaActual -= danioFinal;
        if (vidaActual < 0) {
            vidaActual = 0;
        }
        marcarCambio();
        return danioFinal;
    }

    public void recibirDanioDirecto(int danio) {
        if (turnosInvencible > 0) {
            return;
        }
        recibirDanioDirectoIgnorandoInvencibilidad(danio);
    }

    private void recibirDanioDirectoIgnorandoInvencibilidad(int danio) {
        vidaActual -= Math.max(1, danio);
        if (vidaActual < 0) {
            vidaActual = 0;
        }
        marcarCambio();
    }

    public void eliminar() {
        if (turnosInvencible > 0) {
            return;
        }
        vidaActual = 0;
        marcarCambio();
    }

    public void sacrificar() {
        vidaActual = 0;
        marcarCambio();
    }

    public void revivir(int vida) {
        if (sigueVivo()) {
            return;
        }
        vidaActual = Math.min(vidaTotal, Math.max(1, vida));
        marcarCambio();
    }

    public void quitarPorcentajeVida(double porcentaje) {
        if (turnosInvencible > 0 || !sigueVivo()) {
            return;
        }
        int danio = (int) Math.ceil(vidaActual * porcentaje);
        recibirDanioDirecto(danio);
    }

    public void curar(int cantidad) {
        if (!sigueVivo()) {
            return;
        }
        vidaActual += cantidad;
        if (vidaActual > vidaTotal) {
            vidaActual = vidaTotal;
        }
        marcarCambio();
    }

    public void curarCompleto() {
        if (sigueVivo()) {
            vidaActual = vidaTotal;
            marcarCambio();
        }
    }

    public void disminuirFuerza(int cantidad, int turnos) {
        fuerzaReducida = Math.max(fuerzaReducida, cantidad);
        turnosFuerzaReducida = Math.max(turnosFuerzaReducida, turnos);
        marcarCambio();
    }

    public void aumentarFuerza(int cantidad, int turnos) {
        fuerzaAumentada = Math.max(fuerzaAumentada, cantidad);
        turnosFuerzaAumentada = Math.max(turnosFuerzaAumentada, turnos);
        marcarCambio();
    }

    public void disminuirArmadura(int cantidad, int turnos) {
        armaduraReducida = Math.max(armaduraReducida, cantidad);
        turnosArmaduraReducida = Math.max(turnosArmaduraReducida, turnos);
        marcarCambio();
    }

    public void aumentarArmadura(int cantidad, int turnos) {
        armaduraAumentada = Math.max(armaduraAumentada, cantidad);
        turnosArmaduraAumentada = Math.max(turnosArmaduraAumentada, turnos);
        marcarCambio();
    }

    public void aumentarFuerzaYArmadura(int cantidadFuerza, int cantidadArmadura, int turnos) {
        fuerzaAumentada = Math.max(fuerzaAumentada, cantidadFuerza);
        turnosFuerzaAumentada = Math.max(turnosFuerzaAumentada, turnos);
        armaduraAumentada = Math.max(armaduraAumentada, cantidadArmadura);
        turnosArmaduraAumentada = Math.max(turnosArmaduraAumentada, turnos);
        marcarCambio();
    }

    public void aumentarPoderHabilidades(int cantidad, int turnos) {
        poderHabilidadesAumentado = Math.max(poderHabilidadesAumentado, cantidad);
        turnosPoderHabilidadesAumentado = Math.max(turnosPoderHabilidadesAumentado, turnos);
        marcarCambio();
    }

    public void aumentarDanioAtaque(int cantidad, int turnos) {
        danioExtraAtaque = Math.max(danioExtraAtaque, cantidad);
        turnosDanioExtraAtaque = Math.max(turnosDanioExtraAtaque, turnos);
        marcarCambio();
    }

    public void quitarPoderHabilidades(int turnos) {
        turnosHabilidadesSinPoder = Math.max(turnosHabilidadesSinPoder, turnos);
        marcarCambio();
    }

    public void regenerarVida(int cantidad, int turnos) {
        regeneracionPorTurno = Math.max(regeneracionPorTurno, cantidad);
        turnosRegeneracion = Math.max(turnosRegeneracion, turnos);
        marcarCambio();
    }

    public void aplicarToxicidad(int cantidad, int turnos) {
        if (turnosProteccionToxicidad > 0) {
            return;
        }
        toxicidadPorTurno = Math.max(toxicidadPorTurno, cantidad);
        turnosToxicidad = Math.max(turnosToxicidad, turnos);
        marcarCambio();
    }

    public void protegerToxicidad(int turnos) {
        toxicidadPorTurno = 0;
        turnosToxicidad = 0;
        turnosProteccionToxicidad = Math.max(turnosProteccionToxicidad, turnos);
        marcarCambio();
    }

    public void aplicarDanioPasivo(int cantidad, int turnos) {
        danioPasivoPorTurno = Math.max(danioPasivoPorTurno, cantidad);
        turnosDanioPasivo = Math.max(turnosDanioPasivo, turnos);
        marcarCambio();
    }

    public void aplicarDanioPasivoSevero(int cantidad, int turnos) {
        danioPasivoSeveroPorTurno = Math.max(danioPasivoSeveroPorTurno, cantidad);
        turnosDanioPasivoSevero = Math.max(turnosDanioPasivoSevero, turnos);
        marcarCambio();
    }

    public void aplicarDanioRetardado(int cantidad, int turnos) {
        danioRetardado = Math.max(danioRetardado, cantidad);
        turnosDanioRetardado = Math.max(turnosDanioRetardado, turnos);
        marcarCambio();
    }

    public void hacerInvencible(int turnos) {
        turnosInvencible = Math.max(turnosInvencible, turnos);
        marcarCambio();
    }

    public void activarNeonWall(int armaduraExtra, int turnos) {
        aumentarArmadura(armaduraExtra, turnos);
        turnosNeonWall = Math.max(turnosNeonWall, turnos);
        marcarCambio();
    }

    public boolean protegeConNeonWall() {
        return turnosNeonWall > 0 && sigueVivo();
    }

    public void aplicarDanioCosmico(int cantidad, int turnos) {
        danioCosmicoPorAtaque = Math.max(danioCosmicoPorAtaque, cantidad);
        turnosDanioCosmico = Math.max(turnosDanioCosmico, turnos);
        marcarCambio();
    }

    public void recibirDanioCosmicoPorAtaque() {
        if (turnosDanioCosmico > 0) {
            recibirDanio(danioCosmicoPorAtaque);
        }
    }

    public void noquear(int turnos) {
        turnosNoqueado = Math.max(turnosNoqueado, turnos);
        marcarCambio();
    }

    public boolean estaNoqueado() {
        return turnosNoqueado > 0;
    }

    public void consumirTurnoNoqueado() {
        if (turnosNoqueado > 0) {
            turnosNoqueado--;
            marcarCambio();
        }
    }

    public void activarAtaqueDoble() {
        ataquesDoblesPendientes++;
        marcarCambio();
    }

    public boolean consumirAtaqueDoble() {
        if (ataquesDoblesPendientes <= 0) {
            return false;
        }
        ataquesDoblesPendientes--;
        marcarCambio();
        return true;
    }

    public void copiarFormaDe(Animatronico otro) {
        if (otro == null) {
            return;
        }
        tipo = otro.tipo;
        fuerza = otro.fuerza;
        armadura = otro.armadura;
        vidaActual = otro.vidaActual;
        vidaTotal = otro.vidaTotal;
        habilidades = copiarHabilidades(otro.habilidades);
        marcarCambio();
    }

    private Habilidad[] copiarHabilidades(Habilidad[] originales) {
        if (originales == null) {
            return new Habilidad[0];
        }
        Habilidad[] copia = new Habilidad[originales.length];
        for (int i = 0; i < originales.length; i++) {
            Habilidad habilidad = originales[i];
            if (habilidad != null) {
                copia[i] = new Habilidad(habilidad.getPoder(), habilidad.getTipo(), habilidad.getDescripcion());
            }
        }
        return copia;
    }

    public void avanzarTurnoEfectos() {
        if (turnosRegeneracion > 0) {
            curar(regeneracionPorTurno);
            turnosRegeneracion--;
            if (turnosRegeneracion == 0) {
                regeneracionPorTurno = 0;
            }
        }

        if (turnosToxicidad > 0) {
            recibirDanio(toxicidadPorTurno);
            turnosToxicidad--;
            if (turnosToxicidad == 0) {
                toxicidadPorTurno = 0;
            }
        }

        if (turnosDanioPasivo > 0) {
            recibirDanio(danioPasivoPorTurno);
            turnosDanioPasivo--;
            if (turnosDanioPasivo == 0) {
                danioPasivoPorTurno = 0;
            }
        }

        if (turnosDanioPasivoSevero > 0) {
            recibirDanioDirectoIgnorandoInvencibilidad(danioPasivoSeveroPorTurno);
            turnosDanioPasivoSevero--;
            if (turnosDanioPasivoSevero == 0) {
                danioPasivoSeveroPorTurno = 0;
            }
        }

        if (turnosDanioRetardado > 0) {
            turnosDanioRetardado--;
            if (turnosDanioRetardado == 0) {
                recibirDanio(danioRetardado);
                danioRetardado = 0;
            }
        }

        if (turnosFuerzaReducida > 0) {
            turnosFuerzaReducida--;
            if (turnosFuerzaReducida == 0) {
                fuerzaReducida = 0;
            }
        }

        if (turnosFuerzaAumentada > 0) {
            turnosFuerzaAumentada--;
            if (turnosFuerzaAumentada == 0) {
                fuerzaAumentada = 0;
            }
        }

        if (turnosArmaduraReducida > 0) {
            turnosArmaduraReducida--;
            if (turnosArmaduraReducida == 0) {
                armaduraReducida = 0;
            }
        }

        if (turnosArmaduraAumentada > 0) {
            turnosArmaduraAumentada--;
            if (turnosArmaduraAumentada == 0) {
                armaduraAumentada = 0;
            }
        }

        if (turnosHabilidadesSinPoder > 0) {
            turnosHabilidadesSinPoder--;
        }

        if (turnosPoderHabilidadesAumentado > 0) {
            turnosPoderHabilidadesAumentado--;
            if (turnosPoderHabilidadesAumentado == 0) {
                poderHabilidadesAumentado = 0;
            }
        }

        if (turnosDanioExtraAtaque > 0) {
            turnosDanioExtraAtaque--;
            if (turnosDanioExtraAtaque == 0) {
                danioExtraAtaque = 0;
            }
        }

        if (turnosProteccionToxicidad > 0) {
            turnosProteccionToxicidad--;
        }

        if (turnosInvencible > 0) {
            turnosInvencible--;
        }

        if (turnosNeonWall > 0) {
            turnosNeonWall--;
        }

        if (turnosDanioCosmico > 0) {
            turnosDanioCosmico--;
            if (turnosDanioCosmico == 0) {
                danioCosmicoPorAtaque = 0;
            }
        }
    }

    public TipoAnimatronico getTipo() {
        return tipo;
    }

    public String getIdAnimatronico() {
        return idAnimatronico;
    }
}
