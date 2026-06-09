/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.traductor;

import org.fnafworld.dominio.fachada.FachadaJuego;
import org.fnafworld.dominio.fachada.IFachadaJuego;
import entrada.ServidorTCP;
import entrada.Receptor;
import salida.DispatcherFactory;
import salida.IDispatcher;

/**
 * @author lagar
 */
public class Main {

    public static void main(String[] args) {
        final String MI_IP = "192.168.100.12";
        final int PUERTO = 5000;
        
        System.out.println("=== [FNAF WORLD] Iniciando Servidor de Red en " + MI_IP + " ===");

        IFachadaJuego fachadaJuego = new FachadaJuego();
        System.out.println("1. [DOMINIO] Fachada del juego instanciada (Entidades durmientes).");

        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        System.out.println("2. [INFRAESTRUCTURA] Dispatcher de eventos salientes configurado.");

        TraductorDominio traductor = new TraductorDominio(fachadaJuego, dispatcher);
        System.out.println("3. [TRADUCTOR] Traductor acoplado con Fachada y Dispatcher exitosamente.");

        ServidorTCP servidor = new ServidorTCP(PUERTO);

        Receptor receptorPuente = new Receptor(bytes -> traductor.procesarEntrada(bytes));
        
        servidor.addObserver(receptorPuente);
        System.out.println("4. [CONEXIÓN] Receptor (Observador) vinculado al Servidor TCP.");

        try {
            System.out.println("\n=== [SISTEMA] Servidor listo. Escuchando en puerto: " + PUERTO + " ===");
            servidor.iniciar();
            
            Thread.currentThread().join();
            
        } catch (InterruptedException e) {
            System.err.println("!!! [SISTEMA] El servidor fue interrumpido inesperadamente.");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("=== [SISTEMA] Deteniendo Servidor TCP... ===");
            servidor.detener();
            System.out.println("=== [SISTEMA] Servidor apagado de forma segura ===");
        }
    }
}