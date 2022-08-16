package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
 
@SpringBootApplication
public class DBDemo implements CommandLineRunner {
 
    @Autowired
    private JdbcTemplate jdbcTemplate;
     
    // public static void main(String[] args) {
    //     SpringApplication.run(DBDemo.class, args);
    // }
 
    @Override
    public void run(String... args) throws Exception {
        System.out.println("DB Insert CLI runs...");
    //   String sql = "INSERT INTO animals (name) VALUES ('coco')";
      // String sql = "SELECT * FROM animals";
        // String sql = "INSERT INTO students (name, email) VALUES ("
        //         + "'Nam Ha Minh', 'nam@codejava.net')";

        // String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";
        // return jdbcTemplate.queryForObject(sql);


        // int rows = jdbcTemplate.query(sql, null);
        // int rows = jdbcTemplate.update(sql);
        // if (rows > 0) {
        //     System.out.println("A new row has been inserted.");
        // }
    }
 
}