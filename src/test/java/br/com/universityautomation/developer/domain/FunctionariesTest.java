package br.com.universityautomation.developer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.universityautomation.developer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FunctionariesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Functionaries.class);
        Functionaries functionaries1 = new Functionaries();
        functionaries1.setId(1L);
        Functionaries functionaries2 = new Functionaries();
        functionaries2.setId(functionaries1.getId());
        assertThat(functionaries1).isEqualTo(functionaries2);
        functionaries2.setId(2L);
        assertThat(functionaries1).isNotEqualTo(functionaries2);
        functionaries1.setId(null);
        assertThat(functionaries1).isNotEqualTo(functionaries2);
    }
}
