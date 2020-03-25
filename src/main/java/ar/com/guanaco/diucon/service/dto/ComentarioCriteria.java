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
 * Criteria class for the {@link ar.com.guanaco.diucon.domain.Comentario} entity. This class is used
 * in {@link ar.com.guanaco.diucon.web.rest.ComentarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comentarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ComentarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter usuarioId;

    private LongFilter incidenteId;

    public ComentarioCriteria() {
    }

    public ComentarioCriteria(ComentarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.incidenteId = other.incidenteId == null ? null : other.incidenteId.copy();
    }

    @Override
    public ComentarioCriteria copy() {
        return new ComentarioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LongFilter getIncidenteId() {
        return incidenteId;
    }

    public void setIncidenteId(LongFilter incidenteId) {
        this.incidenteId = incidenteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ComentarioCriteria that = (ComentarioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(incidenteId, that.incidenteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        usuarioId,
        incidenteId
        );
    }

    @Override
    public String toString() {
        return "ComentarioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (incidenteId != null ? "incidenteId=" + incidenteId + ", " : "") +
            "}";
    }

}
