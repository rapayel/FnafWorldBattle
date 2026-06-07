/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.codedesc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.msgpack.jackson.dataformat.MessagePackFactory;

/**
 * 
 * @author lagar
 * @param <T> 
 */
class Serializador<T> implements ISerializador<T> {
    private final ObjectMapper objectMapper;

    public Serializador() {
        this.objectMapper = new ObjectMapper(new MessagePackFactory());
    }

    @Override
    public byte[] objetoABytes(T objeto) {
        if (objeto == null) return null;
        
        try {
            return objectMapper.writeValueAsBytes(objeto);
        } catch (IOException e) {
            System.err.println("Error en Serializador MessagePack: " + e.getMessage());
            return null;
        }
    }
}