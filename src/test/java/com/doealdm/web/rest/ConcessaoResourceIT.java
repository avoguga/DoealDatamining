package com.doealdm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doealdm.IntegrationTest;
import com.doealdm.domain.Concessao;
import com.doealdm.repository.ConcessaoRepository;
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
 * Integration tests for the {@link ConcessaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConcessaoResourceIT {

    private static final LocalDate DEFAULT_DATA_ASSINATURA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ASSINATURA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PORTARIA = "AAAAAAAAAA";
    private static final String UPDATED_PORTARIA = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODO_AQUISITIVO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO_AQUISITIVO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEMPO_AFASTAMENTO = 1;
    private static final Integer UPDATED_TEMPO_AFASTAMENTO = 2;

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FINAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FINAL = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/concessaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConcessaoRepository concessaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcessaoMockMvc;

    private Concessao concessao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concessao createEntity(EntityManager em) {
        Concessao concessao = new Concessao()
            .dataAssinatura(DEFAULT_DATA_ASSINATURA)
            .portaria(DEFAULT_PORTARIA)
            .periodoAquisitivo(DEFAULT_PERIODO_AQUISITIVO)
            .tempoAfastamento(DEFAULT_TEMPO_AFASTAMENTO)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFinal(DEFAULT_DATA_FINAL);
        return concessao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concessao createUpdatedEntity(EntityManager em) {
        Concessao concessao = new Concessao()
            .dataAssinatura(UPDATED_DATA_ASSINATURA)
            .portaria(UPDATED_PORTARIA)
            .periodoAquisitivo(UPDATED_PERIODO_AQUISITIVO)
            .tempoAfastamento(UPDATED_TEMPO_AFASTAMENTO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFinal(UPDATED_DATA_FINAL);
        return concessao;
    }

    @BeforeEach
    public void initTest() {
        concessao = createEntity(em);
    }

    @Test
    @Transactional
    void createConcessao() throws Exception {
        int databaseSizeBeforeCreate = concessaoRepository.findAll().size();
        // Create the Concessao
        restConcessaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concessao)))
            .andExpect(status().isCreated());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeCreate + 1);
        Concessao testConcessao = concessaoList.get(concessaoList.size() - 1);
        assertThat(testConcessao.getDataAssinatura()).isEqualTo(DEFAULT_DATA_ASSINATURA);
        assertThat(testConcessao.getPortaria()).isEqualTo(DEFAULT_PORTARIA);
        assertThat(testConcessao.getPeriodoAquisitivo()).isEqualTo(DEFAULT_PERIODO_AQUISITIVO);
        assertThat(testConcessao.getTempoAfastamento()).isEqualTo(DEFAULT_TEMPO_AFASTAMENTO);
        assertThat(testConcessao.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testConcessao.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
    }

    @Test
    @Transactional
    void createConcessaoWithExistingId() throws Exception {
        // Create the Concessao with an existing ID
        concessao.setId(1L);

        int databaseSizeBeforeCreate = concessaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcessaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concessao)))
            .andExpect(status().isBadRequest());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConcessaos() throws Exception {
        // Initialize the database
        concessaoRepository.saveAndFlush(concessao);

        // Get all the concessaoList
        restConcessaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAssinatura").value(hasItem(DEFAULT_DATA_ASSINATURA.toString())))
            .andExpect(jsonPath("$.[*].portaria").value(hasItem(DEFAULT_PORTARIA)))
            .andExpect(jsonPath("$.[*].periodoAquisitivo").value(hasItem(DEFAULT_PERIODO_AQUISITIVO)))
            .andExpect(jsonPath("$.[*].tempoAfastamento").value(hasItem(DEFAULT_TEMPO_AFASTAMENTO)))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(DEFAULT_DATA_FINAL.toString())));
    }

    @Test
    @Transactional
    void getConcessao() throws Exception {
        // Initialize the database
        concessaoRepository.saveAndFlush(concessao);

        // Get the concessao
        restConcessaoMockMvc
            .perform(get(ENTITY_API_URL_ID, concessao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concessao.getId().intValue()))
            .andExpect(jsonPath("$.dataAssinatura").value(DEFAULT_DATA_ASSINATURA.toString()))
            .andExpect(jsonPath("$.portaria").value(DEFAULT_PORTARIA))
            .andExpect(jsonPath("$.periodoAquisitivo").value(DEFAULT_PERIODO_AQUISITIVO))
            .andExpect(jsonPath("$.tempoAfastamento").value(DEFAULT_TEMPO_AFASTAMENTO))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFinal").value(DEFAULT_DATA_FINAL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConcessao() throws Exception {
        // Get the concessao
        restConcessaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConcessao() throws Exception {
        // Initialize the database
        concessaoRepository.saveAndFlush(concessao);

        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();

        // Update the concessao
        Concessao updatedConcessao = concessaoRepository.findById(concessao.getId()).get();
        // Disconnect from session so that the updates on updatedConcessao are not directly saved in db
        em.detach(updatedConcessao);
        updatedConcessao
            .dataAssinatura(UPDATED_DATA_ASSINATURA)
            .portaria(UPDATED_PORTARIA)
            .periodoAquisitivo(UPDATED_PERIODO_AQUISITIVO)
            .tempoAfastamento(UPDATED_TEMPO_AFASTAMENTO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFinal(UPDATED_DATA_FINAL);

        restConcessaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcessao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConcessao))
            )
            .andExpect(status().isOk());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
        Concessao testConcessao = concessaoList.get(concessaoList.size() - 1);
        assertThat(testConcessao.getDataAssinatura()).isEqualTo(UPDATED_DATA_ASSINATURA);
        assertThat(testConcessao.getPortaria()).isEqualTo(UPDATED_PORTARIA);
        assertThat(testConcessao.getPeriodoAquisitivo()).isEqualTo(UPDATED_PERIODO_AQUISITIVO);
        assertThat(testConcessao.getTempoAfastamento()).isEqualTo(UPDATED_TEMPO_AFASTAMENTO);
        assertThat(testConcessao.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testConcessao.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void putNonExistingConcessao() throws Exception {
        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();
        concessao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcessaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concessao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcessao() throws Exception {
        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();
        concessao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcessao() throws Exception {
        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();
        concessao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concessao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConcessaoWithPatch() throws Exception {
        // Initialize the database
        concessaoRepository.saveAndFlush(concessao);

        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();

        // Update the concessao using partial update
        Concessao partialUpdatedConcessao = new Concessao();
        partialUpdatedConcessao.setId(concessao.getId());

        partialUpdatedConcessao.portaria(UPDATED_PORTARIA).periodoAquisitivo(UPDATED_PERIODO_AQUISITIVO).dataFinal(UPDATED_DATA_FINAL);

        restConcessaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcessao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcessao))
            )
            .andExpect(status().isOk());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
        Concessao testConcessao = concessaoList.get(concessaoList.size() - 1);
        assertThat(testConcessao.getDataAssinatura()).isEqualTo(DEFAULT_DATA_ASSINATURA);
        assertThat(testConcessao.getPortaria()).isEqualTo(UPDATED_PORTARIA);
        assertThat(testConcessao.getPeriodoAquisitivo()).isEqualTo(UPDATED_PERIODO_AQUISITIVO);
        assertThat(testConcessao.getTempoAfastamento()).isEqualTo(DEFAULT_TEMPO_AFASTAMENTO);
        assertThat(testConcessao.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testConcessao.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void fullUpdateConcessaoWithPatch() throws Exception {
        // Initialize the database
        concessaoRepository.saveAndFlush(concessao);

        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();

        // Update the concessao using partial update
        Concessao partialUpdatedConcessao = new Concessao();
        partialUpdatedConcessao.setId(concessao.getId());

        partialUpdatedConcessao
            .dataAssinatura(UPDATED_DATA_ASSINATURA)
            .portaria(UPDATED_PORTARIA)
            .periodoAquisitivo(UPDATED_PERIODO_AQUISITIVO)
            .tempoAfastamento(UPDATED_TEMPO_AFASTAMENTO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFinal(UPDATED_DATA_FINAL);

        restConcessaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcessao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcessao))
            )
            .andExpect(status().isOk());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
        Concessao testConcessao = concessaoList.get(concessaoList.size() - 1);
        assertThat(testConcessao.getDataAssinatura()).isEqualTo(UPDATED_DATA_ASSINATURA);
        assertThat(testConcessao.getPortaria()).isEqualTo(UPDATED_PORTARIA);
        assertThat(testConcessao.getPeriodoAquisitivo()).isEqualTo(UPDATED_PERIODO_AQUISITIVO);
        assertThat(testConcessao.getTempoAfastamento()).isEqualTo(UPDATED_TEMPO_AFASTAMENTO);
        assertThat(testConcessao.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testConcessao.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void patchNonExistingConcessao() throws Exception {
        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();
        concessao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcessaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concessao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concessao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcessao() throws Exception {
        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();
        concessao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concessao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcessao() throws Exception {
        int databaseSizeBeforeUpdate = concessaoRepository.findAll().size();
        concessao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(concessao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concessao in the database
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcessao() throws Exception {
        // Initialize the database
        concessaoRepository.saveAndFlush(concessao);

        int databaseSizeBeforeDelete = concessaoRepository.findAll().size();

        // Delete the concessao
        restConcessaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, concessao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concessao> concessaoList = concessaoRepository.findAll();
        assertThat(concessaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
