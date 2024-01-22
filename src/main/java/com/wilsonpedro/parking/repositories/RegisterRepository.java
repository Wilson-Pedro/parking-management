package com.wilsonpedro.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilsonpedro.parking.models.Register;

public interface RegisterRepository extends JpaRepository<Register, Long>{

}
