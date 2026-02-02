package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Animal;
import com.dsc.clinicaveterinaria.Cliente;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientJpqlTest extends BaseTest {

    @Test
    public void BuscarClienteComAnimais() {
        //_JoinFetch
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT DISTINCT c "
                + "FROM Cliente c "
                + "LEFT JOIN FETCH c.animais "
                + "WHERE c.cpf = :cpf",
                Cliente.class
        );

        query.setParameter("cpf", "925.225.900-73");

        Cliente cliente = query.getSingleResult();

        assertNotNull(cliente);
        assertEquals("José", cliente.getNome());
        assertNotNull(cliente.getAnimais());

        assertEquals(3, cliente.getAnimais().size());
    }

    @Test
    public void ListarClientesQuePossuemMaisDeUmAnimal() {
        //Size (collection)
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c "
                + "FROM Cliente c "
                + "WHERE SIZE(c.animais) > 1 "
                + "ORDER BY c.nome",
                Cliente.class
        );

        List<Cliente> clientes = query.getResultList();

        assertNotNull(clientes);
        assertFalse(clientes.isEmpty());

        // Pelo dataset: pelo menos 3 clientes com mais de 1 animal
        assertTrue(clientes.size() >= 3);
    }

    @Test
    public void ContarAnimaisPorCliente() {
        //GROUP BY + COUNT + ORDER BY.
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT c.nome, COUNT(a) "
                + "FROM Cliente c JOIN c.animais a "
                + "GROUP BY c.nome "
                + "ORDER BY COUNT(a) DESC",
                Object[].class
        );

        List<Object[]> resultados = query.getResultList();

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());

        Object[] primeiro = resultados.get(0);
        String nome = (String) primeiro[0];
        Long qtd = (Long) primeiro[1];

        assertNotNull(nome);
        assertNotNull(qtd);
        assertTrue(qtd >= 2);
    }

    @Test
    public void BuscarClientesQuePossuemAnimalComNomeEspecifico() {
        //JOIN simples
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT DISTINCT c "
                + "FROM Cliente c "
                + "JOIN c.animais a "
                + "WHERE a.nome = :nomeAnimal",
                Cliente.class
        );

        query.setParameter("nomeAnimal", "Rex");

        List<Cliente> clientes = query.getResultList();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("José", clientes.get(0).getNome());
    }

    @Test
    public void VerificarSeAnimalPertenceAoCliente() {
        //Member OF
        Animal rex = em.createQuery(
                "SELECT a FROM Animal a WHERE a.nome = :nome",
                Animal.class
        ).setParameter("nome", "Rex")
                .getSingleResult();

        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c WHERE :animal MEMBER OF c.animais",
                Cliente.class
        );

        query.setParameter("animal", rex);

        Cliente cliente = query.getSingleResult();

        assertNotNull(cliente);
        assertEquals("José", cliente.getNome());
    }

    @Test
    public void BuscarClientesPorNomeIniciadoComATeste() {
        //Funções de string: UPPER, LIKE, TRIM. Busca clientes cujo nome começa com 'A' (desconsiderando espaços).

        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c "
                + "WHERE UPPER(TRIM(c.nome)) LIKE 'A%' "
                + "ORDER BY c.nome",
                Cliente.class
        );

        List<Cliente> clientes = query.getResultList();

        assertNotNull(clientes);
        // pelo dataset há pelo menos uma cliente que começa com 'A' (ex.: Ana, Alice etc.)
        assertTrue(clientes.size() >= 1);
        assertTrue(clientes.stream().allMatch(
                c -> c.getNome().toUpperCase().trim().startsWith("A")
        ));
    }

}
