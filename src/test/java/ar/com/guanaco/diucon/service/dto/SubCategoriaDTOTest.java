package ar.com.guanaco.diucon.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.guanaco.diucon.web.rest.TestUtil;

public class SubCategoriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategoriaDTO.class);
        SubCategoriaDTO subCategoriaDTO1 = new SubCategoriaDTO();
        subCategoriaDTO1.setId(1L);
        SubCategoriaDTO subCategoriaDTO2 = new SubCategoriaDTO();
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
        subCategoriaDTO2.setId(subCategoriaDTO1.getId());
        assertThat(subCategoriaDTO1).isEqualTo(subCategoriaDTO2);
        subCategoriaDTO2.setId(2L);
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
        subCategoriaDTO1.setId(null);
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
    }
}
