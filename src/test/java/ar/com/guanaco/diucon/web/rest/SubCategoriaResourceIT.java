package ar.com.guanaco.diucon.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import ar.com.guanaco.diucon.DiuconApp;
import ar.com.guanaco.diucon.domain.Categoria;
import ar.com.guanaco.diucon.domain.Plantilla;
import ar.com.guanaco.diucon.domain.Responsable;
import ar.com.guanaco.diucon.domain.SubCategoria;
import ar.com.guanaco.diucon.repository.SubCategoriaRepository;
import ar.com.guanaco.diucon.service.SubCategoriaQueryService;
import ar.com.guanaco.diucon.service.SubCategoriaService;
import ar.com.guanaco.diucon.service.dto.SubCategoriaDTO;
import ar.com.guanaco.diucon.service.mapper.SubCategoriaMapper;

/**
 * Integration tests for the {@link SubCategoriaResource} REST controller.
 */
@SpringBootTest(classes = DiuconApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SubCategoriaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Autowired
    private SubCategoriaMapper subCategoriaMapper;

    @Autowired
    private SubCategoriaService subCategoriaService;

    @Autowired
    private SubCategoriaQueryService subCategoriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubCategoriaMockMvc;

    private SubCategoria subCategoria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it, if
     * they test an entity which requires the current entity.
     */
    public static SubCategoria createEntity(EntityManager em) {
        SubCategoria subCategoria = new SubCategoria().nombre(DEFAULT_NOMBRE).observaciones(DEFAULT_OBSERVACIONES);
        return subCategoria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it, if
     * they test an entity which requires the current entity.
     */
    public static SubCategoria createUpdatedEntity(EntityManager em) {
        SubCategoria subCategoria = new SubCategoria().nombre(UPDATED_NOMBRE).observaciones(UPDATED_OBSERVACIONES);
        return subCategoria;
    }

    @BeforeEach
    public void initTest() {
        subCategoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubCategoria() throws Exception {
        int databaseSizeBeforeCreate = subCategoriaRepository.findAll().size();

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);
        restSubCategoriaMockMvc.perform(post("/api/sub-categorias").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))).andExpect(status().isCreated());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeCreate + 1);
        SubCategoria testSubCategoria = subCategoriaList.get(subCategoriaList.size() - 1);
        assertThat(testSubCategoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSubCategoria.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createSubCategoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subCategoriaRepository.findAll().size();

        // Create the SubCategoria with an existing ID
        subCategoria.setId(1L);
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubCategoriaMockMvc
                .perform(post("/api/sub-categorias").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO)))
                .andExpect(status().isBadRequest());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoriaRepository.findAll().size();
        // set the field null
        subCategoria.setNombre(null);

        // Create the SubCategoria, which fails.
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        restSubCategoriaMockMvc
                .perform(post("/api/sub-categorias").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO)))
                .andExpect(status().isBadRequest());

        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubCategorias() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias?sort=id,desc")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subCategoria.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
                .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())));
    }

    @Test
    @Transactional
    public void getSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get the subCategoria
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias/{id}", subCategoria.getId()))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(subCategoria.getId().intValue()))
                .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
                .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()));
    }

    @Test
    @Transactional
    public void getSubCategoriasByIdFiltering() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        Long id = subCategoria.getId();

        defaultSubCategoriaShouldBeFound("id.equals=" + id);
        defaultSubCategoriaShouldNotBeFound("id.notEquals=" + id);

        defaultSubCategoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubCategoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultSubCategoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubCategoriaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllSubCategoriasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList where nombre equals to DEFAULT_NOMBRE
        defaultSubCategoriaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the subCategoriaList where nombre equals to UPDATED_NOMBRE
        defaultSubCategoriaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSubCategoriasByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList where nombre not equals to DEFAULT_NOMBRE
        defaultSubCategoriaShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the subCategoriaList where nombre not equals to UPDATED_NOMBRE
        defaultSubCategoriaShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSubCategoriasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultSubCategoriaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the subCategoriaList where nombre equals to UPDATED_NOMBRE
        defaultSubCategoriaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSubCategoriasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList where nombre is not null
        defaultSubCategoriaShouldBeFound("nombre.specified=true");

        // Get all the subCategoriaList where nombre is null
        defaultSubCategoriaShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubCategoriasByNombreContainsSomething() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList where nombre contains DEFAULT_NOMBRE
        defaultSubCategoriaShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the subCategoriaList where nombre contains UPDATED_NOMBRE
        defaultSubCategoriaShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSubCategoriasByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList where nombre does not contain DEFAULT_NOMBRE
        defaultSubCategoriaShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the subCategoriaList where nombre does not contain UPDATED_NOMBRE
        defaultSubCategoriaShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSubCategoriasBycategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);
        Categoria categoria = CategoriaResourceIT.createEntity(em);
        em.persist(categoria);
        em.flush();
        subCategoria.setcategoria(categoria);
        subCategoriaRepository.saveAndFlush(subCategoria);
        Long categoriaId = categoria.getId();

        // Get all the subCategoriaList where categoria equals to categoriaId
        defaultSubCategoriaShouldBeFound("categoriaId.equals=" + categoriaId);

        // Get all the subCategoriaList where categoria equals to categoriaId + 1
        defaultSubCategoriaShouldNotBeFound("categoriaId.equals=" + (categoriaId + 1));
    }

    @Test
    @Transactional
    public void getAllSubCategoriasByResponsablesIsEqualToSomething() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);
        Responsable responsables = ResponsableResourceIT.createEntity(em);
        em.persist(responsables);
        em.flush();
        subCategoria.addResponsables(responsables);
        subCategoriaRepository.saveAndFlush(subCategoria);
        Long responsablesId = responsables.getId();

        // Get all the subCategoriaList where responsables equals to responsablesId
        defaultSubCategoriaShouldBeFound("responsablesId.equals=" + responsablesId);

        // Get all the subCategoriaList where responsables equals to responsablesId + 1
        defaultSubCategoriaShouldNotBeFound("responsablesId.equals=" + (responsablesId + 1));
    }

    @Test
    @Transactional
    public void getAllSubCategoriasByPlantillasIsEqualToSomething() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);
        Plantilla plantillas = PlantillaResourceIT.createEntity(em);
        em.persist(plantillas);
        em.flush();
        subCategoria.addPlantillas(plantillas);
        subCategoriaRepository.saveAndFlush(subCategoria);
        Long plantillasId = plantillas.getId();

        // Get all the subCategoriaList where plantillas equals to plantillasId
        defaultSubCategoriaShouldBeFound("plantillasId.equals=" + plantillasId);

        // Get all the subCategoriaList where plantillas equals to plantillasId + 1
        defaultSubCategoriaShouldNotBeFound("plantillasId.equals=" + (plantillasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubCategoriaShouldBeFound(String filter) throws Exception {
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias?sort=id,desc&" + filter)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subCategoria.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
                .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())));

        // Check, that the count call also returns 1
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias/count?sort=id,desc&" + filter))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubCategoriaShouldNotBeFound(String filter) throws Exception {
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias?sort=id,desc&" + filter)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias/count?sort=id,desc&" + filter))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSubCategoria() throws Exception {
        // Get the subCategoria
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();

        // Update the subCategoria
        SubCategoria updatedSubCategoria = subCategoriaRepository.findById(subCategoria.getId()).get();
        // Disconnect from session so that the updates on updatedSubCategoria are not
        // directly saved in db
        em.detach(updatedSubCategoria);
        updatedSubCategoria.nombre(UPDATED_NOMBRE).observaciones(UPDATED_OBSERVACIONES);
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(updatedSubCategoria);

        restSubCategoriaMockMvc.perform(put("/api/sub-categorias").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))).andExpect(status().isOk());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
        SubCategoria testSubCategoria = subCategoriaList.get(subCategoriaList.size() - 1);
        assertThat(testSubCategoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSubCategoria.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingSubCategoria() throws Exception {
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCategoriaMockMvc
                .perform(put("/api/sub-categorias").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO)))
                .andExpect(status().isBadRequest());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        int databaseSizeBeforeDelete = subCategoriaRepository.findAll().size();

        // Delete the subCategoria
        restSubCategoriaMockMvc
                .perform(delete("/api/sub-categorias/{id}", subCategoria.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
