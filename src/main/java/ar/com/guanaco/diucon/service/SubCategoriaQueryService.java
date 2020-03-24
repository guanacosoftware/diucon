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

import ar.com.guanaco.diucon.domain.SubCategoria;
import ar.com.guanaco.diucon.domain.*; // for static metamodels
import ar.com.guanaco.diucon.repository.SubCategoriaRepository;
import ar.com.guanaco.diucon.service.dto.SubCategoriaCriteria;
import ar.com.guanaco.diucon.service.dto.SubCategoriaDTO;
import ar.com.guanaco.diucon.service.mapper.SubCategoriaMapper;

/**
 * Service for executing complex queries for {@link SubCategoria} entities in the database.
 * The main input is a {@link SubCategoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubCategoriaDTO} or a {@link Page} of {@link SubCategoriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubCategoriaQueryService extends QueryService<SubCategoria> {

    private final Logger log = LoggerFactory.getLogger(SubCategoriaQueryService.class);

    private final SubCategoriaRepository subCategoriaRepository;

    private final SubCategoriaMapper subCategoriaMapper;

    public SubCategoriaQueryService(SubCategoriaRepository subCategoriaRepository, SubCategoriaMapper subCategoriaMapper) {
        this.subCategoriaRepository = subCategoriaRepository;
        this.subCategoriaMapper = subCategoriaMapper;
    }

    /**
     * Return a {@link List} of {@link SubCategoriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubCategoriaDTO> findByCriteria(SubCategoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubCategoria> specification = createSpecification(criteria);
        return subCategoriaMapper.toDto(subCategoriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SubCategoriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubCategoriaDTO> findByCriteria(SubCategoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubCategoria> specification = createSpecification(criteria);
        return subCategoriaRepository.findAll(specification, page)
            .map(subCategoriaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubCategoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubCategoria> specification = createSpecification(criteria);
        return subCategoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link SubCategoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubCategoria> createSpecification(SubCategoriaCriteria criteria) {
        Specification<SubCategoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubCategoria_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), SubCategoria_.nombre));
            }
            if (criteria.getCategiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategiaId(),
                    root -> root.join(SubCategoria_.categia, JoinType.LEFT).get(Categoria_.id)));
            }
            if (criteria.getResponsablesId() != null) {
                specification = specification.and(buildSpecification(criteria.getResponsablesId(),
                    root -> root.join(SubCategoria_.responsables, JoinType.LEFT).get(Responsable_.id)));
            }
            if (criteria.getPlantillasId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlantillasId(),
                    root -> root.join(SubCategoria_.plantillas, JoinType.LEFT).get(Plantilla_.id)));
            }
        }
        return specification;
    }
}
