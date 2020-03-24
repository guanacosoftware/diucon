package ar.com.guanaco.diucon.service.impl;

import ar.com.guanaco.diucon.service.ResponsableService;
import ar.com.guanaco.diucon.domain.Responsable;
import ar.com.guanaco.diucon.repository.ResponsableRepository;
import ar.com.guanaco.diucon.service.dto.ResponsableDTO;
import ar.com.guanaco.diucon.service.mapper.ResponsableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Responsable}.
 */
@Service
@Transactional
public class ResponsableServiceImpl implements ResponsableService {

    private final Logger log = LoggerFactory.getLogger(ResponsableServiceImpl.class);

    private final ResponsableRepository responsableRepository;

    private final ResponsableMapper responsableMapper;

    public ResponsableServiceImpl(ResponsableRepository responsableRepository, ResponsableMapper responsableMapper) {
        this.responsableRepository = responsableRepository;
        this.responsableMapper = responsableMapper;
    }

    /**
     * Save a responsable.
     *
     * @param responsableDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ResponsableDTO save(ResponsableDTO responsableDTO) {
        log.debug("Request to save Responsable : {}", responsableDTO);
        Responsable responsable = responsableMapper.toEntity(responsableDTO);
        responsable = responsableRepository.save(responsable);
        return responsableMapper.toDto(responsable);
    }

    /**
     * Get all the responsables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResponsableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Responsables");
        return responsableRepository.findAll(pageable)
            .map(responsableMapper::toDto);
    }

    /**
     * Get all the responsables with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ResponsableDTO> findAllWithEagerRelationships(Pageable pageable) {
        return responsableRepository.findAllWithEagerRelationships(pageable).map(responsableMapper::toDto);
    }

    /**
     * Get one responsable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsableDTO> findOne(Long id) {
        log.debug("Request to get Responsable : {}", id);
        return responsableRepository.findOneWithEagerRelationships(id)
            .map(responsableMapper::toDto);
    }

    /**
     * Delete the responsable by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Responsable : {}", id);
        responsableRepository.deleteById(id);
    }
}
