package br.com.universityautomation.developer.web.rest;

import br.com.universityautomation.developer.domain.Functionaries;
import br.com.universityautomation.developer.repository.FunctionariesRepository;
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
 * REST controller for managing {@link br.com.universityautomation.developer.domain.Functionaries}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FunctionariesResource {

    private final Logger log = LoggerFactory.getLogger(FunctionariesResource.class);

    private static final String ENTITY_NAME = "functionaries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctionariesRepository functionariesRepository;

    public FunctionariesResource(FunctionariesRepository functionariesRepository) {
        this.functionariesRepository = functionariesRepository;
    }

    /**
     * {@code POST  /functionaries} : Create a new functionaries.
     *
     * @param functionaries the functionaries to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functionaries, or with status {@code 400 (Bad Request)} if the functionaries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/functionaries")
    public ResponseEntity<Functionaries> createFunctionaries(@RequestBody Functionaries functionaries) throws URISyntaxException {
        log.debug("REST request to save Functionaries : {}", functionaries);
        if (functionaries.getId() != null) {
            throw new BadRequestAlertException("A new functionaries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Functionaries result = functionariesRepository.save(functionaries);
        return ResponseEntity
            .created(new URI("/api/functionaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /functionaries/:id} : Updates an existing functionaries.
     *
     * @param id the id of the functionaries to save.
     * @param functionaries the functionaries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionaries,
     * or with status {@code 400 (Bad Request)} if the functionaries is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functionaries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/functionaries/{id}")
    public ResponseEntity<Functionaries> updateFunctionaries(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Functionaries functionaries
    ) throws URISyntaxException {
        log.debug("REST request to update Functionaries : {}, {}", id, functionaries);
        if (functionaries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functionaries.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functionariesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Functionaries result = functionariesRepository.save(functionaries);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functionaries.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /functionaries/:id} : Partial updates given fields of an existing functionaries, field will ignore if it is null
     *
     * @param id the id of the functionaries to save.
     * @param functionaries the functionaries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionaries,
     * or with status {@code 400 (Bad Request)} if the functionaries is not valid,
     * or with status {@code 404 (Not Found)} if the functionaries is not found,
     * or with status {@code 500 (Internal Server Error)} if the functionaries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/functionaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Functionaries> partialUpdateFunctionaries(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Functionaries functionaries
    ) throws URISyntaxException {
        log.debug("REST request to partial update Functionaries partially : {}, {}", id, functionaries);
        if (functionaries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functionaries.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functionariesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Functionaries> result = functionariesRepository
            .findById(functionaries.getId())
            .map(existingFunctionaries -> {
                if (functionaries.getSetor() != null) {
                    existingFunctionaries.setSetor(functionaries.getSetor());
                }

                return existingFunctionaries;
            })
            .map(functionariesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functionaries.getId().toString())
        );
    }

    /**
     * {@code GET  /functionaries} : get all the functionaries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functionaries in body.
     */
    @GetMapping("/functionaries")
    public List<Functionaries> getAllFunctionaries() {
        log.debug("REST request to get all Functionaries");
        return functionariesRepository.findAll();
    }

    /**
     * {@code GET  /functionaries/:id} : get the "id" functionaries.
     *
     * @param id the id of the functionaries to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functionaries, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/functionaries/{id}")
    public ResponseEntity<Functionaries> getFunctionaries(@PathVariable Long id) {
        log.debug("REST request to get Functionaries : {}", id);
        Optional<Functionaries> functionaries = functionariesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(functionaries);
    }

    /**
     * {@code DELETE  /functionaries/:id} : delete the "id" functionaries.
     *
     * @param id the id of the functionaries to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/functionaries/{id}")
    public ResponseEntity<Void> deleteFunctionaries(@PathVariable Long id) {
        log.debug("REST request to delete Functionaries : {}", id);
        functionariesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
