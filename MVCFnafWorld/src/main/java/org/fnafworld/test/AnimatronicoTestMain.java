/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.fnafworld.test;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import org.fnafworld.mvc.vista.AnimatronicoSprite;

public class AnimatronicoTestMain {

    public static void main(String[] args) {
        // Generar la lista completa de animatrónicos activos y documentados
        List<AnimatronicoSprite> animatronicosCargados = cargarSpritesMock();

        // Lanzar la interfaz de pruebas de manera segura en el hilo de UI
        SwingUtilities.invokeLater(() -> {
            AnimatronicoTestFrame ventanaTest = new AnimatronicoTestFrame(animatronicosCargados);
            ventanaTest.setVisible(true);
        });
    }

    private static List<AnimatronicoSprite> cargarSpritesMock() {
        List<AnimatronicoSprite> lista = new ArrayList<>();
        
        // --- ANIMATRONICOS ANTERIORES / DOCUMENTADOS ACTIVADOS ---
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

        // --- NUEVOS ANIMATRONICOS ---
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
}