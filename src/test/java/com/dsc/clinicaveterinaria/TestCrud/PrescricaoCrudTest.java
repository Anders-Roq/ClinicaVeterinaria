package com.dsc.clinicaveterinaria.TestCrud;

import com.dsc.clinicaveterinaria.Prescricao;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class PrescricaoCrudTest extends BaseTest {

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void atualizarPrescricaoMerge() {

        String novoMedicamento = "Anti-inflamatório";
        String novaDosagem = "500mg a cada 8h";

        Prescricao prescricao = em.find(Prescricao.class, 1L);

        prescricao.setMedicamento(novoMedicamento);
        prescricao.setDosagem(novaDosagem);

        em.clear();

        prescricao = em.merge(prescricao);

        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        prescricao = em.find(Prescricao.class, 1L, props);

        assertEquals(novoMedicamento, prescricao.getMedicamento());
        assertEquals(novaDosagem, prescricao.getDosagem());
    }

    @Test
    public void atualizarPrescricao() {

        String novoMedicamento = "Vitamina";
        String novaDosagem = "1 comprimido por dia";

        Prescricao prescricao = em.find(Prescricao.class, 2L);

        prescricao.setMedicamento(novoMedicamento);
        prescricao.setDosagem(novaDosagem);

        em.flush();

        String jpql = "SELECT p FROM Prescricao p WHERE p = ?1";

        TypedQuery<Prescricao> query = em.createQuery(jpql, Prescricao.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        query.setParameter(1, prescricao);

        prescricao = query.getSingleResult();

        assertEquals(novoMedicamento, prescricao.getMedicamento());
        assertEquals(novaDosagem, prescricao.getDosagem());
    }

    @Test
    public void removerPrescricao() {

        Long id = 3L;
        Prescricao prescricao = em.find(Prescricao.class, id);

        assertNotNull(prescricao);

        em.remove(prescricao);

        prescricao = em.find(Prescricao.class, id);

        assertNull(prescricao);
    }

}
