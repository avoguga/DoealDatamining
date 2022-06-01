package com.doealdm.web.rest;

import com.doealdm.domain.Concessao;
import com.doealdm.repository.ConcessaoRepository;
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
 * REST controller for managing {@link com.doealdm.domain.Concessao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConcessaoResource {

    private final Logger log = LoggerFactory.getLogger(ConcessaoResource.class);

    private static final String ENTITY_NAME = "concessao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcessaoRepository concessaoRepository;

    public ConcessaoResource(ConcessaoRepository concessaoRepository) {
        this.concessaoRepository = concessaoRepository;
    }

    /**
     * {@code POST  /concessaos} : Create a new concessao.
     *
     * @param concessao the concessao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concessao, or with status {@code 400 (Bad Request)} if the concessao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concessaos")
    public ResponseEntity<Concessao> createConcessao(@RequestBody Concessao concessao) throws URISyntaxException {
        log.debug("REST request to save Concessao : {}", concessao);
        if (concessao.getId() != null) {
            throw new BadRequestAlertException("A new concessao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Concessao result = concessaoRepository.save(concessao);
        return ResponseEntity
            .created(new URI("/api/concessaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concessaos/:id} : Updates an existing concessao.
     *
     * @param id the id of the concessao to save.
     * @param concessao the concessao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concessao,
     * or with status {@code 400 (Bad Request)} if the concessao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concessao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concessaos/{id}")
    public ResponseEntity<Concessao> updateConcessao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Concessao concessao
    ) throws URISyntaxException {
        log.debug("REST request to update Concessao : {}, {}", id, concessao);
        if (concessao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concessao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concessaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Concessao result = concessaoRepository.save(concessao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concessao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /concessaos/:id} : Partial updates given fields of an existing concessao, field will ignore if it is null
     *
     * @param id the id of the concessao to save.
     * @param concessao the concessao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concessao,
     * or with status {@code 400 (Bad Request)} if the concessao is not valid,
     * or with status {@code 404 (Not Found)} if the concessao is not found,
     * or with status {@code 500 (Internal Server Error)} if the concessao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/concessaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Concessao> partialUpdateConcessao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Concessao concessao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Concessao partially : {}, {}", id, concessao);
        if (concessao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concessao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concessaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Concessao> result = concessaoRepository
            .findById(concessao.getId())
            .map(existingConcessao -> {
                if (concessao.getDataAssinatura() != null) {
                    existingConcessao.setDataAssinatura(concessao.getDataAssinatura());
                }
                if (concessao.getPortaria() != null) {
                    existingConcessao.setPortaria(concessao.getPortaria());
                }
                if (concessao.getPeriodoAquisitivo() != null) {
                    existingConcessao.setPeriodoAquisitivo(concessao.getPeriodoAquisitivo());
                }
                if (concessao.getTempoAfastamento() != null) {
                    existingConcessao.setTempoAfastamento(concessao.getTempoAfastamento());
                }
                if (concessao.getDataInicio() != null) {
                    existingConcessao.setDataInicio(concessao.getDataInicio());
                }
                if (concessao.getDataFinal() != null) {
                    existingConcessao.setDataFinal(concessao.getDataFinal());
                }

                return existingConcessao;
            })
            .map(concessaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concessao.getId().toString())
        );
    }

    /**
     * {@code GET  /concessaos} : get all the concessaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concessaos in body.
     */
    @GetMapping("/concessaos")
    public List<Concessao> getAllConcessaos() {
        log.debug("REST request to get all Concessaos");
        return concessaoRepository.findAll();
    }

    /**
     * {@code GET  /concessaos/:id} : get the "id" concessao.
     *
     * @param id the id of the concessao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concessao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concessaos/{id}")
    public ResponseEntity<Concessao> getConcessao(@PathVariable Long id) {
        log.debug("REST request to get Concessao : {}", id);
        Optional<Concessao> concessao = concessaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(concessao);
    }

    /**
     * {@code DELETE  /concessaos/:id} : delete the "id" concessao.
     *
     * @param id the id of the concessao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concessaos/{id}")
    public ResponseEntity<Void> deleteConcessao(@PathVariable Long id) {
        log.debug("REST request to delete Concessao : {}", id);
        concessaoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
