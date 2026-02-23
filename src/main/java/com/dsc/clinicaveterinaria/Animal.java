package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.List;

@Entity
@Table(name = "ANIMAL")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_animal", discriminatorType = DiscriminatorType.STRING)
public abstract class Animal implements Serializable {

    // Chave Primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANI_ID")
    private Long idAnimal;

    // Atributos
    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "ANI_NOME")
    private String nome;

    @NotBlank
    @Size(max = 30)
    @Column(name = "ANI_ESPECIE")
    private String especie;

    @NotBlank
    @Size(max = 30)
    @Column(name = "ANI_RACA")
    private String raca;

    @NotNull
    @Column(name = "ANI_SEXO")
    private Character sexo;

    @NotNull
    @Column(name = "ANI_DT_NASCIMENTO")
    private LocalDate dataNascimento;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CLI_ID")
    private Cliente cliente;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Consulta> consultas;

    public Animal() {
    }

    public Animal(String nome, String especie, String raca, char sexo, LocalDate dataNascimento, Cliente cliente) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.cliente = cliente;
    }

    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    // Adicionar equals e hashCode baseados no idAnimal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return idAnimal != null && idAnimal.equals(animal.idAnimal);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}