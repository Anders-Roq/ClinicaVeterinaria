package com.dsc.clinicaveterinaria;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author marcelo
 */

@Entity
@DiscriminatorValue("CACHORRO")
@Table(name = "CACHORRO")
@PrimaryKeyJoinColumn(name = "ANI_ID")
public class Cachorro extends Animal {
    
    public Cachorro () {}
    
    public Cachorro(String nome, String especie, String raca, char sexo, LocalDate dataNascimento, Cliente cliente) {
        super(nome, especie, raca, sexo, dataNascimento, cliente);
    }
    
    
    
    
    
    
}
