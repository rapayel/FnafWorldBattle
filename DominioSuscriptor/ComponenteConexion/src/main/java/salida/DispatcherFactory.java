/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salida;

/**
 * 
 * @author lagar
 */
public class DispatcherFactory {
    public static IDispatcher crearDispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        ClienteTCP clienteTCP = new ClienteTCP();

        dispatcher.addObserver(clienteTCP);

        return dispatcher;
    }
}