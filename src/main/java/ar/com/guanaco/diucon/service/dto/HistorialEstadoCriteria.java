package ar.com.guanaco.diucon.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ar.com.guanaco.diucon.domain.enumeration.Estado;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link ar.com.guanaco.diucon.domain.HistorialEstado} entity. This class is used
 * in {@link ar.com.guanaco.diucon.web.rest.HistorialEstadoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /historial-estados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HistorialEstadoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Estado
     */
    public static class EstadoFilter extends Filter<Estado> {

        public EstadoFilter() {
        }

        public EstadoFilter(EstadoFilter filter) {
            super(filter);
        }

        @Override
        public EstadoFilter copy() {
            return new EstadoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fecha;

    private EstadoFilter estado;

    private LongFilter usuarioId;

    private LongFilter incidenteId;

    public HistorialEstadoCriteria() {
    }

    public HistorialEstadoCriteria(HistorialEstadoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.incidenteId = other.incidenteId == null ? null : other.incidenteId.copy();
    }

    @Override
    public HistorialEstadoCriteria copy() {
        return new HistorialEstadoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public EstadoFilter getEstado() {
        return estado;
    }

    public void setEstado(EstadoFilter estado) {
        this.estado = estado;
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
        final HistorialEstadoCriteria that = (HistorialEstadoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(incidenteId, that.incidenteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fecha,
        estado,
        usuarioId,
        incidenteId
        );
    }

    @Override
    public String toString() {
        return "HistorialEstadoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (incidenteId != null ? "incidenteId=" + incidenteId + ", " : "") +
            "}";
    }

}
