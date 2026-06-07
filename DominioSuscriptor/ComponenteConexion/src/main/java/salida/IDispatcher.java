/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package salida;

/**
 * 
 * @author lagar
 */
public interface IDispatcher {
    void dispatch(String host, int puerto, byte[] bytes);
}