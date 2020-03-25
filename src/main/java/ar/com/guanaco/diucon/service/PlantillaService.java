package ar.com.guanaco.diucon.service;

import ar.com.guanaco.diucon.service.dto.PlantillaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ar.com.guanaco.diucon.domain.Plantilla}.
 */
public interface PlantillaService {

    /**
     * Save a plantilla.
     *
     * @param plantillaDTO the entity to save.
     * @return the persisted entity.
     */
    PlantillaDTO save(PlantillaDTO plantillaDTO);

    /**
     * Get all the plantillas.
     *
     * @return the list of entities.
     */
    List<PlantillaDTO> findAll();

    /**
     * Get all the plantillas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PlantillaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" plantilla.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlantillaDTO> findOne(Long id);

    /**
     * Delete the "id" plantilla.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
