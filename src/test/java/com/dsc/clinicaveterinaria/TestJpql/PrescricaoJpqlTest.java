package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.Query;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrescricaoJpqlTest extends BaseTest {

    @Test
    public void medicamentosPermanentesTeste() {

        Query query = em.createQuery(
                "SELECT p.medicamento, p.frequencia "
                + "FROM Prescricao p "
                + "WHERE UPPER(p.duracao) LIKE '%PERMANENTE%' "
                + "ORDER BY p.medicamento"
        );

        List<Object[]> resultados = query.getResultList();

        assertNotNull(resultados);
        assertEquals(3, resultados.size());

        assertEquals("Ração especializada", resultados.get(0)[0]);
        assertEquals("3 vez ao dia", resultados.get(0)[1]);

        assertEquals("Ração hipoalergênica", resultados.get(1)[0]);
        assertEquals("2 vezes ao dia", resultados.get(1)[1]);

        assertEquals("Suplementos", resultados.get(2)[0]);
        assertEquals("1 vez ao dia", resultados.get(2)[1]);
    }
    
    @Test
    public void medicamentoVeterinarioFrequenteTest(){
    
    }

}
