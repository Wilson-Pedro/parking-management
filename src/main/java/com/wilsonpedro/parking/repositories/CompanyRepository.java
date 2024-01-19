package com.wilsonpedro.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilsonpedro.parking.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	boolean existsByCnpj(String cnpj);
}
