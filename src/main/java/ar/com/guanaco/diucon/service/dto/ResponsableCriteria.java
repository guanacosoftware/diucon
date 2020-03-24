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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link ar.com.guanaco.diucon.domain.Responsable} entity. This class is used
 * in {@link ar.com.guanaco.diucon.web.rest.ResponsableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /responsables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResponsableCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombreCompleto;

    private StringFilter telefono;

    private LocalDateFilter fechaNacimiento;

    private LongFilter dni;

    private StringFilter domicilio;

    private DoubleFilter latitud;

    private DoubleFilter longitud;

    private BooleanFilter profesional;

    private BooleanFilter estudiante;

    private BooleanFilter trabajador;

    private LongFilter usuarioId;

    private LongFilter subcategoriasId;

    public ResponsableCriteria() {
    }

    public ResponsableCriteria(ResponsableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombreCompleto = other.nombreCompleto == null ? null : other.nombreCompleto.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.fechaNacimiento = other.fechaNacimiento == null ? null : other.fechaNacimiento.copy();
        this.dni = other.dni == null ? null : other.dni.copy();
        this.domicilio = other.domicilio == null ? null : other.domicilio.copy();
        this.latitud = other.latitud == null ? null : other.latitud.copy();
        this.longitud = other.longitud == null ? null : other.longitud.copy();
        this.profesional = other.profesional == null ? null : other.profesional.copy();
        this.estudiante = other.estudiante == null ? null : other.estudiante.copy();
        this.trabajador = other.trabajador == null ? null : other.trabajador.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.subcategoriasId = other.subcategoriasId == null ? null : other.subcategoriasId.copy();
    }

    @Override
    public ResponsableCriteria copy() {
        return new ResponsableCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(StringFilter nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public LocalDateFilter getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateFilter fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LongFilter getDni() {
        return dni;
    }

    public void setDni(LongFilter dni) {
        this.dni = dni;
    }

    public StringFilter getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(StringFilter domicilio) {
        this.domicilio = domicilio;
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

    public BooleanFilter getProfesional() {
        return profesional;
    }

    public void setProfesional(BooleanFilter profesional) {
        this.profesional = profesional;
    }

    public BooleanFilter getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(BooleanFilter estudiante) {
        this.estudiante = estudiante;
    }

    public BooleanFilter getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(BooleanFilter trabajador) {
        this.trabajador = trabajador;
    }

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
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
        final ResponsableCriteria that = (ResponsableCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombreCompleto, that.nombreCompleto) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(fechaNacimiento, that.fechaNacimiento) &&
            Objects.equals(dni, that.dni) &&
            Objects.equals(domicilio, that.domicilio) &&
            Objects.equals(latitud, that.latitud) &&
            Objects.equals(longitud, that.longitud) &&
            Objects.equals(profesional, that.profesional) &&
            Objects.equals(estudiante, that.estudiante) &&
            Objects.equals(trabajador, that.trabajador) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(subcategoriasId, that.subcategoriasId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombreCompleto,
        telefono,
        fechaNacimiento,
        dni,
        domicilio,
        latitud,
        longitud,
        profesional,
        estudiante,
        trabajador,
        usuarioId,
        subcategoriasId
        );
    }

    @Override
    public String toString() {
        return "ResponsableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombreCompleto != null ? "nombreCompleto=" + nombreCompleto + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (fechaNacimiento != null ? "fechaNacimiento=" + fechaNacimiento + ", " : "") +
                (dni != null ? "dni=" + dni + ", " : "") +
                (domicilio != null ? "domicilio=" + domicilio + ", " : "") +
                (latitud != null ? "latitud=" + latitud + ", " : "") +
                (longitud != null ? "longitud=" + longitud + ", " : "") +
                (profesional != null ? "profesional=" + profesional + ", " : "") +
                (estudiante != null ? "estudiante=" + estudiante + ", " : "") +
                (trabajador != null ? "trabajador=" + trabajador + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (subcategoriasId != null ? "subcategoriasId=" + subcategoriasId + ", " : "") +
            "}";
    }

}
