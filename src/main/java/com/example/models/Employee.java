package com.example.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
  @Id
  private long id;
  private String firstName;
  private String lastName;
  private long yearlyIncome;

  public Employee() {
    
  }
  
  public Employee(long id, String firstName, String lastName, long yearlyIncome) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.yearlyIncome = yearlyIncome;
  }
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
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
  public long getYearlyIncome() {
    return yearlyIncome;
  }
  public void setYearlyIncome(long yearlyIncome) {
    this.yearlyIncome = yearlyIncome;
  }
}
