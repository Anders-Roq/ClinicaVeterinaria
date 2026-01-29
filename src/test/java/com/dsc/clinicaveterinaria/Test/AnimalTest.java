package com.dsc.clinicaveterinaria.Test;

import com.dsc.clinicaveterinaria.Animal;
import com.dsc.clinicaveterinaria.Cachorro;
import com.dsc.clinicaveterinaria.Cliente;
import com.dsc.clinicaveterinaria.Gato;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.TypedQuery;

public class AnimalTest extends BaseTest { 

    @Test
    public void testPersistirCachorro() {
        // Busca um cliente existente por CPF em vez de ID fixo
        TypedQuery<Cliente> query = em.createQuery(
            "SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class);
        query.setParameter("cpf", "925.225.900-73");
        Cliente cliente = query.getSingleResult();
        
        assertNotNull(cliente, "Cliente com CPF 925.225.900-73 deve existir no dataset");
        
        Cachorro cachorro = new Cachorro();
        cachorro.setNome("Toby");
        cachorro.setEspecie("Cachorro");
        cachorro.setRaca("Labrador");
        cachorro.setSexo('M');
        cachorro.setDataNascimento(LocalDate.of(2020, 5, 10));
        cachorro.setCliente(cliente);
        cachorro.setPorte("Grande");
        cachorro.setAdestrado(true);
        

        em.persist(cachorro);
        em.flush();
        em.clear();

        // Busca por query em vez de ID fixo
        TypedQuery<Cachorro> cachorroQuery = em.createQuery(
            "SELECT c FROM Cachorro c WHERE c.nome = :nome", Cachorro.class);
        cachorroQuery.setParameter("nome", "Toby");
        Cachorro cachorroSalvo = cachorroQuery.getSingleResult();

        assertNotNull(cachorroSalvo);
        assertEquals("Toby", cachorroSalvo.getNome());
        assertEquals(cliente.getIdCliente(), cachorroSalvo.getCliente().getIdCliente());
    }

    @Test
    public void testPersistirGato() {
        // Busca cliente por CPF
        TypedQuery<Cliente> query = em.createQuery(
            "SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class);
        query.setParameter("cpf", "642.161.320-90");
        Cliente cliente = query.getSingleResult();

        assertNotNull(cliente, "Cliente com CPF 642.161.320-90 deve existir no dataset");

        Gato gato = new Gato();
        gato.setNome("Garfield");
        gato.setEspecie("Gato");
        gato.setRaca("Persa");
        gato.setSexo('M');
        gato.setDataNascimento(LocalDate.of(2023, 10, 25));
        gato.setCliente(cliente);
        gato.setGostaDeCaixa(true);
        gato.setPreguicoso(true);

        em.persist(gato);
        em.flush();
        em.clear(); 

        // Busca por query
        TypedQuery<Gato> gatoQuery = em.createQuery(
            "SELECT g FROM Gato g WHERE g.nome = :nome", Gato.class);
        gatoQuery.setParameter("nome", "Garfield");
        Gato gatoSalvo = gatoQuery.getSingleResult();
        
        assertNotNull(gatoSalvo);
        assertEquals("Garfield", gatoSalvo.getNome());
    }
    
    @Test
    public void testRelacionamentoConsultas() {
        // Busca animal por nome em vez de ID fixo
        TypedQuery<Animal> query = em.createQuery(
            "SELECT a FROM Animal a WHERE a.nome = :nome", Animal.class);
        query.setParameter("nome", "Fofinho");
        Animal animal = query.getSingleResult();

        assertNotNull(animal);
        assertNotNull(animal.getConsultas(), "A lista de consultas não pode ser nula.");
        assertEquals(1, animal.getConsultas().size(), "Fofinho deve ter 1 consulta associada.");
        assertEquals("Check-up anual", animal.getConsultas().get(0).getMotivo());
    }

    @Test
    public void testConsultarAnimalPorNome() {
        // Consulta o animal por nome
        TypedQuery<Animal> query = em.createQuery(
            "SELECT a FROM Animal a WHERE a.nome = :nome", Animal.class);
        query.setParameter("nome", "Fofinho");
        Animal animal = query.getSingleResult();

        assertNotNull(animal);
        assertEquals("Fofinho", animal.getNome());
        assertEquals("Gato", animal.getEspecie()); 
        assertTrue(animal instanceof Gato, "O Animal Fofinho deve ser uma instância de Gato.");

        assertNotNull(animal.getCliente());
        assertEquals("José", animal.getCliente().getNome());
    }

    @Test
    public void testConsultarGatoPorNome() {
        TypedQuery<Gato> query = em.createQuery(
            "SELECT g FROM Gato g WHERE g.nome = :nome", Gato.class);
        query.setParameter("nome", "Fofinho");
        Gato gato = query.getSingleResult();

        assertNotNull(gato);
        assertEquals("Fofinho", gato.getNome());
        assertEquals("Gato", gato.getEspecie());
        assertEquals("Laranja", gato.getRaca());
    }

    @Test
    public void testConsultarCachorroPorNome() {
        TypedQuery<Cachorro> query = em.createQuery(
            "SELECT c FROM Cachorro c WHERE c.nome = :nome", Cachorro.class);
        query.setParameter("nome", "Rex");
        Cachorro cachorro = query.getSingleResult();

        assertNotNull(cachorro);
        assertEquals("Rex", cachorro.getNome());
        assertEquals("Cachorro", cachorro.getEspecie());
        assertEquals("Pastor Alemão", cachorro.getRaca());
    }

    @Test
    public void testHerancaGato() {
        TypedQuery<Gato> query = em.createQuery(
            "SELECT g FROM Gato g WHERE g.nome = :nome", Gato.class);
        query.setParameter("nome", "Luna");
        Gato gato = query.getSingleResult();

        assertNotNull(gato);
        assertEquals("Luna", gato.getNome());
        assertEquals("Gato", gato.getEspecie());
        assertEquals("Branca", gato.getRaca());
        assertTrue(gato instanceof Gato);
        assertTrue(gato instanceof Animal);
    }

    @Test
    public void testHerancaCachorro() {
        TypedQuery<Cachorro> query = em.createQuery(
            "SELECT c FROM Cachorro c WHERE c.nome = :nome", Cachorro.class);
        query.setParameter("nome", "Chuchu");
        Cachorro cachorro = query.getSingleResult();

        assertNotNull(cachorro);
        assertEquals("Chuchu", cachorro.getNome());
        assertEquals("Cachorro", cachorro.getEspecie());
        assertEquals("Pug", cachorro.getRaca());
        assertTrue(cachorro instanceof Cachorro);
        assertTrue(cachorro instanceof Animal);
    }
}