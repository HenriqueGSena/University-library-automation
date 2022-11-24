package br.com.universityautomation.developer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.universityautomation.developer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodicalsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periodicals.class);
        Periodicals periodicals1 = new Periodicals();
        periodicals1.setId(1L);
        Periodicals periodicals2 = new Periodicals();
        periodicals2.setId(periodicals1.getId());
        assertThat(periodicals1).isEqualTo(periodicals2);
        periodicals2.setId(2L);
        assertThat(periodicals1).isNotEqualTo(periodicals2);
        periodicals1.setId(null);
        assertThat(periodicals1).isNotEqualTo(periodicals2);
    }
}
