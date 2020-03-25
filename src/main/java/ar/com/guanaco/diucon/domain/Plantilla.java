package ar.com.guanaco.diucon.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Plantilla.
 */
@Entity
@Table(name = "plantilla")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plantilla implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "cuerpo", nullable = false)
    private String cuerpo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "plantilla_subcategorias",
               joinColumns = @JoinColumn(name = "plantilla_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subcategorias_id", referencedColumnName = "id"))
    private Set<SubCategoria> subcategorias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Plantilla nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public Plantilla cuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
        return this;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Set<SubCategoria> getSubcategorias() {
        return subcategorias;
    }

    public Plantilla subcategorias(Set<SubCategoria> subCategorias) {
        this.subcategorias = subCategorias;
        return this;
    }

    public Plantilla addSubcategorias(SubCategoria subCategoria) {
        this.subcategorias.add(subCategoria);
        subCategoria.getPlantillas().add(this);
        return this;
    }

    public Plantilla removeSubcategorias(SubCategoria subCategoria) {
        this.subcategorias.remove(subCategoria);
        subCategoria.getPlantillas().remove(this);
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
        if (!(o instanceof Plantilla)) {
            return false;
        }
        return id != null && id.equals(((Plantilla) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Plantilla{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", cuerpo='" + getCuerpo() + "'" +
            "}";
    }
}
