package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.service.PlantillaService;
import ar.com.guanaco.diucon.web.rest.errors.BadRequestAlertException;
import ar.com.guanaco.diucon.service.dto.PlantillaDTO;
import ar.com.guanaco.diucon.service.dto.PlantillaCriteria;
import ar.com.guanaco.diucon.service.PlantillaQueryService;

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
 * REST controller for managing {@link ar.com.guanaco.diucon.domain.Plantilla}.
 */
@RestController
@RequestMapping("/api")
public class PlantillaResource {

    private final Logger log = LoggerFactory.getLogger(PlantillaResource.class);

    private static final String ENTITY_NAME = "plantilla";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantillaService plantillaService;

    private final PlantillaQueryService plantillaQueryService;

    public PlantillaResource(PlantillaService plantillaService, PlantillaQueryService plantillaQueryService) {
        this.plantillaService = plantillaService;
        this.plantillaQueryService = plantillaQueryService;
    }

    /**
     * {@code POST  /plantillas} : Create a new plantilla.
     *
     * @param plantillaDTO the plantillaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plantillaDTO, or with status {@code 400 (Bad Request)} if the plantilla has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plantillas")
    public ResponseEntity<PlantillaDTO> createPlantilla(@Valid @RequestBody PlantillaDTO plantillaDTO) throws URISyntaxException {
        log.debug("REST request to save Plantilla : {}", plantillaDTO);
        if (plantillaDTO.getId() != null) {
            throw new BadRequestAlertException("A new plantilla cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlantillaDTO result = plantillaService.save(plantillaDTO);
        return ResponseEntity.created(new URI("/api/plantillas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plantillas} : Updates an existing plantilla.
     *
     * @param plantillaDTO the plantillaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantillaDTO,
     * or with status {@code 400 (Bad Request)} if the plantillaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plantillaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plantillas")
    public ResponseEntity<PlantillaDTO> updatePlantilla(@Valid @RequestBody PlantillaDTO plantillaDTO) throws URISyntaxException {
        log.debug("REST request to update Plantilla : {}", plantillaDTO);
        if (plantillaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlantillaDTO result = plantillaService.save(plantillaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantillaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plantillas} : get all the plantillas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plantillas in body.
     */
    @GetMapping("/plantillas")
    public ResponseEntity<List<PlantillaDTO>> getAllPlantillas(PlantillaCriteria criteria) {
        log.debug("REST request to get Plantillas by criteria: {}", criteria);
        List<PlantillaDTO> entityList = plantillaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /plantillas/count} : count all the plantillas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plantillas/count")
    public ResponseEntity<Long> countPlantillas(PlantillaCriteria criteria) {
        log.debug("REST request to count Plantillas by criteria: {}", criteria);
        return ResponseEntity.ok().body(plantillaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plantillas/:id} : get the "id" plantilla.
     *
     * @param id the id of the plantillaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plantillaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plantillas/{id}")
    public ResponseEntity<PlantillaDTO> getPlantilla(@PathVariable Long id) {
        log.debug("REST request to get Plantilla : {}", id);
        Optional<PlantillaDTO> plantillaDTO = plantillaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plantillaDTO);
    }

    /**
     * {@code DELETE  /plantillas/:id} : delete the "id" plantilla.
     *
     * @param id the id of the plantillaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plantillas/{id}")
    public ResponseEntity<Void> deletePlantilla(@PathVariable Long id) {
        log.debug("REST request to delete Plantilla : {}", id);
        plantillaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
