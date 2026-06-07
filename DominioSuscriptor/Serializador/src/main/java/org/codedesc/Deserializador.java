/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.codedesc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import java.io.IOException;
/**
 * 
 * @author lagar
 * @param <T> 
 */
class Deserializador<T> implements IDeserializador<T> {
    private final ObjectMapper objectMapper;

    public Deserializador() {
        this.objectMapper = new ObjectMapper(new MessagePackFactory());
    }

    @Override
    public T bytesAObjeto(byte[] datos, Class<T> claseDestino) {
        if (datos == null || datos.length == 0 || claseDestino == null) return null;

        try {
            return objectMapper.readValue(datos, claseDestino);
        } catch (IOException e) {
            System.err.println("Error en Deserializador MessagePack: " + e.getMessage());
            return null;
        }
    }
}