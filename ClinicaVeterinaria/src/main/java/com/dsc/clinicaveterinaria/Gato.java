/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
@DiscriminatorValue("GATO")
@Table(name = "GATO")
@PrimaryKeyJoinColumn(name = "ANI_ID")
public class Gato extends Animal{
    
    public Gato() {}

    public Gato(String nome, String especie, String raca, char sexo, LocalDate dataNascimento, Cliente cliente) {
        super(nome, especie, raca, sexo, dataNascimento, cliente);
    }
}
