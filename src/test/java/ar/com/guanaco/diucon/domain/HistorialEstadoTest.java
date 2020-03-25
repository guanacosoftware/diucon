package ar.com.guanaco.diucon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.guanaco.diucon.web.rest.TestUtil;

public class HistorialEstadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistorialEstado.class);
        HistorialEstado historialEstado1 = new HistorialEstado();
        historialEstado1.setId(1L);
        HistorialEstado historialEstado2 = new HistorialEstado();
        historialEstado2.setId(historialEstado1.getId());
        assertThat(historialEstado1).isEqualTo(historialEstado2);
        historialEstado2.setId(2L);
        assertThat(historialEstado1).isNotEqualTo(historialEstado2);
        historialEstado1.setId(null);
        assertThat(historialEstado1).isNotEqualTo(historialEstado2);
    }
}
