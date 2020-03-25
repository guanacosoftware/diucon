package ar.com.guanaco.diucon.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ComentarioMapperTest {

    private ComentarioMapper comentarioMapper;

    @BeforeEach
    public void setUp() {
        comentarioMapper = new ComentarioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(comentarioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(comentarioMapper.fromId(null)).isNull();
    }
}
