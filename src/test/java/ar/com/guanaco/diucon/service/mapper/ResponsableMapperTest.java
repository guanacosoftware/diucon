package ar.com.guanaco.diucon.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ResponsableMapperTest {

    private ResponsableMapper responsableMapper;

    @BeforeEach
    public void setUp() {
        responsableMapper = new ResponsableMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(responsableMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(responsableMapper.fromId(null)).isNull();
    }
}
