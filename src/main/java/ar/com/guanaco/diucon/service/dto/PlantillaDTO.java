package ar.com.guanaco.diucon.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link ar.com.guanaco.diucon.domain.Plantilla} entity.
 */
public class PlantillaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nombre;

    
    @Lob
    private String cuerpo;

    private Set<SubCategoriaDTO> subcategorias = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Set<SubCategoriaDTO> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(Set<SubCategoriaDTO> subCategorias) {
        this.subcategorias = subCategorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlantillaDTO plantillaDTO = (PlantillaDTO) o;
        if (plantillaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plantillaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlantillaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", cuerpo='" + getCuerpo() + "'" +
            ", subcategorias='" + getSubcategorias() + "'" +
            "}";
    }
}
