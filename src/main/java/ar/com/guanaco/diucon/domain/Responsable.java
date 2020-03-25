package ar.com.guanaco.diucon.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Responsable.
 */
@Entity
@Table(name = "responsable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Responsable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Min(value = 0L)
    @Max(value = 99999999L)
    @Column(name = "dni")
    private Long dni;

    @NotNull
    @Column(name = "domicilio", nullable = false)
    private String domicilio;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "profesional")
    private Boolean profesional;

    @Column(name = "estudiante")
    private Boolean estudiante;

    @Column(name = "trabajador")
    private Boolean trabajador;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "responsable_subcategorias",
               joinColumns = @JoinColumn(name = "responsable_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subcategorias_id", referencedColumnName = "id"))
    private Set<SubCategoria> subcategorias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Responsable nombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public Responsable telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Responsable fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getDni() {
        return dni;
    }

    public Responsable dni(Long dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Responsable domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Responsable latitud(Double latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Responsable longitud(Double longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Boolean isProfesional() {
        return profesional;
    }

    public Responsable profesional(Boolean profesional) {
        this.profesional = profesional;
        return this;
    }

    public void setProfesional(Boolean profesional) {
        this.profesional = profesional;
    }

    public Boolean isEstudiante() {
        return estudiante;
    }

    public Responsable estudiante(Boolean estudiante) {
        this.estudiante = estudiante;
        return this;
    }

    public void setEstudiante(Boolean estudiante) {
        this.estudiante = estudiante;
    }

    public Boolean isTrabajador() {
        return trabajador;
    }

    public Responsable trabajador(Boolean trabajador) {
        this.trabajador = trabajador;
        return this;
    }

    public void setTrabajador(Boolean trabajador) {
        this.trabajador = trabajador;
    }

    public User getUsuario() {
        return usuario;
    }

    public Responsable usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<SubCategoria> getSubcategorias() {
        return subcategorias;
    }

    public Responsable subcategorias(Set<SubCategoria> subCategorias) {
        this.subcategorias = subCategorias;
        return this;
    }

    public Responsable addSubcategorias(SubCategoria subCategoria) {
        this.subcategorias.add(subCategoria);
        subCategoria.getResponsables().add(this);
        return this;
    }

    public Responsable removeSubcategorias(SubCategoria subCategoria) {
        this.subcategorias.remove(subCategoria);
        subCategoria.getResponsables().remove(this);
        return this;
    }

    public void setSubcategorias(Set<SubCategoria> subCategorias) {
        this.subcategorias = subCategorias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Responsable)) {
            return false;
        }
        return id != null && id.equals(((Responsable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Responsable{" +
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
            "}";
    }
}
