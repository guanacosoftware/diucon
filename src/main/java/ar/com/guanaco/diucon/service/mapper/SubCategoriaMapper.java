package ar.com.guanaco.diucon.service.mapper;


import ar.com.guanaco.diucon.domain.*;
import ar.com.guanaco.diucon.service.dto.SubCategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubCategoria} and its DTO {@link SubCategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface SubCategoriaMapper extends EntityMapper<SubCategoriaDTO, SubCategoria> {

    @Mapping(source = "categia.id", target = "categiaId")
    @Mapping(source = "categia.nombre", target = "categiaNombre")
    SubCategoriaDTO toDto(SubCategoria subCategoria);

    @Mapping(source = "categiaId", target = "categia")
    @Mapping(target = "responsables", ignore = true)
    @Mapping(target = "removeResponsables", ignore = true)
    @Mapping(target = "plantillas", ignore = true)
    @Mapping(target = "removePlantillas", ignore = true)
    SubCategoria toEntity(SubCategoriaDTO subCategoriaDTO);

    default SubCategoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(id);
        return subCategoria;
    }
}
