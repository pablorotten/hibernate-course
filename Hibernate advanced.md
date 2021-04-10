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

### Enumerations

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

### Mapped Superclass Inheritance

We need to map the inheritance between classes in order to persist them. We just need to use the annotation ```@MappedSuperclass``` in the supperclass to tell
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

⚠ The @MappedSuperclass is only for abstract classes!!! If a class has this annotation it can't be persisted not instantiated!!!