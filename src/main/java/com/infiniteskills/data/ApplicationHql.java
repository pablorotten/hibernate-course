package com.infiniteskills.data;

import com.infiniteskills.data.entities.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ApplicationHql {

  public static void main(String[] args) {

    SessionFactory factory = null;
    Session session = null;
    org.hibernate.Transaction tx = null;

    try {
      factory = HibernateUtil.getSessionFactory();
      session = factory.openSession();
      // TODO: functions here!
      writingQueries(session, tx, factory);

    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    } finally {
      session.close();
      factory.close();
    }
  }


  public static void writingQueries(Session session, org.hibernate.Transaction tx, SessionFactory factory) {
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
}
