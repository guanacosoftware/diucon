package ar.com.guanaco.diucon.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.guanaco.diucon.web.rest.TestUtil;

public class PlantillaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantillaDTO.class);
        PlantillaDTO plantillaDTO1 = new PlantillaDTO();
        plantillaDTO1.setId(1L);
        PlantillaDTO plantillaDTO2 = new PlantillaDTO();
        assertThat(plantillaDTO1).isNotEqualTo(plantillaDTO2);
        plantillaDTO2.setId(plantillaDTO1.getId());
        assertThat(plantillaDTO1).isEqualTo(plantillaDTO2);
        plantillaDTO2.setId(2L);
        assertThat(plantillaDTO1).isNotEqualTo(plantillaDTO2);
        plantillaDTO1.setId(null);
        assertThat(plantillaDTO1).isNotEqualTo(plantillaDTO2);
    }
}
