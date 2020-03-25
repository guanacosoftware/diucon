package ar.com.guanaco.diucon.service.mapper;


import ar.com.guanaco.diucon.domain.*;
import ar.com.guanaco.diucon.service.dto.IncidenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Incidente} and its DTO {@link IncidenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class, SubCategoriaMapper.class, UserMapper.class, ResponsableMapper.class})
public interface IncidenteMapper extends EntityMapper<IncidenteDTO, Incidente> {

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nombre", target = "categoriaNombre")
    @Mapping(source = "subcategoria.id", target = "subcategoriaId")
    @Mapping(source = "subcategoria.nombre", target = "subcategoriaNombre")
    @Mapping(source = "operador.id", target = "operadorId")
    @Mapping(source = "operador.login", target = "operadorLogin")
    @Mapping(source = "responsable.id", target = "responsableId")
    @Mapping(source = "responsable.nombreCompleto", target = "responsableNombreCompleto")
    IncidenteDTO toDto(Incidente incidente);

    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "removeComentarios", ignore = true)
    @Mapping(target = "historials", ignore = true)
    @Mapping(target = "removeHistorial", ignore = true)
    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(source = "subcategoriaId", target = "subcategoria")
    @Mapping(source = "operadorId", target = "operador")
    @Mapping(source = "responsableId", target = "responsable")
    Incidente toEntity(IncidenteDTO incidenteDTO);

    default Incidente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incidente incidente = new Incidente();
        incidente.setId(id);
        return incidente;
    }
}
