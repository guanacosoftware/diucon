package ar.com.guanaco.diucon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ar.com.guanaco.diucon.domain.enumeration.Estado;

/**
 * A Incidente.
 */
@Entity
@Table(name = "incidente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Incidente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "cuerpo", nullable = false)
    private String cuerpo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Column(name = "localizacion")
    private String localizacion;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "fecha_resolucion")
    private LocalDate fechaResolucion;

    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "incidente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comentario> comentarios = new HashSet<>();

    @OneToMany(mappedBy = "incidente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HistorialEstado> historials = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("incidentes")
    private Categoria categoria;

    @ManyToOne
    @JsonIgnoreProperties("incidentes")
    private SubCategoria subcategoria;

    @ManyToOne
    @JsonIgnoreProperties("incidentes")
    private User operador;

    @ManyToOne
    @JsonIgnoreProperties("incidentes")
    private Responsable responsable;

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

    public Incidente fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public Incidente cuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
        return this;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Estado getEstado() {
        return estado;
    }

    public Incidente estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public Incidente localizacion(String localizacion) {
        this.localizacion = localizacion;
        return this;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Incidente latitud(Double latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Incidente longitud(Double longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    public Incidente fechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
        return this;
    }

    public void setFechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public Incidente fechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
        return this;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getEmail() {
        return email;
    }

    public Incidente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public Incidente comentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
        return this;
    }

    public Incidente addComentarios(Comentario comentario) {
        this.comentarios.add(comentario);
        comentario.setIncidente(this);
        return this;
    }

    public Incidente removeComentarios(Comentario comentario) {
        this.comentarios.remove(comentario);
        comentario.setIncidente(null);
        return this;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Set<HistorialEstado> getHistorials() {
        return historials;
    }

    public Incidente historials(Set<HistorialEstado> historialEstados) {
        this.historials = historialEstados;
        return this;
    }

    public Incidente addHistorial(HistorialEstado historialEstado) {
        this.historials.add(historialEstado);
        historialEstado.setIncidente(this);
        return this;
    }

    public Incidente removeHistorial(HistorialEstado historialEstado) {
        this.historials.remove(historialEstado);
        historialEstado.setIncidente(null);
        return this;
    }

    public void setHistorials(Set<HistorialEstado> historialEstados) {
        this.historials = historialEstados;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Incidente categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public SubCategoria getSubcategoria() {
        return subcategoria;
    }

    public Incidente subcategoria(SubCategoria subCategoria) {
        this.subcategoria = subCategoria;
        return this;
    }

    public void setSubcategoria(SubCategoria subCategoria) {
        this.subcategoria = subCategoria;
    }

    public User getOperador() {
        return operador;
    }

    public Incidente operador(User user) {
        this.operador = user;
        return this;
    }

    public void setOperador(User user) {
        this.operador = user;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public Incidente responsable(Responsable responsable) {
        this.responsable = responsable;
        return this;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incidente)) {
            return false;
        }
        return id != null && id.equals(((Incidente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Incidente{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", cuerpo='" + getCuerpo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", localizacion='" + getLocalizacion() + "'" +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            ", fechaResolucion='" + getFechaResolucion() + "'" +
            ", fechaCierre='" + getFechaCierre() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
