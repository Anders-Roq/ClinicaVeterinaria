/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

/**
 *
 * @author dla19
 */
@Entity
@Table(name = "CONSULTA")
public class Consulta implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON_ID", nullable = false)
    private Long idConsulta;

    
    @Column(name = "CON_DT_CONSULTA", nullable = false)
    private LocalDateTime dataConsulta;
    
    // Motivo da consulta.
    @Column(name = "CON_MOTIVO", nullable = false, length = 255)
    private String motivo;
    
    
    @Column(name = "CON_DIAGNOSTICO", nullable = true, length = 1000) //Nullable TRUE, pois pode ser preenchido posteriormente
    private String diagnostico;
    
    
    
    @ManyToOne
    @JoinColumn(name = "ANI_ID", nullable = false)    
    private Animal animal;
    
    
    @ManyToOne
    @JoinColumn(name = "VET_ID", nullable = false)
    private Veterinario veterinario;
    
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescricao> prescricoes;
    
    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pagamento pagamento;
    

    public Consulta() {}

    public Consulta(LocalDateTime dataConsulta, String motivo, String diagnostico, Animal animal, Veterinario veterinario) {
        this.dataConsulta = dataConsulta;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.animal = animal;
        this.veterinario = veterinario;
    }

    
    
    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(List<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

   
    
}
