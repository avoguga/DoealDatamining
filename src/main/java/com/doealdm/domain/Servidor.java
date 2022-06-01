package com.doealdm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Servidor.
 */
@Entity
@Table(name = "servidor")
public class Servidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "cargo")
    private String cargo;

    @JsonIgnoreProperties(value = { "servidor", "diario" }, allowSetters = true)
    @OneToOne(mappedBy = "servidor")
    private Concessao concessao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Servidor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Servidor nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Servidor cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public Servidor matricula(String matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return this.cargo;
    }

    public Servidor cargo(String cargo) {
        this.setCargo(cargo);
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Concessao getConcessao() {
        return this.concessao;
    }

    public void setConcessao(Concessao concessao) {
        if (this.concessao != null) {
            this.concessao.setServidor(null);
        }
        if (concessao != null) {
            concessao.setServidor(this);
        }
        this.concessao = concessao;
    }

    public Servidor concessao(Concessao concessao) {
        this.setConcessao(concessao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Servidor)) {
            return false;
        }
        return id != null && id.equals(((Servidor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Servidor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", matricula='" + getMatricula() + "'" +
            ", cargo='" + getCargo() + "'" +
            "}";
    }
}
