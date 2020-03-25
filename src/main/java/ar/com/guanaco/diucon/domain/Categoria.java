package ar.com.guanaco.diucon.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Categoria implements Serializable {

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
    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "categoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SubCategoria> subcategorias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not
    // remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Categoria observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Set<SubCategoria> getSubcategorias() {
        return subcategorias;
    }

    public Categoria subcategorias(Set<SubCategoria> subCategorias) {
        this.subcategorias = subCategorias;
        return this;
    }

    public Categoria addSubcategorias(SubCategoria subCategoria) {
        this.subcategorias.add(subCategoria);
        subCategoria.setcategoria(this);
        return this;
    }

    public Categoria removeSubcategorias(SubCategoria subCategoria) {
        this.subcategorias.remove(subCategoria);
        subCategoria.setcategoria(null);
        return this;
    }

    public void setSubcategorias(Set<SubCategoria> subCategorias) {
        this.subcategorias = subCategorias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categoria)) {
            return false;
        }
        return id != null && id.equals(((Categoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + ", observaciones='"
                + getObservaciones() + "'" + "}";
    }
}
