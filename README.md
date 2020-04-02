# Hibernate and Java Persistence API (JPA)

## Initial setup

#### POM

The libraries we need are:
* Hibernate: Version 4
* log4j: configure also in log4j.properties file
* mysql-connector: Make sure that is the same version of your MySQL installation

#### User Entitiy
* Create a POJO with annotations for Mapping the USER table with the User class
* Use @Table annotation for the matching the corresponding Table of the Class
* Use @Column even if is not needed and @Id for the primary key
```java
@Entity
@Table(name="FINANCES_USER")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long userId;
```

#### Session Factory
The purpose of that is to handle the session, that is the interface between our application and Hibernate.
Create the session factory in a Util class using the Factory pattern.
In this class, we have to say to Hibernate where are the **annotated classes**
```java
Configuration configuration = new Configuration();
configuration.addAnnotatedClass(User.class);
```

#### Hibernate properties
Setup the properties to allow Hibernate to connect with the database

#### Application
With the configuration above, we can make a test and see if everything is working fine
```java
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();
session.close();
```

### Hibernate XML configuration
Instead of using the properties file, we can setup the hibernate configuration in a XML file. This is the most common approach.
You can have a look to all the properties here: https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch03.html#configuration-programmatic

Also, here **we can map the classes instead of doing it in the code**

```xml
<mapping class="com.infiniteskills.data.entities.User"/>
```

Don't forget to update your SessionFactory to make sure that is using the xml configuration:
```java
configuration.configure(); // Very important
return configuration
        .buildSessionFactory(new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()) // use the xml configuration
                .build());
```
> ❓ Doesn't explain anything about why this code works with the XML file

## Basic Mapping Annotations

#### @Access

Defines the access strategy of Hibernate to the entity attributes

```java
@Access(AccessType.FIELD)
```
Access to the attribute directly with reflection

```java
@Access(AccessType.PROPERTY)
```
Access to the attribute with the getter/setter methods. So you can add some logic there


#### @Column

```java
@Column(name = "...", updatable = false)
```
Don't include these elements on update actions

```java
@Column(name = "...", nullable = false)
```
This attribute/field can't be null

```java
@Basic
```
Same as "nullable = false" but doesnt't give information to Hibernate on creating the DB Schema

#### Identifiers

```java
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
```
Fast but not avaliable for MySQL. Oracle supports

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```
Auto-incremented

```java
@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_table_generator")
@TableGenerator(name = "user_table_generator", table = "IFINANCES_KEYS", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE")
```
Uses another table, IFINANCES_KEYS in this case, to increment the primary key
> ❓ Don't understand

```java
@GeneratedValue(strategy = GenerationType.AUTO)
```

#### @Transient
Says to hibernate to **not** map an attribute of an Entity

```java
@Transient
private boolean valid;
```
valid is an attribute of the Entitiy but is not a Column of the DB Table

#### @Temporal
To deal with Date/Time types

#### @Formula
Used for calculated attributes on runtime

### Hibernate Types

* Entitiy Types: have a database identity
* Vaue Types: no database identitiy
  * Basic
  * Composite Types
  * Collection

```java
@Entity
public class User {
  
  @Id
  private Long userId; //Basic Value Type
  private String firstName; //Basic Value Type
  private User referredBy; //Entity Type
  private List<String> aliases; //Collection Value Type
  private Address address; //Composite Value Type
}

public class Address { // Auxiliary class. Is not an entity, the values are stored in the User table
  
  private String addressLine1;
  private String addressLine2;
  private String city;
  // ...
}
```  