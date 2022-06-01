package com.doealdm.web.rest;

import com.doealdm.domain.Servidor;
import com.doealdm.repository.ServidorRepository;
import com.doealdm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.doealdm.domain.Servidor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ServidorResource {

    private final Logger log = LoggerFactory.getLogger(ServidorResource.class);

    private static final String ENTITY_NAME = "servidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServidorRepository servidorRepository;

    public ServidorResource(ServidorRepository servidorRepository) {
        this.servidorRepository = servidorRepository;
    }

    /**
     * {@code POST  /servidors} : Create a new servidor.
     *
     * @param servidor the servidor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servidor, or with status {@code 400 (Bad Request)} if the servidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servidors")
    public ResponseEntity<Servidor> createServidor(@RequestBody Servidor servidor) throws URISyntaxException {
        log.debug("REST request to save Servidor : {}", servidor);
        if (servidor.getId() != null) {
            throw new BadRequestAlertException("A new servidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Servidor result = servidorRepository.save(servidor);
        return ResponseEntity
            .created(new URI("/api/servidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servidors/:id} : Updates an existing servidor.
     *
     * @param id the id of the servidor to save.
     * @param servidor the servidor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servidor,
     * or with status {@code 400 (Bad Request)} if the servidor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servidor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servidors/{id}")
    public ResponseEntity<Servidor> updateServidor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Servidor servidor
    ) throws URISyntaxException {
        log.debug("REST request to update Servidor : {}, {}", id, servidor);
        if (servidor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servidor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Servidor result = servidorRepository.save(servidor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servidor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /servidors/:id} : Partial updates given fields of an existing servidor, field will ignore if it is null
     *
     * @param id the id of the servidor to save.
     * @param servidor the servidor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servidor,
     * or with status {@code 400 (Bad Request)} if the servidor is not valid,
     * or with status {@code 404 (Not Found)} if the servidor is not found,
     * or with status {@code 500 (Internal Server Error)} if the servidor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/servidors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Servidor> partialUpdateServidor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Servidor servidor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Servidor partially : {}, {}", id, servidor);
        if (servidor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servidor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Servidor> result = servidorRepository
            .findById(servidor.getId())
            .map(existingServidor -> {
                if (servidor.getNome() != null) {
                    existingServidor.setNome(servidor.getNome());
                }
                if (servidor.getCpf() != null) {
                    existingServidor.setCpf(servidor.getCpf());
                }
                if (servidor.getMatricula() != null) {
                    existingServidor.setMatricula(servidor.getMatricula());
                }
                if (servidor.getCargo() != null) {
                    existingServidor.setCargo(servidor.getCargo());
                }

                return existingServidor;
            })
            .map(servidorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servidor.getId().toString())
        );
    }

    /**
     * {@code GET  /servidors} : get all the servidors.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servidors in body.
     */
    @GetMapping("/servidors")
    public List<Servidor> getAllServidors(@RequestParam(required = false) String filter) {
        if ("concessao-is-null".equals(filter)) {
            log.debug("REST request to get all Servidors where concessao is null");
            return StreamSupport
                .stream(servidorRepository.findAll().spliterator(), false)
                .filter(servidor -> servidor.getConcessao() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Servidors");
        return servidorRepository.findAll();
    }

    /**
     * {@code GET  /servidors/:id} : get the "id" servidor.
     *
     * @param id the id of the servidor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servidor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servidors/{id}")
    public ResponseEntity<Servidor> getServidor(@PathVariable Long id) {
        log.debug("REST request to get Servidor : {}", id);
        Optional<Servidor> servidor = servidorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(servidor);
    }

    /**
     * {@code DELETE  /servidors/:id} : delete the "id" servidor.
     *
     * @param id the id of the servidor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servidors/{id}")
    public ResponseEntity<Void> deleteServidor(@PathVariable Long id) {
        log.debug("REST request to delete Servidor : {}", id);
        servidorRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
