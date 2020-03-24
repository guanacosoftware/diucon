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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link ar.com.guanaco.diucon.domain.Incidente} entity. This class is used
 * in {@link ar.com.guanaco.diucon.web.rest.IncidenteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /incidentes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IncidenteCriteria implements Serializable, Criteria {
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

    private StringFilter localizacion;

    private DoubleFilter latitud;

    private DoubleFilter longitud;

    private LocalDateFilter fechaResolucion;

    private LocalDateFilter fechaCierre;

    private StringFilter email;

    private LongFilter comentariosId;

    private LongFilter historialId;

    private LongFilter categoriaId;

    private LongFilter subcategoriaId;

    private LongFilter operadorId;

    private LongFilter responsableId;

    public IncidenteCriteria() {
    }

    public IncidenteCriteria(IncidenteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.localizacion = other.localizacion == null ? null : other.localizacion.copy();
        this.latitud = other.latitud == null ? null : other.latitud.copy();
        this.longitud = other.longitud == null ? null : other.longitud.copy();
        this.fechaResolucion = other.fechaResolucion == null ? null : other.fechaResolucion.copy();
        this.fechaCierre = other.fechaCierre == null ? null : other.fechaCierre.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.comentariosId = other.comentariosId == null ? null : other.comentariosId.copy();
        this.historialId = other.historialId == null ? null : other.historialId.copy();
        this.categoriaId = other.categoriaId == null ? null : other.categoriaId.copy();
        this.subcategoriaId = other.subcategoriaId == null ? null : other.subcategoriaId.copy();
        this.operadorId = other.operadorId == null ? null : other.operadorId.copy();
        this.responsableId = other.responsableId == null ? null : other.responsableId.copy();
    }

    @Override
    public IncidenteCriteria copy() {
        return new IncidenteCriteria(this);
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

    public StringFilter getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(StringFilter localizacion) {
        this.localizacion = localizacion;
    }

    public DoubleFilter getLatitud() {
        return latitud;
    }

    public void setLatitud(DoubleFilter latitud) {
        this.latitud = latitud;
    }

    public DoubleFilter getLongitud() {
        return longitud;
    }

    public void setLongitud(DoubleFilter longitud) {
        this.longitud = longitud;
    }

    public LocalDateFilter getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateFilter fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDateFilter getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateFilter fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getComentariosId() {
        return comentariosId;
    }

    public void setComentariosId(LongFilter comentariosId) {
        this.comentariosId = comentariosId;
    }

    public LongFilter getHistorialId() {
        return historialId;
    }

    public void setHistorialId(LongFilter historialId) {
        this.historialId = historialId;
    }

    public LongFilter getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(LongFilter categoriaId) {
        this.categoriaId = categoriaId;
    }

    public LongFilter getSubcategoriaId() {
        return subcategoriaId;
    }

    public void setSubcategoriaId(LongFilter subcategoriaId) {
        this.subcategoriaId = subcategoriaId;
    }

    public LongFilter getOperadorId() {
        return operadorId;
    }

    public void setOperadorId(LongFilter operadorId) {
        this.operadorId = operadorId;
    }

    public LongFilter getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(LongFilter responsableId) {
        this.responsableId = responsableId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IncidenteCriteria that = (IncidenteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(localizacion, that.localizacion) &&
            Objects.equals(latitud, that.latitud) &&
            Objects.equals(longitud, that.longitud) &&
            Objects.equals(fechaResolucion, that.fechaResolucion) &&
            Objects.equals(fechaCierre, that.fechaCierre) &&
            Objects.equals(email, that.email) &&
            Objects.equals(comentariosId, that.comentariosId) &&
            Objects.equals(historialId, that.historialId) &&
            Objects.equals(categoriaId, that.categoriaId) &&
            Objects.equals(subcategoriaId, that.subcategoriaId) &&
            Objects.equals(operadorId, that.operadorId) &&
            Objects.equals(responsableId, that.responsableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fecha,
        estado,
        localizacion,
        latitud,
        longitud,
        fechaResolucion,
        fechaCierre,
        email,
        comentariosId,
        historialId,
        categoriaId,
        subcategoriaId,
        operadorId,
        responsableId
        );
    }

    @Override
    public String toString() {
        return "IncidenteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (localizacion != null ? "localizacion=" + localizacion + ", " : "") +
                (latitud != null ? "latitud=" + latitud + ", " : "") +
                (longitud != null ? "longitud=" + longitud + ", " : "") +
                (fechaResolucion != null ? "fechaResolucion=" + fechaResolucion + ", " : "") +
                (fechaCierre != null ? "fechaCierre=" + fechaCierre + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (comentariosId != null ? "comentariosId=" + comentariosId + ", " : "") +
                (historialId != null ? "historialId=" + historialId + ", " : "") +
                (categoriaId != null ? "categoriaId=" + categoriaId + ", " : "") +
                (subcategoriaId != null ? "subcategoriaId=" + subcategoriaId + ", " : "") +
                (operadorId != null ? "operadorId=" + operadorId + ", " : "") +
                (responsableId != null ? "responsableId=" + responsableId + ", " : "") +
            "}";
    }

}
