package com.doealdm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doealdm.IntegrationTest;
import com.doealdm.domain.Diario;
import com.doealdm.repository.DiarioRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DiarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiarioResourceIT {

    private static final LocalDate DEFAULT_DATA_PUBLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PUBLICACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String ENTITY_API_URL = "/api/diarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiarioRepository diarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiarioMockMvc;

    private Diario diario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diario createEntity(EntityManager em) {
        Diario diario = new Diario().dataPublicacao(DEFAULT_DATA_PUBLICACAO).ano(DEFAULT_ANO).numero(DEFAULT_NUMERO);
        return diario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diario createUpdatedEntity(EntityManager em) {
        Diario diario = new Diario().dataPublicacao(UPDATED_DATA_PUBLICACAO).ano(UPDATED_ANO).numero(UPDATED_NUMERO);
        return diario;
    }

    @BeforeEach
    public void initTest() {
        diario = createEntity(em);
    }

    @Test
    @Transactional
    void createDiario() throws Exception {
        int databaseSizeBeforeCreate = diarioRepository.findAll().size();
        // Create the Diario
        restDiarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diario)))
            .andExpect(status().isCreated());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeCreate + 1);
        Diario testDiario = diarioList.get(diarioList.size() - 1);
        assertThat(testDiario.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
        assertThat(testDiario.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testDiario.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void createDiarioWithExistingId() throws Exception {
        // Create the Diario with an existing ID
        diario.setId(1L);

        int databaseSizeBeforeCreate = diarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diario)))
            .andExpect(status().isBadRequest());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiarios() throws Exception {
        // Initialize the database
        diarioRepository.saveAndFlush(diario);

        // Get all the diarioList
        restDiarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diario.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }

    @Test
    @Transactional
    void getDiario() throws Exception {
        // Initialize the database
        diarioRepository.saveAndFlush(diario);

        // Get the diario
        restDiarioMockMvc
            .perform(get(ENTITY_API_URL_ID, diario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diario.getId().intValue()))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    void getNonExistingDiario() throws Exception {
        // Get the diario
        restDiarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDiario() throws Exception {
        // Initialize the database
        diarioRepository.saveAndFlush(diario);

        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();

        // Update the diario
        Diario updatedDiario = diarioRepository.findById(diario.getId()).get();
        // Disconnect from session so that the updates on updatedDiario are not directly saved in db
        em.detach(updatedDiario);
        updatedDiario.dataPublicacao(UPDATED_DATA_PUBLICACAO).ano(UPDATED_ANO).numero(UPDATED_NUMERO);

        restDiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDiario))
            )
            .andExpect(status().isOk());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
        Diario testDiario = diarioList.get(diarioList.size() - 1);
        assertThat(testDiario.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testDiario.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testDiario.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void putNonExistingDiario() throws Exception {
        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();
        diario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiario() throws Exception {
        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();
        diario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiario() throws Exception {
        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();
        diario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiarioWithPatch() throws Exception {
        // Initialize the database
        diarioRepository.saveAndFlush(diario);

        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();

        // Update the diario using partial update
        Diario partialUpdatedDiario = new Diario();
        partialUpdatedDiario.setId(diario.getId());

        restDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiario))
            )
            .andExpect(status().isOk());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
        Diario testDiario = diarioList.get(diarioList.size() - 1);
        assertThat(testDiario.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
        assertThat(testDiario.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testDiario.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void fullUpdateDiarioWithPatch() throws Exception {
        // Initialize the database
        diarioRepository.saveAndFlush(diario);

        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();

        // Update the diario using partial update
        Diario partialUpdatedDiario = new Diario();
        partialUpdatedDiario.setId(diario.getId());

        partialUpdatedDiario.dataPublicacao(UPDATED_DATA_PUBLICACAO).ano(UPDATED_ANO).numero(UPDATED_NUMERO);

        restDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiario))
            )
            .andExpect(status().isOk());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
        Diario testDiario = diarioList.get(diarioList.size() - 1);
        assertThat(testDiario.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testDiario.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testDiario.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void patchNonExistingDiario() throws Exception {
        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();
        diario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiario() throws Exception {
        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();
        diario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiario() throws Exception {
        int databaseSizeBeforeUpdate = diarioRepository.findAll().size();
        diario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(diario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Diario in the database
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiario() throws Exception {
        // Initialize the database
        diarioRepository.saveAndFlush(diario);

        int databaseSizeBeforeDelete = diarioRepository.findAll().size();

        // Delete the diario
        restDiarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, diario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Diario> diarioList = diarioRepository.findAll();
        assertThat(diarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
