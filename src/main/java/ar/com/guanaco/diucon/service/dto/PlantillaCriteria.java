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
 * Criteria class for the {@link ar.com.guanaco.diucon.domain.Plantilla} entity. This class is used
 * in {@link ar.com.guanaco.diucon.web.rest.PlantillaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plantillas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlantillaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private LongFilter subcategoriasId;

    public PlantillaCriteria() {
    }

    public PlantillaCriteria(PlantillaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.subcategoriasId = other.subcategoriasId == null ? null : other.subcategoriasId.copy();
    }

    @Override
    public PlantillaCriteria copy() {
        return new PlantillaCriteria(this);
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

    public LongFilter getSubcategoriasId() {
        return subcategoriasId;
    }

    public void setSubcategoriasId(LongFilter subcategoriasId) {
        this.subcategoriasId = subcategoriasId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlantillaCriteria that = (PlantillaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(subcategoriasId, that.subcategoriasId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        subcategoriasId
        );
    }

    @Override
    public String toString() {
        return "PlantillaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (subcategoriasId != null ? "subcategoriasId=" + subcategoriasId + ", " : "") +
            "}";
    }

}
