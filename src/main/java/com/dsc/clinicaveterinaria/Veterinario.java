/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
/**
 *
 * @author dla19
 */
@Entity
@Table(name = "VETERINARIO")
public class Veterinario implements Serializable {

// Chave Primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VET_ID", nullable = false)
    private Long idVeterinario;

    
    @Column(name = "VET_NOME", nullable = false, length = 150)
    private String nome;
    
    // CRMV é o identificador profissional, deve ser único.
    @Column(name = "VET_CRMV", nullable = false, length = 15, unique = true)
    private String crmv;
    
    // Especialidade é obrigatória, mas não única.
    @Column(name = "VET_ESPECIALIDADE", nullable = false, length = 50)
    private String especialidade;
    
    // Telefone
    @Column(name = "VET_TELEFONE", nullable = true, length = 15) 
    private String telefone;
    
    
    @Column(name = "VET_EMAIL", nullable = true, length = 100)
    private String email;    
    
    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL)    
    private List<Consulta> consultas;
    
        public Veterinario() {}

    public Veterinario(String nome, String crmv, String especialidade, String telefone, String email) {
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.email = email;
    }
    
    
    public Long getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(Long idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Veterinario vet = (Veterinario) o;
    return idVeterinario != null && idVeterinario.equals(vet.idVeterinario);
}

@Override
public int hashCode() {
    return getClass().hashCode();
}


    
    
}
