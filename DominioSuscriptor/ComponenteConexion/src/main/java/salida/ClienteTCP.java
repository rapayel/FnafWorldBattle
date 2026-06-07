/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salida;

import comunes.ContextoConexion;
import comunes.Observer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 
 * @author lagar
 */
public class ClienteTCP implements Observer {

    @Override
    public void update(ContextoConexion contexto) {
        if (contexto == null) {
            System.out.println("[ClienteTCP] Contexto nulo.");
            return;
        }
        transmitir(contexto);
    }

    private void transmitir(ContextoConexion contexto) {
        try (Socket socket = new Socket(contexto.getHost(), contexto.getPuerto());
             OutputStream out = socket.getOutputStream()) {
            out.write(contexto.getBytes());
            out.flush();

            System.out.println("[ClienteTCP] Carga binaria MessagePack transmitida correctamente.");

        } catch (IOException e) {
            System.err.println("[ClienteTCP] Error al transmitir bytes: " + e.getMessage());
        }
    }
}