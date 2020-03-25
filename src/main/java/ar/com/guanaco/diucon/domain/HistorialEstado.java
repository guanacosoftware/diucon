package ar.com.guanaco.diucon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import ar.com.guanaco.diucon.domain.enumeration.Estado;

/**
 * A HistorialEstado.
 */
@Entity
@Table(name = "historial_estado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HistorialEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @ManyToOne
    @JsonIgnoreProperties("historialEstados")
    private User usuario;

    @ManyToOne
    @JsonIgnoreProperties("historials")
    private Incidente incidente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public HistorialEstado fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public HistorialEstado estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public User getUsuario() {
        return usuario;
    }

    public HistorialEstado usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public HistorialEstado incidente(Incidente incidente) {
        this.incidente = incidente;
        return this;
    }

    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistorialEstado)) {
            return false;
        }
        return id != null && id.equals(((HistorialEstado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HistorialEstado{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
