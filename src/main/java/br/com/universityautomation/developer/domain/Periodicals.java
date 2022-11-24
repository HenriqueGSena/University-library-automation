package br.com.universityautomation.developer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Periodicals.
 */
@Entity
@Table(name = "periodicals")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Periodicals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "numero")
    private Double numero;

    @Column(name = "issn")
    private String issn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "books", "periodicals", "emprestimos" }, allowSetters = true)
    private Publications publications;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Periodicals id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVolume() {
        return this.volume;
    }

    public Periodicals volume(Double volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getNumero() {
        return this.numero;
    }

    public Periodicals numero(Double numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public String getIssn() {
        return this.issn;
    }

    public Periodicals issn(String issn) {
        this.setIssn(issn);
        return this;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public Publications getPublications() {
        return this.publications;
    }

    public void setPublications(Publications publications) {
        this.publications = publications;
    }

    public Periodicals publications(Publications publications) {
        this.setPublications(publications);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Periodicals)) {
            return false;
        }
        return id != null && id.equals(((Periodicals) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Periodicals{" +
            "id=" + getId() +
            ", volume=" + getVolume() +
            ", numero=" + getNumero() +
            ", issn='" + getIssn() + "'" +
            "}";
    }
}
