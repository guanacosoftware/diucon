package ar.com.guanaco.diucon.service;

import ar.com.guanaco.diucon.service.dto.ResponsableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ar.com.guanaco.diucon.domain.Responsable}.
 */
public interface ResponsableService {

    /**
     * Save a responsable.
     *
     * @param responsableDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsableDTO save(ResponsableDTO responsableDTO);

    /**
     * Get all the responsables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsableDTO> findAll(Pageable pageable);

    /**
     * Get all the responsables with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ResponsableDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" responsable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsableDTO> findOne(Long id);

    /**
     * Delete the "id" responsable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
