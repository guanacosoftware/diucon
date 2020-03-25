package ar.com.guanaco.diucon.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import ar.com.guanaco.diucon.domain.HistorialEstado;
import ar.com.guanaco.diucon.domain.*; // for static metamodels
import ar.com.guanaco.diucon.repository.HistorialEstadoRepository;
import ar.com.guanaco.diucon.service.dto.HistorialEstadoCriteria;
import ar.com.guanaco.diucon.service.dto.HistorialEstadoDTO;
import ar.com.guanaco.diucon.service.mapper.HistorialEstadoMapper;

/**
 * Service for executing complex queries for {@link HistorialEstado} entities in the database.
 * The main input is a {@link HistorialEstadoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HistorialEstadoDTO} or a {@link Page} of {@link HistorialEstadoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HistorialEstadoQueryService extends QueryService<HistorialEstado> {

    private final Logger log = LoggerFactory.getLogger(HistorialEstadoQueryService.class);

    private final HistorialEstadoRepository historialEstadoRepository;

    private final HistorialEstadoMapper historialEstadoMapper;

    public HistorialEstadoQueryService(HistorialEstadoRepository historialEstadoRepository, HistorialEstadoMapper historialEstadoMapper) {
        this.historialEstadoRepository = historialEstadoRepository;
        this.historialEstadoMapper = historialEstadoMapper;
    }

    /**
     * Return a {@link List} of {@link HistorialEstadoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HistorialEstadoDTO> findByCriteria(HistorialEstadoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HistorialEstado> specification = createSpecification(criteria);
        return historialEstadoMapper.toDto(historialEstadoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HistorialEstadoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HistorialEstadoDTO> findByCriteria(HistorialEstadoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HistorialEstado> specification = createSpecification(criteria);
        return historialEstadoRepository.findAll(specification, page)
            .map(historialEstadoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HistorialEstadoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HistorialEstado> specification = createSpecification(criteria);
        return historialEstadoRepository.count(specification);
    }

    /**
     * Function to convert {@link HistorialEstadoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HistorialEstado> createSpecification(HistorialEstadoCriteria criteria) {
        Specification<HistorialEstado> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HistorialEstado_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), HistorialEstado_.fecha));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), HistorialEstado_.estado));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(HistorialEstado_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getIncidenteId() != null) {
                specification = specification.and(buildSpecification(criteria.getIncidenteId(),
                    root -> root.join(HistorialEstado_.incidente, JoinType.LEFT).get(Incidente_.id)));
            }
        }
        return specification;
    }
}
