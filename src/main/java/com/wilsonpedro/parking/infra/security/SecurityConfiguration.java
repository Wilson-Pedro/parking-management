package com.wilsonpedro.parking.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(new AntPathRequestMatcher("/auth/login", HttpMethod.POST.toString())).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/auth/register", HttpMethod.POST.toString())).permitAll()
						//COMPANIES
						.requestMatchers(new AntPathRequestMatcher("/companies/", HttpMethod.POST.toString())).hasRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/companies", HttpMethod.GET.toString())).hasRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/companies/{id}", HttpMethod.GET.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/companies/{id}", HttpMethod.PUT.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/companies/{id}", HttpMethod.DELETE.toString())).hasRole("ADMIN")
						//ADDRESES
						.requestMatchers(new AntPathRequestMatcher("/addreses/", HttpMethod.POST.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/addreses", HttpMethod.GET.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/addreses/{id}", HttpMethod.GET.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/addreses/{id}", HttpMethod.PUT.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/addreses/{id}", HttpMethod.DELETE.toString())).hasRole("ADMIN")
						//VEHICLES
						.requestMatchers(new AntPathRequestMatcher("/vehicles/", HttpMethod.POST.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/vehicles", HttpMethod.GET.toString())).hasAnyRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/vehicles/{id}", HttpMethod.GET.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/vehicles/{id}", HttpMethod.PUT.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/vehicles/{id}/park", HttpMethod.PUT.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/vehicles/{id}/notPark", HttpMethod.PUT.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/summary", HttpMethod.GET.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/{id}/summary", HttpMethod.GET.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/vehicles/{id}", HttpMethod.DELETE.toString())).hasRole("ADMIN")
						.anyRequest().authenticated()
				)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
