package com.infiniteskills.data;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.infiniteskills.data.entities.*;
import org.hibernate.Session;

public class ApplicationHibernateBasics {

  public static void main(String[] args) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
      org.hibernate.Transaction transaction = session.beginTransaction();

//      timeDemo(session, transaction);
//      userDemo(session, transaction);
//      userAgeDemo(session, transaction);
//      bankDemo(session, transaction);
//      userAddressDemo(session, transaction);
//      bankContactDemo(session, transaction);
//      bankContactMapDemo(session, transaction);
//      userAddressCollectionDemo(session, transaction);
//      credentialUserDemo(session, transaction);
//      accountTransactionUnidirectionalDemo(session, transaction);
//      accountTransactionBidirectionalDemo(session, transaction);
//      budgetTransactionsJoinTableDemo(session, transaction);
//      unidirectionalManyToManyDemo(session, transaction);
      bidirectionalManyToManyDemo(session, transaction);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    } finally {
      session.close();
      HibernateUtil.getSessionFactory().close();
      System.exit(0);
    }

  }

  // Basic Mapping Annotations
  public static void userDemo(Session session, org.hibernate.Transaction transaction) {
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

  public static void userAgeDemo(Session session, org.hibernate.Transaction transaction) {
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

  public static void timeDemo(Session session, org.hibernate.Transaction transaction) {
    session.getTransaction().begin();
    TimeTest test = new TimeTest(new Date());
    session.save(test);
    session.getTransaction().commit();
    session.refresh(test);
    System.out.println(test.toString());
  }

  // Mapping Composite Value Types
  public static void bankDemo(Session session, org.hibernate.Transaction transaction) {
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

  public static void userAddressDemo(Session session, org.hibernate.Transaction transaction) {
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

  // Mapping Collections Of Basic Value Types: @ElementCollection (One to Many)
  public static void bankContactDemo(Session session, org.hibernate.Transaction transaction) {
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

  // Mapping Collections Of Basic Value Types: @MapKeyColumn: Mapping a Map
  public static void bankContactMapDemo(Session session, org.hibernate.Transaction transaction) {
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

  // Mapping A Collection Of Composite
  public static void userAddressCollectionDemo(Session session, org.hibernate.Transaction transaction) {
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

  // Unidirectional One To One Association: @OneToOne
  public static void credentialUserDemo(Session session, org.hibernate.Transaction transaction) {
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

  // Unidirectional One To Many Association: @OneToMany

  public static void accountTransactionUnidirectionalDemo(Session session, org.hibernate.Transaction transaction) {
    Account account = createNewAccount();
    account.getTransactions().add(createNewBeltPurchase());
    account.getTransactions().add(createShoePurchase());
    session.save(account);

    transaction.commit();
  }

  private static Transaction createNewBeltPurchase() {
    Transaction beltPurchase = new Transaction();
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

  private static Transaction createShoePurchase() {
    Transaction shoePurchase = new Transaction();
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

  // Bidirectional One To Many Association: @OneToMany

  public static void accountTransactionBidirectionalDemo(Session session, org.hibernate.Transaction transaction) {
    Account account = createNewAccount();
    account.getTransactions().add(createNewBeltPurchase(account));// add Transaction to Account and viceversa
    account.getTransactions().add(createShoePurchase(account));
    session.save(account);

    transaction.commit();

    Transaction dbTransaction = (Transaction) session.get(Transaction.class, account.getTransactions().get(0).getTransactionId());
    System.out.println(dbTransaction.getAccount().getName());
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

  // One to Many Association using Junction Table: @JoinTable

  public static void budgetTransactionsJoinTableDemo(Session session, org.hibernate.Transaction transaction) {
    Account account = createNewAccount();

    Budget budget = new Budget();
    budget.setGoalAmount(new BigDecimal("10000.00"));
    budget.setName("Emergency Fund");
    budget.setPeriod("Yearly");

    budget.getTransactions().add(createNewBeltPurchase(account));
    budget.getTransactions().add(createShoePurchase(account));

    session.save(budget);
    transaction.commit();
  }

  // Unidirectional Many to Many association: @ManyToMany

  public static void unidirectionalManyToManyDemo(Session session, org.hibernate.Transaction transaction) {
    Account account = createNewAccount();
    Account account2 = createNewAccount();
    User user = createUser();
    User user2 = createUser();

    account.getUsers().add(user);
    account.getUsers().add(user2);
    account2.getUsers().add(user);
    account2.getUsers().add(user2);

    session.save(account);
    session.save(account2);

    transaction.commit();

    Account dbAccount = (Account) session.get(Account.class, account.getAccountId());
    System.out.println(dbAccount.getUsers().iterator().next().getEmailAddress());
  }

  private static User createUser() {
    User user = new User();
    user.setBirthDate(new Date());
    user.setCreatedBy("Kevin Bowersox");
    user.setCreatedDate(new Date());
    user.setEmailAddress("test@test.com");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setLastUpdatedBy("system");
    user.setLastUpdatedDate(new Date());
    return user;
  }

  // Bidirectional Many to Many association: @ManyToMany
  public static void bidirectionalManyToManyDemo(Session session, org.hibernate.Transaction transaction) {
    Account account = createNewAccount();
    Account account2 = createNewAccount();
    User user = createUser();
    User user2 = createUser();

    account.getUsers().add(user);
    account.getUsers().add(user2);
    user.getAccounts().add(account);
    user2.getAccounts().add(account);

    account2.getUsers().add(user);
    account2.getUsers().add(user2);
    user.getAccounts().add(account2);
    user2.getAccounts().add(account2);


    session.save(user);
    session.save(user2);

    transaction.commit();

    User dbUser = (User) session.get(User.class, user.getUserId());
    System.out.println(dbUser.getAccounts().iterator().next().getName());
  }
}
