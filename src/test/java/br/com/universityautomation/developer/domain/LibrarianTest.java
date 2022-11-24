package br.com.universityautomation.developer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.universityautomation.developer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LibrarianTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Librarian.class);
        Librarian librarian1 = new Librarian();
        librarian1.setId(1L);
        Librarian librarian2 = new Librarian();
        librarian2.setId(librarian1.getId());
        assertThat(librarian1).isEqualTo(librarian2);
        librarian2.setId(2L);
        assertThat(librarian1).isNotEqualTo(librarian2);
        librarian1.setId(null);
        assertThat(librarian1).isNotEqualTo(librarian2);
    }
}
