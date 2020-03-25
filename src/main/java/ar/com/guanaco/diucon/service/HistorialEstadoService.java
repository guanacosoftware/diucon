package ar.com.guanaco.diucon.service;

import ar.com.guanaco.diucon.service.dto.HistorialEstadoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ar.com.guanaco.diucon.domain.HistorialEstado}.
 */
public interface HistorialEstadoService {

    /**
     * Save a historialEstado.
     *
     * @param historialEstadoDTO the entity to save.
     * @return the persisted entity.
     */
    HistorialEstadoDTO save(HistorialEstadoDTO historialEstadoDTO);

    /**
     * Get all the historialEstados.
     *
     * @return the list of entities.
     */
    List<HistorialEstadoDTO> findAll();

    /**
     * Get the "id" historialEstado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HistorialEstadoDTO> findOne(Long id);

    /**
     * Delete the "id" historialEstado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
