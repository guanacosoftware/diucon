package ar.com.guanaco.diucon.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HistorialEstadoMapperTest {

    private HistorialEstadoMapper historialEstadoMapper;

    @BeforeEach
    public void setUp() {
        historialEstadoMapper = new HistorialEstadoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(historialEstadoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(historialEstadoMapper.fromId(null)).isNull();
    }
}
