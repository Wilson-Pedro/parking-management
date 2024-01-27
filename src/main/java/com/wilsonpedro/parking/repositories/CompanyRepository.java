package com.wilsonpedro.parking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilsonpedro.parking.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	boolean existsByCnpj(String cnpj);
	
	boolean existsByPhone(String phone);
	
	boolean existsByName(String name);
	
	Optional<Company> findByName(String name);
	
	Optional<Company> findByCnpj(String cnpj);
	
	Optional<Company> findByPhone(String phone);
}
