package com.wilsonpedro.parking.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wilsonpedro.parking.dtos.RegistroDTO;
import com.wilsonpedro.parking.dtos.records.AuthenticationDTO;
import com.wilsonpedro.parking.enums.UserRole;
import com.wilsonpedro.parking.infra.security.TokenService;
import com.wilsonpedro.parking.models.User;
import com.wilsonpedro.parking.repositories.UserRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationControllerTest {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenService tokenService;
	
	@Test
	@Order(1)
	void mustRegisterTheUserSuccessfully() {
		userRepository.deleteAll();
		
		RegistroDTO registroDTO = new RegistroDTO("wilson", "12345", UserRole.ADMIN);
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(registroDTO.getPassword());
		
		assertNotNull(encryptedPassword);
		assertNotEquals(encryptedPassword, registroDTO.getPassword());
		
		User user = new User(registroDTO.getLogin(), encryptedPassword, registroDTO.getRole());
		
		assertEquals(0, userRepository.count());
		
		userRepository.save(user);
		
		assertEquals(1, userRepository.count());
		assertEquals(UserRole.ADMIN, user.getRole());
	}

	@Test
	@Order(2)
	void mustRealizeLoginSuccessfully() {
		AuthenticationDTO dto = new AuthenticationDTO("wilson", "12345");
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		assertNotNull(token);
	}
}
