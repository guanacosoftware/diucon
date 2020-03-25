package ar.com.guanaco.diucon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.guanaco.diucon.web.rest.TestUtil;

public class SubCategoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategoria.class);
        SubCategoria subCategoria1 = new SubCategoria();
        subCategoria1.setId(1L);
        SubCategoria subCategoria2 = new SubCategoria();
        subCategoria2.setId(subCategoria1.getId());
        assertThat(subCategoria1).isEqualTo(subCategoria2);
        subCategoria2.setId(2L);
        assertThat(subCategoria1).isNotEqualTo(subCategoria2);
        subCategoria1.setId(null);
        assertThat(subCategoria1).isNotEqualTo(subCategoria2);
    }
}
