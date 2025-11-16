package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "CLIENTE")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLI_ID", nullable = false)
    private Long idCliente;

    @Column(name = "CLI_NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "CLI_CPF", nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "CLI_TELEFONE", nullable = false, length = 20)
    private String telefone;

    @Column(name = "CLI_EMAIL", nullable = true, length = 100)
    private String email;

    @Column(name = "CLI_ENDERECO", nullable = false, length = 255)
    private String endereco;

    //faz o find (do teste) carregar os animais imediatamente.
    @OneToMany(
            mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Animal> animais;

    public Cliente() {
    }

    public Cliente(String nome, String cpf, String telefone, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    //Garante que o ArrayList não tenha NullPointerException, fazendo com que o DBUnit carregue os dados sem o JPA inicializar a coleção.
    public List<Animal> getAnimais() {
        if (animais == null) {
            animais = new ArrayList<>();
        }
        return animais;
    }

   
    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }
}
