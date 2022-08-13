package com.example.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class DemoApplication {

  @GetMapping("/")
  @ResponseBody
  String home() {
    return "Hello World!";
  }

  @GetMapping("/hello")
  public String hello(
    @RequestParam(value = "name", defaultValue = "World") String name
  ) {
    return String.format("Hello %s!", name);
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
