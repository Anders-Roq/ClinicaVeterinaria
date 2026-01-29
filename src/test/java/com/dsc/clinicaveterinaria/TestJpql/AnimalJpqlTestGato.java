package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Animal;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AnimalJpqlTestGato extends BaseTest {

    @Test
    public void GatoQuantidadeTeste() {

        char sexo = 'F';

        String jqplContar = "SELECT COUNT (g) FROM Gato g WHERE g.sexo = :sexo";
        TypedQuery<Long> queryContar = em.createQuery(jqplContar, Long.class).
                setParameter("sexo", sexo);

        Long quantidade = queryContar.getSingleResult();
 
        assertEquals(3L, quantidade);
    }
    
    @Test
    public void GatoPorClienteTeste(){
        
        String jpql = " SELECT a FROM Animal a JOIN a.cliente c WHERE c.nome = :nomeCliente";
        TypedQuery<Animal> query = em.createQuery(jpql, Animal.class);
        query.setParameter("nomeCliente", "Carlos");
        List<Animal> animais = query.getResultList();

        assertEquals(3, animais.size());
       
    }

    @Test
    public void GatoIdadeEspecificaTeste(){
        String jpql = """
            SELECT g.nome, g.dataNascimento, 
                   FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', g.dataNascimento) as idade
            FROM Gato g 
            WHERE FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', g.dataNascimento) < 2
            ORDER BY idade
            """;
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        List<Long> resultados = query.getResultList();
        
        assertEquals(1L,resultados.size());

    }
}
