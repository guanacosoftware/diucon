package ar.com.guanaco.diucon.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ar.com.guanaco.diucon.domain.SubCategoria} entity. This class is used
 * in {@link ar.com.guanaco.diucon.web.rest.SubCategoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sub-categorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubCategoriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private LongFilter categiaId;

    private LongFilter responsablesId;

    private LongFilter plantillasId;

    public SubCategoriaCriteria() {
    }

    public SubCategoriaCriteria(SubCategoriaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.categiaId = other.categiaId == null ? null : other.categiaId.copy();
        this.responsablesId = other.responsablesId == null ? null : other.responsablesId.copy();
        this.plantillasId = other.plantillasId == null ? null : other.plantillasId.copy();
    }

    @Override
    public SubCategoriaCriteria copy() {
        return new SubCategoriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public LongFilter getCategiaId() {
        return categiaId;
    }

    public void setCategiaId(LongFilter categiaId) {
        this.categiaId = categiaId;
    }

    public LongFilter getResponsablesId() {
        return responsablesId;
    }

    public void setResponsablesId(LongFilter responsablesId) {
        this.responsablesId = responsablesId;
    }

    public LongFilter getPlantillasId() {
        return plantillasId;
    }

    public void setPlantillasId(LongFilter plantillasId) {
        this.plantillasId = plantillasId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SubCategoriaCriteria that = (SubCategoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(categiaId, that.categiaId) &&
            Objects.equals(responsablesId, that.responsablesId) &&
            Objects.equals(plantillasId, that.plantillasId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        categiaId,
        responsablesId,
        plantillasId
        );
    }

    @Override
    public String toString() {
        return "SubCategoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (categiaId != null ? "categiaId=" + categiaId + ", " : "") +
                (responsablesId != null ? "responsablesId=" + responsablesId + ", " : "") +
                (plantillasId != null ? "plantillasId=" + plantillasId + ", " : "") +
            "}";
    }

}
