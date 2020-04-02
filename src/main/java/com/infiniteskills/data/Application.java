package com.infiniteskills.data;

import java.util.Calendar;
import java.util.Date;

import com.infiniteskills.data.entities.Address;
import com.infiniteskills.data.entities.Bank;
import com.infiniteskills.data.entities.TimeTest;
import org.hibernate.Session;
import com.infiniteskills.data.entities.User;
import org.hibernate.Transaction;

public class Application {

  public static void main(String[] args) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
//      timeTest(session);
//      userTest(session);
//      userAgeTest(session);
//      bankTest(session);
//      userAddressTest(session);
      bankContactTest(session);

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }finally{
      session.close();
      HibernateUtil.getSessionFactory().close();
      System.exit(0);
    }

  }

  public static void userTest(Session session) {
    session.getTransaction().begin();

    User user = new User();
    user.setBirthDate(getMyBirthday());
    user.setCreatedBy("kevin");
    user.setCreatedDate(new Date());
    user.setEmailAddress("kmb385@yahoo.com");
    user.setFirstName("Kevin");
    user.setLastName("Bowersox");
    user.setLastUpdatedBy("kevin");
    user.setLastUpdatedDate(new Date());

    session.save(user);
  }

  public static void userAgeTest(Session session) {
    session.getTransaction().begin();

    User user = new User();
    user.setBirthDate(getMyBirthday());
    user.setCreatedBy("kevin");
    user.setCreatedDate(new Date());
    user.setEmailAddress("kmb385@yahoo.com");
    user.setFirstName("Kevin");
    user.setLastName("Bowersox");
    user.setLastUpdatedBy("kevin");
    user.setLastUpdatedDate(new Date());

    session.save(user);
    session.getTransaction().commit();

    session.refresh(user);

    System.out.println(user.getAge());
  }

  private static Date getMyBirthday() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 1984);
    calendar.set(Calendar.MONTH, 6);
    calendar.set(Calendar.DATE, 19);
    return calendar.getTime();
  }

  public static void timeTest(Session session){
    session.getTransaction().begin();
    TimeTest test = new TimeTest(new Date());
    session.save(test);
    session.getTransaction().commit();
    session.refresh(test);
    System.out.println(test.toString());
  }

  public static void bankTest(Session session) {
    Transaction transaction = session.beginTransaction();

    Bank bank = new Bank();
    bank.setName("Federal Trust");
    bank.setAddressLine1("33 Wall Street");
    bank.setAddressLine2("Suite 233");
    bank.setCity("New York");
    bank.setState("NY");
    bank.setZipCode("12345");
    bank.setInternational(false);
    bank.setCreatedBy("Kevin");
    bank.setCreatedDate(new Date());
    bank.setLastUpdatedBy("Kevin");
    bank.setLastUpdatedDate(new Date());
    session.save(bank);

    transaction.commit();

  }

  public static void userAddressTest(Session session) {
    Transaction transaction = session.beginTransaction();

    User user = new User();
    Address address = new Address();
    user.setAge(22);
    user.setBirthDate(new Date());
    user.setCreatedBy("Kevin");
    user.setCreatedDate(new Date());
    user.setEmailAddress("kmb3");
    user.setFirstName("kevin");
    user.setLastName("bowersox");
    user.setLastUpdatedBy("kmb");
    user.setLastUpdatedDate(new Date());

    address.setAddressLine1("line 1");
    address.setAddressLine2("line2");
    address.setCity("Philadelphia");
    address.setState("PA");
    address.setZipCode("12345");

    user.setAddress(address);
    session.save(user);

    transaction.commit();
  }

  public static void bankContactTest(Session session) {
    Transaction transaction = session.beginTransaction();

    Bank bank = new Bank();
    bank.setName("Federal Trust");
    bank.setAddressLine1("33 Wall Street");
    bank.setAddressLine2("Suite 233");
    bank.setCity("New York");
    bank.setState("NY");
    bank.setZipCode("12345");
    bank.setInternational(false);
    bank.setCreatedBy("Kevin");
    bank.setCreatedDate(new Date());
    bank.setLastUpdatedBy("Kevin");
    bank.setLastUpdatedDate(new Date());
    bank.getContacts().add("Joe");
    bank.getContacts().add("Mary");
    session.save(bank);

    transaction.commit();
  }
}
