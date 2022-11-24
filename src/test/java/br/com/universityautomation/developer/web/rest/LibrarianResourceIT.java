package br.com.universityautomation.developer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.universityautomation.developer.IntegrationTest;
import br.com.universityautomation.developer.domain.Librarian;
import br.com.universityautomation.developer.repository.LibrarianRepository;
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
 * Integration tests for the {@link LibrarianResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LibrarianResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/librarians";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLibrarianMockMvc;

    private Librarian librarian;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Librarian createEntity(EntityManager em) {
        Librarian librarian = new Librarian().email(DEFAULT_EMAIL);
        return librarian;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Librarian createUpdatedEntity(EntityManager em) {
        Librarian librarian = new Librarian().email(UPDATED_EMAIL);
        return librarian;
    }

    @BeforeEach
    public void initTest() {
        librarian = createEntity(em);
    }

    @Test
    @Transactional
    void createLibrarian() throws Exception {
        int databaseSizeBeforeCreate = librarianRepository.findAll().size();
        // Create the Librarian
        restLibrarianMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(librarian)))
            .andExpect(status().isCreated());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeCreate + 1);
        Librarian testLibrarian = librarianList.get(librarianList.size() - 1);
        assertThat(testLibrarian.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createLibrarianWithExistingId() throws Exception {
        // Create the Librarian with an existing ID
        librarian.setId(1L);

        int databaseSizeBeforeCreate = librarianRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibrarianMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(librarian)))
            .andExpect(status().isBadRequest());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLibrarians() throws Exception {
        // Initialize the database
        librarianRepository.saveAndFlush(librarian);

        // Get all the librarianList
        restLibrarianMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(librarian.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getLibrarian() throws Exception {
        // Initialize the database
        librarianRepository.saveAndFlush(librarian);

        // Get the librarian
        restLibrarianMockMvc
            .perform(get(ENTITY_API_URL_ID, librarian.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(librarian.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingLibrarian() throws Exception {
        // Get the librarian
        restLibrarianMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLibrarian() throws Exception {
        // Initialize the database
        librarianRepository.saveAndFlush(librarian);

        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();

        // Update the librarian
        Librarian updatedLibrarian = librarianRepository.findById(librarian.getId()).get();
        // Disconnect from session so that the updates on updatedLibrarian are not directly saved in db
        em.detach(updatedLibrarian);
        updatedLibrarian.email(UPDATED_EMAIL);

        restLibrarianMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLibrarian.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLibrarian))
            )
            .andExpect(status().isOk());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
        Librarian testLibrarian = librarianList.get(librarianList.size() - 1);
        assertThat(testLibrarian.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingLibrarian() throws Exception {
        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();
        librarian.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLibrarianMockMvc
            .perform(
                put(ENTITY_API_URL_ID, librarian.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(librarian))
            )
            .andExpect(status().isBadRequest());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLibrarian() throws Exception {
        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();
        librarian.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLibrarianMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(librarian))
            )
            .andExpect(status().isBadRequest());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLibrarian() throws Exception {
        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();
        librarian.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLibrarianMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(librarian)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLibrarianWithPatch() throws Exception {
        // Initialize the database
        librarianRepository.saveAndFlush(librarian);

        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();

        // Update the librarian using partial update
        Librarian partialUpdatedLibrarian = new Librarian();
        partialUpdatedLibrarian.setId(librarian.getId());

        restLibrarianMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLibrarian.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLibrarian))
            )
            .andExpect(status().isOk());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
        Librarian testLibrarian = librarianList.get(librarianList.size() - 1);
        assertThat(testLibrarian.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateLibrarianWithPatch() throws Exception {
        // Initialize the database
        librarianRepository.saveAndFlush(librarian);

        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();

        // Update the librarian using partial update
        Librarian partialUpdatedLibrarian = new Librarian();
        partialUpdatedLibrarian.setId(librarian.getId());

        partialUpdatedLibrarian.email(UPDATED_EMAIL);

        restLibrarianMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLibrarian.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLibrarian))
            )
            .andExpect(status().isOk());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
        Librarian testLibrarian = librarianList.get(librarianList.size() - 1);
        assertThat(testLibrarian.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingLibrarian() throws Exception {
        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();
        librarian.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLibrarianMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, librarian.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(librarian))
            )
            .andExpect(status().isBadRequest());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLibrarian() throws Exception {
        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();
        librarian.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLibrarianMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(librarian))
            )
            .andExpect(status().isBadRequest());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLibrarian() throws Exception {
        int databaseSizeBeforeUpdate = librarianRepository.findAll().size();
        librarian.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLibrarianMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(librarian))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Librarian in the database
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLibrarian() throws Exception {
        // Initialize the database
        librarianRepository.saveAndFlush(librarian);

        int databaseSizeBeforeDelete = librarianRepository.findAll().size();

        // Delete the librarian
        restLibrarianMockMvc
            .perform(delete(ENTITY_API_URL_ID, librarian.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Librarian> librarianList = librarianRepository.findAll();
        assertThat(librarianList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
