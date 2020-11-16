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
//    savingEntities();
//    retrievingEntities();
//    modifyingEntities();
//    removingEntities();
    mergingDetachedEntities();
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

  public static void retrievingEntities() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("infinite-finances");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx =  em.getTransaction();
    tx.begin();


    Bank bank1 = em.find(Bank.class, 1L); // null pointer exception if doesn't exist
    System.out.println(em.contains(bank1));
    System.out.println(bank1.getName());

    Bank bank = em.getReference(Bank.class, 1L); // returns a proxy (lazy). object not found if doesn't exist
    System.out.println(em.contains(bank));
    System.out.println(bank.getName());

    tx.commit();
    em.close();
    emf.close();
  }

  public static void modifyingEntities() {
    EntityManagerFactory emf = null;
    EntityManager em = null;
    EntityTransaction tx = null;

    try{
      emf = Persistence.createEntityManagerFactory("infinite-finances");
      em = emf.createEntityManager();
      tx = em.getTransaction();
      tx.begin();

      Bank bank = em.find(Bank.class, 1L);
      bank.setName("Another Demonstration");

      tx.commit();
    } catch(Exception e) {
      tx.rollback();
    } finally {
      em.close();
      emf.close();
    }
  }

  public static void removingEntities() {
    EntityManagerFactory emf = null;
    EntityManager em = null;
    EntityTransaction tx = null;

    try{
      emf = Persistence.createEntityManagerFactory("infinite-finances");
      em = emf.createEntityManager();
      tx = em.getTransaction();
      tx.begin();

      Bank bank = em.find(Bank.class, 2L);
      System.out.println(em.contains(bank));
      em.remove(bank);
      System.out.println(em.contains(bank));

      tx.commit();
    }catch(Exception e){
      tx.rollback();
    }finally{
      em.close();
      emf.close();
    }
  }

  public static void mergingDetachedEntities() {
    EntityManagerFactory emf = null;
    EntityManager em = null;
    EntityTransaction tx = null;

    try{
      emf = Persistence.createEntityManagerFactory("infinite-finances");
      em = emf.createEntityManager();
      tx = em.getTransaction();
      tx.begin();

      Bank bank = em.find(Bank.class, 1L); // get the instance for bank with id = 1
      em.detach(bank); // detaches bank
      System.out.println(em.contains(bank));

      bank.setName("Something else"); // changing name in detached bank
      Bank bank2 = em.merge(bank); // reattach the bank to the instance with id = 1 applying the changes made in the transient state and saves it in another variable

      bank.setName("Something else 2"); // bank is now detached and his changes won't be persisted
      tx.commit();
    } catch(Exception e){
      tx.rollback();
    } finally{
      em.close();
      emf.close();
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
