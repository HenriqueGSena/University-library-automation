package br.com.universityautomation.developer.web.rest;

import br.com.universityautomation.developer.domain.Periodicals;
import br.com.universityautomation.developer.repository.PeriodicalsRepository;
import br.com.universityautomation.developer.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link br.com.universityautomation.developer.domain.Periodicals}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PeriodicalsResource {

    private final Logger log = LoggerFactory.getLogger(PeriodicalsResource.class);

    private static final String ENTITY_NAME = "periodicals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodicalsRepository periodicalsRepository;

    public PeriodicalsResource(PeriodicalsRepository periodicalsRepository) {
        this.periodicalsRepository = periodicalsRepository;
    }

    /**
     * {@code POST  /periodicals} : Create a new periodicals.
     *
     * @param periodicals the periodicals to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodicals, or with status {@code 400 (Bad Request)} if the periodicals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodicals")
    public ResponseEntity<Periodicals> createPeriodicals(@RequestBody Periodicals periodicals) throws URISyntaxException {
        log.debug("REST request to save Periodicals : {}", periodicals);
        if (periodicals.getId() != null) {
            throw new BadRequestAlertException("A new periodicals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Periodicals result = periodicalsRepository.save(periodicals);
        return ResponseEntity
            .created(new URI("/api/periodicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodicals/:id} : Updates an existing periodicals.
     *
     * @param id the id of the periodicals to save.
     * @param periodicals the periodicals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodicals,
     * or with status {@code 400 (Bad Request)} if the periodicals is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodicals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodicals/{id}")
    public ResponseEntity<Periodicals> updatePeriodicals(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Periodicals periodicals
    ) throws URISyntaxException {
        log.debug("REST request to update Periodicals : {}, {}", id, periodicals);
        if (periodicals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodicals.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodicalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Periodicals result = periodicalsRepository.save(periodicals);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, periodicals.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /periodicals/:id} : Partial updates given fields of an existing periodicals, field will ignore if it is null
     *
     * @param id the id of the periodicals to save.
     * @param periodicals the periodicals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodicals,
     * or with status {@code 400 (Bad Request)} if the periodicals is not valid,
     * or with status {@code 404 (Not Found)} if the periodicals is not found,
     * or with status {@code 500 (Internal Server Error)} if the periodicals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/periodicals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Periodicals> partialUpdatePeriodicals(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Periodicals periodicals
    ) throws URISyntaxException {
        log.debug("REST request to partial update Periodicals partially : {}, {}", id, periodicals);
        if (periodicals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodicals.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodicalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Periodicals> result = periodicalsRepository
            .findById(periodicals.getId())
            .map(existingPeriodicals -> {
                if (periodicals.getVolume() != null) {
                    existingPeriodicals.setVolume(periodicals.getVolume());
                }
                if (periodicals.getNumero() != null) {
                    existingPeriodicals.setNumero(periodicals.getNumero());
                }
                if (periodicals.getIssn() != null) {
                    existingPeriodicals.setIssn(periodicals.getIssn());
                }

                return existingPeriodicals;
            })
            .map(periodicalsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, periodicals.getId().toString())
        );
    }

    /**
     * {@code GET  /periodicals} : get all the periodicals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodicals in body.
     */
    @GetMapping("/periodicals")
    public List<Periodicals> getAllPeriodicals() {
        log.debug("REST request to get all Periodicals");
        return periodicalsRepository.findAll();
    }

    /**
     * {@code GET  /periodicals/:id} : get the "id" periodicals.
     *
     * @param id the id of the periodicals to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodicals, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodicals/{id}")
    public ResponseEntity<Periodicals> getPeriodicals(@PathVariable Long id) {
        log.debug("REST request to get Periodicals : {}", id);
        Optional<Periodicals> periodicals = periodicalsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(periodicals);
    }

    /**
     * {@code DELETE  /periodicals/:id} : delete the "id" periodicals.
     *
     * @param id the id of the periodicals to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodicals/{id}")
    public ResponseEntity<Void> deletePeriodicals(@PathVariable Long id) {
        log.debug("REST request to delete Periodicals : {}", id);
        periodicalsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
