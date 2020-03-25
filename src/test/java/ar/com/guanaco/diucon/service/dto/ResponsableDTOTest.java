package ar.com.guanaco.diucon.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.guanaco.diucon.web.rest.TestUtil;

public class ResponsableDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsableDTO.class);
        ResponsableDTO responsableDTO1 = new ResponsableDTO();
        responsableDTO1.setId(1L);
        ResponsableDTO responsableDTO2 = new ResponsableDTO();
        assertThat(responsableDTO1).isNotEqualTo(responsableDTO2);
        responsableDTO2.setId(responsableDTO1.getId());
        assertThat(responsableDTO1).isEqualTo(responsableDTO2);
        responsableDTO2.setId(2L);
        assertThat(responsableDTO1).isNotEqualTo(responsableDTO2);
        responsableDTO1.setId(null);
        assertThat(responsableDTO1).isNotEqualTo(responsableDTO2);
    }
}
