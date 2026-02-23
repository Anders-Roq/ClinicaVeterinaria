
package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "PRESCRICAO")
public class Prescricao implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRE_ID")
    private Long idPrescricao;

    @NotBlank
    @Size(min = 5, max = 50)    
    @Column(name = "PRE_MEDICAMENTO")
    private String medicamento;
    
    @NotBlank
    @Size(max = 30)
    @Column(name = "PRE_DOSAGEM")
    private String dosagem;
    
    @NotBlank
    @Size(max = 30)    
    @Column(name = "PRE_FREQUENCIA")
    private String frequencia;
    
    @NotBlank  
    @Size(max = 30)    
    @Column(name = "PRE_DURACAO")
    private String duracao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CON_ID")
    private Consulta consulta;

    public Prescricao() {}

    public Prescricao(String medicamento, String dosagem, String frequencia, String duracao, Consulta consulta) {
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.frequencia = frequencia;
        this.duracao = duracao;
        this.consulta = consulta;
    }
    
    
    public Long getId() {
        return idPrescricao;
    }

    public void setId(Long id) {
        this.idPrescricao = id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Prescricao p = (Prescricao) o;
    return idPrescricao != null && idPrescricao.equals(p.idPrescricao);
}

@Override
public int hashCode() {
    return getClass().hashCode();
}

    
}
