package com.infiniteskills.data.entities;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FINANCES_USER")
@Access(AccessType.FIELD) // Defines the access strategy to the attributes
public class User {

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremented
//  @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_table_generator") // Uses another table to increment the primary key
//  @TableGenerator(name = "user_table_generator", table = "IFINANCES_KEYS", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE")
  @GeneratedValue(strategy = GenerationType.AUTO) // Takes the db default. Dont need to specify, just write @GeneratedValue
  @Column(name = "USER_ID")
  private Long userId;

  @OneToOne(mappedBy="user")
  private Credential credential;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Basic // same as nullable but doesn't give information to Hibernate on creating the DB Schema
  @Column(name = "BIRTH_DATE", nullable = false) // cannot be null
  private Date birthDate;

  @Column(name = "EMAIL_ADDRESS")
  private String emailAddress;

  // Address as a Composite Value Type >> Stored in USER table
//  @Embedded
//  @AttributeOverrides({
//          @AttributeOverride(name="addressLine1", column=@Column(name="USER_ADDRESS_LINE_1")),
//          @AttributeOverride(name="addressLine2", column=@Column(name="USER_ADDRESS_LINE_2"))})
//  private Address address;

  // Address as a Collection of CVT >> Stored in USER_ADDRESS table
  @ElementCollection
  @CollectionTable(name = "USER_ADDRESS", joinColumns = @JoinColumn(name = "USER_ID")) // Mapping a collection
  @AttributeOverrides({ //  Mapping Composite Value Types
          @AttributeOverride(name="addressLine1", column=@Column(name="USER_ADDRESS_LINE_1")),
          @AttributeOverride(name="addressLine2", column=@Column(name="USER_ADDRESS_LINE_2"))})
  private List<Address> address = new ArrayList<Address>();

  @Column(name = "LAST_UPDATED_DATE")
  private Date lastUpdatedDate;

  @Column(name = "LAST_UPDATED_BY")
  private String lastUpdatedBy;

  @Column(name = "CREATED_DATE", updatable = false) // don't include these elements on update actions
  private Date createdDate;

  @Column(name = "CREATED_BY", updatable = false)
  private String createdBy;

  @Transient // This attribute is not going to be mapped by Hibernate. Is not a Column
  private boolean valid;

  public boolean isValid() {
    return valid;
  }

  @Formula("lower(datediff(curdate(), birth_date)/365)")
  private int age;

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthDate() { return birthDate; }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

  // Address as a Composite Value Type
//  public Address getAddress() { return address; }
//
//  public void setAddress(Address address) { this.address = address; }

  // Address as a Collection of CVT
  public List<Address> getAddress() {
    return address;
  }

  public void setAddress(List<Address> address) {
    this.address = address;
  }

  public Date getLastUpdatedDate() {
    return lastUpdatedDate;
  }

  public void setLastUpdatedDate(Date lastUpdatedDate) {
    this.lastUpdatedDate = lastUpdatedDate;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Credential getCredential() { return credential; }

  public void setCredential(Credential credential) { this.credential = credential; }
}
