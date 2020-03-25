package ar.com.guanaco.diucon.service.mapper;


import ar.com.guanaco.diucon.domain.*;
import ar.com.guanaco.diucon.service.dto.ComentarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comentario} and its DTO {@link ComentarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, IncidenteMapper.class})
public interface ComentarioMapper extends EntityMapper<ComentarioDTO, Comentario> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.login", target = "usuarioLogin")
    @Mapping(source = "incidente.id", target = "incidenteId")
    ComentarioDTO toDto(Comentario comentario);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "incidenteId", target = "incidente")
    Comentario toEntity(ComentarioDTO comentarioDTO);

    default Comentario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comentario comentario = new Comentario();
        comentario.setId(id);
        return comentario;
    }
}
