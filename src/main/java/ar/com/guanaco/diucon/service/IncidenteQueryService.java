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

import ar.com.guanaco.diucon.domain.Incidente;
import ar.com.guanaco.diucon.domain.*; // for static metamodels
import ar.com.guanaco.diucon.repository.IncidenteRepository;
import ar.com.guanaco.diucon.service.dto.IncidenteCriteria;
import ar.com.guanaco.diucon.service.dto.IncidenteDTO;
import ar.com.guanaco.diucon.service.mapper.IncidenteMapper;

/**
 * Service for executing complex queries for {@link Incidente} entities in the database.
 * The main input is a {@link IncidenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IncidenteDTO} or a {@link Page} of {@link IncidenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IncidenteQueryService extends QueryService<Incidente> {

    private final Logger log = LoggerFactory.getLogger(IncidenteQueryService.class);

    private final IncidenteRepository incidenteRepository;

    private final IncidenteMapper incidenteMapper;

    public IncidenteQueryService(IncidenteRepository incidenteRepository, IncidenteMapper incidenteMapper) {
        this.incidenteRepository = incidenteRepository;
        this.incidenteMapper = incidenteMapper;
    }

    /**
     * Return a {@link List} of {@link IncidenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IncidenteDTO> findByCriteria(IncidenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Incidente> specification = createSpecification(criteria);
        return incidenteMapper.toDto(incidenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IncidenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IncidenteDTO> findByCriteria(IncidenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Incidente> specification = createSpecification(criteria);
        return incidenteRepository.findAll(specification, page)
            .map(incidenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IncidenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Incidente> specification = createSpecification(criteria);
        return incidenteRepository.count(specification);
    }

    /**
     * Function to convert {@link IncidenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Incidente> createSpecification(IncidenteCriteria criteria) {
        Specification<Incidente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Incidente_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Incidente_.fecha));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Incidente_.estado));
            }
            if (criteria.getLocalizacion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalizacion(), Incidente_.localizacion));
            }
            if (criteria.getLatitud() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitud(), Incidente_.latitud));
            }
            if (criteria.getLongitud() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitud(), Incidente_.longitud));
            }
            if (criteria.getFechaResolucion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaResolucion(), Incidente_.fechaResolucion));
            }
            if (criteria.getFechaCierre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaCierre(), Incidente_.fechaCierre));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Incidente_.email));
            }
            if (criteria.getComentariosId() != null) {
                specification = specification.and(buildSpecification(criteria.getComentariosId(),
                    root -> root.join(Incidente_.comentarios, JoinType.LEFT).get(Comentario_.id)));
            }
            if (criteria.getHistorialId() != null) {
                specification = specification.and(buildSpecification(criteria.getHistorialId(),
                    root -> root.join(Incidente_.historials, JoinType.LEFT).get(HistorialEstado_.id)));
            }
            if (criteria.getCategoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaId(),
                    root -> root.join(Incidente_.categoria, JoinType.LEFT).get(Categoria_.id)));
            }
            if (criteria.getSubcategoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubcategoriaId(),
                    root -> root.join(Incidente_.subcategoria, JoinType.LEFT).get(SubCategoria_.id)));
            }
            if (criteria.getOperadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperadorId(),
                    root -> root.join(Incidente_.operador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getResponsableId() != null) {
                specification = specification.and(buildSpecification(criteria.getResponsableId(),
                    root -> root.join(Incidente_.responsable, JoinType.LEFT).get(Responsable_.id)));
            }
        }
        return specification;
    }
}
