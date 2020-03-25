package ar.com.guanaco.diucon.service.impl;

import ar.com.guanaco.diucon.service.IncidenteService;
import ar.com.guanaco.diucon.domain.Incidente;
import ar.com.guanaco.diucon.repository.IncidenteRepository;
import ar.com.guanaco.diucon.service.dto.IncidenteDTO;
import ar.com.guanaco.diucon.service.mapper.IncidenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Incidente}.
 */
@Service
@Transactional
public class IncidenteServiceImpl implements IncidenteService {

    private final Logger log = LoggerFactory.getLogger(IncidenteServiceImpl.class);

    private final IncidenteRepository incidenteRepository;

    private final IncidenteMapper incidenteMapper;

    public IncidenteServiceImpl(IncidenteRepository incidenteRepository, IncidenteMapper incidenteMapper) {
        this.incidenteRepository = incidenteRepository;
        this.incidenteMapper = incidenteMapper;
    }

    /**
     * Save a incidente.
     *
     * @param incidenteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IncidenteDTO save(IncidenteDTO incidenteDTO) {
        log.debug("Request to save Incidente : {}", incidenteDTO);
        Incidente incidente = incidenteMapper.toEntity(incidenteDTO);
        incidente = incidenteRepository.save(incidente);
        return incidenteMapper.toDto(incidente);
    }

    /**
     * Get all the incidentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IncidenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Incidentes");
        return incidenteRepository.findAll(pageable)
            .map(incidenteMapper::toDto);
    }

    /**
     * Get one incidente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IncidenteDTO> findOne(Long id) {
        log.debug("Request to get Incidente : {}", id);
        return incidenteRepository.findById(id)
            .map(incidenteMapper::toDto);
    }

    /**
     * Delete the incidente by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Incidente : {}", id);
        incidenteRepository.deleteById(id);
    }
}
