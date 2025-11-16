package com.dsc.clinicaveterinaria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.io.InputStream;
import java.sql.Connection;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction et;
    
    private static IDatabaseConnection dbConnection;

    @BeforeAll
    public static void setUpClass() throws Exception {
        // Inicializa o EMF 
        emf = Persistence.createEntityManagerFactory("clinicaveterinariaDB");
        
        // Configura o DBUnit
        EntityManager emForDbUnit = emf.createEntityManager();
        EntityTransaction etForDbUnit = emForDbUnit.getTransaction();
        
        try {
            etForDbUnit.begin(); 
            
            // Obtém a conexão JDBC subjacente
            Connection jdbcConnection = emForDbUnit.unwrap(java.sql.Connection.class);
            dbConnection = new DatabaseConnection(jdbcConnection);
            
            // Carrega o arquivo XML do dataset
            InputStream xmlFile = BaseTest.class.getClassLoader().getResourceAsStream("dataset.xml");
            if (xmlFile == null) {
                throw new RuntimeException("Não foi possível encontrar o arquivo 'dataset.xml' no 'src/test/resources'");
            }
            
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            IDataSet dataSet = builder.build(xmlFile);
            
            // Limpa e insere os dados
            DatabaseOperation.CLEAN_INSERT.execute(dbConnection, dataSet);
            
            etForDbUnit.commit();
            
        } catch (Exception e) {
            if (etForDbUnit != null && etForDbUnit.isActive()) {
                etForDbUnit.rollback();
            }
            throw e; 
        } finally {
            if (emForDbUnit != null) {
                emForDbUnit.close();
            }
        }
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
        if (dbConnection != null) {
            dbConnection.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    public void setUp() {
        // Cria um EntityManager e inicia uma transação ANTES de cada teste
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @AfterEach
    public void tearDown() {
        // Desfaz a transação APÓS cada teste (rollback)
        // Isso isola os testes e os faz rodar sempre contra o 
        // estado inicial limpo carregado pelo DBUnit.
        try {
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}