package br.com.universityautomation.developer.repository;

import br.com.universityautomation.developer.domain.Emprestimo;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class EmprestimoRepositoryWithBagRelationshipsImpl implements EmprestimoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Emprestimo> fetchBagRelationships(Optional<Emprestimo> emprestimo) {
        return emprestimo.map(this::fetchPublications).map(this::fetchRegistrations);
    }

    @Override
    public Page<Emprestimo> fetchBagRelationships(Page<Emprestimo> emprestimos) {
        return new PageImpl<>(fetchBagRelationships(emprestimos.getContent()), emprestimos.getPageable(), emprestimos.getTotalElements());
    }

    @Override
    public List<Emprestimo> fetchBagRelationships(List<Emprestimo> emprestimos) {
        return Optional.of(emprestimos).map(this::fetchPublications).map(this::fetchRegistrations).orElse(Collections.emptyList());
    }

    Emprestimo fetchPublications(Emprestimo result) {
        return entityManager
            .createQuery(
                "select emprestimo from Emprestimo emprestimo left join fetch emprestimo.publications where emprestimo is :emprestimo",
                Emprestimo.class
            )
            .setParameter("emprestimo", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Emprestimo> fetchPublications(List<Emprestimo> emprestimos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, emprestimos.size()).forEach(index -> order.put(emprestimos.get(index).getId(), index));
        List<Emprestimo> result = entityManager
            .createQuery(
                "select distinct emprestimo from Emprestimo emprestimo left join fetch emprestimo.publications where emprestimo in :emprestimos",
                Emprestimo.class
            )
            .setParameter("emprestimos", emprestimos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Emprestimo fetchRegistrations(Emprestimo result) {
        return entityManager
            .createQuery(
                "select emprestimo from Emprestimo emprestimo left join fetch emprestimo.registrations where emprestimo is :emprestimo",
                Emprestimo.class
            )
            .setParameter("emprestimo", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Emprestimo> fetchRegistrations(List<Emprestimo> emprestimos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, emprestimos.size()).forEach(index -> order.put(emprestimos.get(index).getId(), index));
        List<Emprestimo> result = entityManager
            .createQuery(
                "select distinct emprestimo from Emprestimo emprestimo left join fetch emprestimo.registrations where emprestimo in :emprestimos",
                Emprestimo.class
            )
            .setParameter("emprestimos", emprestimos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
