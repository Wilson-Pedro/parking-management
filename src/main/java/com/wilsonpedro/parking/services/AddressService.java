package com.wilsonpedro.parking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.repositories.AddressRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CompanyService companyService;
	
	public Address save(AddressDTO addressDto) {
		Address address = toEntity(addressDto);
		return addressRepository.save(address);
	}

	private Address toEntity(AddressDTO addressDto) {
		Company company = companyService.findById(addressDto.getCompanyId());
		Address address = new Address(addressDto);
		address.setCompany(company);
		return address;
	}

	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	public Address findById(Long id) {
		return addressRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
	}

	public Address update(AddressDTO addressDTO, Long id) {
		return addressRepository.findById(id)
				.map(addressUpdated -> {
					addressUpdated.setId(id);
					addressUpdated.setCep(addressDTO.getCep());
					addressUpdated.setStreet(addressDTO.getStreet());
					addressUpdated.setNeighborhood(addressDTO.getNeighborhood());
					addressUpdated.setCity(addressDTO.getCity());
					return addressRepository.save(addressUpdated);
				}).orElseThrow(() -> new EntityNotFoundException());
	}

	public void delete(Long id) {
		addressRepository.delete(addressRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException()));
	}
}
