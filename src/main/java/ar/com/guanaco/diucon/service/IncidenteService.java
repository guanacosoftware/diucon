package ar.com.guanaco.diucon.service;

import ar.com.guanaco.diucon.service.dto.IncidenteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ar.com.guanaco.diucon.domain.Incidente}.
 */
public interface IncidenteService {

    /**
     * Save a incidente.
     *
     * @param incidenteDTO the entity to save.
     * @return the persisted entity.
     */
    IncidenteDTO save(IncidenteDTO incidenteDTO);

    /**
     * Get all the incidentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncidenteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" incidente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncidenteDTO> findOne(Long id);

    /**
     * Delete the "id" incidente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
