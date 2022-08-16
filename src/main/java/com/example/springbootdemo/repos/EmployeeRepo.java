package com.example.springbootdemo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springbootdemo.models.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {}
