# Hibernate and JPA API

## Hibernate API

### Persistence Lifecycle

* Transient: When you instantiate an *Object* using the constructor (new). It still has no association with any database row
* Persistent: An Entity instance associated with a database row and contained within a **persistence context**
* Removed: An object in persistent state scheduled for deletion
* Detached: An Entity that was in Persistent state but now is not anymore. The persistent context is closed.

### Persistence context

* Provides a cache of all persistent entity instances
* Corresponds with a session:
  ```java
  SessionFactory factory = HibernateUtil.getSessionFactory();
  Session session = factory.openSession();
    // perform operations on entities
  session.close();
  ```
  
### Hibernate API Actions

| Initial State | Action                       | End State  |
|---------------|------------------------------|------------|
| None          | get() <br> load()            | Persistent | 
| Transient     | save() <br> saveOrUpdate()   | Persistent | 
| Persistent    | delete()                     | Removed    | 
| Detached      | update() <br> saveOrUpdate() | Persistent | 
| Persistent    | evict()                      | Detached   |


### Saving entities

```java
// ---- TRANSIENT ----
Account account = createNewAccount();
Transaction trans1 = createNewBeltPurchase(account);
account.getTransactions().add(trans1);

try {
  org.hibernate.Transaction transaction = session.beginTransaction();
  // ---- PERSISTENT ----
  session.save(account);
  transaction.commit();
``` 

### Retrieving entities

**get()**: executes the query **immediately** and returns the entity. If not found, returns null pointer on accessing.
```java
Bank getBank = (Bank) session.get(Bank.class, 1L); // executes the query and saves bank in persistence context
getBank = (Bank) session.get(Bank.class, 1L); // bank is already in Persistence context cache, no need to perform a new query
getBank.getName();

Bank getBankNull = (Bank) session.get(Bank.class, 123L);
getBankNull.getName(); // null pointer exception
```
**load()**: Returns a proxy instead of the entity and only executes the query when is really needed (lazy). If not found, returns an _Object not found_ exception

```java
Bank loadBank = (Bank) session.load(Bank.class, 1L); // Hibernate returns a proxy in place of the actual entity
// bank is still not needed, so no query is executed
loadBank.getName();// query is executed here when the bank name is needed

Bank getBankNotFound = (Bank) session.get(Bank.class, 123L);
getBankNotFound.getName(); // object not found exception
```

### Modifying Entities

When changes are made against a retrieved entity, the entity is not in sync with the database anymore. Hibernate is aware of
that and when the transaction is commited, generates one single update query with all the changes made to the entity.

```java
Bank bank = (Bank) session.get(Bank.class, 1L); // select statement

bank.setName("New Hope Bank");
bank.setLastUpdatedBy("Kevin Bowersox");
bank.setLastUpdatedDate(new Date());

transaction.commit(); // changes have been made >> sinlge update statement
```

### Removing entities

**delete()**: When an entity is removed, is kickout of the Persistent state. We can still use but whe shouldn't, the garbage
collector will remove it finally. The Delete query will be preformed on the transaction commit
```java
org.hibernate.Transaction transaction = session.beginTransaction();

Bank bank = (Bank) session.get(Bank.class, 11L);
session.delete(bank); // bank is not part of the application anymore
transaction.commit();// delete query
```

### Reattaching Detached Entities

**update()**: use for reattaching entities from other session to the current one. Hibernate will perform an Update query
to make sure the entity is synchronized with the database

```java
Session session = HibernateUtil.getSessionFactory().openSession();
org.hibernate.Transaction transaction = session.beginTransaction();
Bank bank = (Bank) session.get(Bank.class, 1L); // bank from session 1
transaction.commit();
session.close();

// session is closed and bank is now detached

Session session2 = HibernateUtil.getSessionFactory().openSession();
org.hibernate.Transaction transaction2 = session2.beginTransaction();
session2.update(bank); // attaches bank to session2. Performs a update query
transaction2.commit();
session2.close();
```

### Save or update

Either persists or reattaches an entitiy.

```java
Session session = HibernateUtil.getSessionFactory().openSession();
org.hibernate.Transaction transaction = session.beginTransaction();
Bank detachedBank = (Bank) session.get(Bank.class, 1L);
transaction.commit();
session.close();

Bank transientBank = createBank();

Session session2 = HibernateUtil.getSessionFactory().openSession();
org.hibernate.Transaction transaction2 = session2.beginTransaction();

session2.saveOrUpdate(transientBank); // saves entity >> insert query
session2.saveOrUpdate(detachedBank); // reattaches entity >> update query
detachedBank.setName("Test Bank 2");
transaction2.commit();
session2.close();
```

### Flushing The Persistence Context

**Transaction**: Houses the connection between the entities and the database. Persists all the changes of th entities as an
atomic unit, if something fails rollbacks everything. Performs a flush behind the courtains.

We can perform a flush directly ```session.flush()``` or indirectly ```transaction.commit()```

```java
Session session = HibernateUtil.getSessionFactory().openSession();
org.hibernate.Transaction transaction = session.beginTransaction();
try {
  Bank bank = (Bank) session.get(Bank.class, 1L);
  bank.setName("Something Different");
  System.out.println("Calling Flush");
  session.flush(); // Update query 1

  bank.setAddressLine1("Another Address Line");
  System.out.println("Calling commit");
  transaction.commit(); // Update query 2
} catch (Exception e) {
  transaction.rollback(); // rollbacks if something fails
  e.printStackTrace();
} finally{
  session.close();
  HibernateUtil.getSessionFactory().close();
}
```

We can remove the ```session.flush()``` and leave the ```transaction.commit()```, then all the update queries will be performed there.

## JPA API

JPA is a standard that the Java community have developed. Hibernate is an implementation of JPA, that's what we are going to use.

### Configuration

Add hibernate entitiy manager in the pom.xml:
```xml
<dependency>
  <groupId>org.hibernate</groupId>
  <artifactId>hibernate-entitymanager</artifactId>
  <version>4.3.7.Final</version>
</dependency>
```

Add a configuration similar than the one we did for Hibernate in ```hibernate.cfg.xml```. Create the file persistence.xml
in ```resources/META-INF``` and write the configuration there.

### Saving entities

With EntitiyManager ```persist(entity)```

```java
EntityManagerFactory factory = null; // similar to the SessionFactory in Hibernate, creates entityManagers instead of sessions
EntityManager em = null; // like the session in Hibernate. Used to perform operations on the entities
EntityTransaction tx = null; // similar to the transaction interface in Hibernate

try {
  factory = Persistence.createEntityManagerFactory("infinite-finances");
  em = factory.createEntityManager();
  tx = em.getTransaction();
  tx.begin();

  Bank bank = createBank();

  em.persist(bank);
  tx.commit();
} catch (Exception e) {
  tx.rollback();
} finally {
  em.close();
}
```
### Retrieving entities

**find()**: executes the query **immediately** and returns the entity. If not found, returns null pointer on accessing.
**getReference()**: Returns a proxy instead of the entity and only executes the query when is really needed (lazy). If not found, returns an _Object not found_ exception

```java
    Bank findBank = em.find(Bank.class, 1L);
    Bank getRefBank = em.getReference(Bank.class, 1L);
```

### Modifying entities

```find()``` or ```getReference()```, then change the entitiy and commit

```java
Bank bank = em.find(Bank.class, 1L);
bank.setName("Another Demonstration");
tx.commit();
```


## Hibernate and JPA comparison

### Setup

The stuff needed to work with the entities

| Hibernate      | JPA                  |
|----------------|----------------------|
| SessionFactory | EntityManagerFactory |
| Session        | EntityManager        |
| Transaction    | EntityTransaction    |

Hibernate:
```java
Session session = HibernateUtil.getSessionFactory().openSession();
org.hibernate.Transaction transaction = session.beginTransaction();

Bank bank = new Bank();

session.save(bank);
transaction.commit();
session.close();
HibernateUtil.getSessionFactory().close();
```

JPA:
```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("infinite-finances");
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
tx.begin();

Bank bank = createBank();

em.persist(bank);
tx.commit();
em.close();
```

### Functions

| Hibernate         | JPA                        |
|-------------------|----------------------------|
| get() <br> load() | find() <br> getReference() |