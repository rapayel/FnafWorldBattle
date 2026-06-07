/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salida;

import comunes.ContextoConexion;
import comunes.Observer;
import comunes.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 
 * @author lagar
 */
public class Dispatcher implements IDispatcher, Subject {
    private final Queue<ContextoConexion> cola;
    private final List<Observer> observers;
    private ContextoConexion contextoActual;

    public Dispatcher() {
        this.cola = new LinkedBlockingQueue<>();
        this.observers = new ArrayList<>();
        this.contextoActual = null;
    }

    @Override
    public void dispatch(String host, int puerto, byte[] bytes) {
        if (host == null || host.isBlank()) {
            System.out.println("[Dispatcher] Host inválido.");
            return;
        }

        if (puerto <= 0) {
            System.out.println("[Dispatcher] Puerto inválido.");
            return;
        }

        if (bytes == null || bytes.length == 0) {
            System.out.println("[Dispatcher] Bytes nulos o vacíos.");
            return;
        }

        contextoActual = new ContextoConexion(host, puerto, bytes);
        cola.offer(contextoActual);

        System.out.println("[Dispatcher] Contexto agregado a la cola.");
        notifyObservers();
    }

    @Override
    public void addObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        if (contextoActual == null) {
            return;
        }

        for (Observer o : observers) {
            o.update(contextoActual);
        }
    }
}