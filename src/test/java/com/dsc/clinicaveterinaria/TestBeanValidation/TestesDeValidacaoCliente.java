package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.Cliente;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestesDeValidacaoCliente extends BaseTest{
    
    @Test
    public void CadastrarClienteInvalido(){
                Cliente cliente = new Cliente(
                "Na",
                "000.000.000-00",
                "65656565",
                "TesteErradoGmail.com",
                " "
        );
        
        try{
            em.persist(cliente);
            em.flush();
        }catch(ConstraintViolationException ex){
            
            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
            violations.forEach(v -> System.out.println(v.getMessage()));
            assertEquals(6, violations.size());
            
        }
        
    }
    
    @Test
    public void AtualizarClienteInválido(){
        String novoEmail = " ";
        String telefone = "(81)984844599";
        Long id = 1L;
        Cliente cliente = em.find(Cliente.class, id);
        cliente.setEmail(novoEmail);
        cliente.setTelefone(telefone);
        
        try{
            em.flush();
        }
        catch(ConstraintViolationException ex){
            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
            violations.forEach(v -> System.out.println(v.getMessage()));
            assertEquals(4, violations.size());
       
        }
        
    }
    
}
