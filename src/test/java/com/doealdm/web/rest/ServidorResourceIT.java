package com.doealdm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doealdm.IntegrationTest;
import com.doealdm.domain.Servidor;
import com.doealdm.repository.ServidorRepository;
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
 * Integration tests for the {@link ServidorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServidorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULA = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/servidors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServidorRepository servidorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServidorMockMvc;

    private Servidor servidor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servidor createEntity(EntityManager em) {
        Servidor servidor = new Servidor().nome(DEFAULT_NOME).cpf(DEFAULT_CPF).matricula(DEFAULT_MATRICULA).cargo(DEFAULT_CARGO);
        return servidor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servidor createUpdatedEntity(EntityManager em) {
        Servidor servidor = new Servidor().nome(UPDATED_NOME).cpf(UPDATED_CPF).matricula(UPDATED_MATRICULA).cargo(UPDATED_CARGO);
        return servidor;
    }

    @BeforeEach
    public void initTest() {
        servidor = createEntity(em);
    }

    @Test
    @Transactional
    void createServidor() throws Exception {
        int databaseSizeBeforeCreate = servidorRepository.findAll().size();
        // Create the Servidor
        restServidorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isCreated());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeCreate + 1);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testServidor.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testServidor.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testServidor.getCargo()).isEqualTo(DEFAULT_CARGO);
    }

    @Test
    @Transactional
    void createServidorWithExistingId() throws Exception {
        // Create the Servidor with an existing ID
        servidor.setId(1L);

        int databaseSizeBeforeCreate = servidorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServidorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServidors() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        // Get all the servidorList
        restServidorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)));
    }

    @Test
    @Transactional
    void getServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        // Get the servidor
        restServidorMockMvc
            .perform(get(ENTITY_API_URL_ID, servidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servidor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO));
    }

    @Test
    @Transactional
    void getNonExistingServidor() throws Exception {
        // Get the servidor
        restServidorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();

        // Update the servidor
        Servidor updatedServidor = servidorRepository.findById(servidor.getId()).get();
        // Disconnect from session so that the updates on updatedServidor are not directly saved in db
        em.detach(updatedServidor);
        updatedServidor.nome(UPDATED_NOME).cpf(UPDATED_CPF).matricula(UPDATED_MATRICULA).cargo(UPDATED_CARGO);

        restServidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServidor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedServidor))
            )
            .andExpect(status().isOk());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testServidor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testServidor.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testServidor.getCargo()).isEqualTo(UPDATED_CARGO);
    }

    @Test
    @Transactional
    void putNonExistingServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();
        servidor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servidor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servidor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();
        servidor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servidor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();
        servidor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServidorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServidorWithPatch() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();

        // Update the servidor using partial update
        Servidor partialUpdatedServidor = new Servidor();
        partialUpdatedServidor.setId(servidor.getId());

        partialUpdatedServidor.cpf(UPDATED_CPF).matricula(UPDATED_MATRICULA).cargo(UPDATED_CARGO);

        restServidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServidor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServidor))
            )
            .andExpect(status().isOk());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testServidor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testServidor.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testServidor.getCargo()).isEqualTo(UPDATED_CARGO);
    }

    @Test
    @Transactional
    void fullUpdateServidorWithPatch() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();

        // Update the servidor using partial update
        Servidor partialUpdatedServidor = new Servidor();
        partialUpdatedServidor.setId(servidor.getId());

        partialUpdatedServidor.nome(UPDATED_NOME).cpf(UPDATED_CPF).matricula(UPDATED_MATRICULA).cargo(UPDATED_CARGO);

        restServidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServidor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServidor))
            )
            .andExpect(status().isOk());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testServidor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testServidor.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testServidor.getCargo()).isEqualTo(UPDATED_CARGO);
    }

    @Test
    @Transactional
    void patchNonExistingServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();
        servidor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servidor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servidor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();
        servidor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servidor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();
        servidor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServidorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        int databaseSizeBeforeDelete = servidorRepository.findAll().size();

        // Delete the servidor
        restServidorMockMvc
            .perform(delete(ENTITY_API_URL_ID, servidor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
