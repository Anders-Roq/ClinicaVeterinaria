/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 *
 * @author dla19
 */
@Entity
@Table(name = "PRESCRICAO")
public class Prescricao implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRE_ID", nullable = false)
    private Long idPrescricao;

    
    
    @Column(name = "PRE_MEDICAMENTO", nullable = false, length = 100)
    private String medicamento;
    
    @Column(name = "PRE_DOSAGEM", nullable = false, length = 50)
    private String dosagem;
    
    @Column(name = "PRE_FREQUENCIA", nullable = false, length = 50)
    private String frequencia;
    
    @Column(name = "PRE_DURACAO", nullable = false, length = 50)
    private String duracao;

    
    @ManyToOne
    @JoinColumn(name = "CON_ID", nullable = false)
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
