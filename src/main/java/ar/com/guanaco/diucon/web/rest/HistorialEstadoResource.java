package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.service.HistorialEstadoService;
import ar.com.guanaco.diucon.web.rest.errors.BadRequestAlertException;
import ar.com.guanaco.diucon.service.dto.HistorialEstadoDTO;
import ar.com.guanaco.diucon.service.dto.HistorialEstadoCriteria;
import ar.com.guanaco.diucon.service.HistorialEstadoQueryService;

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
 * REST controller for managing {@link ar.com.guanaco.diucon.domain.HistorialEstado}.
 */
@RestController
@RequestMapping("/api")
public class HistorialEstadoResource {

    private final Logger log = LoggerFactory.getLogger(HistorialEstadoResource.class);

    private static final String ENTITY_NAME = "historialEstado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistorialEstadoService historialEstadoService;

    private final HistorialEstadoQueryService historialEstadoQueryService;

    public HistorialEstadoResource(HistorialEstadoService historialEstadoService, HistorialEstadoQueryService historialEstadoQueryService) {
        this.historialEstadoService = historialEstadoService;
        this.historialEstadoQueryService = historialEstadoQueryService;
    }

    /**
     * {@code POST  /historial-estados} : Create a new historialEstado.
     *
     * @param historialEstadoDTO the historialEstadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historialEstadoDTO, or with status {@code 400 (Bad Request)} if the historialEstado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historial-estados")
    public ResponseEntity<HistorialEstadoDTO> createHistorialEstado(@Valid @RequestBody HistorialEstadoDTO historialEstadoDTO) throws URISyntaxException {
        log.debug("REST request to save HistorialEstado : {}", historialEstadoDTO);
        if (historialEstadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new historialEstado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistorialEstadoDTO result = historialEstadoService.save(historialEstadoDTO);
        return ResponseEntity.created(new URI("/api/historial-estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historial-estados} : Updates an existing historialEstado.
     *
     * @param historialEstadoDTO the historialEstadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historialEstadoDTO,
     * or with status {@code 400 (Bad Request)} if the historialEstadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historialEstadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historial-estados")
    public ResponseEntity<HistorialEstadoDTO> updateHistorialEstado(@Valid @RequestBody HistorialEstadoDTO historialEstadoDTO) throws URISyntaxException {
        log.debug("REST request to update HistorialEstado : {}", historialEstadoDTO);
        if (historialEstadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistorialEstadoDTO result = historialEstadoService.save(historialEstadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historialEstadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /historial-estados} : get all the historialEstados.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historialEstados in body.
     */
    @GetMapping("/historial-estados")
    public ResponseEntity<List<HistorialEstadoDTO>> getAllHistorialEstados(HistorialEstadoCriteria criteria) {
        log.debug("REST request to get HistorialEstados by criteria: {}", criteria);
        List<HistorialEstadoDTO> entityList = historialEstadoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /historial-estados/count} : count all the historialEstados.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/historial-estados/count")
    public ResponseEntity<Long> countHistorialEstados(HistorialEstadoCriteria criteria) {
        log.debug("REST request to count HistorialEstados by criteria: {}", criteria);
        return ResponseEntity.ok().body(historialEstadoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /historial-estados/:id} : get the "id" historialEstado.
     *
     * @param id the id of the historialEstadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historialEstadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historial-estados/{id}")
    public ResponseEntity<HistorialEstadoDTO> getHistorialEstado(@PathVariable Long id) {
        log.debug("REST request to get HistorialEstado : {}", id);
        Optional<HistorialEstadoDTO> historialEstadoDTO = historialEstadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historialEstadoDTO);
    }

    /**
     * {@code DELETE  /historial-estados/:id} : delete the "id" historialEstado.
     *
     * @param id the id of the historialEstadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historial-estados/{id}")
    public ResponseEntity<Void> deleteHistorialEstado(@PathVariable Long id) {
        log.debug("REST request to delete HistorialEstado : {}", id);
        historialEstadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
