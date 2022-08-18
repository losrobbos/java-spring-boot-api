package com.example.springbootdemo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column
  private String firstName;
  @Column
  private String lastName;
  @Column
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

  @Override
  public String toString() {
    return "Employee [firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + ", yearlyIncome="
        + yearlyIncome + "]";
  }
  
}
