package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.DiuconApp;
import ar.com.guanaco.diucon.domain.Responsable;
import ar.com.guanaco.diucon.domain.User;
import ar.com.guanaco.diucon.domain.SubCategoria;
import ar.com.guanaco.diucon.repository.ResponsableRepository;
import ar.com.guanaco.diucon.service.ResponsableService;
import ar.com.guanaco.diucon.service.dto.ResponsableDTO;
import ar.com.guanaco.diucon.service.mapper.ResponsableMapper;
import ar.com.guanaco.diucon.service.dto.ResponsableCriteria;
import ar.com.guanaco.diucon.service.ResponsableQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResponsableResource} REST controller.
 */
@SpringBootTest(classes = DiuconApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResponsableResourceIT {

    private static final String DEFAULT_NOMBRE_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMPLETO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_NACIMIENTO = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_DNI = 0L;
    private static final Long UPDATED_DNI = 1L;
    private static final Long SMALLER_DNI = 0L - 1L;

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUD = 1D;
    private static final Double UPDATED_LATITUD = 2D;
    private static final Double SMALLER_LATITUD = 1D - 1D;

    private static final Double DEFAULT_LONGITUD = 1D;
    private static final Double UPDATED_LONGITUD = 2D;
    private static final Double SMALLER_LONGITUD = 1D - 1D;

    private static final Boolean DEFAULT_PROFESIONAL = false;
    private static final Boolean UPDATED_PROFESIONAL = true;

    private static final Boolean DEFAULT_ESTUDIANTE = false;
    private static final Boolean UPDATED_ESTUDIANTE = true;

    private static final Boolean DEFAULT_TRABAJADOR = false;
    private static final Boolean UPDATED_TRABAJADOR = true;

    @Autowired
    private ResponsableRepository responsableRepository;

    @Mock
    private ResponsableRepository responsableRepositoryMock;

    @Autowired
    private ResponsableMapper responsableMapper;

    @Mock
    private ResponsableService responsableServiceMock;

    @Autowired
    private ResponsableService responsableService;

    @Autowired
    private ResponsableQueryService responsableQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsableMockMvc;

    private Responsable responsable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsable createEntity(EntityManager em) {
        Responsable responsable = new Responsable()
            .nombreCompleto(DEFAULT_NOMBRE_COMPLETO)
            .telefono(DEFAULT_TELEFONO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .dni(DEFAULT_DNI)
            .domicilio(DEFAULT_DOMICILIO)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .profesional(DEFAULT_PROFESIONAL)
            .estudiante(DEFAULT_ESTUDIANTE)
            .trabajador(DEFAULT_TRABAJADOR);
        return responsable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsable createUpdatedEntity(EntityManager em) {
        Responsable responsable = new Responsable()
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO)
            .telefono(UPDATED_TELEFONO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .dni(UPDATED_DNI)
            .domicilio(UPDATED_DOMICILIO)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .profesional(UPDATED_PROFESIONAL)
            .estudiante(UPDATED_ESTUDIANTE)
            .trabajador(UPDATED_TRABAJADOR);
        return responsable;
    }

    @BeforeEach
    public void initTest() {
        responsable = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsable() throws Exception {
        int databaseSizeBeforeCreate = responsableRepository.findAll().size();

        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);
        restResponsableMockMvc.perform(post("/api/responsables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsableDTO)))
            .andExpect(status().isCreated());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeCreate + 1);
        Responsable testResponsable = responsableList.get(responsableList.size() - 1);
        assertThat(testResponsable.getNombreCompleto()).isEqualTo(DEFAULT_NOMBRE_COMPLETO);
        assertThat(testResponsable.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testResponsable.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testResponsable.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testResponsable.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testResponsable.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testResponsable.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testResponsable.isProfesional()).isEqualTo(DEFAULT_PROFESIONAL);
        assertThat(testResponsable.isEstudiante()).isEqualTo(DEFAULT_ESTUDIANTE);
        assertThat(testResponsable.isTrabajador()).isEqualTo(DEFAULT_TRABAJADOR);
    }

    @Test
    @Transactional
    public void createResponsableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsableRepository.findAll().size();

        // Create the Responsable with an existing ID
        responsable.setId(1L);
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsableMockMvc.perform(post("/api/responsables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreCompletoIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setNombreCompleto(null);

        // Create the Responsable, which fails.
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        restResponsableMockMvc.perform(post("/api/responsables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsableDTO)))
            .andExpect(status().isBadRequest());

        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setTelefono(null);

        // Create the Responsable, which fails.
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        restResponsableMockMvc.perform(post("/api/responsables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsableDTO)))
            .andExpect(status().isBadRequest());

        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilioIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setDomicilio(null);

        // Create the Responsable, which fails.
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        restResponsableMockMvc.perform(post("/api/responsables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsableDTO)))
            .andExpect(status().isBadRequest());

        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsables() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList
        restResponsableMockMvc.perform(get("/api/responsables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsable.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.intValue())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].profesional").value(hasItem(DEFAULT_PROFESIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].estudiante").value(hasItem(DEFAULT_ESTUDIANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].trabajador").value(hasItem(DEFAULT_TRABAJADOR.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllResponsablesWithEagerRelationshipsIsEnabled() throws Exception {
        when(responsableServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsableMockMvc.perform(get("/api/responsables?eagerload=true"))
            .andExpect(status().isOk());

        verify(responsableServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllResponsablesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(responsableServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsableMockMvc.perform(get("/api/responsables?eagerload=true"))
            .andExpect(status().isOk());

        verify(responsableServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", responsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsable.getId().intValue()))
            .andExpect(jsonPath("$.nombreCompleto").value(DEFAULT_NOMBRE_COMPLETO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI.intValue()))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.doubleValue()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.doubleValue()))
            .andExpect(jsonPath("$.profesional").value(DEFAULT_PROFESIONAL.booleanValue()))
            .andExpect(jsonPath("$.estudiante").value(DEFAULT_ESTUDIANTE.booleanValue()))
            .andExpect(jsonPath("$.trabajador").value(DEFAULT_TRABAJADOR.booleanValue()));
    }


    @Test
    @Transactional
    public void getResponsablesByIdFiltering() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        Long id = responsable.getId();

        defaultResponsableShouldBeFound("id.equals=" + id);
        defaultResponsableShouldNotBeFound("id.notEquals=" + id);

        defaultResponsableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResponsableShouldNotBeFound("id.greaterThan=" + id);

        defaultResponsableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResponsableShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllResponsablesByNombreCompletoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where nombreCompleto equals to DEFAULT_NOMBRE_COMPLETO
        defaultResponsableShouldBeFound("nombreCompleto.equals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the responsableList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultResponsableShouldNotBeFound("nombreCompleto.equals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByNombreCompletoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where nombreCompleto not equals to DEFAULT_NOMBRE_COMPLETO
        defaultResponsableShouldNotBeFound("nombreCompleto.notEquals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the responsableList where nombreCompleto not equals to UPDATED_NOMBRE_COMPLETO
        defaultResponsableShouldBeFound("nombreCompleto.notEquals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByNombreCompletoIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where nombreCompleto in DEFAULT_NOMBRE_COMPLETO or UPDATED_NOMBRE_COMPLETO
        defaultResponsableShouldBeFound("nombreCompleto.in=" + DEFAULT_NOMBRE_COMPLETO + "," + UPDATED_NOMBRE_COMPLETO);

        // Get all the responsableList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultResponsableShouldNotBeFound("nombreCompleto.in=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByNombreCompletoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where nombreCompleto is not null
        defaultResponsableShouldBeFound("nombreCompleto.specified=true");

        // Get all the responsableList where nombreCompleto is null
        defaultResponsableShouldNotBeFound("nombreCompleto.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsablesByNombreCompletoContainsSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where nombreCompleto contains DEFAULT_NOMBRE_COMPLETO
        defaultResponsableShouldBeFound("nombreCompleto.contains=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the responsableList where nombreCompleto contains UPDATED_NOMBRE_COMPLETO
        defaultResponsableShouldNotBeFound("nombreCompleto.contains=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByNombreCompletoNotContainsSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where nombreCompleto does not contain DEFAULT_NOMBRE_COMPLETO
        defaultResponsableShouldNotBeFound("nombreCompleto.doesNotContain=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the responsableList where nombreCompleto does not contain UPDATED_NOMBRE_COMPLETO
        defaultResponsableShouldBeFound("nombreCompleto.doesNotContain=" + UPDATED_NOMBRE_COMPLETO);
    }


    @Test
    @Transactional
    public void getAllResponsablesByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where telefono equals to DEFAULT_TELEFONO
        defaultResponsableShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the responsableList where telefono equals to UPDATED_TELEFONO
        defaultResponsableShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where telefono not equals to DEFAULT_TELEFONO
        defaultResponsableShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the responsableList where telefono not equals to UPDATED_TELEFONO
        defaultResponsableShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultResponsableShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the responsableList where telefono equals to UPDATED_TELEFONO
        defaultResponsableShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where telefono is not null
        defaultResponsableShouldBeFound("telefono.specified=true");

        // Get all the responsableList where telefono is null
        defaultResponsableShouldNotBeFound("telefono.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsablesByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where telefono contains DEFAULT_TELEFONO
        defaultResponsableShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the responsableList where telefono contains UPDATED_TELEFONO
        defaultResponsableShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where telefono does not contain DEFAULT_TELEFONO
        defaultResponsableShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the responsableList where telefono does not contain UPDATED_TELEFONO
        defaultResponsableShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }


    @Test
    @Transactional
    public void getAllResponsablesByFechaNacimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where fechaNacimiento equals to DEFAULT_FECHA_NACIMIENTO
        defaultResponsableShouldBeFound("fechaNacimiento.equals=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the responsableList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultResponsableShouldNotBeFound("fechaNacimiento.equals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByFechaNacimientoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where fechaNacimiento not equals to DEFAULT_FECHA_NACIMIENTO
        defaultResponsableShouldNotBeFound("fechaNacimiento.notEquals=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the responsableList where fechaNacimiento not equals to UPDATED_FECHA_NACIMIENTO
        defaultResponsableShouldBeFound("fechaNacimiento.notEquals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByFechaNacimientoIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where fechaNacimiento in DEFAULT_FECHA_NACIMIENTO or UPDATED_FECHA_NACIMIENTO
        defaultResponsableShouldBeFound("fechaNacimiento.in=" + DEFAULT_FECHA_NACIMIENTO + "," + UPDATED_FECHA_NACIMIENTO);

        // Get all the responsableList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultResponsableShouldNotBeFound("fechaNacimiento.in=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByFechaNacimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where fechaNacimiento is not null
        defaultResponsableShouldBeFound("fechaNacimiento.specified=true");

        // Get all the responsableList where fechaNacimiento is null
        defaultResponsableShouldNotBeFound("fechaNacimiento.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsablesByFechaNacimientoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where fechaNacimiento is greater than or equal to DEFAULT_FECHA_NACIMIENTO
        defaultResponsableShouldBeFound("fechaNacimiento.greaterThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the responsableList where fechaNacimiento is greater than or equal to UPDATED_FECHA_NACIMIENTO
        defaultResponsableShouldNotBeFound("fechaNacimiento.greaterThanOrEqual=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByFechaNacimientoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where fechaNacimiento is less than or equal to DEFAULT_FECHA_NACIMIENTO
        defaultResponsableShouldBeFound("fechaNacimiento.lessThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the responsableList where fechaNacimiento is less than or equal to SMALLER_FECHA_NACIMIENTO
        defaultResponsableShouldNotBeFound("fechaNacimiento.lessThanOrEqual=" + SMALLER_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByFechaNacimientoIsLessThanSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where fechaNacimiento is less than DEFAULT_FECHA_NACIMIENTO
        defaultResponsableShouldNotBeFound("fechaNacimiento.lessThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the responsableList where fechaNacimiento is less than UPDATED_FECHA_NACIMIENTO
        defaultResponsableShouldBeFound("fechaNacimiento.lessThan=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByFechaNacimientoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where fechaNacimiento is greater than DEFAULT_FECHA_NACIMIENTO
        defaultResponsableShouldNotBeFound("fechaNacimiento.greaterThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the responsableList where fechaNacimiento is greater than SMALLER_FECHA_NACIMIENTO
        defaultResponsableShouldBeFound("fechaNacimiento.greaterThan=" + SMALLER_FECHA_NACIMIENTO);
    }


    @Test
    @Transactional
    public void getAllResponsablesByDniIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where dni equals to DEFAULT_DNI
        defaultResponsableShouldBeFound("dni.equals=" + DEFAULT_DNI);

        // Get all the responsableList where dni equals to UPDATED_DNI
        defaultResponsableShouldNotBeFound("dni.equals=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    public void getAllResponsablesByDniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where dni not equals to DEFAULT_DNI
        defaultResponsableShouldNotBeFound("dni.notEquals=" + DEFAULT_DNI);

        // Get all the responsableList where dni not equals to UPDATED_DNI
        defaultResponsableShouldBeFound("dni.notEquals=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    public void getAllResponsablesByDniIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where dni in DEFAULT_DNI or UPDATED_DNI
        defaultResponsableShouldBeFound("dni.in=" + DEFAULT_DNI + "," + UPDATED_DNI);

        // Get all the responsableList where dni equals to UPDATED_DNI
        defaultResponsableShouldNotBeFound("dni.in=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    public void getAllResponsablesByDniIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where dni is not null
        defaultResponsableShouldBeFound("dni.specified=true");

        // Get all the responsableList where dni is null
        defaultResponsableShouldNotBeFound("dni.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsablesByDniIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where dni is greater than or equal to DEFAULT_DNI
        defaultResponsableShouldBeFound("dni.greaterThanOrEqual=" + DEFAULT_DNI);

        // Get all the responsableList where dni is greater than or equal to (DEFAULT_DNI + 1)
        defaultResponsableShouldNotBeFound("dni.greaterThanOrEqual=" + (DEFAULT_DNI + 1));
    }

    @Test
    @Transactional
    public void getAllResponsablesByDniIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where dni is less than or equal to DEFAULT_DNI
        defaultResponsableShouldBeFound("dni.lessThanOrEqual=" + DEFAULT_DNI);

        // Get all the responsableList where dni is less than or equal to SMALLER_DNI
        defaultResponsableShouldNotBeFound("dni.lessThanOrEqual=" + SMALLER_DNI);
    }

    @Test
    @Transactional
    public void getAllResponsablesByDniIsLessThanSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where dni is less than DEFAULT_DNI
        defaultResponsableShouldNotBeFound("dni.lessThan=" + DEFAULT_DNI);

        // Get all the responsableList where dni is less than (DEFAULT_DNI + 1)
        defaultResponsableShouldBeFound("dni.lessThan=" + (DEFAULT_DNI + 1));
    }

    @Test
    @Transactional
    public void getAllResponsablesByDniIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where dni is greater than DEFAULT_DNI
        defaultResponsableShouldNotBeFound("dni.greaterThan=" + DEFAULT_DNI);

        // Get all the responsableList where dni is greater than SMALLER_DNI
        defaultResponsableShouldBeFound("dni.greaterThan=" + SMALLER_DNI);
    }


    @Test
    @Transactional
    public void getAllResponsablesByDomicilioIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where domicilio equals to DEFAULT_DOMICILIO
        defaultResponsableShouldBeFound("domicilio.equals=" + DEFAULT_DOMICILIO);

        // Get all the responsableList where domicilio equals to UPDATED_DOMICILIO
        defaultResponsableShouldNotBeFound("domicilio.equals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByDomicilioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where domicilio not equals to DEFAULT_DOMICILIO
        defaultResponsableShouldNotBeFound("domicilio.notEquals=" + DEFAULT_DOMICILIO);

        // Get all the responsableList where domicilio not equals to UPDATED_DOMICILIO
        defaultResponsableShouldBeFound("domicilio.notEquals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByDomicilioIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where domicilio in DEFAULT_DOMICILIO or UPDATED_DOMICILIO
        defaultResponsableShouldBeFound("domicilio.in=" + DEFAULT_DOMICILIO + "," + UPDATED_DOMICILIO);

        // Get all the responsableList where domicilio equals to UPDATED_DOMICILIO
        defaultResponsableShouldNotBeFound("domicilio.in=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByDomicilioIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where domicilio is not null
        defaultResponsableShouldBeFound("domicilio.specified=true");

        // Get all the responsableList where domicilio is null
        defaultResponsableShouldNotBeFound("domicilio.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsablesByDomicilioContainsSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where domicilio contains DEFAULT_DOMICILIO
        defaultResponsableShouldBeFound("domicilio.contains=" + DEFAULT_DOMICILIO);

        // Get all the responsableList where domicilio contains UPDATED_DOMICILIO
        defaultResponsableShouldNotBeFound("domicilio.contains=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllResponsablesByDomicilioNotContainsSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where domicilio does not contain DEFAULT_DOMICILIO
        defaultResponsableShouldNotBeFound("domicilio.doesNotContain=" + DEFAULT_DOMICILIO);

        // Get all the responsableList where domicilio does not contain UPDATED_DOMICILIO
        defaultResponsableShouldBeFound("domicilio.doesNotContain=" + UPDATED_DOMICILIO);
    }


    @Test
    @Transactional
    public void getAllResponsablesByLatitudIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where latitud equals to DEFAULT_LATITUD
        defaultResponsableShouldBeFound("latitud.equals=" + DEFAULT_LATITUD);

        // Get all the responsableList where latitud equals to UPDATED_LATITUD
        defaultResponsableShouldNotBeFound("latitud.equals=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLatitudIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where latitud not equals to DEFAULT_LATITUD
        defaultResponsableShouldNotBeFound("latitud.notEquals=" + DEFAULT_LATITUD);

        // Get all the responsableList where latitud not equals to UPDATED_LATITUD
        defaultResponsableShouldBeFound("latitud.notEquals=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLatitudIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where latitud in DEFAULT_LATITUD or UPDATED_LATITUD
        defaultResponsableShouldBeFound("latitud.in=" + DEFAULT_LATITUD + "," + UPDATED_LATITUD);

        // Get all the responsableList where latitud equals to UPDATED_LATITUD
        defaultResponsableShouldNotBeFound("latitud.in=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLatitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where latitud is not null
        defaultResponsableShouldBeFound("latitud.specified=true");

        // Get all the responsableList where latitud is null
        defaultResponsableShouldNotBeFound("latitud.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsablesByLatitudIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where latitud is greater than or equal to DEFAULT_LATITUD
        defaultResponsableShouldBeFound("latitud.greaterThanOrEqual=" + DEFAULT_LATITUD);

        // Get all the responsableList where latitud is greater than or equal to UPDATED_LATITUD
        defaultResponsableShouldNotBeFound("latitud.greaterThanOrEqual=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLatitudIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where latitud is less than or equal to DEFAULT_LATITUD
        defaultResponsableShouldBeFound("latitud.lessThanOrEqual=" + DEFAULT_LATITUD);

        // Get all the responsableList where latitud is less than or equal to SMALLER_LATITUD
        defaultResponsableShouldNotBeFound("latitud.lessThanOrEqual=" + SMALLER_LATITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLatitudIsLessThanSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where latitud is less than DEFAULT_LATITUD
        defaultResponsableShouldNotBeFound("latitud.lessThan=" + DEFAULT_LATITUD);

        // Get all the responsableList where latitud is less than UPDATED_LATITUD
        defaultResponsableShouldBeFound("latitud.lessThan=" + UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLatitudIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where latitud is greater than DEFAULT_LATITUD
        defaultResponsableShouldNotBeFound("latitud.greaterThan=" + DEFAULT_LATITUD);

        // Get all the responsableList where latitud is greater than SMALLER_LATITUD
        defaultResponsableShouldBeFound("latitud.greaterThan=" + SMALLER_LATITUD);
    }


    @Test
    @Transactional
    public void getAllResponsablesByLongitudIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where longitud equals to DEFAULT_LONGITUD
        defaultResponsableShouldBeFound("longitud.equals=" + DEFAULT_LONGITUD);

        // Get all the responsableList where longitud equals to UPDATED_LONGITUD
        defaultResponsableShouldNotBeFound("longitud.equals=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLongitudIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where longitud not equals to DEFAULT_LONGITUD
        defaultResponsableShouldNotBeFound("longitud.notEquals=" + DEFAULT_LONGITUD);

        // Get all the responsableList where longitud not equals to UPDATED_LONGITUD
        defaultResponsableShouldBeFound("longitud.notEquals=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLongitudIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where longitud in DEFAULT_LONGITUD or UPDATED_LONGITUD
        defaultResponsableShouldBeFound("longitud.in=" + DEFAULT_LONGITUD + "," + UPDATED_LONGITUD);

        // Get all the responsableList where longitud equals to UPDATED_LONGITUD
        defaultResponsableShouldNotBeFound("longitud.in=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLongitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where longitud is not null
        defaultResponsableShouldBeFound("longitud.specified=true");

        // Get all the responsableList where longitud is null
        defaultResponsableShouldNotBeFound("longitud.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsablesByLongitudIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where longitud is greater than or equal to DEFAULT_LONGITUD
        defaultResponsableShouldBeFound("longitud.greaterThanOrEqual=" + DEFAULT_LONGITUD);

        // Get all the responsableList where longitud is greater than or equal to UPDATED_LONGITUD
        defaultResponsableShouldNotBeFound("longitud.greaterThanOrEqual=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLongitudIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where longitud is less than or equal to DEFAULT_LONGITUD
        defaultResponsableShouldBeFound("longitud.lessThanOrEqual=" + DEFAULT_LONGITUD);

        // Get all the responsableList where longitud is less than or equal to SMALLER_LONGITUD
        defaultResponsableShouldNotBeFound("longitud.lessThanOrEqual=" + SMALLER_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLongitudIsLessThanSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where longitud is less than DEFAULT_LONGITUD
        defaultResponsableShouldNotBeFound("longitud.lessThan=" + DEFAULT_LONGITUD);

        // Get all the responsableList where longitud is less than UPDATED_LONGITUD
        defaultResponsableShouldBeFound("longitud.lessThan=" + UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void getAllResponsablesByLongitudIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where longitud is greater than DEFAULT_LONGITUD
        defaultResponsableShouldNotBeFound("longitud.greaterThan=" + DEFAULT_LONGITUD);

        // Get all the responsableList where longitud is greater than SMALLER_LONGITUD
        defaultResponsableShouldBeFound("longitud.greaterThan=" + SMALLER_LONGITUD);
    }


    @Test
    @Transactional
    public void getAllResponsablesByProfesionalIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where profesional equals to DEFAULT_PROFESIONAL
        defaultResponsableShouldBeFound("profesional.equals=" + DEFAULT_PROFESIONAL);

        // Get all the responsableList where profesional equals to UPDATED_PROFESIONAL
        defaultResponsableShouldNotBeFound("profesional.equals=" + UPDATED_PROFESIONAL);
    }

    @Test
    @Transactional
    public void getAllResponsablesByProfesionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where profesional not equals to DEFAULT_PROFESIONAL
        defaultResponsableShouldNotBeFound("profesional.notEquals=" + DEFAULT_PROFESIONAL);

        // Get all the responsableList where profesional not equals to UPDATED_PROFESIONAL
        defaultResponsableShouldBeFound("profesional.notEquals=" + UPDATED_PROFESIONAL);
    }

    @Test
    @Transactional
    public void getAllResponsablesByProfesionalIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where profesional in DEFAULT_PROFESIONAL or UPDATED_PROFESIONAL
        defaultResponsableShouldBeFound("profesional.in=" + DEFAULT_PROFESIONAL + "," + UPDATED_PROFESIONAL);

        // Get all the responsableList where profesional equals to UPDATED_PROFESIONAL
        defaultResponsableShouldNotBeFound("profesional.in=" + UPDATED_PROFESIONAL);
    }

    @Test
    @Transactional
    public void getAllResponsablesByProfesionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where profesional is not null
        defaultResponsableShouldBeFound("profesional.specified=true");

        // Get all the responsableList where profesional is null
        defaultResponsableShouldNotBeFound("profesional.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsablesByEstudianteIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where estudiante equals to DEFAULT_ESTUDIANTE
        defaultResponsableShouldBeFound("estudiante.equals=" + DEFAULT_ESTUDIANTE);

        // Get all the responsableList where estudiante equals to UPDATED_ESTUDIANTE
        defaultResponsableShouldNotBeFound("estudiante.equals=" + UPDATED_ESTUDIANTE);
    }

    @Test
    @Transactional
    public void getAllResponsablesByEstudianteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where estudiante not equals to DEFAULT_ESTUDIANTE
        defaultResponsableShouldNotBeFound("estudiante.notEquals=" + DEFAULT_ESTUDIANTE);

        // Get all the responsableList where estudiante not equals to UPDATED_ESTUDIANTE
        defaultResponsableShouldBeFound("estudiante.notEquals=" + UPDATED_ESTUDIANTE);
    }

    @Test
    @Transactional
    public void getAllResponsablesByEstudianteIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where estudiante in DEFAULT_ESTUDIANTE or UPDATED_ESTUDIANTE
        defaultResponsableShouldBeFound("estudiante.in=" + DEFAULT_ESTUDIANTE + "," + UPDATED_ESTUDIANTE);

        // Get all the responsableList where estudiante equals to UPDATED_ESTUDIANTE
        defaultResponsableShouldNotBeFound("estudiante.in=" + UPDATED_ESTUDIANTE);
    }

    @Test
    @Transactional
    public void getAllResponsablesByEstudianteIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where estudiante is not null
        defaultResponsableShouldBeFound("estudiante.specified=true");

        // Get all the responsableList where estudiante is null
        defaultResponsableShouldNotBeFound("estudiante.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsablesByTrabajadorIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where trabajador equals to DEFAULT_TRABAJADOR
        defaultResponsableShouldBeFound("trabajador.equals=" + DEFAULT_TRABAJADOR);

        // Get all the responsableList where trabajador equals to UPDATED_TRABAJADOR
        defaultResponsableShouldNotBeFound("trabajador.equals=" + UPDATED_TRABAJADOR);
    }

    @Test
    @Transactional
    public void getAllResponsablesByTrabajadorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where trabajador not equals to DEFAULT_TRABAJADOR
        defaultResponsableShouldNotBeFound("trabajador.notEquals=" + DEFAULT_TRABAJADOR);

        // Get all the responsableList where trabajador not equals to UPDATED_TRABAJADOR
        defaultResponsableShouldBeFound("trabajador.notEquals=" + UPDATED_TRABAJADOR);
    }

    @Test
    @Transactional
    public void getAllResponsablesByTrabajadorIsInShouldWork() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where trabajador in DEFAULT_TRABAJADOR or UPDATED_TRABAJADOR
        defaultResponsableShouldBeFound("trabajador.in=" + DEFAULT_TRABAJADOR + "," + UPDATED_TRABAJADOR);

        // Get all the responsableList where trabajador equals to UPDATED_TRABAJADOR
        defaultResponsableShouldNotBeFound("trabajador.in=" + UPDATED_TRABAJADOR);
    }

    @Test
    @Transactional
    public void getAllResponsablesByTrabajadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList where trabajador is not null
        defaultResponsableShouldBeFound("trabajador.specified=true");

        // Get all the responsableList where trabajador is null
        defaultResponsableShouldNotBeFound("trabajador.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsablesByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);
        User usuario = UserResourceIT.createEntity(em);
        em.persist(usuario);
        em.flush();
        responsable.setUsuario(usuario);
        responsableRepository.saveAndFlush(responsable);
        Long usuarioId = usuario.getId();

        // Get all the responsableList where usuario equals to usuarioId
        defaultResponsableShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the responsableList where usuario equals to usuarioId + 1
        defaultResponsableShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllResponsablesBySubcategoriasIsEqualToSomething() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);
        SubCategoria subcategorias = SubCategoriaResourceIT.createEntity(em);
        em.persist(subcategorias);
        em.flush();
        responsable.addSubcategorias(subcategorias);
        responsableRepository.saveAndFlush(responsable);
        Long subcategoriasId = subcategorias.getId();

        // Get all the responsableList where subcategorias equals to subcategoriasId
        defaultResponsableShouldBeFound("subcategoriasId.equals=" + subcategoriasId);

        // Get all the responsableList where subcategorias equals to subcategoriasId + 1
        defaultResponsableShouldNotBeFound("subcategoriasId.equals=" + (subcategoriasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsableShouldBeFound(String filter) throws Exception {
        restResponsableMockMvc.perform(get("/api/responsables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsable.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.intValue())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].profesional").value(hasItem(DEFAULT_PROFESIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].estudiante").value(hasItem(DEFAULT_ESTUDIANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].trabajador").value(hasItem(DEFAULT_TRABAJADOR.booleanValue())));

        // Check, that the count call also returns 1
        restResponsableMockMvc.perform(get("/api/responsables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsableShouldNotBeFound(String filter) throws Exception {
        restResponsableMockMvc.perform(get("/api/responsables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsableMockMvc.perform(get("/api/responsables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResponsable() throws Exception {
        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Update the responsable
        Responsable updatedResponsable = responsableRepository.findById(responsable.getId()).get();
        // Disconnect from session so that the updates on updatedResponsable are not directly saved in db
        em.detach(updatedResponsable);
        updatedResponsable
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO)
            .telefono(UPDATED_TELEFONO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .dni(UPDATED_DNI)
            .domicilio(UPDATED_DOMICILIO)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .profesional(UPDATED_PROFESIONAL)
            .estudiante(UPDATED_ESTUDIANTE)
            .trabajador(UPDATED_TRABAJADOR);
        ResponsableDTO responsableDTO = responsableMapper.toDto(updatedResponsable);

        restResponsableMockMvc.perform(put("/api/responsables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsableDTO)))
            .andExpect(status().isOk());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
        Responsable testResponsable = responsableList.get(responsableList.size() - 1);
        assertThat(testResponsable.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
        assertThat(testResponsable.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testResponsable.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testResponsable.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testResponsable.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testResponsable.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testResponsable.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testResponsable.isProfesional()).isEqualTo(UPDATED_PROFESIONAL);
        assertThat(testResponsable.isEstudiante()).isEqualTo(UPDATED_ESTUDIANTE);
        assertThat(testResponsable.isTrabajador()).isEqualTo(UPDATED_TRABAJADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsable() throws Exception {
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsableMockMvc.perform(put("/api/responsables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        int databaseSizeBeforeDelete = responsableRepository.findAll().size();

        // Delete the responsable
        restResponsableMockMvc.perform(delete("/api/responsables/{id}", responsable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
