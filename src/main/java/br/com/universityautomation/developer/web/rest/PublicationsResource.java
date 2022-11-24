package br.com.universityautomation.developer.web.rest;

import br.com.universityautomation.developer.domain.Publications;
import br.com.universityautomation.developer.repository.PublicationsRepository;
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
 * REST controller for managing {@link br.com.universityautomation.developer.domain.Publications}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PublicationsResource {

    private final Logger log = LoggerFactory.getLogger(PublicationsResource.class);

    private static final String ENTITY_NAME = "publications";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicationsRepository publicationsRepository;

    public PublicationsResource(PublicationsRepository publicationsRepository) {
        this.publicationsRepository = publicationsRepository;
    }

    /**
     * {@code POST  /publications} : Create a new publications.
     *
     * @param publications the publications to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publications, or with status {@code 400 (Bad Request)} if the publications has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/publications")
    public ResponseEntity<Publications> createPublications(@RequestBody Publications publications) throws URISyntaxException {
        log.debug("REST request to save Publications : {}", publications);
        if (publications.getId() != null) {
            throw new BadRequestAlertException("A new publications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Publications result = publicationsRepository.save(publications);
        return ResponseEntity
            .created(new URI("/api/publications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /publications/:id} : Updates an existing publications.
     *
     * @param id the id of the publications to save.
     * @param publications the publications to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publications,
     * or with status {@code 400 (Bad Request)} if the publications is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publications couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/publications/{id}")
    public ResponseEntity<Publications> updatePublications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Publications publications
    ) throws URISyntaxException {
        log.debug("REST request to update Publications : {}, {}", id, publications);
        if (publications.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publications.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Publications result = publicationsRepository.save(publications);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, publications.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /publications/:id} : Partial updates given fields of an existing publications, field will ignore if it is null
     *
     * @param id the id of the publications to save.
     * @param publications the publications to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publications,
     * or with status {@code 400 (Bad Request)} if the publications is not valid,
     * or with status {@code 404 (Not Found)} if the publications is not found,
     * or with status {@code 500 (Internal Server Error)} if the publications couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/publications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Publications> partialUpdatePublications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Publications publications
    ) throws URISyntaxException {
        log.debug("REST request to partial update Publications partially : {}, {}", id, publications);
        if (publications.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publications.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Publications> result = publicationsRepository
            .findById(publications.getId())
            .map(existingPublications -> {
                if (publications.getTitulo() != null) {
                    existingPublications.setTitulo(publications.getTitulo());
                }
                if (publications.getPreco() != null) {
                    existingPublications.setPreco(publications.getPreco());
                }
                if (publications.getDataPublicacao() != null) {
                    existingPublications.setDataPublicacao(publications.getDataPublicacao());
                }
                if (publications.getStatus() != null) {
                    existingPublications.setStatus(publications.getStatus());
                }

                return existingPublications;
            })
            .map(publicationsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, publications.getId().toString())
        );
    }

    /**
     * {@code GET  /publications} : get all the publications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publications in body.
     */
    @GetMapping("/publications")
    public List<Publications> getAllPublications() {
        log.debug("REST request to get all Publications");
        return publicationsRepository.findAll();
    }

    /**
     * {@code GET  /publications/:id} : get the "id" publications.
     *
     * @param id the id of the publications to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publications, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publications/{id}")
    public ResponseEntity<Publications> getPublications(@PathVariable Long id) {
        log.debug("REST request to get Publications : {}", id);
        Optional<Publications> publications = publicationsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(publications);
    }

    /**
     * {@code DELETE  /publications/:id} : delete the "id" publications.
     *
     * @param id the id of the publications to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/publications/{id}")
    public ResponseEntity<Void> deletePublications(@PathVariable Long id) {
        log.debug("REST request to delete Publications : {}", id);
        publicationsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
