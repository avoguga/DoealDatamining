package com.doealdm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.doealdm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConcessaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concessao.class);
        Concessao concessao1 = new Concessao();
        concessao1.setId(1L);
        Concessao concessao2 = new Concessao();
        concessao2.setId(concessao1.getId());
        assertThat(concessao1).isEqualTo(concessao2);
        concessao2.setId(2L);
        assertThat(concessao1).isNotEqualTo(concessao2);
        concessao1.setId(null);
        assertThat(concessao1).isNotEqualTo(concessao2);
    }
}
