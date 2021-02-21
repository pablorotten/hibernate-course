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