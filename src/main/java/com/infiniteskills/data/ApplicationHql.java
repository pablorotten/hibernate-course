package com.infiniteskills.data;

import com.infiniteskills.data.entities.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ApplicationHql {

  public static void main(String[] args) {

    SessionFactory factory = null;
    Session session = null;
    org.hibernate.Transaction tx = null;

    try {
      factory = HibernateUtil.getSessionFactory();
      session = factory.openSession();
      // TODO: functions here!
//      writingQueriesDemo(session, tx, factory);
//      expressionsAndOperatorsDemo(session, tx, factory);
//      parametersDemo(session, tx, factory);
//      joinsDemo(session, tx, factory);
      functionsDemo(session, tx, factory);

    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    } finally {
      session.close();
      factory.close();
    }
  }

  public static void writingQueriesDemo(Session session, org.hibernate.Transaction tx, SessionFactory factory) {
    factory = HibernateUtil.getSessionFactory();
    session = factory.openSession();
    tx = session.beginTransaction();

    Query query = session.createQuery("select t from Transaction t");
    List<Transaction> transactions = query.list();

    for (Transaction t:transactions) {
      System.out.println(t.getTitle());
    }
    tx.commit();
  }

  public static void expressionsAndOperatorsDemo(Session session, org.hibernate.Transaction tx, SessionFactory factory) {
    factory = HibernateUtil.getSessionFactory();
    session = factory.openSession();
    tx = session.beginTransaction();

    Query query = session.createQuery("SELECT t FROM Transaction t "
            + "WHERE t.amount > 75 and t.transactionType = 'Withdrawl'");
    List<Transaction> transactions = query.list();

    for (Transaction t:transactions) {
      System.out.println(t.getTitle());
    }
    tx.commit();
  }

  public static void parametersDemo(Session session, org.hibernate.Transaction tx, SessionFactory factory) {
    Scanner scanner = new Scanner(System.in);
    Query query = session.createQuery("select t from Transaction t "
            + "where t.amount > :amount and t.transactionType = 'Withdrawl'");
    System.out.println("Please specify an amount:");

    query.setParameter("amount", new BigDecimal(scanner.next()));
    List<Transaction> transactions = query.list();

    for (Transaction t : transactions) {
      System.out.println(t.getTitle());
    }

    tx.commit();
  }

  public static void joinsDemo(Session session, org.hibernate.Transaction tx, SessionFactory factory) {
    Query query = session.createQuery("select distinct t.account from Transaction t where t.amount > 500 and t.transactionType = 'Deposit'");

    List<Account> accounts = query.list();

    for(Account a:accounts){
      System.out.println(a.getName());
    }

    tx.commit();
  }

  public static void functionsDemo(Session session, org.hibernate.Transaction tx, SessionFactory factory) {

    Query query = session.createQuery("select distinct t.account from Transaction t"
            + " where t.amount > 500 and lower(t.transactionType) = 'deposit'");

    List<Account> accounts = query.list();

    for (Account a : accounts) {
      System.out.println(a.getName());
    }

    tx.commit();
  }
}
