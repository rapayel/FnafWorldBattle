/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entrada;

import comunes.ContextoConexion;
import comunes.Observer;

/**
 * 
 * @author lagar
 */
public class Receptor implements Observer {
    private final IReceptorExterno receptorExterno;

    public Receptor(IReceptorExterno receptorExterno) {
        this.receptorExterno = receptorExterno;
    }

    @Override
    public void update(ContextoConexion contexto) {
        if (contexto == null || contexto.getBytes() == null || contexto.getBytes().length == 0) {
            System.out.println("[Receptor] Contexto binario inválido.");
            return;
        }
        System.out.println("[Receptor] Bytes binarios (MessagePack) recibidos del mecanismo de entrada.");
        receptorExterno.recibir(contexto.getBytes());
    }
}