package com.dsc.clinicaveterinaria;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "PAGAMENTO")
public class Pagamento implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAG_ID")
    private Long idPagamento;
    
    @Positive    
    @Column(name = "PAG_VALOR_TOTAL") 
    private double valorTotal;
    
    @NotBlank
    @Size(min = 3, max = 30)    
    @Column(name = "PAG_METODO")
    private String metodoPagamento;
    

    @NotNull
    @Column(name = "PAG_DT_PAGAMENTO")
    private LocalDateTime dataPagamento;


    @NotNull
    @OneToOne
    // UNIQUE TRUE para garantir o One-to-One.
    @JoinColumn(name = "CON_ID", unique = true) 
    private Consulta consulta;

    public Pagamento() {}

    public Pagamento(double valorTotal, String metodoPagamento, LocalDateTime dataPagamento, Consulta consulta) {
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.dataPagamento = dataPagamento;
        this.consulta = consulta;
    }
    

    public Long getId() {
        return idPagamento;
    }

    public void setId(Long id) {
        this.idPagamento = id;
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

 @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pagamento p = (Pagamento) o;
    return idPagamento != null && idPagamento.equals(p.idPagamento);
}

@Override
public int hashCode() {
    return getClass().hashCode();
}

}
