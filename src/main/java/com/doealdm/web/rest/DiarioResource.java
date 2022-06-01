package com.doealdm.web.rest;

import com.doealdm.domain.Diario;
import com.doealdm.repository.DiarioRepository;
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
 * REST controller for managing {@link com.doealdm.domain.Diario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiarioResource {

    private final Logger log = LoggerFactory.getLogger(DiarioResource.class);

    private static final String ENTITY_NAME = "diario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiarioRepository diarioRepository;

    public DiarioResource(DiarioRepository diarioRepository) {
        this.diarioRepository = diarioRepository;
    }

    /**
     * {@code POST  /diarios} : Create a new diario.
     *
     * @param diario the diario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diario, or with status {@code 400 (Bad Request)} if the diario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diarios")
    public ResponseEntity<Diario> createDiario(@RequestBody Diario diario) throws URISyntaxException {
        log.debug("REST request to save Diario : {}", diario);
        if (diario.getId() != null) {
            throw new BadRequestAlertException("A new diario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Diario result = diarioRepository.save(diario);
        return ResponseEntity
            .created(new URI("/api/diarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diarios/:id} : Updates an existing diario.
     *
     * @param id the id of the diario to save.
     * @param diario the diario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diario,
     * or with status {@code 400 (Bad Request)} if the diario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diarios/{id}")
    public ResponseEntity<Diario> updateDiario(@PathVariable(value = "id", required = false) final Long id, @RequestBody Diario diario)
        throws URISyntaxException {
        log.debug("REST request to update Diario : {}, {}", id, diario);
        if (diario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Diario result = diarioRepository.save(diario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diario.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /diarios/:id} : Partial updates given fields of an existing diario, field will ignore if it is null
     *
     * @param id the id of the diario to save.
     * @param diario the diario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diario,
     * or with status {@code 400 (Bad Request)} if the diario is not valid,
     * or with status {@code 404 (Not Found)} if the diario is not found,
     * or with status {@code 500 (Internal Server Error)} if the diario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/diarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Diario> partialUpdateDiario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Diario diario
    ) throws URISyntaxException {
        log.debug("REST request to partial update Diario partially : {}, {}", id, diario);
        if (diario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Diario> result = diarioRepository
            .findById(diario.getId())
            .map(existingDiario -> {
                if (diario.getDataPublicacao() != null) {
                    existingDiario.setDataPublicacao(diario.getDataPublicacao());
                }
                if (diario.getAno() != null) {
                    existingDiario.setAno(diario.getAno());
                }
                if (diario.getNumero() != null) {
                    existingDiario.setNumero(diario.getNumero());
                }

                return existingDiario;
            })
            .map(diarioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diario.getId().toString())
        );
    }

    /**
     * {@code GET  /diarios} : get all the diarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diarios in body.
     */
    @GetMapping("/diarios")
    public List<Diario> getAllDiarios() {
        log.debug("REST request to get all Diarios");
        return diarioRepository.findAll();
    }

    /**
     * {@code GET  /diarios/:id} : get the "id" diario.
     *
     * @param id the id of the diario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diarios/{id}")
    public ResponseEntity<Diario> getDiario(@PathVariable Long id) {
        log.debug("REST request to get Diario : {}", id);
        Optional<Diario> diario = diarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diario);
    }

    /**
     * {@code DELETE  /diarios/:id} : delete the "id" diario.
     *
     * @param id the id of the diario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diarios/{id}")
    public ResponseEntity<Void> deleteDiario(@PathVariable Long id) {
        log.debug("REST request to delete Diario : {}", id);
        diarioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
