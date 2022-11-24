package br.com.universityautomation.developer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Emprestimo.
 */
@Entity
@Table(name = "emprestimo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "rel_emprestimo__publications",
        joinColumns = @JoinColumn(name = "emprestimo_id"),
        inverseJoinColumns = @JoinColumn(name = "publications_id")
    )
    @JsonIgnoreProperties(value = { "books", "periodicals", "emprestimos" }, allowSetters = true)
    private Set<Publications> publications = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_emprestimo__registration",
        joinColumns = @JoinColumn(name = "emprestimo_id"),
        inverseJoinColumns = @JoinColumn(name = "registration_id")
    )
    @JsonIgnoreProperties(value = { "functionaries", "librarians", "students", "teachers", "emprestimos" }, allowSetters = true)
    private Set<Registration> registrations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Emprestimo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Publications> getPublications() {
        return this.publications;
    }

    public void setPublications(Set<Publications> publications) {
        this.publications = publications;
    }

    public Emprestimo publications(Set<Publications> publications) {
        this.setPublications(publications);
        return this;
    }

    public Emprestimo addPublications(Publications publications) {
        this.publications.add(publications);
        publications.getEmprestimos().add(this);
        return this;
    }

    public Emprestimo removePublications(Publications publications) {
        this.publications.remove(publications);
        publications.getEmprestimos().remove(this);
        return this;
    }

    public Set<Registration> getRegistrations() {
        return this.registrations;
    }

    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }

    public Emprestimo registrations(Set<Registration> registrations) {
        this.setRegistrations(registrations);
        return this;
    }

    public Emprestimo addRegistration(Registration registration) {
        this.registrations.add(registration);
        registration.getEmprestimos().add(this);
        return this;
    }

    public Emprestimo removeRegistration(Registration registration) {
        this.registrations.remove(registration);
        registration.getEmprestimos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emprestimo)) {
            return false;
        }
        return id != null && id.equals(((Emprestimo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emprestimo{" +
            "id=" + getId() +
            "}";
    }
}
