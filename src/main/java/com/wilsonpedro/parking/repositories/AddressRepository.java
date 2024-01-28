package com.wilsonpedro.parking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilsonpedro.parking.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	boolean existsByCep(String cep);
	
	Optional<Address> findByCep(String cep);
}
