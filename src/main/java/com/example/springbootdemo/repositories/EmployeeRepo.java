package com.example.springbootdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootdemo.entities.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {}
