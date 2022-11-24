package br.com.universityautomation.developer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "curso")
    private String curso;

    @ManyToOne
    @JsonIgnoreProperties(value = { "functionaries", "librarians", "students", "teachers", "emprestimos" }, allowSetters = true)
    private Registration registration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurso() {
        return this.curso;
    }

    public Student curso(String curso) {
        this.setCurso(curso);
        return this;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Registration getRegistration() {
        return this.registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Student registration(Registration registration) {
        this.setRegistration(registration);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", curso='" + getCurso() + "'" +
            "}";
    }
}
