package br.com.universityautomation.developer.domain;

import br.com.universityautomation.developer.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Publications.
 */
@Entity
@Table(name = "publications")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Publications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "data_publicacao")
    private String dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "publications")
    @JsonIgnoreProperties(value = { "publications" }, allowSetters = true)
    private Set<Book> books = new HashSet<>();

    @OneToMany(mappedBy = "publications")
    @JsonIgnoreProperties(value = { "publications" }, allowSetters = true)
    private Set<Periodicals> periodicals = new HashSet<>();

    @ManyToMany(mappedBy = "publications")
    @JsonIgnoreProperties(value = { "publications", "registrations" }, allowSetters = true)
    private Set<Emprestimo> emprestimos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Publications id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Publications titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPreco() {
        return this.preco;
    }

    public Publications preco(Double preco) {
        this.setPreco(preco);
        return this;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDataPublicacao() {
        return this.dataPublicacao;
    }

    public Publications dataPublicacao(String dataPublicacao) {
        this.setDataPublicacao(dataPublicacao);
        return this;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Status getStatus() {
        return this.status;
    }

    public Publications status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        if (this.books != null) {
            this.books.forEach(i -> i.setPublications(null));
        }
        if (books != null) {
            books.forEach(i -> i.setPublications(this));
        }
        this.books = books;
    }

    public Publications books(Set<Book> books) {
        this.setBooks(books);
        return this;
    }

    public Publications addBook(Book book) {
        this.books.add(book);
        book.setPublications(this);
        return this;
    }

    public Publications removeBook(Book book) {
        this.books.remove(book);
        book.setPublications(null);
        return this;
    }

    public Set<Periodicals> getPeriodicals() {
        return this.periodicals;
    }

    public void setPeriodicals(Set<Periodicals> periodicals) {
        if (this.periodicals != null) {
            this.periodicals.forEach(i -> i.setPublications(null));
        }
        if (periodicals != null) {
            periodicals.forEach(i -> i.setPublications(this));
        }
        this.periodicals = periodicals;
    }

    public Publications periodicals(Set<Periodicals> periodicals) {
        this.setPeriodicals(periodicals);
        return this;
    }

    public Publications addPeriodicals(Periodicals periodicals) {
        this.periodicals.add(periodicals);
        periodicals.setPublications(this);
        return this;
    }

    public Publications removePeriodicals(Periodicals periodicals) {
        this.periodicals.remove(periodicals);
        periodicals.setPublications(null);
        return this;
    }

    public Set<Emprestimo> getEmprestimos() {
        return this.emprestimos;
    }

    public void setEmprestimos(Set<Emprestimo> emprestimos) {
        if (this.emprestimos != null) {
            this.emprestimos.forEach(i -> i.removePublications(this));
        }
        if (emprestimos != null) {
            emprestimos.forEach(i -> i.addPublications(this));
        }
        this.emprestimos = emprestimos;
    }

    public Publications emprestimos(Set<Emprestimo> emprestimos) {
        this.setEmprestimos(emprestimos);
        return this;
    }

    public Publications addEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
        emprestimo.getPublications().add(this);
        return this;
    }

    public Publications removeEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
        emprestimo.getPublications().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publications)) {
            return false;
        }
        return id != null && id.equals(((Publications) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Publications{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", preco=" + getPreco() +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
