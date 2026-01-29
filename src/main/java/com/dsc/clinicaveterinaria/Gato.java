package com.dsc.clinicaveterinaria;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;


@Entity
@DiscriminatorValue("GATO")
@Table(name = "GATO")
@PrimaryKeyJoinColumn(name = "ANI_ID")

public class Gato extends Animal{
    
    @Column(name = "GATO_CAIXA", nullable = false, length = 5)
    private boolean gostaDeCaixa;
    @Column(name = "GATO_PREGUICOSO", nullable = false, length = 5)
    private boolean preguicoso;
    
    public Gato() {}

    public Gato(String nome, String especie, String raca, char sexo, LocalDate dataNascimento, Cliente cliente, boolean gostDeCaixa, boolean preguicoso) {
        super(nome, especie, raca, sexo, dataNascimento, cliente);
        this.gostaDeCaixa = gostaDeCaixa;
        this.preguicoso = preguicoso;        
    }

    public boolean isGostaDeCaixa() {
        return gostaDeCaixa;
    }

    public void setGostaDeCaixa(boolean gostaDeCaixa) {
        this.gostaDeCaixa = gostaDeCaixa;
    }

    public boolean isPreguicoso() {
        return preguicoso;
    }

    public void setPreguicoso(boolean preguicoso) {
        this.preguicoso = preguicoso;
    }
    
    
    
}
