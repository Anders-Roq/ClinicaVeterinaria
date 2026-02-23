
package com.dsc.clinicaveterinaria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        
        //Clientes
        Cliente cliente1 = new Cliente("José", "925.225.900-73", "999999999", "jose@mail.com", "Rua Só Nós Dois, 123");       
        Cliente cliente2 = new Cliente("Maria", "642.161.320-90", "888888888", "maria@mail.com", "Av. Deus Te Guarde, 69");
        Cliente cliente3 = new Cliente("Fulano", "847.453.530-14", "777777777", "fulano@gmail.com", "Rua Borboletas Psicodélicas, 333");
        Cliente cliente4 = new Cliente("Ana", "739.706.610-05", "666666666", "ana@mail.com", "Rua das Flores, 456");
        Cliente cliente5 = new Cliente("Carlos", "268.218.560-60", "555555555", "carlos@mail.com", "Av. Principal, 789");

        //Animais
        Gato g1 = new Gato("Fofinho", "Gato", "Laranja", 'M', LocalDate.of(2020, 5, 10), cliente1, true, true);
        Cachorro c1 = new Cachorro("Rex", "Cachorro", "Pastor Alemão", 'M', LocalDate.of(2022, 7, 1), cliente1, "Grande", true);
        Gato g2 = new Gato("Luna", "Gato", "Branca", 'F', LocalDate.of(2019, 1, 1), cliente2, true, false);
        Cachorro c2 = new Cachorro("Chuchu", "Cachorro", "Pug", 'M', LocalDate.of(2021, 5, 9), cliente3, "Pequeno", true);
        Cachorro c3 = new Cachorro("Mel", "Cachorro", "Golden Retriever", 'F', LocalDate.of(2020, 3, 15), cliente4, "Grande", true);
        Cachorro c4 = new Cachorro("Thor", "Cachorro", "Bulldog", 'M', LocalDate.of(2018, 8, 20), cliente4, "Medio", false);
        Gato g3 = new Gato("Nina", "Gato", "Siamesa", 'F', LocalDate.of(2022, 2, 14), cliente5, false, true);
        Cachorro c5 = new Cachorro("Bob", "Cachorro", "Labrador", 'M', LocalDate.of(2019, 11, 30), cliente5, "Grande", true);
        
        // Animais dos clientes
        cliente1.setAnimais(new ArrayList<>(List.of(g1, c1)));
        cliente2.setAnimais(new ArrayList<>(List.of(g2)));
        cliente3.setAnimais(new ArrayList<>(List.of(c2)));
        cliente4.setAnimais(new ArrayList<>(List.of(c3, c4)));
        cliente5.setAnimais(new ArrayList<>(List.of(g3, c5)));
        
        // Veterinarios
        Veterinario vet1 = new Veterinario("Dr. João Silva", "CRMV-12345", "Clínica Geral", "111111111", "joao.silva@vet.com");
        Veterinario vet2 = new Veterinario("Dra. Maria Santos", "CRMV-67890", "Cirurgia", "222222222", "maria.santos@vet.com");
        Veterinario vet3 = new Veterinario("Dr. Pedro Costa", "CRMV-11111", "Dermatologia", "333333333", "pedro.costa@vet.com");
        Veterinario vet4 = new Veterinario("Dra. Julia Oliveira", "CRMV-22222", "Oftalmologia", "444444444", "julia.oliveira@vet.com");

        // Consultas
        Consulta consulta1 = new Consulta(
            LocalDateTime.of(2024, 1, 15, 10, 0),
            "Check-up anual",
            "Animal saudável, vacinação em dia",
            c1,
            vet1
        );
        
        Consulta consulta2 = new Consulta(
            LocalDateTime.of(2024, 2, 20, 14, 30),
            "Ferida na pata",
            "Ferida tratada e cicatrizando bem",
            g1,
            vet1
        );
        
        Consulta consulta3 = new Consulta(
            LocalDateTime.of(2024, 3, 10, 9, 0),
            "Cirurgia de castração",
            "Cirurgia realizada com sucesso",
            c2,
            vet2
        );
        
        Consulta consulta4 = new Consulta(
            LocalDateTime.of(2024, 3, 25, 11, 0),
            "Consulta dermatológica",
            "Alergia alimentar diagnosticada",
            g2,
            vet3
        );
        
        Consulta consulta5 = new Consulta(
            LocalDateTime.of(2024, 4, 5, 15, 0),
            "Vacinação",
            "Vacinas atualizadas",
            g3,
            vet1
        );
        
        Consulta consulta6 = new Consulta(
            LocalDateTime.of(2024, 4, 18, 10, 30),
            "Problema nos olhos",
            "Conjuntivite tratada",
            c3,
            vet4
        );
        
        Consulta consulta7 = new Consulta(
            LocalDateTime.of(2024, 5, 2, 13, 0),
            "Check-up",
            "Animal em bom estado de saúde",
            c4,
            vet1
        );
        
        Consulta consulta8 = new Consulta(
            LocalDateTime.of(2024, 5, 15, 16, 0),
            "Dor nas articulações",
            "Artrite diagnosticada, tratamento iniciado",
            c5,
            vet2
        );
        
        // Prescrições
        Prescricao presc1 = new Prescricao("Antibiótico", "250mg", "A cada 12 horas", "7 dias", consulta2);
        Prescricao presc2 = new Prescricao("Anti-inflamatório", "50mg", "A cada 8 horas", "5 dias", consulta2);
        Prescricao presc3 = new Prescricao("Ração hipoalergênica", "1 xícara", "2 vezes ao dia", "Permanente", consulta4);
        Prescricao presc4 = new Prescricao("Colírio", "2 gotas", "3 vezes ao dia", "10 dias", consulta6);
        Prescricao presc5 = new Prescricao("Analgésico", "100mg", "A cada 12 horas", "15 dias", consulta8);
        Prescricao presc6 = new Prescricao("Suplemento de colágeno", "1 comprimido", "1 vez ao dia", "30 dias", consulta8);
        
        // Prescrições nas consultas
        consulta2.setPrescricoes(new ArrayList<>(List.of(presc1, presc2)));
        consulta4.setPrescricoes(new ArrayList<>(List.of(presc3)));
        consulta6.setPrescricoes(new ArrayList<>(List.of(presc4)));
        consulta8.setPrescricoes(new ArrayList<>(List.of(presc5, presc6)));
        
        // Pagamentos
        Pagamento pagamento1 = new Pagamento(150.00, "Dinheiro", LocalDateTime.of(2024, 1, 15, 10, 30), consulta1);
        Pagamento pagamento2 = new Pagamento(200.00, "Cartão de Débito", LocalDateTime.of(2024, 2, 20, 14, 45), consulta2);
        Pagamento pagamento3 = new Pagamento(500.00, "Cartão de Crédito", LocalDateTime.of(2024, 3, 10, 9, 30), consulta3);
        Pagamento pagamento4 = new Pagamento(180.00, "PIX", LocalDateTime.of(2024, 3, 25, 11, 15), consulta4);
        Pagamento pagamento5 = new Pagamento(120.00, "Dinheiro", LocalDateTime.of(2024, 4, 5, 15, 10), consulta5);
        Pagamento pagamento6 = new Pagamento(160.00, "Cartão de Débito", LocalDateTime.of(2024, 4, 18, 10, 45), consulta6);
        Pagamento pagamento7 = new Pagamento(140.00, "PIX", LocalDateTime.of(2024, 5, 2, 13, 15), consulta7);
        Pagamento pagamento8 = new Pagamento(220.00, "Cartão de Crédito", LocalDateTime.of(2024, 5, 15, 16, 15), consulta8);
        
        // Pagamentos nas consultas
        consulta1.setPagamento(pagamento1);
        consulta2.setPagamento(pagamento2);
        consulta3.setPagamento(pagamento3);
        consulta4.setPagamento(pagamento4);
        consulta5.setPagamento(pagamento5);
        consulta6.setPagamento(pagamento6);
        consulta7.setPagamento(pagamento7);
        consulta8.setPagamento(pagamento8);
        
        // Consultas nos veterinários
        vet1.setConsultas(new ArrayList<>(List.of(consulta1, consulta2, consulta5, consulta7)));
        vet2.setConsultas(new ArrayList<>(List.of(consulta3, consulta8)));
        vet3.setConsultas(new ArrayList<>(List.of(consulta4)));
        vet4.setConsultas(new ArrayList<>(List.of(consulta6)));

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            emf = Persistence.createEntityManagerFactory("clinicaveterinariaDB");
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            
            // Persistindo clientes
            em.persist(cliente1);
            em.persist(cliente2);
            em.persist(cliente3);
            em.persist(cliente4);
            em.persist(cliente5);
            
            // Persistindo animais
            em.persist(c1);
            em.persist(g1);
            em.persist(c2);
            em.persist(g2);
            em.persist(c3);
            em.persist(g3);
            em.persist(c4);
            em.persist(c5);
            
            // Persistindo veterinários
            em.persist(vet1);
            em.persist(vet2);
            em.persist(vet3);
            em.persist(vet4);
            
            // Persistindo consultas
            em.persist(consulta1);
            em.persist(consulta2);
            em.persist(consulta3);
            em.persist(consulta4);
            em.persist(consulta5);
            em.persist(consulta6);
            em.persist(consulta7);
            em.persist(consulta8);
            
            // Persistindo prescrições
            em.persist(presc1);
            em.persist(presc2);
            em.persist(presc3);
            em.persist(presc4);
            em.persist(presc5);
            em.persist(presc6);
            
            // Persistindo pagamentos
            em.persist(pagamento1);
            em.persist(pagamento2);
            em.persist(pagamento3);
            em.persist(pagamento4);
            em.persist(pagamento5);
            em.persist(pagamento6);
            em.persist(pagamento7);
            em.persist(pagamento8);
            
            et.commit();
            System.out.println("Dados cadastrados com sucesso!");

        } catch (RuntimeException ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }

    }

}
