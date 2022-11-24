package br.com.universityautomation.developer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Registration.
 */
@Entity
@Table(name = "registration")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "matricula")
    private Double matricula;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "registration")
    @JsonIgnoreProperties(value = { "registration" }, allowSetters = true)
    private Set<Functionaries> functionaries = new HashSet<>();

    @OneToMany(mappedBy = "registration")
    @JsonIgnoreProperties(value = { "registration" }, allowSetters = true)
    private Set<Librarian> librarians = new HashSet<>();

    @OneToMany(mappedBy = "registration")
    @JsonIgnoreProperties(value = { "registration" }, allowSetters = true)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "registration")
    @JsonIgnoreProperties(value = { "registration" }, allowSetters = true)
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(mappedBy = "registrations")
    @JsonIgnoreProperties(value = { "publications", "registrations" }, allowSetters = true)
    private Set<Emprestimo> emprestimos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Registration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMatricula() {
        return this.matricula;
    }

    public Registration matricula(Double matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public void setMatricula(Double matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return this.nome;
    }

    public Registration nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public Registration dataNascimento(String dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public Registration endereco(String endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Registration telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public Registration email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Functionaries> getFunctionaries() {
        return this.functionaries;
    }

    public void setFunctionaries(Set<Functionaries> functionaries) {
        if (this.functionaries != null) {
            this.functionaries.forEach(i -> i.setRegistration(null));
        }
        if (functionaries != null) {
            functionaries.forEach(i -> i.setRegistration(this));
        }
        this.functionaries = functionaries;
    }

    public Registration functionaries(Set<Functionaries> functionaries) {
        this.setFunctionaries(functionaries);
        return this;
    }

    public Registration addFunctionaries(Functionaries functionaries) {
        this.functionaries.add(functionaries);
        functionaries.setRegistration(this);
        return this;
    }

    public Registration removeFunctionaries(Functionaries functionaries) {
        this.functionaries.remove(functionaries);
        functionaries.setRegistration(null);
        return this;
    }

    public Set<Librarian> getLibrarians() {
        return this.librarians;
    }

    public void setLibrarians(Set<Librarian> librarians) {
        if (this.librarians != null) {
            this.librarians.forEach(i -> i.setRegistration(null));
        }
        if (librarians != null) {
            librarians.forEach(i -> i.setRegistration(this));
        }
        this.librarians = librarians;
    }

    public Registration librarians(Set<Librarian> librarians) {
        this.setLibrarians(librarians);
        return this;
    }

    public Registration addLibrarian(Librarian librarian) {
        this.librarians.add(librarian);
        librarian.setRegistration(this);
        return this;
    }

    public Registration removeLibrarian(Librarian librarian) {
        this.librarians.remove(librarian);
        librarian.setRegistration(null);
        return this;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        if (this.students != null) {
            this.students.forEach(i -> i.setRegistration(null));
        }
        if (students != null) {
            students.forEach(i -> i.setRegistration(this));
        }
        this.students = students;
    }

    public Registration students(Set<Student> students) {
        this.setStudents(students);
        return this;
    }

    public Registration addStudent(Student student) {
        this.students.add(student);
        student.setRegistration(this);
        return this;
    }

    public Registration removeStudent(Student student) {
        this.students.remove(student);
        student.setRegistration(null);
        return this;
    }

    public Set<Teacher> getTeachers() {
        return this.teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        if (this.teachers != null) {
            this.teachers.forEach(i -> i.setRegistration(null));
        }
        if (teachers != null) {
            teachers.forEach(i -> i.setRegistration(this));
        }
        this.teachers = teachers;
    }

    public Registration teachers(Set<Teacher> teachers) {
        this.setTeachers(teachers);
        return this;
    }

    public Registration addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.setRegistration(this);
        return this;
    }

    public Registration removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.setRegistration(null);
        return this;
    }

    public Set<Emprestimo> getEmprestimos() {
        return this.emprestimos;
    }

    public void setEmprestimos(Set<Emprestimo> emprestimos) {
        if (this.emprestimos != null) {
            this.emprestimos.forEach(i -> i.removeRegistration(this));
        }
        if (emprestimos != null) {
            emprestimos.forEach(i -> i.addRegistration(this));
        }
        this.emprestimos = emprestimos;
    }

    public Registration emprestimos(Set<Emprestimo> emprestimos) {
        this.setEmprestimos(emprestimos);
        return this;
    }

    public Registration addEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
        emprestimo.getRegistrations().add(this);
        return this;
    }

    public Registration removeEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
        emprestimo.getRegistrations().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Registration)) {
            return false;
        }
        return id != null && id.equals(((Registration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Registration{" +
            "id=" + getId() +
            ", matricula=" + getMatricula() +
            ", nome='" + getNome() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
