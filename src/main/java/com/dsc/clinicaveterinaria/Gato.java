package com.dsc.clinicaveterinaria;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@DiscriminatorValue("GATO")
@Table(name = "GATO")
@PrimaryKeyJoinColumn(name = "ANI_ID")
public class Gato extends Animal {

    @NotNull(message = "{gato.caixa.notnull}")
    @Column(name = "GATO_CAIXA")
    private Boolean gostaDeCaixa;

    @NotNull(message = "{gato.preguicoso.notnull}")
    @Column(name = "GATO_PREGUICOSO")
    private Boolean preguicoso;

    public Gato() {}

    public Gato(String nome, String especie, String raca,
                char sexo, LocalDate dataNascimento,
                Cliente cliente, Boolean gostaDeCaixa,
                Boolean preguicoso) {

        super(nome, especie, raca, sexo, dataNascimento, cliente);
        this.gostaDeCaixa = gostaDeCaixa;
        this.preguicoso = preguicoso;
    }

    public Boolean getGostaDeCaixa() { return gostaDeCaixa; }
    public void setGostaDeCaixa(Boolean gostaDeCaixa) { this.gostaDeCaixa = gostaDeCaixa; }

    public Boolean getPreguicoso() { return preguicoso; }
    public void setPreguicoso(Boolean preguicoso) { this.preguicoso = preguicoso; }
}
    
    
    

