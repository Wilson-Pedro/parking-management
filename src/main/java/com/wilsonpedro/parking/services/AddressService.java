package com.wilsonpedro.parking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.repositories.AddressRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	public Address save(Address address) {
		return addressRepository.save(address);
	}

	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	public Address findById(Long id) {
		return addressRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
	}

	public Address update(Address address, Long id) {
		return addressRepository.findById(id)
				.map(addressUpdated -> {
					addressUpdated.setId(id);
					addressUpdated.setCep(address.getCep());
					addressUpdated.setStreet(address.getStreet());
					addressUpdated.setNeighborhood(address.getNeighborhood());
					addressUpdated.setCity(address.getCity());
					return addressRepository.save(addressUpdated);
				}).orElseThrow(() -> new EntityNotFoundException());
	}

	public void delete(Long id) {
		addressRepository.delete(addressRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException()));
	}
}
