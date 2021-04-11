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

Safely support user input