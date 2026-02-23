package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Animal;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalJpqlTest extends BaseTest {

    @Test
    public void BuscarAnimalPorNome() {

        TypedQuery<Animal> query = em.createQuery(
                "SELECT a FROM Animal a WHERE a.nome = :nome",
                Animal.class
        );

        query.setParameter("nome", "Rex");

        Animal animal = query.getSingleResult();

        assertNotNull(animal);
        assertEquals("Cachorro", animal.getEspecie());
        assertEquals("Pastor Alemão", animal.getRaca());
        assertEquals("José", animal.getCliente().getNome());
    }

    @Test
    public void BuscarAnimalComCliente() {
        //JoinFetch
        TypedQuery<Animal> query = em.createQuery(
                "SELECT a "
                + "FROM Animal a "
                + "JOIN FETCH a.cliente c "
                + "WHERE a.nome = :nome",
                Animal.class
        );

        query.setParameter("nome", "Luna");

        Animal animal = query.getSingleResult();

        assertNotNull(animal);
        assertEquals("Luna", animal.getNome());
        assertNotNull(animal.getCliente());
        assertEquals("Maria", animal.getCliente().getNome());
    }

    @Test
    public void ListarAnimaisPorCpfDoCliente() {

        TypedQuery<Animal> query = em.createQuery(
                "SELECT a "
                + "FROM Animal a "
                + "JOIN a.cliente c "
                + "WHERE c.cpf = :cpf "
                + "ORDER BY a.nome",
                Animal.class
        );

        query.setParameter("cpf", "925.225.900-73"); // José

        List<Animal> animais = query.getResultList();

        assertNotNull(animais);
        assertFalse(animais.isEmpty());

        // no dataset: José tem Fofinho e Rex e Josephine (gato extra)
        assertTrue(animais.size() >= 2);

        // valida que todos pertencem ao José
        assertTrue(animais.stream().allMatch(a -> a.getCliente().getCpf().equals("925.225.900-73")));
    }

    @Test
    public void BuscarAnimaisPorRaca() {
        //Like
        TypedQuery<Animal> query = em.createQuery(
                "SELECT a FROM Animal a WHERE LOWER(a.raca) LIKE :raca",
                Animal.class
        );

        query.setParameter("raca", "%labrador%");

        List<Animal> animais = query.getResultList();

        assertNotNull(animais);
        assertFalse(animais.isEmpty());
        assertTrue(animais.stream().anyMatch(a -> a.getNome().equals("Bob")));
    }

}
