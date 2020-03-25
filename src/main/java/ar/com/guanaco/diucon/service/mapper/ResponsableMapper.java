package ar.com.guanaco.diucon.service.mapper;


import ar.com.guanaco.diucon.domain.*;
import ar.com.guanaco.diucon.service.dto.ResponsableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Responsable} and its DTO {@link ResponsableDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SubCategoriaMapper.class})
public interface ResponsableMapper extends EntityMapper<ResponsableDTO, Responsable> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.login", target = "usuarioLogin")
    ResponsableDTO toDto(Responsable responsable);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(target = "removeSubcategorias", ignore = true)
    Responsable toEntity(ResponsableDTO responsableDTO);

    default Responsable fromId(Long id) {
        if (id == null) {
            return null;
        }
        Responsable responsable = new Responsable();
        responsable.setId(id);
        return responsable;
    }
}
