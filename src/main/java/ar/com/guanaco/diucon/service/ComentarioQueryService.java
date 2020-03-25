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

import ar.com.guanaco.diucon.domain.Comentario;
import ar.com.guanaco.diucon.domain.*; // for static metamodels
import ar.com.guanaco.diucon.repository.ComentarioRepository;
import ar.com.guanaco.diucon.service.dto.ComentarioCriteria;
import ar.com.guanaco.diucon.service.dto.ComentarioDTO;
import ar.com.guanaco.diucon.service.mapper.ComentarioMapper;

/**
 * Service for executing complex queries for {@link Comentario} entities in the database.
 * The main input is a {@link ComentarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComentarioDTO} or a {@link Page} of {@link ComentarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComentarioQueryService extends QueryService<Comentario> {

    private final Logger log = LoggerFactory.getLogger(ComentarioQueryService.class);

    private final ComentarioRepository comentarioRepository;

    private final ComentarioMapper comentarioMapper;

    public ComentarioQueryService(ComentarioRepository comentarioRepository, ComentarioMapper comentarioMapper) {
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
    }

    /**
     * Return a {@link List} of {@link ComentarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComentarioDTO> findByCriteria(ComentarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Comentario> specification = createSpecification(criteria);
        return comentarioMapper.toDto(comentarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComentarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentarioDTO> findByCriteria(ComentarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Comentario> specification = createSpecification(criteria);
        return comentarioRepository.findAll(specification, page)
            .map(comentarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComentarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Comentario> specification = createSpecification(criteria);
        return comentarioRepository.count(specification);
    }

    /**
     * Function to convert {@link ComentarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Comentario> createSpecification(ComentarioCriteria criteria) {
        Specification<Comentario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Comentario_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(Comentario_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getIncidenteId() != null) {
                specification = specification.and(buildSpecification(criteria.getIncidenteId(),
                    root -> root.join(Comentario_.incidente, JoinType.LEFT).get(Incidente_.id)));
            }
        }
        return specification;
    }
}
