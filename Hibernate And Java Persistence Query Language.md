# Hibernate (HQL) And Java Persistence (JPQL) Query Language

Regualar sql queries are performed agains **data model** (tables and columns).
This is similar but those are performed agains the **entitiy model** (classes and objets).

**JQPL** is the standard, **HQL** covers all **JQPL** and add more functionalities.

## Writing Queries

Pretty similar for both JQPL and HQL

```java
// HQL
Query query = session.createQuery("select t from Transaction t");

// JQPL
Query query = em.createQuery("from Transaction t order by t.title");
```

Using **JQPL** is highly recommended to use ```TypedQuery``` for using the entitiy type in the query.
```java
TypedQuery<Transaction> query = em.createQuery("from Transaction t order by t.title", Transaction.class);
```

## Expressions And Operators

Some examples:

HQL using single quotes for string literals
```java
Query query = session.createQuery("SELECT t FROM Transaction t "
  + "WHERE t.amount > 75 AND t.transactionType = 'Withdrawl'");
```

JPQL suing a wildcard for Like
```java
TypedQuery<Transaction> query = em.createQuery(
  "FROM Transaction t"
    + " WHERE (t.amount between 75 and 100) AND t.title LIKE '%s'"
    + " ORDER BY t.title", Transaction.class);
```

## Parameters
Safely use user input

### HQL parameters
We can use positional and name parameter. Positional start counts with 0 and replace all the ```?``` widlcards.
Using name parametr is safer:
```java
// positional
Query query = session.createQuery("select t from Transaction t WHERE t.amount > ?");
query.setParameter(0, new BigDecimal(scanner.next()));

// name
Query query = session.createQuery("select t from Transaction t WHERE t.amount > :amount");
query.setParameter("amount", new BigDecimal(userAmount));
```

### JPQL parameters
Very similar to HQL, but positional start counting from 1 instead of 0
```java
TypedQuery<Transaction> query = em.createQuery("FROM Transaction t WHERE (t.amount between ?1 and ?2)", Transaction.class);

query.setParameter(1, number1);
query.setParameter(2, number2);
```

## Joins
In HQL we don't need to specify the join. In JPQL we have to explicitly do the Join.

```java
//HQL
Query query = session.createQuery("select distinct t.account from Transaction t where t.amount > 500 and t.transactionType = 'Deposit'");

//JPQL
TypedQuery<Account> query = em.createQuery("select distinct a from Transaction t join t.account a where t.amount > 500 and t.transactionType = 'Deposit'",Account.class);
```

## Functions
We can also use **SQL** functions. Also we can use projections.

```java
//HQL
Query query = session.createQuery("select distinct t.account from Transaction t"
            + " where t.amount > 500 and lower(t.transactionType) = 'deposit'");
//JPQL
Query query = em.createQuery("select distinct t.account.name, "
        + "concat(concat(t.account.bank.name, ' '),t.account.bank.address.state)"
        + " from Transaction t"
        + " where t.amount > 500 and t.transactionType = 'Deposit'");```