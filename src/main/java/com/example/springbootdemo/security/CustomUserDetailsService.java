package com.example.springbootdemo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.springbootdemo.entities.User;
import com.example.springbootdemo.repositories.UserRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepo userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    // find user by email from database
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User does not exist, companero!"));

    // return found user wrapped with UserService
    return new org.springframework.security.core.userdetails.User(
      user.email, user.password, Collections.emptyList()
    );
  }
  
}
