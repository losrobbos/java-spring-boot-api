package com.example.springbootdemo.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  CustomUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  )
    throws ServletException, IOException {
    String token = getJwtFromRequest(request);

    if (!StringUtils.hasText(token)) {
      filterChain.doFilter(request, response);
      return;
    }
    String userEmail = jwtTokenProvider.verifyToken(token);
    if (!StringUtils.hasText(userEmail)) {
      filterChain.doFilter(request, response);
      return;
    }

    // valid token => authenticate user
    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
      userDetails,
      null,
      userDetails.getAuthorities()
    );

    // store user in request
    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    // tell spring app context that user is authenticated 
    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    return token;
  }
}
