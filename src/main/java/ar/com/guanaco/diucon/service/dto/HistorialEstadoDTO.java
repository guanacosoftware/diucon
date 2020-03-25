package ar.com.guanaco.diucon.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ar.com.guanaco.diucon.domain.enumeration.Estado;

/**
 * A DTO for the {@link ar.com.guanaco.diucon.domain.HistorialEstado} entity.
 */
public class HistorialEstadoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant fecha;

    private Estado estado;


    private Long usuarioId;

    private String usuarioLogin;

    private Long incidenteId;
    
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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

    public Long getIncidenteId() {
        return incidenteId;
    }

    public void setIncidenteId(Long incidenteId) {
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

        HistorialEstadoDTO historialEstadoDTO = (HistorialEstadoDTO) o;
        if (historialEstadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historialEstadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistorialEstadoDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            ", usuarioId=" + getUsuarioId() +
            ", usuarioLogin='" + getUsuarioLogin() + "'" +
            ", incidenteId=" + getIncidenteId() +
            "}";
    }
}
