package br.com.universityautomation.developer.repository;

import br.com.universityautomation.developer.domain.Publications;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Publications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationsRepository extends JpaRepository<Publications, Long> {}
