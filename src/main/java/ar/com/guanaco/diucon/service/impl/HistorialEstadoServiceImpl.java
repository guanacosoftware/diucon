package ar.com.guanaco.diucon.service.impl;

import ar.com.guanaco.diucon.service.HistorialEstadoService;
import ar.com.guanaco.diucon.domain.HistorialEstado;
import ar.com.guanaco.diucon.repository.HistorialEstadoRepository;
import ar.com.guanaco.diucon.service.dto.HistorialEstadoDTO;
import ar.com.guanaco.diucon.service.mapper.HistorialEstadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link HistorialEstado}.
 */
@Service
@Transactional
public class HistorialEstadoServiceImpl implements HistorialEstadoService {

    private final Logger log = LoggerFactory.getLogger(HistorialEstadoServiceImpl.class);

    private final HistorialEstadoRepository historialEstadoRepository;

    private final HistorialEstadoMapper historialEstadoMapper;

    public HistorialEstadoServiceImpl(HistorialEstadoRepository historialEstadoRepository, HistorialEstadoMapper historialEstadoMapper) {
        this.historialEstadoRepository = historialEstadoRepository;
        this.historialEstadoMapper = historialEstadoMapper;
    }

    /**
     * Save a historialEstado.
     *
     * @param historialEstadoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HistorialEstadoDTO save(HistorialEstadoDTO historialEstadoDTO) {
        log.debug("Request to save HistorialEstado : {}", historialEstadoDTO);
        HistorialEstado historialEstado = historialEstadoMapper.toEntity(historialEstadoDTO);
        historialEstado = historialEstadoRepository.save(historialEstado);
        return historialEstadoMapper.toDto(historialEstado);
    }

    /**
     * Get all the historialEstados.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistorialEstadoDTO> findAll() {
        log.debug("Request to get all HistorialEstados");
        return historialEstadoRepository.findAll().stream()
            .map(historialEstadoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one historialEstado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HistorialEstadoDTO> findOne(Long id) {
        log.debug("Request to get HistorialEstado : {}", id);
        return historialEstadoRepository.findById(id)
            .map(historialEstadoMapper::toDto);
    }

    /**
     * Delete the historialEstado by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistorialEstado : {}", id);
        historialEstadoRepository.deleteById(id);
    }
}
