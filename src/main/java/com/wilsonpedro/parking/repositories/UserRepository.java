package com.wilsonpedro.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.wilsonpedro.parking.models.User;

public interface UserRepository extends JpaRepository<User, String>{

	UserDetails findByLogin(String login);
	
}
