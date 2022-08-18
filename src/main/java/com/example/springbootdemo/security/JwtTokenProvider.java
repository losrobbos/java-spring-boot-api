package com.example.springbootdemo.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

// import com.example.springbootdemo.entities.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
  
  @Value("jwt.secret")
  private String jwtSecret;
  
  private Key jwtSecretKey;

  public JwtTokenProvider() {
    jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  public String generateToken(Authentication authentication) {
    System.out.println("Principal: " + authentication.getPrincipal());
    User user = (User)authentication.getPrincipal();
    return generateToken(user.getUsername());
  }

  public String generateToken(String userEmail) {
    Instant instant = Instant.now();
    Instant expiryTime = instant.plus(1, ChronoUnit.HOURS);

    // configure token
    return Jwts.builder()
      .setSubject(userEmail)
      // .setPayload(userEmail)
      .setExpiration(Date.from(expiryTime))
      .signWith(jwtSecretKey)
      .compact();
  }

  public String verifyToken(String token) {
    try {
      String email = Jwts.parserBuilder().setSigningKey((jwtSecretKey)).build().parseClaimsJws(token).getBody().getSubject();
      return email;
    }
    catch(JwtException ex) {
      return "";
    }
  }

}
