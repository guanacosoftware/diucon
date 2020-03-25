package ar.com.guanaco.diucon.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link ar.com.guanaco.diucon.domain.SubCategoria} entity.
 */
public class SubCategoriaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @Lob
    private String observaciones;

    private Long categoriaId;

    private String categoriaNombre;

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

    public Long getcategoriaId() {
        return categoriaId;
    }

    public void setcategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getcategoriaNombre() {
        return categoriaNombre;
    }

    public void setcategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
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
        return "SubCategoriaDTO{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + ", observaciones='"
                + getObservaciones() + "'" + ", categoriaId=" + getcategoriaId() + ", categoriaNombre='"
                + getcategoriaNombre() + "'" + "}";
    }
}
