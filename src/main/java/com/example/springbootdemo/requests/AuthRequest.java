package com.example.springbootdemo.requests;

import lombok.Data;

@Data
public class AuthRequest {
  String email;
  String password;
}
