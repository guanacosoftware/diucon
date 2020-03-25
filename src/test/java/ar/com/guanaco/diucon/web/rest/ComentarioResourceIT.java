package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.DiuconApp;
import ar.com.guanaco.diucon.domain.Comentario;
import ar.com.guanaco.diucon.domain.User;
import ar.com.guanaco.diucon.domain.Incidente;
import ar.com.guanaco.diucon.repository.ComentarioRepository;
import ar.com.guanaco.diucon.service.ComentarioService;
import ar.com.guanaco.diucon.service.dto.ComentarioDTO;
import ar.com.guanaco.diucon.service.mapper.ComentarioMapper;
import ar.com.guanaco.diucon.service.dto.ComentarioCriteria;
import ar.com.guanaco.diucon.service.ComentarioQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ComentarioResource} REST controller.
 */
@SpringBootTest(classes = DiuconApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ComentarioResourceIT {

    private static final String DEFAULT_CUERPO = "AAAAAAAAAA";
    private static final String UPDATED_CUERPO = "BBBBBBBBBB";

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ComentarioMapper comentarioMapper;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ComentarioQueryService comentarioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComentarioMockMvc;

    private Comentario comentario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comentario createEntity(EntityManager em) {
        Comentario comentario = new Comentario()
            .cuerpo(DEFAULT_CUERPO);
        return comentario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comentario createUpdatedEntity(EntityManager em) {
        Comentario comentario = new Comentario()
            .cuerpo(UPDATED_CUERPO);
        return comentario;
    }

    @BeforeEach
    public void initTest() {
        comentario = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentario() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();

        // Create the Comentario
        ComentarioDTO comentarioDTO = comentarioMapper.toDto(comentario);
        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeCreate + 1);
        Comentario testComentario = comentarioList.get(comentarioList.size() - 1);
        assertThat(testComentario.getCuerpo()).isEqualTo(DEFAULT_CUERPO);
    }

    @Test
    @Transactional
    public void createComentarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();

        // Create the Comentario with an existing ID
        comentario.setId(1L);
        ComentarioDTO comentarioDTO = comentarioMapper.toDto(comentario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComentarios() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get all the comentarioList
        restComentarioMockMvc.perform(get("/api/comentarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentario.getId().intValue())))
            .andExpect(jsonPath("$.[*].cuerpo").value(hasItem(DEFAULT_CUERPO.toString())));
    }
    
    @Test
    @Transactional
    public void getComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", comentario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comentario.getId().intValue()))
            .andExpect(jsonPath("$.cuerpo").value(DEFAULT_CUERPO.toString()));
    }


    @Test
    @Transactional
    public void getComentariosByIdFiltering() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        Long id = comentario.getId();

        defaultComentarioShouldBeFound("id.equals=" + id);
        defaultComentarioShouldNotBeFound("id.notEquals=" + id);

        defaultComentarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComentarioShouldNotBeFound("id.greaterThan=" + id);

        defaultComentarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComentarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllComentariosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);
        User usuario = UserResourceIT.createEntity(em);
        em.persist(usuario);
        em.flush();
        comentario.setUsuario(usuario);
        comentarioRepository.saveAndFlush(comentario);
        Long usuarioId = usuario.getId();

        // Get all the comentarioList where usuario equals to usuarioId
        defaultComentarioShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the comentarioList where usuario equals to usuarioId + 1
        defaultComentarioShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllComentariosByIncidenteIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);
        Incidente incidente = IncidenteResourceIT.createEntity(em);
        em.persist(incidente);
        em.flush();
        comentario.setIncidente(incidente);
        comentarioRepository.saveAndFlush(comentario);
        Long incidenteId = incidente.getId();

        // Get all the comentarioList where incidente equals to incidenteId
        defaultComentarioShouldBeFound("incidenteId.equals=" + incidenteId);

        // Get all the comentarioList where incidente equals to incidenteId + 1
        defaultComentarioShouldNotBeFound("incidenteId.equals=" + (incidenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComentarioShouldBeFound(String filter) throws Exception {
        restComentarioMockMvc.perform(get("/api/comentarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentario.getId().intValue())))
            .andExpect(jsonPath("$.[*].cuerpo").value(hasItem(DEFAULT_CUERPO.toString())));

        // Check, that the count call also returns 1
        restComentarioMockMvc.perform(get("/api/comentarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComentarioShouldNotBeFound(String filter) throws Exception {
        restComentarioMockMvc.perform(get("/api/comentarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComentarioMockMvc.perform(get("/api/comentarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComentario() throws Exception {
        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // Update the comentario
        Comentario updatedComentario = comentarioRepository.findById(comentario.getId()).get();
        // Disconnect from session so that the updates on updatedComentario are not directly saved in db
        em.detach(updatedComentario);
        updatedComentario
            .cuerpo(UPDATED_CUERPO);
        ComentarioDTO comentarioDTO = comentarioMapper.toDto(updatedComentario);

        restComentarioMockMvc.perform(put("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentarioDTO)))
            .andExpect(status().isOk());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeUpdate);
        Comentario testComentario = comentarioList.get(comentarioList.size() - 1);
        assertThat(testComentario.getCuerpo()).isEqualTo(UPDATED_CUERPO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentario() throws Exception {
        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // Create the Comentario
        ComentarioDTO comentarioDTO = comentarioMapper.toDto(comentario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComentarioMockMvc.perform(put("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        int databaseSizeBeforeDelete = comentarioRepository.findAll().size();

        // Delete the comentario
        restComentarioMockMvc.perform(delete("/api/comentarios/{id}", comentario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
