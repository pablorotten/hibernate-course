package com.infiniteskills.data.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CREDENTIAL")
public class Credential {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="CREDENTIAL_ID")
  public Long credentialId;

  // One to one association. This is the source Entity because in the table, it holds the foreign key of User, the target Entity
  @OneToOne(cascade=CascadeType.ALL)// Allows to persist an User from this Credential
  @JoinColumn(name="USER_ID")// The foreign key column. The one that should be used to join the tables
  public User user;

  @Column(name="USERNAME")
  private String username;

  @Column(name="PASSWORD")
  private String password;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Long getCredentialId() {
    return credentialId;
  }

  public void setCredentialId(Long credentialId) {
    this.credentialId = credentialId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
