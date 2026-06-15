/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package org.fnafworld;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author lagar
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ErrorLobby {
    URL_CAMPO_INVALIDA,
    URL_MUSICA_INVALIDA,
    PARTIDA_YA_INICIADA,
    LOBBY_LLENO,
    JUGADOR_YA_EN_LOBBY,
    JUGADOR_INVALIDO,
    JUGADOR_NO_EXISTE,
    EQUIPO_INVALIDO,
    EQUIPO_NO_SELECCIONADO,
    GRUPO_INVALIDO,
    ANIMATRONICO_INVALIDO,
    ANIMATRONICO_DUPLICADO,
    JUGADORES_INSUFICIENTES,
    JUGADORES_NO_LISTOS,
    BATALLA_NO_INICIADA,
    ATAQUE_INVALIDO,
    PARTIDA_FINALIZADA
}
