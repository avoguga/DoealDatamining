package com.doealdm.web.rest;

import com.doealdm.domain.PDF;
import com.doealdm.repository.PDFRepository;
import com.doealdm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.doealdm.domain.PDF}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PDFResource {

    private final Logger log = LoggerFactory.getLogger(PDFResource.class);

    private static final String ENTITY_NAME = "pDF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PDFRepository pDFRepository;

    public PDFResource(PDFRepository pDFRepository) {
        this.pDFRepository = pDFRepository;
    }

    /**
     * {@code POST  /pdfs} : Create a new pDF.
     *
     * @param pDF the pDF to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pDF, or with status {@code 400 (Bad Request)} if the pDF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pdfs")
    public ResponseEntity<PDF> createPDF(@RequestBody PDF pDF) throws URISyntaxException {
        log.debug("REST request to save PDF : {}", pDF);
        if (pDF.getId() != null) {
            throw new BadRequestAlertException("A new pDF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PDF result = pDFRepository.save(pDF);
        return ResponseEntity
            .created(new URI("/api/pdfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pdfs/:id} : Updates an existing pDF.
     *
     * @param id the id of the pDF to save.
     * @param pDF the pDF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pDF,
     * or with status {@code 400 (Bad Request)} if the pDF is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pDF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pdfs/{id}")
    public ResponseEntity<PDF> updatePDF(@PathVariable(value = "id", required = false) final Long id, @RequestBody PDF pDF)
        throws URISyntaxException {
        log.debug("REST request to update PDF : {}, {}", id, pDF);
        if (pDF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pDF.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pDFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PDF result = pDFRepository.save(pDF);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pDF.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pdfs/:id} : Partial updates given fields of an existing pDF, field will ignore if it is null
     *
     * @param id the id of the pDF to save.
     * @param pDF the pDF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pDF,
     * or with status {@code 400 (Bad Request)} if the pDF is not valid,
     * or with status {@code 404 (Not Found)} if the pDF is not found,
     * or with status {@code 500 (Internal Server Error)} if the pDF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pdfs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PDF> partialUpdatePDF(@PathVariable(value = "id", required = false) final Long id, @RequestBody PDF pDF)
        throws URISyntaxException {
        log.debug("REST request to partial update PDF partially : {}, {}", id, pDF);
        if (pDF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pDF.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pDFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PDF> result = pDFRepository
            .findById(pDF.getId())
            .map(existingPDF -> {
                if (pDF.getPdf() != null) {
                    existingPDF.setPdf(pDF.getPdf());
                }
                if (pDF.getPdfContentType() != null) {
                    existingPDF.setPdfContentType(pDF.getPdfContentType());
                }

                return existingPDF;
            })
            .map(pDFRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pDF.getId().toString())
        );
    }

    /**
     * {@code GET  /pdfs} : get all the pDFS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pDFS in body.
     */
    @GetMapping("/pdfs")
    public List<PDF> getAllPDFS() {
        log.debug("REST request to get all PDFS");
        return pDFRepository.findAll();
    }

    /**
     * {@code GET  /pdfs/:id} : get the "id" pDF.
     *
     * @param id the id of the pDF to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pDF, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pdfs/{id}")
    public ResponseEntity<PDF> getPDF(@PathVariable Long id) {
        log.debug("REST request to get PDF : {}", id);
        Optional<PDF> pDF = pDFRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pDF);
    }

    /**
     * {@code DELETE  /pdfs/:id} : delete the "id" pDF.
     *
     * @param id the id of the pDF to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pdfs/{id}")
    public ResponseEntity<Void> deletePDF(@PathVariable Long id) {
        log.debug("REST request to delete PDF : {}", id);
        pDFRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
