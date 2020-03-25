package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.service.ResponsableService;
import ar.com.guanaco.diucon.web.rest.errors.BadRequestAlertException;
import ar.com.guanaco.diucon.service.dto.ResponsableDTO;
import ar.com.guanaco.diucon.service.dto.ResponsableCriteria;
import ar.com.guanaco.diucon.service.ResponsableQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.com.guanaco.diucon.domain.Responsable}.
 */
@RestController
@RequestMapping("/api")
public class ResponsableResource {

    private final Logger log = LoggerFactory.getLogger(ResponsableResource.class);

    private static final String ENTITY_NAME = "responsable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsableService responsableService;

    private final ResponsableQueryService responsableQueryService;

    public ResponsableResource(ResponsableService responsableService, ResponsableQueryService responsableQueryService) {
        this.responsableService = responsableService;
        this.responsableQueryService = responsableQueryService;
    }

    /**
     * {@code POST  /responsables} : Create a new responsable.
     *
     * @param responsableDTO the responsableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsableDTO, or with status {@code 400 (Bad Request)} if the responsable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsables")
    public ResponseEntity<ResponsableDTO> createResponsable(@Valid @RequestBody ResponsableDTO responsableDTO) throws URISyntaxException {
        log.debug("REST request to save Responsable : {}", responsableDTO);
        if (responsableDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsableDTO result = responsableService.save(responsableDTO);
        return ResponseEntity.created(new URI("/api/responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsables} : Updates an existing responsable.
     *
     * @param responsableDTO the responsableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsableDTO,
     * or with status {@code 400 (Bad Request)} if the responsableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsables")
    public ResponseEntity<ResponsableDTO> updateResponsable(@Valid @RequestBody ResponsableDTO responsableDTO) throws URISyntaxException {
        log.debug("REST request to update Responsable : {}", responsableDTO);
        if (responsableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResponsableDTO result = responsableService.save(responsableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /responsables} : get all the responsables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsables in body.
     */
    @GetMapping("/responsables")
    public ResponseEntity<List<ResponsableDTO>> getAllResponsables(ResponsableCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Responsables by criteria: {}", criteria);
        Page<ResponsableDTO> page = responsableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsables/count} : count all the responsables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/responsables/count")
    public ResponseEntity<Long> countResponsables(ResponsableCriteria criteria) {
        log.debug("REST request to count Responsables by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsables/:id} : get the "id" responsable.
     *
     * @param id the id of the responsableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsables/{id}")
    public ResponseEntity<ResponsableDTO> getResponsable(@PathVariable Long id) {
        log.debug("REST request to get Responsable : {}", id);
        Optional<ResponsableDTO> responsableDTO = responsableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsableDTO);
    }

    /**
     * {@code DELETE  /responsables/:id} : delete the "id" responsable.
     *
     * @param id the id of the responsableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsables/{id}")
    public ResponseEntity<Void> deleteResponsable(@PathVariable Long id) {
        log.debug("REST request to delete Responsable : {}", id);
        responsableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
