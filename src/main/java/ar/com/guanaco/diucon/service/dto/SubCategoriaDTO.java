package ar.com.guanaco.diucon.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link ar.com.guanaco.diucon.domain.SubCategoria} entity.
 */
public class SubCategoriaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nombre;

    @Lob
    private String observaciones;


    private Long categiaId;

    private String categiaNombre;
    
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getCategiaId() {
        return categiaId;
    }

    public void setCategiaId(Long categoriaId) {
        this.categiaId = categoriaId;
    }

    public String getCategiaNombre() {
        return categiaNombre;
    }

    public void setCategiaNombre(String categoriaNombre) {
        this.categiaNombre = categoriaNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubCategoriaDTO subCategoriaDTO = (SubCategoriaDTO) o;
        if (subCategoriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subCategoriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubCategoriaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", categiaId=" + getCategiaId() +
            ", categiaNombre='" + getCategiaNombre() + "'" +
            "}";
    }
}
