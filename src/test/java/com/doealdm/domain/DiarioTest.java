package com.doealdm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.doealdm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diario.class);
        Diario diario1 = new Diario();
        diario1.setId(1L);
        Diario diario2 = new Diario();
        diario2.setId(diario1.getId());
        assertThat(diario1).isEqualTo(diario2);
        diario2.setId(2L);
        assertThat(diario1).isNotEqualTo(diario2);
        diario1.setId(null);
        assertThat(diario1).isNotEqualTo(diario2);
    }
}
