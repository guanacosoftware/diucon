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

import ar.com.guanaco.diucon.domain.Plantilla;
import ar.com.guanaco.diucon.domain.*; // for static metamodels
import ar.com.guanaco.diucon.repository.PlantillaRepository;
import ar.com.guanaco.diucon.service.dto.PlantillaCriteria;
import ar.com.guanaco.diucon.service.dto.PlantillaDTO;
import ar.com.guanaco.diucon.service.mapper.PlantillaMapper;

/**
 * Service for executing complex queries for {@link Plantilla} entities in the database.
 * The main input is a {@link PlantillaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlantillaDTO} or a {@link Page} of {@link PlantillaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlantillaQueryService extends QueryService<Plantilla> {

    private final Logger log = LoggerFactory.getLogger(PlantillaQueryService.class);

    private final PlantillaRepository plantillaRepository;

    private final PlantillaMapper plantillaMapper;

    public PlantillaQueryService(PlantillaRepository plantillaRepository, PlantillaMapper plantillaMapper) {
        this.plantillaRepository = plantillaRepository;
        this.plantillaMapper = plantillaMapper;
    }

    /**
     * Return a {@link List} of {@link PlantillaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlantillaDTO> findByCriteria(PlantillaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Plantilla> specification = createSpecification(criteria);
        return plantillaMapper.toDto(plantillaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlantillaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlantillaDTO> findByCriteria(PlantillaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Plantilla> specification = createSpecification(criteria);
        return plantillaRepository.findAll(specification, page)
            .map(plantillaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlantillaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Plantilla> specification = createSpecification(criteria);
        return plantillaRepository.count(specification);
    }

    /**
     * Function to convert {@link PlantillaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Plantilla> createSpecification(PlantillaCriteria criteria) {
        Specification<Plantilla> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Plantilla_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Plantilla_.nombre));
            }
            if (criteria.getSubcategoriasId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubcategoriasId(),
                    root -> root.join(Plantilla_.subcategorias, JoinType.LEFT).get(SubCategoria_.id)));
            }
        }
        return specification;
    }
}
