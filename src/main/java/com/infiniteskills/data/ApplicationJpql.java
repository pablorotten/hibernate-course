package com.infiniteskills.data;

import com.infiniteskills.data.entities.Transaction;

import javax.persistence.*;

import java.util.List;

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
      writingQueries(em, tx, factory);

    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    } finally {
      em.close();
      factory.close();
    }
  }


  public static void writingQueries(EntityManager em, EntityTransaction tx, EntityManagerFactory factory) {
    TypedQuery<Transaction> query = em.createQuery("from Transaction t order by t.title", Transaction.class);
    List<Transaction> transactions = query.getResultList();

    for(Transaction t:transactions){
      System.out.println(t.getTitle());
    }
    tx.commit();
  }
}
