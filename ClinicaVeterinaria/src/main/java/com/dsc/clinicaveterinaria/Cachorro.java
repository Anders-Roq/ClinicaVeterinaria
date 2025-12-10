package com.dsc.clinicaveterinaria;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;


@Entity
@DiscriminatorValue("CACHORRO")
@Table(name = "CACHORRO")
@PrimaryKeyJoinColumn(name = "ANI_ID")
public class Cachorro extends Animal {
    
    @Column(name = "CACHORRO_PORTE", nullable = false, length = 30)
    private String porte;
    @Column(name = "CACHORRO_ADESTRADO", nullable = false, length = 30)
    private boolean adestrado;
    
    public Cachorro () {}
    
    public Cachorro(String nome, String especie, String raca, char sexo, LocalDate dataNascimento, Cliente cliente, String porte, boolean adestrado) {
        super(nome, especie, raca, sexo, dataNascimento, cliente);
        this.porte = porte;
        this.adestrado = adestrado;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public boolean isAdestrado() {
        return adestrado;
    }

    public void setAdestrado(boolean adestrado) {
        this.adestrado = adestrado;
    }
   
    
    
    
    
    
    
    
}
