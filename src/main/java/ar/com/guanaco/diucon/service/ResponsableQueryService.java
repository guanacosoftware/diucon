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

import ar.com.guanaco.diucon.domain.Responsable;
import ar.com.guanaco.diucon.domain.*; // for static metamodels
import ar.com.guanaco.diucon.repository.ResponsableRepository;
import ar.com.guanaco.diucon.service.dto.ResponsableCriteria;
import ar.com.guanaco.diucon.service.dto.ResponsableDTO;
import ar.com.guanaco.diucon.service.mapper.ResponsableMapper;

/**
 * Service for executing complex queries for {@link Responsable} entities in the database.
 * The main input is a {@link ResponsableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResponsableDTO} or a {@link Page} of {@link ResponsableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsableQueryService extends QueryService<Responsable> {

    private final Logger log = LoggerFactory.getLogger(ResponsableQueryService.class);

    private final ResponsableRepository responsableRepository;

    private final ResponsableMapper responsableMapper;

    public ResponsableQueryService(ResponsableRepository responsableRepository, ResponsableMapper responsableMapper) {
        this.responsableRepository = responsableRepository;
        this.responsableMapper = responsableMapper;
    }

    /**
     * Return a {@link List} of {@link ResponsableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsableDTO> findByCriteria(ResponsableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Responsable> specification = createSpecification(criteria);
        return responsableMapper.toDto(responsableRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResponsableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsableDTO> findByCriteria(ResponsableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Responsable> specification = createSpecification(criteria);
        return responsableRepository.findAll(specification, page)
            .map(responsableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Responsable> specification = createSpecification(criteria);
        return responsableRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Responsable> createSpecification(ResponsableCriteria criteria) {
        Specification<Responsable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Responsable_.id));
            }
            if (criteria.getNombreCompleto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreCompleto(), Responsable_.nombreCompleto));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), Responsable_.telefono));
            }
            if (criteria.getFechaNacimiento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaNacimiento(), Responsable_.fechaNacimiento));
            }
            if (criteria.getDni() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDni(), Responsable_.dni));
            }
            if (criteria.getDomicilio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomicilio(), Responsable_.domicilio));
            }
            if (criteria.getLatitud() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitud(), Responsable_.latitud));
            }
            if (criteria.getLongitud() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitud(), Responsable_.longitud));
            }
            if (criteria.getProfesional() != null) {
                specification = specification.and(buildSpecification(criteria.getProfesional(), Responsable_.profesional));
            }
            if (criteria.getEstudiante() != null) {
                specification = specification.and(buildSpecification(criteria.getEstudiante(), Responsable_.estudiante));
            }
            if (criteria.getTrabajador() != null) {
                specification = specification.and(buildSpecification(criteria.getTrabajador(), Responsable_.trabajador));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(Responsable_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getSubcategoriasId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubcategoriasId(),
                    root -> root.join(Responsable_.subcategorias, JoinType.LEFT).get(SubCategoria_.id)));
            }
        }
        return specification;
    }
}
