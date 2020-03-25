package ar.com.guanaco.diucon.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.guanaco.diucon.web.rest.TestUtil;

public class ComentarioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentarioDTO.class);
        ComentarioDTO comentarioDTO1 = new ComentarioDTO();
        comentarioDTO1.setId(1L);
        ComentarioDTO comentarioDTO2 = new ComentarioDTO();
        assertThat(comentarioDTO1).isNotEqualTo(comentarioDTO2);
        comentarioDTO2.setId(comentarioDTO1.getId());
        assertThat(comentarioDTO1).isEqualTo(comentarioDTO2);
        comentarioDTO2.setId(2L);
        assertThat(comentarioDTO1).isNotEqualTo(comentarioDTO2);
        comentarioDTO1.setId(null);
        assertThat(comentarioDTO1).isNotEqualTo(comentarioDTO2);
    }
}
