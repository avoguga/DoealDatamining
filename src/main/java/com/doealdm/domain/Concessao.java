package com.doealdm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Concessao.
 */
@Entity
@Table(name = "concessao")
public class Concessao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_assinatura")
    private LocalDate dataAssinatura;

    @Column(name = "portaria")
    private String portaria;

    @Column(name = "periodo_aquisitivo")
    private String periodoAquisitivo;

    @Column(name = "tempo_afastamento")
    private Integer tempoAfastamento;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_final")
    private LocalDate dataFinal;

    @JsonIgnoreProperties(value = { "concessao" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Servidor servidor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "concessaos" }, allowSetters = true)
    private Diario diario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Concessao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAssinatura() {
        return this.dataAssinatura;
    }

    public Concessao dataAssinatura(LocalDate dataAssinatura) {
        this.setDataAssinatura(dataAssinatura);
        return this;
    }

    public void setDataAssinatura(LocalDate dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public String getPortaria() {
        return this.portaria;
    }

    public Concessao portaria(String portaria) {
        this.setPortaria(portaria);
        return this;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
    }

    public String getPeriodoAquisitivo() {
        return this.periodoAquisitivo;
    }

    public Concessao periodoAquisitivo(String periodoAquisitivo) {
        this.setPeriodoAquisitivo(periodoAquisitivo);
        return this;
    }

    public void setPeriodoAquisitivo(String periodoAquisitivo) {
        this.periodoAquisitivo = periodoAquisitivo;
    }

    public Integer getTempoAfastamento() {
        return this.tempoAfastamento;
    }

    public Concessao tempoAfastamento(Integer tempoAfastamento) {
        this.setTempoAfastamento(tempoAfastamento);
        return this;
    }

    public void setTempoAfastamento(Integer tempoAfastamento) {
        this.tempoAfastamento = tempoAfastamento;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public Concessao dataInicio(LocalDate dataInicio) {
        this.setDataInicio(dataInicio);
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return this.dataFinal;
    }

    public Concessao dataFinal(LocalDate dataFinal) {
        this.setDataFinal(dataFinal);
        return this;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Servidor getServidor() {
        return this.servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public Concessao servidor(Servidor servidor) {
        this.setServidor(servidor);
        return this;
    }

    public Diario getDiario() {
        return this.diario;
    }

    public void setDiario(Diario diario) {
        this.diario = diario;
    }

    public Concessao diario(Diario diario) {
        this.setDiario(diario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concessao)) {
            return false;
        }
        return id != null && id.equals(((Concessao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concessao{" +
            "id=" + getId() +
            ", dataAssinatura='" + getDataAssinatura() + "'" +
            ", portaria='" + getPortaria() + "'" +
            ", periodoAquisitivo='" + getPeriodoAquisitivo() + "'" +
            ", tempoAfastamento=" + getTempoAfastamento() +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFinal='" + getDataFinal() + "'" +
            "}";
    }
}
