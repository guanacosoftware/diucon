package ar.com.guanaco.diucon.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import ar.com.guanaco.diucon.domain.enumeration.Estado;

/**
 * A DTO for the {@link ar.com.guanaco.diucon.domain.Incidente} entity.
 */
public class IncidenteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant fecha;

    @NotNull
    private String resumen;

    
    @Lob
    private String descripcion;

    @NotNull
    private Estado estado;

    private String localizacion;

    private Double latitud;

    private Double longitud;

    private Instant fechaResolucion;

    private Instant fechaCierre;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;


    private Long categoriaId;

    private String categoriaNombre;

    private Long subcategoriaId;

    private String subcategoriaNombre;

    private Long operadorId;

    private String operadorLogin;

    private Long responsableId;

    private String responsableNombreCompleto;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
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

    public Instant getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Instant fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public Instant getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Instant fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Long getSubcategoriaId() {
        return subcategoriaId;
    }

    public void setSubcategoriaId(Long subCategoriaId) {
        this.subcategoriaId = subCategoriaId;
    }

    public String getSubcategoriaNombre() {
        return subcategoriaNombre;
    }

    public void setSubcategoriaNombre(String subCategoriaNombre) {
        this.subcategoriaNombre = subCategoriaNombre;
    }

    public Long getOperadorId() {
        return operadorId;
    }

    public void setOperadorId(Long userId) {
        this.operadorId = userId;
    }

    public String getOperadorLogin() {
        return operadorLogin;
    }

    public void setOperadorLogin(String userLogin) {
        this.operadorLogin = userLogin;
    }

    public Long getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Long responsableId) {
        this.responsableId = responsableId;
    }

    public String getResponsableNombreCompleto() {
        return responsableNombreCompleto;
    }

    public void setResponsableNombreCompleto(String responsableNombreCompleto) {
        this.responsableNombreCompleto = responsableNombreCompleto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidenteDTO incidenteDTO = (IncidenteDTO) o;
        if (incidenteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidenteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidenteDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", resumen='" + getResumen() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", localizacion='" + getLocalizacion() + "'" +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            ", fechaResolucion='" + getFechaResolucion() + "'" +
            ", fechaCierre='" + getFechaCierre() + "'" +
            ", email='" + getEmail() + "'" +
            ", categoriaId=" + getCategoriaId() +
            ", categoriaNombre='" + getCategoriaNombre() + "'" +
            ", subcategoriaId=" + getSubcategoriaId() +
            ", subcategoriaNombre='" + getSubcategoriaNombre() + "'" +
            ", operadorId=" + getOperadorId() +
            ", operadorLogin='" + getOperadorLogin() + "'" +
            ", responsableId=" + getResponsableId() +
            ", responsableNombreCompleto='" + getResponsableNombreCompleto() + "'" +
            "}";
    }
}
