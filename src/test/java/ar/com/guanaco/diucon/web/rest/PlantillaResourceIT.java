package ar.com.guanaco.diucon.web.rest;

import ar.com.guanaco.diucon.DiuconApp;
import ar.com.guanaco.diucon.domain.Plantilla;
import ar.com.guanaco.diucon.domain.SubCategoria;
import ar.com.guanaco.diucon.repository.PlantillaRepository;
import ar.com.guanaco.diucon.service.PlantillaService;
import ar.com.guanaco.diucon.service.dto.PlantillaDTO;
import ar.com.guanaco.diucon.service.mapper.PlantillaMapper;
import ar.com.guanaco.diucon.service.dto.PlantillaCriteria;
import ar.com.guanaco.diucon.service.PlantillaQueryService;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlantillaResource} REST controller.
 */
@SpringBootTest(classes = DiuconApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlantillaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CUERPO = "AAAAAAAAAA";
    private static final String UPDATED_CUERPO = "BBBBBBBBBB";

    @Autowired
    private PlantillaRepository plantillaRepository;

    @Mock
    private PlantillaRepository plantillaRepositoryMock;

    @Autowired
    private PlantillaMapper plantillaMapper;

    @Mock
    private PlantillaService plantillaServiceMock;

    @Autowired
    private PlantillaService plantillaService;

    @Autowired
    private PlantillaQueryService plantillaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantillaMockMvc;

    private Plantilla plantilla;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plantilla createEntity(EntityManager em) {
        Plantilla plantilla = new Plantilla()
            .nombre(DEFAULT_NOMBRE)
            .cuerpo(DEFAULT_CUERPO);
        return plantilla;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plantilla createUpdatedEntity(EntityManager em) {
        Plantilla plantilla = new Plantilla()
            .nombre(UPDATED_NOMBRE)
            .cuerpo(UPDATED_CUERPO);
        return plantilla;
    }

    @BeforeEach
    public void initTest() {
        plantilla = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlantilla() throws Exception {
        int databaseSizeBeforeCreate = plantillaRepository.findAll().size();

        // Create the Plantilla
        PlantillaDTO plantillaDTO = plantillaMapper.toDto(plantilla);
        restPlantillaMockMvc.perform(post("/api/plantillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantillaDTO)))
            .andExpect(status().isCreated());

        // Validate the Plantilla in the database
        List<Plantilla> plantillaList = plantillaRepository.findAll();
        assertThat(plantillaList).hasSize(databaseSizeBeforeCreate + 1);
        Plantilla testPlantilla = plantillaList.get(plantillaList.size() - 1);
        assertThat(testPlantilla.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPlantilla.getCuerpo()).isEqualTo(DEFAULT_CUERPO);
    }

    @Test
    @Transactional
    public void createPlantillaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plantillaRepository.findAll().size();

        // Create the Plantilla with an existing ID
        plantilla.setId(1L);
        PlantillaDTO plantillaDTO = plantillaMapper.toDto(plantilla);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantillaMockMvc.perform(post("/api/plantillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantillaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plantilla in the database
        List<Plantilla> plantillaList = plantillaRepository.findAll();
        assertThat(plantillaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = plantillaRepository.findAll().size();
        // set the field null
        plantilla.setNombre(null);

        // Create the Plantilla, which fails.
        PlantillaDTO plantillaDTO = plantillaMapper.toDto(plantilla);

        restPlantillaMockMvc.perform(post("/api/plantillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantillaDTO)))
            .andExpect(status().isBadRequest());

        List<Plantilla> plantillaList = plantillaRepository.findAll();
        assertThat(plantillaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlantillas() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        // Get all the plantillaList
        restPlantillaMockMvc.perform(get("/api/plantillas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantilla.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].cuerpo").value(hasItem(DEFAULT_CUERPO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPlantillasWithEagerRelationshipsIsEnabled() throws Exception {
        when(plantillaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlantillaMockMvc.perform(get("/api/plantillas?eagerload=true"))
            .andExpect(status().isOk());

        verify(plantillaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPlantillasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(plantillaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlantillaMockMvc.perform(get("/api/plantillas?eagerload=true"))
            .andExpect(status().isOk());

        verify(plantillaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPlantilla() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        // Get the plantilla
        restPlantillaMockMvc.perform(get("/api/plantillas/{id}", plantilla.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plantilla.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.cuerpo").value(DEFAULT_CUERPO.toString()));
    }


    @Test
    @Transactional
    public void getPlantillasByIdFiltering() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        Long id = plantilla.getId();

        defaultPlantillaShouldBeFound("id.equals=" + id);
        defaultPlantillaShouldNotBeFound("id.notEquals=" + id);

        defaultPlantillaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlantillaShouldNotBeFound("id.greaterThan=" + id);

        defaultPlantillaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlantillaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlantillasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        // Get all the plantillaList where nombre equals to DEFAULT_NOMBRE
        defaultPlantillaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the plantillaList where nombre equals to UPDATED_NOMBRE
        defaultPlantillaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPlantillasByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        // Get all the plantillaList where nombre not equals to DEFAULT_NOMBRE
        defaultPlantillaShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the plantillaList where nombre not equals to UPDATED_NOMBRE
        defaultPlantillaShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPlantillasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        // Get all the plantillaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultPlantillaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the plantillaList where nombre equals to UPDATED_NOMBRE
        defaultPlantillaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPlantillasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        // Get all the plantillaList where nombre is not null
        defaultPlantillaShouldBeFound("nombre.specified=true");

        // Get all the plantillaList where nombre is null
        defaultPlantillaShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlantillasByNombreContainsSomething() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        // Get all the plantillaList where nombre contains DEFAULT_NOMBRE
        defaultPlantillaShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the plantillaList where nombre contains UPDATED_NOMBRE
        defaultPlantillaShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPlantillasByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        // Get all the plantillaList where nombre does not contain DEFAULT_NOMBRE
        defaultPlantillaShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the plantillaList where nombre does not contain UPDATED_NOMBRE
        defaultPlantillaShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllPlantillasBySubcategoriasIsEqualToSomething() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);
        SubCategoria subcategorias = SubCategoriaResourceIT.createEntity(em);
        em.persist(subcategorias);
        em.flush();
        plantilla.addSubcategorias(subcategorias);
        plantillaRepository.saveAndFlush(plantilla);
        Long subcategoriasId = subcategorias.getId();

        // Get all the plantillaList where subcategorias equals to subcategoriasId
        defaultPlantillaShouldBeFound("subcategoriasId.equals=" + subcategoriasId);

        // Get all the plantillaList where subcategorias equals to subcategoriasId + 1
        defaultPlantillaShouldNotBeFound("subcategoriasId.equals=" + (subcategoriasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlantillaShouldBeFound(String filter) throws Exception {
        restPlantillaMockMvc.perform(get("/api/plantillas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantilla.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].cuerpo").value(hasItem(DEFAULT_CUERPO.toString())));

        // Check, that the count call also returns 1
        restPlantillaMockMvc.perform(get("/api/plantillas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlantillaShouldNotBeFound(String filter) throws Exception {
        restPlantillaMockMvc.perform(get("/api/plantillas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlantillaMockMvc.perform(get("/api/plantillas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPlantilla() throws Exception {
        // Get the plantilla
        restPlantillaMockMvc.perform(get("/api/plantillas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantilla() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        int databaseSizeBeforeUpdate = plantillaRepository.findAll().size();

        // Update the plantilla
        Plantilla updatedPlantilla = plantillaRepository.findById(plantilla.getId()).get();
        // Disconnect from session so that the updates on updatedPlantilla are not directly saved in db
        em.detach(updatedPlantilla);
        updatedPlantilla
            .nombre(UPDATED_NOMBRE)
            .cuerpo(UPDATED_CUERPO);
        PlantillaDTO plantillaDTO = plantillaMapper.toDto(updatedPlantilla);

        restPlantillaMockMvc.perform(put("/api/plantillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantillaDTO)))
            .andExpect(status().isOk());

        // Validate the Plantilla in the database
        List<Plantilla> plantillaList = plantillaRepository.findAll();
        assertThat(plantillaList).hasSize(databaseSizeBeforeUpdate);
        Plantilla testPlantilla = plantillaList.get(plantillaList.size() - 1);
        assertThat(testPlantilla.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPlantilla.getCuerpo()).isEqualTo(UPDATED_CUERPO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlantilla() throws Exception {
        int databaseSizeBeforeUpdate = plantillaRepository.findAll().size();

        // Create the Plantilla
        PlantillaDTO plantillaDTO = plantillaMapper.toDto(plantilla);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantillaMockMvc.perform(put("/api/plantillas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantillaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plantilla in the database
        List<Plantilla> plantillaList = plantillaRepository.findAll();
        assertThat(plantillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlantilla() throws Exception {
        // Initialize the database
        plantillaRepository.saveAndFlush(plantilla);

        int databaseSizeBeforeDelete = plantillaRepository.findAll().size();

        // Delete the plantilla
        restPlantillaMockMvc.perform(delete("/api/plantillas/{id}", plantilla.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plantilla> plantillaList = plantillaRepository.findAll();
        assertThat(plantillaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
