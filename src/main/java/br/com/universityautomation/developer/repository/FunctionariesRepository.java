package br.com.universityautomation.developer.repository;

import br.com.universityautomation.developer.domain.Functionaries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Functionaries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctionariesRepository extends JpaRepository<Functionaries, Long> {}
