package com.infiniteskills.data;

import com.infiniteskills.data.entities.Account;
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
//      parametersDemo(em, tx, factory);
//      joinsDemo(em, tx, factory);
      functionsDemo(em, tx, factory);

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

  public static void joinsDemo(EntityManager em, EntityTransaction tx, EntityManagerFactory factory) {
    TypedQuery<Account> query = em.createQuery("select distinct a from Transaction t join t.account a where t.amount > 500 and t.transactionType = 'Deposit'",Account.class);

    List<Account> accounts = query.getResultList();

    for(Account a:accounts){
      System.out.println(a.getName());
    }

    tx.commit();
  }

  public static void functionsDemo(EntityManager em, EntityTransaction tx, EntityManagerFactory factory) {
    Query query = em.createQuery("select distinct t.account.name, "
            + "concat(concat(t.account.bank.name, ' '),t.account.bank.address.state)"
            + " from Transaction t"
            + " where t.amount > 500 and t.transactionType = 'Deposit'");

    List<Object[]> accounts = query.getResultList();

    for(Object[] a:accounts){
      System.out.println(a[0]);
      System.out.println(a[1]);
    }

    tx.commit();
  }

  public static void namedQueriesDemo(EntityManager em, EntityTransaction tx, EntityManagerFactory factory) {
    Query query = em.createNamedQuery("Account.byWithdrawlAmount");
    query.setParameter("amount", new BigDecimal("99"));

    List<Object[]> accounts = query.getResultList();

    for(Object[] a:accounts){
      System.out.println(a[0]);
      System.out.println(a[1]);
    }

    tx.commit();
  }
}
