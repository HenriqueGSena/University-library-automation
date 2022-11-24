package br.com.universityautomation.developer.web.rest;

import br.com.universityautomation.developer.domain.Librarian;
import br.com.universityautomation.developer.repository.LibrarianRepository;
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
 * REST controller for managing {@link br.com.universityautomation.developer.domain.Librarian}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LibrarianResource {

    private final Logger log = LoggerFactory.getLogger(LibrarianResource.class);

    private static final String ENTITY_NAME = "librarian";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LibrarianRepository librarianRepository;

    public LibrarianResource(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    /**
     * {@code POST  /librarians} : Create a new librarian.
     *
     * @param librarian the librarian to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new librarian, or with status {@code 400 (Bad Request)} if the librarian has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/librarians")
    public ResponseEntity<Librarian> createLibrarian(@RequestBody Librarian librarian) throws URISyntaxException {
        log.debug("REST request to save Librarian : {}", librarian);
        if (librarian.getId() != null) {
            throw new BadRequestAlertException("A new librarian cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Librarian result = librarianRepository.save(librarian);
        return ResponseEntity
            .created(new URI("/api/librarians/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /librarians/:id} : Updates an existing librarian.
     *
     * @param id the id of the librarian to save.
     * @param librarian the librarian to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated librarian,
     * or with status {@code 400 (Bad Request)} if the librarian is not valid,
     * or with status {@code 500 (Internal Server Error)} if the librarian couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/librarians/{id}")
    public ResponseEntity<Librarian> updateLibrarian(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Librarian librarian
    ) throws URISyntaxException {
        log.debug("REST request to update Librarian : {}, {}", id, librarian);
        if (librarian.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, librarian.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!librarianRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Librarian result = librarianRepository.save(librarian);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, librarian.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /librarians/:id} : Partial updates given fields of an existing librarian, field will ignore if it is null
     *
     * @param id the id of the librarian to save.
     * @param librarian the librarian to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated librarian,
     * or with status {@code 400 (Bad Request)} if the librarian is not valid,
     * or with status {@code 404 (Not Found)} if the librarian is not found,
     * or with status {@code 500 (Internal Server Error)} if the librarian couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/librarians/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Librarian> partialUpdateLibrarian(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Librarian librarian
    ) throws URISyntaxException {
        log.debug("REST request to partial update Librarian partially : {}, {}", id, librarian);
        if (librarian.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, librarian.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!librarianRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Librarian> result = librarianRepository
            .findById(librarian.getId())
            .map(existingLibrarian -> {
                if (librarian.getEmail() != null) {
                    existingLibrarian.setEmail(librarian.getEmail());
                }

                return existingLibrarian;
            })
            .map(librarianRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, librarian.getId().toString())
        );
    }

    /**
     * {@code GET  /librarians} : get all the librarians.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of librarians in body.
     */
    @GetMapping("/librarians")
    public List<Librarian> getAllLibrarians() {
        log.debug("REST request to get all Librarians");
        return librarianRepository.findAll();
    }

    /**
     * {@code GET  /librarians/:id} : get the "id" librarian.
     *
     * @param id the id of the librarian to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the librarian, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/librarians/{id}")
    public ResponseEntity<Librarian> getLibrarian(@PathVariable Long id) {
        log.debug("REST request to get Librarian : {}", id);
        Optional<Librarian> librarian = librarianRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(librarian);
    }

    /**
     * {@code DELETE  /librarians/:id} : delete the "id" librarian.
     *
     * @param id the id of the librarian to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/librarians/{id}")
    public ResponseEntity<Void> deleteLibrarian(@PathVariable Long id) {
        log.debug("REST request to delete Librarian : {}", id);
        librarianRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
