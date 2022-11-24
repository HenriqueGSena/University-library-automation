package br.com.universityautomation.developer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "autor")
    private String autor;

    @Column(name = "isbn")
    private Double isbn;

    @Column(name = "editora")
    private String editora;

    @Column(name = "edicao")
    private String edicao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "books", "periodicals", "emprestimos" }, allowSetters = true)
    private Publications publications;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return this.autor;
    }

    public Book autor(String autor) {
        this.setAutor(autor);
        return this;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Double getIsbn() {
        return this.isbn;
    }

    public Book isbn(Double isbn) {
        this.setIsbn(isbn);
        return this;
    }

    public void setIsbn(Double isbn) {
        this.isbn = isbn;
    }

    public String getEditora() {
        return this.editora;
    }

    public Book editora(String editora) {
        this.setEditora(editora);
        return this;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getEdicao() {
        return this.edicao;
    }

    public Book edicao(String edicao) {
        this.setEdicao(edicao);
        return this;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public Publications getPublications() {
        return this.publications;
    }

    public void setPublications(Publications publications) {
        this.publications = publications;
    }

    public Book publications(Publications publications) {
        this.setPublications(publications);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", autor='" + getAutor() + "'" +
            ", isbn=" + getIsbn() +
            ", editora='" + getEditora() + "'" +
            ", edicao='" + getEdicao() + "'" +
            "}";
    }
}
