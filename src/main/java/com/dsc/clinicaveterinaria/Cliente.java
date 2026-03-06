package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "CLIENTE")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLI_ID")
    private Long idCliente;
    

    @NotBlank(message = "{cliente.nome.notblank}")
    @Size(min = 3, max = 50, message = "{cliente.nome.size}")
    @Column(name = "CLI_NOME")
    private String nome;

    @NotNull(message = "{cliente.cpf.notnull}")
    @CPF(message = "{cliente.cpf}")
    @Column(name = "CLI_CPF", unique = true)
    private String cpf;

    @NotBlank(message = "{cliente.telefone.notblank}")
    @Pattern(regexp = "\\d{9}")
    @Size(min =9, max = 9, message = "{cliente.telefone.size}")
    @Column(name = "CLI_TELEFONE")
    private String telefone;

    @NotBlank(message = "{cliente.email.noblank}")
    @Email(message = "{cliente.email}")
    @Size(max = 30)
    @Column(name = "CLI_EMAIL")
    private String email;

    @NotBlank(message = "{cliente.endereco.noblank}")
    @Size(max = 255)
    @Column(name = "CLI_ENDERECO")
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
    
    
    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cliente cliente = (Cliente) o;
    return idCliente != null && idCliente.equals(cliente.idCliente);
}

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
