package com.infiniteskills.data;

import com.infiniteskills.data.entities.Transaction;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ApplicationJpql {

  public static void main(String[] args) {

    EntityManagerFactory factory = null;
    EntityManager em = null;
    EntityTransaction tx = null;

    try {
      factory = Persistence.createEntityManagerFactory("infinite-finances");
      em = factory.createEntityManager();
      tx = em.getTransaction();
      tx.begin();
      // TODO: functions here!
//      writingQueriesDemo(em, tx, factory);
//      expressionsAndOperatorsDemo(em, tx, factory);
      parametersDemo(em, tx, factory);

    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    } finally {
      em.close();
      factory.close();
    }
  }


  public static void writingQueriesDemo(EntityManager em, EntityTransaction tx, EntityManagerFactory factory) {

    TypedQuery<Transaction> query = em.createQuery(
            "from Transaction t"
                    + " where (t.amount between 75 and 100) and t.title like '%s'"
                    + " order by t.title", Transaction.class);

    List<Transaction> transactions = query.getResultList();

    for(Transaction t:transactions){
      System.out.println(t.getTitle());
    }
    tx.commit();
  }

  public static void expressionsAndOperatorsDemo(EntityManager em, EntityTransaction tx, EntityManagerFactory factory) {

    TypedQuery<Transaction> query = em.createQuery(
            "from Transaction t"
                    + " where (t.amount between 75 and 100) and t.title like '%s'"
                    + " order by t.title", Transaction.class);

    List<Transaction> transactions = query.getResultList();

    for(Transaction t:transactions){
      System.out.println(t.getTitle());
    }
    tx.commit();
  }

  public static void parametersDemo(EntityManager em, EntityTransaction tx, EntityManagerFactory factory) {
    Scanner scanner = new Scanner(System.in);
    TypedQuery<Transaction> query = em.createQuery(
            "from Transaction t"
                    + " where (t.amount between ?1 and ?2) and t.title like '%s'"
                    + " order by t.title", Transaction.class);

    System.out.println("Please provide the first amount:");
    query.setParameter(1, new BigDecimal(scanner.next()));
    System.out.println("Please provide the second amount:");
    query.setParameter(2, new BigDecimal(scanner.next()));

    List<Transaction> transactions = query.getResultList();

    for (Transaction t : transactions) {
      System.out.println(t.getTitle());
    }

    tx.commit();
  }
}
