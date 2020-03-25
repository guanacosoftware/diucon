package ar.com.guanaco.diucon.service;

import ar.com.guanaco.diucon.service.dto.ComentarioDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ar.com.guanaco.diucon.domain.Comentario}.
 */
public interface ComentarioService {

    /**
     * Save a comentario.
     *
     * @param comentarioDTO the entity to save.
     * @return the persisted entity.
     */
    ComentarioDTO save(ComentarioDTO comentarioDTO);

    /**
     * Get all the comentarios.
     *
     * @return the list of entities.
     */
    List<ComentarioDTO> findAll();

    /**
     * Get the "id" comentario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComentarioDTO> findOne(Long id);

    /**
     * Delete the "id" comentario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
