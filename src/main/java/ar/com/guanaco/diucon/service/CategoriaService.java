package ar.com.guanaco.diucon.service;

import ar.com.guanaco.diucon.service.dto.CategoriaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ar.com.guanaco.diucon.domain.Categoria}.
 */
public interface CategoriaService {

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriaDTO save(CategoriaDTO categoriaDTO);

    /**
     * Get all the categorias.
     *
     * @return the list of entities.
     */
    List<CategoriaDTO> findAll();

    /**
     * Get the "id" categoria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaDTO> findOne(Long id);

    /**
     * Delete the "id" categoria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
