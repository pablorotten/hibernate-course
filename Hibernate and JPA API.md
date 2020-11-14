# Hibernate and JPA API

## Hibernate API

### Persistence Lifecycle

* Transient: When you instantiate an *Object* using the constructor (new). It still has no association with any database row
* Persistent: An Entity instance associated with a database row and contained within a **persistence context**
* Removed: An object in persistent state scheduled for deletion
* Detached: An Entity that was in Persistent state but now is not anymore. The persistent context is closed.

### Persistence context

* Provides a cache of all persistent entity instances
* Correspondes with a session:
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