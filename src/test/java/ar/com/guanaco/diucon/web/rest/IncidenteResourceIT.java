package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.DiuconApp;
import ar.com.guanaco.diucon.domain.Incidente;
import ar.com.guanaco.diucon.domain.Comentario;
import ar.com.guanaco.diucon.domain.HistorialEstado;
import ar.com.guanaco.diucon.domain.Categoria;
import ar.com.guanaco.diucon.domain.SubCategoria;
import ar.com.guanaco.diucon.domain.User;
import ar.com.guanaco.diucon.domain.Responsable;
import ar.com.guanaco.diucon.repository.IncidenteRepository;
import ar.com.guanaco.diucon.service.IncidenteService;
import ar.com.guanaco.diucon.service.dto.IncidenteDTO;
import ar.com.guanaco.diucon.service.mapper.IncidenteMapper;
import ar.com.guanaco.diucon.service.dto.IncidenteCriteria;
import ar.com.guanaco.diucon.service.IncidenteQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.guanaco.diucon.domain.enumeration.Estado;
/**
 * Integration tests for the {@link IncidenteResource} REST controller.
 */
@SpringBootTest(classes = DiuconApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IncidenteResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CUERPO = "AAAAAAAAAA";
    private static final String UPDATED_CUERPO = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.CREADA;
    private static final Estado UPDATED_ESTADO = Estado.ASIGNADA;

    private static final String DEFAULT_LOCALIZACION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIZACION = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUD = 1D;
    private static final Double UPDATED_LATITUD = 2D;
    private static final Double SMALLER_LATITUD = 1D - 1D;

    private static final Double DEFAULT_LONGITUD = 1D;
    private static final Double UPDATED_LONGITUD = 2D;
    private static final Double SMALLER_LONGITUD = 1D - 1D;

    private static final LocalDate DEFAULT_FECHA_RESOLUCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_RESOLUCION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_RESOLUCION = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_CIERRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CIERRE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_CIERRE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EMAIL = "#(/p)@p*.!8";
    private static final String UPDATED_EMAIL = "(y[1N@TpV\".jK`-q$";

    @Autowired
    private IncidenteRepository incidenteRepository;

    @Autowired
    private IncidenteMapper incidenteMapper;

    @Autowired
    private IncidenteService incidenteService;

    @Autowired
    private IncidenteQueryService incidenteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncidenteMockMvc;

    private Incidente incidente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incidente createEntity(EntityManager em) {
        Incidente incidente = new Incidente()
            .fecha(DEFAULT_FECHA)
            .cuerpo(DEFAULT_CUERPO)
            .estado(DEFAULT_ESTADO)
            .localizacion(DEFAULT_LOCALIZACION)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .fechaResolucion(DEFAULT_FECHA_RESOLUCION)
            .fechaCierre(DEFAULT_FECHA_CIERRE)
            .email(DEFAULT_EMAIL);
        return incidente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incidente createUpdatedEntity(EntityManager em) {
        Incidente incidente = new Incidente()
            .fecha(UPDATED_FECHA)
            .cuerpo(UPDATED_CUERPO)
            .estado(UPDATED_ESTADO)
            .localizacion(UPDATED_LOCALIZACION)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .fechaResolucion(UPDATED_FECHA_RESOLUCION)
            .fechaCierre(UPDATED_FECHA_CIERRE)
            .email(UPDATED_EMAIL);
        return incidente;
    }

    @BeforeEach
    public void initTest() {
        incidente = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncidente() throws Exception {
        int databaseSizeBeforeCreate = incidenteRepository.findAll().size();

        // Create the Incidente
        IncidenteDTO incidenteDTO = incidenteMapper.toDto(incidente);
        restIncidenteMockMvc.perform(post("/api/incidentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incidenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Incidente in the database
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeCreate + 1);
        Incidente testIncidente = incidenteList.get(incidenteList.size() - 1);
        assertThat(testIncidente.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testIncidente.getCuerpo()).isEqualTo(DEFAULT_CUERPO);
        assertThat(testIncidente.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testIncidente.getLocalizacion()).isEqualTo(DEFAULT_LOCALIZACION);
        assertThat(testIncidente.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testIncidente.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testIncidente.getFechaResolucion()).isEqualTo(DEFAULT_FECHA_RESOLUCION);
        assertThat(testIncidente.getFechaCierre()).isEqualTo(DEFAULT_FECHA_CIERRE);
        assertThat(testIncidente.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createIncidenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidenteRepository.findAll().size();

        // Create the Incidente with an existing ID
        incidente.setId(1L);
        IncidenteDTO incidenteDTO = incidenteMapper.toDto(incidente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidenteMockMvc.perform(post("/api/incidentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incidenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incidente in the database
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidenteRepository.findAll().size();
        // set the field null
        incidente.setFecha(null);

        // Create the Incidente, which fails.
        IncidenteDTO incidenteDTO = incidenteMapper.toDto(incidente);

        restIncidenteMockMvc.perform(post("/api/incidentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incidenteDTO)))
            .andExpect(status().isBadRequest());

        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidenteRepository.findAll().size();
        // set the field null
        incidente.setEstado(null);

        // Create the Incidente, which fails.
        IncidenteDTO incidenteDTO = incidenteMapper.toDto(incidente);

        restIncidenteMockMvc.perform(post("/api/incidentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incidenteDTO)))
            .andExpect(status().isBadRequest());

        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncidentes() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList
        restIncidenteMockMvc.perform(get("/api/incidentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].cuerpo").value(hasItem(DEFAULT_CUERPO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].localizacion").value(hasItem(DEFAULT_LOCALIZACION)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaResolucion").value(hasItem(DEFAULT_FECHA_RESOLUCION.toString())))
            .andExpect(jsonPath("$.[*].fechaCierre").value(hasItem(DEFAULT_FECHA_CIERRE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getIncidente() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get the incidente
        restIncidenteMockMvc.perform(get("/api/incidentes/{id}", incidente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incidente.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.cuerpo").value(DEFAULT_CUERPO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.localizacion").value(DEFAULT_LOCALIZACION))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.doubleValue()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.doubleValue()))
            .andExpect(jsonPath("$.fechaResolucion").value(DEFAULT_FECHA_RESOLUCION.toString()))
            .andExpect(jsonPath("$.fechaCierre").value(DEFAULT_FECHA_CIERRE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }


    @Test
    @Transactional
    public void getIncidentesByIdFiltering() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        Long id = incidente.getId();

        defaultIncidenteShouldBeFound("id.equals=" + id);
        defaultIncidenteShouldNotBeFound("id.notEquals=" + id);

        defaultIncidenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIncidenteShouldNotBeFound("id.greaterThan=" + id);

        defaultIncidenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIncidenteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIncidentesByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fecha equals to DEFAULT_FECHA
        defaultIncidenteShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the incidenteList where fecha equals to UPDATED_FECHA
        defaultIncidenteShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fecha not equals to DEFAULT_FECHA
        defaultIncidenteShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the incidenteList where fecha not equals to UPDATED_FECHA
        defaultIncidenteShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultIncidenteShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the incidenteList where fecha equals to UPDATED_FECHA
        defaultIncidenteShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fecha is not null
        defaultIncidenteShouldBeFound("fecha.specified=true");

        // Get all the incidenteList where fecha is null
        defaultIncidenteShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentesByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where estado equals to DEFAULT_ESTADO
        defaultIncidenteShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the incidenteList where estado equals to UPDATED_ESTADO
        defaultIncidenteShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllIncidentesByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where estado not equals to DEFAULT_ESTADO
        defaultIncidenteShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the incidenteList where estado not equals to UPDATED_ESTADO
        defaultIncidenteShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllIncidentesByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultIncidenteShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the incidenteList where estado equals to UPDATED_ESTADO
        defaultIncidenteShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllIncidentesByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where estado is not null
        defaultIncidenteShouldBeFound("estado.specified=true");

        // Get all the incidenteList where estado is null
        defaultIncidenteShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentesByLocalizacionIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where localizacion equals to DEFAULT_LOCALIZACION
        defaultIncidenteShouldBeFound("localizacion.equals=" + DEFAULT_LOCALIZACION);

        // Get all the incidenteList where localizacion equals to UPDATED_LOCALIZACION
        defaultIncidenteShouldNotBeFound("localizacion.equals=" + UPDATED_LOCALIZACION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLocalizacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where localizacion not equals to DEFAULT_LOCALIZACION
        defaultIncidenteShouldNotBeFound("localizacion.notEquals=" + DEFAULT_LOCALIZACION);

        // Get all the incidenteList where localizacion not equals to UPDATED_LOCALIZACION
        defaultIncidenteShouldBeFound("localizacion.notEquals=" + UPDATED_LOCALIZACION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLocalizacionIsInShouldWork() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where localizacion in DEFAULT_LOCALIZACION or UPDATED_LOCALIZACION
        defaultIncidenteShouldBeFound("localizacion.in=" + DEFAULT_LOCALIZACION + "," + UPDATED_LOCALIZACION);

        // Get all the incidenteList where localizacion equals to UPDATED_LOCALIZACION
        defaultIncidenteShouldNotBeFound("localizacion.in=" + UPDATED_LOCALIZACION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLocalizacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where localizacion is not null
        defaultIncidenteShouldBeFound("localizacion.specified=true");

        // Get all the incidenteList where localizacion is null
        defaultIncidenteShouldNotBeFound("localizacion.specified=false");
    }
                @Test
    @Transactional
    public void getAllIncidentesByLocalizacionContainsSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where localizacion contains DEFAULT_LOCALIZACION
        defaultIncidenteShouldBeFound("localizacion.contains=" + DEFAULT_LOCALIZACION);

        // Get all the incidenteList where localizacion contains UPDATED_LOCALIZACION
        defaultIncidenteShouldNotBeFound("localizacion.contains=" + UPDATED_LOCALIZACION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLocalizacionNotContainsSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where localizacion does not contain DEFAULT_LOCALIZACION
        defaultIncidenteShouldNotBeFound("localizacion.doesNotContain=" + DEFAULT_LOCALIZACION);

        // Get all the incidenteList where localizacion does not contain UPDATED_LOCALIZACION
        defaultIncidenteShouldBeFound("localizacion.doesNotContain=" + UPDATED_LOCALIZACION);
    }


    @Test
    @Transactional
    public void getAllIncidentesByLatitudIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where latitud equals to DEFAULT_LATITUD
        defaultIncidenteShouldBeFound("latitud.equals=" + DEFAULT_LATITUD);

        // Get all the incidenteList where latitud equals to UPDATED_LATITUD
        defaultIncidenteShouldNotBeFound("latitud.equals=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLatitudIsNotEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where latitud not equals to DEFAULT_LATITUD
        defaultIncidenteShouldNotBeFound("latitud.notEquals=" + DEFAULT_LATITUD);

        // Get all the incidenteList where latitud not equals to UPDATED_LATITUD
        defaultIncidenteShouldBeFound("latitud.notEquals=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLatitudIsInShouldWork() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where latitud in DEFAULT_LATITUD or UPDATED_LATITUD
        defaultIncidenteShouldBeFound("latitud.in=" + DEFAULT_LATITUD + "," + UPDATED_LATITUD);

        // Get all the incidenteList where latitud equals to UPDATED_LATITUD
        defaultIncidenteShouldNotBeFound("latitud.in=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLatitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where latitud is not null
        defaultIncidenteShouldBeFound("latitud.specified=true");

        // Get all the incidenteList where latitud is null
        defaultIncidenteShouldNotBeFound("latitud.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentesByLatitudIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where latitud is greater than or equal to DEFAULT_LATITUD
        defaultIncidenteShouldBeFound("latitud.greaterThanOrEqual=" + DEFAULT_LATITUD);

        // Get all the incidenteList where latitud is greater than or equal to UPDATED_LATITUD
        defaultIncidenteShouldNotBeFound("latitud.greaterThanOrEqual=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLatitudIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where latitud is less than or equal to DEFAULT_LATITUD
        defaultIncidenteShouldBeFound("latitud.lessThanOrEqual=" + DEFAULT_LATITUD);

        // Get all the incidenteList where latitud is less than or equal to SMALLER_LATITUD
        defaultIncidenteShouldNotBeFound("latitud.lessThanOrEqual=" + SMALLER_LATITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLatitudIsLessThanSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where latitud is less than DEFAULT_LATITUD
        defaultIncidenteShouldNotBeFound("latitud.lessThan=" + DEFAULT_LATITUD);

        // Get all the incidenteList where latitud is less than UPDATED_LATITUD
        defaultIncidenteShouldBeFound("latitud.lessThan=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLatitudIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where latitud is greater than DEFAULT_LATITUD
        defaultIncidenteShouldNotBeFound("latitud.greaterThan=" + DEFAULT_LATITUD);

        // Get all the incidenteList where latitud is greater than SMALLER_LATITUD
        defaultIncidenteShouldBeFound("latitud.greaterThan=" + SMALLER_LATITUD);
    }


    @Test
    @Transactional
    public void getAllIncidentesByLongitudIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where longitud equals to DEFAULT_LONGITUD
        defaultIncidenteShouldBeFound("longitud.equals=" + DEFAULT_LONGITUD);

        // Get all the incidenteList where longitud equals to UPDATED_LONGITUD
        defaultIncidenteShouldNotBeFound("longitud.equals=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLongitudIsNotEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where longitud not equals to DEFAULT_LONGITUD
        defaultIncidenteShouldNotBeFound("longitud.notEquals=" + DEFAULT_LONGITUD);

        // Get all the incidenteList where longitud not equals to UPDATED_LONGITUD
        defaultIncidenteShouldBeFound("longitud.notEquals=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLongitudIsInShouldWork() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where longitud in DEFAULT_LONGITUD or UPDATED_LONGITUD
        defaultIncidenteShouldBeFound("longitud.in=" + DEFAULT_LONGITUD + "," + UPDATED_LONGITUD);

        // Get all the incidenteList where longitud equals to UPDATED_LONGITUD
        defaultIncidenteShouldNotBeFound("longitud.in=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLongitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where longitud is not null
        defaultIncidenteShouldBeFound("longitud.specified=true");

        // Get all the incidenteList where longitud is null
        defaultIncidenteShouldNotBeFound("longitud.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentesByLongitudIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where longitud is greater than or equal to DEFAULT_LONGITUD
        defaultIncidenteShouldBeFound("longitud.greaterThanOrEqual=" + DEFAULT_LONGITUD);

        // Get all the incidenteList where longitud is greater than or equal to UPDATED_LONGITUD
        defaultIncidenteShouldNotBeFound("longitud.greaterThanOrEqual=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLongitudIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where longitud is less than or equal to DEFAULT_LONGITUD
        defaultIncidenteShouldBeFound("longitud.lessThanOrEqual=" + DEFAULT_LONGITUD);

        // Get all the incidenteList where longitud is less than or equal to SMALLER_LONGITUD
        defaultIncidenteShouldNotBeFound("longitud.lessThanOrEqual=" + SMALLER_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLongitudIsLessThanSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where longitud is less than DEFAULT_LONGITUD
        defaultIncidenteShouldNotBeFound("longitud.lessThan=" + DEFAULT_LONGITUD);

        // Get all the incidenteList where longitud is less than UPDATED_LONGITUD
        defaultIncidenteShouldBeFound("longitud.lessThan=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllIncidentesByLongitudIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where longitud is greater than DEFAULT_LONGITUD
        defaultIncidenteShouldNotBeFound("longitud.greaterThan=" + DEFAULT_LONGITUD);

        // Get all the incidenteList where longitud is greater than SMALLER_LONGITUD
        defaultIncidenteShouldBeFound("longitud.greaterThan=" + SMALLER_LONGITUD);
    }


    @Test
    @Transactional
    public void getAllIncidentesByFechaResolucionIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaResolucion equals to DEFAULT_FECHA_RESOLUCION
        defaultIncidenteShouldBeFound("fechaResolucion.equals=" + DEFAULT_FECHA_RESOLUCION);

        // Get all the incidenteList where fechaResolucion equals to UPDATED_FECHA_RESOLUCION
        defaultIncidenteShouldNotBeFound("fechaResolucion.equals=" + UPDATED_FECHA_RESOLUCION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaResolucionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaResolucion not equals to DEFAULT_FECHA_RESOLUCION
        defaultIncidenteShouldNotBeFound("fechaResolucion.notEquals=" + DEFAULT_FECHA_RESOLUCION);

        // Get all the incidenteList where fechaResolucion not equals to UPDATED_FECHA_RESOLUCION
        defaultIncidenteShouldBeFound("fechaResolucion.notEquals=" + UPDATED_FECHA_RESOLUCION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaResolucionIsInShouldWork() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaResolucion in DEFAULT_FECHA_RESOLUCION or UPDATED_FECHA_RESOLUCION
        defaultIncidenteShouldBeFound("fechaResolucion.in=" + DEFAULT_FECHA_RESOLUCION + "," + UPDATED_FECHA_RESOLUCION);

        // Get all the incidenteList where fechaResolucion equals to UPDATED_FECHA_RESOLUCION
        defaultIncidenteShouldNotBeFound("fechaResolucion.in=" + UPDATED_FECHA_RESOLUCION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaResolucionIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaResolucion is not null
        defaultIncidenteShouldBeFound("fechaResolucion.specified=true");

        // Get all the incidenteList where fechaResolucion is null
        defaultIncidenteShouldNotBeFound("fechaResolucion.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaResolucionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaResolucion is greater than or equal to DEFAULT_FECHA_RESOLUCION
        defaultIncidenteShouldBeFound("fechaResolucion.greaterThanOrEqual=" + DEFAULT_FECHA_RESOLUCION);

        // Get all the incidenteList where fechaResolucion is greater than or equal to UPDATED_FECHA_RESOLUCION
        defaultIncidenteShouldNotBeFound("fechaResolucion.greaterThanOrEqual=" + UPDATED_FECHA_RESOLUCION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaResolucionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaResolucion is less than or equal to DEFAULT_FECHA_RESOLUCION
        defaultIncidenteShouldBeFound("fechaResolucion.lessThanOrEqual=" + DEFAULT_FECHA_RESOLUCION);

        // Get all the incidenteList where fechaResolucion is less than or equal to SMALLER_FECHA_RESOLUCION
        defaultIncidenteShouldNotBeFound("fechaResolucion.lessThanOrEqual=" + SMALLER_FECHA_RESOLUCION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaResolucionIsLessThanSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaResolucion is less than DEFAULT_FECHA_RESOLUCION
        defaultIncidenteShouldNotBeFound("fechaResolucion.lessThan=" + DEFAULT_FECHA_RESOLUCION);

        // Get all the incidenteList where fechaResolucion is less than UPDATED_FECHA_RESOLUCION
        defaultIncidenteShouldBeFound("fechaResolucion.lessThan=" + UPDATED_FECHA_RESOLUCION);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaResolucionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaResolucion is greater than DEFAULT_FECHA_RESOLUCION
        defaultIncidenteShouldNotBeFound("fechaResolucion.greaterThan=" + DEFAULT_FECHA_RESOLUCION);

        // Get all the incidenteList where fechaResolucion is greater than SMALLER_FECHA_RESOLUCION
        defaultIncidenteShouldBeFound("fechaResolucion.greaterThan=" + SMALLER_FECHA_RESOLUCION);
    }


    @Test
    @Transactional
    public void getAllIncidentesByFechaCierreIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaCierre equals to DEFAULT_FECHA_CIERRE
        defaultIncidenteShouldBeFound("fechaCierre.equals=" + DEFAULT_FECHA_CIERRE);

        // Get all the incidenteList where fechaCierre equals to UPDATED_FECHA_CIERRE
        defaultIncidenteShouldNotBeFound("fechaCierre.equals=" + UPDATED_FECHA_CIERRE);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaCierreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaCierre not equals to DEFAULT_FECHA_CIERRE
        defaultIncidenteShouldNotBeFound("fechaCierre.notEquals=" + DEFAULT_FECHA_CIERRE);

        // Get all the incidenteList where fechaCierre not equals to UPDATED_FECHA_CIERRE
        defaultIncidenteShouldBeFound("fechaCierre.notEquals=" + UPDATED_FECHA_CIERRE);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaCierreIsInShouldWork() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaCierre in DEFAULT_FECHA_CIERRE or UPDATED_FECHA_CIERRE
        defaultIncidenteShouldBeFound("fechaCierre.in=" + DEFAULT_FECHA_CIERRE + "," + UPDATED_FECHA_CIERRE);

        // Get all the incidenteList where fechaCierre equals to UPDATED_FECHA_CIERRE
        defaultIncidenteShouldNotBeFound("fechaCierre.in=" + UPDATED_FECHA_CIERRE);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaCierreIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaCierre is not null
        defaultIncidenteShouldBeFound("fechaCierre.specified=true");

        // Get all the incidenteList where fechaCierre is null
        defaultIncidenteShouldNotBeFound("fechaCierre.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaCierreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaCierre is greater than or equal to DEFAULT_FECHA_CIERRE
        defaultIncidenteShouldBeFound("fechaCierre.greaterThanOrEqual=" + DEFAULT_FECHA_CIERRE);

        // Get all the incidenteList where fechaCierre is greater than or equal to UPDATED_FECHA_CIERRE
        defaultIncidenteShouldNotBeFound("fechaCierre.greaterThanOrEqual=" + UPDATED_FECHA_CIERRE);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaCierreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaCierre is less than or equal to DEFAULT_FECHA_CIERRE
        defaultIncidenteShouldBeFound("fechaCierre.lessThanOrEqual=" + DEFAULT_FECHA_CIERRE);

        // Get all the incidenteList where fechaCierre is less than or equal to SMALLER_FECHA_CIERRE
        defaultIncidenteShouldNotBeFound("fechaCierre.lessThanOrEqual=" + SMALLER_FECHA_CIERRE);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaCierreIsLessThanSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaCierre is less than DEFAULT_FECHA_CIERRE
        defaultIncidenteShouldNotBeFound("fechaCierre.lessThan=" + DEFAULT_FECHA_CIERRE);

        // Get all the incidenteList where fechaCierre is less than UPDATED_FECHA_CIERRE
        defaultIncidenteShouldBeFound("fechaCierre.lessThan=" + UPDATED_FECHA_CIERRE);
    }

    @Test
    @Transactional
    public void getAllIncidentesByFechaCierreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where fechaCierre is greater than DEFAULT_FECHA_CIERRE
        defaultIncidenteShouldNotBeFound("fechaCierre.greaterThan=" + DEFAULT_FECHA_CIERRE);

        // Get all the incidenteList where fechaCierre is greater than SMALLER_FECHA_CIERRE
        defaultIncidenteShouldBeFound("fechaCierre.greaterThan=" + SMALLER_FECHA_CIERRE);
    }


    @Test
    @Transactional
    public void getAllIncidentesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where email equals to DEFAULT_EMAIL
        defaultIncidenteShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the incidenteList where email equals to UPDATED_EMAIL
        defaultIncidenteShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllIncidentesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where email not equals to DEFAULT_EMAIL
        defaultIncidenteShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the incidenteList where email not equals to UPDATED_EMAIL
        defaultIncidenteShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllIncidentesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultIncidenteShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the incidenteList where email equals to UPDATED_EMAIL
        defaultIncidenteShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllIncidentesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where email is not null
        defaultIncidenteShouldBeFound("email.specified=true");

        // Get all the incidenteList where email is null
        defaultIncidenteShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllIncidentesByEmailContainsSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where email contains DEFAULT_EMAIL
        defaultIncidenteShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the incidenteList where email contains UPDATED_EMAIL
        defaultIncidenteShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllIncidentesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList where email does not contain DEFAULT_EMAIL
        defaultIncidenteShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the incidenteList where email does not contain UPDATED_EMAIL
        defaultIncidenteShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllIncidentesByComentariosIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);
        Comentario comentarios = ComentarioResourceIT.createEntity(em);
        em.persist(comentarios);
        em.flush();
        incidente.addComentarios(comentarios);
        incidenteRepository.saveAndFlush(incidente);
        Long comentariosId = comentarios.getId();

        // Get all the incidenteList where comentarios equals to comentariosId
        defaultIncidenteShouldBeFound("comentariosId.equals=" + comentariosId);

        // Get all the incidenteList where comentarios equals to comentariosId + 1
        defaultIncidenteShouldNotBeFound("comentariosId.equals=" + (comentariosId + 1));
    }


    @Test
    @Transactional
    public void getAllIncidentesByHistorialIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);
        HistorialEstado historial = HistorialEstadoResourceIT.createEntity(em);
        em.persist(historial);
        em.flush();
        incidente.addHistorial(historial);
        incidenteRepository.saveAndFlush(incidente);
        Long historialId = historial.getId();

        // Get all the incidenteList where historial equals to historialId
        defaultIncidenteShouldBeFound("historialId.equals=" + historialId);

        // Get all the incidenteList where historial equals to historialId + 1
        defaultIncidenteShouldNotBeFound("historialId.equals=" + (historialId + 1));
    }


    @Test
    @Transactional
    public void getAllIncidentesByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);
        Categoria categoria = CategoriaResourceIT.createEntity(em);
        em.persist(categoria);
        em.flush();
        incidente.setCategoria(categoria);
        incidenteRepository.saveAndFlush(incidente);
        Long categoriaId = categoria.getId();

        // Get all the incidenteList where categoria equals to categoriaId
        defaultIncidenteShouldBeFound("categoriaId.equals=" + categoriaId);

        // Get all the incidenteList where categoria equals to categoriaId + 1
        defaultIncidenteShouldNotBeFound("categoriaId.equals=" + (categoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllIncidentesBySubcategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);
        SubCategoria subcategoria = SubCategoriaResourceIT.createEntity(em);
        em.persist(subcategoria);
        em.flush();
        incidente.setSubcategoria(subcategoria);
        incidenteRepository.saveAndFlush(incidente);
        Long subcategoriaId = subcategoria.getId();

        // Get all the incidenteList where subcategoria equals to subcategoriaId
        defaultIncidenteShouldBeFound("subcategoriaId.equals=" + subcategoriaId);

        // Get all the incidenteList where subcategoria equals to subcategoriaId + 1
        defaultIncidenteShouldNotBeFound("subcategoriaId.equals=" + (subcategoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllIncidentesByOperadorIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);
        User operador = UserResourceIT.createEntity(em);
        em.persist(operador);
        em.flush();
        incidente.setOperador(operador);
        incidenteRepository.saveAndFlush(incidente);
        Long operadorId = operador.getId();

        // Get all the incidenteList where operador equals to operadorId
        defaultIncidenteShouldBeFound("operadorId.equals=" + operadorId);

        // Get all the incidenteList where operador equals to operadorId + 1
        defaultIncidenteShouldNotBeFound("operadorId.equals=" + (operadorId + 1));
    }


    @Test
    @Transactional
    public void getAllIncidentesByResponsableIsEqualToSomething() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);
        Responsable responsable = ResponsableResourceIT.createEntity(em);
        em.persist(responsable);
        em.flush();
        incidente.setResponsable(responsable);
        incidenteRepository.saveAndFlush(incidente);
        Long responsableId = responsable.getId();

        // Get all the incidenteList where responsable equals to responsableId
        defaultIncidenteShouldBeFound("responsableId.equals=" + responsableId);

        // Get all the incidenteList where responsable equals to responsableId + 1
        defaultIncidenteShouldNotBeFound("responsableId.equals=" + (responsableId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIncidenteShouldBeFound(String filter) throws Exception {
        restIncidenteMockMvc.perform(get("/api/incidentes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].cuerpo").value(hasItem(DEFAULT_CUERPO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].localizacion").value(hasItem(DEFAULT_LOCALIZACION)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaResolucion").value(hasItem(DEFAULT_FECHA_RESOLUCION.toString())))
            .andExpect(jsonPath("$.[*].fechaCierre").value(hasItem(DEFAULT_FECHA_CIERRE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restIncidenteMockMvc.perform(get("/api/incidentes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIncidenteShouldNotBeFound(String filter) throws Exception {
        restIncidenteMockMvc.perform(get("/api/incidentes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIncidenteMockMvc.perform(get("/api/incidentes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingIncidente() throws Exception {
        // Get the incidente
        restIncidenteMockMvc.perform(get("/api/incidentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncidente() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        int databaseSizeBeforeUpdate = incidenteRepository.findAll().size();

        // Update the incidente
        Incidente updatedIncidente = incidenteRepository.findById(incidente.getId()).get();
        // Disconnect from session so that the updates on updatedIncidente are not directly saved in db
        em.detach(updatedIncidente);
        updatedIncidente
            .fecha(UPDATED_FECHA)
            .cuerpo(UPDATED_CUERPO)
            .estado(UPDATED_ESTADO)
            .localizacion(UPDATED_LOCALIZACION)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .fechaResolucion(UPDATED_FECHA_RESOLUCION)
            .fechaCierre(UPDATED_FECHA_CIERRE)
            .email(UPDATED_EMAIL);
        IncidenteDTO incidenteDTO = incidenteMapper.toDto(updatedIncidente);

        restIncidenteMockMvc.perform(put("/api/incidentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incidenteDTO)))
            .andExpect(status().isOk());

        // Validate the Incidente in the database
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeUpdate);
        Incidente testIncidente = incidenteList.get(incidenteList.size() - 1);
        assertThat(testIncidente.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testIncidente.getCuerpo()).isEqualTo(UPDATED_CUERPO);
        assertThat(testIncidente.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testIncidente.getLocalizacion()).isEqualTo(UPDATED_LOCALIZACION);
        assertThat(testIncidente.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testIncidente.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testIncidente.getFechaResolucion()).isEqualTo(UPDATED_FECHA_RESOLUCION);
        assertThat(testIncidente.getFechaCierre()).isEqualTo(UPDATED_FECHA_CIERRE);
        assertThat(testIncidente.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingIncidente() throws Exception {
        int databaseSizeBeforeUpdate = incidenteRepository.findAll().size();

        // Create the Incidente
        IncidenteDTO incidenteDTO = incidenteMapper.toDto(incidente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidenteMockMvc.perform(put("/api/incidentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incidenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incidente in the database
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncidente() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        int databaseSizeBeforeDelete = incidenteRepository.findAll().size();

        // Delete the incidente
        restIncidenteMockMvc.perform(delete("/api/incidentes/{id}", incidente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
