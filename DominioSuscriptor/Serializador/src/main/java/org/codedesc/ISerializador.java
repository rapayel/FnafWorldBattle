/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.codedesc;


/**
 *
 * @author lagar
 * @param <T>
 */
public interface ISerializador<T> {
    byte[] objetoABytes(T objeto);
}
