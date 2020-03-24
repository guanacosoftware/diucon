package ar.com.guanaco.diucon.service.impl;

import ar.com.guanaco.diucon.service.SubCategoriaService;
import ar.com.guanaco.diucon.domain.SubCategoria;
import ar.com.guanaco.diucon.repository.SubCategoriaRepository;
import ar.com.guanaco.diucon.service.dto.SubCategoriaDTO;
import ar.com.guanaco.diucon.service.mapper.SubCategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SubCategoria}.
 */
@Service
@Transactional
public class SubCategoriaServiceImpl implements SubCategoriaService {

    private final Logger log = LoggerFactory.getLogger(SubCategoriaServiceImpl.class);

    private final SubCategoriaRepository subCategoriaRepository;

    private final SubCategoriaMapper subCategoriaMapper;

    public SubCategoriaServiceImpl(SubCategoriaRepository subCategoriaRepository, SubCategoriaMapper subCategoriaMapper) {
        this.subCategoriaRepository = subCategoriaRepository;
        this.subCategoriaMapper = subCategoriaMapper;
    }

    /**
     * Save a subCategoria.
     *
     * @param subCategoriaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubCategoriaDTO save(SubCategoriaDTO subCategoriaDTO) {
        log.debug("Request to save SubCategoria : {}", subCategoriaDTO);
        SubCategoria subCategoria = subCategoriaMapper.toEntity(subCategoriaDTO);
        subCategoria = subCategoriaRepository.save(subCategoria);
        return subCategoriaMapper.toDto(subCategoria);
    }

    /**
     * Get all the subCategorias.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubCategoriaDTO> findAll() {
        log.debug("Request to get all SubCategorias");
        return subCategoriaRepository.findAll().stream()
            .map(subCategoriaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one subCategoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubCategoriaDTO> findOne(Long id) {
        log.debug("Request to get SubCategoria : {}", id);
        return subCategoriaRepository.findById(id)
            .map(subCategoriaMapper::toDto);
    }

    /**
     * Delete the subCategoria by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubCategoria : {}", id);
        subCategoriaRepository.deleteById(id);
    }
}
