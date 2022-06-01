package com.doealdm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.doealdm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServidorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servidor.class);
        Servidor servidor1 = new Servidor();
        servidor1.setId(1L);
        Servidor servidor2 = new Servidor();
        servidor2.setId(servidor1.getId());
        assertThat(servidor1).isEqualTo(servidor2);
        servidor2.setId(2L);
        assertThat(servidor1).isNotEqualTo(servidor2);
        servidor1.setId(null);
        assertThat(servidor1).isNotEqualTo(servidor2);
    }
}
