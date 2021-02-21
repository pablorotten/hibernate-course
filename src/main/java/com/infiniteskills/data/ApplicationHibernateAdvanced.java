package com.infiniteskills.data;

import com.infiniteskills.data.entities.*;
import com.infiniteskills.data.entities.ids.CurrencyId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Calendar;
import java.util.Date;

public class ApplicationHibernateAdvanced {

  public static void main(String[] args) {

    SessionFactory sessionFactory = null;
    Session session = null;
    Session session2 = null;
    org.hibernate.Transaction tx = null;
    org.hibernate.Transaction tx2 = null;

    try {
      sessionFactory = HibernateUtil.getSessionFactory();
      session = sessionFactory.openSession();
//      compoundPKDemo(session, session2, tx, tx2, sessionFactory);
      compoundJoinColumnsDemo(session, tx, sessionFactory);

    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
      if (tx2 != null) {
        tx2.rollback();
      }
    } finally {
      session.close();
      sessionFactory.close();
    }
  }


  public static void compoundPKDemo(Session session, Session session2, org.hibernate.Transaction tx, org.hibernate.Transaction tx2, SessionFactory sessionFactory) {
    tx = session.beginTransaction();

    Currency currency = new Currency();
    currency.setCountryName("United Kingdom");
    currency.setName("Pound");
    currency.setSymbol("£");

    session.persist(currency);
    tx.commit();

    session2 = sessionFactory.openSession();
    tx2 = session2.beginTransaction();

    Currency dbCurrency = (Currency) session2.get(Currency.class,
            new CurrencyId("Pound", "United Kingdom"));
    System.out.println(dbCurrency.getName());

    tx2.commit();
  }


  public static void compoundJoinColumnsDemo(Session session, org.hibernate.Transaction tx, SessionFactory sessionFactory) {
    tx = session.beginTransaction();

    Currency currency = new Currency();
    currency.setCountryName("United Kingdom");
    currency.setName("Pound");
    currency.setSymbol("£");

    Market market = new Market();
    market.setMarketName("London Stock Exchange");
    market.setCurrency(currency);

    session.persist(market);
    tx.commit();

    Market dbMarket = (Market) session.get(Market.class, market.getMarketId());
    System.out.println(dbMarket.getCurrency().getName());
  }
}
