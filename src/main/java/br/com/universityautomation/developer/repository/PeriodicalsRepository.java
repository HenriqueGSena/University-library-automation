package br.com.universityautomation.developer.repository;

import br.com.universityautomation.developer.domain.Periodicals;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Periodicals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodicalsRepository extends JpaRepository<Periodicals, Long> {}
