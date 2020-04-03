package com.infiniteskills.data;

import java.util.Calendar;
import java.util.Date;

import com.infiniteskills.data.entities.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Application {

  public static void main(String[] args) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
//      timeDemo(session);
//      userDemo(session);
//      userAgeDemo(session);
//      bankDemo(session);
//      userAddressDemo(session);
//      bankContactDemo(session);
//      bankContactMapDemo(session);
//      userAddressCollectionDemo(session);
      CredentialUser(session);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }finally{
      session.close();
      HibernateUtil.getSessionFactory().close();
      System.exit(0);
    }

  }

  public static void userDemo(Session session) {
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

  public static void userAgeDemo(Session session) {
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

  public static void timeDemo(Session session){
    session.getTransaction().begin();
    TimeTest test = new TimeTest(new Date());
    session.save(test);
    session.getTransaction().commit();
    session.refresh(test);
    System.out.println(test.toString());
  }

  public static void bankDemo(Session session) {
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

  public static void userAddressDemo(Session session) {
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

//    user.setAddress(address);
//    session.save(user);

    transaction.commit();
  }

  public static void bankContactDemo(Session session) {
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
//    bank.getContacts().add("Joe");
//    bank.getContacts().add("Mary");
    session.save(bank);

    transaction.commit();
  }

  public static void bankContactMapDemo(Session session) {
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
    bank.getContacts().put("MANAGER", "Joe");
    bank.getContacts().put("TELLER", "Mary");
    session.save(bank);

    transaction.commit();
  }

  public static void userAddressCollectionDemo(Session session) {
    Transaction transaction = session.beginTransaction();

    User user = new User();

    Address address = new Address();
    Address address2 = new Address();
    setAddressFields(address);
    setAddressFields2(address2);
    user.getAddress().add(address);
    user.getAddress().add(address2);
    setUserFields(user);

    session.save(user);
    transaction.commit();
  }

  private static void setUserFields(User user) {
    user.setAge(22);
    user.setBirthDate(new Date());
    user.setCreatedBy("kmb");
    user.setCreatedDate(new Date());
    user.setEmailAddress("kmb385");
    user.setFirstName("Kevin");
    user.setLastName("bowersox");
    user.setLastUpdatedBy("kevin");
    user.setLastUpdatedDate(new Date());
  }

  private static void setAddressFields(Address address) {
    address.setAddressLine1("Line 1");
    address.setAddressLine2("Line 2");
    address.setCity("New York");
    address.setState("NY");
    address.setZipCode("12345");
  }

  private static void setAddressFields2(Address address) {
    address.setAddressLine1("Line 3");
    address.setAddressLine2("Line 4");
    address.setCity("Corning");
    address.setState("NY");
    address.setZipCode("12345");
  }

  public static void CredentialUser(Session session) {
    Transaction transaction = session.beginTransaction();

    User user = new User();
    user.setFirstName("Kevin");
    user.setLastName("Bowersox");
    user.setAge(20);
    user.setBirthDate(new Date());
    user.setCreatedBy("Kevin Bowersox");
    user.setCreatedDate(new Date());
    user.setEmailAddress("kevin.bowersox@navy.mil");
    user.setLastUpdatedDate(new Date());
    user.setLastUpdatedBy("Kevin Bowersox");

    Credential credential = new Credential();
    credential.setPassword("kevinspassword");
    credential.setUsername("kmb385");

    // Don't forget to set both sides of the bidirectional relationship!!!
    credential.setUser(user);
    user.setCredential(credential);

    session.save(credential);// Cascade will save the Credential and the User
    transaction.commit();

    User dbUser = (User) session.get(User.class, credential.getUser().getUserId());
    System.out.println(dbUser.getFirstName());
  }
}
