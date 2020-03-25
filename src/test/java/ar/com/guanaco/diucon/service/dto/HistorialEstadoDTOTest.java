package ar.com.guanaco.diucon.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.guanaco.diucon.web.rest.TestUtil;

public class HistorialEstadoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistorialEstadoDTO.class);
        HistorialEstadoDTO historialEstadoDTO1 = new HistorialEstadoDTO();
        historialEstadoDTO1.setId(1L);
        HistorialEstadoDTO historialEstadoDTO2 = new HistorialEstadoDTO();
        assertThat(historialEstadoDTO1).isNotEqualTo(historialEstadoDTO2);
        historialEstadoDTO2.setId(historialEstadoDTO1.getId());
        assertThat(historialEstadoDTO1).isEqualTo(historialEstadoDTO2);
        historialEstadoDTO2.setId(2L);
        assertThat(historialEstadoDTO1).isNotEqualTo(historialEstadoDTO2);
        historialEstadoDTO1.setId(null);
        assertThat(historialEstadoDTO1).isNotEqualTo(historialEstadoDTO2);
    }
}
