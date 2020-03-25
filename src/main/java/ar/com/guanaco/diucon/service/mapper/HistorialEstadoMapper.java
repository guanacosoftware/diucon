package ar.com.guanaco.diucon.service.mapper;


import ar.com.guanaco.diucon.domain.*;
import ar.com.guanaco.diucon.service.dto.HistorialEstadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HistorialEstado} and its DTO {@link HistorialEstadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, IncidenteMapper.class})
public interface HistorialEstadoMapper extends EntityMapper<HistorialEstadoDTO, HistorialEstado> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.login", target = "usuarioLogin")
    @Mapping(source = "incidente.id", target = "incidenteId")
    HistorialEstadoDTO toDto(HistorialEstado historialEstado);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "incidenteId", target = "incidente")
    HistorialEstado toEntity(HistorialEstadoDTO historialEstadoDTO);

    default HistorialEstado fromId(Long id) {
        if (id == null) {
            return null;
        }
        HistorialEstado historialEstado = new HistorialEstado();
        historialEstado.setId(id);
        return historialEstado;
    }
}
