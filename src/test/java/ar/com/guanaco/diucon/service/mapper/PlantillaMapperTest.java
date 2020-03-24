package ar.com.guanaco.diucon.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlantillaMapperTest {

    private PlantillaMapper plantillaMapper;

    @BeforeEach
    public void setUp() {
        plantillaMapper = new PlantillaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(plantillaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(plantillaMapper.fromId(null)).isNull();
    }
}
