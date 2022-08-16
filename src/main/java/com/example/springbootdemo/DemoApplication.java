package com.example.springbootdemo;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Topic;

@SpringBootApplication
@RestController
public class DemoApplication {

  @Value("${server.port}")
  String port;
  @Value("${jwt.secret}")
  String jwtSecret;

  @GetMapping("/")
  @ResponseBody
  String home() {
    return jwtSecret + " " + port;
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

  @GetMapping("/topics")
  public ArrayList<Topic> topics() {
      var topics = new ArrayList<Topic>();
      topics.add(new Topic("t1", "newwws", "News. this works!"));
      topics.add(new Topic("t2", "carsss", "Cars. this works!"));
      topics.add(new Topic("t3", "mobbbs", "Mobbs. this works!"));

      return topics;
  }
  

  @GetMapping("/topic")
  public HashMap topic() {
    HashMap<String, Object> person = new HashMap<String, Object>();

    // add elements dynamically
    person.put("name", "Lem");
    person.put("age", 46);
    person.put("gender", 'M');

    return person;
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
