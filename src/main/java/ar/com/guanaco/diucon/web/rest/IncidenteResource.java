package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.service.IncidenteService;
import ar.com.guanaco.diucon.web.rest.errors.BadRequestAlertException;
import ar.com.guanaco.diucon.service.dto.IncidenteDTO;
import ar.com.guanaco.diucon.service.dto.IncidenteCriteria;
import ar.com.guanaco.diucon.service.IncidenteQueryService;

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
 * REST controller for managing {@link ar.com.guanaco.diucon.domain.Incidente}.
 */
@RestController
@RequestMapping("/api")
public class IncidenteResource {

    private final Logger log = LoggerFactory.getLogger(IncidenteResource.class);

    private static final String ENTITY_NAME = "incidente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncidenteService incidenteService;

    private final IncidenteQueryService incidenteQueryService;

    public IncidenteResource(IncidenteService incidenteService, IncidenteQueryService incidenteQueryService) {
        this.incidenteService = incidenteService;
        this.incidenteQueryService = incidenteQueryService;
    }

    /**
     * {@code POST  /incidentes} : Create a new incidente.
     *
     * @param incidenteDTO the incidenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incidenteDTO, or with status {@code 400 (Bad Request)} if the incidente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incidentes")
    public ResponseEntity<IncidenteDTO> createIncidente(@Valid @RequestBody IncidenteDTO incidenteDTO) throws URISyntaxException {
        log.debug("REST request to save Incidente : {}", incidenteDTO);
        if (incidenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidenteDTO result = incidenteService.save(incidenteDTO);
        return ResponseEntity.created(new URI("/api/incidentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incidentes} : Updates an existing incidente.
     *
     * @param incidenteDTO the incidenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incidenteDTO,
     * or with status {@code 400 (Bad Request)} if the incidenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incidenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incidentes")
    public ResponseEntity<IncidenteDTO> updateIncidente(@Valid @RequestBody IncidenteDTO incidenteDTO) throws URISyntaxException {
        log.debug("REST request to update Incidente : {}", incidenteDTO);
        if (incidenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncidenteDTO result = incidenteService.save(incidenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incidenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incidentes} : get all the incidentes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incidentes in body.
     */
    @GetMapping("/incidentes")
    public ResponseEntity<List<IncidenteDTO>> getAllIncidentes(IncidenteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Incidentes by criteria: {}", criteria);
        Page<IncidenteDTO> page = incidenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incidentes/count} : count all the incidentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/incidentes/count")
    public ResponseEntity<Long> countIncidentes(IncidenteCriteria criteria) {
        log.debug("REST request to count Incidentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(incidenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /incidentes/:id} : get the "id" incidente.
     *
     * @param id the id of the incidenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incidenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incidentes/{id}")
    public ResponseEntity<IncidenteDTO> getIncidente(@PathVariable Long id) {
        log.debug("REST request to get Incidente : {}", id);
        Optional<IncidenteDTO> incidenteDTO = incidenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidenteDTO);
    }

    /**
     * {@code DELETE  /incidentes/:id} : delete the "id" incidente.
     *
     * @param id the id of the incidenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incidentes/{id}")
    public ResponseEntity<Void> deleteIncidente(@PathVariable Long id) {
        log.debug("REST request to delete Incidente : {}", id);
        incidenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
