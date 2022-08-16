package com.example.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
  
}
