package com.infiniteskills.data;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.Session;

import com.infiniteskills.data.entities.Account;
import com.infiniteskills.data.entities.Bank;
import com.infiniteskills.data.entities.Transaction;

public class ApplicationHibernateApi {

  public static void main(String[] args) {
//    savingEntities();
//    retrievingEntities();
//    modifyingEntities();
//    removingEntities();
//    reattachingEntities();
    saveOrUpdateEntities();
  }

  public static void savingEntities() {
    // ---- TRANSIENT ----
    Session session = HibernateUtil.getSessionFactory().openSession();

    Account account = createNewAccount();
    Transaction trans1 = createNewBeltPurchase(account);
    Transaction trans2 = createShoePurchase(account);
    account.getTransactions().add(trans1);
    account.getTransactions().add(trans2);

    System.out.println(session.contains(account));
    System.out.println(session.contains(trans1));
    System.out.println(session.contains(trans1));

    try {
      org.hibernate.Transaction transaction = session.beginTransaction();
      // ---- PERSISTENT ----
      session.save(account);

      System.out.println(session.contains(account));
      System.out.println(session.contains(trans1));
      System.out.println(session.contains(trans1));

      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      session.close();
      HibernateUtil.getSessionFactory().close();
    }
  }

  public static void retrievingEntities() {
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
      org.hibernate.Transaction transaction = session.beginTransaction();
      // ---- PERSISTENT ----
      Bank getBank = (Bank) session.get(Bank.class, 1L); // executes the query and saves bank in persistence context
      getBank = (Bank) session.get(Bank.class, 1L); // bank is already in Persistence context cache, no need to perform a new query
      System.out.println("get Method Executed");
      System.out.println(getBank.getName());

      Bank getBankNull = (Bank) session.get(Bank.class, 123L);
//      System.out.println(getBankNull.getName()); // null pointer exception >> process finished

      System.out.println("------------");

      Bank loadBank = (Bank) session.load(Bank.class, 1L); // Hibernate returns a proxy in place of the actual entity
      System.out.println("load Method Executed");// bank is still not needed, so no query is executed
      System.out.println(loadBank.getName());// query is executed here when the bank name is needed

      Bank getBankNotFound = (Bank) session.get(Bank.class, 123L);
      System.out.println(getBankNotFound.getName()); // object not found exception >> process continues

      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      session.close();
      HibernateUtil.getSessionFactory().close();
    }
  }

  public static void modifyingEntities() {
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
      org.hibernate.Transaction transaction = session.beginTransaction();

      Bank bank = (Bank) session.get(Bank.class, 1L); // select statement

      bank.setName("New Hope Bank");
      bank.setLastUpdatedBy("Kevin Bowersox");
      bank.setLastUpdatedDate(new Date());

      transaction.commit(); // changes have been made >> update statement
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      session.close();
      HibernateUtil.getSessionFactory().close();
    }
  }

  public static void removingEntities() {
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
      org.hibernate.Transaction transaction = session.beginTransaction();

      Bank bank = (Bank) session.get(Bank.class, 11L);

      System.out.println(session.contains(bank));
      session.delete(bank); // bank is not part of the application anymore
      System.out.println("delete Method Invoked");
      System.out.println(session.contains(bank));

      transaction.commit();// delete query
    } catch (Exception e) {
      e.printStackTrace();
    } finally{
      session.close();
      HibernateUtil.getSessionFactory().close();
    }
  }

  public static void reattachingEntities() {
    try {
      Session session = HibernateUtil.getSessionFactory().openSession();
      org.hibernate.Transaction transaction = session.beginTransaction();
      Bank bank = (Bank) session.get(Bank.class, 1L);
      transaction.commit();
      session.close();

      Session session2 = HibernateUtil.getSessionFactory().openSession();
      org.hibernate.Transaction transaction2 = session2.beginTransaction();

      System.out.println(session2.contains(bank));
      session2.update(bank);
      System.out.println("Method Invoked");
      System.out.println(session2.contains(bank));

      transaction2.commit();
      session2.close();

    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      HibernateUtil.getSessionFactory().close();
    }
  }

  public static void saveOrUpdateEntities() {
    try {
      Session session = HibernateUtil.getSessionFactory().openSession();
      org.hibernate.Transaction transaction = session.beginTransaction();
      Bank detachedBank = (Bank) session.get(Bank.class, 1L);
      transaction.commit();
      session.close();

      Bank transientBank = createBank();

      Session session2 = HibernateUtil.getSessionFactory().openSession();
      org.hibernate.Transaction transaction2 = session2.beginTransaction();

      session2.saveOrUpdate(transientBank);
      session2.saveOrUpdate(detachedBank);
      detachedBank.setName("Test Bank 2");
      transaction2.commit();
      session2.close();

    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      HibernateUtil.getSessionFactory().close();
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

  private static Transaction createNewBeltPurchase(Account account) {
    Transaction beltPurchase = new Transaction();
    beltPurchase.setAccount(account);
    beltPurchase.setTitle("Dress Belt");
    beltPurchase.setAmount(new BigDecimal("50.00"));
    beltPurchase.setClosingBalance(new BigDecimal("0.00"));
    beltPurchase.setCreatedBy("Kevin Bowersox");
    beltPurchase.setCreatedDate(new Date());
    beltPurchase.setInitialBalance(new BigDecimal("0.00"));
    beltPurchase.setLastUpdatedBy("Kevin Bowersox");
    beltPurchase.setLastUpdatedDate(new Date());
    beltPurchase.setNotes("New Dress Belt");
    beltPurchase.setTransactionType("Debit");
    return beltPurchase;
  }

  private static Transaction createShoePurchase(Account account) {
    Transaction shoePurchase = new Transaction();
    shoePurchase.setAccount(account);
    shoePurchase.setTitle("Work Shoes");
    shoePurchase.setAmount(new BigDecimal("100.00"));
    shoePurchase.setClosingBalance(new BigDecimal("0.00"));
    shoePurchase.setCreatedBy("Kevin Bowersox");
    shoePurchase.setCreatedDate(new Date());
    shoePurchase.setInitialBalance(new BigDecimal("0.00"));
    shoePurchase.setLastUpdatedBy("Kevin Bowersox");
    shoePurchase.setLastUpdatedDate(new Date());
    shoePurchase.setNotes("Nice Pair of Shoes");
    shoePurchase.setTransactionType("Debit");
    return shoePurchase;
  }

  private static Account createNewAccount() {
    Account account = new Account();
    account.setCloseDate(new Date());
    account.setOpenDate(new Date());
    account.setCreatedBy("Kevin Bowersox");
    account.setInitialBalance(new BigDecimal("50.00"));
    account.setName("Savings Account");
    account.setCurrentBalance(new BigDecimal("100.00"));
    account.setLastUpdatedBy("Kevin Bowersox");
    account.setLastUpdatedDate(new Date());
    account.setCreatedDate(new Date());
    return account;
  }

}
