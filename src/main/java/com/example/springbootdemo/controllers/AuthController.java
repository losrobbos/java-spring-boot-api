package com.example.springbootdemo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootdemo.entities.User;
import com.example.springbootdemo.repositories.UserRepo;
import com.example.springbootdemo.requests.AuthRequest;
import com.example.springbootdemo.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  
    // AUTH routes
  @PostMapping("/signup")
  @ResponseBody
  public ResponseEntity<User> signup(@RequestBody AuthRequest request) {
    Optional<User> userFound = userRepo.findByEmail(request.getEmail());

    // user already exists in DB? => reject!
    if(userFound.isPresent()) {
      return ResponseEntity.badRequest().build();
    }

    // hash password for new user and save in DB
    String hashedPw = passwordEncoder.encode( request.getPassword() );
    User userNew = new User();
    userNew.setEmail(request.getEmail());
    userNew.setPassword(hashedPw);
    
    User userCreated = userRepo.save(userNew);
    return ResponseEntity.ok(userCreated);
  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<String> login(@RequestBody AuthRequest request) {

    // authenticate user (including password hash comparison built in!)
    Authentication auth = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    // generate token from authenticated user
    String token = jwtTokenProvider.generateToken(auth);

    return ResponseEntity.ok(token);
  }



}
