package br.com.universityautomation.developer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.universityautomation.developer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publications.class);
        Publications publications1 = new Publications();
        publications1.setId(1L);
        Publications publications2 = new Publications();
        publications2.setId(publications1.getId());
        assertThat(publications1).isEqualTo(publications2);
        publications2.setId(2L);
        assertThat(publications1).isNotEqualTo(publications2);
        publications1.setId(null);
        assertThat(publications1).isNotEqualTo(publications2);
    }
}
