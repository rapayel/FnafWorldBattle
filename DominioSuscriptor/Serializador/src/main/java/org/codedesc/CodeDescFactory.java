/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.codedesc;
/**
 * 
 * @author lagar
 */
public class CodeDescFactory {
    public static <T> ISerializador<T> crearSerializador() {
        return new Serializador<>();
    }
    public static <T> IDeserializador<T> crearDeserializador() {
        return new Deserializador<>();
    }
}