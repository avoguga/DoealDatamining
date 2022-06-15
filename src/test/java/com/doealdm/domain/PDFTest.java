package com.doealdm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.doealdm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PDFTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PDF.class);
        PDF pDF1 = new PDF();
        pDF1.setId(1L);
        PDF pDF2 = new PDF();
        pDF2.setId(pDF1.getId());
        assertThat(pDF1).isEqualTo(pDF2);
        pDF2.setId(2L);
        assertThat(pDF1).isNotEqualTo(pDF2);
        pDF1.setId(null);
        assertThat(pDF1).isNotEqualTo(pDF2);
    }
}
