
package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.dsc.clinicaveterinaria.validation.CRMVValido;
import com.dsc.clinicaveterinaria.validation.TelefoneValido;


@Entity
@Table(name = "VETERINARIO")
public class Veterinario implements Serializable {

// Chave Primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VET_ID")
    private Long idVeterinario;

    @NotBlank(message = "{veterinario.nome.notblank}")
    @Size(min = 3, max = 100, message = "{veterinario.nome.size}")
    @Column(name = "VET_NOME")
    private String nome;
    
    @NotBlank(message = "{veterinario.crmv.notblank}")
    @CRMVValido
    @Size(max = 15, message = "{veterinario.crmv.size}")
    // CRMV é o identificador profissional, deve ser único.
    @Column(name = "VET_CRMV", unique = true)
    private String crmv;
    
    @NotBlank(message = "{veterinario.especialidade.notblank}")
    @Size(max = 50, message = "{veterinario.especialidade.size}")
    // Especialidade é obrigatória, mas não única.
    @Column(name = "VET_ESPECIALIDADE")
    private String especialidade;
    
    @NotBlank(message = "{veterinario.telefone.notblank}")
    @TelefoneValido
    @Size(min =9, max = 9, message = "{veterinario.telefone.size}") 
    // Telefone
    @Column(name = "VET_TELEFONE") 
    private String telefone;
    
    @NotBlank(message = "{veterinario.email.notblank}")
    @Email
    @Size(max = 30)
    @Column(name = "VET_EMAIL")
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
