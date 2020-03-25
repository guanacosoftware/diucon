package ar.com.guanaco.diucon.service.mapper;


import ar.com.guanaco.diucon.domain.*;
import ar.com.guanaco.diucon.service.dto.PlantillaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plantilla} and its DTO {@link PlantillaDTO}.
 */
@Mapper(componentModel = "spring", uses = {SubCategoriaMapper.class})
public interface PlantillaMapper extends EntityMapper<PlantillaDTO, Plantilla> {


    @Mapping(target = "removeSubcategorias", ignore = true)

    default Plantilla fromId(Long id) {
        if (id == null) {
            return null;
        }
        Plantilla plantilla = new Plantilla();
        plantilla.setId(id);
        return plantilla;
    }
}
