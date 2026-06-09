/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fnafworld.traductor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.codedesc.*;
import org.fnafworld.dominio.fachada.IFachadaJuego;
import org.fnafworld.dtos.*;
import org.fnafworld.evento.*;
import salida.IDispatcher;
import java.util.Map;

/**
 *
 * @author lagar
 */
public class TraductorDominio {
    private IFachadaJuego dominio;
    private IDispatcher dispatcher;
    private ISerializador<Object> serializador;
    @SuppressWarnings("rawtypes")
    private IDeserializador deserializador;
    private final ObjectMapper mapperRaw;
    private String ipBroker = "192.168.100.12";
    private int puertoBroker = 5001;

    public TraductorDominio(IFachadaJuego dominio, IDispatcher dispatcher) {
        this.dominio = dominio;
        this.dispatcher = dispatcher;
        this.deserializador = CodeDescFactory.crearDeserializador();
        this.serializador = CodeDescFactory.crearSerializador();
        this.mapperRaw = new ObjectMapper(new MessagePackFactory());
    }
    
    @SuppressWarnings("unchecked")
    private <T> T deserializar(byte[] bytes, Class<T> clase) {
        return (T) deserializador.bytesAObjeto(bytes, clase);
    }

    public void procesarEntrada(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return;
        String tipoEvento;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> rawMap = mapperRaw.readValue(bytes, Map.class);
            Object idObj = rawMap.get("id");
            if (idObj == null) {
                System.err.println("Traductor: El mensaje no contiene el campo 'id'.");
                return;
            }
            tipoEvento = idObj.toString();
        } catch (Exception e) {
            System.err.println("Traductor: No se pudo leer el campo 'id' del mensaje: " + e.getMessage());
            return;
        }

        Object resultadoParaEnviar = null;
        try {
            switch (tipoEvento) {

            case "crearPartida":
                EventoCrearPartida ecp = deserializar(bytes, EventoCrearPartida.class);
                JugadorLobbyDTO jlCrear = dominio.crearPartida(ecp.getUrlFondo(), ecp.getUrlMusica(), ecp.getJugadorCreado());

                if (jlCrear != null && jlCrear.getError() != null) {
                    resultadoParaEnviar = new EventoErrorJugadorLobby(jlCrear, "errorCrearPartida");
                } else {
                    resultadoParaEnviar = new EventoPartidaCreada(jlCrear, "partidaCreada");
                }
                break;

            case "unirsePartida":
                EventoUnirsePartida eup = deserializar(bytes, EventoUnirsePartida.class);
                JugadorLobbyDTO jlUnir = dominio.unirsePartida(eup.getJugadorSolicitud());

                if (jlUnir != null && jlUnir.getError() != null) {
                    resultadoParaEnviar = new EventoErrorJugadorLobby(jlUnir, "errorUnirsePartida");
                } else {
                    resultadoParaEnviar = new EventoJugadorUnido(jlUnir, "jugadorUnido");
                }
                break;

            case "seleccionarEquipo":
                EventoSeleccionarEquipo ese = deserializar(bytes, EventoSeleccionarEquipo.class);
                JugadorLobbyDTO jlEquipo = dominio.seleccionarEquipo(ese.getIdJugador(), ese.getEquipo());

                if (jlEquipo != null && jlEquipo.getError() != null) {
                    resultadoParaEnviar = new EventoErrorJugadorLobby(jlEquipo, "errorSeleccionarEquipo");
                } else {
                    resultadoParaEnviar = new EventoEquipoSeleccionado(jlEquipo, "equipoSeleccionado");
                }
                break;

            case "establecerListo":
                EventoEstablecerListo eel = deserializar(bytes, EventoEstablecerListo.class);
                JugadorLobbyDTO jlListo = dominio.establecerListo(eel.getIdJugador(), eel.getGrupo(), eel.isListo());

                if (jlListo != null && jlListo.getError() != null) {
                    resultadoParaEnviar = new EventoErrorJugadorLobby(jlListo, "errorEstablecerListo");
                } else {
                    resultadoParaEnviar = new EventoJugadorListo(jlListo, "jugadorListo");
                }
                break;

            case "iniciarPartida":
                ResultadoAtaqueDTO resInicio = dominio.iniciarPartida();
                if (resInicio != null && resInicio.getError() != null) {
                    JugadorLobbyDTO jugadorErrorLobby = new JugadorLobbyDTO(null, null, null, null, null, false);
                    resultadoParaEnviar = new EventoErrorJugadorLobby(jugadorErrorLobby, "errorIniciarPartida");
                } else {
                    resultadoParaEnviar = new EventoPartidaIniciada(resInicio, "partidaIniciada");
                }
                break;

            case "atacar":
                EventoAtacar ea = deserializar(bytes, EventoAtacar.class);
                ResultadoAtaqueDTO resAtaque = dominio.atacar(ea.getAtaque());

                if (resAtaque != null && resAtaque.getError() != null) {
                    resultadoParaEnviar = new EventoAtaqueError(ea.getAtaque(), "ataqueError");
                } else {
                    resultadoParaEnviar = new EventoResultadoAtaque(resAtaque, "resultadoAtaque");
                }
                break;
                        default:
                            System.err.println("Traductor: Identificador de acción desconocido -> " + tipoEvento);
                            return;
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando la lógica del evento '" + tipoEvento + "': " + e.getMessage());
                    e.printStackTrace();
                }

        if (resultadoParaEnviar != null) {
            enviarRespuesta(resultadoParaEnviar);
        }
    }
    
    private void enviarRespuesta(Object resultado) {
        try {
            byte[] bytesSalida = serializador.objetoABytes(resultado);
            
            if (bytesSalida != null && bytesSalida.length > 0) {
                dispatcher.dispatch(ipBroker, puertoBroker, bytesSalida);
            }
        } catch (Exception e) {
            System.err.println("Error al serializar o enviar la respuesta: " + e.getMessage());
        }
    }
}