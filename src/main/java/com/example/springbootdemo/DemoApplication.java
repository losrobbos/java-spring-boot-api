package com.example.springbootdemo;

import com.example.springbootdemo.models.Employee;
import com.example.springbootdemo.repos.EmployeeRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {

  // Autowired means: Inject an instance automatically when class instance is created
  @Autowired
  EmployeeRepo employeeRepo;

  // Read environment variables
  @Value("${server.port}")
  String port;

  @GetMapping("/")
  @ResponseBody
  String home() {
    return "App running on port: " + port;
  }

  @GetMapping("/hello")
  public String hello(
    @RequestParam(value = "name", defaultValue = "World") String name
  ) {
    return String.format("Hello %s!", name);
  }

  @GetMapping("/about")
  public String about() {
    return "<h3>It's a me. Luigi!</h3>";
  }

  @GetMapping("/employee")
  public List<Employee> getEmployeesAll() {
    return employeeRepo.findAll();
  }

  @GetMapping("/employee/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
    Optional<Employee> employee = employeeRepo.findById(id);

    if (employee.isPresent()) {
      return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/employee")
  public ResponseEntity<Employee> createEmployee(
    @RequestBody Employee employee
  ) {
    try {
      Employee employeeNew = employeeRepo.save(employee);
      return new ResponseEntity<>(employeeNew, HttpStatus.CREATED);
    } catch (Exception ex) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/employee/{id}")
  public ResponseEntity<Employee> updateEmployee(
    @PathVariable("id") Long id,
    @RequestBody Employee employeeDataUpdate
  ) {
    Optional<Employee> employee = employeeRepo.findById(id);

    // if found => update!
    if (employee.isPresent()) {
      Employee employeeToUpdate = employee.get();
      if (employeeDataUpdate.getFirstName() != null) {
        employeeToUpdate.setFirstName(employeeDataUpdate.getFirstName());
      }
      if (employeeDataUpdate.getLastName() != null) {
        employeeToUpdate.setLastName(employeeDataUpdate.getLastName());
      }
      if (employeeDataUpdate.getYearlyIncome() > 0) {
        employeeToUpdate.setYearlyIncome(employeeDataUpdate.getYearlyIncome());
      }
      Employee employeeUpdated = employeeRepo.save(employeeToUpdate);
      return new ResponseEntity<>(employeeUpdated, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/employee/{id}")
  public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id) {
    Optional<Employee> _employeeToDelete = employeeRepo.findById(id);

    if (_employeeToDelete.isPresent()) {
      Employee employeeToDelete = _employeeToDelete.get();
      employeeRepo.delete(employeeToDelete);
      return new ResponseEntity<>(employeeToDelete, HttpStatus.OK);
    }

    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
