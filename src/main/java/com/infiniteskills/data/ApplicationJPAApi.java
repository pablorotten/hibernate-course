package com.infiniteskills.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.infiniteskills.data.entities.Bank;

import java.util.Date;

public class ApplicationJPAApi {

  public static void main(String[] args) {
//    JPATest();
    savingEntities();
  }

  public static void JPATest() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("infinite-finances");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx =  em.getTransaction();
    tx.begin();

    Bank bank = createBank();

    em.persist(bank);
    tx.commit();
    em.close();
    emf.close();
  }

  public static void savingEntities() {
    EntityManagerFactory factory = null; // similar to the SessionFactory in Hibernate, creates entityManagers instead of sessions
    EntityManager em = null; // like the session in Hibernate. Used to perform operations on the entities
    EntityTransaction tx = null; // similar to the transaction interface in Hibernate

    try {
      factory = Persistence.createEntityManagerFactory("infinite-finances");
      em = factory.createEntityManager();
      tx = em.getTransaction();
      tx.begin();

      Bank bank = createBank();

      em.persist(bank);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
  }

  private static Bank createBank() {
    Bank bank = new Bank();
    bank.setName("First United Federal");
    bank.setAddressLine1("103 Washington Plaza");
    bank.setAddressLine2("Suite 332");
    bank.setCity("New York");
    bank.setCreatedBy("Kevin Bowersox");
    bank.setCreatedDate(new Date());
    bank.setInternational(false);
    bank.setLastUpdatedBy("Kevin Bowersox");
    bank.setLastUpdatedDate(new Date());
    bank.setState("NY");
    bank.setZipCode("10000");
    return bank;
  }


}
