package br.com.universityautomation.developer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.universityautomation.developer.IntegrationTest;
import br.com.universityautomation.developer.domain.Publications;
import br.com.universityautomation.developer.domain.enumeration.Status;
import br.com.universityautomation.developer.repository.PublicationsRepository;
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
 * Integration tests for the {@link PublicationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicationsResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECO = 1D;
    private static final Double UPDATED_PRECO = 2D;

    private static final String DEFAULT_DATA_PUBLICACAO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_PUBLICACAO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.DISPONIVEL;
    private static final Status UPDATED_STATUS = Status.EMPRESTADO;

    private static final String ENTITY_API_URL = "/api/publications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PublicationsRepository publicationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicationsMockMvc;

    private Publications publications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publications createEntity(EntityManager em) {
        Publications publications = new Publications()
            .titulo(DEFAULT_TITULO)
            .preco(DEFAULT_PRECO)
            .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
            .status(DEFAULT_STATUS);
        return publications;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publications createUpdatedEntity(EntityManager em) {
        Publications publications = new Publications()
            .titulo(UPDATED_TITULO)
            .preco(UPDATED_PRECO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .status(UPDATED_STATUS);
        return publications;
    }

    @BeforeEach
    public void initTest() {
        publications = createEntity(em);
    }

    @Test
    @Transactional
    void createPublications() throws Exception {
        int databaseSizeBeforeCreate = publicationsRepository.findAll().size();
        // Create the Publications
        restPublicationsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publications)))
            .andExpect(status().isCreated());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeCreate + 1);
        Publications testPublications = publicationsList.get(publicationsList.size() - 1);
        assertThat(testPublications.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPublications.getPreco()).isEqualTo(DEFAULT_PRECO);
        assertThat(testPublications.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
        assertThat(testPublications.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createPublicationsWithExistingId() throws Exception {
        // Create the Publications with an existing ID
        publications.setId(1L);

        int databaseSizeBeforeCreate = publicationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publications)))
            .andExpect(status().isBadRequest());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublications() throws Exception {
        // Initialize the database
        publicationsRepository.saveAndFlush(publications);

        // Get all the publicationsList
        restPublicationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publications.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.doubleValue())))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getPublications() throws Exception {
        // Initialize the database
        publicationsRepository.saveAndFlush(publications);

        // Get the publications
        restPublicationsMockMvc
            .perform(get(ENTITY_API_URL_ID, publications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publications.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.doubleValue()))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPublications() throws Exception {
        // Get the publications
        restPublicationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPublications() throws Exception {
        // Initialize the database
        publicationsRepository.saveAndFlush(publications);

        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();

        // Update the publications
        Publications updatedPublications = publicationsRepository.findById(publications.getId()).get();
        // Disconnect from session so that the updates on updatedPublications are not directly saved in db
        em.detach(updatedPublications);
        updatedPublications.titulo(UPDATED_TITULO).preco(UPDATED_PRECO).dataPublicacao(UPDATED_DATA_PUBLICACAO).status(UPDATED_STATUS);

        restPublicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPublications.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPublications))
            )
            .andExpect(status().isOk());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
        Publications testPublications = publicationsList.get(publicationsList.size() - 1);
        assertThat(testPublications.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPublications.getPreco()).isEqualTo(UPDATED_PRECO);
        assertThat(testPublications.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testPublications.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPublications() throws Exception {
        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();
        publications.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publications.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publications))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublications() throws Exception {
        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();
        publications.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publications))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublications() throws Exception {
        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();
        publications.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publications)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicationsWithPatch() throws Exception {
        // Initialize the database
        publicationsRepository.saveAndFlush(publications);

        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();

        // Update the publications using partial update
        Publications partialUpdatedPublications = new Publications();
        partialUpdatedPublications.setId(publications.getId());

        partialUpdatedPublications.preco(UPDATED_PRECO).dataPublicacao(UPDATED_DATA_PUBLICACAO);

        restPublicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublications))
            )
            .andExpect(status().isOk());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
        Publications testPublications = publicationsList.get(publicationsList.size() - 1);
        assertThat(testPublications.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPublications.getPreco()).isEqualTo(UPDATED_PRECO);
        assertThat(testPublications.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testPublications.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePublicationsWithPatch() throws Exception {
        // Initialize the database
        publicationsRepository.saveAndFlush(publications);

        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();

        // Update the publications using partial update
        Publications partialUpdatedPublications = new Publications();
        partialUpdatedPublications.setId(publications.getId());

        partialUpdatedPublications
            .titulo(UPDATED_TITULO)
            .preco(UPDATED_PRECO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .status(UPDATED_STATUS);

        restPublicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublications))
            )
            .andExpect(status().isOk());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
        Publications testPublications = publicationsList.get(publicationsList.size() - 1);
        assertThat(testPublications.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPublications.getPreco()).isEqualTo(UPDATED_PRECO);
        assertThat(testPublications.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testPublications.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPublications() throws Exception {
        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();
        publications.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publications))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublications() throws Exception {
        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();
        publications.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publications))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublications() throws Exception {
        int databaseSizeBeforeUpdate = publicationsRepository.findAll().size();
        publications.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(publications))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publications in the database
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublications() throws Exception {
        // Initialize the database
        publicationsRepository.saveAndFlush(publications);

        int databaseSizeBeforeDelete = publicationsRepository.findAll().size();

        // Delete the publications
        restPublicationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, publications.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Publications> publicationsList = publicationsRepository.findAll();
        assertThat(publicationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
