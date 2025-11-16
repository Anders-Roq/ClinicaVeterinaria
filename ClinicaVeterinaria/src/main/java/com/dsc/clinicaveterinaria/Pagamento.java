/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
/**
 *
 * @author dla19
 */
@Entity
@Table(name = "PAGAMENTO")
public class Pagamento implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAG_ID", nullable = false)
    private Long id;
    
    
    @Column(name = "PAG_VALOR_TOTAL", nullable = false) 
    private double valorTotal;
    

    @Column(name = "PAG_METODO", nullable = false, length = 50)
    private String metodoPagamento;
    

    @Column(name = "PAG_DT_PAGAMENTO", nullable = false)
    private LocalDateTime dataPagamento;


    @OneToOne
    // UNIQUE TRUE para garantir o One-to-One.
    @JoinColumn(name = "CON_ID", nullable = false, unique = true) 
    private Consulta consulta;

    public Pagamento() {}

    public Pagamento(double valorTotal, String metodoPagamento, LocalDateTime dataPagamento, Consulta consulta) {
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.dataPagamento = dataPagamento;
        this.consulta = consulta;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

 
}
