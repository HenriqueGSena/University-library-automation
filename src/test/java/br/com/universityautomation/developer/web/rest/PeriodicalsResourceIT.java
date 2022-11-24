package br.com.universityautomation.developer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.universityautomation.developer.IntegrationTest;
import br.com.universityautomation.developer.domain.Periodicals;
import br.com.universityautomation.developer.repository.PeriodicalsRepository;
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
 * Integration tests for the {@link PeriodicalsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PeriodicalsResourceIT {

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;

    private static final Double DEFAULT_NUMERO = 1D;
    private static final Double UPDATED_NUMERO = 2D;

    private static final String DEFAULT_ISSN = "AAAAAAAAAA";
    private static final String UPDATED_ISSN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/periodicals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PeriodicalsRepository periodicalsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodicalsMockMvc;

    private Periodicals periodicals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodicals createEntity(EntityManager em) {
        Periodicals periodicals = new Periodicals().volume(DEFAULT_VOLUME).numero(DEFAULT_NUMERO).issn(DEFAULT_ISSN);
        return periodicals;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodicals createUpdatedEntity(EntityManager em) {
        Periodicals periodicals = new Periodicals().volume(UPDATED_VOLUME).numero(UPDATED_NUMERO).issn(UPDATED_ISSN);
        return periodicals;
    }

    @BeforeEach
    public void initTest() {
        periodicals = createEntity(em);
    }

    @Test
    @Transactional
    void createPeriodicals() throws Exception {
        int databaseSizeBeforeCreate = periodicalsRepository.findAll().size();
        // Create the Periodicals
        restPeriodicalsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodicals)))
            .andExpect(status().isCreated());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeCreate + 1);
        Periodicals testPeriodicals = periodicalsList.get(periodicalsList.size() - 1);
        assertThat(testPeriodicals.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testPeriodicals.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testPeriodicals.getIssn()).isEqualTo(DEFAULT_ISSN);
    }

    @Test
    @Transactional
    void createPeriodicalsWithExistingId() throws Exception {
        // Create the Periodicals with an existing ID
        periodicals.setId(1L);

        int databaseSizeBeforeCreate = periodicalsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodicalsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodicals)))
            .andExpect(status().isBadRequest());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeriodicals() throws Exception {
        // Initialize the database
        periodicalsRepository.saveAndFlush(periodicals);

        // Get all the periodicalsList
        restPeriodicalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodicals.getId().intValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.doubleValue())))
            .andExpect(jsonPath("$.[*].issn").value(hasItem(DEFAULT_ISSN)));
    }

    @Test
    @Transactional
    void getPeriodicals() throws Exception {
        // Initialize the database
        periodicalsRepository.saveAndFlush(periodicals);

        // Get the periodicals
        restPeriodicalsMockMvc
            .perform(get(ENTITY_API_URL_ID, periodicals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodicals.getId().intValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.doubleValue()))
            .andExpect(jsonPath("$.issn").value(DEFAULT_ISSN));
    }

    @Test
    @Transactional
    void getNonExistingPeriodicals() throws Exception {
        // Get the periodicals
        restPeriodicalsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPeriodicals() throws Exception {
        // Initialize the database
        periodicalsRepository.saveAndFlush(periodicals);

        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();

        // Update the periodicals
        Periodicals updatedPeriodicals = periodicalsRepository.findById(periodicals.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodicals are not directly saved in db
        em.detach(updatedPeriodicals);
        updatedPeriodicals.volume(UPDATED_VOLUME).numero(UPDATED_NUMERO).issn(UPDATED_ISSN);

        restPeriodicalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPeriodicals.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPeriodicals))
            )
            .andExpect(status().isOk());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
        Periodicals testPeriodicals = periodicalsList.get(periodicalsList.size() - 1);
        assertThat(testPeriodicals.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPeriodicals.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testPeriodicals.getIssn()).isEqualTo(UPDATED_ISSN);
    }

    @Test
    @Transactional
    void putNonExistingPeriodicals() throws Exception {
        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();
        periodicals.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodicalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodicals.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodicals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeriodicals() throws Exception {
        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();
        periodicals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodicalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodicals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeriodicals() throws Exception {
        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();
        periodicals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodicalsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodicals)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeriodicalsWithPatch() throws Exception {
        // Initialize the database
        periodicalsRepository.saveAndFlush(periodicals);

        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();

        // Update the periodicals using partial update
        Periodicals partialUpdatedPeriodicals = new Periodicals();
        partialUpdatedPeriodicals.setId(periodicals.getId());

        partialUpdatedPeriodicals.issn(UPDATED_ISSN);

        restPeriodicalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodicals.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodicals))
            )
            .andExpect(status().isOk());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
        Periodicals testPeriodicals = periodicalsList.get(periodicalsList.size() - 1);
        assertThat(testPeriodicals.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testPeriodicals.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testPeriodicals.getIssn()).isEqualTo(UPDATED_ISSN);
    }

    @Test
    @Transactional
    void fullUpdatePeriodicalsWithPatch() throws Exception {
        // Initialize the database
        periodicalsRepository.saveAndFlush(periodicals);

        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();

        // Update the periodicals using partial update
        Periodicals partialUpdatedPeriodicals = new Periodicals();
        partialUpdatedPeriodicals.setId(periodicals.getId());

        partialUpdatedPeriodicals.volume(UPDATED_VOLUME).numero(UPDATED_NUMERO).issn(UPDATED_ISSN);

        restPeriodicalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodicals.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodicals))
            )
            .andExpect(status().isOk());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
        Periodicals testPeriodicals = periodicalsList.get(periodicalsList.size() - 1);
        assertThat(testPeriodicals.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPeriodicals.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testPeriodicals.getIssn()).isEqualTo(UPDATED_ISSN);
    }

    @Test
    @Transactional
    void patchNonExistingPeriodicals() throws Exception {
        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();
        periodicals.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodicalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periodicals.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodicals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeriodicals() throws Exception {
        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();
        periodicals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodicalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodicals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeriodicals() throws Exception {
        int databaseSizeBeforeUpdate = periodicalsRepository.findAll().size();
        periodicals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodicalsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(periodicals))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Periodicals in the database
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeriodicals() throws Exception {
        // Initialize the database
        periodicalsRepository.saveAndFlush(periodicals);

        int databaseSizeBeforeDelete = periodicalsRepository.findAll().size();

        // Delete the periodicals
        restPeriodicalsMockMvc
            .perform(delete(ENTITY_API_URL_ID, periodicals.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Periodicals> periodicalsList = periodicalsRepository.findAll();
        assertThat(periodicalsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
