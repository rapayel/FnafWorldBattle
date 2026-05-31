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
        lista.add(new AnimatronicoSprite("Endoplush", 2, 3, 250, 248, 12, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Animdude", 2, 0, 250, 250, 10, 2, 2, 0, 250, 248, 10, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Coffee", 4, 4, 199, 147, 9, 4, 4, 157, 197, 147, 12, 6, 40, 40));
        lista.add(new AnimatronicoSprite("CryingChild", 2, 3, 250, 248, 10, 2, 2, 253, 250, 248, 12, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Fredbear", 2, 3, 250, 298, 10, 2, 2, 303, 250, 298, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("FuntimeFoxy", 2, 3, 250, 248, 12, 2, 2, 253, 250, 248, 11, 2, 40, 45));
        lista.add(new AnimatronicoSprite("JackOBonnie", 2, 3, 250, 248, 11, 2, 2, 253, 250, 248, 12, 2, 40, 40));
        lista.add(new AnimatronicoSprite("JackOChica", 4, 4, 249, 248, 11, 4, 4, 257, 254, 258, 12, 4, 40, 40));
        lista.add(new AnimatronicoSprite("MrChipper", 5, 4, 248, 249, 9, 5, 5, 257, 248, 249, 10, 5, 40, 40));
        lista.add(new AnimatronicoSprite("Nightmare", 2, 1, 250, 300, 12, 2, 2, 303, 250, 300, 13, 2, 40, 40));
        lista.add(new AnimatronicoSprite("NightmareFredbear", 2, 1, 250, 300, 12, 2, 2, 303, 250, 300, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("NightmareBB", 2, 3, 250, 247, 10, 2, 2, 253, 250, 247, 10, 2, 40, 40));
        lista.add(new AnimatronicoSprite("NightmarePuppet", 2, 0, 250, 248, 9, 2, 2, 252, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Plushtrap", 2, 1, 250, 248, 10, 2, 2, 505, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Purpleguy", 2, 3, 250, 247, 2, 2, 2, 253, 250, 248, 11, 2, 500, 40));
        lista.add(new AnimatronicoSprite("SpringBonnie", 2, 1, 250, 300, 12, 2, 2, 303, 250, 300, 13, 2, 40, 40));
        lista.add(new AnimatronicoSprite("Springtrap", 2, 3, 250, 247, 12, 2, 2, 253, 250, 248, 11, 2, 40, 40));
        lista.add(new AnimatronicoSprite("TheFan", 2, 1, 100, 100, 5, 2, 2, 1, 100, 100, 5, 2, 40, 40));
        
        return lista;
    }
}