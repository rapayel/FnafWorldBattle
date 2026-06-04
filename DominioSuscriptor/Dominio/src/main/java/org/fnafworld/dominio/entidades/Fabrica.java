package org.fnafworld.dominio.entidades;

import java.util.ArrayList;
import java.util.List;
import org.fnafworld.TipoAnimatronico;
import org.fnafworld.dtos.AnimatronicoDTO;
import org.fnafworld.dtos.GrupoFabricaDTO;
import org.fnafworld.dtos.HabilidadDTO;
import org.fnafworld.dtos.SolicitudAnimatronicoFabricaDTO;

public class Fabrica {
    private static final int ESCALA_VIDA_BALANCE = 10;

    public AnimatronicoDTO crearAnimatronico(SolicitudAnimatronicoFabricaDTO solicitud) {
        if (solicitud == null || solicitud.getTipo() == null) {
            throw new IllegalArgumentException("La solicitud de animatronico no puede estar vacia.");
        }

        String id = solicitud.getIdAnimatronico() != null
                ? solicitud.getIdAnimatronico()
                : solicitud.getTipo().name();

        return crearAnimatronico(id, solicitud.getTipo(), solicitud.isTurnoAnimatronico());
    }

    public AnimatronicoDTO[] crearGrupo(GrupoFabricaDTO solicitudGrupo) {
        if (solicitudGrupo == null || solicitudGrupo.getAnimatronicos() == null) {
            return new AnimatronicoDTO[0];
        }

        List<AnimatronicoDTO> grupo = new ArrayList<>();
        for (SolicitudAnimatronicoFabricaDTO solicitud : solicitudGrupo.getAnimatronicos()) {
            grupo.add(crearAnimatronico(solicitud));
        }
        return grupo.toArray(new AnimatronicoDTO[0]);
    }

    public AnimatronicoDTO crearAnimatronico(String id, TipoAnimatronico tipo, boolean turnoAnimatronico) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de animatronico es obligatorio.");
        }

        int vidaBalanceada = balancearVida(tipo.getVidaBase());
        
        HabilidadDTO[] habilidadesDto = new HabilidadDTO[tipo.getHabilidades().length];
        for (int i = 0; i < tipo.getHabilidades().length; i++) {
            Habilidad h = tipo.getHabilidades()[i];
            habilidadesDto[i] = new HabilidadDTO(h.getPoder(), h.getTipo(), h.getDescripcion());
        }

        return new AnimatronicoDTO(
            id, 
            tipo, 
            turnoAnimatronico, 
            tipo.getFuerzaBase(), 
            vidaBalanceada, 
            vidaBalanceada, 
            habilidadesDto, 
            true
        );
    }

    private int balancearVida(int vida) {
        if (vida <= 0 || vida >= 500) {
            return vida;
        }
        return vida * ESCALA_VIDA_BALANCE;
    }
}