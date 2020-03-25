package ar.com.guanaco.diucon.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link ar.com.guanaco.diucon.domain.Comentario} entity.
 */
public class ComentarioDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private String cuerpo;


    private Long usuarioId;

    private String usuarioLogin;

    private Long incidenteId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
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

        ComentarioDTO comentarioDTO = (ComentarioDTO) o;
        if (comentarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comentarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComentarioDTO{" +
            "id=" + getId() +
            ", cuerpo='" + getCuerpo() + "'" +
            ", usuarioId=" + getUsuarioId() +
            ", usuarioLogin='" + getUsuarioLogin() + "'" +
            ", incidenteId=" + getIncidenteId() +
            "}";
    }
}
