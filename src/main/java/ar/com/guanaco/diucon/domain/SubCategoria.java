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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A SubCategoria.
 */
@Entity
@Table(name = "sub_categoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubCategoria implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("subcategorias")
    private Categoria categoria;

    @ManyToMany(mappedBy = "subcategorias")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Responsable> responsables = new HashSet<>();

    @ManyToMany(mappedBy = "subcategorias")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Plantilla> plantillas = new HashSet<>();

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

    public SubCategoria nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public SubCategoria observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Categoria getcategoria() {
        return categoria;
    }

    public SubCategoria categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setcategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Responsable> getResponsables() {
        return responsables;
    }

    public SubCategoria responsables(Set<Responsable> responsables) {
        this.responsables = responsables;
        return this;
    }

    public SubCategoria addResponsables(Responsable responsable) {
        this.responsables.add(responsable);
        responsable.getSubcategorias().add(this);
        return this;
    }

    public SubCategoria removeResponsables(Responsable responsable) {
        this.responsables.remove(responsable);
        responsable.getSubcategorias().remove(this);
        return this;
    }

    public void setResponsables(Set<Responsable> responsables) {
        this.responsables = responsables;
    }

    public Set<Plantilla> getPlantillas() {
        return plantillas;
    }

    public SubCategoria plantillas(Set<Plantilla> plantillas) {
        this.plantillas = plantillas;
        return this;
    }

    public SubCategoria addPlantillas(Plantilla plantilla) {
        this.plantillas.add(plantilla);
        plantilla.getSubcategorias().add(this);
        return this;
    }

    public SubCategoria removePlantillas(Plantilla plantilla) {
        this.plantillas.remove(plantilla);
        plantilla.getSubcategorias().remove(this);
        return this;
    }

    public void setPlantillas(Set<Plantilla> plantillas) {
        this.plantillas = plantillas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubCategoria)) {
            return false;
        }
        return id != null && id.equals(((SubCategoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubCategoria{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + ", observaciones='"
                + getObservaciones() + "'" + "}";
    }
}
