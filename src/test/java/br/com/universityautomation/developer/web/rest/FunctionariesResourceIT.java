package br.com.universityautomation.developer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.universityautomation.developer.IntegrationTest;
import br.com.universityautomation.developer.domain.Functionaries;
import br.com.universityautomation.developer.repository.FunctionariesRepository;
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
 * Integration tests for the {@link FunctionariesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FunctionariesResourceIT {

    private static final String DEFAULT_SETOR = "AAAAAAAAAA";
    private static final String UPDATED_SETOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/functionaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FunctionariesRepository functionariesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFunctionariesMockMvc;

    private Functionaries functionaries;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functionaries createEntity(EntityManager em) {
        Functionaries functionaries = new Functionaries().setor(DEFAULT_SETOR);
        return functionaries;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functionaries createUpdatedEntity(EntityManager em) {
        Functionaries functionaries = new Functionaries().setor(UPDATED_SETOR);
        return functionaries;
    }

    @BeforeEach
    public void initTest() {
        functionaries = createEntity(em);
    }

    @Test
    @Transactional
    void createFunctionaries() throws Exception {
        int databaseSizeBeforeCreate = functionariesRepository.findAll().size();
        // Create the Functionaries
        restFunctionariesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionaries)))
            .andExpect(status().isCreated());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeCreate + 1);
        Functionaries testFunctionaries = functionariesList.get(functionariesList.size() - 1);
        assertThat(testFunctionaries.getSetor()).isEqualTo(DEFAULT_SETOR);
    }

    @Test
    @Transactional
    void createFunctionariesWithExistingId() throws Exception {
        // Create the Functionaries with an existing ID
        functionaries.setId(1L);

        int databaseSizeBeforeCreate = functionariesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctionariesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionaries)))
            .andExpect(status().isBadRequest());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFunctionaries() throws Exception {
        // Initialize the database
        functionariesRepository.saveAndFlush(functionaries);

        // Get all the functionariesList
        restFunctionariesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functionaries.getId().intValue())))
            .andExpect(jsonPath("$.[*].setor").value(hasItem(DEFAULT_SETOR)));
    }

    @Test
    @Transactional
    void getFunctionaries() throws Exception {
        // Initialize the database
        functionariesRepository.saveAndFlush(functionaries);

        // Get the functionaries
        restFunctionariesMockMvc
            .perform(get(ENTITY_API_URL_ID, functionaries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(functionaries.getId().intValue()))
            .andExpect(jsonPath("$.setor").value(DEFAULT_SETOR));
    }

    @Test
    @Transactional
    void getNonExistingFunctionaries() throws Exception {
        // Get the functionaries
        restFunctionariesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFunctionaries() throws Exception {
        // Initialize the database
        functionariesRepository.saveAndFlush(functionaries);

        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();

        // Update the functionaries
        Functionaries updatedFunctionaries = functionariesRepository.findById(functionaries.getId()).get();
        // Disconnect from session so that the updates on updatedFunctionaries are not directly saved in db
        em.detach(updatedFunctionaries);
        updatedFunctionaries.setor(UPDATED_SETOR);

        restFunctionariesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFunctionaries.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFunctionaries))
            )
            .andExpect(status().isOk());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
        Functionaries testFunctionaries = functionariesList.get(functionariesList.size() - 1);
        assertThat(testFunctionaries.getSetor()).isEqualTo(UPDATED_SETOR);
    }

    @Test
    @Transactional
    void putNonExistingFunctionaries() throws Exception {
        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();
        functionaries.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionariesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, functionaries.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionaries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFunctionaries() throws Exception {
        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();
        functionaries.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionariesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionaries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFunctionaries() throws Exception {
        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();
        functionaries.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionariesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionaries)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFunctionariesWithPatch() throws Exception {
        // Initialize the database
        functionariesRepository.saveAndFlush(functionaries);

        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();

        // Update the functionaries using partial update
        Functionaries partialUpdatedFunctionaries = new Functionaries();
        partialUpdatedFunctionaries.setId(functionaries.getId());

        partialUpdatedFunctionaries.setor(UPDATED_SETOR);

        restFunctionariesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctionaries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunctionaries))
            )
            .andExpect(status().isOk());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
        Functionaries testFunctionaries = functionariesList.get(functionariesList.size() - 1);
        assertThat(testFunctionaries.getSetor()).isEqualTo(UPDATED_SETOR);
    }

    @Test
    @Transactional
    void fullUpdateFunctionariesWithPatch() throws Exception {
        // Initialize the database
        functionariesRepository.saveAndFlush(functionaries);

        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();

        // Update the functionaries using partial update
        Functionaries partialUpdatedFunctionaries = new Functionaries();
        partialUpdatedFunctionaries.setId(functionaries.getId());

        partialUpdatedFunctionaries.setor(UPDATED_SETOR);

        restFunctionariesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctionaries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunctionaries))
            )
            .andExpect(status().isOk());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
        Functionaries testFunctionaries = functionariesList.get(functionariesList.size() - 1);
        assertThat(testFunctionaries.getSetor()).isEqualTo(UPDATED_SETOR);
    }

    @Test
    @Transactional
    void patchNonExistingFunctionaries() throws Exception {
        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();
        functionaries.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionariesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, functionaries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionaries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFunctionaries() throws Exception {
        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();
        functionaries.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionariesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionaries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFunctionaries() throws Exception {
        int databaseSizeBeforeUpdate = functionariesRepository.findAll().size();
        functionaries.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionariesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(functionaries))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functionaries in the database
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFunctionaries() throws Exception {
        // Initialize the database
        functionariesRepository.saveAndFlush(functionaries);

        int databaseSizeBeforeDelete = functionariesRepository.findAll().size();

        // Delete the functionaries
        restFunctionariesMockMvc
            .perform(delete(ENTITY_API_URL_ID, functionaries.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Functionaries> functionariesList = functionariesRepository.findAll();
        assertThat(functionariesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
