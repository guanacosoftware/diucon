package ar.com.guanaco.diucon.service.impl;

import ar.com.guanaco.diucon.service.PlantillaService;
import ar.com.guanaco.diucon.domain.Plantilla;
import ar.com.guanaco.diucon.repository.PlantillaRepository;
import ar.com.guanaco.diucon.service.dto.PlantillaDTO;
import ar.com.guanaco.diucon.service.mapper.PlantillaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Plantilla}.
 */
@Service
@Transactional
public class PlantillaServiceImpl implements PlantillaService {

    private final Logger log = LoggerFactory.getLogger(PlantillaServiceImpl.class);

    private final PlantillaRepository plantillaRepository;

    private final PlantillaMapper plantillaMapper;

    public PlantillaServiceImpl(PlantillaRepository plantillaRepository, PlantillaMapper plantillaMapper) {
        this.plantillaRepository = plantillaRepository;
        this.plantillaMapper = plantillaMapper;
    }

    /**
     * Save a plantilla.
     *
     * @param plantillaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlantillaDTO save(PlantillaDTO plantillaDTO) {
        log.debug("Request to save Plantilla : {}", plantillaDTO);
        Plantilla plantilla = plantillaMapper.toEntity(plantillaDTO);
        plantilla = plantillaRepository.save(plantilla);
        return plantillaMapper.toDto(plantilla);
    }

    /**
     * Get all the plantillas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlantillaDTO> findAll() {
        log.debug("Request to get all Plantillas");
        return plantillaRepository.findAllWithEagerRelationships().stream()
            .map(plantillaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the plantillas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PlantillaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return plantillaRepository.findAllWithEagerRelationships(pageable).map(plantillaMapper::toDto);
    }

    /**
     * Get one plantilla by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlantillaDTO> findOne(Long id) {
        log.debug("Request to get Plantilla : {}", id);
        return plantillaRepository.findOneWithEagerRelationships(id)
            .map(plantillaMapper::toDto);
    }

    /**
     * Delete the plantilla by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plantilla : {}", id);
        plantillaRepository.deleteById(id);
    }
}
