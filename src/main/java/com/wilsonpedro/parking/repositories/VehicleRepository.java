package com.wilsonpedro.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilsonpedro.parking.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

	boolean existsByPlate(String plate);
}
