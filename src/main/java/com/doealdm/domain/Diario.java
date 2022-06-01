package com.doealdm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Diario.
 */
@Entity
@Table(name = "diario")
public class Diario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "numero")
    private Integer numero;

    @OneToMany(mappedBy = "diario")
    @JsonIgnoreProperties(value = { "servidor", "diario" }, allowSetters = true)
    private Set<Concessao> concessaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Diario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPublicacao() {
        return this.dataPublicacao;
    }

    public Diario dataPublicacao(LocalDate dataPublicacao) {
        this.setDataPublicacao(dataPublicacao);
        return this;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Integer getAno() {
        return this.ano;
    }

    public Diario ano(Integer ano) {
        this.setAno(ano);
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Diario numero(Integer numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Set<Concessao> getConcessaos() {
        return this.concessaos;
    }

    public void setConcessaos(Set<Concessao> concessaos) {
        if (this.concessaos != null) {
            this.concessaos.forEach(i -> i.setDiario(null));
        }
        if (concessaos != null) {
            concessaos.forEach(i -> i.setDiario(this));
        }
        this.concessaos = concessaos;
    }

    public Diario concessaos(Set<Concessao> concessaos) {
        this.setConcessaos(concessaos);
        return this;
    }

    public Diario addConcessao(Concessao concessao) {
        this.concessaos.add(concessao);
        concessao.setDiario(this);
        return this;
    }

    public Diario removeConcessao(Concessao concessao) {
        this.concessaos.remove(concessao);
        concessao.setDiario(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diario)) {
            return false;
        }
        return id != null && id.equals(((Diario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Diario{" +
            "id=" + getId() +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            ", ano=" + getAno() +
            ", numero=" + getNumero() +
            "}";
    }
}
