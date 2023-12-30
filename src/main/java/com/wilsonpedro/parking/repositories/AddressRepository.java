package com.wilsonpedro.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wilsonpedro.parking.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
