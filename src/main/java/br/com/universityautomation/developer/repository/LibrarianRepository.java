package br.com.universityautomation.developer.repository;

import br.com.universityautomation.developer.domain.Librarian;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Librarian entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long> {}
