package ar.com.guanaco.diucon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.guanaco.diucon.web.rest.TestUtil;

public class PlantillaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plantilla.class);
        Plantilla plantilla1 = new Plantilla();
        plantilla1.setId(1L);
        Plantilla plantilla2 = new Plantilla();
        plantilla2.setId(plantilla1.getId());
        assertThat(plantilla1).isEqualTo(plantilla2);
        plantilla2.setId(2L);
        assertThat(plantilla1).isNotEqualTo(plantilla2);
        plantilla1.setId(null);
        assertThat(plantilla1).isNotEqualTo(plantilla2);
    }
}
