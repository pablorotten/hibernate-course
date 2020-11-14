package com.infiniteskills.data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.Session;

import com.infiniteskills.data.entities.Account;
import com.infiniteskills.data.entities.Address;
import com.infiniteskills.data.entities.Bank;
import com.infiniteskills.data.entities.Credential;
import com.infiniteskills.data.entities.Transaction;
import com.infiniteskills.data.entities.User;

public class ApplicationHibernateApi {

  public static void main(String[] args) {
    savingEntities();
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
