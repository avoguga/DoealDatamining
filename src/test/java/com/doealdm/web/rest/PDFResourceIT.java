package com.doealdm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doealdm.IntegrationTest;
import com.doealdm.domain.PDF;
import com.doealdm.repository.PDFRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PDFResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PDFResourceIT {

    private static final byte[] DEFAULT_PDF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PDF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PDF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PDF_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/pdfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PDFRepository pDFRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPDFMockMvc;

    private PDF pDF;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PDF createEntity(EntityManager em) {
        PDF pDF = new PDF().pdf(DEFAULT_PDF).pdfContentType(DEFAULT_PDF_CONTENT_TYPE);
        return pDF;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PDF createUpdatedEntity(EntityManager em) {
        PDF pDF = new PDF().pdf(UPDATED_PDF).pdfContentType(UPDATED_PDF_CONTENT_TYPE);
        return pDF;
    }

    @BeforeEach
    public void initTest() {
        pDF = createEntity(em);
    }

    @Test
    @Transactional
    void createPDF() throws Exception {
        int databaseSizeBeforeCreate = pDFRepository.findAll().size();
        // Create the PDF
        restPDFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pDF)))
            .andExpect(status().isCreated());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeCreate + 1);
        PDF testPDF = pDFList.get(pDFList.size() - 1);
        assertThat(testPDF.getPdf()).isEqualTo(DEFAULT_PDF);
        assertThat(testPDF.getPdfContentType()).isEqualTo(DEFAULT_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPDFWithExistingId() throws Exception {
        // Create the PDF with an existing ID
        pDF.setId(1L);

        int databaseSizeBeforeCreate = pDFRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPDFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pDF)))
            .andExpect(status().isBadRequest());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPDFS() throws Exception {
        // Initialize the database
        pDFRepository.saveAndFlush(pDF);

        // Get all the pDFList
        restPDFMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pDF.getId().intValue())))
            .andExpect(jsonPath("$.[*].pdfContentType").value(hasItem(DEFAULT_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_PDF))));
    }

    @Test
    @Transactional
    void getPDF() throws Exception {
        // Initialize the database
        pDFRepository.saveAndFlush(pDF);

        // Get the pDF
        restPDFMockMvc
            .perform(get(ENTITY_API_URL_ID, pDF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pDF.getId().intValue()))
            .andExpect(jsonPath("$.pdfContentType").value(DEFAULT_PDF_CONTENT_TYPE))
            .andExpect(jsonPath("$.pdf").value(Base64Utils.encodeToString(DEFAULT_PDF)));
    }

    @Test
    @Transactional
    void getNonExistingPDF() throws Exception {
        // Get the pDF
        restPDFMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPDF() throws Exception {
        // Initialize the database
        pDFRepository.saveAndFlush(pDF);

        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();

        // Update the pDF
        PDF updatedPDF = pDFRepository.findById(pDF.getId()).get();
        // Disconnect from session so that the updates on updatedPDF are not directly saved in db
        em.detach(updatedPDF);
        updatedPDF.pdf(UPDATED_PDF).pdfContentType(UPDATED_PDF_CONTENT_TYPE);

        restPDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPDF.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPDF))
            )
            .andExpect(status().isOk());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
        PDF testPDF = pDFList.get(pDFList.size() - 1);
        assertThat(testPDF.getPdf()).isEqualTo(UPDATED_PDF);
        assertThat(testPDF.getPdfContentType()).isEqualTo(UPDATED_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPDF() throws Exception {
        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();
        pDF.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pDF.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pDF))
            )
            .andExpect(status().isBadRequest());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPDF() throws Exception {
        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();
        pDF.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pDF))
            )
            .andExpect(status().isBadRequest());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPDF() throws Exception {
        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();
        pDF.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPDFMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pDF)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePDFWithPatch() throws Exception {
        // Initialize the database
        pDFRepository.saveAndFlush(pDF);

        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();

        // Update the pDF using partial update
        PDF partialUpdatedPDF = new PDF();
        partialUpdatedPDF.setId(pDF.getId());

        restPDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPDF.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPDF))
            )
            .andExpect(status().isOk());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
        PDF testPDF = pDFList.get(pDFList.size() - 1);
        assertThat(testPDF.getPdf()).isEqualTo(DEFAULT_PDF);
        assertThat(testPDF.getPdfContentType()).isEqualTo(DEFAULT_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePDFWithPatch() throws Exception {
        // Initialize the database
        pDFRepository.saveAndFlush(pDF);

        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();

        // Update the pDF using partial update
        PDF partialUpdatedPDF = new PDF();
        partialUpdatedPDF.setId(pDF.getId());

        partialUpdatedPDF.pdf(UPDATED_PDF).pdfContentType(UPDATED_PDF_CONTENT_TYPE);

        restPDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPDF.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPDF))
            )
            .andExpect(status().isOk());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
        PDF testPDF = pDFList.get(pDFList.size() - 1);
        assertThat(testPDF.getPdf()).isEqualTo(UPDATED_PDF);
        assertThat(testPDF.getPdfContentType()).isEqualTo(UPDATED_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPDF() throws Exception {
        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();
        pDF.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pDF.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pDF))
            )
            .andExpect(status().isBadRequest());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPDF() throws Exception {
        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();
        pDF.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pDF))
            )
            .andExpect(status().isBadRequest());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPDF() throws Exception {
        int databaseSizeBeforeUpdate = pDFRepository.findAll().size();
        pDF.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPDFMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pDF)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PDF in the database
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePDF() throws Exception {
        // Initialize the database
        pDFRepository.saveAndFlush(pDF);

        int databaseSizeBeforeDelete = pDFRepository.findAll().size();

        // Delete the pDF
        restPDFMockMvc.perform(delete(ENTITY_API_URL_ID, pDF.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PDF> pDFList = pDFRepository.findAll();
        assertThat(pDFList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
