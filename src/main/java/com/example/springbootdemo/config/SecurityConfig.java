package com.example.springbootdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springbootdemo.security.JwtAuthenticationEntryPoint;
import com.example.springbootdemo.security.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity
				.cors().and().csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/dbinfo").permitAll()
					.antMatchers("/about").permitAll()
					.antMatchers("/auth/*").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated()
				.and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

				// .cors()
				// .and()
				// .csrf()
				// 	.disable()
				// .exceptionHandling()
				// // all requests need to run though our entry point
				// .authenticationEntryPoint(jwtAuthenticationEntryPoint)
				// .and()
				// // make sure we use stateless session; session won't be used to
				// // store user's state.
				// .sessionManagement()
				// .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// .and()
				// .authorizeRequests()
				// dont authenticate this particular request
				// allow access to /auth routes unauthenticated 
				// .antMatchers("/").permitAll()
				// .antMatchers("/about").permitAll()
				// .antMatchers("/auth/**").permitAll();
				// .anyRequest()				
				// all other requests need to be authenticated
				// .authenticated();

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(
			jwtRequestFilter, 
			UsernamePasswordAuthenticationFilter.class
		);
	}
}
