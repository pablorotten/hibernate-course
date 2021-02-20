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