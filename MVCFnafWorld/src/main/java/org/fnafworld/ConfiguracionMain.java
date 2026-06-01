/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.fnafworld;

import java.awt.EventQueue;
import org.fnafworld.mvc.FrameConfiguracion;
/**
 *
 * @author lagar
 */
public class ConfiguracionMain {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            FrameConfiguracion frame = new FrameConfiguracion();
            frame.setVisible(true);
        });
    }
    
}
