package ar.com.guanaco.diucon.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.com.guanaco.diucon.domain.SubCategoria;
import ar.com.guanaco.diucon.service.dto.SubCategoriaDTO;

/**
 * Mapper for the entity {@link SubCategoria} and its DTO
 * {@link SubCategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoriaMapper.class })
public interface SubCategoriaMapper extends EntityMapper<SubCategoriaDTO, SubCategoria> {

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nombre", target = "categoriaNombre")
    SubCategoriaDTO toDto(SubCategoria subCategoria);

    @Mapping(source = "categoriaId", target = "categoria")
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
