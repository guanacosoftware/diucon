package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.service.SubCategoriaService;
import ar.com.guanaco.diucon.web.rest.errors.BadRequestAlertException;
import ar.com.guanaco.diucon.service.dto.SubCategoriaDTO;
import ar.com.guanaco.diucon.service.dto.SubCategoriaCriteria;
import ar.com.guanaco.diucon.service.SubCategoriaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.com.guanaco.diucon.domain.SubCategoria}.
 */
@RestController
@RequestMapping("/api")
public class SubCategoriaResource {

    private final Logger log = LoggerFactory.getLogger(SubCategoriaResource.class);

    private static final String ENTITY_NAME = "subCategoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubCategoriaService subCategoriaService;

    private final SubCategoriaQueryService subCategoriaQueryService;

    public SubCategoriaResource(SubCategoriaService subCategoriaService, SubCategoriaQueryService subCategoriaQueryService) {
        this.subCategoriaService = subCategoriaService;
        this.subCategoriaQueryService = subCategoriaQueryService;
    }

    /**
     * {@code POST  /sub-categorias} : Create a new subCategoria.
     *
     * @param subCategoriaDTO the subCategoriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subCategoriaDTO, or with status {@code 400 (Bad Request)} if the subCategoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-categorias")
    public ResponseEntity<SubCategoriaDTO> createSubCategoria(@Valid @RequestBody SubCategoriaDTO subCategoriaDTO) throws URISyntaxException {
        log.debug("REST request to save SubCategoria : {}", subCategoriaDTO);
        if (subCategoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new subCategoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubCategoriaDTO result = subCategoriaService.save(subCategoriaDTO);
        return ResponseEntity.created(new URI("/api/sub-categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-categorias} : Updates an existing subCategoria.
     *
     * @param subCategoriaDTO the subCategoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategoriaDTO,
     * or with status {@code 400 (Bad Request)} if the subCategoriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subCategoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-categorias")
    public ResponseEntity<SubCategoriaDTO> updateSubCategoria(@Valid @RequestBody SubCategoriaDTO subCategoriaDTO) throws URISyntaxException {
        log.debug("REST request to update SubCategoria : {}", subCategoriaDTO);
        if (subCategoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubCategoriaDTO result = subCategoriaService.save(subCategoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-categorias} : get all the subCategorias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subCategorias in body.
     */
    @GetMapping("/sub-categorias")
    public ResponseEntity<List<SubCategoriaDTO>> getAllSubCategorias(SubCategoriaCriteria criteria) {
        log.debug("REST request to get SubCategorias by criteria: {}", criteria);
        List<SubCategoriaDTO> entityList = subCategoriaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /sub-categorias/count} : count all the subCategorias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sub-categorias/count")
    public ResponseEntity<Long> countSubCategorias(SubCategoriaCriteria criteria) {
        log.debug("REST request to count SubCategorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(subCategoriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sub-categorias/:id} : get the "id" subCategoria.
     *
     * @param id the id of the subCategoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subCategoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-categorias/{id}")
    public ResponseEntity<SubCategoriaDTO> getSubCategoria(@PathVariable Long id) {
        log.debug("REST request to get SubCategoria : {}", id);
        Optional<SubCategoriaDTO> subCategoriaDTO = subCategoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subCategoriaDTO);
    }

    /**
     * {@code DELETE  /sub-categorias/:id} : delete the "id" subCategoria.
     *
     * @param id the id of the subCategoriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-categorias/{id}")
    public ResponseEntity<Void> deleteSubCategoria(@PathVariable Long id) {
        log.debug("REST request to delete SubCategoria : {}", id);
        subCategoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
