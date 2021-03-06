# Hibernate Advanced

## Advanced Mappings And Configuration

### Compound Primary Keys: @IdClass

Usually, there's a column for Ids. But sometimes there are Natural keys. That is using data columns as primary key.
For example **Currency** uses the columns **Name** and **Country_Name** as primary keys.

So we need to create a new Entity Id Class for mapping this compund primary key and ask the Entity to use that class using the **@IdClass** annotation

```java
// The entity
@Entity
@IdClass(CurrencyId.class)
public class Currency {

  @Id
  @Column(name="NAME")
  private String name;

  @Id
  @Column(name="COUNTRY_NAME")
  private String countryName;

  ...
}

// The Entity Id class. Needs constructor
public class CurrencyId implements Serializable{

  private String name;
  private String countryName;

  public CurrencyId(String name, String countryName) {
    this.name = name;
    this.countryName = countryName;
}
```

### Compound Join Columns

How can we model a relationship between a normal Entity (surrogated PK using id column) and an Entity with a compund PK?
```
MARKET has as FK the PK of CURRENCY which is a composition of currency_name + country_name

+----------------+       +----------------+
|      MARKET    |       |    CURRENCY    |
| * id           |       | * currency_name|
| currency_name  +>------| * country_name |
| country_name   |       +----------------+
+----------------+
```

Just create a ManyToOne relationship in Market specifying the column names:

```java
@Entity
@Table(name = "MARKET")
public class Market {

  @ManyToOne(cascade=CascadeType.ALL)
  @JoinColumns({
          @JoinColumn(name="CURRENCY_NAME", referencedColumnName="NAME"),
          @JoinColumn(name="COUNTRY_NAME", referencedColumnName="COUNTRY_NAME")
  })
  private Currency currency;
  ...
}
```

### Enumerations @Enumerated

We can use enumerations in order to have fixed values for a column. Just create an enumeration and use it as the type of the field

```java
public enum AccountType {
	LOAN,
	SAVINGS,
	CHECKING
}

@Enumerated(EnumType.STRING) // Instead of using the ordinal, use the string value of the enumeration
@Column(name="ACCOUNT_TYPE")
private AccountType accountType;
```

Using ```@Enumerated(EnumType.STRING)``` stores the enumeration value in database. By default it uses the ordinary of the assigned value.
This is very dangerous because if the order of the enumeration change, the values in database will be wrong.

### Mapped Superclass Inheritance @MappedSuperclass

When we are using superclasses, we need to map the inheritance between classes in order to persist them. We just need to use the annotation ```@MappedSuperclass``` in the supperclass to tell
hibernate to use the superclass fields to persist the subclass.

```java
@MappedSuperclass
public abstract class Investment {

@Entity
@Table(name = "BOND")
public class Bond extends Investment{

@Entity
@Table(name = "STOCK")
public class Stock extends Investment {
```

⚠ The @MappedSuperclass annotation has 2 limitations:
* @MappedSuperclass class can't be persisted nor instantiated!!! So it must behave as an abstract class (not mandatory but highly recomended)
* You can't use the @MappedSuperclass class as a generic type. For example, this is forbidden: ```List<Superclass> list```

### Table Per Class @Inheritance

To have something more flexible than @MappedSuperclass, we can use the **@Inheritance** annotation using the strategy **InheritanceType.TABLE_PER_CLASS**

This strategy is very similar to @MappedSuperclass. Will create a table for each subclass, but it will allow us to use the superclass as well.

* The superclass must have an @Id that will be used by the subclasses, which don't have id.
* We can't use the standard Identity generator, we need to define a custom one with @GeneratedValue and @TableGenerator ❓ why???
* Hibernate has to perform an Union loosing performance

### Single Table @Inheritance

We just use a table for the superclass where all the subclasses will be persisted. Depending on what are we persisting,
some of the columns might not be used. Most common and best performance. Use the strategy **InheritanceType.SINGLE_TABLE**

* The superclass must have a @Table to persist all the subclasses. Subclasses don't have @Table annotation
* The superclass must have an @Id that will be used by the subclasses, which don't have id.
* We can't use the standard Identity generator
* ```@DiscriminatorColumn``` Column used to identify each entry which the subclass. By default is the class name, can be customized using ```@DiscriminatorValue```

Superclass:
```java
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="INVESTMENT_TYPE")
@Table(name="INVESTMENT")
public abstract class Investment {

	@Id
	@GeneratedValue
	@Column(name="INVESTMENT_ID")
	private Long investmentId;
```

Subclass (no id):
```java
@Entity
@DiscriminatorValue("BND")
public class Bond extends Investment{

	@Column(name = "VALUE")
	private BigDecimal value;
```

Everything will be persistend into **Investment** table.

### Building A Persistence Layer

Using the **Dao pattern** the recommended project structure is

* main.java.com.customName
  * data
    * dao
      * interfaces
        * **Dao.java**
        * **ConcreteClassDao.java**
      * **AbstractDao.java**: implements ```Dao.java```
      * **ConcreteClassImpDao.java**: implements ```ConcreteClassDao.java```
    * entities: all the Entity classes

The Dao pattern consists in:
* **Dao.java**: Interface that defines all generic operations such as findById, save, delete, etc.
* **AbstractDao.java**: Implements ```Dao.java``` generic operations that can be used for any entity.
* **ConcreteClassDao.java**: Interface that defines concrete operations for the **ConcreteClass**
* **ConcreteClassImpDao.java**: Implements ```ConcreteClassDao.java```

### Views For Complex Queries

How to create custom sql queries? Create a view in database and map it into an entity.

In @table set the name of the view and in @Id don't use @GeneratedValue
```java
@Entity
@Table(name = "V_USER_CREDENTIAL")
public class UserCredentialView {

	@Id
	@Column(name="USER_ID")
	private Long userId;
```

### Schema Generation

Hibernate can auto generate our tables. Very useful for developing and POF, but not recommended for production at all.

For that we need edit our ```hibernate.cfg.xml``` and use the property ```hbm2ddl.auto```

```xml
<hibernate-configuration>
  <session-factory>
		<property name="hbm2ddl.auto">create</property>
```
Some possible values for ```hbm2ddl.auto``` are:
* create: creates all the tables
* validate: checks if the tables and the objects match
* update: if something is missing, adds it
* create-drop: drops everything, creates the schema and finally drops everything again
