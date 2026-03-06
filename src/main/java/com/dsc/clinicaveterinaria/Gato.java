package com.dsc.clinicaveterinaria;

import com.dsc.clinicaveterinaria.validation.PelagemValido;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
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

    
    @NotBlank(message = "{gato.pelagem.notblank}")
    @Size(max = 20, message = "{gato.pelagem.size}")
    @PelagemValido
    @Column(name = "GATO_PELAGEM")
    private String pelagem;

    public Gato() {
    }

    public Gato(String nome, String especie, String raca,
            char sexo, LocalDate dataNascimento,
            Cliente cliente, Boolean gostaDeCaixa,
            Boolean preguicoso, String pelagem) {

        super(nome, especie, raca, sexo, dataNascimento, cliente);
        this.gostaDeCaixa = gostaDeCaixa;
        this.preguicoso = preguicoso;
        this.pelagem = pelagem;
    }

    public Boolean getGostaDeCaixa() {
        return gostaDeCaixa;
    }

    public void setGostaDeCaixa(Boolean gostaDeCaixa) {
        this.gostaDeCaixa = gostaDeCaixa;
    }

    public Boolean getPreguicoso() {
        return preguicoso;
    }

    public void setPreguicoso(Boolean preguicoso) {
        this.preguicoso = preguicoso;
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }
}
