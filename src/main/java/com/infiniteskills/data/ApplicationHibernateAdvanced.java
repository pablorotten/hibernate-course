package com.infiniteskills.data;

import com.infiniteskills.data.entities.*;
import com.infiniteskills.data.entities.ids.CurrencyId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
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
//      compoundJoinColumnsDemo(session, tx, sessionFactory);
//      enumerationsDemo(session, tx, sessionFactory);
//      mappedSuperclassDemo(session, tx, sessionFactory);
//      tablePerClassInheritanceDemo(session, tx, sessionFactory);
      singleTableClassInheritanceDemo(session, tx, sessionFactory);

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

  public static void enumerationsDemo(Session session, org.hibernate.Transaction tx, SessionFactory sessionFactory) {
    sessionFactory = HibernateUtil.getSessionFactory();
    session = sessionFactory.openSession();
    tx = session.beginTransaction();

    Account account = createNewAccount();
    account.setAccountType(AccountType.SAVINGS);

    session.save(account);
    tx.commit();

    Account dbAccount = (Account) session.get(Account.class, account.getAccountId());
    System.out.println(dbAccount.getName());
    System.out.println(dbAccount.getAccountType());
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

  public static void mappedSuperclassDemo(Session session, org.hibernate.Transaction tx, SessionFactory sessionFactory) {
    sessionFactory = HibernateUtil.getSessionFactory();
    session = sessionFactory.openSession();
    tx = session.beginTransaction();

    Stock stock = createStock();
    session.save(stock);

    Bond bond = createBond();
    session.save(bond);

    tx.commit();
  }

  private static Bond createBond() {
    Bond bond = new Bond();
    bond.setInterestRate(new BigDecimal("123.22"));
    bond.setIssuer("JP Morgan Chase");
    bond.setMaturityDate(new Date());
    bond.setPurchaseDate(new Date());
    bond.setName("Long Term Bond Purchases");
    bond.setValue(new BigDecimal("10.22"));
    return bond;
  }

  private static Stock createStock(){
    Stock stock = new Stock();
    stock.setIssuer("Allen Edmonds");
    stock.setName("Private American Stock Purchases");
    stock.setPurchaseDate(new Date());
    stock.setQuantity(new BigDecimal("1922"));
    stock.setSharePrice(new BigDecimal("100.00"));
    return stock;
  }

  public static void tablePerClassInheritanceDemo(Session session, org.hibernate.Transaction tx, SessionFactory sessionFactory) {
    sessionFactory = HibernateUtil.getSessionFactory();
    session = sessionFactory.openSession();
    tx = session.beginTransaction();

    Portfolio portfolio= new Portfolio();
    portfolio.setName("First Investments");

    Stock stock = createStock();
    stock.setPortfolio(portfolio);

    Bond bond = createBond();
    bond.setPortfolio(portfolio);

    portfolio.getInvestements().add(stock);
    portfolio.getInvestements().add(bond);

    session.save(stock);
    session.save(bond);

    tx.commit();

    Portfolio dbPortfolio = (Portfolio) session.get(Portfolio.class, portfolio.getPortfolioId());
    session.refresh(dbPortfolio);

    for(Investment i:dbPortfolio.getInvestements()){
      System.out.println(i.getName());
    }
  }


  public static void singleTableClassInheritanceDemo(Session session, org.hibernate.Transaction tx, SessionFactory sessionFactory) {
    sessionFactory = HibernateUtil.getSessionFactory();
    session = sessionFactory.openSession();
    tx = session.beginTransaction();

    Portfolio portfolio= new Portfolio();
    portfolio.setName("First Investments");

    Stock stock = createStock();
    stock.setPortfolio(portfolio);

    Bond bond = createBond();
    bond.setPortfolio(portfolio);

    portfolio.getInvestements().add(stock);
    portfolio.getInvestements().add(bond);

    session.save(stock);
    session.save(bond);

    tx.commit();

    Portfolio dbPortfolio = (Portfolio) session.get(Portfolio.class, portfolio.getPortfolioId());
    session.refresh(dbPortfolio);

    for(Investment i:dbPortfolio.getInvestements()){
      System.out.println(i.getName());
    }
  }

}
