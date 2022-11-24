package br.com.universityautomation.developer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.universityautomation.developer.IntegrationTest;
import br.com.universityautomation.developer.domain.Emprestimo;
import br.com.universityautomation.developer.repository.EmprestimoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmprestimoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmprestimoResourceIT {

    private static final String ENTITY_API_URL = "/api/emprestimos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private EmprestimoRepository emprestimoRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmprestimoMockMvc;

    private Emprestimo emprestimo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emprestimo createEntity(EntityManager em) {
        Emprestimo emprestimo = new Emprestimo();
        return emprestimo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emprestimo createUpdatedEntity(EntityManager em) {
        Emprestimo emprestimo = new Emprestimo();
        return emprestimo;
    }

    @BeforeEach
    public void initTest() {
        emprestimo = createEntity(em);
    }

    @Test
    @Transactional
    void createEmprestimo() throws Exception {
        int databaseSizeBeforeCreate = emprestimoRepository.findAll().size();
        // Create the Emprestimo
        restEmprestimoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emprestimo)))
            .andExpect(status().isCreated());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeCreate + 1);
        Emprestimo testEmprestimo = emprestimoList.get(emprestimoList.size() - 1);
    }

    @Test
    @Transactional
    void createEmprestimoWithExistingId() throws Exception {
        // Create the Emprestimo with an existing ID
        emprestimo.setId(1L);

        int databaseSizeBeforeCreate = emprestimoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmprestimoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emprestimo)))
            .andExpect(status().isBadRequest());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmprestimos() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList
        restEmprestimoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emprestimo.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmprestimosWithEagerRelationshipsIsEnabled() throws Exception {
        when(emprestimoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmprestimoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(emprestimoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmprestimosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(emprestimoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmprestimoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(emprestimoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmprestimo() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get the emprestimo
        restEmprestimoMockMvc
            .perform(get(ENTITY_API_URL_ID, emprestimo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emprestimo.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEmprestimo() throws Exception {
        // Get the emprestimo
        restEmprestimoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmprestimo() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();

        // Update the emprestimo
        Emprestimo updatedEmprestimo = emprestimoRepository.findById(emprestimo.getId()).get();
        // Disconnect from session so that the updates on updatedEmprestimo are not directly saved in db
        em.detach(updatedEmprestimo);

        restEmprestimoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmprestimo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmprestimo))
            )
            .andExpect(status().isOk());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
        Emprestimo testEmprestimo = emprestimoList.get(emprestimoList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingEmprestimo() throws Exception {
        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();
        emprestimo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmprestimoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emprestimo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emprestimo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmprestimo() throws Exception {
        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();
        emprestimo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmprestimoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emprestimo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmprestimo() throws Exception {
        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();
        emprestimo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmprestimoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emprestimo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmprestimoWithPatch() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();

        // Update the emprestimo using partial update
        Emprestimo partialUpdatedEmprestimo = new Emprestimo();
        partialUpdatedEmprestimo.setId(emprestimo.getId());

        restEmprestimoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmprestimo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmprestimo))
            )
            .andExpect(status().isOk());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
        Emprestimo testEmprestimo = emprestimoList.get(emprestimoList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateEmprestimoWithPatch() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();

        // Update the emprestimo using partial update
        Emprestimo partialUpdatedEmprestimo = new Emprestimo();
        partialUpdatedEmprestimo.setId(emprestimo.getId());

        restEmprestimoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmprestimo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmprestimo))
            )
            .andExpect(status().isOk());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
        Emprestimo testEmprestimo = emprestimoList.get(emprestimoList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingEmprestimo() throws Exception {
        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();
        emprestimo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmprestimoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emprestimo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emprestimo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmprestimo() throws Exception {
        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();
        emprestimo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmprestimoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emprestimo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmprestimo() throws Exception {
        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();
        emprestimo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmprestimoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emprestimo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmprestimo() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        int databaseSizeBeforeDelete = emprestimoRepository.findAll().size();

        // Delete the emprestimo
        restEmprestimoMockMvc
            .perform(delete(ENTITY_API_URL_ID, emprestimo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
