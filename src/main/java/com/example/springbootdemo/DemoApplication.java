package com.example.springbootdemo;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Employee;
import org.springframework.web.bind.annotation.RequestBody;


@SpringBootApplication
@RestController
public class DemoApplication {

  @Autowired
  private JdbcTemplate jdbcTemplate;
     
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
    String sql = "SELECT * FROM employees";
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Employee.class));
  }

  @GetMapping("/employee/{id}")
  public Employee getEmployeeById(@PathVariable("id") long id) {
    try {
      Employee employee = jdbcTemplate.queryForObject("SELECT * FROM employees WHERE id = ?",
          BeanPropertyRowMapper.newInstance(Employee.class), id);
      return employee;
    } catch (IncorrectResultSizeDataAccessException e) {
      return null;
    }
  }
  

  @PostMapping("/employee")
  public Employee createEmployee(@RequestBody Employee employee) {
      
    String sqlQuery = "insert into employees(first_name, last_name, yearly_income) " +
                    "values (?, ?, ?)";

    // return ID value 
    KeyHolder keyHolder = new GeneratedKeyHolder();
  
    // execute prepared statement and return ID afterwards
    jdbcTemplate.update(connection -> {
      PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
      stmt.setString(1, employee.getFirstName());
      stmt.setString(2, employee.getLastName());
      stmt.setLong(3, employee.getYearlyIncome());
      return stmt;
    }, keyHolder);
  
    Long idNew = keyHolder.getKey().longValue();
    employee.setId(idNew);
    return employee;
  }

  @PatchMapping("/employee/{id}")
  public int updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employeeUpdate) {
    return jdbcTemplate.update("UPDATE employees SET yearly_income = ? WHERE id = ?",
        new Object[] { employeeUpdate.getYearlyIncome(), id });
  }

  @DeleteMapping("/employee/{id}")
  public int deleteEmployee(@PathVariable("id") Long id) {
    return jdbcTemplate.update("DELETE FROM employees WHERE id = ?", id);
  }

  @GetMapping("/migrate")
  public int migrate() {
    
    // CREATE topics table
    jdbcTemplate.execute("""
      CREATE TABLE IF NOT EXISTS topics (
      id SERIAL PRIMARY KEY,
      title VARCHAR(50) NOT NULL,
      description TEXT NULL
    );""");

    // CREATE employee tables
    jdbcTemplate.execute("""
    CREATE TABLE IF NOT EXISTS employees (
      id SERIAL PRIMARY KEY,
      first_name varchar(100) NOT NULL,
      last_name varchar(100) NOT NULL,
      yearly_income integer NOT NULL
    );""");


    int result = jdbcTemplate.queryForObject(
    "SELECT COUNT(*) FROM employees", Integer.class);

    return result;
  }


  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
