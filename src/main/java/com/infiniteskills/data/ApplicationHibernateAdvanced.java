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
      currencyDemo(session, session2, tx, tx2, sessionFactory);

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


  public static void currencyDemo(Session session, Session session2, org.hibernate.Transaction tx, org.hibernate.Transaction tx2, SessionFactory sessionFactory) {
    tx = session.beginTransaction();

    Currency currency = new Currency();
    currency.setCountryName("Spain");
    currency.setName("Peseta");
    currency.setSymbol("pts");

    session.persist(currency);
    tx.commit();

    session2 = sessionFactory.openSession();
    tx2 = session2.beginTransaction();

    Currency dbCurrency = (Currency) session2.get(Currency.class,
            new CurrencyId("Peseta", "Spain"));
    System.out.println(dbCurrency.getName());

    tx2.commit();
  }

  private static Date getMyBirthday() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 1984);
    calendar.set(Calendar.MONTH, 6);
    calendar.set(Calendar.DATE, 19);
    return calendar.getTime();
  }
}
