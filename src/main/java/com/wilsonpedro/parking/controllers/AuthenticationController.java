package com.wilsonpedro.parking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilsonpedro.parking.dtos.RegistroDTO;
import com.wilsonpedro.parking.dtos.records.AuthenticationDTO;
import com.wilsonpedro.parking.dtos.records.LoginResponseDTO;
import com.wilsonpedro.parking.infra.security.TokenService;
import com.wilsonpedro.parking.models.User;
import com.wilsonpedro.parking.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		var login = new LoginResponseDTO(token);
		
		return ResponseEntity.ok(login);
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegistroDTO data) {
		if(this.userRepository.findByLogin(data.getLogin()) != null) {
			return ResponseEntity.badRequest().build();
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
		User newUser = new User(data.getLogin(), encryptedPassword, data.getRole());
		
		this.userRepository.save(newUser);
		
		return ResponseEntity.ok().build();
	}
}

