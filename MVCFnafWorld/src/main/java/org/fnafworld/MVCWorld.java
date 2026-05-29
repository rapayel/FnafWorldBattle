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

        HabilidadDTO[] habilidadesVacias = new HabilidadDTO[0];
        
        AnimatronicoDTO[] grupoJ1 = {
            new AnimatronicoDTO("Freddy", TipoAnimatronico.Freddy, true, 50, 100, 100, habilidadesVacias, true),
            new AnimatronicoDTO("Bonnie", TipoAnimatronico.Bonnie, false, 45, 100, 100, habilidadesVacias, true),
            new AnimatronicoDTO("Chica", TipoAnimatronico.Chica, false, 30, 100, 100, habilidadesVacias, true),
            new AnimatronicoDTO("Foxy", TipoAnimatronico.Foxy, false, 60, 100, 100, habilidadesVacias, true)
        };
        
        AnimatronicoDTO[] grupoJ2 = {
            new AnimatronicoDTO("WitheredFreddy", TipoAnimatronico.WitheredFreddy, false, 70, 120, 120, habilidadesVacias, true),
            new AnimatronicoDTO("WitheredBonnie", TipoAnimatronico.WitheredBonnie, false, 65, 110, 110, habilidadesVacias, true),
            new AnimatronicoDTO("WitheredChica", TipoAnimatronico.WitheredChica, false, 40, 120, 120, habilidadesVacias, true),
            new AnimatronicoDTO("WitheredFoxy", TipoAnimatronico.WitheredFoxy, false, 80, 90, 90, habilidadesVacias, true)
        };

        AnimatronicoDTO[] grupoJ3 = {
            new AnimatronicoDTO("NightmareFreddy", TipoAnimatronico.NightmareFreddy, false, 80, 140, 140, habilidadesVacias, true),
            new AnimatronicoDTO("NightmareBonnie", TipoAnimatronico.NightmareBonnie, false, 75, 130, 130, habilidadesVacias, true),
            new AnimatronicoDTO("NightmareChica", TipoAnimatronico.NightmareChica, false, 70, 135, 135, habilidadesVacias, true),
            new AnimatronicoDTO("NightmareFoxy", TipoAnimatronico.NightmareFoxy, false, 85, 110, 110, habilidadesVacias, true)
        };

        AnimatronicoDTO[] grupoJ4 = {
            new AnimatronicoDTO("ToyFreddy", TipoAnimatronico.ToyFreddy, false, 45, 90, 90, habilidadesVacias, true),
            new AnimatronicoDTO("ToyBonnie", TipoAnimatronico.ToyBonnie, false, 50, 85, 85, habilidadesVacias, true),
            new AnimatronicoDTO("ToyChica", TipoAnimatronico.ToyChica, false, 40, 95, 95, habilidadesVacias, true),
            new AnimatronicoDTO("Mangle", TipoAnimatronico.Mangle, false, 55, 80, 80, habilidadesVacias, true)
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