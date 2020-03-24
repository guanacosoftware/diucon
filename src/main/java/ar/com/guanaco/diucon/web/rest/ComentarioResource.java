package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.service.ComentarioService;
import ar.com.guanaco.diucon.web.rest.errors.BadRequestAlertException;
import ar.com.guanaco.diucon.service.dto.ComentarioDTO;
import ar.com.guanaco.diucon.service.dto.ComentarioCriteria;
import ar.com.guanaco.diucon.service.ComentarioQueryService;

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
 * REST controller for managing {@link ar.com.guanaco.diucon.domain.Comentario}.
 */
@RestController
@RequestMapping("/api")
public class ComentarioResource {

    private final Logger log = LoggerFactory.getLogger(ComentarioResource.class);

    private static final String ENTITY_NAME = "comentario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComentarioService comentarioService;

    private final ComentarioQueryService comentarioQueryService;

    public ComentarioResource(ComentarioService comentarioService, ComentarioQueryService comentarioQueryService) {
        this.comentarioService = comentarioService;
        this.comentarioQueryService = comentarioQueryService;
    }

    /**
     * {@code POST  /comentarios} : Create a new comentario.
     *
     * @param comentarioDTO the comentarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comentarioDTO, or with status {@code 400 (Bad Request)} if the comentario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comentarios")
    public ResponseEntity<ComentarioDTO> createComentario(@Valid @RequestBody ComentarioDTO comentarioDTO) throws URISyntaxException {
        log.debug("REST request to save Comentario : {}", comentarioDTO);
        if (comentarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new comentario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComentarioDTO result = comentarioService.save(comentarioDTO);
        return ResponseEntity.created(new URI("/api/comentarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comentarios} : Updates an existing comentario.
     *
     * @param comentarioDTO the comentarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comentarioDTO,
     * or with status {@code 400 (Bad Request)} if the comentarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comentarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comentarios")
    public ResponseEntity<ComentarioDTO> updateComentario(@Valid @RequestBody ComentarioDTO comentarioDTO) throws URISyntaxException {
        log.debug("REST request to update Comentario : {}", comentarioDTO);
        if (comentarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComentarioDTO result = comentarioService.save(comentarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comentarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comentarios} : get all the comentarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comentarios in body.
     */
    @GetMapping("/comentarios")
    public ResponseEntity<List<ComentarioDTO>> getAllComentarios(ComentarioCriteria criteria) {
        log.debug("REST request to get Comentarios by criteria: {}", criteria);
        List<ComentarioDTO> entityList = comentarioQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /comentarios/count} : count all the comentarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comentarios/count")
    public ResponseEntity<Long> countComentarios(ComentarioCriteria criteria) {
        log.debug("REST request to count Comentarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(comentarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comentarios/:id} : get the "id" comentario.
     *
     * @param id the id of the comentarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comentarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> getComentario(@PathVariable Long id) {
        log.debug("REST request to get Comentario : {}", id);
        Optional<ComentarioDTO> comentarioDTO = comentarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comentarioDTO);
    }

    /**
     * {@code DELETE  /comentarios/:id} : delete the "id" comentario.
     *
     * @param id the id of the comentarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        log.debug("REST request to delete Comentario : {}", id);
        comentarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
