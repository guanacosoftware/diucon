package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.DiuconApp;
import ar.com.guanaco.diucon.domain.HistorialEstado;
import ar.com.guanaco.diucon.domain.User;
import ar.com.guanaco.diucon.domain.Incidente;
import ar.com.guanaco.diucon.repository.HistorialEstadoRepository;
import ar.com.guanaco.diucon.service.HistorialEstadoService;
import ar.com.guanaco.diucon.service.dto.HistorialEstadoDTO;
import ar.com.guanaco.diucon.service.mapper.HistorialEstadoMapper;
import ar.com.guanaco.diucon.service.dto.HistorialEstadoCriteria;
import ar.com.guanaco.diucon.service.HistorialEstadoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.guanaco.diucon.domain.enumeration.Estado;
/**
 * Integration tests for the {@link HistorialEstadoResource} REST controller.
 */
@SpringBootTest(classes = DiuconApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class HistorialEstadoResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Estado DEFAULT_ESTADO = Estado.CREADA;
    private static final Estado UPDATED_ESTADO = Estado.ASIGNADA;

    @Autowired
    private HistorialEstadoRepository historialEstadoRepository;

    @Autowired
    private HistorialEstadoMapper historialEstadoMapper;

    @Autowired
    private HistorialEstadoService historialEstadoService;

    @Autowired
    private HistorialEstadoQueryService historialEstadoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistorialEstadoMockMvc;

    private HistorialEstado historialEstado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistorialEstado createEntity(EntityManager em) {
        HistorialEstado historialEstado = new HistorialEstado()
            .fecha(DEFAULT_FECHA)
            .estado(DEFAULT_ESTADO);
        return historialEstado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistorialEstado createUpdatedEntity(EntityManager em) {
        HistorialEstado historialEstado = new HistorialEstado()
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);
        return historialEstado;
    }

    @BeforeEach
    public void initTest() {
        historialEstado = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistorialEstado() throws Exception {
        int databaseSizeBeforeCreate = historialEstadoRepository.findAll().size();

        // Create the HistorialEstado
        HistorialEstadoDTO historialEstadoDTO = historialEstadoMapper.toDto(historialEstado);
        restHistorialEstadoMockMvc.perform(post("/api/historial-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historialEstadoDTO)))
            .andExpect(status().isCreated());

        // Validate the HistorialEstado in the database
        List<HistorialEstado> historialEstadoList = historialEstadoRepository.findAll();
        assertThat(historialEstadoList).hasSize(databaseSizeBeforeCreate + 1);
        HistorialEstado testHistorialEstado = historialEstadoList.get(historialEstadoList.size() - 1);
        assertThat(testHistorialEstado.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testHistorialEstado.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createHistorialEstadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historialEstadoRepository.findAll().size();

        // Create the HistorialEstado with an existing ID
        historialEstado.setId(1L);
        HistorialEstadoDTO historialEstadoDTO = historialEstadoMapper.toDto(historialEstado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistorialEstadoMockMvc.perform(post("/api/historial-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historialEstadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HistorialEstado in the database
        List<HistorialEstado> historialEstadoList = historialEstadoRepository.findAll();
        assertThat(historialEstadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = historialEstadoRepository.findAll().size();
        // set the field null
        historialEstado.setFecha(null);

        // Create the HistorialEstado, which fails.
        HistorialEstadoDTO historialEstadoDTO = historialEstadoMapper.toDto(historialEstado);

        restHistorialEstadoMockMvc.perform(post("/api/historial-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historialEstadoDTO)))
            .andExpect(status().isBadRequest());

        List<HistorialEstado> historialEstadoList = historialEstadoRepository.findAll();
        assertThat(historialEstadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistorialEstados() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList
        restHistorialEstadoMockMvc.perform(get("/api/historial-estados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historialEstado.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getHistorialEstado() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get the historialEstado
        restHistorialEstadoMockMvc.perform(get("/api/historial-estados/{id}", historialEstado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historialEstado.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }


    @Test
    @Transactional
    public void getHistorialEstadosByIdFiltering() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        Long id = historialEstado.getId();

        defaultHistorialEstadoShouldBeFound("id.equals=" + id);
        defaultHistorialEstadoShouldNotBeFound("id.notEquals=" + id);

        defaultHistorialEstadoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHistorialEstadoShouldNotBeFound("id.greaterThan=" + id);

        defaultHistorialEstadoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHistorialEstadoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHistorialEstadosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList where fecha equals to DEFAULT_FECHA
        defaultHistorialEstadoShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the historialEstadoList where fecha equals to UPDATED_FECHA
        defaultHistorialEstadoShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllHistorialEstadosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList where fecha not equals to DEFAULT_FECHA
        defaultHistorialEstadoShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the historialEstadoList where fecha not equals to UPDATED_FECHA
        defaultHistorialEstadoShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllHistorialEstadosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultHistorialEstadoShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the historialEstadoList where fecha equals to UPDATED_FECHA
        defaultHistorialEstadoShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllHistorialEstadosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList where fecha is not null
        defaultHistorialEstadoShouldBeFound("fecha.specified=true");

        // Get all the historialEstadoList where fecha is null
        defaultHistorialEstadoShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllHistorialEstadosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList where estado equals to DEFAULT_ESTADO
        defaultHistorialEstadoShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the historialEstadoList where estado equals to UPDATED_ESTADO
        defaultHistorialEstadoShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllHistorialEstadosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList where estado not equals to DEFAULT_ESTADO
        defaultHistorialEstadoShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the historialEstadoList where estado not equals to UPDATED_ESTADO
        defaultHistorialEstadoShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllHistorialEstadosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultHistorialEstadoShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the historialEstadoList where estado equals to UPDATED_ESTADO
        defaultHistorialEstadoShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllHistorialEstadosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        // Get all the historialEstadoList where estado is not null
        defaultHistorialEstadoShouldBeFound("estado.specified=true");

        // Get all the historialEstadoList where estado is null
        defaultHistorialEstadoShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    public void getAllHistorialEstadosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);
        User usuario = UserResourceIT.createEntity(em);
        em.persist(usuario);
        em.flush();
        historialEstado.setUsuario(usuario);
        historialEstadoRepository.saveAndFlush(historialEstado);
        Long usuarioId = usuario.getId();

        // Get all the historialEstadoList where usuario equals to usuarioId
        defaultHistorialEstadoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the historialEstadoList where usuario equals to usuarioId + 1
        defaultHistorialEstadoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllHistorialEstadosByIncidenteIsEqualToSomething() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);
        Incidente incidente = IncidenteResourceIT.createEntity(em);
        em.persist(incidente);
        em.flush();
        historialEstado.setIncidente(incidente);
        historialEstadoRepository.saveAndFlush(historialEstado);
        Long incidenteId = incidente.getId();

        // Get all the historialEstadoList where incidente equals to incidenteId
        defaultHistorialEstadoShouldBeFound("incidenteId.equals=" + incidenteId);

        // Get all the historialEstadoList where incidente equals to incidenteId + 1
        defaultHistorialEstadoShouldNotBeFound("incidenteId.equals=" + (incidenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHistorialEstadoShouldBeFound(String filter) throws Exception {
        restHistorialEstadoMockMvc.perform(get("/api/historial-estados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historialEstado.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));

        // Check, that the count call also returns 1
        restHistorialEstadoMockMvc.perform(get("/api/historial-estados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHistorialEstadoShouldNotBeFound(String filter) throws Exception {
        restHistorialEstadoMockMvc.perform(get("/api/historial-estados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHistorialEstadoMockMvc.perform(get("/api/historial-estados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHistorialEstado() throws Exception {
        // Get the historialEstado
        restHistorialEstadoMockMvc.perform(get("/api/historial-estados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistorialEstado() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        int databaseSizeBeforeUpdate = historialEstadoRepository.findAll().size();

        // Update the historialEstado
        HistorialEstado updatedHistorialEstado = historialEstadoRepository.findById(historialEstado.getId()).get();
        // Disconnect from session so that the updates on updatedHistorialEstado are not directly saved in db
        em.detach(updatedHistorialEstado);
        updatedHistorialEstado
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);
        HistorialEstadoDTO historialEstadoDTO = historialEstadoMapper.toDto(updatedHistorialEstado);

        restHistorialEstadoMockMvc.perform(put("/api/historial-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historialEstadoDTO)))
            .andExpect(status().isOk());

        // Validate the HistorialEstado in the database
        List<HistorialEstado> historialEstadoList = historialEstadoRepository.findAll();
        assertThat(historialEstadoList).hasSize(databaseSizeBeforeUpdate);
        HistorialEstado testHistorialEstado = historialEstadoList.get(historialEstadoList.size() - 1);
        assertThat(testHistorialEstado.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testHistorialEstado.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingHistorialEstado() throws Exception {
        int databaseSizeBeforeUpdate = historialEstadoRepository.findAll().size();

        // Create the HistorialEstado
        HistorialEstadoDTO historialEstadoDTO = historialEstadoMapper.toDto(historialEstado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistorialEstadoMockMvc.perform(put("/api/historial-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historialEstadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HistorialEstado in the database
        List<HistorialEstado> historialEstadoList = historialEstadoRepository.findAll();
        assertThat(historialEstadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistorialEstado() throws Exception {
        // Initialize the database
        historialEstadoRepository.saveAndFlush(historialEstado);

        int databaseSizeBeforeDelete = historialEstadoRepository.findAll().size();

        // Delete the historialEstado
        restHistorialEstadoMockMvc.perform(delete("/api/historial-estados/{id}", historialEstado.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistorialEstado> historialEstadoList = historialEstadoRepository.findAll();
        assertThat(historialEstadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
