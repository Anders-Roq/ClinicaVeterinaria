package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "CONSULTA")
public class Consulta implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON_ID")
    private Long idConsulta;

    @NotNull
    @Column(name = "CON_DT_CONSULTA")
    private LocalDateTime dataConsulta;
    
    // Motivo da consulta.
    @NotBlank
    @Size(min = 10, max = 255)
    @Column(name = "CON_MOTIVO")
    private String motivo;
    
    
    @Size(min = 10, max = 255)
    @Column(name = "CON_DIAGNOSTICO") //Nullable TRUE, pois pode ser preenchido posteriormente
    private String diagnostico;
    
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "ANI_ID")    
    private Animal animal;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "VET_ID")
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

   @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Consulta c = (Consulta) o;
    return idConsulta != null && idConsulta.equals(c.idConsulta);
}

@Override
public int hashCode() {
    return getClass().hashCode();
}

    
}
