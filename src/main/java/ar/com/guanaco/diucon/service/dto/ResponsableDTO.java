package ar.com.guanaco.diucon.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.guanaco.diucon.domain.Responsable} entity.
 */
public class ResponsableDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nombreCompleto;

    @NotNull
    private String telefono;

    private LocalDate fechaNacimiento;

    @Min(value = 0L)
    @Max(value = 99999999L)
    private Long dni;

    @NotNull
    private String domicilio;

    private Double latitud;

    private Double longitud;

    private Boolean profesional;

    private Boolean estudiante;

    private Boolean trabajador;


    private Long usuarioId;

    private String usuarioLogin;
    private Set<SubCategoriaDTO> subcategorias = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Boolean isProfesional() {
        return profesional;
    }

    public void setProfesional(Boolean profesional) {
        this.profesional = profesional;
    }

    public Boolean isEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Boolean estudiante) {
        this.estudiante = estudiante;
    }

    public Boolean isTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Boolean trabajador) {
        this.trabajador = trabajador;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String userLogin) {
        this.usuarioLogin = userLogin;
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

        ResponsableDTO responsableDTO = (ResponsableDTO) o;
        if (responsableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), responsableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResponsableDTO{" +
            "id=" + getId() +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", dni=" + getDni() +
            ", domicilio='" + getDomicilio() + "'" +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            ", profesional='" + isProfesional() + "'" +
            ", estudiante='" + isEstudiante() + "'" +
            ", trabajador='" + isTrabajador() + "'" +
            ", usuarioId=" + getUsuarioId() +
            ", usuarioLogin='" + getUsuarioLogin() + "'" +
            ", subcategorias='" + getSubcategorias() + "'" +
            "}";
    }
}
